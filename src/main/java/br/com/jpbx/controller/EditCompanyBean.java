/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Company;
import br.com.jpbx.model.CompanyDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "editCompanyBean")
@ViewScoped
public class EditCompanyBean implements Serializable{

    private Company company;
    private List<Company> companys;
    private boolean submitCtrl;
    private String borderColor;
    public EditCompanyBean() {
        companys=new CompanyDAO().getAllCompanys();
        company=(Company) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("editCompany");
        submitCtrl=false;
    }
    
    public String updateCompany(){
        if(submitCtrl){
            String res=new CompanyDAO().updateCompany(company);
            if(res.equals("ok"))
                return "/restricted/companys";
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                res, ""));
            return null;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "O Nome da Empresa não pode ser validado.", ""));
        return null;
    }
    public void verifyCompanyName(){
        submitCtrl=true;
        borderColor="yellowgreen";
        for(Company c:companys)
            if(c.getName().toLowerCase().equals(company.getName().toLowerCase()))
                if(c.getId()!=company.getId()){
                    submitCtrl=false;
                    borderColor="red";
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "O nome "+c.getName()+" está sendo usado por outra Empresa.", ""));
                    break;
                }
    }
    public String getBorderColor() {
        return borderColor;
    }
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
}
