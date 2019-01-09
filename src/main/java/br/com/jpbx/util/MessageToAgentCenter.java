/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class MessageToAgentCenter implements Serializable{

    private static Map<String,MessageToAgent> agentMsgs;

    public Map<String, MessageToAgent> getAgentMsgs() {
        if(agentMsgs==null)
            agentMsgs=new HashMap<>();
        return agentMsgs;
    }

    public void setAgentMsgs(Map<String, MessageToAgent> agentMsgs) {
        this.agentMsgs = agentMsgs;
    }
    
    
    
}
