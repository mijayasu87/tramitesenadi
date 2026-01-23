/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.procesos;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.model.postgres.PpdiSolicitudSignoDistintivo;
import senadi.com.ditramites.model.postgres.PpdiTituloSignoDistintivo;

/**
 *
 * @author micharesp
 */
public class PpdiSolicitudSignoDAO extends DAOAbstract<PpdiSolicitudSignoDistintivo>{

    public PpdiSolicitudSignoDAO(PpdiSolicitudSignoDistintivo t) {
        super(t);
    }

    @Override
    public List<PpdiSolicitudSignoDistintivo> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select p from PpdiSolicitudSignoDistintivo p");
        return query.getResultList();
    }
    
    public PpdiSolicitudSignoDistintivo getPpdiSolicitudSignoDistintivoByTramite(String tramite){
        Query query = this.getEntityManager().createQuery("Select p from PpdiSolicitudSignoDistintivo p where p.numeroTramite = '"+tramite+"'");
        List<PpdiSolicitudSignoDistintivo> signos = query.getResultList();
        if(signos.isEmpty()){
            return new PpdiSolicitudSignoDistintivo();
        }else{
            return signos.get(0);
        }
    }
    
    public PpdiTituloSignoDistintivo getPpdiTituloSignoDistintivoByCodigoSolicitudSigno(int codigoSolicitudSigno){
        Query query = this.getEntityManager().createQuery("Select p from PpdiTituloSignoDistintivo p where p.codigoSolicitudSigno = "+codigoSolicitudSigno);
        List<PpdiTituloSignoDistintivo> tits = query.getResultList();
        if(tits.isEmpty()){
            return new PpdiTituloSignoDistintivo();
        }else{
            return tits.get(0);
        }
    }

}
