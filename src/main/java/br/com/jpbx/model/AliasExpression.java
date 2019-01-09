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
 * @author jefaokpta
 */
@Entity @Table(name = "alias_expressions")
public class AliasExpression implements Serializable{
    
    @Column(name = "idalias_expressions")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int idalias;
    private String expression;

    public AliasExpression() {
    }

    public AliasExpression(int idalias, String expression) {
        this.idalias = idalias;
        this.expression = expression;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.expression);
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
        final AliasExpression other = (AliasExpression) obj;
        if (!Objects.equals(this.expression, other.expression)) {
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

    public int getIdalias() {
        return idalias;
    }

    public void setIdalias(int idalias) {
        this.idalias = idalias;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
    
    
    
}
