/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "testeAjaxStatusBean")
@ViewScoped
public class TesteAjaxStatusBean implements Serializable{
    private String info;
    /**
     * Creates a new instance of TesteAjaxStatusBean
     */
    public TesteAjaxStatusBean() {
    }
    public void carregaInfo(){
        try {
            System.out.println("inicio chamada");
            Thread.sleep(10000);
            System.out.println("fim espera");
            info="Feito carregamento tardio";
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(info));
        } catch (InterruptedException ex) {
            info=ex.getMessage();
        }
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
    
}
