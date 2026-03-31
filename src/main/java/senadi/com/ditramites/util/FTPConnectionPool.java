package senadi.com.ditramites.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Pool de conexiones SFTP para reutilizar conexiones y mejorar rendimiento
 * en lugar de crear nuevas conexiones para cada operación.
 * 
 * Utiliza Queue sincronizado para manejar múltiples threads de forma segura.
 */
public class FTPConnectionPool {
    
    private static final Logger LOGGER = Logger.getLogger(FTPConnectionPool.class.getName());
    
    //prueba
    private static final String REMOTE_HOST = "10.0.26.130";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "temporal123";
    
    //produccion
//    private static final String REMOTE_HOST = "10.0.20.130";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "B7GJuaxu3Y";
    
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 30000;
    
    private static final int POOL_SIZE = 8; // Número máximo de conexiones en el pool
    private static final int MAX_IDLE_TIME = 300000; // 5 minutos en milisegundos
    
    private static FTPConnectionPool instance;
    private Queue<ChannelSftpWrapper> availableConnections;
    private Queue<ChannelSftpWrapper> usedConnections;
    private int currentSize = 0;
    
    /**
     * Singleton para acceder al pool global
     */
    public static synchronized FTPConnectionPool getInstance() {
        if (instance == null) {
            instance = new FTPConnectionPool();
        }
        return instance;
    }
    
    /**
     * Constructor privado para singleton
     */
    private FTPConnectionPool() {
        this.availableConnections = new LinkedList<>();
        this.usedConnections = new LinkedList<>();
    }
    
    /**
     * Obtiene una conexión SFTP del pool o crea una nueva si es necesario
     */
    public synchronized ChannelSftpWrapper getConnection() throws JSchException {
        
        // Limpiar conexiones expiradas
        cleanExpiredConnections();
        
        ChannelSftpWrapper wrapper;
        
        // Intentar reutilizar una conexión disponible
        while (!availableConnections.isEmpty()) {
            wrapper = availableConnections.poll();
            if (wrapper != null && wrapper.isValid()) {
                LOGGER.info("Reutilizando conexión SFTP del pool");
                usedConnections.add(wrapper);
                return wrapper;
            }
        }
        
        // Si no hay conexiones disponibles y podemos crear más
        if (currentSize < POOL_SIZE) {
            wrapper = createNewConnection();
            usedConnections.add(wrapper);
            return wrapper;
        }
        
        // Si llegamos aquí, esperamos a que se libere una conexión
        LOGGER.warning("Pool lleno, esperando conexión disponible...");
        while (availableConnections.isEmpty()) {
            try {
                wait(1000); // Esperar 1 segundo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new JSchException("Interrumpido esperando conexión");
            }
        }
        
        wrapper = availableConnections.poll();
        if (wrapper != null && wrapper.isValid()) {
            usedConnections.add(wrapper);
            return wrapper;
        }
        
        // Crear nueva conexión si la obtenida no es válida
        wrapper = createNewConnection();
        usedConnections.add(wrapper);
        return wrapper;
    }
    
    /**
     * Retorna una conexión al pool
     */
    public synchronized void releaseConnection(ChannelSftpWrapper wrapper) {
        if (wrapper != null && usedConnections.remove(wrapper)) {
            wrapper.updateLastUsedTime();
            availableConnections.add(wrapper);
            LOGGER.fine("Conexión retornada al pool");
            notifyAll(); // Notificar threads esperando por conexiones
        }
    }
    
    /**
     * Crea una nueva conexión SFTP
     */
    private ChannelSftpWrapper createNewConnection() throws JSchException {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            session.setPassword(PASSWORD);
            
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            
            session.connect(SESSION_TIMEOUT);
            
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect(CHANNEL_TIMEOUT);
            
            currentSize++;
            LOGGER.info("Nueva conexión SFTP creada. Pool size: " + currentSize + "/" + POOL_SIZE);
            
            return new ChannelSftpWrapper(channelSftp, session);
        } catch (JSchException e) {
            LOGGER.log(Level.SEVERE, "Error creando conexión SFTP: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Limpia conexiones que han excedido el tiempo de inactividad
     */
    private void cleanExpiredConnections() {
        long currentTime = System.currentTimeMillis();
        Queue<ChannelSftpWrapper> validConnections = new LinkedList<>();
        
        while (!availableConnections.isEmpty()) {
            ChannelSftpWrapper wrapper = availableConnections.poll();
            if (wrapper.isValid() && (currentTime - wrapper.getLastUsedTime()) < MAX_IDLE_TIME) {
                validConnections.add(wrapper);
            } else {
                wrapper.disconnect();
                currentSize--;
                LOGGER.fine("Conexión expirada eliminada del pool");
            }
        }
        
        availableConnections = validConnections;
    }
    
    /**
     * Cierra todas las conexiones del pool (para shutdown)
     */
    public synchronized void shutdown() {
        LOGGER.info("Cerrando pool de conexiones SFTP");
        
        while (!availableConnections.isEmpty()) {
            ChannelSftpWrapper wrapper = availableConnections.poll();
            wrapper.disconnect();
        }
        
        while (!usedConnections.isEmpty()) {
            ChannelSftpWrapper wrapper = usedConnections.poll();
            wrapper.disconnect();
        }
        
        currentSize = 0;
    }
    
    /**
     * Wrapper para manejar ChannelSftp y Session juntos
     */
    public static class ChannelSftpWrapper {
        private ChannelSftp channelSftp;
        private Session session;
        private long lastUsedTime;
        
        public ChannelSftpWrapper(ChannelSftp channelSftp, Session session) {
            this.channelSftp = channelSftp;
            this.session = session;
            this.lastUsedTime = System.currentTimeMillis();
        }
        
        public ChannelSftp getChannelSftp() {
            return channelSftp;
        }
        
        public Session getSession() {
            return session;
        }
        
        public boolean isValid() {
            return channelSftp != null && channelSftp.isConnected() && 
                   session != null && session.isConnected();
        }
        
        public void updateLastUsedTime() {
            this.lastUsedTime = System.currentTimeMillis();
        }
        
        public long getLastUsedTime() {
            return lastUsedTime;
        }
        
        public void disconnect() {
            try {
                if (channelSftp != null && channelSftp.isConnected()) {
                    channelSftp.disconnect();
                }
                if (session != null && session.isConnected()) {
                    session.disconnect();
                }
            } catch (Exception e) {
                Logger.getLogger(ChannelSftpWrapper.class.getName())
                        .log(Level.WARNING, "Error desconectando: " + e.getMessage());
            }
        }
    }
}
