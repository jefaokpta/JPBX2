/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import br.com.jpbx.model.Agent;
import java.io.Serializable;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class AgentLogged implements Serializable{
    
    private Agent agent;
    private int peer;
    private int peerId;
    private String canal;

    public AgentLogged() {
    }

    public AgentLogged(Agent agent, int peer, int peerId,String canal) {
        this.agent = agent;
        this.peer = peer;
        this.peerId = peerId;
        this.canal = canal;
    }

    
    
    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public int getPeer() {
        return peer;
    }

    public void setPeer(int peer) {
        this.peer = peer;
    }

    public int getPeerId() {
        return peerId;
    }

    public void setPeerId(int peerId) {
        this.peerId = peerId;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

}
