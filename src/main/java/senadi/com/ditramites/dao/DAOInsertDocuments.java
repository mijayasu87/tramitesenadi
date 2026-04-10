/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import senadi.com.ditramites.model.FileAnnexesApplication;
import senadi.com.ditramites.util.ParametrosBD;

/**
 *
 * @author michael
 */
public class DAOInsertDocuments {

    public boolean saveDenominationDocument(Integer denominationFormId, Integer denominationAnnexId, String file,
            String fileDescription, String userUpload, Timestamp uploadDate) {
        String query = "INSERT INTO denomination_annexes_data VALUES(?,?,?,?,?,?)";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, denominationFormId);
            pst.setInt(2, denominationAnnexId);
            pst.setString(3, file);
            pst.setString(4, fileDescription);
            pst.setString(5, userUpload);//new Timestamp(new Date().getTime()));
            pst.setTimestamp(6, uploadDate);

            int num = pst.executeUpdate();
            con.close();
            return num > 0;
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al guardar denomination_annexes_data: " + ex);
            return false;
        }
    }

    public boolean saveFileAnnexeApplication(FileAnnexesApplication fap) {
        String query = "INSERT INTO file_annexes_application VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, 0);
            pst.setString(2, fap.getFileName());
            pst.setString(3, fap.getFileDescription());
            pst.setString(4, fap.getUserUpload());
            pst.setTimestamp(5, fap.getUploadDate());
            pst.setString(6, fap.getFileStatus());
            pst.setString(7, fap.getApplicationType());
            pst.setString(8, fap.getApplicationNumber());
            pst.setString(9, fap.getUserUpdate());
            pst.setTimestamp(10, fap.getUpdateDate());

            int num = pst.executeUpdate();
            con.close();
            return num > 0;
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al guardar file_annexes_application: " + ex);
            return false;
        }
    }

    public boolean deleteDenominationDocument(Integer denominationFormId, Integer denominationAnnexId, String file) {
        String query = "DELETE FROM denomination_annexes_data WHERE denomination_form_id = ? AND denomination_annex_id = ? AND file = ?";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, denominationFormId);
            pst.setInt(2, denominationAnnexId);
            pst.setString(3, file);
            int num = pst.executeUpdate();
            con.close();
            return num > 0;
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al eliminar denomination_annexes_data: " + ex);
            return false;
        }
    }

    public boolean softDeleteFileAnnexeApplication(String applicationNumber, String fileName, String applicationType, String userUpdate, Timestamp updateDate) {
        String query = "UPDATE file_annexes_application SET file_status = 'DELETED', user_update = ?, update_date = ? "
                + "WHERE application_number = ? AND file_name = ? AND application_type = ?";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, userUpdate);
            pst.setTimestamp(2, updateDate);
            pst.setString(3, applicationNumber);
            pst.setString(4, fileName);
            pst.setString(5, applicationType);
            int num = pst.executeUpdate();
            con.close();
            return num > 0;
        } catch (SQLException ex) {
            System.out.println("Hubo un problema al marcar file_annexes_application como DELETED: " + ex);
            return false;
        }
    }

}
