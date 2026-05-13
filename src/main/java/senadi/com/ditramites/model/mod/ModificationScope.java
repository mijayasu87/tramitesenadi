/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.mod;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author michael
 */
@Entity
@Table(name = "modification_scope")
public class ModificationScope implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "scope_number")
    private String scopeNumber;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "submission_date")
    private Timestamp submissionDate;

    @Column(name = "affected_application_number")
    private String affectedApplicationNumber;

    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "status")
    private String status;

    @Column(name = "applicant_id")
    private Integer applicantId;

    @Column(name = "pathscope")
    private String pathScope;

    @Transient
    private String fileAlcancePath;
    
    @Column(name = "modification_type")
    private String modificationType;
    
    @Column(name = "application_type")
    private String applicationType;

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
     * @return the scopeNumber
     */
    public String getScopeNumber() {
        return scopeNumber;
    }

    /**
     * @param scopeNumber the scopeNumber to set
     */
    public void setScopeNumber(String scopeNumber) {
        this.scopeNumber = scopeNumber;
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
     * @return the submissionDate
     */
    public Timestamp getSubmissionDate() {
        return submissionDate;
    }

    /**
     * @param submissionDate the submissionDate to set
     */
    public void setSubmissionDate(Timestamp submissionDate) {
        this.submissionDate = submissionDate;
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
     * @return the applicantId
     */
    public Integer getApplicantId() {
        return applicantId;
    }

    /**
     * @param applicantId the applicantId to set
     */
    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    /**
     * @return the pathScope
     */
    public String getPathScope() {
        return pathScope;
    }

    /**
     * @param pathScope the pathScope to set
     */
    public void setPathScope(String pathScope) {
        this.pathScope = pathScope;
    }

    /**
     * @return the fileAlcancePath
     */
    public String getFileAlcancePath() {
        return fileAlcancePath;
    }

    /**
     * @param fileAlcancePath the fileAlcancePath to set
     */
    public void setFileAlcancePath(String fileAlcancePath) {
        this.fileAlcancePath = fileAlcancePath;
    }

    /**
     * @return the modificationType
     */
    public String getModificationType() {
        return modificationType;
    }

    /**
     * @param modificationType the modificationType to set
     */
    public void setModificationType(String modificationType) {
        this.modificationType = modificationType;
    }

    /**
     * @return the applicationType
     */
    public String getApplicationType() {
        return applicationType;
    }

    /**
     * @param applicationType the applicationType to set
     */
    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

}
