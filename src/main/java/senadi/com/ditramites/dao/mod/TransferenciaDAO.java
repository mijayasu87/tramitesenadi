/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.mod;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.model.mod.CambioDomicilio;
import senadi.com.ditramites.model.mod.CambioNombre;
import senadi.com.ditramites.model.mod.LicenciaUso;
import senadi.com.ditramites.model.mod.PrendaComercial;
import senadi.com.ditramites.model.mod.SubLicenciaUso;
import senadi.com.ditramites.model.mod.Transferencia;

/**
 *
 * @author micharesp
 */
public class TransferenciaDAO extends DAOAbstractMod<Transferencia> {

    public TransferenciaDAO(Transferencia t) {
        super(t);
    }

    @Override
    public List<Transferencia> buscarTodos() {
        try {
            Query query = this.getEntityManager().createQuery("Select t from Transferencia t");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }

    public List<Transferencia> getTransferencias(String tituloOrTramite, boolean avisa) {
        String sql;
        if (avisa) {
            sql = "Select t from Transferencia t where t.solicitud = '" + tituloOrTramite + "'";
        } else {
            sql = "Select t from Transferencia t where t.registro = '" + tituloOrTramite + "'";
        }
        try {
            Query query = this.getEntityManager().createQuery(sql);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }

    public List<CambioNombre> getCambioNombresByTitulo(String tituloOrTramite, boolean avisa) {

        String sql;
        if (avisa) {
            sql = "Select c from CambioNombre c where c.solicitud = '" + tituloOrTramite + "'";
        } else {
            sql = "Select c from CambioNombre c where c.registro = '" + tituloOrTramite + "'";
        }

        try {
            Query query = this.getEntityManager().createQuery(sql);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }

    public List<CambioDomicilio> getCambioDomicilioByTitulo(String tituloOrTramite, boolean avisa) {
        String sql;
        if (avisa) {
            sql = "Select c from CambioDomicilio c where c.solicitud = '" + tituloOrTramite + "'";
        } else {
            sql = "Select c from CambioDomicilio c where c.registro = '" + tituloOrTramite + "'";
        }

        try {
            Query query = this.getEntityManager().createQuery(sql);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }

    public List<PrendaComercial> getPrendaComercialByTitulo(String tituloOrTramite, boolean avisa) {
        String sql;
        if (avisa) {
            sql = "Select c from PrendaComercial c where c.solicitud = '" + tituloOrTramite + "'";
        } else {
            sql = "Select c from PrendaComercial c where c.registro = '" + tituloOrTramite + "'";
        }
        try {
            Query query = this.getEntityManager().createQuery(sql);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }

    }

    public List<LicenciaUso> getLicenciaUsosByTitulo(String tituloOrTramite, boolean avisa) {
        String sql;
        if (avisa) {
            sql = "Select c from LicenciaUso c where c.solicitud = '" + tituloOrTramite + "'";
        } else {
            sql = "Select c from LicenciaUso c where c.registro = '" + tituloOrTramite + "'";
        }

        try {
            Query query = this.getEntityManager().createQuery(sql);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }

    public List<SubLicenciaUso> getSubLicenciaUsosByTitulo(String tituloOrTramite, boolean avisa) {
        String sql;
        if (avisa) {
            sql = "Select c from SubLicenciaUso c where c.solicitud = '" + tituloOrTramite + "'";
        } else {
            sql = "Select c from SubLicenciaUso c where c.registro = '" + tituloOrTramite + "'";
        }
        try {
            Query query = this.getEntityManager().createQuery(sql);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }
}
