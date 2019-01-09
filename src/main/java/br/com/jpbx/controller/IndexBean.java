/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.linux.LinuxInfo;
import br.com.jpbx.model.User;
import br.com.jpbx.model.UserDAO;
import br.com.jpbx.model.WebPageDAO;
import br.com.jpbx.util.MD5Factory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "indexBean")
@RequestScoped
public class IndexBean implements Serializable{

    private User user;

    public IndexBean() {
        user = user=new User();
    }
    
    
    public String doLogin(){
        FacesContext fc=FacesContext.getCurrentInstance();
        HttpSession hwKey=(HttpSession) fc.getExternalContext().getSession(false);
        String JPBX_HARD_KEY = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("/opt/HARDKEY"));
            if(br.ready())
                JPBX_HARD_KEY=br.readLine();
        } catch (FileNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Esta cópia do JPBX não está licenciada! Os serviços continuam funcionando mas o controle ficará bloqueado. Entre em contato com seu mantenedor.",null));
            return "licencing";
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Esta cópia do JPBX não está licenciada! Os serviços continuam funcionando mas o controle ficará bloqueado. Entre em contato com seu mantenedor.",null));
            return "licencing";
        }
        if(!JPBX_HARD_KEY.equals(new MD5Factory().md5(new LinuxInfo().hwKey()+":JPBX"))){
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Esta cópia do JPBX não está licenciada! Os serviços continuam funcionando mas o controle ficará bloqueado. Entre em contato com seu mantenedor.",null));
            return "licencing";
        }
        User userDAO=new UserDAO().doLogin(user.getName(), user.getPassword());
        if(userDAO==null){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Usuário ou senha errados.",null));
        }           
        else{           
            HttpSession session=(HttpSession) fc.getExternalContext().getSession(true);
            session.setMaxInactiveInterval(3600);
            fc.getExternalContext().getSessionMap().put("user", userDAO);
            fc.getExternalContext().getSessionMap().put("pages",
                    new WebPageDAO().getAllPages());
            fc.getExternalContext().getSessionMap().put("currentCompany",userDAO.getCompany());
            return "restricted/home";
        }
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    
}
