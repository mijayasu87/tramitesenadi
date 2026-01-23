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
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "ppdi_persona", schema = "iepi_procesos")
public class PpdiPersona implements Serializable {
    
    @Id
    @Column(name = "codigo_persona")
    private Integer codigo_persona;
    
    @Column(name = "numero_identificacion")
    private String numero_identificacion;
    
    @Column(name = "nombre_persona")
    private String nombre_persona;
    
    @Column(name = "direccion_persona")
    private String direccion_persona;
    
    @Column(name = "codigo_pais")
    private Integer codigo_pais;
    
    @Column(name = "codigo_ciudad")
    private Integer codigo_ciudad;
    
    @Column(name = "correo_electronico")
    private String correo_electronico;
    
    @Column(name = "numero_telefonico")
    private String numero_telefonico;
    
    @Column(name = "tipo_identificacion")
    private String tipo_identificacion;
    
    @Column(name = "bonita")
    private String bonita;
    
    /**
     * @return the codigo_persona
     */
    public Integer getCodigo_persona() {
        return codigo_persona;
    }

    /**
     * @param codigo_persona the codigo_persona to set
     */
    public void setCodigo_persona(Integer codigo_persona) {
        this.codigo_persona = codigo_persona;
    }

    /**
     * @return the numero_identificacion
     */
    public String getNumero_identificacion() {
        return numero_identificacion;
    }

    /**
     * @param numero_identificacion the numero_identificacion to set
     */
    public void setNumero_identificacion(String numero_identificacion) {
        this.numero_identificacion = numero_identificacion;
    }

    /**
     * @return the nombre_persona
     */
    public String getNombre_persona() {
        return nombre_persona;
    }

    /**
     * @param nombre_persona the nombre_persona to set
     */
    public void setNombre_persona(String nombre_persona) {
        this.nombre_persona = nombre_persona;
    }

    /**
     * @return the direccion_persona
     */
    public String getDireccion_persona() {
        return direccion_persona;
    }

    /**
     * @param direccion_persona the direccion_persona to set
     */
    public void setDireccion_persona(String direccion_persona) {
        this.direccion_persona = direccion_persona;
    }

    /**
     * @return the codigo_pais
     */
    public Integer getCodigo_pais() {
        return codigo_pais;
    }

    /**
     * @param codigo_pais the codigo_pais to set
     */
    public void setCodigo_pais(Integer codigo_pais) {
        this.codigo_pais = codigo_pais;
    }
    
    @Override
    public String toString(){
        return getNombre_persona()+", "+getNumero_identificacion();
    }

    /**
     * @return the codigo_ciudad
     */
    public Integer getCodigo_ciudad() {
        return codigo_ciudad;
    }

    /**
     * @param codigo_ciudad the codigo_ciudad to set
     */
    public void setCodigo_ciudad(Integer codigo_ciudad) {
        this.codigo_ciudad = codigo_ciudad;
    }

    /**
     * @return the correo_electronico
     */
    public String getCorreo_electronico() {
        return correo_electronico;
    }

    /**
     * @param correo_electronico the correo_electronico to set
     */
    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    /**
     * @return the numero_telefonico
     */
    public String getNumero_telefonico() {
        return numero_telefonico;
    }

    /**
     * @param numero_telefonico the numero_telefonico to set
     */
    public void setNumero_telefonico(String numero_telefonico) {
        this.numero_telefonico = numero_telefonico;
    }

    /**
     * @return the tipo_identificacion
     */
    public String getTipo_identificacion() {
        return tipo_identificacion;
    }

    /**
     * @param tipo_identificacion the tipo_identificacion to set
     */
    public void setTipo_identificacion(String tipo_identificacion) {
        this.tipo_identificacion = tipo_identificacion;
    }

    /**
     * @return the bonita
     */
    public String getBonita() {
        return bonita;
    }

    /**
     * @param bonita the bonita to set
     */
    public void setBonita(String bonita) {
        this.bonita = bonita;
    }
}
