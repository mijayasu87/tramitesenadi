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

/**
 *
 * @author micharesp
 */
@Entity
@Table(name = "patente", schema = "iepi_procesos")
public class PpdiSolicitudPatente implements Serializable{
    
    @Id
    @Column(name = "id")
    private Integer codigoSolicitudPatente;
    @Column(name = "codigo_tipo_derecho")
    private Integer codigoTipoDerecho;
    @Column(name = "numero_expediente_patente")
    private String numeroExpediente;
    
    @Column(name = "numero_tramite_patente")
    private String numeroTramite;
    
    @Column(name = "fecha_presentacion")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPresentacion;
    
    @Column(name = "titulo")
    private String titulo;

    /**
     * @return the codigoSolicitudPatente
     */
    public Integer getCodigoSolicitudPatente() {
        return codigoSolicitudPatente;
    }

    /**
     * @param codigoSolicitudPatente the codigoSolicitudPatente to set
     */
    public void setCodigoSolicitudPatente(Integer codigoSolicitudPatente) {
        this.codigoSolicitudPatente = codigoSolicitudPatente;
    }

    /**
     * @return the codigoTipoDerecho
     */
    public Integer getCodigoTipoDerecho() {
        return codigoTipoDerecho;
    }

    /**
     * @param codigoTipoDerecho the codigoTipoDerecho to set
     */
    public void setCodigoTipoDerecho(Integer codigoTipoDerecho) {
        this.codigoTipoDerecho = codigoTipoDerecho;
    }

    /**
     * @return the numeroExpediente
     */
    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    /**
     * @param numeroExpediente the numeroExpediente to set
     */
    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    /**
     * @return the numeroTramite
     */
    public String getNumeroTramite() {
        return numeroTramite;
    }

    /**
     * @param numeroTramite the numeroTramite to set
     */
    public void setNumeroTramite(String numeroTramite) {
        this.numeroTramite = numeroTramite;
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
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
