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
public class QueueStatusCenterControl {
    
    private static Map<String,QueueStatusControl> qstcc;

    public Map<String, QueueStatusControl> getQstcc() {
        if(qstcc==null)
            qstcc=new HashMap<>();
        return qstcc;
    }

    public void setQstcc(Map<String, QueueStatusControl> qstcc) {
        QueueStatusCenterControl.qstcc = qstcc;
    }
    
    

}
