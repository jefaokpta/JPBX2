/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Entity @Table(name="agent_pauses")
public class AgentPause implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idagent_pauses")
    private int id;
    private String name;
    private int company;

    public AgentPause() {
    }

    public AgentPause(String name, int company) {
        this.name = name;
        this.company = company;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }
    
    
}
