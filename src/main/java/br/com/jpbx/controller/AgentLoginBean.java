/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.asterisk.Asterisk;
import br.com.jpbx.model.Agent;
import br.com.jpbx.model.AgentDAO;
import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import br.com.jpbx.util.AgentLogged;
import br.com.jpbx.util.MD5Factory;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "agentLoginBean")
@RequestScoped
public class AgentLoginBean {

    private int peer;
    private int agent;
    private String pass;
    
    public AgentLoginBean() {
    }
    public String agentLogin(){
        Peer p=new PeerDAO().getPeerByName(peer);
        if(p==null){
            javax.faces.context.FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Ramal não encontrado. Verifique o ramal informado.",null));
            return null;
        }
        for (Agent a : new AgentDAO().getAllAgents(p.getCompany())) {
            if(a.getAgent()==agent){
                if(pass.equals(new MD5Factory().md5(a.getAgent()+":"+a.getPassword()))){
                   // try {
                        // EFETUAR VERIFICACAO DE RAMAL OU AGENTE EM USO
                        for (String s : new Asterisk().getInfos("database show")) {
                            if(s.contains("Agent/")){
                                int peerData=Integer.parseInt(s.substring(7, s.indexOf(" ")));
                                int agentData=Integer.parseInt(s.split(":")[1].trim());
                                if(peerData==peer){ // PODE JÀ ESTAR LOGADO
                                    if(agentData==agent){
                                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                                            put("agentLogged", new AgentLogged(a, peer, p.getId(),p.getCanal()));
                                        return "/agentLogged";
                                    }
                                    javax.faces.context.FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                                "No momento o Ramal "+peer+" está com outro Agente logado.",null));
                                    return null;
                                }
                                if(agentData==agent){
                                    if(new PeerDAO().getPeerByName(peerData).getCompany()==p.getCompany()){
                                        javax.faces.context.FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                                    "No momento o Agente "+agent+" está logado em outro Ramal.",null));
                                        return null;
                                    }
                                }
                            }                          
                        }
                        // EFETUA O LOGIN
                        new Asterisk().astDBAdd("Agent", String.valueOf(peer), agent+":"+p.getCanal()+":"+a.getName());
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                                            put("agentLogged", new AgentLogged(a, peer, p.getId(),p.getCanal()));
                        return "/agentLogged";
//                    } catch (IOException ex) {
//                        System.out.println("DEU RUIM AO PEGAR INFO ASTDB: "+ex.getMessage());
//                    }
                }
                javax.faces.context.FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Senha incorreta.",null));
                return null;
            }
        }
        javax.faces.context.FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Agente não encontrado. Verifique o Agente informado.",null));
            return null;
    }

    public int getPeer() {
        return peer;
    }

    public void setPeer(int peer) {
        this.peer = peer;
    }

    public int getAgent() {
        return agent;
    }

    public void setAgent(int agent) {
        this.agent = agent;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
}
