/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.LinkUser;
import br.com.jpbx.model.Route;
import br.com.jpbx.model.RouteDAO;
import br.com.jpbx.model.User;
import br.com.jpbx.model.UserDAO;
import br.com.jpbx.model.WebPage;
import br.com.jpbx.model.WebPageDAO;
import br.com.jpbx.util.CurrentMonth;
import br.com.jpbx.util.DefineUserPriority;
import java.io.Serializable;
import java.util.ArrayList;
import javax.inject.Named;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DragDropEvent;


/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "homeBean")
@ViewScoped
public class HomeBean implements Serializable{

    
    private String month;
    private List<WebPage> webPages;
    private FacesContext fc;
    private User user;
    private List<WebPage> droopedLinks;
    private WebPage selectedLink;
    private List<Route> routes;
    private Route route;
    private int currentCompany;

    
    public HomeBean() {    
        
        month=new CurrentMonth().currentMonth(Calendar.getInstance().get(Calendar.MONTH));
        webPages=new WebPageDAO().getAllPages();
        fc=FacesContext.getCurrentInstance();
        user=(User) fc.getExternalContext().getSessionMap().get("user");
        currentCompany=(int) fc.getExternalContext().getSessionMap().get("currentCompany");
        droopedLinks=new ArrayList<>();

        for(LinkUser l:user.getLinks())
            for(WebPage w:webPages)
                if(l.getPageId()==w.getId())
                    droopedLinks.add(w);
        for(WebPage w:droopedLinks)
            webPages.remove(w);
        
        routes=new RouteDAO().getAllRoutes(currentCompany);
    }
    public void onLinkDrop(DragDropEvent ev){
        WebPage page=(WebPage) ev.getData();
        droopedLinks.add(page);
        user.getLinks().add(new LinkUser(page.getId()));
        webPages.remove(page);
    }
    public void clearLinks(){ 
        user.getLinks().clear();
        droopedLinks.clear();
        webPages.clear();
        webPages=new WebPageDAO().getAllPages();
    }
    public void saveLinks(){
        String ret=new UserDAO().updateUserWebLinks(user);
        if(ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Links Rápidos salvos.", ""));
        else
           FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        ret, "")); 
    }
    public void showRoute(Route r){
        route=r;
        RequestContext.getCurrentInstance()
            .execute("PF('showRoute').show()");
    }   
    public List<WebPage> getWebPages() {
        List<WebPage> res=new ArrayList<>();
        int prio=new DefineUserPriority().userPriority(user.getNivel());
        for(WebPage w:webPages)
            if(w.getDisplay()>0)
                if(prio>=w.getPriority())
                    res.add(w);
        return res;
    }

    public String getMonth() {
        return month;
    }

    public WebPage getSelectedLink() {
        return selectedLink;
    }

    public void setSelectedLink(WebPage selectedLink) {
        this.selectedLink = selectedLink;
    }

    public List<WebPage> getDroopedLinks() {
        return droopedLinks;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }



    
}
