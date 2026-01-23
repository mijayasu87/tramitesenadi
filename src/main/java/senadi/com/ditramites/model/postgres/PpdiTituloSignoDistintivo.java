/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.postgres;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author micharesp
 */
@Entity
@Table(name = "ppdi_titulo_signo_distintivo", schema = "iepi_procesos")
public class PpdiTituloSignoDistintivo implements Serializable{
    
    @Id
    @Column(name = "codigo_titulo_signo_distintivo")
    private Integer codigoTituloSignoDistintivo;
    
    @Column(name = "codigo_solicitud_signo")
    private Integer codigoSolicitudSigno;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_emision_documento")
    private Date fechaEmisionDocumento;
    
    @Column(name = "usuario_elabora")
    private String usuarioElabora;
    
    @Column(name = "usuario_aprueba")
    private String usuarioAprueba;
    
    @Column(name = "numero_titulo")
    private String numeroTitulo;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_vencimiento_titulo")
    private Date fechaVencimientoTitulo;
    
    @Column(name = "titular")
    private String titular;

    /**
     * @return the codigoTituloSignoDistintivo
     */
    public Integer getCodigoTituloSignoDistintivo() {
        return codigoTituloSignoDistintivo;
    }

    /**
     * @param codigoTituloSignoDistintivo the codigoTituloSignoDistintivo to set
     */
    public void setCodigoTituloSignoDistintivo(Integer codigoTituloSignoDistintivo) {
        this.codigoTituloSignoDistintivo = codigoTituloSignoDistintivo;
    }

    /**
     * @return the codigoSolicitudSigno
     */
    public Integer getCodigoSolicitudSigno() {
        return codigoSolicitudSigno;
    }

    /**
     * @param codigoSolicitudSigno the codigoSolicitudSigno to set
     */
    public void setCodigoSolicitudSigno(Integer codigoSolicitudSigno) {
        this.codigoSolicitudSigno = codigoSolicitudSigno;
    }

    /**
     * @return the fechaEmisionDocumento
     */
    public Date getFechaEmisionDocumento() {
        return fechaEmisionDocumento;
    }

    /**
     * @param fechaEmisionDocumento the fechaEmisionDocumento to set
     */
    public void setFechaEmisionDocumento(Date fechaEmisionDocumento) {
        this.fechaEmisionDocumento = fechaEmisionDocumento;
    }

    /**
     * @return the usuarioElabora
     */
    public String getUsuarioElabora() {
        return usuarioElabora;
    }

    /**
     * @param usuarioElabora the usuarioElabora to set
     */
    public void setUsuarioElabora(String usuarioElabora) {
        this.usuarioElabora = usuarioElabora;
    }

    /**
     * @return the usuarioAprueba
     */
    public String getUsuarioAprueba() {
        return usuarioAprueba;
    }

    /**
     * @param usuarioAprueba the usuarioAprueba to set
     */
    public void setUsuarioAprueba(String usuarioAprueba) {
        this.usuarioAprueba = usuarioAprueba;
    }

    /**
     * @return the numeroTitulo
     */
    public String getNumeroTitulo() {
        return numeroTitulo;
    }

    /**
     * @param numeroTitulo the numeroTitulo to set
     */
    public void setNumeroTitulo(String numeroTitulo) {
        this.numeroTitulo = numeroTitulo;
    }

    /**
     * @return the fechaVencimientoTitulo
     */
    public Date getFechaVencimientoTitulo() {
        return fechaVencimientoTitulo;
    }

    /**
     * @param fechaVencimientoTitulo the fechaVencimientoTitulo to set
     */
    public void setFechaVencimientoTitulo(Date fechaVencimientoTitulo) {
        this.fechaVencimientoTitulo = fechaVencimientoTitulo;
    }

    /**
     * @return the titular
     */
    public String getTitular() {
        return titular;
    }

    /**
     * @param titular the titular to set
     */
    public void setTitular(String titular) {
        this.titular = titular;
    }
}
