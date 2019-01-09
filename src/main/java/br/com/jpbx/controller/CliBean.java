/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.asterisk.Asterisk;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


/**
 *
 * @author jefaokpta
 */
@Named(value = "cliBean")
@ViewScoped
public class CliBean implements Serializable{

    private String filter;
    
    public String handleCommand(String command,String[] params){
        String res="";
        for (String p : params) {
            command+=" "+p;
        }
       // try {
            for (String r : new Asterisk().getInfos(command)) {
                res+=r+"\r\n";
            }
//        } catch (IOException ex) {
//            return "DEU RUIM NO COMANDO :"+ex.getMessage();
//        }
        return res;
    }
    public String getLogs(){
        String res="";
        try {
            Process ls_proc = Runtime.getRuntime().exec("/usr/bin/tail -n1000 /var/log/asterisk/full");
            String ls_str;
            BufferedReader bf=new BufferedReader(
                    new InputStreamReader(ls_proc.getInputStream()));
            while ((ls_str = bf.readLine()) != null) {
                if(filter==null){
                    res+=ls_str+"\n";
                    continue;
                }
                if(ls_str.contains(filter))
                    res+=ls_str+"\n";
            }
        } catch (IOException ex) {
            return "DEU RUIM AO PEGAR LOGS: "+ex.getMessage();
        }
        return res;
    }
    
    public void filterLogs(){
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Mostrando Logs com: "+filter, "")); 
    }
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
    
}
