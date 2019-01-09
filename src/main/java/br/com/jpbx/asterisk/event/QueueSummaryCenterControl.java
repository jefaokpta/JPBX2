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
public class QueueSummaryCenterControl {
    
    private static Map<String,QueueSummaryControl> queueSummary;

    public  Map<String, QueueSummaryControl> getQueueSummary() {
        if(queueSummary==null)
            queueSummary=new HashMap<>();
        return queueSummary;
    }

    public  void setQueueSummary(Map<String, QueueSummaryControl> queueSummary) {
        QueueSummaryCenterControl.queueSummary = queueSummary;
    }
    
    

}
