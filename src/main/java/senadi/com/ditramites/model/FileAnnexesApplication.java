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
public class FileAnnexesApplication {

    private Integer id;
    private String fileName;
    private String fileDescription;
    private String userUpload;
    private Timestamp uploadDate;
    private String fileStatus;
    private String applicationType;
    private String applicationNumber;
    private String userUpdate;
    private Timestamp updateDate;

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
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileDescription
     */
    public String getFileDescription() {
        return fileDescription;
    }

    /**
     * @param fileDescription the fileDescription to set
     */
    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    /**
     * @return the userUpload
     */
    public String getUserUpload() {
        return userUpload;
    }

    /**
     * @param userUpload the userUpload to set
     */
    public void setUserUpload(String userUpload) {
        this.userUpload = userUpload;
    }

    /**
     * @return the uploadDate
     */
    public Timestamp getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate the uploadDate to set
     */
    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * @return the fileStatus
     */
    public String getFileStatus() {
        return fileStatus;
    }

    /**
     * @param fileStatus the fileStatus to set
     */
    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
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
     * @return the userUpdate
     */
    public String getUserUpdate() {
        return userUpdate;
    }

    /**
     * @param userUpdate the userUpdate to set
     */
    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    /**
     * @return the updateDate
     */
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }
}
