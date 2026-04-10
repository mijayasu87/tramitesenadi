/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model;

import java.sql.Timestamp;
import java.util.Date;
import senadi.com.ditramites.util.ParametrosBD;

/**
 *
 * @author michael
 */
public class PowerOfAttorney {
    private Integer id;
    private String number;
    private String type;
    private Date effectiveDateFrom;
    private Date effectiveDateTo;
    private Date issueDate;
    private String issueAddress;
    private String attorneyLawFirm;
    private Timestamp applicationDate;
    private String applicationNumber;
    private String file;
    private String fileLawFirm;
    private String serviceWindowUser;
    private String status;
    private Integer ownerId;
    
    private String attorneyUrl;
    
    private String granters;
    private String attorneys;
    
    public PowerOfAttorney(){
        attorneyUrl = ParametrosBD.urlPath + "powerofattorney/";
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
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the effectiveDateFrom
     */
    public Date getEffectiveDateFrom() {
        return effectiveDateFrom;
    }

    /**
     * @param effectiveDateFrom the effectiveDateFrom to set
     */
    public void setEffectiveDateFrom(Date effectiveDateFrom) {
        this.effectiveDateFrom = effectiveDateFrom;
    }

    /**
     * @return the effectiveDateTo
     */
    public Date getEffectiveDateTo() {
        return effectiveDateTo;
    }

    /**
     * @param effectiveDateTo the effectiveDateTo to set
     */
    public void setEffectiveDateTo(Date effectiveDateTo) {
        this.effectiveDateTo = effectiveDateTo;
    }

    /**
     * @return the issueDate
     */
    public Date getIssueDate() {
        return issueDate;
    }

    /**
     * @param issueDate the issueDate to set
     */
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * @return the issueAddress
     */
    public String getIssueAddress() {
        return issueAddress;
    }

    /**
     * @param issueAddress the issueAddress to set
     */
    public void setIssueAddress(String issueAddress) {
        this.issueAddress = issueAddress;
    }

    /**
     * @return the attorneyLawFirm
     */
    public String getAttorneyLawFirm() {
        return attorneyLawFirm;
    }

    /**
     * @param attorneyLawFirm the attorneyLawFirm to set
     */
    public void setAttorneyLawFirm(String attorneyLawFirm) {
        this.attorneyLawFirm = attorneyLawFirm;
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
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @return the fileLawFirm
     */
    public String getFileLawFirm() {
        return fileLawFirm;
    }

    /**
     * @param fileLawFirm the fileLawFirm to set
     */
    public void setFileLawFirm(String fileLawFirm) {
        this.fileLawFirm = fileLawFirm;
    }

    /**
     * @return the serviceWindowUser
     */
    public String getServiceWindowUser() {
        return serviceWindowUser;
    }

    /**
     * @param serviceWindowUser the serviceWindowUser to set
     */
    public void setServiceWindowUser(String serviceWindowUser) {
        this.serviceWindowUser = serviceWindowUser;
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
     * @return the attorneyUrl
     */
    public String getAttorneyUrl() {
        return attorneyUrl;
    }

    /**
     * @param attorneyUrl the attorneyUrl to set
     */
    public void setAttorneyUrl(String attorneyUrl) {
        this.attorneyUrl = attorneyUrl;
    }

    /**
     * @return the granters
     */
    public String getGranters() {
        return granters;
    }

    /**
     * @param granters the granters to set
     */
    public void setGranters(String granters) {
        this.granters = granters;
    }

    /**
     * @return the attorneys
     */
    public String getAttorneys() {
        return attorneys;
    }

    /**
     * @param attorneys the attorneys to set
     */
    public void setAttorneys(String attorneys) {
        this.attorneys = attorneys;
    }
    
    
    
}
