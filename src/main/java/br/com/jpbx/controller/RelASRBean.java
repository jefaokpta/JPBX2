/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Trunk;
import br.com.jpbx.model.TrunkDAO;
import br.com.jpbx.util.HangupCauseFilter;
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
@Named(value = "relASRBean")
@ViewScoped
public class RelASRBean implements Serializable{

    private HangupCauseFilter filter;
    private DualListModel<String> trunksSelection;
    
    public RelASRBean() {
        filter=new HangupCauseFilter();
        List<String> trunkSrc=new ArrayList<>();
        filter.setTrunks(new ArrayList<String>());
        for (Trunk t : new TrunkDAO().getAllTrunks()) {
            trunkSrc.add(t.getId()+" - "+t.getName());
        }
        trunksSelection=new DualListModel<>(trunkSrc, filter.getTrunks());
        Calendar hj=Calendar.getInstance();
        filter.setEnd(hj.getTime());
        hj.set(Calendar.DAY_OF_MONTH, 1);
        filter.setStart(hj.getTime());
    }
    public void onTransfer(TransferEvent ev){
        for (Object item : ev.getItems()) {
            filter.getTrunks().add(item.toString());
        }
    }
    public String showRelAsr(){
        if(filter.getTrunks().isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Escolha ao menos 1 tronco.", ""));
            return null;
        }
        Calendar hours=Calendar.getInstance();
        hours.setTime(filter.getEnd());
        hours.set(Calendar.HOUR_OF_DAY, 23);
        hours.set(Calendar.MINUTE, 59);      
        hours.set(Calendar.SECOND, 59);   
        filter.setEnd(hours.getTime());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filterAsr", filter);
        return "/restricted/relAsr";
    }
    public String showRelDetraf(){
        if(filter.getTrunks().isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Escolha ao menos 1 tronco.", ""));
            return null;
        }
        Calendar hours=Calendar.getInstance();
        hours.setTime(filter.getEnd());
        hours.set(Calendar.HOUR_OF_DAY, 23);
        hours.set(Calendar.MINUTE, 59);      
        hours.set(Calendar.SECOND, 59);   
        filter.setEnd(hours.getTime());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("filterAsr", filter);
        return "/restricted/detrafShow";
    }

    public HangupCauseFilter getFilter() {
        return filter;
    }

    public void setFilter(HangupCauseFilter filter) {
        this.filter = filter;
    }

    public DualListModel<String> getTrunksSelection() {
        return trunksSelection;
    }

    public void setTrunksSelection(DualListModel<String> trunksSelection) {
        this.trunksSelection = trunksSelection;
    }
    
}
