/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.chart.MeterGaugeCPUFactory;
import br.com.jpbx.chart.MeterGaugeRAMFactory;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "cPUAndRAMBean")
@RequestScoped
public class CPUAndRAMBean {
    private MeterGaugeChartModel cpu;
    private MeterGaugeChartModel ram;
    /**
     * Creates a new instance of CPUAndRAMBean
     */
    public CPUAndRAMBean() {
        cpu=new MeterGaugeCPUFactory().meterGaugeCPU();
        ram=new MeterGaugeRAMFactory().meterGaugeRAM();
    }

    public MeterGaugeChartModel getCpu() {
        return cpu;
    }

    public MeterGaugeChartModel getRam() {
        return ram;
    }
    
}
