/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model;

import java.sql.Timestamp;
import senadi.com.ditramites.util.ParametrosBD;

/**
 *
 * @author michael
 */
public class DenominationForms {

    private Integer id;
    private Integer paymentReceiptId;
    private Integer ownerId;
    private String applicationNumber;
    private Timestamp applicationDate;
    private String denomination;
    private String partOfProcess;
    private String inspectionPlace;
    private String status;
    private String discountFile;
    private String sitio;
    private String ftp;

    public DenominationForms() {
        sitio = ParametrosBD.urlPath + "denomination_forms/";
        ftp = ParametrosBD.ftpPath + "denomination_forms/";
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
     * @return the denomination
     */
    public String getDenomination() {
        return denomination;
    }

    /**
     * @param denomination the denomination to set
     */
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    /**
     * @return the partOfProcess
     */
    public String getPartOfProcess() {
        return partOfProcess;
    }

    /**
     * @param partOfProcess the partOfProcess to set
     */
    public void setPartOfProcess(String partOfProcess) {
        this.partOfProcess = partOfProcess;
    }

    /**
     * @return the inspectionPlace
     */
    public String getInspectionPlace() {
        return inspectionPlace;
    }

    /**
     * @param inspectionPlace the inspectionPlace to set
     */
    public void setInspectionPlace(String inspectionPlace) {
        this.inspectionPlace = inspectionPlace;
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
