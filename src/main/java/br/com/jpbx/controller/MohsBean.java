/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.CompanyDAO;
import br.com.jpbx.model.Moh;
import br.com.jpbx.model.MohDAO;
import br.com.jpbx.model.User;
import br.com.jpbx.util.HandleAudio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "mohsBean")
@ViewScoped
public class MohsBean implements Serializable{

    private Moh moh;
    private List<Moh> mohs;
    private UploadedFile upFile;
    private Map<String,String> companyMohs;
    private String companyMoh;
    private User userSession;
    
    public MohsBean() {
        moh=new Moh();
        userSession=(User) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user");  
        if(!userSession.getNivel().equals("Administrador")){ //CARREGA APENAS A PROPRIA EMPRESA
            mohs=new ArrayList<>();
            for (Moh m : new MohDAO().getAllMohs()) {
                if(m.getCompany()==userSession.getCompany())
                    mohs.add(m);
            }
        }
        else
            mohs=new MohDAO().getAllMohs();
        
        moh.setCompany((int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany"));
        
        companyMohs=new HashMap<>();
        for (Moh m : mohs) {
            if(m.getCompany()==userSession.getCompany())
                companyMohs.put(m.getName(), m.getMoh());
        }
        companyMoh=new CompanyDAO().getSingleCompany(userSession.getCompany()).getMoh();
    }
    public void onRowEdit(CellEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    new MohDAO().updateMoh(mohs), ""));
    }
    public String persistNewMoh(){
        if(upFile!=null){
            moh.setMoh("moh"+System.currentTimeMillis());
            String ret=new HandleAudio().newMoh(upFile, moh.getMoh());
            if(!ret.equals("ok")){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        ret, ""));
                return "/restricted/mohs";
            }
            ret=new MohDAO().persistNewMoh(moh);
            if(!ret.equals("ok")){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        ret, ""));
                return "/restricted/mohs";
            }
            
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Precisa escolher um arquivo de áudio.", ""));
            return null;
        }
        return "/restricted/mohs";
    }
    public void updateCompanyMoh(){
        if(companyMoh==null)
            companyMoh="";
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                new CompanyDAO().updateCompanyMoh(companyMoh, userSession.getCompany()), ""));
    }
    public void alertDelete(Moh m){
        moh=m;
        RequestContext.getCurrentInstance()
            .execute("PF('alertMoh').show()"); 
    }
    public String deleteMoh(){
        String ret=new HandleAudio().removeMoh(moh.getMoh());
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        ret=new MohDAO().deleteMoh(moh);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));        
        return "/restricted/mohs";
    }

    public Map<String, String> getCompanyMohs() {
        return companyMohs;
    }

    public String getCompanyMoh() {
        return companyMoh;
    }

    public void setCompanyMoh(String companyMoh) {
        this.companyMoh = companyMoh;
    }

    public UploadedFile getUpFile() {
        return upFile;
    }

    public void setUpFile(UploadedFile upFile) {
        this.upFile = upFile;
    }
    
    public Moh getMoh() {
        return moh;
    }

    public void setMoh(Moh moh) {
        this.moh = moh;
    }

    public List<Moh> getMohs() {
        return mohs;
    }
    
}
