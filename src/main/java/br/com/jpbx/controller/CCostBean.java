/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.CenterCost;
import br.com.jpbx.model.CenterCostDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "cCostBean")
@ViewScoped
public class CCostBean implements Serializable{

    private List<CenterCost> table;
    private CenterCost ccost;
    private String borderColor;
    private boolean submitCtrl;
    private boolean showDelete;
    private float ccostType;
    private Map<String,Float> ccostTypes;
    private int currentCompany;
    
    /**
     * Creates a new instance of CCostBean
     */
    public CCostBean() {
        currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
        table=new ArrayList<>();
        for (CenterCost cc : new CenterCostDAO().getCCosts()) {
            if(cc.getCompany()==currentCompany)
                table.add(cc);
        }   
        ccostTypes=new HashMap<String,Float>();
        for(CenterCost c:table)
            if(c.getCcost()==Math.round(c.getCcost()))
                ccostTypes.put(c.getName(), c.getCcost());
    }
    public void ccostSelected(SelectEvent evt){
        ccost=(CenterCost) evt.getObject();
        showDelete=false;
        if(ccost.getCcost()>Math.round(ccost.getCcost()))
            showDelete=true;
        borderColor="";   
        RequestContext.getCurrentInstance()
                .execute("PF('editCostDialog').show()");
    }
    public void createNewCCost(){
        ccost=new CenterCost();
        ccost.setCicle(60);
        ccost.setFraction(6);
        ccost.setCompany(currentCompany);
        borderColor="";   
        RequestContext.getCurrentInstance()
                .execute("PF('newCostDialog').show()");
    }
    public void verifyCCostName(){    
        submitCtrl=true;
        if(!ccost.getName().equals("")){
            for(CenterCost c:table){
                if(c.getName().toLowerCase().equals(ccost.getName().toLowerCase())){
                    if(ccost.getId()==c.getId()){
                        borderColor="greenyellow";
                    }
                    else{
                        submitCtrl=false;
                        borderColor="red";
                        FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Centro de custo "
                                    +ccost.getName()+" já existe!",""));
                        break;
                    }
                }
                else{
                  borderColor="greenyellow"; 
                }   
            }
        }
        else{
                borderColor="red";
                FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "O Campo nome não pode ficar vazio",""));
            }
    }
    public void verifyNewCCostName(){    
        submitCtrl=true;
        if(!ccost.getName().equals("")){
            for(CenterCost c:table){
                if(c.getName().toLowerCase().equals(ccost.getName().toLowerCase())){
                    submitCtrl=false;
                    borderColor="red";
                    FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Centro de custo "
                                +ccost.getName()+" já existe!",""));
                    break;
                }
                else{
                  borderColor="greenyellow"; 
                }   
            }
        }
        else{
                borderColor="red";
                FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "O Campo nome não pode ficar vazio",""));
            }
    }
    public String updateCCost(){
        String ret="/restricted/ccosts";
        verifyCCostName();
        if(submitCtrl){
            String mens=new CenterCostDAO().updateCCost(ccost);
            if(!mens.equals("ok")){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    mens,""));
                ret=null;
            }
        }
        return ret;
    }
    public List<CenterCost> getTable() {
        return table;
    }
    public String deleteCCost(){
        String ret="/restricted/ccosts";
        String mens=new CenterCostDAO().deleteCCost(ccost);
        if(!mens.equals("ok")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                mens,""));
            ret=null;
        }
        return ret;
    }
    public String persistNewCCost(){
        String ret="/restricted/ccosts";
        verifyNewCCostName();
        if(submitCtrl){
            String mens=new CenterCostDAO().persistNewCCost(ccost, ccostType);
            if(!mens.equals("ok")){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    mens,""));
                ret=null;
            }
        }
        return ret;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public CenterCost getCcost() {
        return ccost;
    }

    public void setCcost(CenterCost ccost) {
        this.ccost = ccost;
    }

    public Map<String, Float> getCcostTypes() {
        return ccostTypes;
    }

    public float getCcostType() {
        return ccostType;
    }

    public void setCcostType(float ccostType) {
        this.ccostType = ccostType;
    }


    

    public boolean isShowDelete() {
        return showDelete;
    }

  


    
}
