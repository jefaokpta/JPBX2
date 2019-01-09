/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Email;
import br.com.jpbx.model.EmailDAO;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "emailServerBean")
@RequestScoped
public class EmailServerBean {

    private Email email;
    private boolean tls;
    private boolean ssl;
    private String emails;
    
    public EmailServerBean() {
        email=new EmailDAO().getEmailConfig();
        if(email.getTls()>0)
            tls=true;
        if(email.getSsl()>0)
            ssl=true;
    }
    public String updateEmail(){
        email.setTls(0);
        email.setSsl(0);
        if(tls)
            email.setTls(1);
        if(ssl)
            email.setSsl(1);
        String res=new EmailDAO().updateEmail(email);
        if(!res.equals("ok")){
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                   res, ""));
            return null;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                   "Servidor de Email salvo com sucesso", ""));
        return "/restricted/emailServer";
    }
    public void testEmailServer(){
        String res=new br.com.jpbx.util.Email().infoEmail(emails.split(","));
        if(res.equals("ok")){
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                   "Email enviado!", "Aparentemente o servidor de email conseguiu enviar, confirme o recebimento do email teste."));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                   "Ops! Não foi possivel enviar.", res));
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public boolean isTls() {
        return tls;
    }

    public void setTls(boolean tls) {
        this.tls = tls;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }
    
    
    
}
