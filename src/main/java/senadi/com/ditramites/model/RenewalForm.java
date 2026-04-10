/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Michael Yanangómez
 */
@Entity
@Table(name = "renewal_forms")
public class RenewalForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Column(name = "application_date")
    private Timestamp applicationDate;

    @Column(name = "application_number")
    private String applicationNumber;

    @Column(name = "branch_office")
    private String branchOffice;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "debug_date")
    private Date debugDate;

    @Column(name = "debug_id")
    private Integer debugId;

    @Column(name = "debug_table_name")
    private String debugTableName;

    private String denomination;

    @Column(name = "discount_file")
    private String discountFile;

    private String expedient;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expedient_date")
    private Date expedientDate;

    @Column(name = "license_type")
    private String licenseType;    

    @Column(name = "owner_id")
    private int ownerId;

    @Column(name = "power_attorney")
    private String powerAttorney;

    @Column(name = "power_attorney_register_type")
    private String powerAttorneyRegisterType;

    @Column(name = "search_clue")
    private String searchClue;

    private String status;

    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "voucher_file")
    private String voucherFile;
    
    private Integer paymentReceiptId;
    
    private String tipo;
    
    private String casillero;

    public String getApplicationNumber() {
        return this.applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getBranchOffice() {
        return this.branchOffice;
    }

    public void setBranchOffice(String branchOffice) {
        this.branchOffice = branchOffice;
    }

    public Timestamp getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Date getDebugDate() {
        return this.debugDate;
    }

    public void setDebugDate(Date debugDate) {
        this.debugDate = debugDate;
    }

    public Integer getDebugId() {
        return this.debugId;
    }

    public void setDebugId(Integer debugId) {
        this.debugId = debugId;
    }

    public String getDebugTableName() {
        return this.debugTableName;
    }

    public void setDebugTableName(String debugTableName) {
        this.debugTableName = debugTableName;
    }

    public String getDenomination() {
        return this.denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDiscountFile() {
        return this.discountFile;
    }

    public void setDiscountFile(String discountFile) {
        this.discountFile = discountFile;
    }

    public String getExpedient() {
        return this.expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }

    public Date getExpedientDate() {
        return this.expedientDate;
    }

    public void setExpedientDate(Date expedientDate) {
        this.expedientDate = expedientDate;
    }

    public String getLicenseType() {
        return this.licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getPowerAttorney() {
        return this.powerAttorney;
    }

    public void setPowerAttorney(String powerAttorney) {
        this.powerAttorney = powerAttorney;
    }

    public String getPowerAttorneyRegisterType() {
        return this.powerAttorneyRegisterType;
    }

    public void setPowerAttorneyRegisterType(String powerAttorneyRegisterType) {
        this.powerAttorneyRegisterType = powerAttorneyRegisterType;
    }

    public String getSearchClue() {
        return this.searchClue;
    }

    public void setSearchClue(String searchClue) {
        this.searchClue = searchClue;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionNumber() {
        return this.transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getVoucherFile() {
        return this.voucherFile;
    }

    public void setVoucherFile(String voucherFile) {
        this.voucherFile = voucherFile;
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

}
