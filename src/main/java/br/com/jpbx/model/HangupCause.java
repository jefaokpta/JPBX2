/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.io.Serializable;
import java.util.Date;
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
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Entity @Table(name = "hangup_cause")
public class HangupCause implements Serializable{
    @Column(name = "idhangup_cause")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date datetime;
    @Column(name = "trunk_id")
    private int trunk;
    private String uniqueid;
    private int code;
    @Column(name = "dial_status")
    private String dialStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getTrunk() {
        return trunk;
    }

    public void setTrunk(int trunk) {
        this.trunk = trunk;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDialStatus() {
        return dialStatus;
    }

    public void setDialStatus(String dialStatus) {
        this.dialStatus = dialStatus;
    }
}
