/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk.event;

import java.util.Map;



/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class QueueAgent {

    private String agent;
    private String agentName;
    private PeerStatusControl peer;
    private String queue;
    private Integer penalty;
    private Integer callsTaken;
    private Long lastCall;
    private Integer status;
    private boolean pause;
    private String reason;
    private Long pauseTime;

    public QueueAgent(String agent, String queue, Integer penalty, Integer callsTaken, Long lastCall, Integer status,String reason,PeerStatusControl peer) {
        this.agent = agent;
        this.peer = peer;
        this.queue = queue;
        this.penalty = penalty;
        this.callsTaken = callsTaken;
        this.lastCall = lastCall;
        this.status = status;
        this.reason=reason;
    }

    public Long getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(Long pauseTime) {
        this.pauseTime = pauseTime;
    }


    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public PeerStatusControl getPeer() {
        if(this.peer.getPeer().split("/")[0].equals("Local")){
            String peerKhomp=this.peer.getPeer().split("/")[1];
            peerKhomp=peerKhomp.substring(0, peerKhomp.indexOf("@"));
            peerKhomp=(peerKhomp.length()==3?"1"+peerKhomp:peerKhomp);
            for (Map.Entry<String, PeerStatusControl> p : new PeerStatusCenterControl().getPeerStatusController().entrySet()) {
                if(peerKhomp.equals(p.getValue().getPeer())){
                    if(status==1){
                        peer.setExten(p.getValue().getExten());
                        peer.setDirection(p.getValue().getDirection());
                    }
                    peer.setLastCall(p.getValue().getLastCall());
                    peer.setState(p.getValue().getState());
                    peer.setStateDesc(p.getValue().getStateDesc());
                    peer.setUniqueid(p.getValue().getUniqueid());
                    peer.setChannel(p.getValue().getChannel());
                    return peer;
                }
            }
            peer.setStateDesc("Down");
            peer.setState(0);
        }
        return peer;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public void setPeer(PeerStatusControl peer) {
        this.peer = peer;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public Integer getPenalty() {
        return penalty;
    }

    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }

    public Integer getCallsTaken() {
        return callsTaken;
    }

    public void setCallsTaken(Integer callsTaken) {
        this.callsTaken = callsTaken;
    }

    public Long getLastCall() {
        return lastCall;
    }

    public void setLastCall(Long lastCall) {
        this.lastCall = lastCall;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    
    
}
