/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.chart;

import br.com.jpbx.linux.LinuxInfo;
import java.text.DecimalFormat;
import java.util.List;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class BarChartHD {

    public HorizontalBarChartModel getHDUsage(){
        HorizontalBarChartModel ret=new HorizontalBarChartModel();
        List<Long> hd=new LinuxInfo().getHDUsage();
        long total=hd.get(0),usedVal=hd.get(1);
        
        ret.setLegendPosition("e");
        ret.setStacked(true);
        
       String[] units = new String[] { "kB", "MB", "GB", "TB" };
       int digitGroups = (int) (Math.log10(total)/Math.log10(1024));
       String totalFormated=new DecimalFormat("#,##0.#").format(total/Math.pow(1024, digitGroups));
       String unit=units[digitGroups];
       
       digitGroups = (int) (Math.log10(usedVal)/Math.log10(1024));
       String usedFormated=new DecimalFormat("#,##0.#").format(usedVal/Math.pow(1024, digitGroups));
        
        ChartSeries used=new ChartSeries("Usado");
        used.set("HD", Float.parseFloat(usedFormated.replace(",", ".")));
//        ChartSeries free=new ChartSeries("Livre");
//        used.set("HD", 36);
               
        //ret.addSeries(free);
        ret.addSeries(used);
        
        Axis xAxis=ret.getAxis(AxisType.X);
        xAxis.setTickAngle(-50);
        xAxis.setTickFormat("%.1f "+unit);
        xAxis.setMin(0);
        xAxis.setMax(Float.parseFloat(totalFormated.replace(",", ".")));
        
        return ret;
    }
}
