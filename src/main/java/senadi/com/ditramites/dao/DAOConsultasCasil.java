/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import senadi.com.ditramites.model.LockerNotifications;
import senadi.com.ditramites.model.Notifications;
import senadi.com.ditramites.model.Owner;
import senadi.com.ditramites.util.Operaciones;
import senadi.com.ditramites.util.ParametrosBD;

/**
 *
 * @author micharesp
 */
public class DAOConsultasCasil {

    public boolean saveNotifications(Notifications not) {
        String query = "INSERT INTO notifications VALUES(0,?,?,?,?,?,?,?,?,?)";
        try {
            Connection con = ParametrosBD.doConnectionToCasilleros();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, not.getMatId());
            pst.setInt(2, not.getNotId());
            pst.setString(3, not.getMatter());
            pst.setString(4, not.getDocument());
            pst.setString(5, not.getCreateDate());//new Timestamp(new Date().getTime()));
            pst.setTimestamp(6, null);
            pst.setString(7, null);
            pst.setString(8, not.getSource());
            pst.setInt(9, not.getCreatedId());

            int num = pst.executeUpdate();
            con.close();
            return num > 0;
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al guardar notifications: " + ex);
            return false;
        }
    }

    public boolean saveLockerNotifications(LockerNotifications ln) {
        String query = "INSERT INTO locker_notifications VALUES(0,?,?,?,?,?)";
        try {
            Connection con = ParametrosBD.doConnectionToCasilleros();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, ln.getLockerId());
            pst.setInt(2, ln.getNotification_id());
            pst.setTimestamp(3, null);
            pst.setString(4, "SENT");
            pst.setString(5, ln.getDocument());            
            int num = pst.executeUpdate();
            con.close();
            return num > 0;
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al guardar locker_notifications: " + ex);
            return false;
        }
    }

    public Notifications getNotificationsByMatterAndCreateDt(String matter, String createDt) {
        String query = "SELECT * FROM notifications WHERE matter = ? and create_dt = '"+createDt+"'";
        try {
            Connection con = ParametrosBD.doConnectionToCasilleros();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, matter);
            ResultSet rs = pst.executeQuery();
            Notifications not = new Notifications();
            if (rs.next()) {
                not.setId(rs.getInt("id"));
                not.setMatId(rs.getInt("mat_id"));
                not.setNotId(rs.getInt("not_id"));
                not.setMatter(rs.getString("matter"));
                not.setDocument(rs.getString("document"));
                Timestamp tt = rs.getTimestamp("create_dt");
                if (tt != null) {
                    not.setCreateDate(Operaciones.formatTimesTamp(tt));
                }
                not.setSource(rs.getString("source"));
                not.setCreatedId(rs.getInt("created_id"));
            }
            con.close();
            return not;
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al obtener notifications: " + ex);
            return new Notifications();
        }
    }

    public List<Notifications> getNotificationsByTramite(String tramite) {
        String query = "SELECT n.create_dt, n.document, n.id, lon.id as lo_id,  lon.locker_id, "
                + "n.mat_id, n.matter, n.not_id, lon.open_dt, n.source, lon.status, mt.name, nt.name "
                + "FROM notifications AS n "
                + "INNER JOIN locker_notifications AS lon ON lon.notification_id = n.id "
                + "INNER JOIN matter_types AS mt ON mt.id = n.mat_id "
                + "INNER JOIN notification_types AS nt ON nt.id = n.not_id "
                + "WHERE n.matter = '" + tramite + "' order by n.create_dt";
        try {
            Connection con = ParametrosBD.doConnectionToCasilleros();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            List<Notifications> notifications = new ArrayList<>();
            while (rs.next()) {
                Notifications notification = new Notifications();
                notification.setCreateDate(Operaciones.formatTimesTamp(rs.getTimestamp("create_dt")));
                notification.setDocument(rs.getString("document"));
                notification.setId(rs.getInt("id"));
                notification.setLocker(rs.getInt("locker_id"));
                notification.setMatId(rs.getInt("mat_id"));
                notification.setMatter(rs.getString("matter"));
                notification.setNotId(rs.getInt("not_id"));
                
                notification.setLoId(rs.getInt("lo_id"));
                
                Timestamp tt = rs.getTimestamp("open_dt");
                if (tt != null) {
                    notification.setOpenDate(Operaciones.formatTimesTamp(tt));
                }

                notification.setSource(rs.getString("source"));
                notification.setStatus(rs.getString("status"));

                if (notification.getDocument().toLowerCase().contains("_rs_") || notification.getDocument().toLowerCase().contains("resolución")
                        || notification.getDocument().toLowerCase().contains("resolucion")) {
                    notification.setType("RESOLUCIÓN");
                } else if (notification.getDocument().toLowerCase().contains("_ti_") || notification.getDocument().toLowerCase().contains("título")
                        || notification.getDocument().toLowerCase().contains("titulo")) {
                    notification.setType("TÍTULO");
                } else {
                    notification.setType("NOTIFICACIÓN");
                }
                
                notification.setMatterType(rs.getString("mt.name"));
                notification.setNotificationType(rs.getString("nt.name"));                                

                notifications.add(notification);
            }
            con.close();
            return notifications;
        } catch (Exception ex) {
            System.out.println("error al obtener notifications del trámite " + tramite + ": " + ex);
            return new ArrayList<>();
        }
    }
    
    public List<Notifications> getNotificationsByTramiteAndCasillero(String tramite, int casillero) {
        String query = "SELECT n.create_dt, n.document, n.id, lon.id as lo_id,  lon.locker_id, "
                + "n.mat_id, n.matter, n.not_id, lon.open_dt, n.source, lon.status, mt.name, nt.name "
                + "FROM notifications AS n "
                + "INNER JOIN locker_notifications AS lon ON lon.notification_id = n.id "
                + "INNER JOIN matter_types AS mt ON mt.id = n.mat_id "
                + "INNER JOIN notification_types AS nt ON nt.id = n.not_id "
                + "WHERE n.matter = '" + tramite + "' and lon.locker_id = "+casillero+" order by n.create_dt";
        try {
            Connection con = ParametrosBD.doConnectionToCasilleros();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            List<Notifications> notifications = new ArrayList<>();
            while (rs.next()) {
                Notifications notification = new Notifications();
                notification.setCreateDate(Operaciones.formatTimesTamp(rs.getTimestamp("create_dt")));
                notification.setDocument(rs.getString("document"));
                notification.setId(rs.getInt("id"));
                notification.setLocker(rs.getInt("locker_id"));
                notification.setMatId(rs.getInt("mat_id"));
                notification.setMatter(rs.getString("matter"));
                notification.setNotId(rs.getInt("not_id"));
                
                notification.setLoId(rs.getInt("lo_id"));
                
                Timestamp tt = rs.getTimestamp("open_dt");
                if (tt != null) {
                    notification.setOpenDate(Operaciones.formatTimesTamp(tt));
                }

                notification.setSource(rs.getString("source"));
                notification.setStatus(rs.getString("status"));

                if (notification.getDocument().toLowerCase().contains("_rs_") || notification.getDocument().toLowerCase().contains("resolución")
                        || notification.getDocument().toLowerCase().contains("resolucion")) {
                    notification.setType("RESOLUCIÓN");
                } else if (notification.getDocument().toLowerCase().contains("_ti_") || notification.getDocument().toLowerCase().contains("título")
                        || notification.getDocument().toLowerCase().contains("titulo")) {
                    notification.setType("TÍTULO");
                } else {
                    notification.setType("NOTIFICACIÓN");
                }
                
                notification.setMatterType(rs.getString("mt.name"));
                notification.setNotificationType(rs.getString("nt.name"));

                notifications.add(notification);
            }
            con.close();
            return notifications;
        } catch (Exception ex) {
            System.out.println("error al obtener notifications del trámite " + tramite + ": " + ex);
            return new ArrayList<>();
        }
    }
    
    public List<String> getTramitesNotificationsByCasillero(int casillero) {
        String query = "SELECT DISTINCT(n.matter) AS tram, COUNT(n.matter) AS cantidad "
                + "FROM notifications AS n "
                + "INNER JOIN locker_notifications AS lon ON lon.notification_id = n.id "
                + "WHERE lon.locker_id = " + casillero+" "
                + "GROUP BY n.matter "
                + "HAVING COUNT(n.matter) > 1";
        try {
            Connection con = ParametrosBD.doConnectionToCasilleros();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            List<String> casilleros = new ArrayList<>();
            while (rs.next()) {
                String sal = rs.getString("tram");
                casilleros.add(sal);
            }
            con.close();
            return casilleros;
        } catch (Exception ex) {
            System.out.println("error al obtener tramites del casillero " + casillero + ": " + ex);
            return new ArrayList<>();
        }
    }

    public Owner getOwnerById(int id) {
        String sql = "Select ow.id, ow.document, ow.document_type, ow.email, "
                + "ow.mobile, ow.firstname, ow.lastname, ow.phone, ow.law_firm, "
                + "lo.id as casillero "
                + "from owners as ow "
                + "inner join lockers as lo on lo.owner_id = ow.id "
                + "where ow.id = " + id;
        try {
            Connection con = ParametrosBD.doConnectionToCasilleros();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            Owner owner = new Owner();
            while (rs.next()) {
                owner.setId(rs.getInt("id"));
                owner.setDocument(rs.getString("document"));
                owner.setDocumentType(rs.getString("document_type"));
                owner.setEmail(rs.getString("email"));
                owner.setMobile(rs.getString("mobile"));
                owner.setName(rs.getString("firstname") + " " + rs.getString("lastname"));
                owner.setPhone(rs.getString("phone"));
                owner.setLawFirm(rs.getString("law_firm"));
                owner.setCasillero(rs.getString("casillero"));
            }
            con.close();
            return owner;
        } catch (Exception ex) {
            System.err.println("Error al obtener el owner del id " + id);
            return new Owner();
        }
    }

    public Owner getOwnerByLockerId(int lockerId) {
        String sql = "Select o.id, o.document, o.document_type, o.email, o.mobile, o.firstname, o.lastname, o.phone, "
                + "lo.id as casillero "
                + "from owners as o "
                + "inner join lockers as lo on lo.owner_id = o.id "
                + "where lo.id = " + lockerId;
        try {
            Connection con = ParametrosBD.doConnectionToCasilleros();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            Owner owner = new Owner();
            while (rs.next()) {
                owner.setId(rs.getInt("id"));
                owner.setDocument(rs.getString("document"));
                owner.setDocumentType(rs.getString("document_type"));
                owner.setEmail(rs.getString("email"));
                owner.setMobile(rs.getString("mobile"));
                owner.setName(rs.getString("firstname") + " " + rs.getString("lastname"));
                owner.setPhone(rs.getString("phone"));
                owner.setCasillero(rs.getString("casillero"));
            }
            con.close();
            return owner;
        } catch (Exception ex) {
            System.err.println("Error al obtener el owner del locker_id " + lockerId);
            return new Owner();
        }
    }

    public List<Owner> getOwnersByCriteria(String criteria) {
        String query = "Select o.id, o.firstname, o.lastname,o.document_type,"
                + "o.document,o.email,o.phone,o.mobile, "
                + "lo.id as casillero, o.law_firm "
                + "from owners o "
                + "inner join lockers as lo on o.id = lo.owner_id "
                + "where o.document like '%" + criteria + "%' or o.firstname like '%" + criteria + "%' "
                + "or o.lastname like '%" + criteria + "%' or o.email like '%" + criteria + "%' or lo.id like '" + criteria + "' order by lo.id limit 300";
        try {
            Connection con = ParametrosBD.doConnectionToCasilleros();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<Owner> owners = new ArrayList<>();
            while (rs.next()) {
                Owner owner = new Owner();
                owner.setId(rs.getInt("id"));
                owner.setName(rs.getString("firstname") + " " + rs.getString("lastname"));
                owner.setDocumentType(rs.getString("document_type"));
                owner.setDocument(rs.getString("document"));
                owner.setEmail(rs.getString("email"));
                owner.setPhone(rs.getString("phone"));
                owner.setMobile(rs.getString("mobile"));
                owner.setLawFirm(rs.getString("law_firm"));
                owner.setCasillero(rs.getString("casillero"));
                owners.add(owner);
            }
            con.close();
            return owners;
        } catch (Exception ex) {
            System.out.println("error en obtener datos owners: " + ex);
            return new ArrayList<>();
        }
    }
    
    public boolean removeLockerNotifications(List<Integer> lockerIds){
        String ids = "";
        for (int i = 0; i < lockerIds.size(); i++) {
            ids += lockerIds.get(i)+",";
        }
        ids = ids.trim();
        ids = ids.substring(0,ids.length()-1);
        System.out.println("locker_notifications a eliminar: "+ids);
        
        String query = "delete from locker_notifications where id in ("+ids+")" ;
        try {
            Connection con = ParametrosBD.doConnectionToCasilleros();
            PreparedStatement pst = con.prepareStatement(query);            
            int num = pst.executeUpdate();
            con.close();
            return num > 0;
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al eliminar los locker_notifications: " + ids);
            return false;
        }        
    }
    
    public boolean removeLockerNotification(int id){
        
        System.out.println("locker_notification a eliminar: "+id);
        
        String query = "delete from locker_notifications where id = "+id ;
        try {
            Connection con = ParametrosBD.doConnectionToCasilleros();
            PreparedStatement pst = con.prepareStatement(query);            
            int num = pst.executeUpdate();
            con.close();
            return num > 0;
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al eliminar el locker_notification: " + id);
            return false;
        }        
    }
}
