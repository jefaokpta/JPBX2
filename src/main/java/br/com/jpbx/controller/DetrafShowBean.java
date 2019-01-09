/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.CenterCost;
import br.com.jpbx.model.CenterCostDAO;
import br.com.jpbx.model.RelCallDAO;
import br.com.jpbx.model.TrunkCostDAO;
import br.com.jpbx.util.DetrafTotal;
import br.com.jpbx.util.HangupCauseFilter;
import br.com.jpbx.util.TrunkDetraf;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "detrafShowBean")
@ViewScoped
public class DetrafShowBean implements Serializable{
    private HangupCauseFilter filter;
    private List<TrunkDetraf> detrafs;
    private List<CenterCost> centerCosts;
    private DecimalFormat df;
    private List<DetrafTotal> totals;
    private BarChartModel barModel;
    
    public DetrafShowBean() {
        centerCosts=new CenterCostDAO().getCCosts();
        filter=(HangupCauseFilter) javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filterAsr");
        detrafs=new RelCallDAO().getRelDetraf(filter, new TrunkCostDAO().getAllTrunkCosts(), centerCosts);
        df=new DecimalFormat("0.00");    
        
        totals=new ArrayList<>();
        int totalCall=0;
        float cost=0;
        float sale=0;
        float profit=0;
        for (TrunkDetraf d : detrafs) {
            totalCall++;
            cost+=d.getCost();
            sale+=d.getSale();
            profit+=d.getProfit();
        }
        totals.add(new DetrafTotal(totalCall, cost, sale, profit));
        barModel=new BarChartModel();
        barModel.setAnimate(true);
        ChartSeries tronco=new ChartSeries();
        for (String s : filter.getTrunks()) {
            String name=s.split("-")[1].trim();    
            int qtde=0;
            for (TrunkDetraf d : detrafs) {
                if(name.equals(d.getTrunk().trim()))
                    qtde++;
            }
            tronco.set(name, qtde);
        }
        barModel.addSeries(tronco);
    }
    public String acCountCall(float value){
        return df.format(value).replace(".", ",");
    }
    public List<TrunkDetraf> getDetrafs() {
        return detrafs;
    }

    public HangupCauseFilter getFilter() {
        return filter;
    }

    public List<DetrafTotal> getTotals() {
        return totals;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }
    
}
