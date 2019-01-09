/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Trunk;
import br.com.jpbx.model.TrunkDAO;
import java.io.Serializable;
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
@Named(value = "trunksBean")
@ViewScoped
public class TrunksBean implements Serializable{

    private Trunk trunk;
    private List<Trunk> trunks;
    public TrunksBean() {
        trunks=new TrunkDAO().getAllTrunks();
    }
    public String deleteTrunk(){
        String ret=new TrunkDAO().deleteTrunk(trunk.getId());
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/trunks";
    }
    public String editTrunk(Trunk t){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editTrunk", t);
        return "/restricted/editTrunk";
    }
    public void alertDelete(Trunk t){
        trunk=t;
        RequestContext.getCurrentInstance()
            .execute("PF('alertTrunk').show()"); 
    }

    public List<Trunk> getTrunks() {
        return trunks;
    }

    public Trunk getTrunk() {
        return trunk;
    }

    public void setTrunk(Trunk trunk) {
        this.trunk = trunk;
    }


    
}
