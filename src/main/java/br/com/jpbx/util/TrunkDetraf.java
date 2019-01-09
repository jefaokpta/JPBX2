/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class TrunkDetraf implements Serializable{
    private Date calldate;
    private String src;
    private String dst;
    private String duration;
    private String billsec;
    private String acc;
    private float cost;
    private float sale;
    private float profit;
    private String trunk;

    public TrunkDetraf() {
    }

    public TrunkDetraf(Date calldate, String src, String dst, String duration, String billsec, String acc, float cost, float sale, float profit, String trunk) {
        this.calldate = calldate;
        this.src = src;
        this.dst = dst;
        this.duration = duration;
        this.billsec = billsec;
        this.acc = acc;
        this.cost = cost;
        this.sale = sale;
        this.profit = profit;
        this.trunk = trunk;
    }
    
    

    public Date getCalldate() {
        return calldate;
    }

    public void setCalldate(Date calldate) {
        this.calldate = calldate;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBillsec() {
        return billsec;
    }

    public void setBillsec(String billsec) {
        this.billsec = billsec;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }


    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public String getTrunk() {
        return trunk;
    }

    public void setTrunk(String trunk) {
        this.trunk = trunk;
    }

  
    
    

}
