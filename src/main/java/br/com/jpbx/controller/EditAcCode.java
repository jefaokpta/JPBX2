/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.AcCode;
import br.com.jpbx.model.AcCodeDAO;
import br.com.jpbx.model.AcCodePin;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jefaokpta
 */
@Named(value = "editAcCode")
@ViewScoped
public class EditAcCode implements Serializable{

    private AcCode accode;
    private AcCodePin pin;
    private List<AcCodePin> pins;
    
    public EditAcCode() {
        accode=(AcCode) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("editAcCode");
        pin=new AcCodePin();
        pins=accode.getAcCodePins();
    }
    
    public String updateAcCode(){    
        if(pins.size()>0){
            String ret=new AcCodeDAO().updateAcCode(accode);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/acCodes";
        }
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "O Código de Conta deve ter ao menos 1 PIN cadastrado.", ""));
        return null;
    }
    public void addPIN(){
        if(!pin.getName().equals("")&&pin.getPin()>0){
            if(String.valueOf(pin.getPin()).matches("^[0-9][0-9][0-9]?[0-9]$")){
                if(!pins.contains(pin)){
                    pins.add(new AcCodePin(pin.getName(), pin.getPin()));
                    return;
                }
                FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "O PIN parece ser repetido.", ""));
                return;
            }
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                "O PIN deve ter 3 ou 4 digitos.", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                "Nome ou PIN não podem estar vazios!", ""));
    }
    public void removePIN(AcCodePin p){
        pins.remove(p);
    }

    public AcCode getAccode() {
        return accode;
    }

    public void setAccode(AcCode accode) {
        this.accode = accode;
    }

    public AcCodePin getPin() {
        return pin;
    }

    public void setPin(AcCodePin pin) {
        this.pin = pin;
    }

    public List<AcCodePin> getPins() {
        return pins;
    }

    public void setPins(List<AcCodePin> pins) {
        this.pins = pins;
    }
    
    
}
