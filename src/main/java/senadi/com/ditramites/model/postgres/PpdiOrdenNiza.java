/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.postgres;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author micharesp
 */
@Entity
@Table(name = "ppdi_orden_niza", schema = "iepi_procesos")
public class PpdiOrdenNiza implements Serializable{
    
    @Id
    @Column(name = "codigo_orden_niza")
    private String codigoOrdenNiza;
    
    @Column(name = "nombre_orden_niza")
    private String nombreOrdenNiza;
    
    @Column(name = "codigo_clasificacion_niza")
    private Integer codigoClasificacionNiza;
    
    @OneToMany(mappedBy="ppdiOrdenNiza" )
    private List<PpdiSolicitudOrdenNiza> ppdiSolicitudOrdenesNiza;
    
    public PpdiOrdenNiza(){
        ppdiSolicitudOrdenesNiza = new ArrayList<>();
    }

    /**
     * @return the ppdiSolicitudOrdenesNiza
     */
    public List<PpdiSolicitudOrdenNiza> getPpdiSolicitudOrdenesNiza() {
        return ppdiSolicitudOrdenesNiza;
    }

    /**
     * @param ppdiSolicitudOrdenesNiza the ppdiSolicitudOrdenesNiza to set
     */
    public void setPpdiSolicitudOrdenesNiza(List<PpdiSolicitudOrdenNiza> ppdiSolicitudOrdenesNiza) {
        this.ppdiSolicitudOrdenesNiza = ppdiSolicitudOrdenesNiza;
    }

    /**
     * @return the codigoOrdenNiza
     */
    public String getCodigoOrdenNiza() {
        return codigoOrdenNiza;
    }

    /**
     * @param codigoOrdenNiza the codigoOrdenNiza to set
     */
    public void setCodigoOrdenNiza(String codigoOrdenNiza) {
        this.codigoOrdenNiza = codigoOrdenNiza;
    }

    /**
     * @return the nombreOrdenNiza
     */
    public String getNombreOrdenNiza() {
        return nombreOrdenNiza;
    }

    /**
     * @param nombreOrdenNiza the nombreOrdenNiza to set
     */
    public void setNombreOrdenNiza(String nombreOrdenNiza) {
        this.nombreOrdenNiza = nombreOrdenNiza;
    }

    /**
     * @return the codigoClasificacionNiza
     */
    public Integer getCodigoClasificacionNiza() {
        return codigoClasificacionNiza;
    }

    /**
     * @param codigoClasificacionNiza the codigoClasificacionNiza to set
     */
    public void setCodigoClasificacionNiza(Integer codigoClasificacionNiza) {
        this.codigoClasificacionNiza = codigoClasificacionNiza;
    }

    
}
