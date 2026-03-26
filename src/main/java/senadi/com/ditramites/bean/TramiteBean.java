/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.PrimeFaces;
import org.primefaces.component.api.UIData;
import org.primefaces.event.RowEditEvent;
import senadi.com.ditramites.model.BreederForms;
import senadi.com.ditramites.model.Documento;
import senadi.com.ditramites.model.HallmarkForm;
import senadi.com.ditramites.model.HallmarkFormDepurada;
import senadi.com.ditramites.model.Notifications;
import senadi.com.ditramites.model.OppositionForms;
import senadi.com.ditramites.model.Owner;
import senadi.com.ditramites.model.PatentForms;
import senadi.com.ditramites.model.PlayForm;
import senadi.com.ditramites.model.RenewalForm;
import senadi.com.ditramites.model.ScopeForms;
import senadi.com.ditramites.model.Tramite;
import senadi.com.ditramites.model.TutelageForms;
import senadi.com.ditramites.model.VoucherForm;
import senadi.com.ditramites.model.form.ModificacionAux;
import senadi.com.ditramites.model.form.NolineaAux;
import senadi.com.ditramites.model.form.NotificationsAux;
import senadi.com.ditramites.model.form.OppositionFormsAux;
import senadi.com.ditramites.model.form.PlayFormsAux;
import senadi.com.ditramites.model.form.ScopeFormsAux;
import senadi.com.ditramites.model.form.TutelageFormsAux;
import senadi.com.ditramites.model.mod.CambioCasillero;
import senadi.com.ditramites.model.mod.CambioDomicilio;
import senadi.com.ditramites.model.mod.CambioNombre;
import senadi.com.ditramites.model.mod.ConfiguracionCC;
import senadi.com.ditramites.model.mod.LicenciaUso;
import senadi.com.ditramites.model.mod.PrendaComercial;
import senadi.com.ditramites.model.modren.Renovacion;
import senadi.com.ditramites.model.mod.SubLicenciaUso;
import senadi.com.ditramites.model.mod.Transferencia;
import senadi.com.ditramites.model.mod.Usuario;
import senadi.com.ditramites.model.postgres.PpdiSolicitudSignoDistintivo;
import senadi.com.ditramites.model.postgres.PpdiTituloSignoDistintivo;
import senadi.com.ditramites.util.Controlador;
import senadi.com.ditramites.util.FTPFiles;
import senadi.com.ditramites.util.Operaciones;
import senadi.com.ditramites.util.TramiteUtil;

/**
 *
 * @author micharesp
 */
@ManagedBean(name = "tramiteBean")
@ViewScoped
public class TramiteBean implements Serializable {

    private boolean depurar;

    private String casilleroText;
    private String tramiteText;
    private String tituloTramiteText;
    private String tipoTramite;

    private List<String> documentosExpediente;
    private List<Documento> documentos;

    private HallmarkForm hallmarkForm;
    private TutelageForms tutelageForm;
    private TutelageFormsAux tutelaAux;
    private PlayForm playForm;
    private PlayFormsAux playFormAux;
    private PatentForms patentForm;
    private BreederForms breederForm;

    private List<ScopeFormsAux> alcances;

    private List<ModificacionAux> modificaciones;

    private List<OppositionFormsAux> oposiciones;

    private List<NolineaAux> nolineas;

    private List<NotificationsAux> notificaciones;

    private boolean conmarca;
    private boolean conalcance;
    private boolean conmodificacion;
    private boolean contutela;
    private boolean conoposicion;
    private boolean connolinea;
    private boolean conderechos;
    private boolean conpatente;
    private boolean connotificacion;
    private boolean conbreeder;

    private boolean resultadopanel;
    private boolean hijas;

    private LoginBean login;

    private Owner owner;
    private String dialogCasillero;

    private boolean cambioCasillero;
    private Tramite tramite;
    private CambioCasillero cambiarCasillero;
    private List<Owner> owners;

    private List<Owner> ownersFiltrados;
    private UIData ownerDataTable;
    private Owner owneraux;

    private String criterioOwner;
    private String confirmChangeMessage;

    private List<ConfiguracionCC> configuraciones;
    private UIData configDataTable;

    private List<String> tiposConfiguracionCC;

    private String notaprovidencia;

    private boolean cambiocas;

    private Usuario usuarioCambioCasillero;

    private PpdiSolicitudSignoDistintivo ppdiSolicitudSignoDistintivo;

    public TramiteBean() {
        Controlador c = new Controlador();
        login = c.getLogin();
        owner = new Owner();
        cambioCasillero = false;
        cambiocas = false;
    }

    public void buscarTramite(ActionEvent ae) {
        FacesMessage msg = null;
        System.out.println("--------- usuario: " + login.getNombre() + " ------- tramite: " + tramiteText + " -------------");
        if (tramiteText.trim().isEmpty() && tramiteText.trim().length() < 4) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "INGRESE UN NÚMERO DE TRÁMITE CORRECTO");
        } else {
            limpiarDatos(false);
            tramiteText = tramiteText.trim().replace(" ", "").toUpperCase();

            Controlador c = new Controlador();

            String tipoTram = c.getApplicationType(tramiteText);
            boolean flag = true;
            if (!tipoTram.trim().isEmpty()) {
                System.out.println("**** TIPO TRÁMITE: " + tipoTram + " ****");
                System.out.println("Inicio lectura de trámite: " + Operaciones.getCurrentTimeStamp());
                switch (tipoTram) {
                    case "BreederForm":
                        if (cargarBreederAVisualizarByTramite(tramiteText)) {
                            System.out.println("OBTENCIONES VEGETALES");
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ LA OBTENCIÓN VEGETAL");
                            resultadopanel = true;
                        }
                        break;
                    // No existe este trámite aún
                    case "Denominationform":
                        break;
                    case "Hallmarkform":
                        if (cargarMarcasAVisualizarByTramite(tramiteText)) {
                            System.out.println("SIGNO DISTINTIVO");
                            ppdiSolicitudSignoDistintivo = c.getPpdiSolicitudSignoDistintivoByTramite(tramiteText);
                            cargarAlcancesAVisualizarByAfforAplicationNumber(hallmarkForm.getApplicationNumber(), false);
                            System.out.println(hallmarkForm.getTitulo());
                            cargarModificacionesAVisualizarByTituloOrTramite(hallmarkForm.getTitulo(), false);
                            cargarOposicionesAVisualizarByTramite(hallmarkForm.getApplicationNumber(), true);
                            cargarNolineaAVisualizarByAppOrDoc(tramiteText, false);
                            cargarNotificacionesAVisualizarByTramite(tramiteText);
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ EL SIGNO DISTINTIVO");
                            resultadopanel = true;
                        }
                        break;
                    case "Oppositionform":
                        if (cargarOposicionesAVisualizarByTramite(tramiteText, false)) {
                            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
                            hijas = true;
                            System.out.println("OPOSICIÓN");
                            cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, false);
                            cargarNotificacionesAVisualizarByTramite(tramiteText);
                            tituloTramiteText = "OPOSICIÓN";
                            resultadopanel = true;
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ LA OPOSICIÓN");
                        }
                        break;
                    case "Patentform":
                        if (cargarPatentFormAVisualizarByTramite(tramiteText)) {
                            System.out.println("------> Patente cargada <------");
                            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
                            cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, false);
                            cargarModificacionesAVisualizarByTituloOrTramite(tramiteText, true);
                            cargarOposicionesAVisualizarByTramite(tramiteText, true);
                            cargarNolineaAVisualizarByAppOrDoc(tramiteText, false);
                            cargarNotificacionesAVisualizarByTramite(tramiteText);
                            resultadopanel = true;
                            System.out.println("Fin Trámite Patente: " + Operaciones.getCurrentTimeStamp());
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ LA PATENTE");
                        }
                        break;
                    case "Playform":
                        if (cargarPlayFormAVisualizarByTramite(tramiteText)) {
                            System.out.println("*********** ENTRO EN DERECHOS DE AUTOR ****************");
                            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
                            hijas = true;
                            System.out.println("DERECHO DE AUTOR");
                            cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, false);
                            cargarNotificacionesAVisualizarByTramite(tramiteText);
                            tituloTramiteText = "DERECHOS DE AUTOR";
                            resultadopanel = true;
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ EL DERECHO DE AUTOR");
                        }
                        break;
                    case "Renewalform":
                    case "renewal_forms":
                        if (cargarModificacionesAVisualizarByTituloOrTramite(tramiteText, true)) {
                            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
                            hijas = true;
                            System.out.println("MODIFICACIÓN AL REGISTRO");
                            cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, false);
                            cargarNotificacionesAVisualizarByTramite(tramiteText);
                            tituloTramiteText = "MODIFICACIÓN AL REGISTRO";
                            resultadopanel = true;
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ LA MODIFICACIÓN");
                        }
                        break;
                    case "Scopeform":
                        if (cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, true)) {
                            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
                            hijas = true;
                            System.out.println("ALCANCE");
                            cargarNotificacionesAVisualizarByTramite(tramiteText);
                            tituloTramiteText = "ALCANCE";
                            resultadopanel = true;
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ EL ALCANCE");
                        }
                        break;
                    case "Tutelageform":
                        if (cargarTutelasAVisualizarByTramite(tramiteText)) {
                            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
//                            hijas = true;
                            System.out.println("TUTELAS");
                            cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, false);
                            cargarNotificacionesAVisualizarByTramite(tramiteText);
                            resultadopanel = true;
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ LA TUTELA ADMINISTRATIVA");
                        }
                        break;
                    case "Voucher":
                        if (cargarNolineaAVisualizarByAppOrDoc(tramiteText, true)) {
                            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
                            hijas = true;
                            System.out.println("TRÁMITE NO EN LÍNEA");
                            cargarNotificacionesAVisualizarByTramite(tramiteText);
                            tituloTramiteText = "TRÁMITE NO EN LÍNEA";
                            resultadopanel = true;
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ EL TRÁMITE NO EN LÍNEA");
                        }
                        break;
                    default:
                        flag = false;
                        break;
                }
            } else {
                flag = false;
            }
            if (!flag) {
//                System.out.println("entro aquí <----------------------------");
//                loadApplication(msg);
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE ENCONTRÓ EL TRÁMITE " + tipoTram);
            }
            System.out.println("Fin lectura trámite: " + Operaciones.getCurrentTimeStamp());
            System.out.println("");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

//    public void loadApplication(FacesMessage msg) {
//        if (cargarMarcasAVisualizarByTramite(tramiteText)) {
//            System.out.println("SIGNO DISTINTIVO");
//            Controlador c = new Controlador();
//            ppdiSolicitudSignoDistintivo = c.getPpdiSolicitudSignoDistintivoByTramite(tramiteText);
//            cargarAlcancesAVisualizarByAfforAplicationNumber(hallmarkForm.getApplicationNumber(), false);
//            System.out.println(hallmarkForm.getTitulo());
//            cargarModificacionesAVisualizarByTituloOrTramite(hallmarkForm.getTitulo(), false);
//            cargarOposicionesAVisualizarByTramite(hallmarkForm.getApplicationNumber(), true);
//            cargarNolineaAVisualizarByAppOrDoc(tramiteText, false);
//            cargarNotificacionesAVisualizarByTramite(tramiteText);
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ EL SIGNO DISTINTIVO");
//            resultadopanel = true;
//        } else if (cargarPatentFormAVisualizarByTramite(tramiteText)) {
//            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
//            System.out.println("PATENTE");
//            cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, false);
//            cargarModificacionesAVisualizarByTituloOrTramite(tramiteText, true);
//            cargarOposicionesAVisualizarByTramite(tramiteText, true);
//            cargarNolineaAVisualizarByAppOrDoc(tramiteText, false);
//            cargarNotificacionesAVisualizarByTramite(tramiteText);
//            resultadopanel = true;
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ LA PATENTE");
//        } else if (cargarTutelasAVisualizarByTramite(tramiteText)) {
//            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
//            hijas = true;
//            System.out.println("TUTELAS");
//            cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, false);
//            cargarNotificacionesAVisualizarByTramite(tramiteText);
//            resultadopanel = true;
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ LA TUTELA ADMINISTRATIVA");
//        } else if (cargarOposicionesAVisualizarByTramite(tramiteText, false)) {
//            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
//            hijas = true;
//            System.out.println("OPOSICIÓN");
//            cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, false);
//            cargarNotificacionesAVisualizarByTramite(tramiteText);
//            tituloTramiteText = "OPOSICIÓN";
//            resultadopanel = true;
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ LA OPOSICIÓN");
//        } else if (cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, true)) {
//            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
//            hijas = true;
//            System.out.println("ALCANCE");
//            cargarNotificacionesAVisualizarByTramite(tramiteText);
//            tituloTramiteText = "ALCANCE";
//            resultadopanel = true;
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ EL ALCANCE");
//        } else if (cargarModificacionesAVisualizarByTituloOrTramite(tramiteText, true)) {
//            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
//            hijas = true;
//            System.out.println("MODIFICACIÓN AL REGISTRO");
//            cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, false);
//            cargarNotificacionesAVisualizarByTramite(tramiteText);
//            tituloTramiteText = "MODIFICACIÓN AL REGISTRO";
//            resultadopanel = true;
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ LA MODIFICACIÓN");
//        } else if (cargarPlayFormAVisualizarByTramite(tramiteText)) {
//            System.out.println("*********** ENTRO EN DERECHOS DE AUTOR ****************");
//            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
//            hijas = true;
//            System.out.println("DERECHO DE AUTOR");
//            cargarAlcancesAVisualizarByAfforAplicationNumber(tramiteText, false);
//            cargarNotificacionesAVisualizarByTramite(tramiteText);
//            tituloTramiteText = "DERECHOS DE AUTOR";
//            resultadopanel = true;
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ EL DERECHO DE AUTOR");
//        } else if (cargarNolineaAVisualizarByAppOrDoc(tramiteText, true)) {
//            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
//            hijas = true;
//            System.out.println("TRÁMITE NO EN LÍNEA");
//            cargarNotificacionesAVisualizarByTramite(tramiteText);
//            tituloTramiteText = "TRÁMITE NO EN LÍNEA";
//            resultadopanel = true;
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE ENCONTRÓ EL TRÁMITE NO EN LÍNEA");
//        } else {
//            ppdiSolicitudSignoDistintivo = new PpdiSolicitudSignoDistintivo();
//            resultadopanel = false;
//            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "NO SE ENCONTRARON RESULTADOS");
//        }
//    }
    public boolean cargarPatentFormAVisualizarByTramite(String applicationNumber) {
        Controlador c = new Controlador();
        patentForm = c.getPatentFormByApplicationNumber(applicationNumber);

        if (patentForm.getId() != null) {
            System.out.println("titulo: " + patentForm.getTitle());
            tituloTramiteText = "PATENTE";
            conpatente = true;

            Integer pago = patentForm.getPaymentReceitpId();
            if (pago != null && pago != 0) {
                if (patentForm.getStatus().equals("DELIVERED")) {
                    patentForm.setStatus("PAGADO E INICIADO EL PROCESO");
                } else {
                    patentForm.setStatus("PAGADO PERO NO TIENE EL PROCESO INICIADO");
                }
            } else {
                patentForm.setStatus("SIN PAGO");
            }
            FTPFiles files = new FTPFiles();
            String rutaExpediente = patentForm.getFtp() + patentForm.getId();
            documentosExpediente = files.listarDirectorio(rutaExpediente);
            documentos = new ArrayList<>();
            for (int i = 0; i < documentosExpediente.size(); i++) {
                Documento doc = new Documento();
                doc.setArchivo(documentosExpediente.get(i));
                String nombre = c.getPatentNombreArchivo(doc.getArchivo());
                if (!nombre.isEmpty()) {
//                    System.out.println("docu " + (i + 1) + ": " + nombre);
                    doc.setTipo(nombre);

                } else {
                    if (doc.getArchivo().contains("pdf_patentfrm_")) {
                        doc.setTipo("Formulario");
                    } else if (doc.getArchivo().contains("pdf_voucher_patentfrm_")) {
                        doc.setTipo("Comprobante");
                    } else {
                        doc.setTipo(doc.getArchivo());
                    }
                    if (patentForm.getImage() != null && !patentForm.getImage().trim().isEmpty()) {
                        if (doc.getArchivo().equals(patentForm.getImage())) {
                            doc.setTipo("ResumenImagen");
                        }
                    }
                }
                documentos.add(doc);
            }

            conpatente = true;
        } else {
            conpatente = false;
        }
        return conpatente;
    }

    public void prepararVerProductos(ActionEvent ae) {
        FacesMessage msg = null;

        if (hallmarkForm != null && hallmarkForm.getId() != null) {
            if (ppdiSolicitudSignoDistintivo != null && ppdiSolicitudSignoDistintivo.getCodigoSolicitudSigno() != null) {
//            System.out.println("sizeeeeeeeeE: "+ppdiSolicitudSignoDistintivo.getPpdiSolicitudOrdenesNiza().size());
                PrimeFaces.current().ajax().addCallbackParam("verprods", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", hallmarkForm.getApplicationNumber() + " CARGADO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL CARGAR LOS PRODUCTOS");
            }

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL CARGAR LOS PRODUCTOS");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean cargarBreederAVisualizarByTramite(String applicationNumber) {
        Controlador c = new Controlador();
        breederForm = c.getBreederForm(applicationNumber);

        if (breederForm.getId() != null) {
            System.out.println("taxon: " + breederForm.getProposedName());
            tituloTramiteText = "OBTENCIONES VEGETALES";
            conbreeder = true;

            int id = c.getVegetableFormsId(applicationNumber);
            if (id != 0) {
                breederForm.setIdVegetableForms(id);
                FTPFiles files = new FTPFiles();
                documentosExpediente = files.listarDirectorio(breederForm.getFtp() + id);
                System.out.println(documentosExpediente.size());
                documentos = new ArrayList<>();
                for (int i = 0; i < documentosExpediente.size(); i++) {
                    Documento doc = new Documento();
                    doc.setArchivo(documentosExpediente.get(i));
                    String nombre = c.getBreederNombreArchivo(doc.getArchivo());
                    if (!nombre.isEmpty()) {
                        doc.setTipo(nombre);
                    } else {
                        if (doc.getArchivo().equals(breederForm.getDiscountFile())) {
                            doc.setTipo("Documento Descuento");
                        } else if (doc.getArchivo().contains("pdf_breederfrm_")) {
                            doc.setTipo("Formulario");
                        } else if (doc.getArchivo().contains("pdf_voucher_breederfrm_")) {
                            doc.setTipo("Comprobante");
                        } else {
                            doc.setTipo(doc.getArchivo());
                        }
                    }
                    documentos.add(doc);
                }
            }
        } else {
            conbreeder = false;
            return conbreeder;
        }

        return true;
    }

    public boolean cargarMarcasAVisualizarByTramite(String applicationNumber) {
        Controlador c = new Controlador();
        hallmarkForm = c.getHallmarkForm(applicationNumber);

        if (hallmarkForm.getId() != null) {
            System.out.println("denominación: " + hallmarkForm.getDenomination());
            tituloTramiteText = "SIGNO DISTINTIVO";
            conmarca = true;

            if (hallmarkForm.getStatus().equals("DELIVERED")) {
                PpdiSolicitudSignoDistintivo signo = c.getPpdiSolicitudSignoDistintivoByTramite(hallmarkForm.getApplicationNumber());
                if (signo.getCodigoSolicitudSigno() != null) {
                    hallmarkForm.setEstado(signo.getEstado());
                    hallmarkForm.setExpedient(signo.getNumeroExpediente().trim());
                    if (signo.getGaceta() != null && !signo.getGaceta().trim().isEmpty()) {
                        hallmarkForm.setGaceta(signo.getGaceta().trim());
                    } else {
                        hallmarkForm.setGaceta(" -- ");
                    }

                    PpdiTituloSignoDistintivo titsigno = c.getPpdiTituloSignoDistintivoByCodigoSolicitudSigno(signo.getCodigoSolicitudSigno());
                    if (titsigno.getCodigoTituloSignoDistintivo() != null) {
                        hallmarkForm.setTitulo(titsigno.getNumeroTitulo());
                        if (titsigno.getFechaEmisionDocumento() != null) {
                            hallmarkForm.setFechaTitulo(Operaciones.formatDate(titsigno.getFechaEmisionDocumento()));
                        }

                        if (titsigno.getFechaVencimientoTitulo() != null) {
                            hallmarkForm.setFechaVencimiento(Operaciones.formatDate(titsigno.getFechaVencimientoTitulo()));
                        } else {
                            hallmarkForm.setFechaVencimiento(" -- ");
                        }

                    } else {
                        hallmarkForm.setTitulo(" -- ");
                        hallmarkForm.setFechaTitulo(" -- ");
                        hallmarkForm.setFechaVencimiento(" -- ");
                    }
                } else {
                    hallmarkForm.setTitulo(" -- ");
                    hallmarkForm.setFechaTitulo(" -- ");
                    hallmarkForm.setFechaVencimiento(" -- ");
                    hallmarkForm.setGaceta(" -- ");
                    hallmarkForm.setEstado("INICIO DE PROCESO DEL TRÁMITE CON PROBLEMAS");
                    hallmarkForm.setExpedient(" -- ");
                }
            } else {
//                System.out.println("---------> "+hallmarkForm.getPaymentReceiptId());
                if (hallmarkForm.getPaymentReceiptId() != null && hallmarkForm.getPaymentReceiptId() != 0) {
                    hallmarkForm.setEstado("PAGADO, PERO SIN INICIO DE PROCESO");
                } else {
                    hallmarkForm.setEstado("SIN PAGO");
                }
                hallmarkForm.setTitulo(" -- ");
                hallmarkForm.setFechaTitulo(" -- ");
                hallmarkForm.setFechaVencimiento(" -- ");
            }
            FTPFiles files = new FTPFiles();
            documentosExpediente = files.listarDirectorio(hallmarkForm.getFtp() + hallmarkForm.getId());
            documentos = new ArrayList<>();
            for (int i = 0; i < documentosExpediente.size(); i++) {
                Documento doc = new Documento();
                doc.setArchivo(documentosExpediente.get(i));
                String nombre = c.getHallmarkNombreArchivo(doc.getArchivo());
                if (!nombre.isEmpty()) {
                    doc.setTipo(nombre);
                } else {
                    if (doc.getArchivo().equals(hallmarkForm.getDiscountFile())) {
                        doc.setTipo("Documento Descuento");
                    } else if (doc.getArchivo().contains("pdf_hallmarkfrm_")) {
                        doc.setTipo("Formulario");
                    } else if (doc.getArchivo().contains("logo_")) {
                        doc.setTipo("Logo");
                    } else if (doc.getArchivo().contains("pdf_voucher_hallmarkfrm_")) {
                        doc.setTipo("Comprobante");
                    } else {
                        doc.setTipo(doc.getArchivo());
                    }
                }
                documentos.add(doc);
            }
            return conmarca;

        } else {
            conmarca = false;
            return conmarca;
        }
    }

    public boolean cargarPlayFormAVisualizarByTramite(String applicationNumber) {
        Controlador c = new Controlador();
        playForm = c.getPlayFormByApplicationNumber(applicationNumber);
        if (playForm.getId() != null) {
            FTPFiles files = new FTPFiles();
            tituloTramiteText = "DERECHOS DE AUTOR";

            playFormAux = new PlayFormsAux();
            playFormAux.setPlayForm(playForm);

            Integer pagoId = playFormAux.getPlayForm().getPaymentReceiptId();
            if (pagoId != null && pagoId != 0) {
                playFormAux.getPlayForm().setStatus("TRÁMITE PAGADO");
            } else {
                playFormAux.getPlayForm().setStatus("SIN PAGO");
            }

            String tipo = playFormAux.getPlayForm().getAlias();
            System.out.println("tipo: " + tipo + ", id: " + playForm.getId());
            String exten = "";
            switch (tipo) {
                case "literaria":
                    exten = "literaryplay_forms/";
                    break;
                case "artistica":
                    if (playForm.getId() < 82312) {
                        exten = "artmusicplay_forms/";
                    } else {
                        exten = "artplay_forms/";
                    }
                    break;
                case "audiovisua":
                    exten = "audiovisualplay_forms/";
                    break;
                case "software":
                    exten = "softwareplay_forms/";
                    break;
                case "radio":
                    if (playForm.getId() < 82312) {
                        exten = "newsradioplay_forms/";
                    } else {
                        exten = "newsplay_forms/";
                    }
                    break;
                case "musicales":
                    exten = "musicplay_forms/";
                    break;
                case "Radio":
                    exten = "radioplay_forms/";
                    break;
                default:
                    exten = "phonogramplay_forms/";
                    break;
            }

            System.out.println("tipo play: " + exten);

            List<String> docsplay = files.listarDirectorio(playFormAux.getFtp() + exten + playFormAux.getPlayForm().getId());

            List<Documento> docs = new ArrayList<>();
            for (int j = 0; j < docsplay.size(); j++) {
                Documento doc = new Documento();
                doc.setArchivo(docsplay.get(j));
                String nombre = c.getPlayNombreArchivo(doc.getArchivo());
                if (!nombre.isEmpty()) {
                    doc.setTipo(nombre);
                } else {
                    System.out.println("exten: " + exten + "; archivo: " + doc.getArchivo());
                    if (doc.getArchivo().contains("pdf_voucher_")) {
                        doc.setTipo("Comprobante");
                    } else if (doc.getArchivo().contains(exten.substring(0, exten.indexOf("_")))) {
                        doc.setTipo("Formulario");
                    } else {

                        doc.setTipo(doc.getArchivo());
                    }
                }
                docs.add(doc);
            }

            playFormAux.setDocumentos(docs);

            playFormAux.setSitio(playFormAux.getSitio() + exten);

            conderechos = true;
        } else {
            conderechos = false;
        }
        return conderechos;
    }

    public boolean cargarNolineaAVisualizarByAppOrDoc(String appordoc, boolean avisa) {
        Controlador c = new Controlador();
        List<VoucherForm> vouchers = c.getVoucherFormByAppOrDocNumber(appordoc, avisa);
        nolineas = new ArrayList<>();
        System.out.println("vouchers: " + vouchers.size());
        if (!vouchers.isEmpty()) {

            FTPFiles files = new FTPFiles();
            for (int i = 0; i < vouchers.size(); i++) {
                NolineaAux vaux = new NolineaAux();
                VoucherForm va  = vouchers.get(i);

                if (va.getCasillero().equals("0")) {
                    va.setCasillero("GENERACIÓN INTERNA");
                }

                vaux.setVoucherForm(va);

                List<String> docsalcance = files.listarDirectorio(vaux.getFtp() + vaux.getVoucherForm().getId());

                List<Documento> docs = new ArrayList<>();
                for (int j = 0; j < docsalcance.size(); j++) {
                    Documento doc = new Documento();
                    doc.setArchivo(docsalcance.get(j));
                    String nombre = c.getOppositionNombreArchivo(doc.getArchivo());
                    if (!nombre.isEmpty()) {
                        doc.setTipo(nombre);
                    } else {
                        if (doc.getArchivo().contains("pdf_voucherfrm_")) {
                            doc.setTipo("Formulario");
                        } else if (doc.getArchivo().contains("pdf_voucher_")) {
                            doc.setTipo("Comprobante");
                        } else {
                            doc.setTipo(doc.getArchivo());
                        }
                    }
                    docs.add(doc);
                }

                vaux.setDocumentos(docs);
                vaux.setNumNoLinea("TRÁMITE NO EN LÍNEA N° " + (i + 1) + "");
                System.out.println("voucher: " + vaux.getVoucherForm().isDelivered());

                Integer pagoId = vaux.getVoucherForm().getPaymentReceiptId();

                if (pagoId != null && pagoId != 0) {
                    if (vaux.getVoucherForm().isDelivered()) {
                        vaux.getVoucherForm().setStatus("TASA UTILIZADA");
                    } else {
                        vaux.getVoucherForm().setStatus("PAGADO, PERO SIN USARSE LA TASA");
                    }
                } else {
                    vaux.getVoucherForm().setStatus("SIN PAGO");
                }

                nolineas.add(vaux);
            }
            connolinea = true;
        } else {
            connolinea = false;
        }
        return connolinea;
    }

    public boolean cargarOposicionesAVisualizarByTramite(String applicationNumber, boolean searched) {
        Controlador c = new Controlador();
        if (applicationNumber != null && !applicationNumber.trim().isEmpty()) {
            List<OppositionForms> oppositionForms = new ArrayList<>();
            if (searched) {
                oppositionForms = c.getOppositionFormsBySearchedApplicationNumber(applicationNumber);
            } else {
                oppositionForms = c.getOppositionFormsByApplicationNumber(applicationNumber);
            }

            oposiciones = new ArrayList<>();
            System.out.println("oposiciones: " + oppositionForms.size());
            if (!oppositionForms.isEmpty()) {
                FTPFiles files = new FTPFiles();
                for (int i = 0; i < oppositionForms.size(); i++) {
                    OppositionFormsAux oppsaux = new OppositionFormsAux();
                    oppsaux.setOppositionForm(oppositionForms.get(i));
                    List<String> docsopposition = files.listarDirectorio(oppsaux.getFtp() + oppsaux.getOppositionForm().getId());

                    List<Documento> docs = new ArrayList<>();
                    for (int j = 0; j < docsopposition.size(); j++) {
                        Documento doc = new Documento();
                        doc.setArchivo(docsopposition.get(j));
                        String nombre = c.getOppositionNombreArchivo(doc.getArchivo());
                        if (!nombre.isEmpty()) {
                            doc.setTipo(nombre);
                        } else {
                            if (doc.getArchivo().contains("pdf_oppositionfrm_")) {
                                doc.setTipo("Formulario");
                            } else if (doc.getArchivo().contains("pdf_voucher_oppositionfrm_")) {
                                doc.setTipo("Comprobante");
                            } else {
                                doc.setTipo(doc.getArchivo());
                            }
                        }
                        docs.add(doc);
                    }

                    oppsaux.setDocumentos(docs);

                    if (searched) {
                        oppsaux.setNumOposicion("OPOSICIÓN N° " + (i + 1));
                    } else {
                        oppsaux.setNumOposicion("");
                    }

                    Integer pagoId = oppsaux.getOppositionForm().getPaymentReceiptId();

                    if (pagoId != null && pagoId != 0) {
                        if (oppsaux.getOppositionForm().getStatus().equals("DELIVERED")) {
                            oppsaux.getOppositionForm().setStatus("PROCESO INICIADO");
                        } else {
                            oppsaux.getOppositionForm().setStatus("PAGADO, PERO SIN INICIO DE PROCESO");
                        }
                    } else {
                        oppsaux.getOppositionForm().setStatus("SIN PAGO");
                    }

                    oposiciones.add(oppsaux);
                }
                conoposicion = true;
            } else {
                conoposicion = false;
            }
        } else {
            conoposicion = false;
        }
        return conoposicion;
    }

    public boolean cargarTutelasAVisualizarByTramite(String applicationNumber) {

        if (applicationNumber != null && !applicationNumber.trim().isEmpty()) {
            FTPFiles files = new FTPFiles();
            Controlador c = new Controlador();
            tutelageForm = c.getTutelageFormsByApplicationNumber(applicationNumber);
            if (tutelageForm.getId() != null) {
                tituloTramiteText = "TUTELA ADMINISTRATIVA";

                tutelaAux = new TutelageFormsAux();
                tutelaAux.setTutelageForm(tutelageForm);

                Integer pagoId = tutelaAux.getTutelageForm().getPaymentReceiptId();
                if (pagoId != null && pagoId != 0) {
                    if (tutelaAux.getTutelageForm().getStatus().equals("DELIVERED")) {
                        tutelaAux.getTutelageForm().setStatus("PROCESO INICIADO");
                    } else {
                        tutelaAux.getTutelageForm().setStatus("PAGADO, PERO SIN INICIO DE PROCESO");
                    }
                } else {
                    tutelaAux.getTutelageForm().setStatus("SIN PAGO");
                }

                List<String> docstutela = files.listarDirectorio(tutelaAux.getFtp() + tutelaAux.getTutelageForm().getId());

                List<Documento> docs = new ArrayList<>();
                for (int j = 0; j < docstutela.size(); j++) {
                    Documento doc = new Documento();
                    doc.setArchivo(docstutela.get(j));
                    String nombre = c.getTutelageNombreArchivo(doc.getArchivo());
                    if (!nombre.isEmpty()) {
                        doc.setTipo(nombre);
                    } else {
                        nombre = c.getTutelageSampleNombreArchivo(doc.getArchivo());
                        if (!nombre.isEmpty()) {
                            doc.setTipo(nombre);
                        } else {
                            if (doc.getArchivo().contains("pdf_tutelagefrm_")) {
                                doc.setTipo("Formulario");
                            } else if (doc.getArchivo().contains("pdf_voucher_tutelagefrm_")) {
                                doc.setTipo("Comprobante");
                            } else {
                                doc.setTipo(doc.getArchivo());
                            }
                        }
                    }
                    docs.add(doc);
                }

                tutelaAux.setDocumentos(docs);

                contutela = true;
            } else {
                contutela = false;
            }
        } else {
            contutela = false;
        }
        return contutela;
    }

    public boolean cargarModificacionesAVisualizarByTituloOrTramite(String tituloOrTramite, boolean avisa) {
        modificaciones = new ArrayList<>();
        boolean bandera = false;
        Controlador c = new Controlador();
        if (tituloOrTramite != null && !tituloOrTramite.trim().isEmpty()) {
            List<Transferencia> transferencias = c.getTransferencias(tituloOrTramite, avisa);
            System.out.println("transferencias: " + transferencias.size());
            if (!transferencias.isEmpty()) {
                System.out.println("llegando transferencia -----> " + tituloOrTramite);
                bandera = true;
                loadTransferencias(transferencias);
            }
            List<CambioNombre> cambiosnombre = c.getCambiosNombreByTitulo(tituloOrTramite, avisa);
            System.out.println("cambios nombre: " + cambiosnombre.size());
            if (!cambiosnombre.isEmpty()) {
                bandera = true;
                loadCambioNombre(cambiosnombre);
            }
            List<CambioDomicilio> cambiosdomicilio = c.getCambiosDomicilioByTitulo(tituloOrTramite, avisa);
            System.out.println("cambios domicilio: " + cambiosdomicilio.size());
            if (!cambiosdomicilio.isEmpty()) {
                bandera = true;
                loadCambioDomicilio(cambiosdomicilio);
            }
            List<PrendaComercial> prendasc = c.getPrendaComercialByTitulo(tituloOrTramite, avisa);
            System.out.println("prendas comerciales: " + prendasc.size());
            if (!prendasc.isEmpty()) {
                bandera = true;
                loadPrendasComerciales(prendasc);
            }

            List<LicenciaUso> licenciasu = c.getLicenciaUsosByTitulo(tituloOrTramite, avisa);
            System.out.println("licencias uso: " + licenciasu.size());
            if (!licenciasu.isEmpty()) {
                bandera = true;
                loadLicenciasUso(licenciasu);
            }
            List<SubLicenciaUso> sublicencia = c.getSubLicenciaUsosByTitulo(tituloOrTramite, avisa);
            System.out.println("sublicencia de uso: " + sublicencia.size());
            if (!sublicencia.isEmpty()) {
                bandera = true;
                loadSublicencias(sublicencia);
            }

            List<Renovacion> renovacions = c.getRenovacionesByTitOrTramite(tituloOrTramite, avisa);
            System.out.println("renovaciones: " + renovacions.size());
            if (!renovacions.isEmpty()) {
                bandera = true;
                loadRenovaciones(renovacions);
            }
        }

        if (!bandera && avisa) {
            System.out.println("Entro por aquí");
            RenewalForm renewal = c.getRenewalFormByApplicationNumber(tituloOrTramite);
            if (renewal.getId() != null) {
                if (renewal.getDebugId() != null) {
                    HallmarkFormDepurada hf = c.getHallmarkDepuradaByDebugId(renewal.getDebugId());
                    if (hf.getId() != null) {
                        bandera = loadRenewalFormToShow(renewal, hf.getExpedient(), hf.getExpYear());
                    } else {
                        bandera = loadRenewalFormToShow(renewal, "", "");
                    }
                } else {
                    bandera = loadRenewalFormToShow(renewal, "", "");
                }
            }
        }

        if (conmarca) {
            System.out.println("Entro por aquí 2");
            HallmarkFormDepurada hf = c.getHallmarkDepuradaByExpedient(hallmarkForm.getApplicationNumber());
            if (hf.getId() != null) {
                RenewalForm renewal = c.getRenewalFormByDebugId(hf.getId());
                if (renewal.getId() != null) {
                    loadRenewalFormToShow(renewal, hf.getExpedient(), hf.getExpYear());
                }
            }
        }
        return bandera;
    }

    public void buscarCasillero(ActionEvent ae) {
        FacesMessage msg = null;
        if (tramite != null && tramite.getIdTramite() != null) {
            if (criterioOwner != null && !criterioOwner.trim().isEmpty()) {
                System.out.println("criterio owner: " + criterioOwner);
                Controlador c = new Controlador();
                owners = new ArrayList<>();

                owners = c.getOwnersByCriteria(criterioOwner);

                if (owners.isEmpty()) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "NO SE ENCONTRARON RESULTADOS");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "CONSULTA REALIZADA");
                }

            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "INGRESE UN CRITERIO DE BÚSQUEDA VÁLIDO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE CARGÓ LA MODIFICACIÓN CORRECTAMENTE");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void seleccionarCasillero(ActionEvent ae) {
        FacesMessage msg = null;
        if (owner != null && owner.getId() != null) {
            if (tramite != null && tramite.getIdTramite() != null) {
                owneraux = (Owner) ownerDataTable.getRowData();
                if (owneraux.getId() != null) {
                    if (!owneraux.getId().equals(owner.getId())) {
                        cambiarCasillero = new CambioCasillero();
                        cambiarCasillero.setNuevoCasillero(owneraux.getCasillero());
                        cambiarCasillero.setTitularCasilleroNuevo(owneraux.getName());
                        cambiarCasillero.setCorreoTitularNuevo(owneraux.getEmail());
                        cambiarCasillero.setTramite(tramite.getSolicitud().trim().toUpperCase());
                        cambiarCasillero.setDenominacion(tramite.getDenominacion());
                        cambiarCasillero.setCasilleroAnterior(owner.getCasillero());
                        cambiarCasillero.setTitularCasilleroAnterior(owner.getName());
                        cambiarCasillero.setCorreoTitularAnterior(owner.getEmail());
                        cambiarCasillero.setFechaProvidencia(new Date());

                        System.out.println(cambiarCasillero.getCasilleroAnterior());

                        owners = new ArrayList<>();
                        criterioOwner = "";
                        PrimeFaces.current().ajax().addCallbackParam("selcas", true);
//                cambioc = true;
                        confirmChangeMessage = "SEGURO DE CAMBIAR EL CASILLERO " + cambiarCasillero.getCasilleroAnterior() + " DEL TRÁMITE " + cambiarCasillero.getTramite() + "\nPOR EL CASILLERO " + cambiarCasillero.getNuevoCasillero() + " ?";
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "CASILLERO " + owneraux.getCasillero() + " SELECCIONADO CORRECTAMENTE");
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL CASILLERO NUEVO NO PUEDE SER EL MISMO QUE EL ANTERIOR");
                    }
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL SELECCIONAR EL CASILLERO");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA EN LA BÚSQUEDA DE CASILLEROS");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL LEER EL CASILLERO ACTUAL");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void prepararBusquedaCasillero(ActionEvent ae) {
        FacesMessage msg = null;
        if (tramite != null) {
            if (tramite.getDenominacion() != null && !tramite.getDenominacion().trim().isEmpty()) {
                owners = new ArrayList<>();
                criterioOwner = "";
                PrimeFaces.current().ajax().addCallbackParam("casit", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SELECCIONE EL NUEVO CASILLERO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "INGRESE UNA DENOMINACIÓN VÁLIDA");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DE INGRESAR UN NÚMERO DE TRÁMITE VÁLIDO");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void viewProvidenciaCambioCasillero(ActionEvent ae) {
        FacesMessage msg = null;
//        System.out.println("fecha providencia 1: " + cambiarCasillero.getFechaProvidencia());
        if (cambiarCasillero != null && cambiarCasillero.getCasilleroAnterior() != null && !cambiarCasillero.getCasilleroAnterior().trim().isEmpty()) {
            if (cambiarCasillero.getNuevoCasillero() != null && !cambiarCasillero.getNuevoCasillero().trim().isEmpty()) {
                Controlador c = new Controlador();
                String text = c.getConfiguracionesActivas();
                if (text.isEmpty()) {
                    if (usuarioCambioCasillero.getId() != null) {
                        if (cambiarCasillero.getNuevoCasillero().equals(cambiarCasillero.getCasilleroAnterior())) {
                            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL CASILLERO NUEVO DEBE SER DIFERENTE AL CASILLERO ANTERIOR");
                        } else {
                            if (cambiarCasillero.getId() == null) {
//                                System.out.println("fecha providencia 1: "+cambiarCasillero.getFechaProvidencia());
                                cambiarCasillero.setFechaNotificacion(new Date());
                                //                                cambiarCasillero.setFechaProvidencia(new Date());
                                cambiarCasillero.setProvidencia(c.nextProvidenciaCambioCasillero());
                                cambiarCasillero.setEstado("PENDIENTE");
                                cambiarCasillero.setUsuario(login.getNombre());

                                if (cambiarCasillero.getDenominacion() == null || cambiarCasillero.getDenominacion().trim().isEmpty()) {
                                    cambiarCasillero.setDenominacion("-denominación no encontrada-");
                                }

                                cambiarCasillero.setFuente("SIGNOS DISTINTIVOS");
                                if (c.saveCambioCasillero(cambiarCasillero)) {
                                    cambiarCasillero = c.getCambioCasilleroWhenNotId(cambiarCasillero);
                                    System.out.println("CambioCasillero: " + cambiarCasillero.getProvidencia());

                                    notaprovidencia = "PROVIDENCIA N° " + cambiarCasillero.getProvidencia() + " GENERADA, DIRÍGASE A LA PESTAÑA CAMBIOS CASILLERO PARA COMPLETAR EL PROCESO";
                                }
                            }
//                            System.out.println("fecha providencia 2: "+cambiarCasillero.getFechaProvidencia());
                            login.setCambioCasillero(cambiarCasillero);
                            login.setUsuarioCambioCasillero(getUsuarioCambioCasillero());

                            PrimeFaces.current().ajax().addCallbackParam("doit", true);

                            System.out.println("envía providencia cambio casillero descargar");
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "DESCARGANDO PROVIDENCIA CAMBIO CASILLERO " + cambiarCasillero.getProvidencia());
                        }
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "ESTE USUARIO NO ESTA ACIVO PARA CAMBIO DE CASILLERO");

                    }

                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", text);
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DEBE SELECCIONAR UN CASILLERO A ASIGNAR VÁLIDO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HAY UN PROBLEMA CON EL TRÁMITE ACTUAL");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void limpiarBusquedaCasillero(ActionEvent ae) {
        owners = new ArrayList<>();
        criterioOwner = "";
    }

    public void prepararCasilleroHallmark(ActionEvent ae) {
        FacesMessage msg;
        if (hallmarkForm != null && hallmarkForm.getId() != null) {
            Controlador c = new Controlador();
            owner = c.getOwnerById(hallmarkForm.getOwnerId());
            if (owner.getId() != null) {
                cambioCasillero = false;
                dialogCasillero = "CASILLERO " + hallmarkForm.getCasillero() + " - TRÁMITE " + hallmarkForm.getApplicationNumber();

                tramite = new Tramite();
                tramite.setCasillero(Integer.parseInt(hallmarkForm.getCasillero()));
                tramite.setIdTramite(hallmarkForm.getId());
                tramite.setOwner(owner.getId());
                tramite.setSolicitud(hallmarkForm.getApplicationNumber());
                tramite.setTipo("MARCA");
                tramite.setDenominacion(hallmarkForm.getDenomination());

                notaprovidencia = "";

                cambiocas = true;

                PrimeFaces.current().ajax().addCallbackParam("doit", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "CASILLERO ENCONTRADO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");
            }

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void prepararCasilleroPantent(ActionEvent ae) {
        FacesMessage msg;
        if (patentForm != null && patentForm.getId() != null) {
            Controlador c = new Controlador();
            owner = c.getOwnerById(patentForm.getOwnerId());
            if (owner.getId() != null) {
                dialogCasillero = "CASILLERO " + patentForm.getCasillero() + " - TRÁMITE " + patentForm.getApplicationNumber();
                cambiocas = false;
                PrimeFaces.current().ajax().addCallbackParam("doit", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "CASILLERO ENCONTRADO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void prepararModificacion(ModificacionAux mod) {
        FacesMessage msg;
        if (mod != null && mod.getId() != null) {
            Controlador c = new Controlador();
            owner = c.getOwnerByLockerId(Integer.parseInt(mod.getCasillero()));
            if (owner.getId() != null) {
                dialogCasillero = "CASILLERO " + mod.getCasillero() + " - TRÁMITE " + mod.getSolicitud();
                cambiocas = false;
                PrimeFaces.current().ajax().addCallbackParam("doit", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "CASILLERO ENCONTRADO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void prepararTutela(ActionEvent ae) {
        FacesMessage msg;
        if (tutelageForm != null && tutelageForm.getId() != null) {
            Controlador c = new Controlador();
            owner = c.getOwnerById(tutelageForm.getOwnerId());
            if (owner.getId() != null) {
                dialogCasillero = "CASILLERO " + tutelageForm.getCasillero() + " - TRÁMITE " + tutelageForm.getApplicationNumber();
                cambiocas = false;
                PrimeFaces.current().ajax().addCallbackParam("doit", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "CASILLERO ENCONTRADO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void prepararDerechos(ActionEvent ae) {
        FacesMessage msg;
        if (playForm != null && playForm.getId() != null) {
            Controlador c = new Controlador();
            owner = c.getOwnerById(playForm.getOwnerId());
            if (owner.getId() != null) {
                dialogCasillero = "CASILLERO " + playForm.getCasillero() + " - TRÁMITE " + playForm.getApplicationNumber();
                cambiocas = false;
                PrimeFaces.current().ajax().addCallbackParam("doit", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "CASILLERO ENCONTRADO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void prepararAlcance(ScopeFormsAux scop) {
        FacesMessage msg;
        if (scop != null && scop.getScopeForm().getId() != null) {
            Controlador c = new Controlador();
            owner = c.getOwnerById(scop.getScopeForm().getOwnerId());
            if (owner.getId() != null) {
                dialogCasillero = "CASILLERO " + scop.getScopeForm().getCasillero() + " - TRÁMITE " + scop.getScopeForm().getApplicationNumber();
                cambiocas = false;
                PrimeFaces.current().ajax().addCallbackParam("doit", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "CASILLERO ENCONTRADO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void prepararNotificacion(NotificationsAux naux) {
        FacesMessage msg;
        if (naux != null && naux.getNotification().getId() != null) {
            Controlador c = new Controlador();
            owner = c.getOwnerByLockerId(naux.getNotification().getLocker());
            if (owner.getId() != null) {
                dialogCasillero = "CASILLERO " + naux.getNotification().getLocker() + " - TRÁMITE " + naux.getNotification().getMatter();
                cambiocas = false;
                PrimeFaces.current().ajax().addCallbackParam("doit", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "CASILLERO ENCONTRADO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void prepararOposicion(OppositionFormsAux opaux) {
        FacesMessage msg;
        if (opaux != null && opaux.getOppositionForm().getId() != null) {
            Controlador c = new Controlador();
            owner = c.getOwnerById(opaux.getOppositionForm().getOwnerId());
            if (owner.getId() != null) {
                dialogCasillero = "CASILLERO " + opaux.getOppositionForm().getCasillero() + " - TRÁMITE " + opaux.getOppositionForm().getApplicationNumber();
                cambiocas = false;
                PrimeFaces.current().ajax().addCallbackParam("doit", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "CASILLERO ENCONTRADO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void prepararTramNoLinea(NolineaAux noaux) {
        FacesMessage msg;
        if (noaux != null && noaux.getVoucherForm().getId() != null) {
            Controlador c = new Controlador();
            owner = c.getOwnerById(noaux.getVoucherForm().getOwnerId());
            if (owner.getId() != null) {
                dialogCasillero = "CASILLERO " + noaux.getVoucherForm().getCasillero() + " - TRÁMITE " + noaux.getVoucherForm().getApplicationNumber();
                cambiocas = false;
                PrimeFaces.current().ajax().addCallbackParam("doit", true);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "CASILLERO ENCONTRADO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR CARGAR AL CASILLERO");

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean loadRenewalFormToShow(RenewalForm renewal, String titulo, String fecha) {
        ModificacionAux mod = new ModificacionAux();
        mod.setSolicitud(renewal.getApplicationNumber());
        mod.setFechaPresentacion(renewal.getApplicationDate());
        mod.setActor1("--");
        mod.setActor2("--");
        mod.setCertificado("--");
        mod.setFechaCertificado("--");
        mod.setDenominacion("--");

        mod.setTitulo(titulo);
        mod.setFechaTitulo(fecha);
        mod.setCasillero(renewal.getCasillero());

        mod.setId(renewal.getId());

        Controlador c = new Controlador();
        FTPFiles files = new FTPFiles();
        List<String> docstra = files.listarDirectorio(mod.getFtp() + mod.getId());

        List<Documento> docs = new ArrayList<>();
        for (int j = 0; j < docstra.size(); j++) {
            Documento doc = new Documento();
            doc.setArchivo(docstra.get(j));
            String nombre = c.getRenewalNombreArchivo(doc.getArchivo());
            if (!nombre.isEmpty()) {
                doc.setTipo(nombre);
            } else {
                if (doc.getArchivo().contains("pdf_renewalfrm_")) {
                    doc.setTipo("Formulario");
                } else if (doc.getArchivo().contains("pdf_voucher_renewalfrm_")) {
                    doc.setTipo("Comprobante");
                } else {
                    doc.setTipo(doc.getArchivo());
                }
            }
            docs.add(doc);
        }

        mod.setDocumentos(docs);
        mod.setTipo(renewal.getTipo());

        if (renewal.getPaymentReceiptId() != null && renewal.getPaymentReceiptId() != 0) {
            if (renewal.getStatus().equals("DELIVERED")) {
                mod.setEstado("PROCESO INICIADO");
            } else {
                mod.setEstado("PROCESO PAGADO, PERO NO INICIADO");
            }
        } else {
            mod.setEstado("PROCESO SIN PAGO");
        }

        modificaciones.add(mod);
        conmodificacion = true;
        return true;
    }

    public boolean cargarNotificacionesAVisualizarByTramite(String tramite) {
        Controlador c = new Controlador();
        List<Notifications> notifications = c.getNotificationsByTramite(tramite);
        notificaciones = new ArrayList<>();
        System.out.println("notificaciones: " + notifications.size());
        if (notifications.isEmpty()) {
            NotificationsAux numnotaux = new NotificationsAux();
            numnotaux.setNumNotification("NO SE ENCONTRARON NOTIFICACIONES PARA ESTE TRÁMITE");
            notificaciones.add(numnotaux);
            connotificacion = false;
        } else {
//            FTPFiles files = new FTPFiles();
            for (int i = 0; i < notifications.size(); i++) {
                NotificationsAux notificationaux = new NotificationsAux();
                notificationaux.setNotification(notifications.get(i));

                //List<String> docsnot = files.listarDirectorio(notificationaux.getFtp() + notificationaux.getNotification().getLocker());
                List<String> docsnot = new ArrayList<>();

                String doc = notificationaux.getNotification().getDocument();
                docsnot.add(doc);

                notificationaux.setDocumentos(docsnot);

                notificationaux.setNumNotification("NOTIFICACIÓN N° " + (i + 1) + "");

                if (notificationaux.getNotification().getStatus().equals("OPENED")) {
                    notificationaux.getNotification().setStatusText("EL DOCUMENTO HA SIDO ABIERTO");
                } else if (notificationaux.getNotification().getStatus().equals("SENT")) {
                    notificationaux.getNotification().setStatusText("EL DOCUMENTO NO HA SIDO ABIERTO AÚN");
                } else if (notificationaux.getNotification().getStatus().equals("DRAFT")) {
                    notificationaux.getNotification().setStatusText("EL DOCUMENTO NO ES VISIBLE PARA EL USUARIO");
                } else {
                    notificationaux.getNotification().setStatusText("EL DOCUMENTO HA SIDO RETIRADO DE LAS NOTIFICACIONES DEL USUARIO");
                }
//                System.out.println(notificationaux.getNotification().getDocument());
                notificaciones.add(notificationaux);
            }
            connotificacion = true;
        }
        return connotificacion;
    }

    public boolean cargarAlcancesAVisualizarByAfforAplicationNumber(String afforApplicationNumber, boolean aviso) {
        Controlador c = new Controlador();
        List<ScopeForms> scopes = c.getScopeFormsByAfforApplicationNumber(afforApplicationNumber, aviso);
        alcances = new ArrayList<>();
        System.out.println("scopes: " + scopes.size());
        if (scopes.isEmpty()) {
            ScopeFormsAux scopeaux = new ScopeFormsAux();
            scopeaux.setNumAlcance("NO SE ENCONTRARON ALCANCES PARA ESTE TRÁMITE");
            alcances.add(scopeaux);
            conalcance = false;
        } else {
            FTPFiles files = new FTPFiles();
            for (int i = 0; i < scopes.size(); i++) {
                ScopeFormsAux scopeaux = new ScopeFormsAux();
                scopeaux.setScopeForm(scopes.get(i));
                List<String> docsalcance = files.listarDirectorio(scopeaux.getFtp() + scopeaux.getScopeForm().getId());

                List<Documento> docs = new ArrayList<>();
                for (int j = 0; j < docsalcance.size(); j++) {
                    Documento doc = new Documento();
                    doc.setArchivo(docsalcance.get(j));
                    String nombre = c.getScopeNombreArchivo(doc.getArchivo());
                    if (!nombre.isEmpty()) {
                        doc.setTipo(nombre);
                    } else {
                        if (doc.getArchivo().contains("pdf_scopefrm_")) {
                            doc.setTipo("Formulario");
                        } else if (doc.getArchivo().contains("pdf_voucher_scopefrm_")) {
                            doc.setTipo("Comprobante");
                        } else {
                            doc.setTipo(doc.getArchivo());
                        }
                    }
                    docs.add(doc);
                }

                scopeaux.setDocumentos(docs);
                if (aviso) {
                    scopeaux.setNumAlcance("");
                } else {
                    scopeaux.setNumAlcance("ALCANCE N° " + (i + 1) + "");
                }

                System.out.println(scopeaux.getScopeForm().getDescription());
                alcances.add(scopeaux);
            }
            conalcance = true;
        }
        return conalcance;
    }

    public void loadTransferencias(List<Transferencia> transferencias) {
        FTPFiles files = new FTPFiles();
        Controlador c = new Controlador();
        for (int i = 0; i < transferencias.size(); i++) {
            Transferencia transf = transferencias.get(i);
            ModificacionAux mod = new ModificacionAux();
            mod.setSolicitud(transf.getSolicitud());
            mod.setFechaPresentacion(Operaciones.formatDate(transf.getFechaPresentacion()));
            mod.setActor1(transf.getTitularAnterior());
            mod.setActor2(transf.getTitularActual());
            mod.setCertificado(transf.getCertificado() + "");
            mod.setFechaCertificado(Operaciones.formatDate(transf.getFechaCertificado()));
            mod.setDenominacion(transf.getDenominacion());
            mod.setTitulo(transf.getRegistro());
            mod.setFechaTitulo(Operaciones.formatDate(transf.getFechaRegistro()));
            mod.setCasillero(transf.getCasilleroSenadi());

            RenewalForm rf = c.getRenewalFormByApplicationNumber(mod.getSolicitud());
            if (rf.getId() != null) {
                mod.setId(rf.getId());
                mod.setCasillero(rf.getCasillero());
            }

            List<String> docstra = files.listarDirectorio(mod.getFtp() + mod.getId());

            List<Documento> docs = new ArrayList<>();
            for (int j = 0; j < docstra.size(); j++) {
                Documento doc = new Documento();
                doc.setArchivo(docstra.get(j));
                String nombre = c.getRenewalNombreArchivo(doc.getArchivo());
                if (!nombre.isEmpty()) {
                    doc.setTipo(nombre);
                } else {
                    if (doc.getArchivo().contains("pdf_renewalfrm_")) {
                        doc.setTipo("Formulario");
                    } else if (doc.getArchivo().contains("pdf_voucher_renewalfrm_")) {
                        doc.setTipo("Comprobante");
                    } else {
                        doc.setTipo(doc.getArchivo());
                    }
                }
                docs.add(doc);
            }

            mod.setDocumentos(docs);
            mod.setTipo("TRANSFERENCIA");

            modificaciones.add(mod);
        }
        conmodificacion = true;
    }

    public void loadCambioNombre(List<CambioNombre> cambiosnombre) {
        FTPFiles files = new FTPFiles();
        Controlador c = new Controlador();
        for (int i = 0; i < cambiosnombre.size(); i++) {
            CambioNombre cnombre = cambiosnombre.get(i);
            ModificacionAux mod = new ModificacionAux();
            mod.setSolicitud(cnombre.getSolicitud());
            mod.setFechaPresentacion(Operaciones.formatDate(cnombre.getFechaPresentacion()));
            mod.setActor1(cnombre.getTitularAnterior());
            mod.setActor2(cnombre.getTitularActual());
            mod.setCertificado(cnombre.getCertificado() + "");
            mod.setFechaCertificado(cnombre.getFechaCertificado() != null ? Operaciones.formatDate(cnombre.getFechaCertificado()) : "");
            mod.setDenominacion(cnombre.getDenominacion());
            mod.setTitulo(cnombre.getRegistro());
            mod.setFechaTitulo(Operaciones.formatDate(cnombre.getFechaRegistro()));
            mod.setCasillero(cnombre.getCasilleroSenadi());
            RenewalForm rf = c.getRenewalFormByApplicationNumber(mod.getSolicitud());
            if (rf.getId() != null) {
                mod.setId(rf.getId());
                mod.setCasillero(rf.getCasillero());
            }
            List<String> docstra = files.listarDirectorio(mod.getFtp() + mod.getId());
            List<Documento> docs = new ArrayList<>();
            for (int j = 0; j < docstra.size(); j++) {
                Documento doc = new Documento();
                doc.setArchivo(docstra.get(j));
                String nombre = c.getRenewalNombreArchivo(doc.getArchivo());
                if (!nombre.isEmpty()) {
                    doc.setTipo(nombre);
                } else {
                    if (doc.getArchivo().contains("pdf_renewalfrm_")) {
                        doc.setTipo("Formulario");
                    } else if (doc.getArchivo().contains("pdf_voucher_renewalfrm_")) {
                        doc.setTipo("Comprobante");
                    } else {
                        doc.setTipo(doc.getArchivo());
                    }
                }
                docs.add(doc);
            }

            mod.setDocumentos(docs);
            mod.setTipo("CAMBIO DE NOMBRE");

            modificaciones.add(mod);
        }
        conmodificacion = true;
    }

    public void loadCambioDomicilio(List<CambioDomicilio> cambiosdomicilio) {
        FTPFiles files = new FTPFiles();
        Controlador c = new Controlador();
        for (int i = 0; i < cambiosdomicilio.size(); i++) {
            CambioDomicilio cdomicilio = cambiosdomicilio.get(i);
            ModificacionAux mod = new ModificacionAux();
            mod.setSolicitud(cdomicilio.getSolicitud());
            mod.setFechaPresentacion(Operaciones.formatDate(cdomicilio.getFechaPresentacion()));
            mod.setActor1(cdomicilio.getTitularAnterior());
            mod.setActor2(cdomicilio.getTitularActual());
            mod.setCertificado(cdomicilio.getCertificado() != null ? cdomicilio.getCertificado() + "" : "");
            mod.setFechaCertificado(cdomicilio.getFechaCertificado() != null ? Operaciones.formatDate(cdomicilio.getFechaCertificado()) : "");
            mod.setDenominacion(cdomicilio.getDenominacion());
            mod.setTitulo(cdomicilio.getRegistro());
            mod.setFechaTitulo(Operaciones.formatDate(cdomicilio.getFechaRegistro()));
            mod.setCasillero(cdomicilio.getCasilleroSenadi());
            RenewalForm rf = c.getRenewalFormByApplicationNumber(mod.getSolicitud());
            if (rf.getId() != null) {
                mod.setId(rf.getId());
                mod.setCasillero(rf.getCasillero());
            }

            List<String> docstra = files.listarDirectorio(mod.getFtp() + mod.getId());

            List<Documento> docs = new ArrayList<>();
            for (int j = 0; j < docstra.size(); j++) {
                Documento doc = new Documento();
                doc.setArchivo(docstra.get(j));
                String nombre = c.getRenewalNombreArchivo(doc.getArchivo());
                if (!nombre.isEmpty()) {
                    doc.setTipo(nombre);
                } else {
                    if (doc.getArchivo().contains("pdf_renewalfrm_")) {
                        doc.setTipo("Formulario");
                    } else if (doc.getArchivo().contains("pdf_voucher_renewalfrm_")) {
                        doc.setTipo("Comprobante");
                    } else {
                        doc.setTipo(doc.getArchivo());
                    }
                }
                docs.add(doc);
            }

            mod.setDocumentos(docs);
            mod.setTipo("CAMBIO DE DOMICILIO");

            modificaciones.add(mod);
        }
        conmodificacion = true;
    }

    public void loadPrendasComerciales(List<PrendaComercial> prendas) {
        FTPFiles files = new FTPFiles();
        Controlador c = new Controlador();
        for (int i = 0; i < prendas.size(); i++) {
            PrendaComercial prenda = prendas.get(i);
            ModificacionAux mod = new ModificacionAux();
            mod.setSolicitud(prenda.getSolicitud());
            mod.setFechaPresentacion(Operaciones.formatDate(prenda.getFechaPresentacion()));
            mod.setActor1(prenda.getDeudoraPrendaria());
            mod.setActor2(prenda.getPrendariaAcreedora());
            mod.setCertificado(prenda.getPrendaNo() + "");
            mod.setFechaCertificado(Operaciones.formatDate(prenda.getFechaPrenda()));
            mod.setDenominacion(prenda.getDenominacion());
            mod.setTitulo(prenda.getRegistro());
            mod.setFechaTitulo(Operaciones.formatDate(prenda.getFechaRegistro()));
            mod.setCasillero(prenda.getCasilleroSenadi());

            RenewalForm rf = c.getRenewalFormByApplicationNumber(mod.getSolicitud());
            if (rf.getId() != null) {
                mod.setId(rf.getId());
                mod.setCasillero(rf.getCasillero());
            }

            List<String> docstra = files.listarDirectorio(mod.getFtp() + mod.getId());

            List<Documento> docs = new ArrayList<>();
            for (int j = 0; j < docstra.size(); j++) {
                Documento doc = new Documento();
                doc.setArchivo(docstra.get(j));
                String nombre = c.getRenewalNombreArchivo(doc.getArchivo());
                if (!nombre.isEmpty()) {
                    doc.setTipo(nombre);
                } else {
                    if (doc.getArchivo().contains("pdf_renewalfrm_")) {
                        doc.setTipo("Formulario");
                    } else if (doc.getArchivo().contains("pdf_voucher_renewalfrm_")) {
                        doc.setTipo("Comprobante");
                    } else {
                        doc.setTipo(doc.getArchivo());
                    }
                }
                docs.add(doc);
            }

            mod.setDocumentos(docs);
            mod.setTipo("PRENDA COMERCIAL");

            modificaciones.add(mod);
        }
        conmodificacion = true;
    }

    public void loadLicenciasUso(List<LicenciaUso> licencias) {
        FTPFiles files = new FTPFiles();
        Controlador c = new Controlador();
        for (int i = 0; i < licencias.size(); i++) {
            LicenciaUso licencia = licencias.get(i);
            ModificacionAux mod = new ModificacionAux();
            mod.setSolicitud(licencia.getSolicitud());
            mod.setFechaPresentacion(Operaciones.formatDate(licencia.getFechaPresentacion()));
            mod.setActor1(licencia.getLicenciante());
            mod.setActor2(licencia.getLicenciatario());
            mod.setCertificado(licencia.getLicenciaNo());
            if (licencia.getFechaLicencia() != null) {
                mod.setFechaCertificado(Operaciones.formatDate(licencia.getFechaLicencia()));
            }
            mod.setDenominacion(licencia.getDenominacion());
            mod.setTitulo(licencia.getRegistro());
            mod.setFechaTitulo(Operaciones.formatDate(licencia.getFechaRegistro()));
            mod.setCasillero(licencia.getCasilleroSenadi());

            RenewalForm rf = c.getRenewalFormByApplicationNumber(mod.getSolicitud());
            if (rf.getId() != null) {
                mod.setId(rf.getId());
                mod.setCasillero(rf.getCasillero());
            }

            List<String> docstra = files.listarDirectorio(mod.getFtp() + mod.getId());

            List<Documento> docs = new ArrayList<>();
            for (int j = 0; j < docstra.size(); j++) {
                Documento doc = new Documento();
                doc.setArchivo(docstra.get(j));
                String nombre = c.getRenewalNombreArchivo(doc.getArchivo());
                if (!nombre.isEmpty()) {
                    doc.setTipo(nombre);
                } else {
                    if (doc.getArchivo().contains("pdf_renewalfrm_")) {
                        doc.setTipo("Formulario");
                    } else if (doc.getArchivo().contains("pdf_voucher_renewalfrm_")) {
                        doc.setTipo("Comprobante");
                    } else {
                        doc.setTipo(doc.getArchivo());
                    }
                }
                docs.add(doc);
            }

            mod.setDocumentos(docs);
            mod.setTipo("LICENCIA DE USO");

            modificaciones.add(mod);
        }
        conmodificacion = true;
    }

    public void loadSublicencias(List<SubLicenciaUso> sublicencias) {
        FTPFiles files = new FTPFiles();
        Controlador c = new Controlador();
        for (int i = 0; i < sublicencias.size(); i++) {
            SubLicenciaUso cnombre = sublicencias.get(i);
            ModificacionAux mod = new ModificacionAux();
            mod.setSolicitud(cnombre.getSolicitud());
            mod.setFechaPresentacion(Operaciones.formatDate(cnombre.getFechaPresentacion()));
            mod.setActor1(cnombre.getSublicenciante());
            mod.setActor2(cnombre.getSublicenciatario());
            mod.setCertificado(cnombre.getSublicenciaNo() + "");
            mod.setFechaCertificado(Operaciones.formatDate(cnombre.getFechaSublicencia()));
            mod.setDenominacion(cnombre.getDenominacion());
            mod.setTitulo(cnombre.getRegistro());
            mod.setFechaTitulo(Operaciones.formatDate(cnombre.getFechaRegistro()));
            mod.setCasillero(cnombre.getCasilleroSenadi());

            RenewalForm rf = c.getRenewalFormByApplicationNumber(mod.getSolicitud());
            if (rf.getId() != null) {
                mod.setId(rf.getId());
                mod.setCasillero(rf.getCasillero());
            }

            List<String> docstra = files.listarDirectorio(mod.getFtp() + mod.getId());

            List<Documento> docs = new ArrayList<>();
            for (int j = 0; j < docstra.size(); j++) {
                Documento doc = new Documento();
                doc.setArchivo(docstra.get(j));
                String nombre = c.getRenewalNombreArchivo(doc.getArchivo());
                if (!nombre.isEmpty()) {
                    doc.setTipo(nombre);
                } else {
                    if (doc.getArchivo().contains("pdf_renewalfrm_")) {
                        doc.setTipo("Formulario");
                    } else if (doc.getArchivo().contains("pdf_voucher_renewalfrm_")) {
                        doc.setTipo("Comprobante");
                    } else {
                        doc.setTipo(doc.getArchivo());
                    }
                }
                docs.add(doc);
            }

            mod.setDocumentos(docs);
            mod.setTipo("SUBLICENCIA DE USO");

            modificaciones.add(mod);
        }
        conmodificacion = true;
    }

    public void loadRenovaciones(List<Renovacion> renovaciones) {
        FTPFiles files = new FTPFiles();
        Controlador c = new Controlador();
        for (int i = 0; i < renovaciones.size(); i++) {
            Renovacion renova = renovaciones.get(i);
            ModificacionAux mod = new ModificacionAux();
            mod.setSolicitud(renova.getSolicitudSenadi());
            mod.setFechaPresentacion(Operaciones.formatDate(renova.getFechaPresentacion()));

            mod.setActor1(renova.getTitularActual());
//            mod.setActor2(renova.getSublicenciatario());
            mod.setCertificado(renova.getCertificadoNo() + "");
            mod.setFechaCertificado(Operaciones.formatDate(renova.getFechaCertificado()));
            mod.setDenominacion(renova.getDenominacion());
            mod.setTitulo(renova.getRegistroNo());
            mod.setFechaTitulo(Operaciones.formatDate(renova.getFechaRegistro()));
            mod.setCasillero(renova.getCasilleroSenadi());

            RenewalForm rf = c.getRenewalFormByApplicationNumber(mod.getSolicitud());
            if (rf.getId() != null) {
                mod.setId(rf.getId());
                mod.setCasillero(rf.getCasillero());
            }

            List<String> docstra = files.listarDirectorio(mod.getFtp() + mod.getId());

            List<Documento> docs = new ArrayList<>();
            for (int j = 0; j < docstra.size(); j++) {
                Documento doc = new Documento();
                doc.setArchivo(docstra.get(j));
                String nombre = c.getRenewalNombreArchivo(doc.getArchivo());
                if (!nombre.isEmpty()) {
                    doc.setTipo(nombre);
                } else {
                    if (doc.getArchivo().contains("pdf_renewalfrm_")) {
                        doc.setTipo("Formulario");
                    } else if (doc.getArchivo().contains("pdf_voucher_renewalfrm_")) {
                        doc.setTipo("Comprobante");
                    } else {
                        doc.setTipo(doc.getArchivo());
                    }
                }
                docs.add(doc);
            }

            mod.setDocumentos(docs);
            mod.setTipo("RENOVACION");

            modificaciones.add(mod);
        }
        conmodificacion = true;
    }

    public void prepararCambio() {
        if (cambioCasillero) {
            Controlador c = new Controlador();
            if (c.existsUsuarioActivo(login.getNombre(), true)) {
                cambiarCasillero = new CambioCasillero();
                owneraux = new Owner();
                String text = c.getConfiguracionesActivas();
                if (!text.isEmpty()) {
                    cambioCasillero = false;

                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", text);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    usuarioCambioCasillero = c.getUsuarioActivo(login.getNombre(), true);
                }
            } else {
                cambioCasillero = false;
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "AVISO", "EL USUARIO ACTUAL NO ESTÁ ACTIVO PARA CAMBIOS DE CASILLERO");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        }
    }

    public void limpiarDatos(boolean enable) {
        conmarca = enable;
        conalcance = enable;
        conmodificacion = enable;
        contutela = enable;
        resultadopanel = enable;
        conoposicion = enable;
        connolinea = enable;
        conderechos = enable;
        conpatente = enable;
        connotificacion = enable;
        hijas = enable;
    }

    public void enableOptions(AjaxBehaviorEvent event) {
        if (depurar) {
            System.out.println("Depurar es true");
        } else {
            System.out.println("Depurar es false");
        }
    }

    public void disableDepurar() {
        depurar = false;
    }

    public void depurarNotificacionesPorCasillero(ActionEvent ae) {
        FacesMessage msg = null;

        if (!casilleroText.trim().isEmpty() && validarCasillero(casilleroText)) {
            Controlador c = new Controlador();
//        c.depurarNotificacionesPorTramiteAndCasillero(tramiteText,2321);
            c.depurarNotificacionesPorCasillero(Integer.parseInt(tramiteText));

            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "TERMINADO");

            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "INGRESE UN NÚMERO DE CASILLERO VÁLIDO");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void depurarNotificacionesPorTramiteAndCasillero(ActionEvent ae) {
        FacesMessage msg = null;

        if (tramiteText.trim().isEmpty() || tramiteText.trim().length() < 4 || casilleroText.trim().isEmpty()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "INGRESE UN NÚMERO DE TRÁMITE O CASILLERO CORRECTO");
        } else {
            if (validarCasillero(casilleroText)) {
                Controlador c = new Controlador();
//        c.depurarNotificacionesPorTramiteAndCasillero(tramiteText,2321);
                int flag = c.depurarNotificacionesPorTramiteAndCasillero(tramiteText, Integer.parseInt(casilleroText), "1");
                if (flag == -1) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "NO SE ENCONTRARON DOCUMENTOS DUPLICADOS");
                } else if (flag == 1) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "SE HA DEPURADO SATISFACTORIAMENTE EL TRÁMITE " + tramiteText);
                } else if (flag == 2) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "NO COINCIDEN LOS DOCUMENTOS CON LAS NOTIFICACIONES");
                }

            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "INGRESE UN NÚMERO DE CASILLERO VÁLIDO");
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean validarCasillero(String cas) {
        try {
            Integer.parseInt(cas);
            return true;
        } catch (NumberFormatException ne) {
            return false;
        }
    }

    public void limpiarPaneles(ActionEvent ae) {
        FacesMessage msg = null;
        tramiteText = "";
        limpiarDatos(false);

        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "LIMPIEZA REALIZADA..!");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEditConfig(RowEditEvent<ConfiguracionCC> event) {
        FacesMessage msg = null;
        ConfiguracionCC config = event.getObject();
        String msj = "";
        if (config != null) {
            if (config.getTipo().equals("ACCIÓN DE PERSONAL")) {
                if (config.getNombre().trim().isEmpty()) {
                    msj = "INGRESE LA DESCRIPCIÓN DE LA ACCIÓN DE PERSONAL";
                } else {
                    try {
                        config.getFecha();
                    } catch (Exception ex) {
                        msj = "INGRESE LA FECHA DE LA ACCIÓN DE PERSONAL";
                    }
                }

            } else if (config.getTipo().equals("RESOLUCIÓN")) {
                if (config.getNombre().trim().isEmpty()) {
                    msj = "INGRESE EL NÚMERO DE RESOLUCIÓN EN DESCRIPCIÓN";
                } else {
                    try {
                        config.getFecha();
                    } catch (Exception ex) {
                        msj = "INGRESE LA FECHA DE LA RESOLUCIÓN";
                    }
                }
            } else if (config.getTipo().equals("DELEGADO")) {
                if (config.getNombre().trim().isEmpty()) {
                    msj = "INGRESE EL NOMBRE DEL DELEGADO/A EN DESCRIPCIÓN";
                }
            } else if (config.getTipo().equals("DELEGACIÓN")) {
                if (config.getNombre().trim().isEmpty()) {
                    msj = "INGRESE EL NOMBRE DEL DELEGACIÓN EN DESCRIPCIÓN";
                }
            }

            if (msj.isEmpty()) {
                Controlador c = new Controlador();
                if (c.updateConfiguracionCC(config)) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "EDITADO", "CONFIGURACIÓN EDITADA");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR EDITAR LA CONFIGURACIÓN");
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", msj);
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO EDITAR LA CONFIGURACI");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancelConfig(RowEditEvent<ConfiguracionCC> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "CANCELADO", "PROCESO CANCELADO");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void prepararConfiguracionCC(ActionEvent ae) {
        Controlador c = new Controlador();
        configuraciones = c.getConfiguracionesCC();
        tiposConfiguracionCC = Operaciones.tiposConfiguracionCC();
    }

    public void onAddNewRegistro() {
        FacesMessage msg = null;
        Controlador c = new Controlador();
        ConfiguracionCC conf = new ConfiguracionCC();
        conf.setId(0);
        conf.setActivo(false);
        conf.setNombre("");
        conf.setUsuarioCrea(login.getNombre());
        conf.setTipo("ACCIÓN DE PERSONAL");
        conf.setFechaCreacion(new Date());
        if (c.saveConfiguracionCC(conf)) {
            configuraciones = c.getConfiguracionesCC();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "NUEVO REGISTRO AGREADO, EDÍTELO COMO DESEE");
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO AGREGAR UN NUEVO REGISTRO");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void enableConfiguracion(ConfiguracionCC conf) {
        if (conf != null && conf.getId() != null) {
            if (conf.getTipo() != null && !conf.getTipo().trim().isEmpty()) {
                Controlador c = new Controlador();
                if (conf.getActivo()) {
                    List<ConfiguracionCC> confs = c.getConfiguracionByTipo(conf.getTipo());
                    for (int i = 0; i < confs.size(); i++) {
                        confs.get(i).setActivo(false);
                        c.updateConfiguracionCC(confs.get(i));
                    }
                    conf.setActivo(true);
                    conf.setFecha(new Date());
                    c.updateConfiguracionCC(conf);
                    configuraciones = c.getConfiguracionesCC();
                } else {
                    conf.setActivo(false);
                    conf.setFecha(new Date());
                    c.updateConfiguracionCC(conf);
                }
            } else {
                conf.setActivo(false);
            }
        }
    }

    public void eliminarConfiguracionCC(ActionEvent ae) {
        FacesMessage msg = null;
        ConfiguracionCC conf = (ConfiguracionCC) configDataTable.getRowData();
        if (conf != null && conf.getId() != null) {
            if (conf.getActivo()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "PRIMERO DESACTIVE EL REGISTRO");
            } else {
                Controlador c = new Controlador();
                if (c.removeConfiguracionCC(conf)) {
                    configuraciones = c.getConfiguracionesCC();
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "REGISTRO ELIMINADO CON ÉXITO");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR ELIMINAR EL REGISTRO");
                }
            }

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO REMOVER EL REGISTRO");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @return the tramiteText
     */
    public String getTramiteText() {
        return tramiteText;
    }

    /**
     * @param tramiteText the tramiteText to set
     */
    public void setTramiteText(String tramiteText) {
        this.tramiteText = tramiteText;
    }

    /**
     * @return the tituloTramiteText
     */
    public String getTituloTramiteText() {
        return tituloTramiteText;
    }

    /**
     * @param tituloTramiteText the tituloTramiteText to set
     */
    public void setTituloTramiteText(String tituloTramiteText) {
        this.tituloTramiteText = tituloTramiteText;
    }

    /**
     * @return the tipoTramite
     */
    public String getTipoTramite() {
        return tipoTramite;
    }

    /**
     * @param tipoTramite the tipoTramite to set
     */
    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    /**
     * @return the hallmarkForm
     */
    public HallmarkForm getHallmarkForm() {
        return hallmarkForm;
    }

    /**
     * @param hallmarkForm the hallmarkForm to set
     */
    public void setHallmarkForm(HallmarkForm hallmarkForm) {
        this.hallmarkForm = hallmarkForm;
    }

    /**
     * @return the documentosExpediente
     */
    public List<String> getDocumentosExpediente() {
        return documentosExpediente;
    }

    /**
     * @param documentosExpediente the documentosExpediente to set
     */
    public void setDocumentosExpediente(List<String> documentosExpediente) {
        this.documentosExpediente = documentosExpediente;
    }

    /**
     * @return the alcances
     */
    public List<ScopeFormsAux> getAlcances() {
        return alcances;
    }

    /**
     * @param alcances the alcances to set
     */
    public void setAlcances(List<ScopeFormsAux> alcances) {
        this.alcances = alcances;
    }

    /**
     * @return the conalcance
     */
    public boolean isConalcance() {
        return conalcance;
    }

    /**
     * @param conalcance the conalcance to set
     */
    public void setConalcance(boolean conalcance) {
        this.conalcance = conalcance;
    }

    /**
     * @return the modificaciones
     */
    public List<ModificacionAux> getModificaciones() {
        return modificaciones;
    }

    /**
     * @param modificaciones the modificaciones to set
     */
    public void setModificaciones(List<ModificacionAux> modificaciones) {
        this.modificaciones = modificaciones;
    }

    /**
     * @return the conmodificacion
     */
    public boolean isConmodificacion() {
        return conmodificacion;
    }

    /**
     * @param conmodificacion the conmodificacion to set
     */
    public void setConmodificacion(boolean conmodificacion) {
        this.conmodificacion = conmodificacion;
    }

    /**
     * @return the contutela
     */
    public boolean isContutela() {
        return contutela;
    }

    /**
     * @param contutela the contutela to set
     */
    public void setContutela(boolean contutela) {
        this.contutela = contutela;
    }

    /**
     * @return the conmarca
     */
    public boolean isConmarca() {
        return conmarca;
    }

    /**
     * @param conmarca the conmarca to set
     */
    public void setConmarca(boolean conmarca) {
        this.conmarca = conmarca;
    }

    /**
     * @return the resultadopanel
     */
    public boolean isResultadopanel() {
        return resultadopanel;
    }

    /**
     * @param resultadopanel the resultadopanel to set
     */
    public void setResultadopanel(boolean resultadopanel) {
        this.resultadopanel = resultadopanel;
    }

    /**
     * @return the tutelageForm
     */
    public TutelageForms getTutelageForm() {
        return tutelageForm;
    }

    /**
     * @param tutelageForm the tutelageForm to set
     */
    public void setTutelageForm(TutelageForms tutelageForm) {
        this.tutelageForm = tutelageForm;
    }

    /**
     * @return the tutelaAux
     */
    public TutelageFormsAux getTutelaAux() {
        return tutelaAux;
    }

    /**
     * @param tutelaAux the tutelaAux to set
     */
    public void setTutelaAux(TutelageFormsAux tutelaAux) {
        this.tutelaAux = tutelaAux;
    }

    /**
     * @return the oposiciones
     */
    public List<OppositionFormsAux> getOposiciones() {
        return oposiciones;
    }

    /**
     * @param oposiciones the oposiciones to set
     */
    public void setOposiciones(List<OppositionFormsAux> oposiciones) {
        this.oposiciones = oposiciones;
    }

    /**
     * @return the conoposicion
     */
    public boolean isConoposicion() {
        return conoposicion;
    }

    /**
     * @param conoposicion the conoposicion to set
     */
    public void setConoposicion(boolean conoposicion) {
        this.conoposicion = conoposicion;
    }

    /**
     * @return the nolineas
     */
    public List<NolineaAux> getNolineas() {
        return nolineas;
    }

    /**
     * @param nolineas the nolineas to set
     */
    public void setNolineas(List<NolineaAux> nolineas) {
        this.nolineas = nolineas;
    }

    /**
     * @return the connolinea
     */
    public boolean isConnolinea() {
        return connolinea;
    }

    /**
     * @param connolinea the connolinea to set
     */
    public void setConnolinea(boolean connolinea) {
        this.connolinea = connolinea;
    }

    /**
     * @return the playForm
     */
    public PlayForm getPlayForm() {
        return playForm;
    }

    /**
     * @param playForm the playForm to set
     */
    public void setPlayForm(PlayForm playForm) {
        this.playForm = playForm;
    }

    /**
     * @return the playFormAux
     */
    public PlayFormsAux getPlayFormAux() {
        return playFormAux;
    }

    /**
     * @param playFormAux the playFormAux to set
     */
    public void setPlayFormAux(PlayFormsAux playFormAux) {
        this.playFormAux = playFormAux;
    }

    /**
     * @return the conderechos
     */
    public boolean isConderechos() {
        return conderechos;
    }

    /**
     * @param conderechos the conderechos to set
     */
    public void setConderechos(boolean conderechos) {
        this.conderechos = conderechos;
    }

    /**
     * @return the patentForm
     */
    public PatentForms getPatentForm() {
        return patentForm;
    }

    /**
     * @param patentForm the patentForm to set
     */
    public void setPatentForm(PatentForms patentForm) {
        this.patentForm = patentForm;
    }

    /**
     * @return the conpatente
     */
    public boolean isConpatente() {
        return conpatente;
    }

    /**
     * @param conpatente the conpatente to set
     */
    public void setConpatente(boolean conpatente) {
        this.conpatente = conpatente;
    }

    /**
     * @return the notificaciones
     */
    public List<NotificationsAux> getNotificaciones() {
        return notificaciones;
    }

    /**
     * @param notificaciones the notificaciones to set
     */
    public void setNotificaciones(List<NotificationsAux> notificaciones) {
        this.notificaciones = notificaciones;
    }

    /**
     * @return the connotificacion
     */
    public boolean isConnotificacion() {
        return connotificacion;
    }

    /**
     * @param connotificacion the connotificacion to set
     */
    public void setConnotificacion(boolean connotificacion) {
        this.connotificacion = connotificacion;
    }

    /**
     * @return the documentos
     */
    public List<Documento> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos the documentos to set
     */
    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
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
    public void setLoging(LoginBean login) {
        this.login = login;
    }

    /**
     * @return the owner
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * @return the dialogCasillero
     */
    public String getDialogCasillero() {
        return dialogCasillero;
    }

    /**
     * @param dialogCasillero the dialogCasillero to set
     */
    public void setDialogCasillero(String dialogCasillero) {
        this.dialogCasillero = dialogCasillero;
    }

    /**
     * @return the hijas
     */
    public boolean isHijas() {
        return hijas;
    }

    /**
     * @param hijas the hijas to set
     */
    public void setHijas(boolean hijas) {
        this.hijas = hijas;
    }

    /**
     * @return the cambioCasillero
     */
    public boolean isCambioCasillero() {
        return cambioCasillero;
    }

    /**
     * @param cambioCasillero the cambioCasillero to set
     */
    public void setCambioCasillero(boolean cambioCasillero) {
        this.cambioCasillero = cambioCasillero;
    }

    /**
     * @return the tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * @param tramite the tramite to set
     */
    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the cambiarCasillero
     */
    public CambioCasillero getCambiarCasillero() {
        return cambiarCasillero;
    }

    /**
     * @param cambiarCasillero the cambiarCasillero to set
     */
    public void setCambiarCasillero(CambioCasillero cambiarCasillero) {
        this.cambiarCasillero = cambiarCasillero;
    }

    /**
     * @return the owners
     */
    public List<Owner> getOwners() {
        return owners;
    }

    /**
     * @param owners the owners to set
     */
    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    /**
     * @return the criterioOwner
     */
    public String getCriterioOwner() {
        return criterioOwner;
    }

    /**
     * @param criterioOwner the criterioOwner to set
     */
    public void setCriterioOwner(String criterioOwner) {
        this.criterioOwner = criterioOwner;
    }

    /**
     * @return the confirmChangeMessage
     */
    public String getConfirmChangeMessage() {
        return confirmChangeMessage;
    }

    /**
     * @param confirmChangeMessage the confirmChangeMessage to set
     */
    public void setConfirmChangeMessage(String confirmChangeMessage) {
        this.confirmChangeMessage = confirmChangeMessage;
    }

    /**
     * @return the ownersFiltrados
     */
    public List<Owner> getOwnersFiltrados() {
        return ownersFiltrados;
    }

    /**
     * @param ownersFiltrados the ownersFiltrados to set
     */
    public void setOwnersFiltrados(List<Owner> ownersFiltrados) {
        this.ownersFiltrados = ownersFiltrados;
    }

    /**
     * @return the ownerDataTable
     */
    public UIData getOwnerDataTable() {
        return ownerDataTable;
    }

    /**
     * @param ownerDataTable the ownerDataTable to set
     */
    public void setOwnerDataTable(UIData ownerDataTable) {
        this.ownerDataTable = ownerDataTable;
    }

    /**
     * @return the owneraux
     */
    public Owner getOwneraux() {
        return owneraux;
    }

    /**
     * @param owneraux the owneraux to set
     */
    public void setOwneraux(Owner owneraux) {
        this.owneraux = owneraux;
    }

    /**
     * @return the configuraciones
     */
    public List<ConfiguracionCC> getConfiguraciones() {
        return configuraciones;
    }

    /**
     * @param configuraciones the configuraciones to set
     */
    public void setConfiguraciones(List<ConfiguracionCC> configuraciones) {
        this.configuraciones = configuraciones;
    }

    /**
     * @return the configDataTable
     */
    public UIData getConfigDataTable() {
        return configDataTable;
    }

    /**
     * @param configDataTable the configDataTable to set
     */
    public void setConfigDataTable(UIData configDataTable) {
        this.configDataTable = configDataTable;
    }

    /**
     * @return the tiposConfiguracionCC
     */
    public List<String> getTiposConfiguracionCC() {
        return tiposConfiguracionCC;
    }

    /**
     * @param tiposConfiguracionCC the tiposConfiguracionCC to set
     */
    public void setTiposConfiguracionCC(List<String> tiposConfiguracionCC) {
        this.tiposConfiguracionCC = tiposConfiguracionCC;
    }

    /**
     * @return the notaprovidencia
     */
    public String getNotaprovidencia() {
        return notaprovidencia;
    }

    /**
     * @param notaprovidencia the notaprovidencia to set
     */
    public void setNotaprovidencia(String notaprovidencia) {
        this.notaprovidencia = notaprovidencia;
    }

    /**
     * @return the cambiocas
     */
    public boolean isCambiocas() {
        return cambiocas;
    }

    /**
     * @param cambiocas the cambiocas to set
     */
    public void setCambiocas(boolean cambiocas) {
        this.cambiocas = cambiocas;
    }

    /**
     * @return the usuarioCambioCasillero
     */
    public Usuario getUsuarioCambioCasillero() {
        return usuarioCambioCasillero;
    }

    /**
     * @param usuarioCambioCasillero the usuarioCambioCasillero to set
     */
    public void setUsuarioCambioCasillero(Usuario usuarioCambioCasillero) {
        this.usuarioCambioCasillero = usuarioCambioCasillero;
    }

    /**
     * @return the casilleroText
     */
    public String getCasilleroText() {
        return casilleroText;
    }

    /**
     * @param casilleroText the casilleroText to set
     */
    public void setCasilleroText(String casilleroText) {
        this.casilleroText = casilleroText;
    }

    /**
     * @return the depurar
     */
    public boolean isDepurar() {
        return depurar;
    }

    /**
     * @param depurar the depurar to set
     */
    public void setDepurar(boolean depurar) {
        this.depurar = depurar;
    }

    /**
     * @return the ppdiSolicitudSignoDistintivo
     */
    public PpdiSolicitudSignoDistintivo getPpdiSolicitudSignoDistintivo() {
        return ppdiSolicitudSignoDistintivo;
    }

    /**
     * @param ppdiSolicitudSignoDistintivo the ppdiSolicitudSignoDistintivo to
     * set
     */
    public void setPpdiSolicitudSignoDistintivo(PpdiSolicitudSignoDistintivo ppdiSolicitudSignoDistintivo) {
        this.ppdiSolicitudSignoDistintivo = ppdiSolicitudSignoDistintivo;
    }

    /**
     * @return the breederForm
     */
    public BreederForms getBreederForm() {
        return breederForm;
    }

    /**
     * @param breederForm the breederForm to set
     */
    public void setBreederForm(BreederForms breederForm) {
        this.breederForm = breederForm;
    }

    /**
     * @return the conbreeder
     */
    public boolean isConbreeder() {
        return conbreeder;
    }

    /**
     * @param conbreeder the conbreeder to set
     */
    public void setConbreeder(boolean conbreeder) {
        this.conbreeder = conbreeder;
    }
}
