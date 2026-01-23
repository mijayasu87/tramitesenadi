/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package senadi.com.ditramites.util;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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
            if (conf.getTipo().equals(tipo)) {
                if (tipo.equals("ACCIÓN DE PERSONAL") || tipo.equals("RESOLUCIÓN")) {
                    return conf.getNombre() + ", de " + Operaciones.formatDateToLarge(conf.getFecha());
                } else {
                    return conf.getNombre();
                }
            }
        }
        return "";
    }

    public FileInputStream viewCambioCasillero(String path, InputStream rutaJrxml, CambioCasillero cambio, String rutapdf,
            List<ConfiguracionCC> config, Usuario usuario) { //, Delegado secretaria
        try {
            FileInputStream entrada;
            JasperReport reportePrincipal = JasperCompileManager.compileReport(rutaJrxml);

            Map parametro = new HashMap();
            //parametro.put("title", "INSCRIPCIÓN LICENCIA DE USO No. " + licencia.getLicenciaNo() + " - SENADI");
            parametro.put("resol", getConfig("ACCIÓN DE PERSONAL", config));
            parametro.put("resolnot", getConfig("RESOLUCIÓN", config));

            parametro.put("subrogante", getConfig("DELEGADO", config));
            parametro.put("subrogantedel", getConfig("DELEGACIÓN", config));

            parametro.put("secretaria", usuario.getNombre());
            parametro.put("denosecre", usuario.getCargo());

            parametro.put("SUBREPORT_DIR", path + "/");
            parametro.put("idCambioCasillero", cambio.getId());

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportePrincipal, parametro, conn);
            if (jasperPrint.getPages().isEmpty()) {
                return null;
            }

            DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();

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
