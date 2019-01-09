/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.chart;

import br.com.jpbx.model.CallsOfMonthDAO;
import br.com.jpbx.model.CenterCost;
import br.com.jpbx.model.CenterCostDAO;
import br.com.jpbx.model.RelCallDAO;
import br.com.jpbx.util.DacSinteticData;
import br.com.jpbx.util.RelCallFilter;
import br.com.jpbx.util.StatistcPeer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.primefaces.model.chart.LineChartSeries;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class ChartSeriesFactory {
    
    private List<CenterCost> ccosts; 

    public List<LineChartSeries> getChartSeries(int company){
        List<LineChartSeries> ret=new ArrayList<>();
        ccosts=new CenterCostDAO().getCCosts();
        List callsOfMonth=new CallsOfMonthDAO().callsOfMonthInMemory(company);
        List<String> aux=new ArrayList();
        for(int i=0;i<callsOfMonth.size();i++){
            Object[] line=(Object[]) callsOfMonth.get(i);
            //System.out.println(line[0]+" e "+line[1]+" e "+line[2]);
            // SOMA CALLDATE ACCOUNTCODE
            if(!aux.contains(line[2].toString())){
                aux.add(line[2].toString());
                ret.add(lineChart(callsOfMonth, line[2].toString()));
            }
        }
        return ret;
    }
    public List<LineChartSeries> getChartSeries(RelCallFilter form,List<CenterCost> ccosts){
        List<LineChartSeries> ret=new ArrayList<>();
        this.ccosts=ccosts;
        List callsOfMonth=new RelCallDAO().getRelCallsToGrafic(form);
        List<String> aux=new ArrayList();
        for(int i=0;i<callsOfMonth.size();i++){
            Object[] line=(Object[]) callsOfMonth.get(i);
            //System.out.println(line[0]+" e "+line[1]+" e "+line[2]);
            // SOMA CALLDATE ACCOUNTCODE
            if(!aux.contains(line[2].toString())){
                aux.add(line[2].toString());
                ret.add(lineChart(callsOfMonth, line[2].toString()));
            }
        }
        return ret;
    }
    List<LineChartSeries> getChartSeries(List<StatistcPeer> statistcs) {
        List<LineChartSeries> res=new ArrayList<>();
        LineChartSeries inbound=new LineChartSeries("Recebida");
        LineChartSeries outbound=new LineChartSeries("Efetuada");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy 12:00:00");
        for (StatistcPeer st : statistcs) {
            if(st.getType().equals("Recebida")){
                inbound.setLabel("Recebida");
                inbound.set(df.format(st.getDay()), st.getCallTotal());
                continue;
            }
            outbound.setLabel("Efetuada");
            outbound.set(df.format(st.getDay()), st.getCallTotal());
        }
        res.add(outbound);
        res.add(inbound);
        return res;
    }
    List<LineChartSeries> getChartSeriesDAC(List<DacSinteticData> statistcs) {
        List<LineChartSeries> res=new ArrayList<>();
        LineChartSeries answered=new LineChartSeries("Atendida");
        LineChartSeries noAnswer=new LineChartSeries("Não Atendida");
        LineChartSeries abandon=new LineChartSeries("Abandonada");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy 12:00:00");
        for (DacSinteticData dsd : statistcs) {
            answered.setLabel("Atendida");
            answered.set(df.format(dsd.getDay()), dsd.getAnswer());
            noAnswer.setLabel("Não Atendida");
            noAnswer.set(df.format(dsd.getDay()), dsd.getNoAnswer());
            abandon.setLabel("Abandonada");
            abandon.set(df.format(dsd.getDay()), dsd.getAbandon());
        }
        res.add(answered);
        res.add(noAnswer);
        res.add(abandon);
        return res;
    }
    private LineChartSeries lineChart(List list,String title){
        LineChartSeries lineChart=new LineChartSeries();
        lineChart.setLabel("indefinido "+title);
        for(CenterCost c:ccosts)
            if(String.valueOf(c.getId()).equals(title)){
                lineChart.setLabel(c.getName());
                break;
            }
        for(int i=0;i<list.size();i++){
            Object[] line=(Object[]) list.get(i);
            
            if((line[2].toString().equals(title))){
                lineChart.set(line[1].toString(), Integer.parseInt(line[0].toString()));
                //System.out.println(line[1].toString()+" "+ line[0].toString());
            }
        }

        return lineChart;
    }
    public LineChartSeries getChartDateSerieEmpty(String label){
        LineChartSeries ret=new LineChartSeries(label);
        Calendar hj=Calendar.getInstance();
        ret.set(hj.get(Calendar.YEAR)+"-"+(hj.get(Calendar.MONTH)+1)+"-"+hj.get(Calendar.DAY_OF_MONTH), 0);
        
        return ret;
    }

    
   
}
