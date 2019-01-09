/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Fax;
import br.com.jpbx.model.FaxDAO;
import br.com.jpbx.util.HandlePdf;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jefaokpta
 */
@Named(value = "faxesBean")
@ViewScoped
public class FaxesBean implements Serializable{

    private Fax fax;
    private List<Fax> faxes;
    private int currentCompany;
    private UploadedFile upFile;
    
    public FaxesBean() {
        currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
        faxes=new FaxDAO().getAllFaxes(currentCompany);
    }
    public String newFax(){
        if(upFile!=null){
            Fax f=new Fax();
            f.setFax("pdf"+System.currentTimeMillis());
            f.setSrc("JPBX WEB");
            f.setStatus("LOADED");
            f.setType("Carregado");
            f.setCompany(currentCompany);
            String ret=new HandlePdf().savePdf(upFile, f.getFax());
            if(!ret.equals("ok")){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        ret, ""));
                return "/restricted/faxes";
            }
            ret=new FaxDAO().persistNewFax(f);
            if(!ret.equals("ok")){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        ret, ""));
                return "/restricted/faxes";
            }
            
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Precisa escolher um arquivo PDF.", ""));
            return null;
        }
        return "/restricted/faxes";
    }
    public String deleteFax(Fax f){
        String res=new FaxDAO().deleteFax(f);
        if(!res.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        res, ""));
        return "/restricted/faxes";
    }
    public void showPdf(Fax f){
        fax=f;
        RequestContext.getCurrentInstance()
            .execute("PF('showPdf').show()");
    }

    public UploadedFile getUpFile() {
        return upFile;
    }

    public void setUpFile(UploadedFile upFile) {
        this.upFile = upFile;
    }

    public Fax getFax() {
        return fax;
    }

    public List<Fax> getFaxes() {
        return faxes;
    }

    public void setFax(Fax fax) {
        this.fax = fax;
    }
    
    
}
