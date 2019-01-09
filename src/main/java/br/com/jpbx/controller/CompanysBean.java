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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "companysBean")
@ViewScoped
public class CompanysBean implements Serializable{

    private List<Company> companys;
    private Company company;
    public CompanysBean() {
        companys=new CompanyDAO().getAllCompanys();
    }

    public List<Company> getCompanys() {
        return companys;
    }
    public String editCompany(Company company){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editCompany", company);
        return "editCompany";
    }
    public void alertCompany(Company company){
        if(company.getId()!=1){
           this.company=company;
            RequestContext.getCurrentInstance()
                .execute("PF('alertCompany').show()"); 
        }
        else
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Vc não deve apagar esta empresa.", ""));
    }
    public String deleteCompany(){
        String res=new CompanyDAO().deleteCompany(company);
        if(!res.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                res, ""));
        return "companys";
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    
}
