/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Entity @Table(name = "acl")
public class Acl implements Serializable{

    public Acl() {
    }

    public Acl(String ip, String mask, String dns) {
        this.ip = ip;
        this.mask = mask;
        this.dns = dns;
    }
    
    

    @Column(name = "idacl")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "profile_id")
    private int profileId;   
    private String ip;
    private String mask;
    private String dns;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date datetime;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.ip);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Acl other = (Acl) obj;
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        return true;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }


    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    
    
}
