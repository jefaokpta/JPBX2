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
@Named(value = "newRouteBean")
@ViewScoped
public class NewRouteBean implements Serializable{

    private Route route;
    private List<RouteTrunk> trunks;
    private boolean compromise;
    private List<Trunk> trunkSel;
    private List<CenterCost> ccosts;

    public NewRouteBean() {
        route=new Route();
        route.setCompany((int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany"));
        route.setFlags("T");
        route.setTimeout(60);
        trunkSel=new TrunkDAO().getAllTrunks();
        ccosts=new ArrayList<>();
        for (CenterCost cc : new CenterCostDAO().getCCosts()) {
            if(cc.getCompany()==route.getCompany())
                ccosts.add(cc);
        }
        trunks=new ArrayList<>();
        trunks.add(new RouteTrunk(0, 0, 0, 2));
        trunks.add(new RouteTrunk(0, 0, 0, 3));
        trunks.add(new RouteTrunk(0, 0, 0, 4));
        trunks.add(new RouteTrunk(0, 0, 0, 5));
        trunks.add(new RouteTrunk(0, 0, 0, 6));
        trunks.add(new RouteTrunk(0, 0, 0, 7));
        trunks.add(new RouteTrunk(0, 0, 0, 8));
        
//        for(CenterCost c:ccosts)
//            if(c.getCcost()==2){
//                trunks.add(new RouteTrunk(0, 0, 0, c.getId()));
//                break;
//            }
//        for(CenterCost c:ccosts)
//            if(c.getCcost()==3){
//                trunks.add(new RouteTrunk(0, 0, 0, c.getId()));
//                break;
//            }
//        for(CenterCost c:ccosts)
//            if(c.getCcost()==4){
//                trunks.add(new RouteTrunk(0, 0, 0, c.getId()));
//                break;
//            }
//        for(CenterCost c:ccosts)
//            if(c.getCcost()==5){
//                trunks.add(new RouteTrunk(0, 0, 0, c.getId()));
//                break;
//            }
//        for(CenterCost c:ccosts)
//            if(c.getCcost()==6){
//                trunks.add(new RouteTrunk(0, 0, 0, c.getId()));
//                break;
//            }
//        for(CenterCost c:ccosts)
//            if(c.getCcost()==7){
//                trunks.add(new RouteTrunk(0, 0, 0, c.getId()));
//                break;
//            }
//        for(CenterCost c:ccosts)
//            if(c.getCcost()==8){
//                trunks.add(new RouteTrunk(0, 0, 0, c.getId()));
//                break;
//            }      
    }
    public String submitNewRoute(){
        route.setLimitBol(0);
        if(compromise){
            route.setLimitBol(1);
            route.setLimitControl(route.getLimitControl()*60);
        }
        route.setRoutes(trunks);
        String ret=new RouteDAO().persistNewRoute(route);
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
    public List<Trunk> getTrunkSel() {
        return trunkSel;
    }

    public List<RouteTrunk> getTrunks() {
        return trunks;
    }

    public void setTrunks(List<RouteTrunk> trunks) {
        this.trunks = trunks;
    }
    
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
    public boolean isCompromise() {
        return compromise;
    }

    public void setCompromise(boolean compromise) {
        this.compromise = compromise;
    }
    
}
