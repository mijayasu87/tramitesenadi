/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author michael
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatentJson {

    private ApplicationNumber applicationNumber;
    private String appplicationDate;
    private String transactionSubCode;
    private String currentStatusCode;
    private String applicantFileReference;
    private List<Title> titleBag;
    private Integer claimTotalQuantity;
    private CustomFields customFields;

    private List<AbstractData> abstracts;

    private List<Design> designBag;
    private List<DesignStatement> designStatementBag;
    private String ipCategory;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Design {

        private List<Description> descriptionBag;
        // getters/setters

        public Design() {
            descriptionBag = new ArrayList<>();
        }

        /**
         * @return the descriptionBag
         */
        public List<Description> getDescriptionBag() {
            return descriptionBag;
        }

        /**
         * @param descriptionBag the descriptionBag to set
         */
        public void setDescriptionBag(List<Description> descriptionBag) {
            this.descriptionBag = descriptionBag;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Description {

        private String text;
        // getters/setters

        /**
         * @return the text
         */
        public String getText() {
            return text;
        }

        /**
         * @param text the text to set
         */
        public void setText(String text) {
            this.text = text;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DesignStatement {

        private String text;
        // getters/setters

        /**
         * @return the text
         */
        public String getText() {
            return text;
        }

        /**
         * @param text the text to set
         */
        public void setText(String text) {
            this.text = text;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApplicationNumber {

        private String applicationNumberText;
        // getters y setters

        /**
         * @return the applicationNumberText
         */
        public String getApplicationNumberText() {
            return applicationNumberText;
        }

        /**
         * @param applicationNumberText the applicationNumberText to set
         */
        public void setApplicationNumberText(String applicationNumberText) {
            this.applicationNumberText = applicationNumberText;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Title {

        private String text;
        // getters y setters

        /**
         * @return the text
         */
        public String getText() {
            return text;
        }

        /**
         * @param text the text to set
         */
        public void setText(String text) {
            this.text = text;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomFields {

        private List<DataNumeric> dataNumericBag;
        // getters y setters

        /**
         * @return the dataNumericBag
         */
        public List<DataNumeric> getDataNumericBag() {
            return dataNumericBag;
        }

        /**
         * @param dataNumericBag the dataNumericBag to set
         */
        public void setDataNumericBag(List<DataNumeric> dataNumericBag) {
            this.dataNumericBag = dataNumericBag;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataNumeric {

        private String id;
        private Integer value;
        // getters y setters

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the value
         */
        public Integer getValue() {
            return value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(Integer value) {
            this.value = value;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AbstractData {

        private List<PBag> pBag;
        // getters y setters

        public AbstractData() {
            pBag = new ArrayList<>();
        }

        /**
         * @return the pBag
         */
        public List<PBag> getpBag() {
            return pBag;
        }

        /**
         * @param pBag the pBag to set
         */
        public void setpBag(List<PBag> pBag) {
            this.pBag = pBag;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PBag {

        private String text;
        // getters y setters

        /**
         * @return the text
         */
        public String getText() {
            return text;
        }

        /**
         * @param text the text to set
         */
        public void setText(String text) {
            this.text = text;
        }
    }

    /**
     * @return the applicationNumber
     */
    public ApplicationNumber getApplicationNumber() {
        return applicationNumber;
    }

    /**
     * @param applicationNumber the applicationNumber to set
     */
    public void setApplicationNumber(ApplicationNumber applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    /**
     * @return the appplicationDate
     */
    public String getAppplicationDate() {
        return appplicationDate;
    }

    /**
     * @param appplicationDate the appplicationDate to set
     */
    public void setAppplicationDate(String appplicationDate) {
        this.appplicationDate = appplicationDate;
    }

    /**
     * @return the transactionSubCode
     */
    public String getTransactionSubCode() {
        return transactionSubCode;
    }

    /**
     * @param transactionSubCode the transactionSubCode to set
     */
    public void setTransactionSubCode(String transactionSubCode) {
        this.transactionSubCode = transactionSubCode;
    }

    /**
     * @return the currentStatusCode
     */
    public String getCurrentStatusCode() {
        return currentStatusCode;
    }

    /**
     * @param currentStatusCode the currentStatusCode to set
     */
    public void setCurrentStatusCode(String currentStatusCode) {
        this.currentStatusCode = currentStatusCode;
    }

    /**
     * @return the applicantFileReference
     */
    public String getApplicantFileReference() {
        return applicantFileReference;
    }

    /**
     * @param applicantFileReference the applicantFileReference to set
     */
    public void setApplicantFileReference(String applicantFileReference) {
        this.applicantFileReference = applicantFileReference;
    }

    /**
     * @return the titleBag
     */
    public List<Title> getTitleBag() {
        return titleBag;
    }

    /**
     * @param titleBag the titleBag to set
     */
    public void setTitleBag(List<Title> titleBag) {
        this.titleBag = titleBag;
    }

    /**
     * @return the claimTotalQuantity
     */
    public Integer getClaimTotalQuantity() {
        return claimTotalQuantity;
    }

    /**
     * @param claimTotalQuantity the claimTotalQuantity to set
     */
    public void setClaimTotalQuantity(Integer claimTotalQuantity) {
        this.claimTotalQuantity = claimTotalQuantity;
    }

    /**
     * @return the customFields
     */
    public CustomFields getCustomFields() {
        return customFields;
    }

    /**
     * @param customFields the customFields to set
     */
    public void setCustomFields(CustomFields customFields) {
        this.customFields = customFields;
    }

    /**
     * @return the abstracts
     */
    public List<AbstractData> getAbstracts() {
        return abstracts;
    }

    /**
     * @param abstracts the abstracts to set
     */
    public void setAbstracts(List<AbstractData> abstracts) {
        this.abstracts = abstracts;
    }

    /**
     * @return the designBag
     */
    public List<Design> getDesignBag() {
        return designBag;
    }

    /**
     * @param designBag the designBag to set
     */
    public void setDesignBag(List<Design> designBag) {
        this.designBag = designBag;
    }

    /**
     * @return the designStatementBag
     */
    public List<DesignStatement> getDesignStatementBag() {
        return designStatementBag;
    }

    /**
     * @param designStatementBag the designStatementBag to set
     */
    public void setDesignStatementBag(List<DesignStatement> designStatementBag) {
        this.designStatementBag = designStatementBag;
    }

    /**
     * @return the ipCategory
     */
    public String getIpCategory() {
        return ipCategory;
    }

    /**
     * @param ipCategory the ipCategory to set
     */
    public void setIpCategory(String ipCategory) {
        this.ipCategory = ipCategory;
    }
}
