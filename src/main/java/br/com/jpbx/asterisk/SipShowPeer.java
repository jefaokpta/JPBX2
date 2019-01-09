/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.asterisk;

/**
 *
 * @author jefaokpta
 */
public class SipShowPeer {

    public SipShowPeer(String qualify) {
    }

    
    public SipShowPeer(String name, String host, String dyn, String forcerPort) {
        this.name = name;
        this.host = host;
        this.dyn = dyn;
        this.forcerPort = forcerPort;
       // this.qualify=qualify;
    }
    
    
    private String name;
    private String host;
    private String dyn;
    private String forcerPort;
    private String qualify;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForcerPort() {
        return forcerPort;
    }

    public void setForcerPort(String forcerPort) {
        this.forcerPort = forcerPort;
    }

    public String getQualify() {
        return qualify;
    }

    public void setQualify(String qualify) {
        this.qualify = qualify;
    }


    

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDyn() {
        return dyn;
    }

    public void setDyn(String dyn) {
        this.dyn = dyn;
    }

    
    
    
}
