/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.procesos;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.model.postgres.PpdiSolicitudSignoDistintivo;
import senadi.com.ditramites.model.postgres.PpdiTituloSignoDistintivo;

/**
 *
 * @author micharesp
 */
public class PpdiSolicitudSignoDAO extends DAOAbstract<PpdiSolicitudSignoDistintivo> {

    public PpdiSolicitudSignoDAO(PpdiSolicitudSignoDistintivo t) {
        super(t);
    }

    @Override
    public List<PpdiSolicitudSignoDistintivo> buscarTodos() {
        try {
            Query query = this.getEntityManager().createQuery("Select p from PpdiSolicitudSignoDistintivo p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }

    }

    public PpdiSolicitudSignoDistintivo getPpdiSolicitudSignoDistintivoByTramite(String tramite) {
        try {
            Query query = this.getEntityManager().createQuery("Select p from PpdiSolicitudSignoDistintivo p where p.numeroTramite = :tramite");
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

    public PpdiTituloSignoDistintivo getPpdiTituloSignoDistintivoByCodigoSolicitudSigno(int codigoSolicitudSigno) {
        try {
            Query query = this.getEntityManager().createQuery("Select p from PpdiTituloSignoDistintivo p where p.codigoSolicitudSigno = :codigo");
            query.setParameter("codigo", codigoSolicitudSigno);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PpdiTituloSignoDistintivo> tits = query.getResultList();
            if (tits.isEmpty()) {
                return new PpdiTituloSignoDistintivo();
            } else {
                return tits.get(0);
            }
        } finally {
            this.getEntityManager().close();
        }
    }
}
