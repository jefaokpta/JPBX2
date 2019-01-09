/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk.event;

import java.util.Date;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class PeerStatusControl {
    // composto por PeerStatusEvent NewChannelEvent NewStateEvent HangupEvent
    private String peer;
    private String channel;
    private String registerStatus;
    private int state;
    private String stateDesc;
    private String address;
    private String exten;
    private String uniqueid;
    private Date lastCall;
    private int direction;

    public PeerStatusControl() {
    }

    public PeerStatusControl(String peer, String registerStatus,String address) {
        this.peer = peer;
        this.registerStatus = registerStatus;
        this.address=address;
    }
    public PeerStatusControl(String peer, String stateDesc,int state,String exten,String uniqueid,String channel,Date lastCall) {
        this.peer = peer;
        this.registerStatus = "Unregistered";
        this.channel=channel;
        this.exten=exten;
        this.state=state;
        this.stateDesc=stateDesc;
        this.uniqueid=uniqueid;
        this.lastCall=lastCall;
    }

    public Date getLastCall() {
        return lastCall;
    }

    public void setLastCall(Date lastCall) {
        this.lastCall = lastCall;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    

    public String getPeer() {
        return peer;
    }

    public void setPeer(String peer) {
        this.peer = peer;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(String registerStatus) {
        this.registerStatus = registerStatus;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getExten() {
        return exten;
    }

    public void setExten(String exten) {
        this.exten = exten;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }
    
    
}
