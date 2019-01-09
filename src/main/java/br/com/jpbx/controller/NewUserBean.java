/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.User;
import br.com.jpbx.model.UserDAO;
import br.com.jpbx.util.DefineUserPriority;
import br.com.jpbx.util.HandleUserImg;
import br.com.jpbx.util.UserScreenFactory;
import br.com.jpbx.util.VerifyPassword;
import java.io.Serializable;
import java.util.HashMap;
 import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.CroppedImage;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "newUserBean")
@ViewScoped
public class NewUserBean implements Serializable{
    
    private List<User> users;
    private User user;
    private User userSession;
    private String borderColor;
    private boolean submitCtrl;
    private boolean siteText;
    private FacesContext fc;
    private CroppedImage img;
    private Map<String,String> userScreen;
    private Map<String,String> levels;
    
    public NewUserBean() {
        users=new UserDAO().getAllUsers();
        user=new User();
        submitCtrl=false;
        siteText=true;
        fc=FacesContext.getCurrentInstance();
        userSession=(User) fc.getExternalContext().getSessionMap().get("user");
        userScreen=new UserScreenFactory().buildUserScreen(userSession.getNivel());
        user.setCompany((int) fc.getExternalContext().
                getSessionMap().get("currentCompany"));
    }
    public void verifyUserName(){
        borderColor="greenyellow";
        submitCtrl=true;
        for(User u:users)
            if(u.getName().equals(user.getName())){
                borderColor="red";
                submitCtrl=false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Usuário "+user.getName()+" já existe.\n"
                                        + "Por favor escolha outro.", ""));
                break;
            }
    }
    public void verifyPassword(){
        submitCtrl=true;       
        if(!new VerifyPassword().verifyPassword(user.getPassword())){
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A senha deve ter ao menos 2 letras e 2 números.", ""));
            submitCtrl=false;
        }
    }
    public boolean showHideNivel(int level){
        return level <=new DefineUserPriority().userPriority(userSession.getNivel());
    }
    public void showHideSiteText(){
        siteText=false;       
        if(user.getScreen()==1)
            siteText=true;
    }
    public String submitNewUser(){
        if(submitCtrl){
            user.setTimeout(30);
            user.setImgType("gif");
            user.setName(user.getName().trim());
            String ret=new UserDAO().persistNewUser(user);
            if(ret.equals("ok")){
                new HandleUserImg().createUserImg(user.getName());
                return "/restricted/users";
            }
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return null;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "O Usuário ou Senha não foram validados.", ""));
        return null;
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

    public Map<String, String> getUserScreen() {
        return userScreen;
    }
    public User getUser() {
        return user;
    }

    public boolean isSiteText() {
        return siteText;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public CroppedImage getImg() {
        return img;
    }

    public void setImg(CroppedImage img) {
        this.img = img;
    }
    
}
