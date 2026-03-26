/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.mod;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.model.mod.ConfiguracionCC;

/**
 *
 * @author michael
 */
public class ConfiguracionCCDAO extends DAOAbstractMod<ConfiguracionCC> {

    public ConfiguracionCCDAO(ConfiguracionCC t) {
        super(t);
    }

    @Override
    public List<ConfiguracionCC> buscarTodos() {
        try {
            Query query = this.getEntityManager().createQuery("Select c from ConfiguracionCC c");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }

    public List<ConfiguracionCC> getConfiguracionByTipo(String tipo) {
        try {
            Query query = this.getEntityManager().createQuery("Select c from ConfiguracionCC c where c.tipo = :tipo order by c.id");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("tipo", tipo);
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }

    public String getConfiguracionesActivas() {
        try {
            Query query = this.getEntityManager().createQuery("SELECT c FROM ConfiguracionCC c WHERE c.activo = TRUE");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ConfiguracionCC> confs = query.getResultList();

            if (confs.isEmpty()) {
                return "AÚN NO HA REALIZADO LA CONFIGURACIÓN";
            } else if (confs.size() == 4) {
                return "";
            } else {
                String s = "";
                for (int i = 0; i < confs.size(); i++) {
                    s += confs.get(i).getTipo();
                }

                String texto = "";
                if (!s.contains("ACCIÓN DE PERSONAL")) {
                    texto = "FALTA ACTIVAR LA ACCIÓN DE PERSONAL EN CONFIGURACIÓN\n";
                }
                if (!s.contains("RESOLUCIÓN")) {
                    texto += "FALTA ACTIVAR LA RESOLUCIÓN EN CONFIGURACIÓN\n";
                }

                if (!s.contains("SUBROGANTE")) {
                    texto += "FALTA ACTIVAR EL SUBROGANTE EN CONFIGURACIÓN\n";
                }
                if (!s.contains("DELEGACIÓN SUBROGANTE")) {
                    texto += "FALTA ACTIVAR LA DELEGACIÓN SUBROGANTE EN CONFIGURACIÓN\n";
                }
                return texto;
            }
        } finally {
            this.getEntityManager().close();
        }
    }

    public List<ConfiguracionCC> getConfiguracionesCCActivas() {
        try {
            Query query = this.getEntityManager().createQuery("Select c from ConfiguracionCC c where c.activo = TRUE");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }
}
