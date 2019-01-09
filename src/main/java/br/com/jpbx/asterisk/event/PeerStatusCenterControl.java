/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk.event;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class PeerStatusCenterControl {
    
    private static Map<String,PeerStatusControl> peerStatusController;
    //private static List<PeerStatusControl> peerStatusController;

    public Map<String,PeerStatusControl> getPeerStatusController() {
        if(peerStatusController==null){
            peerStatusController=new HashMap<>();
           // System.out.println(":::::::::::::::::: CRIADO NOVO PSCC");
        }
        return peerStatusController;
    }

    public void setPeerStatusController(Map<String, PeerStatusControl> peerStatusController) {
        PeerStatusCenterControl.peerStatusController = peerStatusController;
    }

    

    
    

}
