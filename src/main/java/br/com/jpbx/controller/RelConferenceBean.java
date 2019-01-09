/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.util.RelConferenceFilter;
import java.util.Calendar;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "relConferenceBean")
@RequestScoped
public class RelConferenceBean{

    private RelConferenceFilter form;
    
    public RelConferenceBean() {
        form=new RelConferenceFilter();
        Calendar today=Calendar.getInstance();
        form.setEndDate(today.getTime());
        today.set(Calendar.DAY_OF_MONTH, 1);
        form.setStartDate(today.getTime());
    }
    public String showRelConferences(){
        if(form.getStartDate().after(form.getEndDate())){
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "A data de Início deve ser anterior a data de fim", ""));
            return null;
        }
        form.setCompany((int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany"));
        Calendar hours=Calendar.getInstance();
        hours.setTime(form.getEndDate());
        hours.set(Calendar.HOUR_OF_DAY, 23);
        hours.set(Calendar.MINUTE, 59);      
        hours.set(Calendar.SECOND, 59);   
        form.setEndDate(hours.getTime());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("relConfFilter", form);
        return "/restricted/relConferenceShow";
    }

    public RelConferenceFilter getForm() {
        return form;
    }

    public void setForm(RelConferenceFilter form) {
        this.form = form;
    }
    
    
}
