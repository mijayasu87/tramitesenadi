/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.form;

import java.util.ArrayList;
import java.util.List;
import senadi.com.ditramites.model.Documento;
import senadi.com.ditramites.model.TutelageForms;

/**
 *
 * @author micharesp
 */
public class TutelageFormsAux {
    private TutelageForms tutelageForm;
    //private List<String> documentos;
    private List<Documento> documentos;
    private String ftp;
    private String sitio;

    public TutelageFormsAux(){
        documentos = new ArrayList<>();
        ftp = "/var/www/html/solicitudes/media/files/tutelage_forms/";
        sitio = "https://registro.propiedadintelectual.gob.ec/solicitudes/media/files/tutelage_forms/";
    }    

    /**
     * @return the ftp
     */
    public String getFtp() {
        return ftp;
    }

    /**
     * @param ftp the ftp to set
     */
    public void setFtp(String ftp) {
        this.ftp = ftp;
    }

    /**
     * @return the sitio
     */
    public String getSitio() {
        return sitio;
    }

    /**
     * @param sitio the sitio to set
     */
    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    /**
     * @return the tutelageForm
     */
    public TutelageForms getTutelageForm() {
        return tutelageForm;
    }

    /**
     * @param tutelageForm the tutelageForm to set
     */
    public void setTutelageForm(TutelageForms tutelageForm) {
        this.tutelageForm = tutelageForm;
    }

    /**
     * @return the documentos
     */
    public List<Documento> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos the documentos to set
     */
    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }
}
