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
@Entity @Table(name = "accode_pins")
public class AcCodePin implements Serializable{
    
    @Column(name = "idaccode_pins")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "idaccode")
    private int accodeId;
    private String name;
    private int pin;

    public AcCodePin() {
    }

    public AcCodePin(String name, int pin) {
        this.name = name;
        this.pin = pin;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.pin;
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
        final AcCodePin other = (AcCodePin) obj;
        if (this.pin != other.pin) {
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

    public int getAccodeId() {
        return accodeId;
    }

    public void setAccodeId(int accodeId) {
        this.accodeId = accodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
    
    
}
