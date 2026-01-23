/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author micharesp
 */
@Entity
@Table(name = "scope_forms")
public class ScopeForms implements Serializable{

    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "form_type_id")
    private Integer formTypeId;
    
    @Column(name = "payment_receipt_id")
    private Integer paymentReceiptId;
    
    @Column(name = "owner_id")
    private Integer ownerId;
    
    @Column(name = "application_number")
    private String applicationNumber;
    
    @Column(name = "affected_application_number")
    private String affectedApplicationNumber;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "application_date")
    private Timestamp applicationDate;
    
    @Column(name = "expedient")
    private String expedient;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "status")
    private String status;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;
    
    private String casillero;

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
     * @return the formTypeId
     */
    public Integer getFormTypeId() {
        return formTypeId;
    }

    /**
     * @param formTypeId the formTypeId to set
     */
    public void setFormTypeId(Integer formTypeId) {
        this.formTypeId = formTypeId;
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
     * @return the affectedApplicationNumber
     */
    public String getAffectedApplicationNumber() {
        return affectedApplicationNumber;
    }

    /**
     * @param affectedApplicationNumber the affectedApplicationNumber to set
     */
    public void setAffectedApplicationNumber(String affectedApplicationNumber) {
        this.affectedApplicationNumber = affectedApplicationNumber;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
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
}
