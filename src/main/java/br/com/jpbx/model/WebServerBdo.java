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
@Entity @Table(name = "webserv_bdo")
public class WebServerBdo implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idwebserv_bdo")
    private int id;
    private String name;
    private String url;
    private int method;
    private int auth;
    private String params;
    @Column(name = "auth_user")
    private String authUser;
    @Column(name = "auth_pass")
    private String authPass;
    private int csp;

    public int getCsp() {
        return csp;
    }

    public void setCsp(int csp) {
        this.csp = csp;
    }


    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getAuthUser() {
        return authUser;
    }

    public void setAuthUser(String authUser) {
        this.authUser = authUser;
    }

    public String getAuthPass() {
        return authPass;
    }

    public void setAuthPass(String authPass) {
        this.authPass = authPass;
    }
    private int company;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }


    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }
    
}
