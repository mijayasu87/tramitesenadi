/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.form;

import java.util.ArrayList;
import java.util.List;
import senadi.com.ditramites.model.Documento;
import senadi.com.ditramites.model.VoucherForm;

/**
 *
 * @author micharesp
 */
public class NolineaAux {
    private VoucherForm voucherForm;
    //private List<String> documentos;
    private List<Documento> documentos;
    private String numNoLinea;
    
    private String ftp;
    private String sitio;

    public NolineaAux(){
        documentos = new ArrayList<>();
        ftp = "/var/www/html/solicitudes/media/files/vouchers/";
        sitio = "https://registro.propiedadintelectual.gob.ec/solicitudes/media/files/vouchers/";
    }

    /**
     * @return the voucherForm
     */
    public VoucherForm getVoucherForm() {
        return voucherForm;
    }

    /**
     * @param voucherForm the voucherForm to set
     */
    public void setVoucherForm(VoucherForm voucherForm) {
        this.voucherForm = voucherForm;
    }

    /**
     * @return the numNoLinea
     */
    public String getNumNoLinea() {
        return numNoLinea;
    }

    /**
     * @param numNoLinea the numNoLinea to set
     */
    public void setNumNoLinea(String numNoLinea) {
        this.numNoLinea = numNoLinea;
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
