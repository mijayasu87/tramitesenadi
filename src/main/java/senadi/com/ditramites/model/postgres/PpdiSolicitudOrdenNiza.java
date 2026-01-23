/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.postgres;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author micharesp
 */
@Entity
@Table(name = "ppdi_solicitud_orden_niza", schema = "iepi_procesos")
public class PpdiSolicitudOrdenNiza implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "codigo_orden_niza")
    private PpdiOrdenNiza ppdiOrdenNiza;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "codigo_solicitud_signo")
    private PpdiSolicitudSignoDistintivo ppdiSolicitudSignoDistintivo;

    /**
     * @return the ppdiOrdenNiza
     */
    public PpdiOrdenNiza getPpdiOrdenNiza() {
        return ppdiOrdenNiza;
    }

    /**
     * @param ppdiOrdenNiza the ppdiOrdenNiza to set
     */
    public void setPpdiOrdenNiza(PpdiOrdenNiza ppdiOrdenNiza) {
        this.ppdiOrdenNiza = ppdiOrdenNiza;
    }

    /**
     * @return the ppdiSolicitudSignoDistintivo
     */
    public PpdiSolicitudSignoDistintivo getPpdiSolicitudSignoDistintivo() {
        return ppdiSolicitudSignoDistintivo;
    }

    /**
     * @param ppdiSolicitudSignoDistintivo the ppdiSolicitudSignoDistintivo to set
     */
    public void setPpdiSolicitudSignoDistintivo(PpdiSolicitudSignoDistintivo ppdiSolicitudSignoDistintivo) {
        this.ppdiSolicitudSignoDistintivo = ppdiSolicitudSignoDistintivo;
    }
    
}
