/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.postgres.polaris;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author michael
 */
@Entity
@Table(name = "historial_patente", schema = "senadi_polaris")
public class HistorialPatente implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historial_patente_seq")
    @SequenceGenerator(name = "historial_patente_seq", sequenceName = "historial_patente_id_seq", schema = "senadi_polaris", allocationSize = 1)
    private Integer id;

    @Column(name = "tramite_senadi")
    private String tramiteSenadi;
    
    @Column(name = "fecha_envio")
    private Timestamp fechaEnvio;
    
    @Column(name = "tramite_ipas")
    private String tramiteIpas;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "observacion")
    private String observacion;

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
     * @return the tramiteSenadi
     */
    public String getTramiteSenadi() {
        return tramiteSenadi;
    }

    /**
     * @param tramiteSenadi the tramiteSenadi to set
     */
    public void setTramiteSenadi(String tramiteSenadi) {
        this.tramiteSenadi = tramiteSenadi;
    }

    /**
     * @return the tramiteIpas
     */
    public String getTramiteIpas() {
        return tramiteIpas;
    }

    /**
     * @param tramiteIpas the tramiteIpas to set
     */
    public void setTramiteIpas(String tramiteIpas) {
        this.tramiteIpas = tramiteIpas;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the fechaEnvio
     */
    public Timestamp getFechaEnvio() {
        return fechaEnvio;
    }

    /**
     * @param fechaEnvio the fechaEnvio to set
     */
    public void setFechaEnvio(Timestamp fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    
    
}
