/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.chart.BarChartHD;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.chart.HorizontalBarChartModel;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "hDBean")
@RequestScoped
public class HDBean {

    private HorizontalBarChartModel hd;
    /**
     * Creates a new instance of HDBean
     */
    public HDBean() {
        hd=new BarChartHD().getHDUsage();
    }

    public HorizontalBarChartModel getHd() {
        return hd;
    }
    
}
