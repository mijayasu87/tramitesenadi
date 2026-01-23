/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import senadi.com.ditramites.model.HallmarkFormDepurada;
import senadi.com.ditramites.util.Operaciones;
import senadi.com.ditramites.util.ParametrosBD;

/**
 *
 * @author micharesp
 */
public class DAOConsultasDep {

    public HallmarkFormDepurada getHallmarkDepuradaByDebugId(Integer debugId) {
        String query = "Select * from hallmark_forms where id = '" + debugId + "'";
        try {
            Connection con = ParametrosBD.doConnectionToDepurar();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
            HallmarkFormDepurada hallmark = new HallmarkFormDepurada();
            while (rs.next()) {
                hallmark.setCreateDate(Operaciones.formatTimesTamp(rs.getTimestamp("create_date")));
                hallmark.setDenomination(rs.getString("denomination"));
                hallmark.setExpYear(rs.getString("exp_year"));
                hallmark.setExpedient(rs.getString("expedient"));                
                hallmark.setId(rs.getInt("id"));
                hallmark.setOwnerId(rs.getInt("owner_id"));
                hallmark.setStatus(rs.getString("status"));
            }
            con.close();
            return hallmark;
        } catch (Exception ex) {
            System.out.println("error al obtener hallmark depurada del trámite " + debugId + ": " + ex);
            return new HallmarkFormDepurada();
        }
    }
    
    public HallmarkFormDepurada getHallmarkDepuradaByExpedient(String expedient) {
        String query = "Select * from hallmark_forms where expedient = '" + expedient + "'";
        try {
            Connection con = ParametrosBD.doConnectionToDepurar();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
            HallmarkFormDepurada hallmark = new HallmarkFormDepurada();
            while (rs.next()) {
                hallmark.setCreateDate(Operaciones.formatTimesTamp(rs.getTimestamp("create_date")));
                hallmark.setDenomination(rs.getString("denomination"));
                hallmark.setExpYear(rs.getString("exp_year"));
                hallmark.setExpedient(rs.getString("expedient"));                
                hallmark.setId(rs.getInt("id"));
                hallmark.setOwnerId(rs.getInt("owner_id"));
                hallmark.setStatus(rs.getString("status"));
            }
            con.close();
            return hallmark;
        } catch (Exception ex) {
            System.out.println("error al obtener hallmark depurada del trámite " + expedient + ": " + ex);
            return new HallmarkFormDepurada();
        }
    }
    
}
