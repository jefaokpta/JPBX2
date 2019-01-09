/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.asteriskjava.manager.event.QueueEntryEvent;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class QueueStatusControl implements Serializable{

    private String queue;
    private Integer max;
    private String strategy;
    private Integer calls;
    private Integer holdTime;
    private Integer talkTime;
    private Integer completed;
    private Integer abandoned;
    private Integer serviceLevel;
    private Double serviceLevelPerf;
    private Integer weight;
    private List<QueueAgent> agents;
    private List<QueueEntryEvent> callers;

    public QueueStatusControl(String queue, Integer max, String strategy, Integer calls, Integer holdTime,
            Integer talkTime, Integer completed, Integer abandoned, Integer serviceLevel, Double serviceLevelPerf, 
            Integer weight) {
        this.queue = queue;
        this.max = max;
        this.strategy = strategy;
        this.calls = calls;
        this.holdTime = holdTime;
        this.talkTime = talkTime;
        this.completed = completed;
        this.abandoned = abandoned;
        this.serviceLevel = serviceLevel;
        this.serviceLevelPerf = serviceLevelPerf;
        this.weight = weight;
        this.agents = new ArrayList<>();
        this.callers=new ArrayList<>();
    }

    public List<QueueEntryEvent> getCallers() {
        return callers;
    }

    public void setCallers(List<QueueEntryEvent> callers) {
        this.callers = callers;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Integer getCalls() {
        return calls;
    }

    public void setCalls(Integer calls) {
        this.calls = calls;
    }

    public Integer getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(Integer holdTime) {
        this.holdTime = holdTime;
    }

    public Integer getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(Integer talkTime) {
        this.talkTime = talkTime;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Integer getAbandoned() {
        return abandoned;
    }

    public void setAbandoned(Integer abandoned) {
        this.abandoned = abandoned;
    }

    public Integer getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(Integer serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Double getServiceLevelPerf() {
        return serviceLevelPerf;
    }

    public void setServiceLevelPerf(Double serviceLevelPerf) {
        this.serviceLevelPerf = serviceLevelPerf;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<QueueAgent> getAgents() {
        return agents;
    }

    public void setAgents(List<QueueAgent> agents) {
        this.agents = agents;
    }
    
    
}
