/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.form;

import java.util.ArrayList;
import java.util.List;
import senadi.com.ditramites.model.Documento;
import senadi.com.ditramites.model.OppositionForms;
import senadi.com.ditramites.util.ParametrosBD;

/**
 *
 * @author micharesp
 */
public class OppositionFormsAux {

    private OppositionForms oppositionForm;
//    private List<String> documentos;
    private List<Documento> documentos;

    private String numOposicion;

    private String ftp;
    private String sitio;

    public OppositionFormsAux() {
        documentos = new ArrayList<>();
        ftp = ParametrosBD.ftpPath + "opposition_forms/";
        sitio = ParametrosBD.urlPath + "opposition_forms/";
    }

    /**
     * @return the oppositionForm
     */
    public OppositionForms getOppositionForm() {
        return oppositionForm;
    }

    /**
     * @param oppositionForm the oppositionForm to set
     */
    public void setOppositionForm(OppositionForms oppositionForm) {
        this.oppositionForm = oppositionForm;
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
     * @return the numOposicion
     */
    public String getNumOposicion() {
        return numOposicion;
    }

    /**
     * @param numOposicion the numOposicion to set
     */
    public void setNumOposicion(String numOposicion) {
        this.numOposicion = numOposicion;
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
