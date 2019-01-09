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
 * @author jefaokpta
 */
@Entity @Table(name = "ura_options")
public class UraOption implements Serializable{
    @Column(name = "idura_options")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "iduras")
    private int idUra;
    @Column(name = "option_ura")
    private int option;
    @Column(name = "action_name")
    private String action;
    @Column(name = "action_param")
    private String param;

    public UraOption() {
    }

    public UraOption(int option, String action, String param) {
        this.option = option;
        this.action = action;
        this.param = param;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.option;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UraOption other = (UraOption) obj;
        if (this.option != other.option) {
            return false;
        }
        return true;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUra() {
        return idUra;
    }

    public void setIdUra(int idUra) {
        this.idUra = idUra;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
    
}
