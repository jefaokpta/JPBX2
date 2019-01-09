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
@Named(value = "editPickupGrpBean")
@ViewScoped
public class EditPickupGrpBean implements Serializable{

    private List<PickupGrp> grps;
    private PickupGrp grp;
    private boolean submitCtrl;
    private String borderColor;
    public EditPickupGrpBean() {
        grps=new PickupGrpDAO().getAllGrps();
        grp=(PickupGrp) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("editPickupGrp");
        submitCtrl=true;
    }
    public void verifyGrpName(){
        borderColor="greenyellow";
        submitCtrl=true;
        for(PickupGrp p:grps)
            if(p.getName().toUpperCase().equals(grp.getName().toUpperCase())&&
                    p.getCompany()==grp.getCompany())
                if(p.getId()!=grp.getId()){
                    borderColor="red";
                    submitCtrl=false;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "O nome de grupo "+grp.getName()+" já existe.\n"
                                            + "Por favor escolha outro.", ""));
                    break;
                }           
    }
    public String updateGrp(){
        if(submitCtrl){
            String ret=new PickupGrpDAO().updateGrp(grp);
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
