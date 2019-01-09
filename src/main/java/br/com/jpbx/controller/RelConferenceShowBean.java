/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.RelConference;
import br.com.jpbx.model.RelConferenceDAO;
import br.com.jpbx.util.HandleAudio;
import br.com.jpbx.util.RelConferenceFilter;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "relConferenceShowBean")
@ViewScoped
public class RelConferenceShowBean implements Serializable{

    private RelConferenceFilter form;
    private LazyDataModel<RelConference> conferences;
    private int firstReg;
    private int qtdeReg;
    
    public RelConferenceShowBean() {
        form=(RelConferenceFilter) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("relConfFilter");
        conferences=new LazyDataModel<RelConference>() {
            @Override
            public List<RelConference> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                firstReg=first;
                qtdeReg=pageSize;
                List<RelConference> data=new RelConferenceDAO().getRelConferences(form, firstReg, qtdeReg, sortField, sortOrder,filters);
                setRowCount(new RelConferenceDAO().getRelConfCount(filters, form));
                return data;
            }              
        };
    }
    public void deleteConference(RelConference c){
        new HandleAudio().removeConference(c.getRecord());
        new RelConferenceDAO().deleteConference(c);
    }
    public LazyDataModel<RelConference> getConferences() {
        return conferences;
    }

    public RelConferenceFilter getForm() {
        return form;
    }

    
}
