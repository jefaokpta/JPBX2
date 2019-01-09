/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Company;
import br.com.jpbx.model.CompanyDAO;
import br.com.jpbx.model.User;
import br.com.jpbx.util.DefineUserPriority;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "userSession")
@SessionScoped
public class UserSession implements Serializable {

    private User user;

    private FacesContext fc;
    private HttpSession session;

    private String userPicture;
    private int userPriority;
    private Company currentCompany;
    private int currentCompanyId;
    private List<Company> companys;
    private Map<String,String> companySelect;
    private String homeScreen;
    
    public UserSession() {
        fc=FacesContext.getCurrentInstance();

        user=(User) fc.getExternalContext().getSessionMap().get("user");

        switch(user.getScreen()){
            case 1:
                homeScreen="homeSite";
                break;
            case 2:
                homeScreen="homeDashboard";
                break;
            case 3:
                homeScreen="homeServer";
                break;
            default:
                homeScreen="homeDashboard";
                break;
        }
        userPriority=new DefineUserPriority().userPriority(user.getNivel());
        companys=new CompanyDAO().getAllCompanys();
        for(Company c:companys)
            if(c.getId()==user.getCompany()){
                currentCompany=c;
                currentCompanyId=c.getId();
                break;
            }
        companySelect=new HashMap<>();
        if(userPriority==4)
            for(Company c:companys)
                companySelect.put(c.getName(), String.valueOf(c.getId()));
        else
            companySelect.put(currentCompany.getName(), String.valueOf(currentCompany.getId()));
    }
    public void changeCompany(){
        for(Company c:companys)
            if(c.getId()==currentCompanyId)
                currentCompany=c;
        FacesContext fc=FacesContext.getCurrentInstance();
        fc.getExternalContext().getSessionMap().
                put("currentCompany", currentCompany.getId());
        fc.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Gerenciando a Empresa: "+currentCompany.getName()+".", ""));
        NavigationHandler nav=fc.getApplication().getNavigationHandler();
        nav.handleNavigation(fc, null, "home?faces-redirect=true");
    }
    public boolean showHideLinks(int prio){
        return userPriority>=prio;
    }
    public String translateCompany(int id){
        String res="Indefinida";
        for(Company c:companys)
            if(c.getId()==id){
                res=c.getName();
                break;
            }
        return res;
    }
    public String logout(){
        fc=FacesContext.getCurrentInstance();
        session=(HttpSession) fc.getExternalContext().getSession(false);
        fc.getExternalContext().getSessionMap().remove("user");
        session.invalidate();
        return "/index?faces-redirect=true";
    }
    public String getPatchToWebPhone(){
        fc=FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        String res=req.getRequestURL().toString().split("/")[2].replace(":8080", ":8181");
        return "https://"+res+"/jpbx/jpbx/phone.xhtml";
    }
    public int getCurrentCompanyId() {
        return currentCompanyId;
    }

    public void setCurrentCompanyId(int currentCompanyId) {
        this.currentCompanyId = currentCompanyId;
    }

 

    public Map<String, String> getCompanySelect() {
        return companySelect;
    }


    public String getHomeScreen() {
        return homeScreen;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserPicture() {
        return userPicture;
    }
    
}
