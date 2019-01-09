/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Moh;
import br.com.jpbx.model.MohDAO;
import br.com.jpbx.model.PickupGrp;
import br.com.jpbx.model.PickupGrpDAO;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueDAO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "editQueueBean")
@ViewScoped
public class EditQueueBean implements Serializable{

    private Queue queue;
    private Map<String,String> mohs;
    private Map<String,String> pickupGrps;
    private Map<String,String> songs;
    
    public EditQueueBean() {
        queue=(Queue) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("editQueue");
        List<Moh> songsMoh=new MohDAO().getAllMohs();
        songs=new HashMap<>();
        for (Moh m : songsMoh) {
            if(m.getCompany()==queue.getCompany())
                songs.put(m.getName(), "/etc/asterisk/jpbx/moh/"+m.getMoh()+"/"+m.getMoh());
        }
        mohs=new HashMap<>();
        for (Moh m : songsMoh) {
            if(m.getCompany()==queue.getCompany())
                mohs.put(m.getName(), m.getMoh());
        }
        pickupGrps=new HashMap<>();
        for (PickupGrp p : new PickupGrpDAO().getAllGrps()) {
            if(p.getCompany()==queue.getCompany())
                pickupGrps.put(p.getName(), String.valueOf(p.getId()));
        }
        if(queue.getQueueYouAreNext()==null)
            queue.setQueueYouAreNext("pattern");
        if(queue.getQueueThereAre()==null)
            queue.setQueueThereAre("pattern");
        if(queue.getQueueCallsWaiting()==null)
            queue.setQueueCallsWaiting("pattern");
        if(queue.getQueueHoldTime()==null)
            queue.setQueueHoldTime("pattern");
        if(queue.getQueueThankYou()==null)
            queue.setQueueThankYou("pattern");
        if(queue.getQueueReportHold()==null)
            queue.setQueueReportHold("pattern");
    }
    public String updateQueue(){      
        if(queue.getQueueYouAreNext().equals("pattern"))
            queue.setQueueYouAreNext(null);
        if(queue.getQueueThereAre().equals("pattern"))
            queue.setQueueThereAre(null);
        if(queue.getQueueCallsWaiting().equals("pattern"))
            queue.setQueueCallsWaiting(null);
        if(queue.getQueueHoldTime().equals("pattern"))
            queue.setQueueHoldTime(null);
        if(queue.getQueueThankYou().equals("pattern"))
            queue.setQueueThankYou(null);
        if(queue.getQueueReportHold().equals("pattern"))
            queue.setQueueReportHold(null);
        String ret=new QueueDAO().updateQueue(queue);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/queues";
    }
    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Map<String, String> getMohs() {
        return mohs;
    }

    public Map<String, String> getPickupGrps() {
        return pickupGrps;
    }

    public Map<String, String> getSongs() {
        return songs;
    }
    
}
