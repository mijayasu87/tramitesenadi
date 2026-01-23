/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author micharesp
 */
public class Operaciones {

    public static Timestamp formatStringToTimesTamp(String time) {
        String format = "yyyy-MM-dd HH:mm:ss"; // El formato de la cadena

        try {
            // Crear un SimpleDateFormat con el formato especificado
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            // Analizar la cadena para obtener un objeto Date
            Date parsedDate = dateFormat.parse(time);
            // Convertir el objeto Date en un objeto Timestamp
            Timestamp timestamp = new Timestamp(parsedDate.getTime());

            return timestamp;
        } catch (ParseException e) {
            // Manejar la excepción en caso de que la cadena no se pueda analizar
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Da formato a la fecha recibida en el siguiente orden 'yyyy-mm-dd'
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
//        System.out.println("--> "+date.toString());
        int dia = date.getDate();
        int mes = date.getMonth() + 1;
        int año = date.getYear() + 1900;
        String d = dia + "";
        String m = mes + "";
        if (dia < 10) {
            d = "0" + dia;
        }
        if (mes < 10) {
            m = "0" + mes;
        }

        String fecha = año + "-" + m + "-" + d;
//        System.out.println("<-- "+fecha+"\n");
        return fecha;
    }

    public static String formatTimesTamp(Timestamp timestamp) {        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String string = dateFormat.format(timestamp);
        return string;
    }

    public static List<String> tiposConfiguracionCC() {
        List<String> tipos = new ArrayList<>();
        tipos.add("ACCIÓN DE PERSONAL");
        tipos.add("RESOLUCIÓN");
//        tipos.add("DELEGADO");
//        tipos.add("DELEGACIÓN");
        tipos.add("DELEGADO");
        tipos.add("DELEGACIÓN");
        return tipos;
    }

    /**
     * Da formato a una fecha recibida, en el siguiente orden 'Dddddd dd de
     * Mmmmm de yyyy'
     */
    public static String formatDateToLarge(Date fecha) {

        Date aux = fecha;

//        aux.setDate(aux.getDate()+1);
        int dia = aux.getDate();
        int mes = aux.getMonth();
        int año = aux.getYear() + 1900;
//        int diasem = fecha.getDay();
        String fec = dia + " de " + getMes(mes) + " de " + año;  //getDia(diasem) + " " + 
        return fec.toLowerCase();
    }

    public static String getDia(int dia) {
        switch (dia) {
            case 1:
                return "Lunes";
            case 2:
                return "Martes";
            case 3:
                return "Miércoles";
            case 4:
                return "Jueves";
            case 5:
                return "Viernes";
            case 6:
                return "Sábado";
            case 0:
                return "Domingo";
            default:
                return "Error";
        }
    }

    public static String getMes(int mes) {
        switch (mes) {
            case 0:
                return "Enero";
            case 1:
                return "Febrero";
            case 2:
                return "Marzo";
            case 3:
                return "Abril";
            case 4:
                return "Mayo";
            case 5:
                return "Junio";
            case 6:
                return "Julio";
            case 7:
                return "Agosto";
            case 8:
                return "Septiembre";
            case 9:
                return "Octubre";
            case 10:
                return "Noviembre";
            case 11:
                return "Diciembre";
            default:
                return "Error";
        }
    }
}
