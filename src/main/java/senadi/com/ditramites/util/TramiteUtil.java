/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.util;

import java.util.ArrayList;
import java.util.List;
import senadi.com.ditramites.model.Documento;
import senadi.com.ditramites.model.PatentForms;

/**
 *
 * @author michael
 */
public class TramiteUtil {

    public boolean cargarPatentFormAVisualizarByTramite(String applicationNumber, PatentForms patentForm, String tituloTramiteText,
            boolean conpatente, List<String> documentosExpediente, List<Documento> documentos) {
        
        System.out.println("Patente inicio: "+Operaciones.getCurrentTimeStamp());
        Controlador c = new Controlador();
        patentForm = c.getPatentFormByApplicationNumber(applicationNumber);

        if (patentForm.getId() != null) {
            System.out.println("titulo: " + patentForm.getTitle());
            tituloTramiteText = "PATENTE";
            conpatente = true;

            Integer pago = patentForm.getPaymentReceitpId();
            if (pago != null && pago != 0) {
                if (patentForm.getStatus().equals("DELIVERED")) {
                    patentForm.setStatus("PAGADO E INICIADO EL PROCESO");
                } else {
                    patentForm.setStatus("PAGADO PERO NO TIENE EL PROCESO INICIADO");
                }
            } else {
                patentForm.setStatus("SIN PAGO");
            }
            FTPFiles files = new FTPFiles();
            documentosExpediente = files.listarDirectorio(patentForm.getFtp() + patentForm.getId());
            documentos = new ArrayList<>();
            for (int i = 0; i < documentosExpediente.size(); i++) {
                Documento doc = new Documento();
                doc.setArchivo(documentosExpediente.get(i));
                String nombre = c.getPatentNombreArchivo(doc.getArchivo());
                if (!nombre.isEmpty()) {
//                    System.out.println("docu " + (i + 1) + ": " + nombre);
                    doc.setTipo(nombre);

                } else {
                    if (doc.getArchivo().contains("pdf_patentfrm_")) {
                        doc.setTipo("Formulario");
                    } else if (doc.getArchivo().contains("pdf_voucher_patentfrm_")) {
                        doc.setTipo("Comprobante");
                    } else if (doc.getArchivo().contains("logo_")) {
                        doc.setTipo("Logo");
                    } else {
                        doc.setTipo(doc.getArchivo());
                    }
                    if (patentForm.getImage() != null && !patentForm.getImage().trim().isEmpty()) {
                        if (doc.getArchivo().equals(patentForm.getImage())) {
                            doc.setTipo("ResumenImagen");
                        }
                    }
                }
                documentos.add(doc);
            }

            conpatente = true;
        } else {
            conpatente = false;
        }
        System.out.println("Patente fin: "+Operaciones.getCurrentTimeStamp());
        return conpatente;
    }
}
