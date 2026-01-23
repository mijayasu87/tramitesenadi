/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senadi.com.ditramites.model.mod;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import senadi.com.ditramites.util.Operaciones;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "transferencia")
public class Transferencia implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "solicitud")
    private String solicitud;
    
    @Column(name = "fecha_presentacion")    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPresentacion;
    
    @Column(name = "certificado")
    private Integer certificado;    
    
    @Column(name = "fecha_certificado")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCertificado;
    
    @Column(name = "registro")
    private String registro;
    
    @Column(name = "fecha_registro")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRegistro;
    
    @Column(name = "denominacion")
    private String denominacion;
    
    @Column(name = "signo")
    private String signo;
    
    @Column(name = "titular_anterior")
    private String titularAnterior;
    
    @Column(name = "titular_actual")
    private String titularActual;
    
    @Column(name = "apoderado_representante")
    private String apoderadoRepresentanteLegal;
    
    @Column(name = "notificacion")
    private String notificacion;
    
    @Column(name = "fecha_notificacion")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaNotificacion;
    
    @Column(name = "ro")
    private String ro;
    
    @Column(name = "casillero_senadi")
    private String casilleroSenadi;
    
    @Column(name = "casillero_judicial")
    private String casilleroJudicial;
    
    @Column(name = "responsable")
    private String responsable;
    
    @Column(name = "identificacion")
    private String identificacion;   
               
    @Column(name = "email")
    private String email;    

    @Column(name = "domicilio_titular_actual")
    private String domicilioTitularActual;
    
    @Column(name = "fecha_elabora_notificacion")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaElaboraNotificacion;
    
    @Column(name = "comprobante")
    private String comprobante;
    
    @Transient
    private String fechaPresentacionText;
    
    @Transient
    private String fechaCertificadoText;
    
    @Transient
    private String fechaRegistroText;
    
    @Transient
    private String rutaExpediente;
    
    @Transient
    private Integer idRenewalForm;
    
    @Column(name = "certificado_emitido")
    private boolean certificadoEmitido;
    
    @Column(name = "notificacion_emitida")
    private boolean notificacionEmitida;
    
    @Column(name = "cancelado")
    private String cancelado;

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
     * @return the solicitud
     */
    public String getSolicitud() {
        return solicitud;
    }

    /**
     * @param solicitud the solicitud to set
     */
    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    /**
     * @return the fechaPresentacion
     */
    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    /**
     * @param fechaPresentacion the fechaPresentacion to set
     */
    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }
    
    /**
     * @return the fechaCertificado
     */
    public Date getFechaCertificado() {
        return fechaCertificado;
    }

    /**
     * @param fechaCertificado the fechaCertificado to set
     */
    public void setFechaCertificado(Date fechaCertificado) {
        this.fechaCertificado = fechaCertificado;
    }

    /**
     * @return the registro
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * @param registro the registro to set
     */
    public void setRegistro(String registro) {
        this.registro = registro;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the denominacion
     */
    public String getDenominacion() {
        return denominacion;
    }

    /**
     * @param denominacion the denominacion to set
     */
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    /**
     * @return the signo
     */
    public String getSigno() {
        return signo;
    }

    /**
     * @param signo the signo to set
     */
    public void setSigno(String signo) {
        this.signo = signo;
    }

    /**
     * @return the titularAnterior
     */
    public String getTitularAnterior() {
        return titularAnterior;
    }

    /**
     * @param titularAnterior the titularAnterior to set
     */
    public void setTitularAnterior(String titularAnterior) {
        this.titularAnterior = titularAnterior;
    }

    /**
     * @return the titularActual
     */
    public String getTitularActual() {
        return titularActual;
    }

    /**
     * @param titularActual the titularActual to set
     */
    public void setTitularActual(String titularActual) {
        this.titularActual = titularActual;
    }

    /**
     * @return the apoderadoRepresentanteLegal
     */
    public String getApoderadoRepresentanteLegal() {
        return apoderadoRepresentanteLegal;
    }

    /**
     * @param apoderadoRepresentanteLegal the apoderadoRepresentanteLegal to set
     */
    public void setApoderadoRepresentanteLegal(String apoderadoRepresentanteLegal) {
        this.apoderadoRepresentanteLegal = apoderadoRepresentanteLegal;
    }

    /**
     * @return the fechaNotificacion
     */
    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    /**
     * @param fechaNotificacion the fechaNotificacion to set
     */
    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    /**
     * @return the casilleroSenadi
     */
    public String getCasilleroSenadi() {
        return casilleroSenadi;
    }

    /**
     * @param casilleroSenadi the casilleroSenadi to set
     */
    public void setCasilleroSenadi(String casilleroSenadi) {
        this.casilleroSenadi = casilleroSenadi;
    }

    /**
     * @return the casilleroJudicial
     */
    public String getCasilleroJudicial() {
        return casilleroJudicial;
    }

    /**
     * @param casilleroJudicial the casilleroJudicial to set
     */
    public void setCasilleroJudicial(String casilleroJudicial) {
        this.casilleroJudicial = casilleroJudicial;
    }  

    /**
     * @return the ro
     */
    public String getRo() {
        return ro;
    }

    /**
     * @param ro the ro to set
     */
    public void setRo(String ro) {
        this.ro = ro;
    }

    /**
     * @return the responsable
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the fechaPresentacionText
     */
    public String getFechaPresentacionText() {
        return fechaPresentacion.toString();
    }

    /**
     * @param fechaPresentacionText the fechaPresentacionText to set
     */
    public void setFechaPresentacionText(String fechaPresentacionText) {
        this.fechaPresentacionText = fechaPresentacionText;
    }

    /**
     * @return the fechaCertificadoText
     */
    public String getFechaCertificadoText() {
        return Operaciones.formatDate(fechaCertificado);
    }

    /**
     * @param fechaCertificadoText the fechaCertificadoText to set
     */
    public void setFechaCertificadoText(String fechaCertificadoText) {
        this.fechaCertificadoText = fechaCertificadoText;
    }

    /**
     * @return the fechaRegistroText
     */
    public String getFechaRegistroText() {
        return Operaciones.formatDate(fechaRegistro);
    }

    /**
     * @param fechaRegistroText the fechaRegistroText to set
     */
    public void setFechaRegistroText(String fechaRegistroText) {
        this.fechaRegistroText = fechaRegistroText;
    }

    @Override
    public String toString(){
        return fechaPresentacion+" - "+fechaRegistro;
    }

    /**
     * @return the certificado
     */
    public Integer getCertificado() {
        return certificado;
    }

    /**
     * @param certificado the certificado to set
     */
    public void setCertificado(Integer certificado) {
        this.certificado = certificado;
    }

    /**
     * @return the notificacion
     */
    public String getNotificacion() {
        return notificacion;
    }

    /**
     * @param notificacion the notificacion to set
     */
    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
    }

    /**
     * @return the domicilioTitularActual
     */
    public String getDomicilioTitularActual() {
        return domicilioTitularActual;
    }

    /**
     * @param domicilioTitularActual the domicilioTitularActual to set
     */
    public void setDomicilioTitularActual(String domicilioTitularActual) {
        this.domicilioTitularActual = domicilioTitularActual;
    }

    /**
     * @return the fechaElaboraNotificacion
     */
    public Date getFechaElaboraNotificacion() {
        return fechaElaboraNotificacion;
    }

    /**
     * @param fechaElaboraNotificacion the fechaElaboraNotificacion to set
     */
    public void setFechaElaboraNotificacion(Date fechaElaboraNotificacion) {
        this.fechaElaboraNotificacion = fechaElaboraNotificacion;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the rutaExpediente
     */
    public String getRutaExpediente() {
        return "https://registro.propiedadintelectual.gob.ec/solicitudes/media/files/renewal_forms/"+getIdRenewalForm()+"/";
//        return rutaExpediente;
    }

    /**
     * @param rutaExpediente the rutaExpediente to set
     */
    public void setRutaExpediente(String rutaExpediente) {
        this.rutaExpediente = rutaExpediente;
    }

    /**
     * @return the idRenewalForm
     */
    public Integer getIdRenewalForm() {
        return idRenewalForm;
    }

    /**
     * @param idRenewalForm the idRenewalForm to set
     */
    public void setIdRenewalForm(Integer idRenewalForm) {
        this.idRenewalForm = idRenewalForm;
    }

    /**
     * @return the comprobante
     */
    public String getComprobante() {
        return comprobante;
    }

    /**
     * @param comprobante the comprobante to set
     */
    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    /**
     * @return the certificadoEmitido
     */
    public boolean isCertificadoEmitido() {
        return certificadoEmitido;
    }

    /**
     * @param certificadoEmitido the certificadoEmitido to set
     */
    public void setCertificadoEmitido(boolean certificadoEmitido) {
        this.certificadoEmitido = certificadoEmitido;
    }

    /**
     * @return the notificacionEmitida
     */
    public boolean isNotificacionEmitida() {
        return notificacionEmitida;
    }

    /**
     * @param notificacionEmitida the notificacionEmitida to set
     */
    public void setNotificacionEmitida(boolean notificacionEmitida) {
        this.notificacionEmitida = notificacionEmitida;
    }

    /**
     * @return the cancelado
     */
    public String getCancelado() {
        return cancelado;
    }

    /**
     * @param cancelado the cancelado to set
     */
    public void setCancelado(String cancelado) {
        this.cancelado = cancelado;
    }

}
