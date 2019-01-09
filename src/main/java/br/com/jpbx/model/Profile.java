/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;



/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Entity @Table(name = "acl_profile")
public class Profile implements Serializable{
    @Column(name = "profile_id")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int limited;
    @Column(name = "current_value")
    private float currentValue;
    @Column(name = "secure_value")
    private float secureValue;
    @Column(name = "cut_date")
    private int cutDate;
    
    @OneToMany(cascade = javax.persistence.CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private List<Acl> acls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLimited() {
        return limited;
    }

    public void setLimited(int limited) {
        this.limited = limited;
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }

    public float getSecureValue() {
        return secureValue;
    }

    public void setSecureValue(float secureValue) {
        this.secureValue = secureValue;
    }

    public int getCutDate() {
        return cutDate;
    }

    public void setCutDate(int cutDate) {
        this.cutDate = cutDate;
    }

    public List<Acl> getAcls() {
        return acls;
    }

    public void setAcls(List<Acl> acls) {
        this.acls = acls;
    }
    
    
}
