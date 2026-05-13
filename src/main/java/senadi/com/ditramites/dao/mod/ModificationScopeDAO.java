/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao.mod;

import java.util.List;
import javax.persistence.Query;
import senadi.com.ditramites.model.mod.ModificationScope;

/**
 *
 * @author michael
 */
public class ModificationScopeDAO extends DAOAbstractMod<ModificationScope> {

    public ModificationScopeDAO(ModificationScope t) {
        super(t);
    }

    @Override
    public List<ModificationScope> buscarTodos() {
        try {
            Query query = this.getEntityManager().createQuery("Select m from ModificationScope m");
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }

    public List<ModificationScope> getEnviadosByAffectedApplicationNumber(String applicationNumber, String applicationType) {
        try {
            Query query = this.getEntityManager().createQuery(
                    "Select m from ModificationScope m "
                    + "where m.affectedApplicationNumber = :appNumber "
                    + "and m.applicationType = :appType "
                    + "and m.status = 'ENVIADO' "
                    + "order by m.scopeNumber");
            query.setParameter("appNumber", applicationNumber);
            query.setParameter("appType", applicationType);
            return query.getResultList();
        } finally {
            this.getEntityManager().close();
        }
    }

}
