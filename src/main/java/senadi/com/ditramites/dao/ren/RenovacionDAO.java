/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.ren;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.model.modren.Renovacion;

/**
 *
 * @author micharesp
 */
public class RenovacionDAO extends DAOAbstractRen<Renovacion>{

    public RenovacionDAO(Renovacion t) {
        super(t);
    }

    @Override
    public List<Renovacion> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select r from Renovacion r");
        return query.getResultList();
    }
    
    public List<Renovacion> getRenovacionesByTitOrTramite(String tituloOrTramite, boolean avisa){
        String sql;
        if(avisa){
            sql = "Select c from Renovacion c where c.solicitudSenadi = '"+tituloOrTramite+"'";
        }else{
            sql = "Select c from Renovacion c where c.registroNo = '"+tituloOrTramite+"'";
        }
        Query query = this.getEntityManager().createQuery(sql);
        return query.getResultList();
    }
    
}
