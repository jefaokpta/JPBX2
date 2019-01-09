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
 * @author jefaokpta
 */
@Entity @Table(name = "routes")
public class Route implements Serializable{
    @Column(name = "idroutes")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(name = "limit_bol")
    private int limitBol;
    @Column(name = "current_min")
    private int currentMin;
    @Column(name = "limit_control")
    private int limitControl;
    @Column(name = "day_cut")
    private int cutDay;
    @Column(name = "email_control")
    private int emailControl;
    private int timeout;
    @Column(name = "chan_limit")
    private int chanLimit;
    private String flags;
    
    @OneToMany(cascade = javax.persistence.CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "idroutes")
    private List<RouteTrunk> routes;

    public List<RouteTrunk> getRoutes() {
        return routes;
    }

    public int getChanLimit() {
        return chanLimit;
    }

    public void setChanLimit(int chanLimit) {
        this.chanLimit = chanLimit;
    }

    public void setRoutes(List<RouteTrunk> routes) {
        this.routes = routes;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
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

    public int getLimitBol() {
        return limitBol;
    }

    public void setLimitBol(int limitBol) {
        this.limitBol = limitBol;
    }

    public int getCurrentMin() {
        return currentMin;
    }

    public void setCurrentMin(int currentMin) {
        this.currentMin = currentMin;
    }

    public int getLimitControl() {
        return limitControl;
    }

    public void setLimitControl(int limitControl) {
        this.limitControl = limitControl;
    }

    public int getCutDay() {
        return cutDay;
    }

    public void setCutDay(int cutDay) {
        this.cutDay = cutDay;
    }

    public int getEmailControl() {
        return emailControl;
    }

    public void setEmailControl(int emailControl) {
        this.emailControl = emailControl;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }
    private int company;
}
