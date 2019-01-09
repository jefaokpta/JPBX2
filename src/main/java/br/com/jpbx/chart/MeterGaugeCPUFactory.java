/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.chart;

import br.com.jpbx.linux.LinuxInfo;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class MeterGaugeCPUFactory {
    private float percentCPU;

    public MeterGaugeCPUFactory() {
        percentCPU=new LinuxInfo().getCPUPercent();
    }
    
    public MeterGaugeChartModel meterGaugeCPU(){
        List intervals=new ArrayList<Number>(){{
            add(35);
            add(70);
            add(90);
            add(100);
        }};
        
        MeterGaugeChartModel chart=new MeterGaugeChartModel(percentCPU,intervals);
//        chart.setTitle("Uso da CPU");
        chart.setSeriesColors("66cc66,93b75f,E7E658,cc6666");
        chart.setGaugeLabel("CPU ("+percentCPU+"%)");
//        chart.setGaugeLabelPosition("bottom");
//        chart.setShowTickLabels(false);
//        chart.setLabelHeightAdjust(110);
//        chart.setIntervalOuterRadius(100);
        
        return chart;
    }
}
