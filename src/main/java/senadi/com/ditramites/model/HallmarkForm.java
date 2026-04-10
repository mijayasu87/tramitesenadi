/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senadi.com.ditramites.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import senadi.com.ditramites.util.ParametrosBD;

/**
 *
 * @author Michael Yanangómez
 */
@Entity
@Table(name = "hallmark_forms")
public class HallmarkForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private Integer nizaClassId;

    private Integer paymentReceiptId;

    private Timestamp applicationDate;

    private String applicationNumber;

    private Timestamp createDate;

    private String denomination;

    private String description;

    private String expedient;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date expedientDate;

    private int ownerId;

    private String priorityNumber;

    private String status;

    private String sitio;
    private String ftp;
    private String discountFile;    

    private String powerOfAttorney;

    private String titulo;
    private String fechaTitulo;
    private String fechaVencimiento;
    private String estado;
    private String tipoSigno;
    private String naturalezaSigno;
    private String gaceta;
    private String casillero;

    public HallmarkForm() {
        sitio = ParametrosBD.urlPath + "hallmark_forms/";
        ftp = ParametrosBD.ftpPath + "hallmark_forms/";
        
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationNumber() {
        return this.applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Timestamp getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getDenomination() {
        return this.denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getPriorityNumber() {
        return this.priorityNumber;
    }

    public void setPriorityNumber(String priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the nizaClassId
     */
    public Integer getNizaClassId() {
        return nizaClassId;
    }

    /**
     * @param nizaClassId the nizaClassId to set
     */
    public void setNizaClassId(Integer nizaClassId) {
        this.nizaClassId = nizaClassId;
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
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the fechaTitulo
     */
    public String getFechaTitulo() {
        return fechaTitulo;
    }

    /**
     * @param fechaTitulo the fechaTitulo to set
     */
    public void setFechaTitulo(String fechaTitulo) {
        this.fechaTitulo = fechaTitulo;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the fechaVencimiento
     */
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * @param fechaVencimiento the fechaVencimiento to set
     */
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * @return the tipoSigno
     */
    public String getTipoSigno() {
        return tipoSigno;
    }

    /**
     * @param tipoSigno the tipoSigno to set
     */
    public void setTipoSigno(String tipoSigno) {
        this.tipoSigno = tipoSigno;
    }

    /**
     * @return the naturalezaSigno
     */
    public String getNaturalezaSigno() {
        return naturalezaSigno;
    }

    /**
     * @param naturalezaSigno the naturalezaSigno to set
     */
    public void setNaturalezaSigno(String naturalezaSigno) {
        this.naturalezaSigno = naturalezaSigno;
    }

    /**
     * @return the gaceta
     */
    public String getGaceta() {
        return gaceta;
    }

    /**
     * @param gaceta the gaceta to set
     */
    public void setGaceta(String gaceta) {
        this.gaceta = gaceta;
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
     * @return the powerOfAttorney
     */
    public String getPowerOfAttorney() {
        return powerOfAttorney;
    }

    /**
     * @param powerOfAttorney the powerOfAttorney to set
     */
    public void setPowerOfAttorney(String powerOfAttorney) {
        this.powerOfAttorney = powerOfAttorney;
    }
}
