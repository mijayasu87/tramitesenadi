/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import senadi.com.ditramites.model.Cpis;
import senadi.com.ditramites.util.ParametrosBD;

/**
 *
 * @author michael
 */
public class DAOConsultasAdmin {
    public List<Cpis> getCpis(String applicationNumber) {
        String query = "Select * from cpis as cp where cp.earlier_procedure = ? or cp.current_procedure = ? order by cp.last_notification_date";

        try {
            Connection con = ParametrosBD.doConnectionToAdmin();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, applicationNumber);
            pst.setString(2, applicationNumber);
            ResultSet rs = pst.executeQuery();
            List<Cpis> cpis = new ArrayList<>();
            while (rs.next()) {
                Cpis cpi = new Cpis();
                cpi.setResolutionNumber(rs.getString("resolution_number"));
                cpi.setSubject(rs.getString("subject"));
                cpi.setResourceDate(rs.getDate("resource_date"));
                cpi.setEarlierProcedure(rs.getString("earlier_procedure"));
                cpi.setBoardMember(rs.getString("board_member"));
                cpi.setCpiYear(rs.getInt("cpi_year"));
                cpi.setCurrentProcedure(rs.getString("current_procedure"));
                cpi.setCpiReource(rs.getString("cpi_resource"));
                cpi.setDenomination(rs.getString("denomination"));
                cpi.setRecurrent(rs.getString("recurrent"));
                cpi.setStatus(rs.getString("status"));
                cpi.setLastNotificationDate(rs.getDate("last_notification_date"));
                cpi.setResolutionDate(rs.getDate("resolution_date"));
                cpi.setWrittenFile(rs.getString("written_file"));
                cpi.setMemoNumber(rs.getString("memo_number"));
                cpi.setMemoDate(rs.getDate("memo_date"));
                cpi.setHall(rs.getString("hall"));
                cpis.add(cpi);
            }

            con.close();
            return cpis;
        } catch (SQLException ex) {
            System.err.println("Error en obtener cpis del trámite " + applicationNumber + ": " + ex);
            return new ArrayList<>();
        }
    }
}
