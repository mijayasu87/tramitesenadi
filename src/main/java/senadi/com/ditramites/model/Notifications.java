/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model;

/**
 *
 * @author micharesp
 */
public class Notifications {
    
    private Integer id;
    private Integer matId;
    private Integer notId;
    private String matter;
    private String document;
    private String createDate;
    private String source;
    private Integer locker;
    private String openDate;
    private String status;
    private String statusText;
    private String type;
    private Integer createdId;
    
    private Integer loId;
    
    private String matterType;
    private String notificationType;

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
     * @return the matId
     */
    public Integer getMatId() {
        return matId;
    }

    /**
     * @param matId the matId to set
     */
    public void setMatId(Integer matId) {
        this.matId = matId;
    }

    /**
     * @return the notId
     */
    public Integer getNotId() {
        return notId;
    }

    /**
     * @param notId the notId to set
     */
    public void setNotId(Integer notId) {
        this.notId = notId;
    }

    /**
     * @return the matter
     */
    public String getMatter() {
        return matter;
    }

    /**
     * @param matter the matter to set
     */
    public void setMatter(String matter) {
        this.matter = matter;
    }

    /**
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the locker
     */
    public Integer getLocker() {
        return locker;
    }

    /**
     * @param locker the locker to set
     */
    public void setLocker(Integer locker) {
        this.locker = locker;
    }

    /**
     * @return the openDate
     */
    public String getOpenDate() {
        return openDate;
    }

    /**
     * @param openDate the openDate to set
     */
    public void setOpenDate(String openDate) {
        this.openDate = openDate;
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
     * @return the createdId
     */
    public Integer getCreatedId() {
        return createdId;
    }

    /**
     * @param createdId the createdId to set
     */
    public void setCreatedId(Integer createdId) {
        this.createdId = createdId;
    }

    /**
     * @return the loId
     */
    public Integer getLoId() {
        return loId;
    }

    /**
     * @param loId the loId to set
     */
    public void setLoId(Integer loId) {
        this.loId = loId;
    }

    /**
     * @return the statusText
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * @param statusText the statusText to set
     */
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    /**
     * @return the matterType
     */
    public String getMatterType() {
        return matterType;
    }

    /**
     * @param matterType the matterType to set
     */
    public void setMatterType(String matterType) {
        this.matterType = matterType;
    }

    /**
     * @return the notificationType
     */
    public String getNotificationType() {
        return notificationType;
    }

    /**
     * @param notificationType the notificationType to set
     */
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
