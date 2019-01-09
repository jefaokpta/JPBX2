/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Company;
import br.com.jpbx.model.CompanyDAO;
import br.com.jpbx.model.DialPlanAction;
import br.com.jpbx.model.DialPlanRule;
import br.com.jpbx.model.DialPlanRuleDAO;
import br.com.jpbx.model.Ura;
import br.com.jpbx.model.UraDAO;
import br.com.jpbx.model.UraOption;
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
@Named(value = "urasBean")
@ViewScoped
public class UrasBean implements Serializable{

    private Ura ura;
    private List<Ura> uras;
    
    public UrasBean() {
        User userSession=(User) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user");
        
        if(!userSession.getNivel().equals("Administrador")){ //CARREGA APENAS A PROPRIA EMPRESA
            uras=new ArrayList<>();
            for(Ura u:new UraDAO().getAllUras()){
                if(u.getCompany()==userSession.getCompany())
                    uras.add(u);
            }
        }   
        else
            uras=new UraDAO().getAllUras();
    }
    public String deleteUra(){
        for (Ura u : uras) {
            if(u.getId()!=ura.getId()){
                for (UraOption op : u.getUraOptions()) {
                    if(op.getParam()!=null){
                        if(op.getParam().equals(ura.getName())){
                            FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                            "Esta URA não será apagada porque está sendo usada por outra URA!", ""));
                            return "/restricted/uras";
                        }
                    }
                }
            }
        }
        for (DialPlanRule dr : new DialPlanRuleDAO().getAllDialplans(ura.getCompany())) {
            for (DialPlanAction da : dr.getActions()) {
                if(da.getAct().equals("ura")&&da.getArg1().equals(String.valueOf(ura.getId()))){
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Esta URA não pode ser apagada pois é usada na regra: "
                                        +dr.getId()+ " - "+dr.getName()+", Empresa "+dr.getCompany(), ""));
                    return "/restricted/uras";
                }
            }
        }
        String ret=new UraDAO().deleteUra(ura);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/uras";
    }
    public String editUra(Ura u){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editUra", u);
        return "/restricted/editUra";
    }
    public void alertDelete(Ura u){
        ura=u;
        RequestContext.getCurrentInstance()
             .execute("PF('alertUra').show()");     
    }

    public Ura getUra() {
        return ura;
    }

    public void setUra(Ura ura) {
        this.ura = ura;
    }

    public List<Ura> getUras() {
        return uras;
    }
    
}
