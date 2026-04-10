/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.model.postgres.polaris;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author michael
 */
@Entity
@Table(name = "ipasapi", schema = "senadi_polaris")
public class IpasApi implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ipasapi_seq")
    @SequenceGenerator(name = "ipasapi_seq", sequenceName = "ipasapi_id_seq", schema = "senadi_polaris", allocationSize = 1)
    private Integer id;
    
    @Column(name = "cognito_access_token")
    private String cognitoAccessToken;
    
    @Column(name = "x_access_token")
    private String xAccessToken;
    
    @Column(name = "fecha_inicio")
    private Timestamp fechaInicio;
    
    @Column(name = "fecha_fin")
    private Timestamp fechaFin;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the cognitoAccessToken
     */
    public String getCognitoAccessToken() {
        return cognitoAccessToken;
    }

    /**
     * @param cognitoAccessToken the cognitoAccessToken to set
     */
    public void setCognitoAccessToken(String cognitoAccessToken) {
        this.cognitoAccessToken = cognitoAccessToken;
    }

    /**
     * @return the xAccessToken
     */
    public String getxAccessToken() {
        return xAccessToken;
    }

    /**
     * @param xAccessToken the xAccessToken to set
     */
    public void setxAccessToken(String xAccessToken) {
        this.xAccessToken = xAccessToken;
    }  

    /**
     * @return the fechaInicio
     */
    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Timestamp getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }
}
