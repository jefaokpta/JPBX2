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
@Named(value = "newCompanyBean")
@ViewScoped
public class NewCompanyBean implements Serializable{

    private List<Company> companys;
    private Company company;
    private String borderColor;
    private boolean submitCtrl;
    
    public NewCompanyBean() {
        company=new Company();
        submitCtrl=false;
        companys=new CompanyDAO().getAllCompanys();
        company.setMoh("");
    }

    public void verifyCompanyName(){
        borderColor="greenyellow";
        submitCtrl=true;
        for(Company c:companys)
            if(c.getName().toLowerCase().equals(company.getName().toLowerCase())){
                borderColor="red";
                submitCtrl=false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A Empresa com nome "+company.getName()+" já existe.\n"
                                        + "Por favor escolha outro.", ""));
                break;
            }
    }
    public String submitNewCompany(){
        if(submitCtrl){
            String res=new CompanyDAO().insertCompany(company);
            if(res.equals("ok"))
                return "/restricted/companys";
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                res, ""));
            return null;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Empresa não pode ser validada.", ""));
        return null;
    }
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getBorderColor() {
        return borderColor;
    }
    
}
