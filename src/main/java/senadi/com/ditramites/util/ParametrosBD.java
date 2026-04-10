/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author micharesp
 */
public class ParametrosBD {
    //Localhost
//    public static String USER = "root";
//    public static String PASSWORD = "MichaRoot6*";
//    public static String iepi_casilleros = "jdbc:mysql://localhost:3306/iepi_casilleros?useSSL=false";
//    public static String iepi_depurar = "jdbc:mysql://localhost:3306/iepi_depurar?useSSL=false";
//    public static String iepi_formularios = "jdbc:mysql://localhost:3306/iepi_formularios?useSSL=false";

//Producción    
//    public static String USER = "iepi-solicitudes";
//    public static String PASSWORD = "5ad0d5c3fced39d5048f";
//    public static String iepi_formularios = "jdbc:mysql://10.0.20.130:3306/iepi_formularios";
//    public static String iepi_depurar = "jdbc:mysql://10.0.20.130:3306/iepi_depurar";
//    public static String iepi_casilleros = "jdbc:mysql://10.0.20.130:3306/iepi_casilleros";
//    public static String iepi_admin = "jdbc:mysql://10.0.20.130:3306/iepi_admin";
    public static String ftpPath = "/var/www/html/solicitudes/media/files/";
//    public static String urlPath = "https://registro.propiedadintelectual.gob.ec/solicitudes/media/files/";
    
    public static String urlCpis = "http://administracion.propiedadintelectual.gob.ec/servicemanager/media/files/cpis/";
    public static String ftpLockerPath = "/var/www/html/casilleros/media/files/";

//    Prueba
    public static String USER = "iepi-solicitudes";
    public static String PASSWORD = "5ad0d5c3fced39d5048f";
    public static String iepi_formularios = "jdbc:mysql://10.0.26.130:3306/iepi_formularios";
    public static String iepi_depurar = "jdbc:mysql://10.0.26.130:3306/iepi_depurar";
    public static String iepi_casilleros = "jdbc:mysql://10.0.26.130:3306/iepi_casilleros";
    public static String iepi_admin = "jdbc:mysql://10.0.26.130:3306/iepi_admin";

    public static String urlPath = "https://pruebas.propiedadintelectual.gob.ec/solicitudes/media/files/";
    

    //Localhost vegetable
//    public static String USERV = "root";
//    public static String PASSWORDV = "MichaRoot6*";
//    public static String senadi_vegetable = "jdbc:mysql://localhost:3306/senadi_vegetable?useSSL=false&serverTimezone=UTC";
    public static String USERV = "root";
    public static String PASSWORDV = "B8GJuaxu4Y:2020";
    public static String senadi_vegetable = "jdbc:mysql://10.0.20.140:3306/senadi_vegetable?useSSL=false&serverTimezone=UTC";

    public static Connection doConnectionToAdmin() throws SQLException{
        Connection con = null;
        con = DriverManager.getConnection(iepi_admin, USER, PASSWORD);
        return con;
    }
    
    public static Connection doConnectionToFormularios() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(iepi_formularios, USER, PASSWORD);
        return con;
    }

    public static Connection doConnectionToDepurar() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(iepi_depurar, USER, PASSWORD);
        return con;
    }

    public static Connection doConnectionToCasilleros() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(iepi_casilleros, USER, PASSWORD);
        return con;
    }

    public static Connection doConnectionToVegetable() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(senadi_vegetable, USERV, PASSWORDV);
        return con;
    }
}
