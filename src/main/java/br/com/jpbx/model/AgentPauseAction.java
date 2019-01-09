/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Entity @Table(name = "agent_pause_action")
public class AgentPauseAction implements Serializable{

    @Column(name = "idagent_pause_action")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date datetime;
    private int agent;
    private String queue;
    private int company;
    private String pause;

    public AgentPauseAction() {
    }

    public AgentPauseAction(Date datetime, int agent, String queue, int company, String pause) {
        this.datetime=datetime;
        this.agent = agent;
        this.queue = queue;
        this.company = company;
        this.pause = pause;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getAgent() {
        return agent;
    }

    public void setAgent(int agent) {
        this.agent = agent;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getPause() {
        return pause;
    }

    public void setPause(String pause) {
        this.pause = pause;
    }
    
}
