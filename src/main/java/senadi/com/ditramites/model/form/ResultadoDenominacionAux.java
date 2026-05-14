package senadi.com.ditramites.model.form;

import java.io.Serializable;
import java.util.Date;

public class ResultadoDenominacionAux implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TIPO_MARCAS = "MARCAS";
    public static final String TIPO_PATENTES = "PATENTES";
    public static final String TIPO_TRANSFERENCIA = "TRANSFERENCIA";
    public static final String TIPO_CAMBIO_NOMBRE = "CAMBIO NOMBRE";
    public static final String TIPO_CAMBIO_DOMICILIO = "CAMBIO DOMICILIO";
    public static final String TIPO_PRENDA = "PRENDA COMERCIAL";
    public static final String TIPO_LICENCIA = "LICENCIA DE USO";
    public static final String TIPO_SUB_LICENCIA = "SUB-LICENCIA";
    public static final String TIPO_RENOVACION = "RENOVACIÓN";
    public static final String TIPO_OBTENCION_VEGETAL = "OBTENCIÓN VEGETAL";
    public static final String TIPO_DERECHO_AUTOR = "DERECHO DE AUTOR";

    private String tipo;
    private String denominacion;
    private String numeroTramite;
    private Date fechaPresentacion;

    public ResultadoDenominacionAux() {
    }

    public ResultadoDenominacionAux(String tipo, String denominacion, String numeroTramite, Date fechaPresentacion) {
        this.tipo = tipo;
        this.denominacion = denominacion;
        this.numeroTramite = numeroTramite;
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getNumeroTramite() {
        return numeroTramite;
    }

    public void setNumeroTramite(String numeroTramite) {
        this.numeroTramite = numeroTramite;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }
}
