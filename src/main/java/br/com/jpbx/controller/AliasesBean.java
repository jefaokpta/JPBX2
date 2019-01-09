/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Alias;
import br.com.jpbx.model.AliasDAO;
import br.com.jpbx.model.AliasExpression;
import br.com.jpbx.model.DialPlanAction;
import br.com.jpbx.model.DialPlanRule;
import br.com.jpbx.model.DialPlanRuleDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jefaokpta
 */
@Named(value = "aliases")
@ViewScoped
public class AliasesBean implements Serializable{

    private Alias alias;
    private List<Alias> aliases;
    
    public AliasesBean() {
        aliases=new AliasDAO().getAllAlias();       
    }
    
    public String deleteAlias(Alias a){ //NAO APAGAR SE ESTIVER USANDO EM REGRAS
        for (DialPlanRule dr : new DialPlanRuleDAO().getAllDialplans()) {
            if(dr.getDstAlias()==a.getId()||dr.getSrcAlias()==a.getId()){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Este Alias de Expressão não pode ser apagado pois é usado na regra: "
                                        +dr.getId()+ " - "+dr.getName()+", Empresa "+dr.getCompany(), ""));
                return "/restricted/aliases";
            }
        }
        String ret=new AliasDAO().deleteAlias(a);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/aliases";
    }
    public void alertEdit(Alias a){
        alias=a;
        RequestContext.getCurrentInstance()
            .execute("PF('showExpressions').show()"); 
    }
    public String editAlias(Alias a){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editAlias", a);
        return "/restricted/editAlias";
    }

    public Alias getAlias() {
        return alias;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
    }

    public List<Alias> getAliases() {
        return aliases;
    }
    
    
    
}
