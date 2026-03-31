package senadi.com.ditramites.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Gestor de caché global thread-safe para resultados de consultas DAO.
 * 
 * Beneficios:
 * - Compartido entre todos los usuarios (multi-usuario)
 * - Solo lectura → datos siempre consistentes
 * - TTL automático para cada tipo de dato
 * - Límite de tamaño para evitar memory leaks
 */
public class CacheManager {
    
    private static final Logger LOGGER = Logger.getLogger(CacheManager.class.getName());
    private static CacheManager instance;
    
    // Configuración de tamaño y limpieza
    private static final int MAX_CACHE_SIZE = 1000;      // Máximo de entradas
    private static final long CLEANUP_INTERVAL = 5;      // Limpieza cada 5 minutos
    
    private ConcurrentHashMap<String, CacheEntry<?>> cache;
    private ScheduledExecutorService scheduler;
    
    /**
     * Singleton thread-safe
     */
    public static synchronized CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }
    
    /**
     * Constructor privado
     */
    private CacheManager() {
        this.cache = new ConcurrentHashMap<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "CacheCleanupThread");
            t.setDaemon(true);
            return t;
        });
        
        // Iniciar limpieza periódica
        startCleanupTask();
        LOGGER.info("CacheManager inicializado");
    }
    
    /**
     * Guarda un valor en caché con TTL específico.
     * Hace copia defensiva de listas para evitar modificaciones posteriores.
     */
    public <T> void put(String key, T value, int ttlMinutes) {
        if (key == null || value == null) {
            return;
        }
        
        // Limitar tamaño del caché
        if (cache.size() >= MAX_CACHE_SIZE) {
            LOGGER.warning("Caché en límite máximo (" + MAX_CACHE_SIZE + "). Limpiando...");
            cleanupExpired();
        }
        
        // Hacer copia defensiva de listas para evitar que cambios posteriores afecten el caché
        Object valueToCache = value;
        if (value instanceof List) {
            valueToCache = new ArrayList<>((List<?>) value);
            LOGGER.fine("Caché: Copia defensiva de List para clave: " + key);
        }
        
        long expirationTime = System.nanoTime() + TimeUnit.MINUTES.toNanos(ttlMinutes);
        cache.put(key, new CacheEntry<>(valueToCache, expirationTime));
        LOGGER.fine("Caché coloca: " + key + " (TTL: " + ttlMinutes + "min)");
    }
    
    /**
     * Obtiene un valor del caché si no ha expirado.
     * Devuelve una copia defensiva de listas para evitar modificaciones.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        if (key == null) {
            return null;
        }
        
        CacheEntry<?> entry = cache.get(key);
        
        if (entry != null && !entry.isExpired()) {
            Object value = entry.getValue();
            
            // Si es una lista, devolver copia defensiva
            if (value instanceof List) {
                LOGGER.fine("Caché HIT (con copia defensiva): " + key);
                return (T) new ArrayList<>((List<?>) value);
            }
            
            LOGGER.fine("Caché HIT: " + key);
            return (T) value;
        }
        
        // Remover entrada expirada
        if (entry != null && entry.isExpired()) {
            cache.remove(key);
        }
        
        LOGGER.fine("Caché MISS: " + key);
        return null;
    }
    
    /**
     * Limpia manualmente una entrada
     */
    public void invalidate(String key) {
        cache.remove(key);
        LOGGER.fine("Caché invalidada: " + key);
    }
    
    /**
     * Limpia todas las entradas caducadas
     */
    public void cleanupExpired() {
        int initialSize = cache.size();
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
        int removedCount = initialSize - cache.size();
        if (removedCount > 0) {
            LOGGER.info("Limpias " + removedCount + " entradas caducadas. Tamaño actual: " + cache.size());
        }
    }
    
    /**
     * Limpia toda la caché
     */
    public void clear() {
        cache.clear();
        LOGGER.info("Caché completamente limpiada");
    }
    
    /**
     * Obtiene estadísticas del caché
     */
    public CacheStats getStats() {
        int total = cache.size();
        int expired = (int) cache.values().stream().filter(CacheEntry::isExpired).count();
        int valid = total - expired;
        return new CacheStats(total, expired, valid);
    }
    
    /**
     * Inicia tarea de limpieza automática
     */
    private void startCleanupTask() {
        scheduler.scheduleAtFixedRate(
            this::cleanupExpired,
            CLEANUP_INTERVAL, 
            CLEANUP_INTERVAL, 
            TimeUnit.MINUTES
        );
    }
    
    /**
     * Detiene el gestor de caché (para shutdown)
     */
    public void shutdown() {
        LOGGER.info("Cerrando CacheManager");
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        clear();
    }
    
    // ==================== MÉTODOS HELPER PARA CLAVES ====================
    
    /**
     * Claves para CRÍTICO (P0)
     */
    public static String keyHallmarkForm(String applicationNumber) {
        return "HALLMARK|" + applicationNumber;
    }
    
    public static String keyScopesForms(String applicationNumber) {
        return "SCOPES|" + applicationNumber;
    }
    
    public static String keyPpdiSolicitud(String applicationNumber) {
        return "PPDI|" + applicationNumber;
    }
    
    /**
     * Claves para IMPORTANTE (P1)
     */
    public static String keyOppositionForms(String applicationNumber, boolean searched) {
        return "OPPOSITION|" + applicationNumber + "|" + searched;
    }
    
    public static String keyRenewalForm(String applicationNumber) {
        return "RENEWAL|" + applicationNumber;
    }
    
    public static String keyRenovaciones(String tituloOrTramite, boolean aviso) {
        return "RENOVACIONES|" + tituloOrTramite + "|" + aviso;
    }
    
    // ==================== INNER CLASSES ====================
    
    /**
     * Entrada de caché con timestamp de expiración
     */
    private static class CacheEntry<T> {
        private final T value;
        private final long expirationTime;
        
        CacheEntry(T value, long expirationTime) {
            this.value = value;
            this.expirationTime = expirationTime;
        }
        
        T getValue() {
            return value;
        }
        
        boolean isExpired() {
            return System.nanoTime() > expirationTime;
        }
    }
    
    /**
     * Estadísticas del caché
     */
    public static class CacheStats {
        public int totalEntries;
        public int expiredEntries;
        public int validEntries;
        
        CacheStats(int total, int expired, int valid) {
            this.totalEntries = total;
            this.expiredEntries = expired;
            this.validEntries = valid;
        }
        
        @Override
        public String toString() {
            return String.format("CacheStats{total=%d, valid=%d, expired=%d}", 
                totalEntries, validEntries, expiredEntries);
        }
    }
    
    /**
     * Constantes para TTLs globales
     */
    public static class TTL {
        public static final int HALLMARK = 30;
        public static final int SCOPES = 30;
        public static final int PPDI = 60;
        public static final int OPPOSITION = 30;
        public static final int RENEWAL = 60;        // Muy reutilizado
        public static final int RENOVACIONES = 30;
        public static final int DEFAULT = 20;
    }
}
