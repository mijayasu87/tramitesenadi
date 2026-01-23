/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;
import senadi.com.ditramites.model.HallmarkForm;
import senadi.com.ditramites.model.LockerNotifications;
import senadi.com.ditramites.model.Notifications;
import senadi.com.ditramites.model.Owner;
import senadi.com.ditramites.model.mod.CambioCasillero;
import senadi.com.ditramites.model.postgres.PpdiSolicitudSignoDistintivo;
import senadi.com.ditramites.util.Codekru;
import senadi.com.ditramites.util.Controlador;
import senadi.com.ditramites.util.Operaciones;

/**
 *
 * @author michael
 */
@ManagedBean(name = "cambioCasilleroBean")
@ViewScoped
public class CambioCasilleroBean implements Serializable {

    private List<CambioCasillero> cambiosCasillero;
    private List<CambioCasillero> cambiosCasilleroFiltrados;
    private UIData cambiosCasilleroDataTable;
    private CambioCasillero cambioCasillero;

    private UploadedFile file;

    private LoginBean login;

    public CambioCasilleroBean() {
        load();
    }

    private void load() {
        Controlador c = new Controlador();
        cambiosCasillero = c.getCambiosCasilleroSignos();
        login = c.getLogin();
    }

    public void viewProvidencia(ActionEvent ae) {
        FacesMessage msg = null;
        cambioCasillero = (CambioCasillero) cambiosCasilleroDataTable.getRowData();
        if (cambioCasillero != null && cambioCasillero.getId() != null) {
//            cambioCasillero.setFechaProvidencia(new Date());
//            Controlador c = new Controlador();
//            c.updateCambioCasillero(cambioCasillero);
            login.setCambioCasillero(cambioCasillero);
            PrimeFaces.current().ajax().addCallbackParam("doit", true);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "Visualizando la providencia N° " + cambioCasillero.getProvidencia());
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "NO SE ENCONTRARON RESULTADOS");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void prepararProvidencia(ActionEvent ae) {
        FacesMessage msg = null;
        cambioCasillero = (CambioCasillero) cambiosCasilleroDataTable.getRowData();
        if (cambioCasillero != null && cambioCasillero.getId() != null) {
            Controlador c = new Controlador();
            HallmarkForm hfaux = c.getHallmarkForm(cambioCasillero.getTramite());
            System.out.println("antes: "+cambioCasillero.getCasilleroAnterior()+" - "+hfaux.getCasillero());
            if (cambioCasillero.getCasilleroAnterior().equals(hfaux.getCasillero())) {
                PrimeFaces.current().ajax().addCallbackParam("uplo", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "ADJUNTE LA PROVIDENCIA FIRMADA");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "EL TRÁMITE " + cambioCasillero.getTramite() + " NO PERTENECE AL CASILLERO " + cambioCasillero.getCasilleroAnterior());
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "ERROR AL CARGAR LA PROVIDENCIA");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void guardarDocumento(ActionEvent ae) throws IOException {
        FacesMessage msg = null;
        if (cambioCasillero != null && cambioCasillero.getId() != null) {
            if (file != null) {
                if (file.getFileName().toLowerCase().trim().contains("signed")) {
                    Controlador c = new Controlador();
                    String pdfName = cambioCasillero.getTramite() + "_" + cambioCasillero.getProvidencia() + "_cambio_casillero-signed.pdf";
                    pdfName = pdfName.trim().replace(" ", "");
                    HallmarkForm hf = c.getHallmarkForm(cambioCasillero.getTramite());
                    String rutamarca = "/var/www/html/solicitudes/media/files/hallmark_forms/" + hf.getId() + "/" + pdfName;
                    if (uploadDocumentoToExpedient(rutamarca, file, cambioCasillero)) {
                        Codekru cod = new Codekru();

                        String rutacas = "/var/www/html/casilleros/media/files/" + cambioCasillero.getCasilleroAnterior() + "/";
                        String comando = "cp " + rutamarca + " " + rutacas + " && echo 'copiado'";

                        if (cod.exeComando(comando)) {

                            //Notificacion antiguo casillero
                            Notifications notif = new Notifications();
                            String times = Operaciones.formatTimesTamp(new Timestamp(new Date().getTime()));
                            System.out.println("times: " + times);
                            notif.setCreateDate(times);
                            notif.setDocument(pdfName);
                            notif.setMatId(9); //Otros
                            notif.setMatter(cambioCasillero.getTramite());
                            notif.setNotId(1); //Providencia
                            notif.setSource("LOCAL");
                            notif.setCreatedId(1); // primero usuario de iepi_admin
                            notif.setId(0);
                            if (c.guardarNotifications(notif)) {
                                Notifications naux = c.getNotificationsByMatterAndCreateDt(notif.getMatter(), times);
                                if (naux.getId() != null) {
                                    LockerNotifications lon = new LockerNotifications();
                                    lon.setId(0);
                                    lon.setDocument(naux.getDocument());
                                    lon.setLockerId(Integer.valueOf(cambioCasillero.getCasilleroAnterior()));
                                    lon.setNotification_id(naux.getId());
                                    lon.setOpenDt(null);
                                    lon.setStatus("SENT");
                                    if (c.saveLockerNotifications(lon)) {
                                        rutacas = "/var/www/html/casilleros/media/files/" + cambioCasillero.getNuevoCasillero() + "/";
                                        comando = "cp " + rutamarca + " " + rutacas + " && echo 'copiado'";
                                        if (cod.exeComando(comando)) {
                                            lon = new LockerNotifications();
                                            lon.setId(0);
                                            lon.setDocument(naux.getDocument());
                                            lon.setLockerId(Integer.valueOf(cambioCasillero.getNuevoCasillero()));
                                            lon.setNotification_id(naux.getId());
                                            lon.setOpenDt(null);
                                            lon.setStatus("SENT");
                                            if (c.saveLockerNotifications(lon)) {
                                                Owner ownnew = c.getOwnerByLockerId(Integer.parseInt(cambioCasillero.getNuevoCasillero()));
                                                hf.setOwnerId(ownnew.getId());
                                                if (c.updateOwnerHallmark(hf)) {
                                                    PpdiSolicitudSignoDistintivo signo = c.getPpdiSolicitudSignoDistintivoByTramite(hf.getApplicationNumber());
                                                    if (signo.getCodigoSolicitudSigno() != null) {
                                                        signo.setCasilleroIepi(cambioCasillero.getNuevoCasillero());
                                                        if (!c.updatePpdiSolicitudSignoDistintivo(signo)) {
                                                            System.out.println("Error al actualizar el casillero en ppdi_solicitud_signo: " + signo.getNumeroTramite());
                                                        }
                                                    }
                                                    cambioCasillero.setDocumento(pdfName);
                                                    cambioCasillero.setEstado("REALIZADO");
                                                    cambioCasillero.setFechaNotificacion(new Date());
                                                    cambioCasillero.setUsuario(login.getNombre());
                                                    if (c.updateCambioCasillero(cambioCasillero)) {
                                                        load();
                                                        PrimeFaces.current().ajax().addCallbackParam("provid", true);
                                                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "DOCUMENTO DE PROVIDENCIA " + cambioCasillero.getProvidencia() + " ADJUNTADO Y NOTIFICADO CORRECTAMENTE");
                                                    }
                                                } else {
                                                    System.out.println("Se notificó correctamente, pero no se actualizó el casillero en el trámite formularios: " + hf.getApplicationNumber());
                                                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "SE NOTIFICÓ CORRECTAMENTE, PERO NO SE ACTUALIZÓ EL CASILLERO EN EL TRÁMITE FORMULARIOS");
                                                }
                                            } else {
                                                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE NOTIFICÓ LA PROVIDENCIA AL NUEVO CASILLERO");
                                            }
                                        } else {
                                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE NOTIFICÓ LA PROVIDENCIA AL NUEVO CASILLERO");
                                        }
                                    } else {
                                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE NOTIFICÓ LA PROVIDENCIA AL ANTIGUO CASILLERO");
                                    }
                                } else {
                                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA EN LA NOTIFICACIÓN AL ANTIGUO CASILLERO");
                                }
                            } else {
                                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE GUARDÓ LA NOTIFICACIÓN DEL CAMBIO DE CASILLERO");
                            }
                        } else {
                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE NOTIFICÓ LA PROVIDENCIA A LOS CASILLEROS");
                        }
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL CARGAR EL DOCUMENTO EN EL EXPEDIENTE");
                    }
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "FIRMA", "ADJUNTE UN DOCUMENTO .pdf FIRMADO");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "CARGUE UN DOCUMENTO VÁLIDO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUEDE CARGAR LA PROVIDENCIA");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean uploadDocumentoToExpedient(String ruta, UploadedFile documento, CambioCasillero cambio) throws IOException {
        if (documento != null) {
            Codekru cod = new Codekru(130);
            cod.copyAInputStreamToRemoteMachine(file.getInputStream(), ruta);
//                Files.copy(file.getInputStream(), fileaux.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } else {
            return false;
        }
    }

    public void validarProvidencia(CambioCasillero caux) {
        FacesMessage msg = null;
        if (caux != null) {
            if (caux.getDocumento() != null && !caux.getDocumento().trim().isEmpty()) {
                Controlador c = new Controlador();

                HallmarkForm hallmark = c.getHallmarkForm(caux.getTramite());
                if (hallmark.getId() != null) {

                    String rutaux = "https://registro.propiedadintelectual.gob.ec/solicitudes/media/files/hallmark_forms/" + hallmark.getId() + "/" + caux.getDocumento();

                    System.out.println("rutaprovidencia: " + rutaux);
                    PrimeFaces.current().ajax().addCallbackParam("viewdoc", true);
                    PrimeFaces.current().ajax().addCallbackParam("view", rutaux);
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "PROVIDENCIA CARGADA");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "NO SE ENCONTRÓ LA PROVIDENCIA DE CAMBIO DE CASILLERO");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE ENCONTRÓ EL DOCUMENTO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "NO SE CARGÓ CORRECTAMENTE EL REGISTRO SELECCIONADO");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @return the cambiosCasillero
     */
    public List<CambioCasillero> getCambiosCasillero() {
        return cambiosCasillero;
    }

    /**
     * @param cambiosCasillero the cambiosCasillero to set
     */
    public void setCambiosCasillero(List<CambioCasillero> cambiosCasillero) {
        this.cambiosCasillero = cambiosCasillero;
    }

    /**
     * @return the cambiosCasilleroFiltrados
     */
    public List<CambioCasillero> getCambiosCasilleroFiltrados() {
        return cambiosCasilleroFiltrados;
    }

    /**
     * @param cambiosCasilleroFiltrados the cambiosCasilleroFiltrados to set
     */
    public void setCambiosCasilleroFiltrados(List<CambioCasillero> cambiosCasilleroFiltrados) {
        this.cambiosCasilleroFiltrados = cambiosCasilleroFiltrados;
    }

    /**
     * @return the cambiosCasilleroDataTable
     */
    public UIData getCambiosCasilleroDataTable() {
        return cambiosCasilleroDataTable;
    }

    /**
     * @param cambiosCasilleroDataTable the cambiosCasilleroDataTable to set
     */
    public void setCambiosCasilleroDataTable(UIData cambiosCasilleroDataTable) {
        this.cambiosCasilleroDataTable = cambiosCasilleroDataTable;
    }

    /**
     * @return the cambioCasillero
     */
    public CambioCasillero getCambioCasillero() {
        return cambioCasillero;
    }

    /**
     * @param cambioCasillero the cambioCasillero to set
     */
    public void setCambioCasillero(CambioCasillero cambioCasillero) {
        this.cambioCasillero = cambioCasillero;
    }

    /**
     * @return the login
     */
    public LoginBean getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(LoginBean login) {
        this.login = login;
    }

    /**
     * @return the file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
