/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.asterisk;

/**
 *
 * @author jefaokpta
 */
public class ShowChannel {
    private String channel;
    private String state;
    private String duration;

    public ShowChannel(String duration) {
    }

    public ShowChannel(String channel, String state) {
        this.channel = channel;
        this.state = state;
        //this.duration = duration;
    }
    
    

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    
}
