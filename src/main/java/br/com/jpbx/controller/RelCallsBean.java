/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.AcCode;
import br.com.jpbx.model.AcCodeDAO;
import br.com.jpbx.model.AcCodePin;
import br.com.jpbx.model.CenterCost;
import br.com.jpbx.model.CenterCostDAO;
import br.com.jpbx.model.RelCall;
import br.com.jpbx.model.RelCallDAO;
import br.com.jpbx.util.Biller;
import br.com.jpbx.util.FormaterSeconds;
import br.com.jpbx.util.RelCallFilter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author jefaokpta
 */
@Named(value = "relCallsBean")
@ViewScoped
public class RelCallsBean implements Serializable{

    private LazyDataModel<RelCall> relcalls;
    private RelCall relcall;
    private AcCode acCode;
    private int currentCompany;
    private List<CenterCost> ccosts;
    private DecimalFormat df;
    private RelCallFilter filter;
    private boolean showGraf;
    
    private int firstReg;
    private int qtdeReg;

    public RelCallsBean() {             
        currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
        filter=(RelCallFilter) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filter");
        relcalls=new LazyDataModel<RelCall>() {
            @Override
            public List<RelCall> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                firstReg=first;
                qtdeReg=pageSize;
                List<RelCall> data=new RelCallDAO().getRelCalls(currentCompany,first,qtdeReg,sortField,sortOrder,filters,filter);
                setRowCount(new RelCallDAO().getRelCallsCount(currentCompany,filters,filter));
                return data;
            }              
        };
        try{
            acCode=new AcCodeDAO().getSingleAcCodeByCompany(currentCompany);
        }catch(Exception ex){
            acCode=null;
        }
        ccosts=new CenterCostDAO().getCCosts();
        df=new DecimalFormat("0.00");     
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ccosts", ccosts);
    }
    
    public void alert(RelCall r){
        relcall=r;
        RequestContext.getCurrentInstance()
            .execute("PF('alert').show()");
    }   
    public void hideHistory(RelCall r){
        r.setHide(1);
        String ret=new RelCallDAO().hideHistory(r);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    ret, "Histórico não foi apagado."));
    }
    public String formatSecs(int sec){
        return new FormaterSeconds().secondsToHours(sec);
    }
    public String acCountCall(int bill,int acc){
        if(bill==0||acc==0)
            return "0,00";
        for (CenterCost cc : ccosts) {
            if(cc.getId()==acc)
                return df.format(new Biller().accountCall(bill, cc)).replace(".", ",");
        }
        return "0,00";
    }
    public String translateCCosts(int id){
        if(id==0)
            return "Indeterminado";
        for (CenterCost cc : ccosts) {
            if(cc.getId()==id)
                return cc.getName();
        }
        return "Indeterminado";
    }
    public String translateAcCode(String code){
        if(code.isEmpty())
            return "";
        try{
            for (AcCodePin ap : acCode.getAcCodePins()) {
                if(ap.getPin()==Integer.parseInt(code))
                    return ap.getName();
            }
        }catch(NumberFormatException | NullPointerException ex){
            return "";
        }
        return "";
    }
    public void showGrafic(){
        showGraf=true;
    }
    public String translateStatusCall(String status){
        switch(status){
            case "ANSWERED":
                return "Atendida";
            case "NO ANSWER":
                return "Não Atendida";
            case "BUSY":
                return "Ocupada";
            case "FAILED":
                return "Falhada";
            default:
                return "Desconhecido";
        }
    }
    public RelCall getRelcall() {
        return relcall;
    }

    public void setRelcall(RelCall relcall) {
        this.relcall = relcall;
    }

    public LazyDataModel<RelCall> getRelcalls() {
        return relcalls;
    }

    public RelCallFilter getFilter() {
        return filter;
    }

    public boolean isShowGraf() {
        return showGraf;
    }

    public void setShowGraf(boolean showGraf) {
        this.showGraf = showGraf;
    }

    
}
