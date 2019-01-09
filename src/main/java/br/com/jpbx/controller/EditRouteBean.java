/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.CenterCost;
import br.com.jpbx.model.CenterCostDAO;
import br.com.jpbx.model.Route;
import br.com.jpbx.model.RouteDAO;
import br.com.jpbx.model.RouteTrunk;
import br.com.jpbx.model.Trunk;
import br.com.jpbx.model.TrunkDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jefaokpta
 */
@Named(value = "editRouteBean")
@ViewScoped
public class EditRouteBean implements Serializable{

    private Route route;
    private List<RouteTrunk> trunks;
    private boolean compromise;
    private boolean resetUsed;
    private List<Trunk> trunkSel;
    private List<CenterCost> ccosts;
    private int limitControlAux;
    
    public EditRouteBean() {
        route=(Route) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("editRoute");
        trunks=route.getRoutes();
        if(route.getLimitBol()>0)
            compromise=true;
        ccosts=new ArrayList<>();
        for (CenterCost cc : new CenterCostDAO().getCCosts()) {
            if(cc.getCompany()==route.getCompany())
                ccosts.add(cc);
        }
        trunkSel=new TrunkDAO().getAllTrunks();
        limitControlAux=route.getLimitControl();
        //route.setLimitControl(route.getLimitControl()/60);
    }
    public String updateRoute(){
        route.setLimitBol(0);
        if(compromise){
            route.setLimitBol(1);
            route.setLimitControl(limitControlAux);
            if(resetUsed)
                route.setCurrentMin(0);
        }
        String ret=new RouteDAO().updateRoute(route);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            ret, ""));
        return "/restricted/routes";
    }
    public String translateCenterCost(int ccost){
        for (CenterCost cc : ccosts) {
            if(cc.getCcost()==ccost&&cc.getCompany()==route.getCompany())
                return cc.getName();
        }
        return "Indefinido";
    }

    public int getLimitControlAux() {
        return route.getLimitControl()/60;
    }

    public void setLimitControlAux(int limitControlAux) {
        this.limitControlAux = limitControlAux*60;
    }
    
    public Route getRoute() {
        return route;
    }

    public boolean isResetUsed() {
        return resetUsed;
    }

    public void setResetUsed(boolean resetUsed) {
        this.resetUsed = resetUsed;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<RouteTrunk> getTrunks() {
        return trunks;
    }

    public void setTrunks(List<RouteTrunk> trunks) {
        this.trunks = trunks;
    }

    public boolean isCompromise() {
        return compromise;
    }

    public void setCompromise(boolean compromise) {
        this.compromise = compromise;
    }

    public List<Trunk> getTrunkSel() {
        return trunkSel;
    }
    
    
}
