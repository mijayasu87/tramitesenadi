/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.postgres.polaris.dao;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.dao.procesos.DAOAbstract;
import senadi.com.ditramites.model.postgres.polaris.IpasEnvironment;

/**
 *
 * @author michael
 */
public class IpasEnvironmentDAO extends DAOAbstract<IpasEnvironment>{

    public IpasEnvironmentDAO(IpasEnvironment t) {
        super(t);
    }

    @Override
    public List<IpasEnvironment> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select i from IpasEnvironment i");
        return query.getResultList();
    }
    
    public IpasEnvironment getIpasEnvironmentActive(boolean active, String tipo){
        Query query = this.getEntityManager().createQuery("Select i from IpasEnvironment i where i.active = :active and i.tipo = :tipo");
        query.setParameter("active", active);
        query.setParameter("tipo", tipo);
        List<IpasEnvironment> ipasEnvironments = query.getResultList();
        if(ipasEnvironments.isEmpty()){
            return new IpasEnvironment();
        }else{
            return ipasEnvironments.get(0);
        }
    }
    
}
