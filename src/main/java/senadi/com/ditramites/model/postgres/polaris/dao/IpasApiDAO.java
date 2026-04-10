/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.postgres.polaris.dao;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.dao.procesos.DAOAbstract;
import senadi.com.ditramites.model.postgres.polaris.IpasApi;

/**
 *
 * @author michael
 */
public class IpasApiDAO extends DAOAbstract<IpasApi>{

    public IpasApiDAO(IpasApi t) {
        super(t);
    }

    @Override
    public List<IpasApi> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select i from IpasApi i");
        return query.getResultList();
    }
    
    public IpasApi getIpasApiByDate(String date){
        Query query = this.getEntityManager().createQuery("Select i from IpasApi i where i.fechaInicio <= :fecha and i.fechaFin >= :fecha");
        query.setParameter("fecha", Timestamp.valueOf(date));
        List<IpasApi> ipasApis = query.getResultList();
        if(ipasApis.isEmpty()){
            return new IpasApi();
        }else{
            return ipasApis.get(0);
        }
    }   
}
