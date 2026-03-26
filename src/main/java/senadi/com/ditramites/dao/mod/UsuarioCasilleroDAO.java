/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.mod;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.model.mod.Usuario;

/**
 *
 * @author michael
 */
public class UsuarioCasilleroDAO extends DAOAbstractMod<Usuario> {

    public UsuarioCasilleroDAO(Usuario t) {
        super(t);
    }

    @Override
    public List<Usuario> buscarTodos() {
        try {
            Query query = this.getEntityManager().createQuery("Select u from Usuario u order by u.fechaCreacion");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }

    public boolean existsUsuarioActivo(String usuario, boolean activo) {
        try {
            Query query = this.getEntityManager().createQuery("Select u from Usuario u where u.usuario = '" + usuario + "' and u.activo = " + activo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Usuario> usuarios = query.getResultList();
            return !usuarios.isEmpty();
        } finally {
            this.getEntityManager().close();
        }
    }

    public Usuario getUsuarioActivo(String usuario, boolean activo) {
        try {
            Query query = this.getEntityManager().createQuery("Select u from Usuario u where u.usuario = '" + usuario + "' and u.activo = " + activo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Usuario> usuarios = query.getResultList();
            if (usuarios.isEmpty()) {
                return new Usuario();
            } else {
                return usuarios.get(0);
            }
        } finally {
            this.getEntityManager().close();
        }
    }
}
