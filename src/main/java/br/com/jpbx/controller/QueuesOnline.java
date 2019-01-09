/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.asterisk.event.QueueSummaryCenterControl;
import br.com.jpbx.asterisk.event.QueueSummaryControl;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueDAO;
import br.com.jpbx.util.FormaterSeconds;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "queuesOnline")
@ViewScoped
public class QueuesOnline implements Serializable{

    private List<QueueSummaryControl> qscs;
    private List<Queue> queues;
    private Map<String,QueueSummaryControl> qscc;
    
    public QueuesOnline() {
        int currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
        queues=new QueueDAO().getAllQueues(currentCompany);
        qscc=new QueueSummaryCenterControl().getQueueSummary();
        qscs=new ArrayList<>();
    }

    public List<QueueSummaryControl> getQscs() {
        qscs.clear();
        for (Queue q : queues) {
            qscs.add(qscc.get(q.getName()));
        }
        return qscs;
    }
    public String queueMonitor(QueueSummaryControl qsc){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("queueMonitor", qsc);
        return "queueMonitor";
    }
    public String formatSeconds(int secs){
        return new FormaterSeconds().secondsToHours(secs);
    }
    
}
