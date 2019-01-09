/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Trunk;
import br.com.jpbx.model.TrunkCost;
import br.com.jpbx.model.TrunkCostDAO;
import br.com.jpbx.model.TrunkDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "trunkCostsBean")
@ViewScoped
public class TrunkCostsBean implements Serializable{

    private TrunkCost trunkCost;
    private List<TrunkCost> trunkCosts;
    private List<TrunkCost> trunkCostsView;
    private Map<String,String> trunkMap;
    private int trunkId;
    
    public TrunkCostsBean() {
        trunkCosts=new TrunkCostDAO().getAllTrunkCosts();
        trunkMap=new HashMap<>();
        for (Trunk t : new TrunkDAO().getAllTrunks()) {
            trunkMap.put(t.getName(), String.valueOf(t.getId()));
        }
    }

    public List<TrunkCost> getTrunkCostsView() {
        trunkCostsView=new ArrayList<>();
        for (TrunkCost tc : trunkCosts) {
            if(tc.getTrunk()==trunkId)
                trunkCostsView.add(tc);
        }
        return trunkCostsView;
    }
    public void updateTrunkCost(){
        String res=new TrunkCostDAO().updateTrunkCost(trunkCost);
        if(!res.equals("ok"))
            javax.faces.context.FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    res,""));
        RequestContext.getCurrentInstance()
                .execute("PF('editCostDialog').hide()");
    }
    public void trunkCostSelected(SelectEvent evt){
        trunkCost=(TrunkCost) evt.getObject();
        RequestContext.getCurrentInstance()
                .execute("PF('editCostDialog').show()");
    }

    public Map<String, String> getTrunkMap() {
        return trunkMap;
    }

    public TrunkCost getTrunkCost() {
        return trunkCost;
    }

    public void setTrunkCost(TrunkCost trunkCost) {
        this.trunkCost = trunkCost;
    }

    public int getTrunkId() {
        return trunkId;
    }

    public void setTrunkId(int trunkId) {
        this.trunkId = trunkId;
    }
    
    
    
}
