/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model;

import java.util.Calendar;
import java.util.Date;
import senadi.com.ditramites.util.ParametrosBD;

/**
 *
 * @author michael
 */
public class Cpis {

    private String resolutionNumber;
    private String subject;
    private Date resourceDate;
    private String earlierProcedure;
    private String boardMember;
    private Integer cpiYear;
    private String currentProcedure;
    private String cpiReource;
    private String denomination;
    private String recurrent;
    private String status;
    private Date lastNotificationDate;
    private Date resolutionDate;
    private String writtenFile;
    private String memoNumber;
    private Date memoDate;
    private String hall;
    
    private String urlCpi;
    
    public Cpis(){
        urlCpi = ParametrosBD.urlCpis;
    }

    /**
     * @return the resolutionNumber
     */
    public String getResolutionNumber() {
        return resolutionNumber;
    }

    /**
     * @param resolutionNumber the resolutionNumber to set
     */
    public void setResolutionNumber(String resolutionNumber) {
        this.resolutionNumber = resolutionNumber;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the resourceDate
     */
    public Date getResourceDate() {
        return resourceDate;
    }

    /**
     * @param resourceDate the resourceDate to set
     */
    public void setResourceDate(Date resourceDate) {
        this.resourceDate = resourceDate;
    }

    /**
     * @return the earlierProcedure
     */
    public String getEarlierProcedure() {
        return earlierProcedure;
    }

    /**
     * @param earlierProcedure the earlierProcedure to set
     */
    public void setEarlierProcedure(String earlierProcedure) {
        this.earlierProcedure = earlierProcedure;
    }

    /**
     * @return the boardMember
     */
    public String getBoardMember() {
        return boardMember;
    }

    /**
     * @param boardMember the boardMember to set
     */
    public void setBoardMember(String boardMember) {
        this.boardMember = boardMember;
    }

    /**
     * @return the cpiYear
     */
    public Integer getCpiYear() {
        return cpiYear;
    }

    /**
     * @param cpiYear the cpiYear to set
     */
    public void setCpiYear(Integer cpiYear) {
        this.cpiYear = cpiYear;
    }

    /**
     * @return the currentProcedure
     */
    public String getCurrentProcedure() {
        return currentProcedure;
    }

    /**
     * @param currentProcedure the currentProcedure to set
     */
    public void setCurrentProcedure(String currentProcedure) {
        this.currentProcedure = currentProcedure;
    }

    /**
     * @return the cpiReource
     */
    public String getCpiReource() {
        return cpiReource;
    }

    /**
     * @param cpiReource the cpiReource to set
     */
    public void setCpiReource(String cpiReource) {
        this.cpiReource = cpiReource;
    }

    /**
     * @return the denomination
     */
    public String getDenomination() {
        return denomination;
    }

    /**
     * @param denomination the denomination to set
     */
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    /**
     * @return the recurrent
     */
    public String getRecurrent() {
        return recurrent;
    }

    /**
     * @param recurrent the recurrent to set
     */
    public void setRecurrent(String recurrent) {
        this.recurrent = recurrent;
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
     * @return the lastNotificationDate
     */
    public Date getLastNotificationDate() {
        return lastNotificationDate;
    }

    /**
     * @param lastNotificationDate the lastNotificationDate to set
     */
    public void setLastNotificationDate(Date lastNotificationDate) {
        this.lastNotificationDate = lastNotificationDate;
    }

    /**
     * @return the resolutionDate
     */
    public Date getResolutionDate() {
        return resolutionDate;
    }

    /**
     * @param resolutionDate the resolutionDate to set
     */
    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    /**
     * @return the writtenFile
     */
    public String getWrittenFile() {
        return writtenFile;
    }

    /**
     * @param writtenFile the writtenFile to set
     */
    public void setWrittenFile(String writtenFile) {
        this.writtenFile = writtenFile;
    }

    /**
     * @return the memoNumber
     */
    public String getMemoNumber() {
        return memoNumber;
    }

    /**
     * @param memoNumber the memoNumber to set
     */
    public void setMemoNumber(String memoNumber) {
        this.memoNumber = memoNumber;
    }

    /**
     * @return the memoDate
     */
    public Date getMemoDate() {
        return memoDate;
    }

    /**
     * @param memoDate the memoDate to set
     */
    public void setMemoDate(Date memoDate) {
        this.memoDate = memoDate;
    }

    /**
     * @return the hall
     */
    public String getHall() {
        return hall;
    }

    /**
     * @param hall the hall to set
     */
    public void setHall(String hall) {
        this.hall = hall;
    }

    public boolean hasData() {
        return hasText(resolutionNumber)
                || hasText(earlierProcedure)
                || hasText(currentProcedure)
                || hasText(denomination);
    }

    @Override
    public String toString() {
        return getEarlierProcedure() + ", " + getCurrentProcedure() + ", " + getDenomination();
    }

    /**
     * @return the urlCpi
     */
    public String getUrlCpi() {
        return urlCpi;
    }

    /**
     * @param urlCpi the urlCpi to set
     */
    public void setUrlCpi(String urlCpi) {
        this.urlCpi = urlCpi;
    }

    public String getDocumentUrl() {
        return buildDocumentUrl(getNotificationYearPath(), getNotificationMonthPath());
    }

    public String getResolutionDocumentUrl() {
        return buildDocumentUrl(getResolutionYearPath(), getResolutionMonthPath());
    }

    public String getNotificationYearPath() {

        if (lastNotificationDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lastNotificationDate);
            return String.valueOf(calendar.get(Calendar.YEAR));
        }

        return "";
    }

    public String getNotificationMonthPath() {
        if (lastNotificationDate == null) {
            return "";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastNotificationDate);
        int month = calendar.get(Calendar.MONTH) + 1;
        return month < 10 ? "0" + month : String.valueOf(month);
    }

    public String getResolutionYearPath() {
        if (resolutionDate == null) {
            return "";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(resolutionDate);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public String getResolutionMonthPath() {
        if (resolutionDate == null) {
            return "";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(resolutionDate);
        int month = calendar.get(Calendar.MONTH) + 1;
        return month < 10 ? "0" + month : String.valueOf(month);
    }

    private String buildDocumentUrl(String yearPath, String monthPath) {
        if (!hasText(urlCpi) || !hasText(writtenFile) || !hasText(yearPath) || !hasText(monthPath)) {
            return "";
        }

        return urlCpi + yearPath + "/" + monthPath + "/" + writtenFile;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

}
