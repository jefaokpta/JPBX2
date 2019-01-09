/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.chart;

import br.com.jpbx.linux.LinuxInfo;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class MeterGaugeRAMFactory {

    private List<Integer> ramUsage;
    public MeterGaugeRAMFactory() {
        ramUsage=new LinuxInfo().getRAMUsage();
    }

    
    public MeterGaugeChartModel meterGaugeRAM(){
        List intervals=new ArrayList<Number>(){{
            add((ramUsage.get(0)*0.30));
            add((ramUsage.get(0)*0.60));
            add((ramUsage.get(0)*0.90));
            add(ramUsage.get(0));
        }};
        
        MeterGaugeChartModel chart=new MeterGaugeChartModel(
                (ramUsage.get(1)-ramUsage.get(2)),
                intervals);
//        chart.setTitle("Uso da CPU");
        chart.setSeriesColors("66cc66,93b75f,E7E658,cc6666");
        //String formatNum=NumberFormat.getCurrencyInstance().format((ramUsage.get(1)-ramUsage.get(2)));
        chart.setGaugeLabel("RAM ("+(ramUsage.get(1)-ramUsage.get(2))+" MB)");
//        chart.setGaugeLabelPosition("bottom");
//        chart.setShowTickLabels(false);
//        chart.setLabelHeightAdjust(110);
//        chart.setIntervalOuterRadius(100);
        
        return chart;
    }
}
