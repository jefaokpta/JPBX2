/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.chart.LineChartFactory;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "testeGrafico")
@RequestScoped
public class TesteGrafico {
    private LineChartModel lineCallsDateMonth;
    /**
     * Creates a new instance of TesteGrafico
     */
    public TesteGrafico() {
        lineCallsDateMonth=new LineChartFactory().lineCallsDateOfMonth(
            (int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany"));
    }

    public LineChartModel getLineCallsDateMonth() {
        return lineCallsDateMonth;
    }
    
}
