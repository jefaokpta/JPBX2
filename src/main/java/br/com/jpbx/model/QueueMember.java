/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Entity @Table(name = "queue_members")
public class QueueMember implements Serializable{

    public QueueMember() {
    }

    public QueueMember(String membername, String queueName, String interfaces, int penalty, int paused, int peerId) {
        this.membername = membername;
        this.queueName = queueName;
        this.interfaces = interfaces;
        this.penalty = penalty;
        this.paused = paused;
        this.peerId = peerId;
    }
    
    

    @Column(name = "uniqueid")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String membername;
    @Column(name = "queue_name")
    private String queueName;
    @Column(name = "interface")
    private String interfaces;
    private int penalty;
    private int paused;
    @Column(name = "queues_id")
    private int queueId;
    @Column(name = "peers_id")
    private int peerId;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.membername);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueueMember other = (QueueMember) obj;
        if (!Objects.equals(this.membername, other.membername)) {
            return false;
        }
        return true;
    }

    public int getPeerId() {
        return peerId;
    }

    public void setPeerId(int peerId) {
        this.peerId = peerId;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getPaused() {
        return paused;
    }

    public void setPaused(int paused) {
        this.paused = paused;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }
            
}
