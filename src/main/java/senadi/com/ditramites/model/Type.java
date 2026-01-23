/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senadi.com.ditramites.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Michael Yanangómez
 */
@Entity
@Table(name = "types")
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private String alias;

    private String name;

    //bi-directional many-to-one association to FormType
    @OneToMany(mappedBy = "type")
    private List<FormType> formTypes;

    //bi-directional many-to-one association to Type
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Type type;

    //bi-directional many-to-one association to Type
    @OneToMany(mappedBy = "type")
    private List<Type> types;

    public Type() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FormType> getFormTypes() {
        return this.formTypes;
    }

    public void setFormTypes(List<FormType> formTypes) {
        this.formTypes = formTypes;
    }

    public FormType addFormType(FormType formType) {
        getFormTypes().add(formType);
        formType.setType(this);

        return formType;
    }

    public FormType removeFormType(FormType formType) {
        getFormTypes().remove(formType);
        formType.setType(null);

        return formType;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Type> getTypes() {
        return this.types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Type addType(Type type) {
        getTypes().add(type);
        type.setType(this);

        return type;
    }

    public Type removeType(Type type) {
        getTypes().remove(type);
        type.setType(null);

        return type;
    }

}
