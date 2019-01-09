/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import br.com.jpbx.model.Agent;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class RelAgentDisponibilityFilter implements Serializable{
    
    private Date startDate;
    private Date endDate;
    private List<Agent> agentSelected;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Agent> getAgentSelected() {
        return agentSelected;
    }

    public void setAgentSelected(List<Agent> agentSelected) {
        this.agentSelected = agentSelected;
    }
    
    
}
