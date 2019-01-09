/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;


import br.com.jpbx.model.Company;
import br.com.jpbx.model.CompanyDAO;
import br.com.jpbx.model.User;
import br.com.jpbx.model.UserDAO;
import br.com.jpbx.util.HandleUserImg;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "usersBean")
@ViewScoped
public class UsersBean implements Serializable{

    private List<User> users;
    private User user;
    private List<Company> companys;

    
    
    public UsersBean() {
        User userSession=(User) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user");
        
        if(!userSession.getNivel().equals("Administrador")){ //CARREGA APENAS A PROPRIA EMPRESA
            users=new ArrayList<>();
            for(User u:new UserDAO().getAllUsers()){
                if(u.getNivel().equals("Administrador"))
                    continue;
                if(u.getCompany()==userSession.getCompany())
                    users.add(u);
            }
        }   
        else
            users=new UserDAO().getAllUsers();
        companys=new CompanyDAO().getAllCompanys();
    }

    public String deleteUser(){
        String ret=new UserDAO().deleteUser(user);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        else
            new HandleUserImg().deleteUserImg(user.getName());
        return "/restricted/users";
    }
    public String editUser(User u){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editUser", u);
        return "/restricted/editUser";
    }
    public void alertDelete(User u){
        if(u.getId()!=1){
           user=u;
            RequestContext.getCurrentInstance()
                .execute("PF('alertUser').show()"); 
        }
        else
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Vc não quer apagar este usuário.", ""));
        
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

}
