/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.mod;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.model.mod.CambioCasillero;

/**
 *
 * @author micharesp
 */
public class CambioCasilleroDAO extends DAOAbstractMod<CambioCasillero> {

    public CambioCasilleroDAO(CambioCasillero c) {
        super(c);
    }

    @Override
    public List<CambioCasillero> buscarTodos() {
        try {
            Query query = this.getEntityManager().createQuery("Select c from CambioCasillero c");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<CambioCasillero> cambios = query.getResultList();
            return cambios;
        } finally {
            this.getEntityManager().close();
        }

    }

    public List<CambioCasillero> getCambioCasilleroByEstado(String estado) {
        try {
            Query query = this.getEntityManager().createQuery("Select c from CambioCasillero c where c.estado = :estado and c.fuente in ('SIGNOS DISTINTIVOS','PATENTES','OPOSICION')");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("estado", estado);
            @SuppressWarnings("unchecked")
            List<CambioCasillero> cambios = query.getResultList();
            return cambios;
        } finally {
            this.getEntityManager().close();
        }
    }

    public List<CambioCasillero> getCambiosCasilleroSignos() {
        try {
            Query query = this.getEntityManager().createQuery("Select c from CambioCasillero c where c.fuente = 'SIGNOS DISTINTIVOS' order by c.providencia desc");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<CambioCasillero> cambios = query.getResultList();
            return cambios;
        } finally {
            this.getEntityManager().close();
        }

    }

    public List<CambioCasillero> getCambiosCasillero() {
        try {
            Query query = this.getEntityManager().createQuery("Select c from CambioCasillero c where c.fuente in ('SIGNOS DISTINTIVOS','PATENTES','OPOSICION') order by c.providencia desc");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<CambioCasillero> cambios = query.getResultList();
            return cambios;
        } finally {
            this.getEntityManager().close();
        }
    }

    public String nextProvidencia() {
        try {
            Query query = this.getEntityManager().createQuery("Select r from CambioCasillero r where r.id = (Select MAX(r1.id) from CambioCasillero r1 where r1.fuente in ('SIGNOS DISTINTIVOS','PATENTES','OPOSICION'))");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<CambioCasillero> cambios = query.getResultList();
            if (cambios.isEmpty()) {
                return "001";
            } else {
                CambioCasillero cc = cambios.get(0);
                int providencia = Integer.parseInt(cc.getProvidencia()) + 1;
                if (providencia < 10) {
                    return "00" + providencia;
                } else if (providencia < 100) {
                    return "0" + providencia;
                } else {
                    return providencia + "";
                }
            }
        } finally {
            this.getEntityManager().close();
        }
    }

    public CambioCasillero getCambioCasilleroWhenNotId(CambioCasillero cambio) {
        try {
            Query query = this.getEntityManager().createQuery("Select c from CambioCasillero c where c.tramite = :tramite and c.fechaProvidencia = :fechaProvidencia and c.providencia = :providencia and c.fuente = :fuente");
            query.setParameter("tramite", cambio.getTramite());
            query.setParameter("fechaProvidencia", cambio.getFechaProvidencia(), javax.persistence.TemporalType.DATE);
            query.setParameter("providencia", cambio.getProvidencia());
            query.setParameter("fuente", cambio.getFuente());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return (CambioCasillero) query.getSingleResult();
        } finally {
            this.getEntityManager().close();
        }

    }

}
