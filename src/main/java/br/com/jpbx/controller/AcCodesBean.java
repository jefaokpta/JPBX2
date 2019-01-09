/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.AcCode;
import br.com.jpbx.model.AcCodeDAO;
import br.com.jpbx.model.DialPlanAction;
import br.com.jpbx.model.DialPlanRule;
import br.com.jpbx.model.DialPlanRuleDAO;
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
 * @author jefaokpta
 */
@Named(value = "acCodesBean")
@ViewScoped
public class AcCodesBean implements Serializable{

    private AcCode accode;
    private List<AcCode> accodes;
    private int currentCompany;
    
    public AcCodesBean() {
        User userSession=(User) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user");
        if(!userSession.getNivel().equals("Administrador")){ //CARREGA APENAS A PROPRIA EMPRESA
            accodes=new ArrayList<>();
            for(AcCode a:new AcCodeDAO().getAllAcCodes()){
                if(a.getCompany()==userSession.getCompany())
                    accodes.add(a);
            }
        }   
        else
            accodes=new AcCodeDAO().getAllAcCodes();
        currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCompany");
    }
    public boolean getValidNewButton(){
        for (AcCode ac : accodes) {
            if(ac.getCompany()==currentCompany)
                return true;
        }
        return false;
    }
    public String editAcCode(AcCode a){
        FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put("editAcCode", a);
        return "/restricted/editAcCode";
    }
    public void alertDelete(AcCode a){
        accode=a;
        RequestContext.getCurrentInstance()
            .execute("PF('alertAcCode').show()"); 
    }
    public void showPins(AcCode a){
        accode=a;
        RequestContext.getCurrentInstance()
            .execute("PF('showPins').show()"); 
    }
    public String deleteAcCode(){
        for (DialPlanRule dr : new DialPlanRuleDAO().getAllDialplans(accode.getCompany())) {
            for (DialPlanAction da : dr.getActions()) {
                if(da.getAct().equals("accode")&&da.getArg1().equals(String.valueOf(accode.getId()))){
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Este Código de Conta não pode ser apagado pois é usado na regra: "
                                        +dr.getId()+ " - "+dr.getName()+", Empresa "+dr.getCompany(), ""));
                    return "/restricted/acCodes";
                }
            }
        }
        String ret=new AcCodeDAO().deleteAcCode(accode);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/acCodes";
    }
    

    public AcCode getAccode() {
        return accode;
    }

    public void setAccode(AcCode accode) {
        this.accode = accode;
    }

    public List<AcCode> getAccodes() {
        return accodes;
    }
    
    
}
