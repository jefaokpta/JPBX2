/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Config;
import br.com.jpbx.model.ConfigDAO;
import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "configsBean")
@RequestScoped
public class ConfigsBean {

    private Config config;
    private boolean astUp;
    
    public ConfigsBean() {
        config=new ConfigDAO().getConfig();
        if(config.getAstup()>0)
            astUp=true;
    }
    public String saveConfigs(){
        boolean digits4=false;
        for (int i = 1000; i < 10000; i++) {
            if(String.valueOf(i).matches("^"+config.getAgentRange()+"$")){
                digits4=true;
                break;
            }
        }
        if(digits4){
            for (Peer p : new PeerDAO().getAllPeers()) {
                if(String.valueOf(p.getName()).matches("^"+config.getAgentRange()+"$")){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A expressão não foi aceita porque conflita com ramais existentes. Ex: "+p.getName(), ""));
                    return null;
                }
            }
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Agentes precisam ter exatamente 4 digitos! A expressão não aparenta cumprir esta regra.", ""));
            return null;
        }
        // PARECE Q TA TD OK
        config.setAstup(0);
        if(astUp)
            config.setAstup(1);
        String res=new ConfigDAO().updateConfig(config);
        if(!res.equals("ok")){
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                   res, ""));
        return null;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Configurações salvas com sucesso.", ""));
        return "/restricted/configs";
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public boolean isAstUp() {
        return astUp;
    }

    public void setAstUp(boolean astUp) {
        this.astUp = astUp;
    }

    
    
    
}
