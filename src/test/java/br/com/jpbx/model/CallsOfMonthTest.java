/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.model;

import br.com.jpbx.chart.ChartSeriesFactory;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class CallsOfMonthTest {
 
    //@Test
    public void testDateCalls(){
        List li=new CallsOfMonthDAO().callsOfMonth(1);
        for(int i=0;i<li.size();i++){
            Object[] line=(Object[]) li.get(i);
            System.out.println(line[0]+" -- "+line[1]+" -- "+line[2]);
        }
        System.out.println(li.size());
    }
}
