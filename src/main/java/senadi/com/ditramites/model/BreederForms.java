/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model;

import java.sql.Timestamp;

/**
 *
 * @author michael
 */
public class BreederForms {

    private Integer id;
    private Integer ownerId;
    private Timestamp createDate;
    private Timestamp applicationDate;
    private String applicationNumber;
    private String proposedName;
    private String commercialName;
    private String status;
    private String group;
    private Integer paymentReceiptId;
    private String discountFile;

    private String sitio;
    private String ftp;

    private Integer idVegetableForms;

    public BreederForms() {
//        sitio = "https://registro.propiedadintelectual.gob.ec/solicitudes/media/files/breeder_forms/";
        sitio = "https://pruebas.propiedadintelectual.gob.ec/solicitudes/media/files/breeder_forms/";
        ftp = "/var/www/html/solicitudes/media/files/breeder_forms/";
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
     * @return the createDate
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the applicationDate
     */
    public Timestamp getApplicationDate() {
        return applicationDate;
    }

    /**
     * @param applicationDate the applicationDate to set
     */
    public void setApplicationDate(Timestamp applicationDate) {
        this.applicationDate = applicationDate;
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
     * @return the proposedName
     */
    public String getProposedName() {
        return proposedName;
    }

    /**
     * @param proposedName the proposedName to set
     */
    public void setProposedName(String proposedName) {
        this.proposedName = proposedName;
    }

    /**
     * @return the commercialName
     */
    public String getCommercialName() {
        return commercialName;
    }

    /**
     * @param commercialName the commercialName to set
     */
    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
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
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return the paymentReceiptId
     */
    public Integer getPaymentReceiptId() {
        return paymentReceiptId;
    }

    /**
     * @param paymentReceiptId the paymentReceiptId to set
     */
    public void setPaymentReceiptId(Integer paymentReceiptId) {
        this.paymentReceiptId = paymentReceiptId;
    }

    /**
     * @return the discountFile
     */
    public String getDiscountFile() {
        return discountFile;
    }

    /**
     * @param discountFile the discountFile to set
     */
    public void setDiscountFile(String discountFile) {
        this.discountFile = discountFile;
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
     * @return the idVegetableForms
     */
    public Integer getIdVegetableForms() {
        return idVegetableForms;
    }

    /**
     * @param idVegetableForms the idVegetableForms to set
     */
    public void setIdVegetableForms(Integer idVegetableForms) {
        this.idVegetableForms = idVegetableForms;
    }

    public String getEstado() {
        switch (getStatus()) {
            case "SAVED":
                return "GUARDADO";
            case "PREVIEW":
                return "VISTA PREVIA";
            case "FINISHED":
                if (getPaymentReceiptId() != null && getPaymentReceiptId() != 0) {
                    return "PAGADO PERO SIN INICIO DE PROCESO";
                } else {
                    return "PENDIENTE DE PAGO";
                }
            case "DELIVERED":
                return "PROCESO INICIADO";
            default:
                return "ESTADO DESCONOCIDO";
        }
    }
}
