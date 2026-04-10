package senadi.com.ditramites.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import senadi.com.ditramites.model.mod.CambioCasillero;
import senadi.com.ditramites.model.mod.Usuario;
import senadi.com.ditramites.util.Controlador;
import senadi.com.ditramites.util.LDAP;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = -2152389656664659476L;
    private String nombre;
    private String clave;
    private boolean logeado = false;

//    private Usuario usuario;
    private boolean shake;

    private boolean various;

    private boolean lectura;
    private String grupo;

    private CambioCasillero cambioCasillero;

    private Usuario usuarioCambioCasillero;

    private boolean depurarDuplicado;
    private boolean grupoPatentes;

    public LoginBean() {
        shake = true;
        usuarioCambioCasillero = new Usuario();
        usuarioCambioCasillero.setTipo("USUARIO");
        depurarDuplicado = false;
        grupoPatentes = false;
    }

    public boolean estaLogeado() {
        return logeado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    private void loadUsuarioCCasillero() {
        Controlador c = new Controlador();
        Usuario usuario = c.getUsuarioActivo(nombre, true);
        if (usuario.getId() != null) {
            usuarioCambioCasillero = usuario;
        }
    }

    public void login(ActionEvent actionEvent) {
        FacesMessage msg = null;
        LDAP c = new LDAP();
        String grup = "SC_TramitesSENADI";
        String grupocas = "SC_CambioCasillero";
        String grupodep = "SC_DEPURAR_DUP";
        String grupopat = "SC_Patentes";

        int n = c.validarIngresoLDAPRestringido(nombre, clave, grup);
//        int n = c.validarIngresoLDAPSinrestrinccion(nombre, clave) ? 1 : 0;
        if (n == 1) {
            n = c.validarIngresoLDAPRestringido(nombre, clave, grupocas);
            if (n == 1) {
                grupo = grupocas;
                loadUsuarioCCasillero();
            } else {
                grupo = grup;
            }

            n = c.validarIngresoLDAPRestringido(nombre, clave, grupopat);
            grupoPatentes = n == 1;

            shake = false;
            logeado = true;
            lectura = false; //poner true en caso de querer editar en modo lectura

            PrimeFaces.current().ajax().addCallbackParam("estaLogeado", logeado);
            if (logeado) {
                PrimeFaces.current().ajax().addCallbackParam("view", "index.xhtml");
            }

            n = c.validarIngresoLDAPRestringido(nombre, clave, grupodep);
            depurarDuplicado = n == 1;
            System.out.println("depurar: "+depurarDuplicado);

            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", nombre);

        } else if (n == -1) {
            n = c.validarIngresoLDAPRestringido(nombre, clave, grupocas);
            if (n == 1) {
                loadUsuarioCCasillero();
                grupo = grupocas;
                shake = false;
                logeado = true;
                lectura = false; //poner true en caso de querer editar en modo lectura

                PrimeFaces.current().ajax().addCallbackParam("estaLogeado", logeado);
                if (logeado) {
                    PrimeFaces.current().ajax().addCallbackParam("view", "index.xhtml");
                }
                n = c.validarIngresoLDAPRestringido(nombre, clave, grupopat);
                grupoPatentes = n == 1;
            } else {
                n = c.validarIngresoLDAPRestringido(nombre, clave, grupopat);
                if (n == 1) {
                    grupo = grupopat;
                    grupoPatentes = true;
                    shake = false;
                    logeado = true;
                    lectura = false;

                    PrimeFaces.current().ajax().addCallbackParam("estaLogeado", logeado);
                    if (logeado) {
                        PrimeFaces.current().ajax().addCallbackParam("view", "index.xhtml");
                    }
                } else if (n == -1) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "No tiene autorización para ingresar");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales Incorrectas");
                }
            }
            n = c.validarIngresoLDAPRestringido(nombre, clave, grupodep);
            depurarDuplicado = n == 1;
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales Incorrectas");

        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        logeado = false;
        shake = false;
        grupoPatentes = false;
    }

    /**
     * @return the shake
     */
    public boolean isShake() {
        return shake;
    }

    /**
     * @param shake the shake to set
     */
    public void setShake(boolean shake) {
        this.shake = shake;
    }

    /**
     * @return the lectura
     */
    public boolean isLectura() {
        return lectura;
    }

    /**
     * @param lectura the lectura to set
     */
    public void setLectura(boolean lectura) {
        this.lectura = lectura;
    }

    /**
     * @return the various
     */
    public boolean isVarious() {
        return various;
    }

    /**
     * @param various the various to set
     */
    public void setVarious(boolean various) {
        this.various = various;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the cambioCasillero
     */
    public CambioCasillero getCambioCasillero() {
        return cambioCasillero;
    }

    /**
     * @param cambioCasillero the cambioCasillero to set
     */
    public void setCambioCasillero(CambioCasillero cambioCasillero) {
        this.cambioCasillero = cambioCasillero;
    }

    /**
     * @return the usuarioCambioCasillero
     */
    public Usuario getUsuarioCambioCasillero() {
        return usuarioCambioCasillero;
    }

    /**
     * @param usuarioCambioCasillero the usuarioCambioCasillero to set
     */
    public void setUsuarioCambioCasillero(Usuario usuarioCambioCasillero) {
        this.usuarioCambioCasillero = usuarioCambioCasillero;
    }

    /**
     * @return the depurarDuplicado
     */
    public boolean isDepurarDuplicado() {
        return depurarDuplicado;
    }

    /**
     * @param depurarDuplicado the depurarDuplicado to set
     */
    public void setDepurarDuplicado(boolean depurarDuplicado) {
        this.depurarDuplicado = depurarDuplicado;
    }

    public boolean isGrupoPatentes() {
        return grupoPatentes;
    }

    public void setGrupoPatentes(boolean grupoPatentes) {
        this.grupoPatentes = grupoPatentes;
    }

}
