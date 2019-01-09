/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.AgentPause;
import br.com.jpbx.model.AgentPauseDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "agentPausesBean")
@ViewScoped
public class AgentPausesBean implements Serializable{
    
    private String pause;
    private List<AgentPause> pauses;
    private int company;

    public AgentPausesBean() {
        company=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
    }
    public void newPause(){
        for (AgentPause p : pauses) {
            if(pause.equals(p.getName())){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Já existe uma Pausa com esta descrição - "+pause, ""));
                return;
            }
        }
        new AgentPauseDAO().persistNewPause(new AgentPause(pause, company));
    }
    public void deletePause(AgentPause p){
        new AgentPauseDAO().deletePause(p);
    }

    public List<AgentPause> getPauses() {
        pauses=new AgentPauseDAO().getAllPauses(company);
        return pauses;
    }

    public String getPause() {
        return pause;
    }

    public void setPause(String pause) {
        this.pause = pause;
    }

    
}
