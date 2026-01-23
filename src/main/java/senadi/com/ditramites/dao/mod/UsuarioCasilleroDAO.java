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
public class UsuarioCasilleroDAO extends DAOAbstractMod<Usuario>{

    public UsuarioCasilleroDAO(Usuario t) {
        super(t);
    }

    @Override
    public List<Usuario> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select u from Usuario u order by u.fechaCreacion");
        return query.getResultList();
    }
    
    public boolean existsUsuarioActivo(String usuario, boolean activo){
        Query query = this.getEntityManager().createQuery("Select u from Usuario u where u.usuario = '"+usuario+"' and u.activo = "+activo);
        List<Usuario> usuarios = query.getResultList();
        if(usuarios.isEmpty()){
            return false;
        }else{
            return true;
        }        
    }
    
    public Usuario getUsuarioActivo(String usuario, boolean activo){
        Query query = this.getEntityManager().createQuery("Select u from Usuario u where u.usuario = '"+usuario+"' and u.activo = "+activo);
        List<Usuario> usuarios = query.getResultList();
        if(usuarios.isEmpty()){
            return new Usuario();
        }else{
            return usuarios.get(0);
        }
    }
}
