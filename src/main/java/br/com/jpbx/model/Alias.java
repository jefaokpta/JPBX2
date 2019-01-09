/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jefaokpta
 */
@Entity @Table(name = "alias")
public class Alias implements Serializable{
    
    @Column(name = "idalias")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    
    @OneToMany(cascade = javax.persistence.CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "idalias")
    private List<AliasExpression> expressions;

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

    public List<AliasExpression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<AliasExpression> expressions) {
        this.expressions = expressions;
    }
    
    
}
