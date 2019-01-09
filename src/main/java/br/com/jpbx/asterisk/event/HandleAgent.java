/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk.event;

import br.com.jpbx.util.MessageToAgent;
import br.com.jpbx.util.MessageToAgentCenter;
import java.util.Map;
import org.asteriskjava.manager.event.AgentCalledEvent;
import org.asteriskjava.manager.event.AgentConnectEvent;
import org.asteriskjava.manager.event.QueueCallerAbandonEvent;
import org.asteriskjava.manager.event.QueueCallerLeaveEvent;
import org.asteriskjava.manager.event.QueueEntryEvent;
import org.asteriskjava.manager.event.QueueMemberEvent;
import org.asteriskjava.manager.event.QueueMemberPauseEvent;
import org.asteriskjava.manager.event.QueueMemberRemovedEvent;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class HandleAgent {

    private Map<String,QueueStatusControl> qstcc;
    private QueueStatusControl qsc;
    public HandleAgent() {
        qstcc=new QueueStatusCenterControl().getQstcc();
        
    }

    public void handle(QueueMemberEvent qme){
        if(!qme.getQueue().substring(0, 5).equals("Queue"))
            return;
        qsc=qstcc.get(qme.getQueue());
        for (QueueAgent qa : qsc.getAgents()) {
            if(qa.getAgent().equals(qme.getName())){
                qa.setPause(qme.getPaused());
                qa.setPenalty(qme.getPenalty());
                qa.setCallsTaken(qme.getCallsTaken());
                qa.setLastCall(qme.getLastCall());
                qa.setStatus(qme.getStatus());
                return;
            }
        }
        PeerStatusControl psc=new PeerStatusCenterControl().getPeerStatusController().get(qme.getLocation());
        if(psc==null){
            psc=new PeerStatusControl(qme.getLocation(), "Unregistered", "");
            new PeerStatusCenterControl().getPeerStatusController().put(qme.getLocation(), psc);
        }
        qsc.getAgents().add(new QueueAgent(qme.getName(), qme.getQueue(), qme.getPenalty(),
                qme.getCallsTaken(), qme.getLastCall(), qme.getStatus(),(qme.getPaused()?qme.getPausedreason():"Disponível"),
        psc));
    }
    public void handle(QueueMemberRemovedEvent qmre){
        if(!qmre.getQueue().substring(0, 5).equals("Queue"))
            return;
        qsc=qstcc.get(qmre.getQueue());
        QueueAgent del = null;
        for (QueueAgent qa : qsc.getAgents()) {
            if(qa.getAgent().equals(qmre.getMemberName())){
                del=qa;
                break;
            }
        }
        qsc.getAgents().remove(del);
    }
    public void handle(QueueEntryEvent qee){
        if(!qee.getQueue().substring(0, 5).equals("Queue"))
            return;
        qsc=qstcc.get(qee.getQueue());
        for (QueueEntryEvent qe : qsc.getCallers()) {
            if(qe.getUniqueId().equals(qee.getUniqueId())){
                qe.setPosition(qee.getPosition());
            return;
            }
        }
        qsc.getCallers().add(qee);
    }

    void handle(AgentConnectEvent ace) {
        if(!ace.getQueue().substring(0, 5).equals("Queue"))
            return;
        qsc=qstcc.get(ace.getQueue());
        QueueEntryEvent qee = null;
        for (QueueEntryEvent qe : qsc.getCallers()) {
            if(qe.getUniqueId().equals(ace.getUniqueId())){
                qee=qe;
                break;
            }
        }
        qsc.getCallers().remove(qee);       
    }
    void handle(AgentCalledEvent ac){      
        if(!ac.getQueue().substring(0, 5).equals("Queue"))
            return;
        qsc=qstcc.get(ac.getQueue());
        for (QueueAgent qa : qsc.getAgents()) {
            if(qa.getAgent().equals(ac.getMemberName())){
                qa.getPeer().setExten(ac.getCallerIdNum());
                qa.getPeer().setDirection(1);
                break;
            }
        }
        new MessageToAgentCenter().getAgentMsgs().put(ac.getInterface(),
                new MessageToAgent(ac.getCallerIdNum(), ac.getQueue()));
    }

    void handle(QueueCallerAbandonEvent qca) {
        if(!qca.getQueue().substring(0, 5).equals("Queue"))
            return;
        qsc=qstcc.get(qca.getQueue());
        QueueEntryEvent qee = null;
        for (QueueEntryEvent qe : qsc.getCallers()) {
            if(qe.getUniqueId().equals(qca.getUniqueId())){
                qee=qe;
                break;
            }
        }
        qsc.getCallers().remove(qee);
    }

    void handle(QueueMemberPauseEvent qmp) {
        if(!qmp.getQueue().substring(0, 5).equals("Queue"))
            return;
        qsc=qstcc.get(qmp.getQueue());
        for (QueueAgent qa : qsc.getAgents()) {
            if(qa.getAgent().equals(qmp.getMemberName())){
                qa.setReason(qmp.getReason());
                qa.setPauseTime(qmp.getDateReceived().getTime()/1000);
                return;
            }
        }
    }

}
