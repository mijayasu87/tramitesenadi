package senadi.com.ditramites.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio para cargar datos de trámites en paralelo.
 * Ejecuta múltiples cargas independientes simultáneamente para mejorar rendimiento.
 */
public class TramiteLoaderService {
    
    private static final Logger LOGGER = Logger.getLogger(TramiteLoaderService.class.getName());
    private static final int NUM_THREADS = 6; // Para 6 tipos de cargas diferentes
    private static final int TIMEOUT_SECONDS = 60;
    
    private ExecutorService executorService;
    
    public TramiteLoaderService() {
        this.executorService = Executors.newFixedThreadPool(NUM_THREADS);
    }
    
    /**
     * Ejecuta una tarea de carga de forma asincrónica
     */
    public void executeTask(String taskName, LoaderTask task) {
        executorService.submit(() -> {
            long startTime = System.currentTimeMillis();
            try {
                LOGGER.info("Iniciando carga: " + taskName);
                task.load();
                long duration = System.currentTimeMillis() - startTime;
                LOGGER.info("Completada carga: " + taskName + " en " + duration + "ms");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error en carga: " + taskName, e);
            }
        });
    }
    
    /**
     * Espera a que se completen todas las tareas (con timeout)
     */
    public void waitForCompletion() {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                LOGGER.warning("Timeout esperando finalización de cargas. Cancelando tareas pendientes.");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Interrupción esperando cargas", e);
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Interfaz funcional para tareas de carga
     */
    @FunctionalInterface
    public interface LoaderTask {
        void load() throws Exception;
    }
}
