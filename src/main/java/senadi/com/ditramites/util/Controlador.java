/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import senadi.com.ditramites.bean.LoginBean;
import senadi.com.ditramites.dao.DAOConsultasAdmin;
import senadi.com.ditramites.dao.DAOConsultasCasil;
import senadi.com.ditramites.dao.DAOConsultasDep;
import senadi.com.ditramites.dao.DAOConsultasForm;
import senadi.com.ditramites.dao.DAOInsertDocuments;
import senadi.com.ditramites.dao.mod.CambioCasilleroDAO;
import senadi.com.ditramites.dao.mod.ConfiguracionCCDAO;
import senadi.com.ditramites.dao.mod.TransferenciaDAO;
import senadi.com.ditramites.dao.mod.UsuarioCasilleroDAO;
import senadi.com.ditramites.dao.procesos.PpdiSolicitudSignoDAO;
import senadi.com.ditramites.dao.ren.RenovacionDAO;
import senadi.com.ditramites.model.BreederForms;
import senadi.com.ditramites.model.Cpis;
import senadi.com.ditramites.model.HallmarkForm;
import senadi.com.ditramites.model.HallmarkFormDepurada;
import senadi.com.ditramites.model.FileAnnexesApplication;
import senadi.com.ditramites.model.mod.FileAnnex;
import senadi.com.ditramites.model.LockerNotifications;
import senadi.com.ditramites.model.Notifications;
import senadi.com.ditramites.model.DenominationForms;
import senadi.com.ditramites.model.OppositionForms;
import senadi.com.ditramites.model.Owner;
import senadi.com.ditramites.model.PatentForms;
import senadi.com.ditramites.model.PlayForm;
import senadi.com.ditramites.model.PowerOfAttorney;
import senadi.com.ditramites.model.RenewalForm;
import senadi.com.ditramites.model.ScopeForms;
import senadi.com.ditramites.model.TutelageForms;
import senadi.com.ditramites.model.VoucherForm;
import senadi.com.ditramites.model.mod.CambioCasillero;
import senadi.com.ditramites.model.mod.CambioDomicilio;
import senadi.com.ditramites.model.mod.CambioNombre;
import senadi.com.ditramites.model.mod.ConfiguracionCC;
import senadi.com.ditramites.model.mod.LicenciaUso;
import senadi.com.ditramites.model.mod.PrendaComercial;
import senadi.com.ditramites.model.modren.Renovacion;
import senadi.com.ditramites.model.mod.SubLicenciaUso;
import senadi.com.ditramites.model.mod.Transferencia;
import senadi.com.ditramites.model.postgres.PpdiSolicitudSignoDistintivo;
import senadi.com.ditramites.model.postgres.PpdiTituloSignoDistintivo;
import senadi.com.ditramites.model.mod.Usuario;
import senadi.com.ditramites.model.postgres.polaris.IpasApi;
import senadi.com.ditramites.model.postgres.polaris.IpasEnvironment;
import senadi.com.ditramites.model.postgres.polaris.dao.IpasApiDAO;
import senadi.com.ditramites.model.postgres.polaris.dao.IpasEnvironmentDAO;

/**
 *
 * @author micharesp
 */
public class Controlador {

    public LoginBean getLogin() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        LoginBean loginB = (LoginBean) session.getAttribute("loginBean");
        return loginB;
    }

    public String getApplicationType(String applicationNumber) {
        DAOConsultasForm df = new DAOConsultasForm();
        return df.getApplicationType(applicationNumber);
    }

    public Integer getVegetableFormsId(String applicationNumber) {
        DAOConsultasForm df = new DAOConsultasForm();
        return df.getVegetableFormsId(applicationNumber);
    }

    public HallmarkForm getHallmarkForm(String applicationNumber) {
        DAOConsultasForm hd = new DAOConsultasForm();
        return hd.getHallmarkForm(applicationNumber);
    }

    public PowerOfAttorney getPowerOfAttorneyByApplicationNumber(String applicationNumber) {
        DAOConsultasForm ddf = new DAOConsultasForm();
        return ddf.getPowerOfAttorneyRemakeByApplicationNumber(applicationNumber);
    }

    public BreederForms getBreederForm(String applicationNumber) {
        DAOConsultasForm vf = new DAOConsultasForm();
        return vf.getBreederForm(applicationNumber);
    }

    public List<ScopeForms> getScopeFormsByAfforApplicationNumber(String afforApplicationNumber, boolean aviso) {
        DAOConsultasForm sd = new DAOConsultasForm();
        return sd.getScopeFormsByAfforApplicationNumber(afforApplicationNumber, aviso);
    }

    public PpdiSolicitudSignoDistintivo getPpdiSolicitudSignoDistintivoByTramite(String tramite) {
        PpdiSolicitudSignoDAO pd = new PpdiSolicitudSignoDAO(null);
        return pd.getPpdiSolicitudSignoDistintivoByTramite(tramite);
    }

    public boolean updatePpdiSolicitudSignoDistintivo(PpdiSolicitudSignoDistintivo signo) {
        PpdiSolicitudSignoDAO pd = new PpdiSolicitudSignoDAO(signo);
        try {
            pd.update();
            return true;
        } catch (Exception ex) {
            System.out.println("No se pudo editar el ppdi_solicitud_signo " + signo.getNumeroTramite() + ": " + ex);
            return false;
        }
    }

    public PpdiTituloSignoDistintivo getPpdiTituloSignoDistintivoByCodigoSolicitudSigno(int codigoSolitudSigno) {
        PpdiSolicitudSignoDAO pd = new PpdiSolicitudSignoDAO(null);
        return pd.getPpdiTituloSignoDistintivoByCodigoSolicitudSigno(codigoSolitudSigno);
    }
    
    public PpdiTituloSignoDistintivo getPpdiTituloSignoDistintivoByNumeroTitulo(String numeroTitulo) {
        PpdiSolicitudSignoDAO pd = new PpdiSolicitudSignoDAO(null);
        return pd.getPpdiTituloSignoDistintivoByNumeroTitulo(numeroTitulo);
    }
    
    public PpdiSolicitudSignoDistintivo getPpdiSolicitudSignoDistintivoByCodigoSolicitud(Integer codigoSolicitud){
        PpdiSolicitudSignoDAO pd = new PpdiSolicitudSignoDAO(null);
        return pd.getPpdiSolicitudSignoDistintivoByCodigoSolicitud(codigoSolicitud);
    }

    public List<Transferencia> getTransferencias(String titulo, boolean avisa) {
        TransferenciaDAO td = new TransferenciaDAO(null);
        return td.getTransferencias(titulo, avisa);
    }

    public List<CambioNombre> getCambiosNombreByTitulo(String titulo, boolean aviso) {
        TransferenciaDAO td = new TransferenciaDAO(null);
        return td.getCambioNombresByTitulo(titulo, aviso);
    }

    public List<CambioDomicilio> getCambiosDomicilioByTitulo(String titulo, boolean aviso) {
        TransferenciaDAO td = new TransferenciaDAO(null);
        return td.getCambioDomicilioByTitulo(titulo, aviso);
    }

    public List<PrendaComercial> getPrendaComercialByTitulo(String titulo, boolean aviso) {
        TransferenciaDAO td = new TransferenciaDAO(null);
        return td.getPrendaComercialByTitulo(titulo, aviso);
    }

    public List<LicenciaUso> getLicenciaUsosByTitulo(String titulo, boolean aviso) {
        TransferenciaDAO td = new TransferenciaDAO(null);
        return td.getLicenciaUsosByTitulo(titulo, aviso);
    }

    public List<SubLicenciaUso> getSubLicenciaUsosByTitulo(String titulo, boolean aviso) {
        TransferenciaDAO td = new TransferenciaDAO(null);
        return td.getSubLicenciaUsosByTitulo(titulo, aviso);
    }

    public List<TutelageForms> getTutelageFormsByExpedient(String expedient) {
        DAOConsultasForm td = new DAOConsultasForm();
        return td.getTutelageFormsByExpedient(expedient);
    }

    public TutelageForms getTutelageFormsByApplicationNumber(String applicationNumber) {
        DAOConsultasForm td = new DAOConsultasForm();
        return td.getTutelageFormsByApplicationNumber(applicationNumber);
    }

    public List<OppositionForms> getOppositionFormsBySearchedApplicationNumber(String searchedApplicationNumber) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getOppositionFormsBySearchedApplicationNumber(searchedApplicationNumber);
    }

    public List<OppositionForms> getOppositionFormsByApplicationNumber(String applicationNumber) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getOppositionFormsByApplicationNumber(applicationNumber);
    }

    public List<VoucherForm> getVoucherFormByAppOrDocNumber(String appordoc, boolean avisa) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getVoucherFormByAppOrDocNumber(appordoc, avisa);
    }

    public PlayForm getPlayFormByApplicationNumber(String applicationNumber) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getPlayFormByApplicationNumber(applicationNumber);
    }

    public PatentForms getPatentFormByApplicationNumber(String applicationNuber) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getPatentFormByApplicationNumber(applicationNuber);
    }

    public RenewalForm getRenewalFormByApplicationNumber(String applicationNumber) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getRenewalFormByApplicationNumber(applicationNumber);
    }

    public List<Renovacion> getRenovacionesByTitOrTramite(String tituloOrTramite, boolean avisa) {
        RenovacionDAO rd = new RenovacionDAO(null);
        return rd.getRenovacionesByTitOrTramite(tituloOrTramite, avisa);
    }

    public HallmarkFormDepurada getHallmarkDepuradaByDebugId(Integer debugId) {
        DAOConsultasDep dd = new DAOConsultasDep();
        return dd.getHallmarkDepuradaByDebugId(debugId);
    }

    public HallmarkFormDepurada getHallmarkDepuradaByExpedient(String expedient) {
        DAOConsultasDep dd = new DAOConsultasDep();
        return dd.getHallmarkDepuradaByExpedient(expedient);
    }

    public RenewalForm getRenewalFormByDebugId(Integer debugId) {
        DAOConsultasForm df = new DAOConsultasForm();
        return df.getRenewalFormByDebugId(debugId);
    }

    public List<Notifications> getNotificationsByTramite(String tramite) {
        DAOConsultasCasil dc = new DAOConsultasCasil();
        return dc.getNotificationsByTramite(tramite);
    }

    public List<Notifications> getNotificationsByTramiteAndCasillero(String tramite, int casillero) {
        DAOConsultasCasil dc = new DAOConsultasCasil();
        return dc.getNotificationsByTramiteAndCasillero(tramite, casillero);
    }

    public List<String> getTramitesNotificationsByCasillero(int casillero) {
        DAOConsultasCasil dc = new DAOConsultasCasil();
        return dc.getTramitesNotificationsByCasillero(casillero);
    }

    public String getPatentNombreArchivo(String archivo) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getPatentNombreArchivo(archivo);
    }

    public String getScopeNombreArchivo(String archivo) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getScopeNombreArchivo(archivo);
    }

    public String getOppositionNombreArchivo(String archivo) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getOppositionNombreArchivo(archivo);
    }

    public String getPlayNombreArchivo(String archivo) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getPlayNombreArchivo(archivo);
    }

    public String getBreederNombreArchivo(String archivo) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getBreederNombreArchivo(archivo);
    }

    public String getHallmarkNombreArchivo(String archivo) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getHallmarkNombreArchivo(archivo);
    }

    public String getTutelageNombreArchivo(String archivo) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getTutelageNombreArchivo(archivo);
    }

    public String getDenominationNombreArchivo(String archivo) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getDenominationNombreArchivo(archivo);
    }

    public DenominationForms getDenominationFormsByApplicationNumber(String applicationNumber) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getDenominationForm(applicationNumber);
    }

    public FileAnnexesApplication getFileAnnexesApplication(String applicationNumber, String fileName, String applicationType) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getFileAnnexesApplication(applicationNumber, fileName, applicationType);
    }

    public FileAnnex getDenominationFile(String archivo) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getDenominationFile(archivo);
    }

    public boolean saveFileAnnexeApplication(FileAnnexesApplication fap) {
        DAOInsertDocuments did = new DAOInsertDocuments();
        return did.saveFileAnnexeApplication(fap);
    }

    public String getTutelageSampleNombreArchivo(String archivo) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getTutelageSampleNombreArchivo(archivo);
    }

    public String getRenewalNombreArchivo(String archivo) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.getRenewalNombreArchivo(archivo);
    }

    public Owner getOwnerById(int ownerId) {
        DAOConsultasCasil dc = new DAOConsultasCasil();
        return dc.getOwnerById(ownerId);
    }

    public Owner getOwnerByLockerId(int lockerId) {
        DAOConsultasCasil dc = new DAOConsultasCasil();
        return dc.getOwnerByLockerId(lockerId);
    }

    public List<Owner> getOwnersByCriteria(String criteria) {
        DAOConsultasCasil od = new DAOConsultasCasil();
        return od.getOwnersByCriteria(criteria);
    }

    public List<ConfiguracionCC> getConfiguracionesCC() {
        ConfiguracionCCDAO cd = new ConfiguracionCCDAO(null);
        return cd.buscarTodos();
    }

    public boolean updateConfiguracionCC(ConfiguracionCC config) {
        ConfiguracionCCDAO cd = new ConfiguracionCCDAO(config);
        try {
            cd.update();
            return true;
        } catch (Exception ex) {
            System.err.println("Hubo un problema al editar la configuracioncc: " + ex);
            return false;
        }
    }

    public boolean saveConfiguracionCC(ConfiguracionCC config) {
        ConfiguracionCCDAO cd = new ConfiguracionCCDAO(config);
        try {
            cd.persist();
            return true;
        } catch (Exception ex) {
            System.err.println("Hubo un problema al intentar guardar la configuracioncc: " + ex);
            return false;
        }
    }

    public boolean removeConfiguracionCC(ConfiguracionCC config) {
        ConfiguracionCCDAO cd = new ConfiguracionCCDAO(config);
        try {
            if (!cd.getEntityManager().contains(config)) {
                System.out.println("merge configuracioncc");
                config = cd.getEntityManager().merge(config);
                cd = new ConfiguracionCCDAO(config);
            }
            cd.remove();
            return true;
        } catch (Exception ex) {
            System.err.println("Hubo un problema al intentar elminar la configuracioncc: " + ex);
            return false;
        }
    }

    public List<ConfiguracionCC> getConfiguracionByTipo(String tipo) {
        ConfiguracionCCDAO cd = new ConfiguracionCCDAO(null);
        return cd.getConfiguracionByTipo(tipo);
    }

    public boolean existsUsuarioActivo(String usuario, boolean activo) {
        UsuarioCasilleroDAO ud = new UsuarioCasilleroDAO(null);
        return ud.existsUsuarioActivo(usuario, activo);
    }

    public Usuario getUsuarioActivo(String usuario, boolean activo) {
        UsuarioCasilleroDAO ud = new UsuarioCasilleroDAO(null);
        return ud.getUsuarioActivo(usuario, activo);
    }

    public String getConfiguracionesActivas() {
        ConfiguracionCCDAO cd = new ConfiguracionCCDAO(null);
        return cd.getConfiguracionesActivas();
    }

    public boolean saveCambioCasillero(CambioCasillero cc) {
        CambioCasilleroDAO cd = new CambioCasilleroDAO(cc);
        try {
            cd.persist();
            return true;
        } catch (Exception ex) {
            System.err.println("Error al guardar cambio de casillero");
            return false;
        }
    }

    public boolean updateCambioCasillero(CambioCasillero cc) {
        CambioCasilleroDAO cd = new CambioCasilleroDAO(cc);
        try {
            cd.update();
            return true;
        } catch (Exception ex) {
            System.out.println("Error al actualizar el cambio de casillero: " + cc.getId());
            return false;
        }
    }

    public CambioCasillero getCambioCasilleroWhenNotId(CambioCasillero c) {
        CambioCasilleroDAO cd = new CambioCasilleroDAO(null);
        return cd.getCambioCasilleroWhenNotId(c);
    }

    public String nextProvidenciaCambioCasillero() {
        CambioCasilleroDAO cd = new CambioCasilleroDAO(null);
        return cd.nextProvidencia();
    }

    public List<ConfiguracionCC> getConfiguracionesCCActivas() {
        ConfiguracionCCDAO cd = new ConfiguracionCCDAO(null);
        return cd.getConfiguracionesCCActivas();
    }

    public List<CambioCasillero> getCambiosCasilleroSignos() {
        CambioCasilleroDAO cd = new CambioCasilleroDAO(null);
        return cd.getCambiosCasilleroSignos();
    }

    public boolean guardarNotifications(Notifications notificacion) {
        DAOConsultasCasil dc = new DAOConsultasCasil();
        return dc.saveNotifications(notificacion);
    }

    public Notifications getNotificationsByMatterAndCreateDt(String matter, String createDt) {
        DAOConsultasCasil dc = new DAOConsultasCasil();
        return dc.getNotificationsByMatterAndCreateDt(matter, createDt);
    }

    public boolean saveLockerNotifications(LockerNotifications ln) {
        DAOConsultasCasil dc = new DAOConsultasCasil();
        return dc.saveLockerNotifications(ln);
    }

    public boolean updateOwnerHallmark(HallmarkForm hallmark) {
        DAOConsultasForm dc = new DAOConsultasForm();
        return dc.updateOwnerHallmark(hallmark);
    }

    public boolean updateUsuario(Usuario usuario) {
        UsuarioCasilleroDAO ud = new UsuarioCasilleroDAO(usuario);
        try {
            ud.update();
            return true;
        } catch (Exception ex) {
            System.out.println("Error al actualizar usuario: " + ex);
            return false;
        }
    }

    public boolean removeUsuario(Usuario usuario) {
        UsuarioCasilleroDAO ud = new UsuarioCasilleroDAO(usuario);
        try {
            if (!ud.getEntityManager().contains(usuario)) {
                System.out.println("merge usuario casillero");
                usuario = ud.getEntityManager().merge(usuario);
                ud = new UsuarioCasilleroDAO(usuario);
            }
            ud.remove();
            return true;
        } catch (Exception ex) {
            System.err.println("Error al remover usuario casillero: " + ex);
            return false;
        }
    }

    public boolean saveUsuario(Usuario usuario) {
        UsuarioCasilleroDAO cd = new UsuarioCasilleroDAO(usuario);
        try {
            cd.persist();
            return true;
        } catch (Exception ex) {
            System.out.println("Error al guardar usuario casillero: " + ex);
            return false;
        }
    }

    public List<Usuario> getUsuariosCambioCasillero() {
        UsuarioCasilleroDAO ud = new UsuarioCasilleroDAO(null);
        return ud.buscarTodos();
    }

    public int depurarNotificacionesPorTramiteAndCasillero(String tramite, int casillero, String eti) {
        List<Notifications> notificaciones = getNotificationsByTramiteAndCasillero(tramite, casillero);

        int flag;

        List<String> documentos = new ArrayList<>();
        List<String> valen = new ArrayList<>();
        List<String> repetidos = new ArrayList<>();
        System.out.println("*************" + eti + " Notificaciones " + tramite + " - " + casillero + "************");
        List<Integer> lockerIds = new ArrayList<>();
        for (int i = 0; i < notificaciones.size(); i++) {
            Notifications notificacion = notificaciones.get(i);
            if (notificacion.getDocument().contains("_")) {
                System.out.print(notificacion.getLoId() + ": " + notificacion.getDocument());
                String docu = notificacion.getDocument().substring(0, notificacion.getDocument().lastIndexOf("_"));
                System.out.println(": " + docu);
                if (!documentos.contains(docu)) {
                    documentos.add(docu);
                    valen.add(notificacion.getDocument());
                } else {
                    lockerIds.add(notificacion.getLoId());
                    repetidos.add(notificacion.getDocument());
                }
            } else {
                System.out.println("No tomado en cuenta: " + notificacion.getDocument());
            }
        }

        System.out.println("docus: " + valen.toString());
        System.out.println("repetidos: " + repetidos.toString());
        System.out.println("ids: " + lockerIds.toString());
        FTPFiles files = new FTPFiles();

        if (lockerIds.size() == repetidos.size()) {
            flag = -1;
            for (int i = 0; i < repetidos.size(); i++) {
                String rutanot = "/var/www/html/casilleros/media/files/" + notificaciones.get(0).getLocker() + "/" + repetidos.get(i);
                System.out.println("borrar: " + (i + 1) + ": " + rutanot);
                String comando = "rm -R " + rutanot + " && echo 'borrado'";
                if (files.exeComando(comando)) {
                    DAOConsultasCasil dcc = new DAOConsultasCasil();
                    dcc.removeLockerNotification(lockerIds.get(i));
                } else {
                    System.out.println("no entró locker_notification_id: " + lockerIds.get(i));
                }
                flag = 1;
            }
        } else {
            flag = 2;
            System.out.println("No son iguales los ids y los docs repetidos, revisa");
        }
        return flag;
    }

    public void depurarNotificacionesPorCasillero(int casillero) {
        List<String> tramites = getTramitesNotificationsByCasillero(casillero);
        int tam = tramites.size();
        for (int i = 0; i < tramites.size(); i++) {
            String tramite = tramites.get(i);
            depurarNotificacionesPorTramiteAndCasillero(tramite, casillero, "(" + (i + 1) + "/" + tam + ")");
        }
    }

    public boolean deleteDenominationDocument(Integer denominationFormId, Integer denominationAnnexId, String file) {
        DAOInsertDocuments did = new DAOInsertDocuments();
        return did.deleteDenominationDocument(denominationFormId, denominationAnnexId, file);
    }

//    public boolean deleteFileAnnexeApplication(String applicationNumber, String fileName, String applicationType) {
//        DAOInsertDocuments did = new DAOInsertDocuments();
//        return did.deleteFileAnnexeApplication(applicationNumber, fileName, applicationType);
//    }
    public boolean softDeleteFileAnnexeApplication(String applicationNumber, String fileName, String applicationType, String userUpdate, Timestamp updateDate) {
        DAOInsertDocuments did = new DAOInsertDocuments();
        return did.softDeleteFileAnnexeApplication(applicationNumber, fileName, applicationType, userUpdate, updateDate);
    }

//    public boolean softDeleteFileAnnexeApplication(String applicationNumber, String fileName, String applicationType, String userUpdate, Timestamp updateDate) {
//        DAOInsertDocuments did = new DAOInsertDocuments();
//        return did.softDeleteFileAnnexeApplication(applicationNumber, fileName, applicationType, userUpdate, updateDate);
//    }
    public IpasEnvironment getIpasEnvironmentActive(boolean active, String tipo) {
        IpasEnvironmentDAO idao = new IpasEnvironmentDAO(null);
        return idao.getIpasEnvironmentActive(active, tipo);
    }

    public IpasApi getIpasApi(IpasApiClient ipasApiClient) {
        Controlador c = new Controlador();
        //Obtengo la fecha actual en timestamp
        String date = Operaciones.getCurrentTimeStamp();

        //Valido si los token de credenciales existen y están vigentes
        IpasApi ipasApi = c.getIpasApiByDate(date);
        String cognitoAccessToken;
        //si están vigentes los obtengo

        if (ipasApi.getId() != null) {
            System.out.println("--- Si encontró el ipas api con los tokens ---");
            cognitoAccessToken = ipasApi.getCognitoAccessToken();
            System.out.println("Entramos 1: cognito-access-token si: " + cognitoAccessToken.substring(0, 15) + "...");
        } else {
            // si no están los mandamos a crear
            cognitoAccessToken = ipasApiClient.getCognitoAccessToken();
            System.out.println("Entramos 1: cognito-access-token: " + cognitoAccessToken.substring(0, 15) + "...");
            ipasApi = new IpasApi();
        }

        String xAccessToken;

        if (ipasApi.getId() != null) {
            xAccessToken = ipasApi.getxAccessToken();
            System.out.println("Entramos 2: x-access-token si: " + xAccessToken.substring(0, 15) + "...");
        } else {
//            System.out.println("cognitoAccesstoken: "+cognitoAccessToken);
            if (cognitoAccessToken.contains("Hubo un problema al obtener")) {
                return ipasApi;
            } else {
                xAccessToken = ipasApiClient.getXAccessToken(cognitoAccessToken);
                if (!xAccessToken.contains("503 Service Temporarily Unavailable")) {
                    System.out.println("Entramos 2: x-access-token: " + xAccessToken.substring(0, 15) + "...");
                    //En caso de que no se encuentre ipas api o las fechas ya no soporten, se crea  un nuevo registro en bd
                    ipasApi.setCognitoAccessToken(cognitoAccessToken);
                    ipasApi.setxAccessToken(xAccessToken);
                    ipasApi.setFechaInicio(Timestamp.valueOf(Operaciones.getCurrentTimeStamp()));
                    Timestamp timfin = Timestamp.valueOf(Operaciones.getCurrenttimeStampPlusSeconds(ipasApi.getFechaInicio().toString().substring(0, ipasApi.getFechaInicio().toString().length() - 2), 3598));
                    ipasApi.setFechaFin(timfin);

                    Controller con = new Controller();
                    con.deleteAllIpasApi();
                    if (con.saveIpasApi(ipasApi)) {
                        System.out.println("Se guardó el ipas api con los tokens y fechas");
                    }
                } else {
                    System.out.println("El sistema no está en línea 2");
                    return new IpasApi();
                }
            }
        }
        return ipasApi;
    }

    public IpasApi getIpasApiByDate(String date) {
        IpasApiDAO id = new IpasApiDAO(null);
        return id.getIpasApiByDate(date);
    }

    public PatentForms mapFromJson(PatentJson json) {

        PatentForms patentForm = new PatentForms();

        if (json == null) {
            return patentForm;
        }

        // 🔹 Número trámite
        if (json.getApplicationNumber() != null) {
            patentForm.setApplicationNumber(
                    json.getApplicationNumber().getApplicationNumberText()
            );
        }

        // 🔹 Fecha
        patentForm.setApplicationDate(json.getAppplicationDate());

        // 🔹 Tipo
        patentForm.setTipo(json.getTransactionSubCode());

        // 🔹 Estado
        patentForm.setStatus(json.getCurrentStatusCode());

        // 🔹 Expediente
        patentForm.setExpedient(json.getApplicantFileReference());

        // 🔥 DETECTAR TIPO
        boolean isDesign = "INDUSTRIAL_DESIGN".equalsIgnoreCase(json.getIpCategory());

        // =========================================================
        // 🟢 PATENTES
        // =========================================================
        if (!isDesign) {

            // 🔹 Título
            if (json.getTitleBag() != null && !json.getTitleBag().isEmpty()) {
                patentForm.setTitle(json.getTitleBag().get(0).getText());
            }

            // 🔹 Resumen
            if (json.getAbstracts() != null && !json.getAbstracts().isEmpty()) {

                PatentJson.AbstractData abs = json.getAbstracts().get(0);

                if (abs.getpBag() != null && !abs.getpBag().isEmpty()) {

                    StringBuilder resumen = new StringBuilder();

                    for (PatentJson.PBag p : abs.getpBag()) {
                        if (p.getText() != null) {
                            resumen.append(p.getText()).append("\n");
                        }
                    }

                    patentForm.setSummary(resumen.toString().trim());
                }
            }

            // 🔹 Claims
            patentForm.setClaims(json.getClaimTotalQuantity());
        } // =========================================================
        // 🔵 DISEÑOS INDUSTRIALES
        // =========================================================
        else {

            // 🔹 TÍTULO → designStatementBag
            if (json.getDesignStatementBag() != null && !json.getDesignStatementBag().isEmpty()) {
                patentForm.setTitle(
                        json.getDesignStatementBag().get(0).getText()
                );
            }

            // 🔹 DESCRIPCIÓN → summary
            if (json.getDesignBag() != null && !json.getDesignBag().isEmpty()) {

                PatentJson.Design design = json.getDesignBag().get(0);

                if (design.getDescriptionBag() != null && !design.getDescriptionBag().isEmpty()) {

                    StringBuilder desc = new StringBuilder();

                    for (PatentJson.Description d : design.getDescriptionBag()) {

                        if (d.getText() != null && !d.getText().contains("<DATA>")) {
                            desc.append(d.getText()).append("\n");
                        }
                    }

                    patentForm.setSummary(desc.toString().trim());
                }
            }

            // 🔹 Claims no aplica
            patentForm.setClaims(0);
        }

        // =========================================================
        // 🔹 CASILLERO (común)
        // =========================================================
        if (json.getCustomFields() != null
                && json.getCustomFields().getDataNumericBag() != null) {

            for (PatentJson.DataNumeric d : json.getCustomFields().getDataNumericBag()) {
                if ("Casillero virtual".equalsIgnoreCase(d.getId())) {
                    patentForm.setCasillero(
                            d.getValue() != null ? String.valueOf(d.getValue()) : ""
                    );
                }
            }
        }

        // 🔹 Default
        patentForm.setInternationalClassification("");
        patentForm.setSitio("");
        patentForm.setFtp("");
        patentForm.setImage("");

        return patentForm;
    }

    public List<Cpis> getCpisByApplicationNumber(String applicationNumber) {
        DAOConsultasAdmin da = new DAOConsultasAdmin();
        return da.getCpis(applicationNumber);
    }
}
