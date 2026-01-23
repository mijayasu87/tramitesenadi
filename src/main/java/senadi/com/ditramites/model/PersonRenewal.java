/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senadi.com.ditramites.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Michael Yanangómez
 */
@Entity
@Table(name = "person_renewal")
public class PersonRenewal implements Serializable {
    //default serial version id, required for serializable classes.

    private static final long serialVersionUID = 1L;
    
    @Column(name = "renewal_form_id", insertable = false, updatable = false)
    private int renewalFormId;

    @Column(name = "person_id", insertable = false, updatable = false)
    private int personId;
    
    @Id
    @Column(name = "type")
    private String type;

    public PersonRenewal() {
    }

    public int getRenewalFormId() {
        return this.renewalFormId;
    }

    public void setRenewalFormId(int renewalFormId) {
        this.renewalFormId = renewalFormId;
    }

    public int getPersonId() {
        return this.personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
