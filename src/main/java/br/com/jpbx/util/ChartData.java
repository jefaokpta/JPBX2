/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.util;

import java.util.Date;

/**
 *
 * @author jefaokpta
 */
public class ChartData {
    private Date calldate;
    private int count;
    private int acc;

    public ChartData() {
    }

    public ChartData(Date calldate, int count, int acc) {
        this.calldate = calldate;
        this.count = count;
        this.acc = acc;
    }

    public Date getCalldate() {
        return calldate;
    }

    public void setCalldate(Date calldate) {
        this.calldate = calldate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAcc() {
        return acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }
    
    
}
