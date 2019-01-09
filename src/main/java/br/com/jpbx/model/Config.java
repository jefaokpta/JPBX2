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
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Entity @Table(name = "configs")
public class Config implements Serializable{

    @Column(name = "idconfigs")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "agent_range")
    private String agentRange;
    @Column(name = "anti_invasion")
    private int invasion;
    private int astup;
    private String name;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAstup() {
        return astup;
    }

    public void setAstup(int astup) {
        this.astup = astup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgentRange() {
        return agentRange;
    }

    public void setAgentRange(String agentRange) {
        this.agentRange = agentRange;
    }

    public int getInvasion() {
        return invasion;
    }

    public void setInvasion(int invasion) {
        this.invasion = invasion;
    }
    
}
