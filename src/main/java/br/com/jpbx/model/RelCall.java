/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jefaokpta
 */
@Entity @Table(name = "relcalls")
public class RelCall implements Serializable{
    @Column(name = "id")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date calldate;
    private String src;
    private String dst;
    private String srcfinal;
    private String dstfinal;
    private int duration;
    private String disposition;
    private int billsec;
    private int accountcode;
    private String uniqueid;
    private String channel;
    @Column(name = "trunk_id")
    private int trunkId;
    private String accode;
    private int hide;
    private int company;
    private int route;
    private int profile;
    @Column(name = "debited_route")
    private int debitRoute;
    @Column(name = "debited_profile")
    private int debitProfile;
    
    @OneToMany(cascade = javax.persistence.CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "uniqueid",referencedColumnName = "uniqueid")
    @OrderBy("id ASC")
    private List<RelCallHistory> callHistory;

    public List<RelCallHistory> getCallHistory() {
        return callHistory;
    }

    public void setCallHistory(List<RelCallHistory> callHistory) {
        this.callHistory = callHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSrcfinal() {
        return srcfinal;
    }

    public void setSrcfinal(String srcfinal) {
        this.srcfinal = srcfinal;
    }

    public String getDstfinal() {
        return dstfinal;
    }

    public void setDstfinal(String dstfinal) {
        this.dstfinal = dstfinal;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public int getBillsec() {
        return billsec;
    }

    public void setBillsec(int billsec) {
        this.billsec = billsec;
    }

    public int getAccountcode() {
        return accountcode;
    }

    public void setAccountcode(int accountcode) {
        this.accountcode = accountcode;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getTrunkId() {
        return trunkId;
    }

    public void setTrunkId(int trunkId) {
        this.trunkId = trunkId;
    }

    public String getAccode() {
        return accode;
    }

    public void setAccode(String accode) {
        this.accode = accode;
    }

    public int getHide() {
        return hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }


    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getDebitRoute() {
        return debitRoute;
    }

    public void setDebitRoute(int debitRoute) {
        this.debitRoute = debitRoute;
    }

    public int getDebitProfile() {
        return debitProfile;
    }

    public void setDebitProfile(int debitProfile) {
        this.debitProfile = debitProfile;
    }
    
    
}
