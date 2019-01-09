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
@Entity @Table(name = "agent_logged")
public class AgentLoggedQueue implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idagent_logged")
    private int id;
    private String queue;
    @Column(name = "queue_id")
    private int queueId;
    private String agent;
    @Column(name = "queue_name")
    private String queueName;
    private String iface;
    private String pause;
    private int peer;
    private int company;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updated;

    public AgentLoggedQueue() {
    }

    public AgentLoggedQueue(String queue, String agent, String queueName, String iface,int queueId,int peer,int company) {
        this.queue = queue;
        this.agent = agent;
        this.queueName = queueName;
        this.iface = iface;
        this.pause = "Disponível";
        this.queueId = queueId;
        this.peer=peer;
        this.company = company;
    }

    public int getPeer() {
        return peer;
    }

    public void setPeer(int peer) {
        this.peer = peer;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getIface() {
        return iface;
    }

    public void setIface(String iface) {
        this.iface = iface;
    }

    public String getPause() {
        return pause;
    }

    public void setPause(String pause) {
        this.pause = pause;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    
}
