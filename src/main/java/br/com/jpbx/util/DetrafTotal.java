/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class DetrafTotal {
    
    private int totalCall;
    private float cost;
    private float sale;
    private float profit;

    public DetrafTotal() {
    }

    public DetrafTotal(int totalCall, float cost, float sale, float profit) {
        this.totalCall = totalCall;
        this.cost = cost;
        this.sale = sale;
        this.profit = profit;
    }
    
    

    public int getTotalCall() {
        return totalCall;
    }

    public void setTotalCall(int totalCall) {
        this.totalCall = totalCall;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }
    
    

}
