/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Entity @Table(name = "trunk_costs")
public class TrunkCost implements Serializable{

    @Column(name = "idtrunk_costs")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "trunk_id")
    private int trunk;
    private float fare;
    private int cicle;
    private int fraction;
    private int shortage;
    private int ccost;
    @Column(name = "tx_service")
    private float txService;

    public TrunkCost() {
    }

    public TrunkCost(int trunk, int ccost) {
        this.trunk = trunk;
        this.fare = 0;
        this.cicle = 60;
        this.fraction = 6;
        this.shortage = 0;
        this.ccost = ccost;
        this.txService = 0;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrunk() {
        return trunk;
    }

    public void setTrunk(int trunk) {
        this.trunk = trunk;
    }

    public float getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    public int getCicle() {
        return cicle;
    }

    public void setCicle(int cicle) {
        this.cicle = cicle;
    }

    public int getFraction() {
        return fraction;
    }

    public void setFraction(int fraction) {
        this.fraction = fraction;
    }

    public int getShortage() {
        return shortage;
    }

    public void setShortage(int shortage) {
        this.shortage = shortage;
    }

    public int getCcost() {
        return ccost;
    }

    public void setCcost(int ccost) {
        this.ccost = ccost;
    }

    public float getTxService() {
        return txService;
    }

    public void setTxService(float txService) {
        this.txService = txService;
    }
    
    
}
