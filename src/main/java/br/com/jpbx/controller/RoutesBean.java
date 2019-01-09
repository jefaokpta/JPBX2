/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.DialPlanAction;
import br.com.jpbx.model.DialPlanRule;
import br.com.jpbx.model.DialPlanRuleDAO;
import br.com.jpbx.model.Route;
import br.com.jpbx.model.RouteDAO;
import java.io.Serializable;
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
@Named(value = "routesBean")
@ViewScoped
public class RoutesBean implements Serializable{

    private Route route;
    private List<Route> routes;
    
    public RoutesBean() {
        routes=new RouteDAO().getAllRoutes();
    }
    public String deleteRoute(){
        for (DialPlanRule dr : new DialPlanRuleDAO().getAllDialplans(route.getCompany())) {
            for (DialPlanAction da : dr.getActions()) {
                if(da.getAct().equals("trunkRoute")&&da.getArg1().equals(String.valueOf(route.getId()))){
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Este Rota não pode ser apagada pois é usado na regra: "
                                        +dr.getId()+ " - "+dr.getName()+", Empresa "+dr.getCompany(), ""));
                    return "/restricted/routes";
                }
            }
        }
        String ret=new RouteDAO().deleteRoute(route);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/routes";
    }
    public void alertDelete(Route r){
        route=r;
        RequestContext.getCurrentInstance()
            .execute("PF('alertRoute').show()");
    }   
    public void showRoute(Route r){
        route=r;
        RequestContext.getCurrentInstance()
            .execute("PF('showRoute').show()");
    }   
    public String editRoute(Route r){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("editRoute", r);
        return "/restricted/editRoute";
    }
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
    
}
