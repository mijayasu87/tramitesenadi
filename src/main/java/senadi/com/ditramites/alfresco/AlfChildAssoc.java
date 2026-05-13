/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.alfresco;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author michael
 */
@Entity
@Table(name = "alf_node", schema = "public")
public class AlfChildAssoc implements Serializable{
    
    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "child_node_id")
    private Integer childNodeId;
    
    @Column(name = "child_node_name")
    private String childNodeName;

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
     * @return the childNodeId
     */
    public Integer getChildNodeId() {
        return childNodeId;
    }

    /**
     * @param childNodeId the childNodeId to set
     */
    public void setChildNodeId(Integer childNodeId) {
        this.childNodeId = childNodeId;
    }

    /**
     * @return the childNodeName
     */
    public String getChildNodeName() {
        return childNodeName;
    }

    /**
     * @param childNodeName the childNodeName to set
     */
    public void setChildNodeName(String childNodeName) {
        this.childNodeName = childNodeName;
    }
}
