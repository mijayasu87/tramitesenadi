/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.mod;

import java.sql.Timestamp;

/**
 *
 * @author michael
 */
public class FileAnnex {

    private Integer applicationId;
    private Integer annexId;
    private String file;
    private String fileDescription;
    private String userUpload;
    private Timestamp uploadDate;
    
    private String documentName;

    /**
     * @return the applicationId
     */
    public Integer getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * @return the annexId
     */
    public Integer getAnnexId() {
        return annexId;
    }

    /**
     * @param annexId the annexId to set
     */
    public void setAnnexId(Integer annexId) {
        this.annexId = annexId;
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
     * @return the documentName
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * @param documentName the documentName to set
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

}
