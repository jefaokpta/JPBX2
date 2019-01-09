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
@Entity @Table(name = "trunks")
public class Trunk implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trunk_id")
    private int id;
    @Column(name = "trunk_name")
    private String name;
    private String type;
    private String context;
    @Column(name = "lang")
    private String language;
    @Column(name = "fromuser")
    private String fromUser;
    @Column(name = "canreinvite")
    private String canReinvite;
    private String reinvite;
    @Column(name = "dtmfmode")
    private String dtmfMode;
    @Column(name = "host_trunk")
    private String host;
    @Column(name = "fromdomain")
    private String fromDomain;
    private String qualify;
    private String nat;
    @Column(name = "call_limit")
    private int callLimit;
    private String insecure;
    private String allow;
    @Column(name = "allow_video")
    private String allowVideo;
    private String username;
    private String secret;
    @Column(name = "requirecalltoken")
    private String requireCallToken;
    private String tecnology;
    private int reception;
    private String register;
    @Column(name = "ddr_prefix")
    private String ddrPrefix;
    @Column(name = "ddr_start")
    private String ddrStart;
    @Column(name = "ddr_end")
    private String ddrEnd;
    @Column(name = "number_board")
    private int numBoard;
    private int ocupation;
    @Column(name = "chan_range_start")
    private int chanRangeStart;
    @Column(name = "chan_range_end")
    private int chanRangeEnd;
    @Column(name = "tech_prefix")
    private String techPrefix;
    private String canal;
    private int company;
    @Column(name = "advanced_text")
    private String advancedText;
    private int record;
    private String flag;
    @Column(name = "iax_trunk")
    private int iaxTrunk;
    @Column(name = "tech_digital")
    private String techDigital;

    public String getTechDigital() {
        return techDigital;
    }

    public void setTechDigital(String techDigital) {
        this.techDigital = techDigital;
    }

    public int getIaxTrunk() {
        return iaxTrunk;
    }

    public void setIaxTrunk(int iaxTrunk) {
        this.iaxTrunk = iaxTrunk;
    }

   

    public String getDdrPrefix() {
        return ddrPrefix;
    }

    public void setDdrPrefix(String ddrPrefix) {
        this.ddrPrefix = ddrPrefix;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public String getAdvancedText() {
        return advancedText;
    }

    public void setAdvancedText(String advancedText) {
        this.advancedText = advancedText;
    }

    public String getTechPrefix() {
        return techPrefix;
    }

    public void setTechPrefix(String techPrefix) {
        this.techPrefix = techPrefix;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getCanReinvite() {
        return canReinvite;
    }

    public void setCanReinvite(String canReinvite) {
        this.canReinvite = canReinvite;
    }

    public String getReinvite() {
        return reinvite;
    }

    public void setReinvite(String reinvite) {
        this.reinvite = reinvite;
    }

    public String getDtmfMode() {
        return dtmfMode;
    }

    public void setDtmfMode(String dtmfMode) {
        this.dtmfMode = dtmfMode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFromDomain() {
        return fromDomain;
    }

    public void setFromDomain(String fromDomain) {
        this.fromDomain = fromDomain;
    }

    public String getQualify() {
        return qualify;
    }

    public void setQualify(String qualify) {
        this.qualify = qualify;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public int getCallLimit() {
        return callLimit;
    }

    public void setCallLimit(int callLimit) {
        this.callLimit = callLimit;
    }

    public String getInsecure() {
        return insecure;
    }

    public void setInsecure(String insecure) {
        this.insecure = insecure;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getAllowVideo() {
        return allowVideo;
    }

    public void setAllowVideo(String allowVideo) {
        this.allowVideo = allowVideo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRequireCallToken() {
        return requireCallToken;
    }

    public void setRequireCallToken(String requireCallToken) {
        this.requireCallToken = requireCallToken;
    }

    public String getTecnology() {
        return tecnology;
    }

    public void setTecnology(String tecnology) {
        this.tecnology = tecnology;
    }

    public int getReception() {
        return reception;
    }

    public void setReception(int reception) {
        this.reception = reception;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }


    public String getDdrStart() {
        return ddrStart;
    }

    public void setDdrStart(String ddrStart) {
        this.ddrStart = ddrStart;
    }

    public String getDdrEnd() {
        return ddrEnd;
    }

    public void setDdrEnd(String ddrEnd) {
        this.ddrEnd = ddrEnd;
    }


    public int getNumBoard() {
        return numBoard;
    }

    public void setNumBoard(int numBoard) {
        this.numBoard = numBoard;
    }

    public int getOcupation() {
        return ocupation;
    }

    public void setOcupation(int ocupation) {
        this.ocupation = ocupation;
    }

    public int getChanRangeStart() {
        return chanRangeStart;
    }

    public void setChanRangeStart(int chanRangeStart) {
        this.chanRangeStart = chanRangeStart;
    }

    public int getChanRangeEnd() {
        return chanRangeEnd;
    }

    public void setChanRangeEnd(int chanRangeEnd) {
        this.chanRangeEnd = chanRangeEnd;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }
    
    
}
