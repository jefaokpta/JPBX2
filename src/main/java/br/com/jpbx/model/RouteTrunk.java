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
 * @author jefaokpta
 */
@Entity @Table(name = "routes_trunks")
public class RouteTrunk implements Serializable{
    @Column(name = "idroutes_trunks")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "idroutes")
    private int routeId;
    @Column(name = "trunk_id1")
    private int trunkId1;
    @Column(name = "trunk_id2")
    private int trunkId2;
    @Column(name = "trunk_id3")
    private int trunkId3;
    private int ccost;

    public RouteTrunk() {
    }

    public RouteTrunk(int trunkId1, int trunkId2, int trunkId3, int ccost) {
        this.trunkId1 = trunkId1;
        this.trunkId2 = trunkId2;
        this.trunkId3 = trunkId3;
        this.ccost = ccost;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getTrunkId1() {
        return trunkId1;
    }

    public void setTrunkId1(int trunkId1) {
        this.trunkId1 = trunkId1;
    }

    public int getTrunkId2() {
        return trunkId2;
    }

    public void setTrunkId2(int trunkId2) {
        this.trunkId2 = trunkId2;
    }

    public int getTrunkId3() {
        return trunkId3;
    }

    public void setTrunkId3(int trunkId3) {
        this.trunkId3 = trunkId3;
    }

    

    public int getCcost() {
        return ccost;
    }

    public void setCcost(int ccost) {
        this.ccost = ccost;
    }

 
    
    
}
