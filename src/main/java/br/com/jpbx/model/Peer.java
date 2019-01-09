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
@Entity @Table(name = "peers")
public class Peer implements Serializable,Comparable<Peer>{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int name;
    @Column(name = "password")
    private int facilityPass;
    private String callgroup;
    private String callerid;
    private String context;
    private String dtmfmode;
    @Column(name = "host_peer")
    private String host;
    @Column(name = "lang")
    private String language;
    private int mailbox;
    private String md5secret;
    private String nat;
    private int pickupgroup;
    private String qualify;
    private String secret;
    private String type;
    private String username;
    private String allow;
    @Column(name = "allow_video")
    private String allowVideo;
    private String musiconhold;
    private String email;
    private String canal;
    @Column(name = "call_limit")
    private int callLimit;
    @Column(name = "peer_type")
    private String peerType;
    private int dnd;
    private String fwd;
    private int record;
    @Column(name = "locked")
    private int lock;
    private int company;
    private int canreinvite;
    @Column(name = "auth")
    private int authorization;
    @Column(name = "rtcp_mux")
    private String rtcpMux;
    
    public Peer() {
    }

    public Peer(Peer p) {
        this.id = p.getId();
        this.name = p.getName();
        this.facilityPass = p.getFacilityPass();
        this.callgroup = p.getCallgroup();
        this.callerid = p.getCallerid()+" "+p.getName();
        this.context = p.getContext();
        this.dtmfmode = p.getDtmfmode();
        this.host = p.getHost();
        this.language = p.getLanguage();
        this.mailbox = p.getMailbox();
        this.md5secret = p.getMd5secret();
        this.nat = p.getNat();
        this.pickupgroup = p.getPickupgroup();
        this.qualify = p.getQualify();
        this.secret = p.getSecret();
        this.type = p.getType();
        this.username = p.getUsername();
        this.allow = p.getAllow();
        this.allowVideo = p.getAllowVideo();
        this.musiconhold = p.getMusiconhold();
        this.email = p.getEmail();
        this.canal = p.getCanal();
        this.callLimit = p.getCallLimit();
        this.peerType = p.getPeerType();
        this.dnd = p.getDnd();
        this.fwd = p.getFwd();
        this.record = p.getRecord();
        this.lock = p.getLock();
        this.company = p.getCompany();
        this.canreinvite = p.getCanreinvite();
        this.authorization = p.getAuthorization();
        this.rtcpMux=p.getRtcpMux();
    }

    public String getRtcpMux() {
        return rtcpMux;
    }

    public void setRtcpMux(String rtcpMux) {
        this.rtcpMux = rtcpMux;
    }

    

    

    public int getCanreinvite() {
        return canreinvite;
    }

    public void setCanreinvite(int canreivite) {
        this.canreinvite = canreivite;
    }

    public int getAuthorization() {
        return authorization;
    }

    public void setAuthorization(int authorization) {
        this.authorization = authorization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getFacilityPass() {
        return facilityPass;
    }

    public void setFacilityPass(int facilityPass) {
        this.facilityPass = facilityPass;
    }

    public String getCallgroup() {
        return callgroup;
    }

    public void setCallgroup(String callgroup) {
        this.callgroup = callgroup;
    }

    public String getCallerid() {
        return callerid;
    }

    public void setCallerid(String callerid) {
        this.callerid = callerid;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDtmfmode() {
        return dtmfmode;
    }

    public void setDtmfmode(String dtmfmode) {
        this.dtmfmode = dtmfmode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getMailbox() {
        return mailbox;
    }

    public void setMailbox(int mailbox) {
        this.mailbox = mailbox;
    }


    public String getMd5secret() {
        return md5secret;
    }

    public void setMd5secret(String md5secret) {
        this.md5secret = md5secret;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public int getPickupgroup() {
        return pickupgroup;
    }

    public void setPickupgroup(int pickupgroup) {
        this.pickupgroup = pickupgroup;
    }

    public String getQualify() {
        return qualify;
    }

    public void setQualify(String qualify) {
        this.qualify = qualify;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getMusiconhold() {
        return musiconhold;
    }

    public void setMusiconhold(String musiconhold) {
        this.musiconhold = musiconhold;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public int getCallLimit() {
        return callLimit;
    }

    public void setCallLimit(int callLimit) {
        this.callLimit = callLimit;
    }

    public String getPeerType() {
        return peerType;
    }

    public void setPeerType(String peerType) {
        this.peerType = peerType;
    }


    public int getDnd() {
        return dnd;
    }

    public void setDnd(int dnd) {
        this.dnd = dnd;
    }

    public String getFwd() {
        return fwd;
    }

    public void setFwd(String fwd) {
        this.fwd = fwd;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public int getLock() {
        return lock;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getAllowVideo() {
        return allowVideo;
    }

    public void setAllowVideo(String allowVideo) {
        this.allowVideo = allowVideo;
    }

    @Override
    public int compareTo(Peer peer) {
        if(this.name>peer.name)
            return 1;
        else if(this.name<peer.name)
            return -1;
        return this.getCallerid().compareToIgnoreCase(peer.getCallerid());
    }
    
    
}
