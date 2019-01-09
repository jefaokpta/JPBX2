/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Alias;
import br.com.jpbx.model.AliasDAO;
import br.com.jpbx.model.AliasExpression;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jefaokpta
 */
@Named(value = "newAliasBean")
@ViewScoped
public class NewAliasBean implements Serializable{

    private Alias alias;
    private AliasExpression expression;
    private List<AliasExpression> expressions;
    
    public NewAliasBean() {
        alias=new Alias();
        expression=new AliasExpression();
        expressions=new ArrayList<>();
    }
    public void addExpression(){
        if(!expression.getExpression().equals("")){
            if(!expressions.contains(expression)){
                expressions.add(new AliasExpression(alias.getId(), expression.getExpression().trim()));
                return;
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A Expressão parece ser repetida.", ""));
        }
    }
    public void removeExpression(AliasExpression ex){
        expressions.remove(ex);
    }
    public String persistNewAlias(){
        if(expressions.size()>0){
            alias.setExpressions(expressions);
            String ret=new AliasDAO().updateAlias(alias);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/aliases";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "O Alias de expressão deve ter ao menos 1 expressão.", ""));
        return null;
    }

    public Alias getAlias() {
        return alias;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
    }

    public AliasExpression getExpression() {
        return expression;
    }

    public void setExpression(AliasExpression expression) {
        this.expression = expression;
    }

    public List<AliasExpression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<AliasExpression> expressions) {
        this.expressions = expressions;
    }
    
    
}
