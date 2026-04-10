/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.procesos;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.model.postgres.PpdiSolicitudPatente;
import senadi.com.ditramites.model.postgres.PpdiSolicitudSignoDistintivo;

/**
 *
 * @author micharesp
 */
public class PpdiSolicitudPatenteDAO extends DAOAbstract<PpdiSolicitudPatente> {

    public PpdiSolicitudPatenteDAO(PpdiSolicitudPatente t) {
        super(t);
    }

    @Override
    public List<PpdiSolicitudPatente> buscarTodos() {
        try {
            Query query = this.getEntityManager().createQuery("Select p from PpdiSolicitudPatente p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }

    }

    public PpdiSolicitudSignoDistintivo getPpdiSolicitudPatenteByTramite(String tramite) {
        try {
            Query query = this.getEntityManager().createQuery("Select p from PpdiSolicitudPatente p where p.numeroTramite = :tramite");
            query.setParameter("tramite", tramite);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PpdiSolicitudSignoDistintivo> signos = query.getResultList();
            if (signos.isEmpty()) {
                return new PpdiSolicitudSignoDistintivo();
            } else {
                return signos.get(0);
            }
        } finally {
            this.getEntityManager().close();
        }
    }        
    
    public PpdiSolicitudSignoDistintivo getPpdiSolicitudPatenteByCodigoSolicitud(Integer codigoSolicitud) {
        try {
            Query query = this.getEntityManager().createQuery("Select p from PpdiSolicitudPatente p where p.codigoSolicitudPatente = :codigo");
            query.setParameter("codigo", codigoSolicitud);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PpdiSolicitudSignoDistintivo> signos = query.getResultList();
            if (signos.isEmpty()) {
                return new PpdiSolicitudSignoDistintivo();
            } else {
                return signos.get(0);
            }
        } finally {
            this.getEntityManager().close();
        }

    }
    
}
