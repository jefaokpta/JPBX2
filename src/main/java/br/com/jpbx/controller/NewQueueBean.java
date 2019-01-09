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
@Named(value = "newQueueBean")
@ViewScoped
public class NewQueueBean implements Serializable{

    private Queue queue;
    private Map<String,String> mohs;
    private Map<String,String> pickupGrps;
    private Map<String,String> songs;
    
    public NewQueueBean() {
        queue=new Queue();
        queue.setCompany((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCompany"));
        queue.setType("A");
        queue.setMonitorType(0);
        queue.setMonitorFormat("");
        queue.setEventmemberstatus(0);
        queue.setEventwhencalled(0);
        queue.setTimeoutrestart(0);
        queue.setRinginuse(0);
        queue.setAlertMail("");
        
        queue.setTimeout(15);
        queue.setAutopause("no");
        queue.setReportholdtime(0);
        queue.setServicelevel(5);
        
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
    }
    
    public String persistNewQueue(){
        if(!queue.getAnnounce().equals("select"))
            queue.setAnnounce("/etc/asterisk/jpbx/moh/"+queue.getAnnounce()+
                    "/"+queue.getAnnounce());       
        queue.setName("Queue"+System.currentTimeMillis());
        String ret=new QueueDAO().persistNewQueueTrue(queue);
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
