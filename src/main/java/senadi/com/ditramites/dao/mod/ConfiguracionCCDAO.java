/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.mod;

import java.text.Normalizer;
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
            @SuppressWarnings("unchecked")
            List<ConfiguracionCC> configuraciones = query.getResultList();
            return configuraciones;
        } finally {
            this.getEntityManager().close();
        }
    }

    public List<ConfiguracionCC> getConfiguracionByTipo(String tipo) {
        try {
            Query query = this.getEntityManager().createQuery("Select c from ConfiguracionCC c where c.tipo = :tipo order by c.id");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("tipo", tipo);
            @SuppressWarnings("unchecked")
            List<ConfiguracionCC> configuraciones = query.getResultList();
            return configuraciones;
        } finally {
            this.getEntityManager().close();
        }
    }

    public String getConfiguracionesActivas() {
        try {
            Query query = this.getEntityManager().createQuery("SELECT c FROM ConfiguracionCC c WHERE c.activo = TRUE");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<ConfiguracionCC> confs = query.getResultList();

            if (confs.isEmpty()) {
                return "AÚN NO HA REALIZADO LA CONFIGURACIÓN";
            } else {
                String s = "";
                for (int i = 0; i < confs.size(); i++) {
                    s += confs.get(i).getTipo() + "|";
                }

                String texto = "";
                if (!s.contains("ACCIÓN DE PERSONAL")) {
                    texto = "FALTA ACTIVAR LA ACCIÓN DE PERSONAL EN CONFIGURACIÓN\n";
                }
                if (!s.contains("RESOLUCIÓN")) {
                    texto += "FALTA ACTIVAR LA RESOLUCIÓN EN CONFIGURACIÓN\n";
                }

                boolean tieneDelegado = s.contains("DELEGADO|") || s.contains("DELEGADO SIGNOS|") || s.contains("DELEGADO PATENTES|");
                boolean tieneDelegacion = s.contains("DELEGACIÓN|") || s.contains("DELEGACIÓN SIGNOS|") || s.contains("DELEGACIÓN PATENTES|");

                if (!tieneDelegado) {
                    texto += "FALTA ACTIVAR EL SUBROGANTE EN CONFIGURACIÓN\n";
                }
                if (!tieneDelegacion) {
                    texto += "FALTA ACTIVAR LA DELEGACIÓN SUBROGANTE EN CONFIGURACIÓN\n";
                }
                return texto;
            }
        } finally {
            this.getEntityManager().close();
        }
    }

    public String getConfiguracionesActivas(String fuente) {
        try {
            Query query = this.getEntityManager().createQuery("SELECT c FROM ConfiguracionCC c WHERE c.activo = TRUE");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<ConfiguracionCC> confs = query.getResultList();

            if (confs.isEmpty()) {
                return "AÚN NO HA REALIZADO LA CONFIGURACIÓN";
            }

            boolean tieneAccionPersonal = false;
            boolean tieneResolucion = false;
            boolean tieneDelegadoBase = false;
            boolean tieneDelegacionBase = false;
            boolean tieneDelegadoFuente = false;
            boolean tieneDelegacionFuente = false;

            String delegadoFuente = getTipoDelegadoByFuente(fuente);
            String delegacionFuente = getTipoDelegacionByFuente(fuente);

            for (ConfiguracionCC conf : confs) {
                String tipo = normalizeKey(conf.getTipo());
                if ("ACCION DE PERSONAL".equals(tipo)) {
                    tieneAccionPersonal = true;
                } else if ("RESOLUCION".equals(tipo)) {
                    tieneResolucion = true;
                } else if ("DELEGADO".equals(tipo)) {
                    tieneDelegadoBase = true;
                } else if ("DELEGACION".equals(tipo)) {
                    tieneDelegacionBase = true;
                }

                if (normalizeKey(delegadoFuente).equals(tipo)) {
                    tieneDelegadoFuente = true;
                }
                if (normalizeKey(delegacionFuente).equals(tipo)) {
                    tieneDelegacionFuente = true;
                }
            }

            String texto = "";
            if (!tieneAccionPersonal) {
                texto = "FALTA ACTIVAR LA ACCIÓN DE PERSONAL EN CONFIGURACIÓN\n";
            }
            if (!tieneResolucion) {
                texto += "FALTA ACTIVAR LA RESOLUCIÓN EN CONFIGURACIÓN\n";
            }
            if (!(tieneDelegadoFuente || tieneDelegadoBase)) {
                texto += "FALTA ACTIVAR EL SUBROGANTE EN CONFIGURACIÓN\n";
            }
            if (!(tieneDelegacionFuente || tieneDelegacionBase)) {
                texto += "FALTA ACTIVAR LA DELEGACIÓN SUBROGANTE EN CONFIGURACIÓN\n";
            }
            return texto;
        } finally {
            this.getEntityManager().close();
        }
    }

    private String getTipoDelegadoByFuente(String fuente) {
        String fuenteNormalizada = normalizeKey(fuente);
        if (fuenteNormalizada.contains("PATENTE")) {
            return "DELEGADO PATENTES";
        }
        if (fuenteNormalizada.contains("OPOSICION")) {
            return "DELEGADO OPOSICION";
        }
        return "DELEGADO SIGNOS";
    }

    private String getTipoDelegacionByFuente(String fuente) {
        String fuenteNormalizada = normalizeKey(fuente);
        if (fuenteNormalizada.contains("PATENTE")) {
            return "DELEGACIÓN PATENTES";
        }
        if (fuenteNormalizada.contains("OPOSICION")) {
            return "DELEGACIÓN OPOSICION";
        }
        return "DELEGACIÓN SIGNOS";
    }

    private String normalizeKey(String value) {
        if (value == null) {
            return "";
        }
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .trim()
                .toUpperCase();
        return normalized.replaceAll("\\s+", " ");
    }

    public List<ConfiguracionCC> getConfiguracionesCCActivas() {
        try {
            Query query = this.getEntityManager().createQuery("Select c from ConfiguracionCC c where c.activo = TRUE");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<ConfiguracionCC> configuraciones = query.getResultList();
            return configuraciones;
        } finally {
            this.getEntityManager().close();
        }
    }
}
