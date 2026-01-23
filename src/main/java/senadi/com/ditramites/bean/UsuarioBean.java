/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.RowEditEvent;
import senadi.com.ditramites.model.mod.Usuario;
import senadi.com.ditramites.util.Controlador;

/**
 *
 * @author michael
 */
@ManagedBean(name = "userBean")
@ViewScoped
public class UsuarioBean implements Serializable {

    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados;
    private UIData usuarioTable;

    private Usuario usuario;

    private LoginBean login;

    public UsuarioBean() {
        loadData();
    }

    private void loadData() {
        Controlador c = new Controlador();
        login = c.getLogin();
        usuarios = c.getUsuariosCambioCasillero();
    }

    public void onRowEditUsuario(RowEditEvent<Usuario> event) {
        FacesMessage msg = null;
        Usuario useraux = event.getObject();
        if (useraux != null) {
            Controlador c = new Controlador();

            if (c.updateUsuario(useraux)) {
                loadData();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "EDITADO", "USUARIO EDITADO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "HUBO UN PROBLEMA AL INTENTAR EDITAR EL USUARIO");
            }

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO EDITAR EL USUARIO");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancelUsuario(RowEditEvent<Usuario> event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "CANCELADO", "PROCESO CANCELADO");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void enableUsuario(Usuario usuarioaux) {
        Controlador c = new Controlador();
        if (usuarioaux != null) {
            if (usuarioaux.isActivo()) {
                usuarioaux.setActivo(true);
            } else {
                usuarioaux.setActivo(false);
            }
            c.updateUsuario(usuarioaux);
            loadData();
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "INFORMACIÓN", "NO SE PUDO CARGAR EL USUARIO");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void eliminarUsuario(ActionEvent ae) {
        FacesMessage msg = null;
        Usuario usuarioaux = (Usuario) usuarioTable.getRowData();
        if (usuarioaux != null) {
            Controlador c = new Controlador();
            if (c.removeUsuario(usuarioaux)) {
//                String razon = "USUARIO " + usuarioaux.getNombre() + " ELIMINADO";
//                c.saveRazonHistorial(razon, "CONFIGURACIÓN", login.getNombre());
                loadData();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "REMOVIDA", "USARIO " + usuarioaux.getNombre() + " REMOVIDO");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO REMOVER EL USUARIO " + usuarioaux.getNombre());
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE CARGA EL USUARIO");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onAddNewUsuario() {
        FacesMessage msg = null;
        Usuario usuarioaux = new Usuario();
        usuarioaux.setTipo("USUARIO");
        Controlador c = new Controlador();
        if (c.saveUsuario(usuarioaux)) {
            loadData();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "NUEVO USUARIO", "AGREGADO");
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE PUDO AGREGAR EL NUEVO USUARIO");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the usuariosFiltrados
     */
    public List<Usuario> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    /**
     * @param usuariosFiltrados the usuariosFiltrados to set
     */
    public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
        this.usuariosFiltrados = usuariosFiltrados;
    }

    /**
     * @return the usuarioTable
     */
    public UIData getUsuarioTable() {
        return usuarioTable;
    }

    /**
     * @param usuarioTable the usuarioTable to set
     */
    public void setUsuarioTable(UIData usuarioTable) {
        this.usuarioTable = usuarioTable;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the login
     */
    public LoginBean getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(LoginBean login) {
        this.login = login;
    }
}
