/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package senadi.com.ditramites.util;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.Normalizer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import senadi.com.ditramites.model.mod.CambioCasillero;
import senadi.com.ditramites.model.mod.ConfiguracionCC;
import senadi.com.ditramites.model.mod.Usuario;

/**
 *
 * @author michael
 */
public class Report implements Serializable {

    private Connection conn;

    public Report() {
        try {
            //localhost
//            String user = "root";
//            String pass = "MichaRoot6*";
//            String basd = "senadi_transferencia";
//            String host = "localhost";

            //produccion
            String user = "root";
            String pass = "B8GJuaxu4Y:2020";
            String basd = "senadi_transferencia";
            String host = "10.0.20.140";
            Class.forName("com.mysql.jdbc.Driver"); //se carga el driver
            String url = "jdbc:mysql://" + host + "/" + basd + "?serverTimezone=GMT-5&autoReconnect=true&useSSL=false";
            conn = DriverManager.getConnection(url, user, pass);

        } catch (Exception ex) {
            System.out.println("Error conexion: " + ex);
            ex.printStackTrace();
        }
    }

    /*Cierra la conexión a la base de datos mysql*/
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getConfig(String tipo, List<ConfiguracionCC> config) {
        for (ConfiguracionCC conf : config) {
            if (sameConfigType(conf.getTipo(), tipo)) {
                if (tipo.equals("ACCIÓN DE PERSONAL") || tipo.equals("RESOLUCIÓN")) {
                    return conf.getNombre() + ", de " + Operaciones.formatDateToLarge(conf.getFecha());
                } else {
                    return conf.getNombre();
                }
            }
        }
        return "";
    }

    private boolean sameConfigType(String left, String right) {
        return normalizeKey(left).equals(normalizeKey(right));
    }

    private String normalizeKey(String value) {
        if (value == null) {
            return "";
        }
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .trim()
                .toUpperCase();
        return normalized.replaceAll("\\s+", " ");
    }

    private String getConfigByFuente(String fuente, List<ConfiguracionCC> config, String tipoSignos, String tipoPatentes, String tipoOposicion, String tipoDefault) {
        String fuenteNormalizada = normalizeKey(fuente);
        String tipoObjetivo = tipoSignos;
        if (fuenteNormalizada.contains("PATENTE")) {
            tipoObjetivo = tipoPatentes;
        } else if (fuenteNormalizada.contains("OPOSICION")) {
            tipoObjetivo = tipoOposicion;
        }
        String value = getConfig(tipoObjetivo, config);
        if (value == null || value.trim().isEmpty()) {
            return getConfig(tipoDefault, config);
        }
        return value;
    }

    public FileInputStream viewCambioCasillero(String path, InputStream rutaJrxml, CambioCasillero cambio, String rutapdf,
            List<ConfiguracionCC> config, Usuario usuario) { //, Delegado secretaria
        try {
            FileInputStream entrada;
            JasperReport reportePrincipal = JasperCompileManager.compileReport(rutaJrxml);

            Map<String, Object> parametro = new HashMap<>();
            
            if (normalizeKey(cambio.getFuente()).contains("SIGNO")) {
                parametro.put("titulo", "DIRECCIÓN TÉCNICA DE SIGNOS DISNTINTIVOS");
                parametro.put("fuente", "SIGNO DISTINTIVO");
            } else if (normalizeKey(cambio.getFuente()).contains("OPOSICION")) {
                parametro.put("titulo", "DIRECCIÓN TÉCNICA DE OPOSICIONES");
                parametro.put("fuente", "OPOSICIÓN");
            } else {
                parametro.put("titulo", "DIRECCIÓN TÉCNICA DE PATENTES");
                parametro.put("fuente", "PATENTE");
            }
            
            parametro.put("resol", getConfig("ACCIÓN DE PERSONAL", config));
            parametro.put("resolnot", getConfig("RESOLUCIÓN", config));

            parametro.put("subrogante", getConfigByFuente(cambio.getFuente(), config, "DELEGADO SIGNOS", "DELEGADO PATENTES", "DELEGADO OPOSICION", "DELEGADO"));
            parametro.put("subrogantedel", getConfigByFuente(cambio.getFuente(), config, "DELEGACIÓN SIGNOS", "DELEGACIÓN PATENTES", "DELEGACIÓN OPOSICION", "DELEGACIÓN"));

            parametro.put("secretaria", usuario.getNombre());
            parametro.put("denosecre", usuario.getCargo());

            parametro.put("SUBREPORT_DIR", path + "/");
            parametro.put("idCambioCasillero", cambio.getId());

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportePrincipal, parametro, conn);
            if (jasperPrint.getPages().isEmpty()) {
                return null;
            }

            try (OutputStream out = new FileOutputStream(rutapdf + ".pdf")) {
                JRPdfExporter exporter = new JRPdfExporter();
                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                ExporterInput inp = new SimpleExporterInput(jasperPrint);
                configuration.setCreatingBatchModeBookmarks(true);
                configuration.set128BitKey(Boolean.TRUE);

                exporter.setConfiguration(configuration);
                exporter.setExporterInput(inp);
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));

                exporter.exportReport();

            }
            entrada = new FileInputStream(rutapdf + ".pdf");
            return entrada;
        } catch (Exception ex) {
            System.out.println("Error print cambio casillero: " + ex);
            return null;
        }

    }

}
