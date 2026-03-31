package senadi.com.ditramites.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener para gestionar el ciclo de vida del FTP Connection Pool
 * Se ejecuta cuando la aplicación inicia y se detiene.
 */
@WebListener
public class FTPPoolLifecycleListener implements ServletContextListener {
    
    private static final Logger LOGGER = Logger.getLogger(FTPPoolLifecycleListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("=== Inicializando FTP Connection Pool ===");
        try {
            // Pre-calentar el pool: crear algunas conexiones iniciales
            FTPConnectionPool pool = FTPConnectionPool.getInstance();
            LOGGER.info("FTP Connection Pool inicializado correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inicializando FTP Connection Pool", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("=== Cerrando FTP Connection Pool ===");
        try {
            FTPConnectionPool pool = FTPConnectionPool.getInstance();
            pool.shutdown();
            LOGGER.info("FTP Connection Pool cerrado correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error cerrando FTP Connection Pool", e);
        }
    }
}
