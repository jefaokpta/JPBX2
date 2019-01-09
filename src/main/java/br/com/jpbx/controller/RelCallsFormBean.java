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
import br.com.jpbx.util.RelCallFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "relCallsFormBean")
@ViewScoped
public class RelCallsFormBean implements Serializable{

    private int currentCompany;
    private List<String> accSource;
    private DualListModel<String> accSelection;
    private List<String> acCodeSource;
    private DualListModel<String> acCodeSelection;
    private AcCode acCode;
    private RelCallFilter filter;
    
    public RelCallsFormBean() {
        currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCompany");
        filter=new RelCallFilter();
        filter.setCompany(currentCompany);
        filter.setAccTarget(new ArrayList<String>());
        filter.setAcCodeTarget(new ArrayList<String>());
        accSource=new ArrayList<>();
        for (CenterCost acc : new CenterCostDAO().getCCosts(currentCompany)) {
            accSource.add(acc.getId()+" - "+acc.getName());
        }
        accSelection=new DualListModel<>(accSource, filter.getAccTarget());
        filter.setStatus("ALL");
        Calendar hj=Calendar.getInstance();
        filter.setEndDate(hj.getTime());
        hj.set(Calendar.DAY_OF_MONTH, 1);
        filter.setStartDate(hj.getTime());
        acCodeSource=new ArrayList<>();      
        try{
            acCode=new AcCodeDAO().getSingleAcCodeByCompany(currentCompany);
             for (AcCodePin acp : acCode.getAcCodePins()) {
                acCodeSource.add(acp.getPin()+" -  "+acp.getName());
            }
        }catch(Exception ex){
            acCode=null;
        }
        acCodeSelection=new DualListModel<>(acCodeSource, filter.getAcCodeTarget());
    }
    public void onTransfer(TransferEvent ev){
        for (Object item : ev.getItems()) {
            filter.getAccTarget().add(item.toString().split(" ")[0]);
        }
    }
    public void onTransferAcCode(TransferEvent ev){
        for (Object item : ev.getItems()) {
            filter.getAcCodeTarget().add(item.toString().split(" ")[0]);
        }
    }
    public String showRelCalls(){
        if(filter.getStartDate().after(filter.getEndDate())){
            FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "A data de Início deve ser anterior a data de fim", ""));
            return null;
        }
        Calendar hours=Calendar.getInstance();
        hours.setTime(filter.getEndDate());
        hours.set(Calendar.HOUR_OF_DAY, 23);
        hours.set(Calendar.MINUTE, 59);      
        hours.set(Calendar.SECOND, 59);   
        filter.setEndDate(hours.getTime());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filter", filter);
        return "/restricted/relcalls";
    }

    public DualListModel<String> getAccSelection() {
        return accSelection;
    }

    public void setAccSelection(DualListModel<String> accSelection) {
        this.accSelection = accSelection;
    }

    public List<String> getAccSource() {
        return accSource;
    }

    public void setAccSource(List<String> accSource) {
        this.accSource = accSource;
    }

    public RelCallFilter getFilter() {
        return filter;
    }

    public void setFilter(RelCallFilter filter) {
        this.filter = filter;
    }

   

    public List<String> getAcCodeSource() {
        return acCodeSource;
    }

    public void setAcCodeSource(List<String> acCodeSource) {
        this.acCodeSource = acCodeSource;
    }


    public DualListModel<String> getAcCodeSelection() {
        return acCodeSelection;
    }

    public void setAcCodeSelection(DualListModel<String> acCodeSelection) {
        this.acCodeSelection = acCodeSelection;
    }
    
}
