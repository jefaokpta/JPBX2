/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Ddr;
import br.com.jpbx.model.DdrDAO;
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
@Named(value = "ddrsBean")
@ViewScoped
public class DdrsBean implements Serializable{

    private String ddr;
    private List<Ddr> ddrs;
    private int company;

    public DdrsBean() {
        company=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
        //ddrs=new DdrDAO().getAllDdrs();
    }
    public void newDdr(){
        if(!ddr.equals("")){
            for (Ddr d : ddrs) {
                if(d.getDdr().toUpperCase().equals(ddr.toUpperCase())){
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "O DDR "+ddr+" já existe.", ""));
                    return;
                }           
            }
            String ret=new DdrDAO().persistNewDdr(new Ddr(ddr, company));
            if(!ret.equals("ok")){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            ret, ""));
                    return;
            }
             FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "DDR "+ddr+" salvo com sucesso.", ""));  
        }
    }
    public void deleteDdr(Ddr d){
        new DdrDAO().deleteDdr(d);
    }
    
    public List<Ddr> getDdrs() {
        ddrs=new DdrDAO().getAllDdrs();
        return ddrs;
    }

    public String getDdr() {
        return ddr;
    }

    public void setDdr(String ddr) {
        this.ddr = ddr;
    }

    
    
    
}
