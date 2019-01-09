/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.chart.LineChartFactory;
import br.com.jpbx.model.CenterCost;
import br.com.jpbx.util.RelCallFilter;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "relCallsGrafic")
@RequestScoped
public class RelCallsGrafic{

    private LineChartModel lineCalls;
    
    public RelCallsGrafic() {
        lineCalls=new LineChartFactory().lineCallsDateOfMonth(
                (RelCallFilter) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filter"),
                (List<CenterCost>)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ccosts"));
    }

    public LineChartModel getLineCalls() {
        return lineCalls;
    }
    
    
}
