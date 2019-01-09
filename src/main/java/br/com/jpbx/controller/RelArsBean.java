/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.HangupCauseDAO;
import br.com.jpbx.util.HangupCauseFilter;
import br.com.jpbx.util.RelASR;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "relArsBean")
@ViewScoped
public class RelArsBean implements Serializable{
    
    private HangupCauseFilter filter;
    private List<RelASR> asrs;
    private BarChartModel barModel;

    public RelArsBean() {
        filter=(HangupCauseFilter) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("filterAsr");
        asrs=new HangupCauseDAO().getRelASR(filter);
        barModel=new BarChartModel();
        barModel.setAnimate(true);
        barModel.setLegendPosition("ne");
        ChartSeries answer=new ChartSeries("Atendidas");
        ChartSeries noAnswer=new ChartSeries("Não Atendidas");
        ChartSeries fail=new ChartSeries("Falhadas");
        for (RelASR asr : asrs) {
            answer.set(asr.getTrunkName(), asr.getAnswer());
            noAnswer.set(asr.getTrunkName(), asr.getNoAnswer());
            fail.set(asr.getTrunkName(), asr.getFail());
        }
        barModel.addSeries(answer);
        barModel.addSeries(noAnswer);
        barModel.addSeries(fail);
    }

    public String showAverage(int base, int total){
        if(total==0)
            return "0%";
        return (100*base/total)+"%";
    }
    public List<RelASR> getAsrs() {
        return asrs;
    }

    public HangupCauseFilter getFilter() {
        return filter;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }
    
}
