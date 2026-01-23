/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model;

/**
 *
 * @author micharesp
 */
public class PatentForms {

    private Integer id;
    private Integer patentTypeId;
    private Integer paymentReceitpId;
    private Integer ownerId;
    private String applicationNumber;
    private String applicationDate;
    private String title;
    private String internationalClassification;
    private String summary;
    private String expedient;
    private String status;
    private String tipo;
    private Integer claims;
    private String image;
    
    private String casillero;

    private String sitio;
    private String ftp;

    public PatentForms() {
        sitio = "https://registro.propiedadintelectual.gob.ec/solicitudes/media/files/patent_forms/";
        ftp = "/var/www/html/solicitudes/media/files/patent_forms/";
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the patentTypeId
     */
    public Integer getPatentTypeId() {
        return patentTypeId;
    }

    /**
     * @param patentTypeId the patentTypeId to set
     */
    public void setPatentTypeId(Integer patentTypeId) {
        this.patentTypeId = patentTypeId;
    }

    /**
     * @return the paymentReceitpId
     */
    public Integer getPaymentReceitpId() {
        return paymentReceitpId;
    }

    /**
     * @param paymentReceitpId the paymentReceitpId to set
     */
    public void setPaymentReceitpId(Integer paymentReceitpId) {
        this.paymentReceitpId = paymentReceitpId;
    }

    /**
     * @return the ownerId
     */
    public Integer getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return the applicationNumber
     */
    public String getApplicationNumber() {
        return applicationNumber;
    }

    /**
     * @param applicationNumber the applicationNumber to set
     */
    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    /**
     * @return the applicationDate
     */
    public String getApplicationDate() {
        return applicationDate;
    }

    /**
     * @param applicationDate the applicationDate to set
     */
    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the internationalClassification
     */
    public String getInternationalClassification() {
        return internationalClassification;
    }

    /**
     * @param internationalClassification the internationalClassification to set
     */
    public void setInternationalClassification(String internationalClassification) {
        this.internationalClassification = internationalClassification;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return the expedient
     */
    public String getExpedient() {
        return expedient;
    }

    /**
     * @param expedient the expedient to set
     */
    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the claims
     */
    public Integer getClaims() {
        return claims;
    }

    /**
     * @param claims the claims to set
     */
    public void setClaims(Integer claims) {
        this.claims = claims;
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
     * @return the casillero
     */
    public String getCasillero() {
        return casillero;
    }

    /**
     * @param casillero the casillero to set
     */
    public void setCasillero(String casillero) {
        this.casillero = casillero;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }
}
