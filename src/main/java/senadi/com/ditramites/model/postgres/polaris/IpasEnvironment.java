/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.postgres.polaris;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author michael
 */
@Entity
@Table(name = "ipas_environment", schema = "senadi_polaris")
public class IpasEnvironment implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ipas_environment_seq")
    @SequenceGenerator(name = "ipas_environment_seq", sequenceName = "ipasenvironment_id_seq", schema = "senadi_polaris", allocationSize = 1)
    private Integer id;

    @Column(name = "cognito_url")
    private String cognitoUrl;
    
    @Column(name = "app_client_id")
    private String appClientId;
    
    @Column(name = "app_client_secret")
    private String appClientSecret;
    
    @Column(name = "token_url")
    private String tokenUrl;
    
    @Column(name = "base_url")
    private String baseUrl;
    
    @Column(name = "languague_code")
    private String languagueCode;
    
    @Column(name = "source_system_identifier")
    private String sourceSystemIdentifier;
    
    @Column(name = "document_name")
    private String documentName;
    
    @Column(name = "document_format_category")
    private String documentFormatCategory;
    
    @Column(name = "document_kind_category")
    private String documentKindCategory;
    
    @Column(name = "patent_document_number")
    private String patentDocumentNumber;
    
    @Column(name = "design_document_number")
    private String designDocumentNumber;
    
    @Column(name = "document_language_source")
    private String documentLanguageSource;
    
    @Column(name = "document_origin")
    private String documentOrigin;
    
    @Column(name = "comment_text")
    private String commentText;
    
    @Column(name = "tipo")
    private String tipo;
    
    @Column(name = "active")
    private Boolean active;

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
     * @return the cognitoUrl
     */
    public String getCognitoUrl() {
        return cognitoUrl;
    }

    /**
     * @param cognitoUrl the cognitoUrl to set
     */
    public void setCognitoUrl(String cognitoUrl) {
        this.cognitoUrl = cognitoUrl;
    }

    /**
     * @return the appClientId
     */
    public String getAppClientId() {
        return appClientId;
    }

    /**
     * @param appClientId the appClientId to set
     */
    public void setAppClientId(String appClientId) {
        this.appClientId = appClientId;
    }

    /**
     * @return the appClientSecret
     */
    public String getAppClientSecret() {
        return appClientSecret;
    }

    /**
     * @param appClientSecret the appClientSecret to set
     */
    public void setAppClientSecret(String appClientSecret) {
        this.appClientSecret = appClientSecret;
    }

    /**
     * @return the tokenUrl
     */
    public String getTokenUrl() {
        return tokenUrl;
    }

    /**
     * @param tokenUrl the tokenUrl to set
     */
    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    /**
     * @return the baseUrl
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @param baseUrl the baseUrl to set
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * @return the sourceSystemIdentifier
     */
    public String getSourceSystemIdentifier() {
        return sourceSystemIdentifier;
    }

    /**
     * @param sourceSystemIdentifier the sourceSystemIdentifier to set
     */
    public void setSourceSystemIdentifier(String sourceSystemIdentifier) {
        this.sourceSystemIdentifier = sourceSystemIdentifier;
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

    /**
     * @return the documentFormatCategory
     */
    public String getDocumentFormatCategory() {
        return documentFormatCategory;
    }

    /**
     * @param documentFormatCategory the documentFormatCategory to set
     */
    public void setDocumentFormatCategory(String documentFormatCategory) {
        this.documentFormatCategory = documentFormatCategory;
    }

    /**
     * @return the documentKindCategory
     */
    public String getDocumentKindCategory() {
        return documentKindCategory;
    }

    /**
     * @param documentKindCategory the documentKindCategory to set
     */
    public void setDocumentKindCategory(String documentKindCategory) {
        this.documentKindCategory = documentKindCategory;
    }

    /**
     * @return the patentDocumentNumber
     */
    public String getPatentDocumentNumber() {
        return patentDocumentNumber;
    }

    /**
     * @param patentDocumentNumber the patentDocumentNumber to set
     */
    public void setPatentDocumentNumber(String patentDocumentNumber) {
        this.patentDocumentNumber = patentDocumentNumber;
    }

    /**
     * @return the designDocumentNumber
     */
    public String getDesignDocumentNumber() {
        return designDocumentNumber;
    }

    /**
     * @param designDocumentNumber the designDocumentNumber to set
     */
    public void setDesignDocumentNumber(String designDocumentNumber) {
        this.designDocumentNumber = designDocumentNumber;
    }

    /**
     * @return the documentLanguageSource
     */
    public String getDocumentLanguageSource() {
        return documentLanguageSource;
    }

    /**
     * @param documentLanguageSource the documentLanguageSource to set
     */
    public void setDocumentLanguageSource(String documentLanguageSource) {
        this.documentLanguageSource = documentLanguageSource;
    }

    /**
     * @return the documentOrigin
     */
    public String getDocumentOrigin() {
        return documentOrigin;
    }

    /**
     * @param documentOrigin the documentOrigin to set
     */
    public void setDocumentOrigin(String documentOrigin) {
        this.documentOrigin = documentOrigin;
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
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the languagueCode
     */
    public String getLanguagueCode() {
        return languagueCode;
    }

    /**
     * @param languagueCode the languagueCode to set
     */
    public void setLanguagueCode(String languagueCode) {
        this.languagueCode = languagueCode;
    }

    /**
     * @return the commentText
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * @param commentText the commentText to set
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

}
