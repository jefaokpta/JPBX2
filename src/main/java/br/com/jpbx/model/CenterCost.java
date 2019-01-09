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
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Entity @Table(name = "ccosts")
public class CenterCost implements Serializable {

    @Column(name = "idccosts")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "ccost_name")
    private String name;
    private float fare;
    private int cicle;
    private int fraction;
    private int shortage;
    private float ccost;
    @Column(name = "tx_service")
    private float txService;
    private int company;

    public CenterCost() {
    }

    public CenterCost(String name, float fare, int cicle, int fraction, int shortage, float ccost, float txService, int company) {
        this.name = name;
        this.fare = fare;
        this.cicle = cicle;
        this.fraction = fraction;
        this.shortage = shortage;
        this.ccost = ccost;
        this.txService = txService;
        this.company = company;
    }
    
    

    public float getTxService() {
        return txService;
    }

    public void setTxService(float txService) {
        this.txService = txService;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

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

    public float getCcost() {
        return ccost;
    }

    public void setCcost(float ccost) {
        this.ccost = ccost;
    }


}
