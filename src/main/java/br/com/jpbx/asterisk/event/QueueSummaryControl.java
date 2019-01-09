/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk.event;

import java.io.Serializable;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class QueueSummaryControl implements Serializable{

    
    private Integer available;
    private Integer talktime;
    private Integer loggedIn;
    private Integer callers;
    private Integer longestHoldTime;
    private Integer holdtime;
    private String queue;
    private String queueName;

    public QueueSummaryControl() {
    }

    public QueueSummaryControl(Integer available, Integer talktime, Integer loggedIn, Integer callers, 
            Integer longestHoldTime, Integer holdtime, String queue, String queueName) {
        this.available = available;
        this.talktime = talktime;
        this.loggedIn = loggedIn;
        this.callers = callers;
        this.longestHoldTime = longestHoldTime;
        this.holdtime = holdtime;
        this.queue = queue;
        this.queueName = queueName;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getTalktime() {
        return talktime;
    }

    public void setTalktime(Integer talktime) {
        this.talktime = talktime;
    }

    public Integer getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Integer loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Integer getCallers() {
        return callers;
    }

    public void setCallers(Integer callers) {
        this.callers = callers;
    }

    public Integer getLongestHoldTime() {
        return longestHoldTime;
    }

    public void setLongestHoldTime(Integer longestHoldTime) {
        this.longestHoldTime = longestHoldTime;
    }

    public Integer getHoldtime() {
        return holdtime;
    }

    public void setHoldtime(Integer holdtime) {
        this.holdtime = holdtime;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
