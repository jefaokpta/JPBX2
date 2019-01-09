/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.PickupGrp;
import br.com.jpbx.model.PickupGrpDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "newPickupGrpBean")
@ViewScoped
public class NewPickupGrpBean implements Serializable{

    private PickupGrp grp;
    private List<PickupGrp> grps;
    private String borderColor;
    private boolean submitCtrl;
    public NewPickupGrpBean() {
        grp=new PickupGrp();   
        grp.setCompany((int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany"));
        grps=new PickupGrpDAO().getAllGrps();
        submitCtrl=false;
    }
    public void verifyGrpName(){
        borderColor="greenyellow";
        submitCtrl=true;
        for(PickupGrp p:grps)
            if(p.getName().toUpperCase().equals(grp.getName().toUpperCase())&&
                    p.getCompany()==grp.getCompany()){
                borderColor="red";
                submitCtrl=false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "O nome de grupo "+grp.getName()+" já existe.\n"
                                        + "Por favor escolha outro.", ""));
                break;
            }
    }
    public String submitNewGrp(){
        if(submitCtrl){
            String ret=new PickupGrpDAO().persistNewGrp(grp);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/pickupGrps";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Não foi possivel validar o nome do grupo.", ""));
        return null;
    }
    public String getBorderColor() {
        return borderColor;
    }

    public PickupGrp getGrp() {
        return grp;
    }

    public void setGrp(PickupGrp grp) {
        this.grp = grp;
    }
    
    
}
