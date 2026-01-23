/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.form;

import java.util.ArrayList;
import java.util.List;
import senadi.com.ditramites.model.Notifications;

/**
 *
 * @author micharesp
 */
public class NotificationsAux {
    private Notifications notification;
    private List<String> documentos;
    private String numNotification;
    
    private String ftp;
    private String sitio;

    public NotificationsAux(){
        documentos = new ArrayList<>();
        ftp = "/var/www/html/casilleros/media/files/";
        sitio = "https://registro.propiedadintelectual.gob.ec/casilleros/media/files/";
    }
    

    /**
     * @return the documentos
     */
    public List<String> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos the documentos to set
     */
    public void setDocumentos(List<String> documentos) {
        this.documentos = documentos;
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
     * @return the notification
     */
    public Notifications getNotification() {
        return notification;
    }

    /**
     * @param notification the notification to set
     */
    public void setNotification(Notifications notification) {
        this.notification = notification;
    }

    /**
     * @return the numNotification
     */
    public String getNumNotification() {
        return numNotification;
    }

    /**
     * @param numNotification the numNotification to set
     */
    public void setNumNotification(String numNotification) {
        this.numNotification = numNotification;
    }
}
