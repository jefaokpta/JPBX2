/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.io.Serializable;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class Recall implements Serializable{

    private String channel;
    private String extension;
    private int waitTime;

    public Recall() {
    }

    public Recall(String channel, String extension, int waitTime) {
        this.channel = channel;
        this.extension = extension;
        this.waitTime = waitTime;
    }
    
    

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }


    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
    
    
}
