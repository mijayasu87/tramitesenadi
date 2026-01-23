/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.mod;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.model.mod.CambioNombre;

/**
 *
 * @author micharesp
 */
public class CambioNombreDAO extends DAOAbstractMod<CambioNombre>{

    public CambioNombreDAO(CambioNombre t) {
        super(t);
    }

    @Override
    public List<CambioNombre> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select c from CambioNombre c");
        return query.getResultList();
    }
    
    public List<CambioNombre> getCambioNombresByTitulo(String titulo){
        Query query = this.getEntityManager().createQuery("Select c from CambioNombre c where c.registro = '"+titulo+"'");
        return query.getResultList();
    }
    
}
