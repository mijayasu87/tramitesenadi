/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.util;

import java.util.List;
import senadi.com.ditramites.model.postgres.polaris.IpasApi;
import senadi.com.ditramites.model.postgres.polaris.dao.IpasApiDAO;

/**
 *
 * @author michael
 */
public class Controller {

    public boolean saveIpasApi(IpasApi ipasApi) {
        IpasApiDAO id = new IpasApiDAO(ipasApi);
        try {
            id.persist();
            return true;
        } catch (Exception ex) {
            System.err.println(Operaciones.getCurrentTimeStamp() + ": No se pudo guardar ipasApi: " + ex);
            return false;
        }
    }

    public boolean deleteAllIpasApi() {
        List<IpasApi> ipasapis = getAllIpasApi();
        for (int i = 0; i < ipasapis.size(); i++) {
            IpasApi ipasApi = ipasapis.get(i);
            removeIpasApi(ipasApi);
        }
        return true;
    }

    public List<IpasApi> getAllIpasApi() {
        IpasApiDAO id = new IpasApiDAO(null);
        return id.buscarTodos();
    }

    public boolean removeIpasApi(IpasApi ipasApi) {
        IpasApiDAO id = new IpasApiDAO(ipasApi);
        try {
            if (!id.getEntityManager().contains(ipasApi)) {
                System.out.println(Operaciones.getCurrentTimeStamp() + ": merge ipasApi to remove");
                ipasApi = id.getEntityManager().merge(ipasApi);
                id = new IpasApiDAO(ipasApi);
            }
            id.remove();
            return true;
        } catch (Exception ex) {
            System.err.println(Operaciones.getCurrentTimeStamp() + ": Hubo un problema al eliminar ipasapi " + ipasApi.getId() + ": " + ex);
            return false;
        }
    }
}
