/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.User;
import br.com.jpbx.model.UserDAO;
import br.com.jpbx.util.DefineUserPriority;
import br.com.jpbx.util.UserScreenFactory;
import br.com.jpbx.util.VerifyPassword;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "editUserBean")
@ViewScoped
public class EditUserBean implements Serializable{

   private User user;
   private User userSession;
   private Map<String,String> userScreen;
   private String borderColor;
   private boolean siteText;
   private boolean submitCtrl;
   private FacesContext fc;
   private Map<String,String> levels;
   private boolean editPass;
   private String pass;

    public EditUserBean() {
        user=(User) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("editUser");
        siteText=false;
        if(user.getScreen()==1)
            siteText=true;
        submitCtrl=true;
        fc=FacesContext.getCurrentInstance();
        userSession=(User) fc.getExternalContext().getSessionMap().get("user");
        userScreen=new UserScreenFactory().buildUserScreen(userSession.getNivel());
    }
    
    public void verifyPassword(){
        submitCtrl=true;
        if(!user.getPassword().equals("")){
            if(!new VerifyPassword().verifyPassword(user.getPassword())){
                FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "A senha deve ter ao menos 2 letras e 2 números.", ""));
                submitCtrl=false;
            }
        }      
    }
    public String updateUser(){
        if(submitCtrl){
            user.setTimeout(30);
            if(editPass)
                user.setPassword(pass);
            String ret=new UserDAO().updateUser(user,editPass);
            if(ret.equals("ok"))
                return "/restricted/users";
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));           
        }
        else
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Senha não autorizada.", "")); 
        return null;
    }
    public void showHideSiteText(){
        siteText=false;       
        if(user.getScreen()==1)
            siteText=true;
    }
    public Map<String, String> getLevels() {
        levels=new HashMap<>();
        levels.put("Telefonista", "Telefonista");
        levels.put("Supervisor", "Supervisor");
        int prio=new DefineUserPriority().userPriority(userSession.getNivel());
        if(prio>=3)
            levels.put("Gerente", "Gerente");
        if(prio>=4)
            levels.put("Administrador", "Administrador");
        return levels;
    }

    public boolean isEditPass() {
        return editPass;
    }

    public void setEditPass(boolean editPass) {
        this.editPass = editPass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    
    public Map<String, String> getUserScreen() {
        return userScreen;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public boolean isSiteText() {
        return siteText;
    }

    
}
