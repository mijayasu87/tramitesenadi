/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.procesos;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.model.postgres.PpdiSolicitudPatente;

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
            @SuppressWarnings("unchecked")
            List<PpdiSolicitudPatente> solicitudes = query.getResultList();
            return solicitudes;
        } finally {
            this.getEntityManager().close();
        }

    }

    public PpdiSolicitudPatente getPpdiSolicitudPatenteByTramite(String tramite) {
        try {
            Query query = this.getEntityManager().createQuery("Select p from PpdiSolicitudPatente p where p.numeroTramite = :tramite");
            query.setParameter("tramite", tramite);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<PpdiSolicitudPatente> solicitudes = query.getResultList();
            if (solicitudes.isEmpty()) {
                return new PpdiSolicitudPatente();
            } else {
                return solicitudes.get(0);
            }
        } finally {
            this.getEntityManager().close();
        }
    }        
    
    public PpdiSolicitudPatente getPpdiSolicitudPatenteByCodigoSolicitud(Integer codigoSolicitud) {
        try {
            Query query = this.getEntityManager().createQuery("Select p from PpdiSolicitudPatente p where p.codigoSolicitudPatente = :codigo");
            query.setParameter("codigo", codigoSolicitud);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<PpdiSolicitudPatente> solicitudes = query.getResultList();
            if (solicitudes.isEmpty()) {
                return new PpdiSolicitudPatente();
            } else {
                return solicitudes.get(0);
            }
        } finally {
            this.getEntityManager().close();
        }

    }
    
}
