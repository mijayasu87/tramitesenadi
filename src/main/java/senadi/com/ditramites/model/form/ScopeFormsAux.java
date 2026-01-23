/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.form;

import java.util.ArrayList;
import java.util.List;
import senadi.com.ditramites.model.Documento;
import senadi.com.ditramites.model.ScopeForms;

/**
 *
 * @author micharesp
 */
public class ScopeFormsAux {
    private ScopeForms scopeForm;
//    private List<String> documentos;
    private List<Documento> documentos;
    private String numAlcance;
    
    private String ftp;
    private String sitio;

    public ScopeFormsAux(){
        documentos = new ArrayList<>();
        ftp = "/var/www/html/solicitudes/media/files/scope_forms/";
        sitio = "https://registro.propiedadintelectual.gob.ec/solicitudes/media/files/scope_forms/";
    }
    

//    /**
//     * @return the documentos
//     */
//    public List<String> getDocumentos() {
//        return documentos;
//    }
//
//    /**
//     * @param documentos the documentos to set
//     */
//    public void setDocumentos(List<String> documentos) {
//        this.documentos = documentos;
//    }

    /**
     * @return the scopeForm
     */
    public ScopeForms getScopeForm() {
        return scopeForm;
    }

    /**
     * @param scopeForm the scopeForm to set
     */
    public void setScopeForm(ScopeForms scopeForm) {
        this.scopeForm = scopeForm;
    }

    /**
     * @return the numAlcance
     */
    public String getNumAlcance() {
        return numAlcance;
    }

    /**
     * @param numAlcance the numAlcance to set
     */
    public void setNumAlcance(String numAlcance) {
        this.numAlcance = numAlcance;
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
