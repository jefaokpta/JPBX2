/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.PickupGrp;
import br.com.jpbx.model.PickupGrpDAO;
import br.com.jpbx.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "pickupGrpsBean")
@ViewScoped
public class PickupGrpsBean implements Serializable{
    private PickupGrp grp;
    private List<PickupGrp> grps;
    public PickupGrpsBean() {
        User userSession=(User) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user");
        if(!userSession.getNivel().equals("Administrador")){
            grps=new ArrayList<>();
            for(PickupGrp p:new PickupGrpDAO().getAllGrps())
                if(p.getCompany()==userSession.getCompany())
                    grps.add(p);
        }
        else
            grps=new PickupGrpDAO().getAllGrps();
    }
    public String deleteGrp(){
        String ret=new PickupGrpDAO().deleteGrp(grp);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/pickupGrps";
    }
    public void alertDelete(PickupGrp p){
        grp=p;
        RequestContext.getCurrentInstance()
            .execute("PF('alertPickupGrp').show()");
    }   
    public String editGrp(PickupGrp p){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("editPickupGrp", p);
        return "/restricted/editPickupGrp";
    }
    public List<PickupGrp> getGrps() {
        return grps;
    }

    public PickupGrp getGrp() {
        return grp;
    }

    public void setGrp(PickupGrp grp) {
        this.grp = grp;
    }
    
    
}
