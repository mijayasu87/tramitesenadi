/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senadi.com.ditramites.model.postgres;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "ppdi_persona_solicitud_signo_distintivo", schema = "iepi_procesos")
public class PpdiPersonaSolicitudSignoDistintivo implements Serializable{
    
//    @Basic(optional = false)
//    @Column(name = "tipo_persona")
//    private String tipo_persona;
    
        @Id
    @Column(name = "tipo_persona")   
    private String tipo_persona;
    
    @Column(name = "direccion_titulo")
    private String direccion_titulo;
    
    @Column(name = "nombre_titulo")
    private String nombre_titulo;     

    /**
     * @return the direccion_titulo
     */
    public String getDireccion_titulo() {
        return direccion_titulo;
    }

    /**
     * @param direccion_titulo the direccion_titulo to set
     */
    public void setDireccion_titulo(String direccion_titulo) {
        this.direccion_titulo = direccion_titulo;
    }

    /**
     * @return the nombre_titulo
     */
    public String getNombre_titulo() {
        return nombre_titulo;
    }

    /**
     * @param nombre_titulo the nombre_titulo to set
     */
    public void setNombre_titulo(String nombre_titulo) {
        this.nombre_titulo = nombre_titulo;
    }


    /**
     * @return the tipo_persona
     */
    public String getTipo_persona() {
        return tipo_persona;
    }

    /**
     * @param tipo_persona the tipo_persona to set
     */
    public void setTipo_persona(String tipo_persona) {
        this.tipo_persona = tipo_persona;
    }


}
