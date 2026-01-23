/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.util;

import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import senadi.com.ditramites.bean.LoginBean;
import senadi.com.ditramites.dao.DAOConsultasCasil;
import senadi.com.ditramites.dao.DAOConsultasDep;
import senadi.com.ditramites.dao.DAOConsultasForm;
import senadi.com.ditramites.dao.mod.CambioCasilleroDAO;
import senadi.com.ditramites.dao.mod.ConfiguracionCCDAO;
import senadi.com.ditramites.dao.mod.TransferenciaDAO;
import senadi.com.ditramites.dao.mod.UsuarioCasilleroDAO;
import senadi.com.ditramites.dao.procesos.PpdiSolicitudSignoDAO;
import senadi.com.ditramites.dao.ren.RenovacionDAO;
import senadi.com.ditramites.model.BreederForms;
import senadi.com.ditramites.model.HallmarkForm;
import senadi.com.ditramites.model.HallmarkFormDepurada;
import senadi.com.ditramites.model.LockerNotifications;
import senadi.com.ditramites.model.Notifications;
import senadi.com.ditramites.model.OppositionForms;
import senadi.com.ditramites.model.Owner;
import senadi.com.ditramites.model.PatentForms;
import senadi.com.ditramites.model.PlayForm;
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
    
    public String getApplicationType(String applicationNumber){
        DAOConsultasForm df = new DAOConsultasForm();
        return df.getApplicationType(applicationNumber);
    }
    
    public Integer getVegetableFormsId(String applicationNumber){
        DAOConsultasForm df = new DAOConsultasForm();
        return df.getVegetableFormsId(applicationNumber);
    }

    public HallmarkForm getHallmarkForm(String applicationNumber) {
        DAOConsultasForm hd = new DAOConsultasForm();
        return hd.getHallmarkForm(applicationNumber);
    }
    
    public BreederForms getBreederForm(String applicationNumber){
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
    
    public String getBreederNombreArchivo(String archivo){
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
                }else{
                    System.out.println("no entró locker_notification_id: "+lockerIds.get(i));
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
}
