/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.chart;

import br.com.jpbx.model.CenterCost;
import br.com.jpbx.util.DacSinteticData;
import br.com.jpbx.util.RelCallFilter;
import br.com.jpbx.util.StatistcPeer;
import java.util.List;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class LineChartFactory {
    
    
    public LineChartModel lineCallsDateOfMonth(int company){
        LineChartModel graf=new LineChartModel();
        
        //graf.setTitle("Ligações por Data");
        graf.setLegendPosition("w");
        graf.setShowPointLabels(true);
        graf.setAnimate(true);
        graf.setZoom(true);
        DateAxis axis=new DateAxis();
        //axis.setMin("2015-10-01");
        //axis.setMax("2015-10-29");
        axis.setTickAngle(-50);
        axis.setTickFormat("%d/%m");
        List<LineChartSeries> chartList=new ChartSeriesFactory().getChartSeries(company);
        if(chartList.isEmpty()){
            graf.setTitle("Não há ligações");
            graf.addSeries(new ChartSeriesFactory().getChartDateSerieEmpty("Vazio"));
        }
        else{
            for(LineChartSeries line:chartList)
                graf.addSeries(line);
        }
        graf.getAxes().put(AxisType.X, axis);
        
        return graf;
    }
    public LineChartModel lineCallsDateOfMonth(RelCallFilter form,List<CenterCost> ccosts){
        LineChartModel graf=new LineChartModel();
        
        //graf.setTitle("Ligações por Data");
        graf.setLegendPosition("w");
        graf.setShowPointLabels(true);
        graf.setAnimate(true);
        graf.setZoom(true);
        DateAxis axis=new DateAxis();
        //axis.setMin("2015-10-01");
        //axis.setMax("2015-10-29");
        axis.setTickAngle(-50);
        axis.setTickFormat("%d/%m");
        List<LineChartSeries> chartList=new ChartSeriesFactory().getChartSeries(form,ccosts);
        if(chartList.isEmpty()){
            graf.setTitle("Não há ligações");
            graf.addSeries(new ChartSeriesFactory().getChartDateSerieEmpty("Vazio"));
        }
        else{
            for(LineChartSeries line:chartList)
                graf.addSeries(line);
        }
        graf.getAxes().put(AxisType.X, axis);
        
        return graf;
    }

    public LineChartModel lineCallsStatistcPeer(List<StatistcPeer> statistcs){
        LineChartModel graf=new LineChartModel();
        
        //graf.setTitle("Ligações por Data");
        graf.setLegendPosition("w");
        graf.setShowPointLabels(true);
        graf.setAnimate(true);
        graf.setZoom(true);
        DateAxis axis=new DateAxis();
        //axis.setMin("2015-10-01");
        //axis.setMax("2015-10-29");
        axis.setTickAngle(-50);
        axis.setTickFormat("%d/%m");
        List<LineChartSeries> chartList=new ChartSeriesFactory().getChartSeries(statistcs);
        if(chartList.isEmpty()){
            graf.setTitle("Não há ligações");
            graf.addSeries(new ChartSeriesFactory().getChartDateSerieEmpty("Vazio"));
        }
        else{
            for(LineChartSeries line:chartList)
                graf.addSeries(line);
        }
        graf.getAxes().put(AxisType.X, axis);
        
        return graf;
    }
    public LineChartModel lineCallsStatistcDAC(List<DacSinteticData> statistcs){
        LineChartModel graf=new LineChartModel();
        
        //graf.setTitle("Ligações por Data");
        graf.setLegendPosition("w");
        graf.setShowPointLabels(true);
        graf.setAnimate(true);
        graf.setZoom(true);
        DateAxis axis=new DateAxis();
        //axis.setMin("2015-10-01");
        //axis.setMax("2015-10-29");
        axis.setTickAngle(-50);
        axis.setTickFormat("%d/%m");
        List<LineChartSeries> chartList=new ChartSeriesFactory().getChartSeriesDAC(statistcs);
        if(chartList.isEmpty()){
            graf.setTitle("Não há ligações");
            graf.addSeries(new ChartSeriesFactory().getChartDateSerieEmpty("Vazio"));
        }
        else{
            for(LineChartSeries line:chartList)
                graf.addSeries(line);
        }
        graf.getAxes().put(AxisType.X, axis);
        
        return graf;
    }
}
