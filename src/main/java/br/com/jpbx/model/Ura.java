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
@Entity @Table(name = "uras")
public class Ura implements Serializable{
    @Column(name = "iduras")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(name = "web_name")
    private String webName;
    @Column(name = "lang")
    private String language;
    @Column(name = "time_response")
    private int interactTimeout;
    @Column(name = "time_digit")
    private int digitTimeout;
    private String background;
    @Column(name = "invalid")
    private String invalidAction;
    @Column(name = "invalid_value")
    private String invalidParam;
    @Column(name = "timeout")
    private String timeoutAction;
    @Column(name = "timeout_value")
    private String timeoutParam;
    @Column(name = "peers")
    private int dialPeers;
    private int company;
    
    @OneToMany(cascade = javax.persistence.CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "iduras")
    private List<UraOption> uraOptions;

    public List<UraOption> getUraOptions() {
        return uraOptions;
    }

    public void setUraOptions(List<UraOption> uraOptions) {
        this.uraOptions = uraOptions;
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

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getInteractTimeout() {
        return interactTimeout;
    }

    public void setInteractTimeout(int interactTimeout) {
        this.interactTimeout = interactTimeout;
    }

    public int getDigitTimeout() {
        return digitTimeout;
    }

    public void setDigitTimeout(int digitTimeout) {
        this.digitTimeout = digitTimeout;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getInvalidAction() {
        return invalidAction;
    }

    public void setInvalidAction(String invalidAction) {
        this.invalidAction = invalidAction;
    }

    public String getInvalidParam() {
        return invalidParam;
    }

    public void setInvalidParam(String invalidParam) {
        this.invalidParam = invalidParam;
    }

    public String getTimeoutAction() {
        return timeoutAction;
    }

    public void setTimeoutAction(String timeoutAction) {
        this.timeoutAction = timeoutAction;
    }

    public String getTimeoutParam() {
        return timeoutParam;
    }

    public void setTimeoutParam(String timeoutParam) {
        this.timeoutParam = timeoutParam;
    }

    public int getDialPeers() {
        return dialPeers;
    }

    public void setDialPeers(int dialPeers) {
        this.dialPeers = dialPeers;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }
    
}
