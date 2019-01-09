/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.util;

import br.com.jpbx.model.CenterCost;
import br.com.jpbx.model.CenterCostDAO;
import br.com.jpbx.model.TrunkCost;

/**
 *
 * @author jefaokpta
 */
public class Biller {
    public float accountCall(int bill,CenterCost ccost){
        if(bill>0){
            try{
                if(bill>ccost.getShortage()&&ccost.getFare()>0){
                
                    float res=ccost.getFare()*ccost.getCicle()/60;
                    //System.out.println(res+" ::::::::::::::::::::::::::::::::::");
                    float valFraction=ccost.getFare()*ccost.getFraction()/60;
                    //System.out.println("valor da fracao "+valFraction+ "");
                    float timeRest=bill-ccost.getCicle();
                    if(bill<=ccost.getCicle())
                        timeRest=0;
                    //System.out.println("time rest= "+timeRest+" fracao "+ccost.getFraction());
                    if(timeRest>0){
                        int fractions= (int) Math.ceil((timeRest/ccost.getFraction()));
                        //System.out.println("fracoes "+fractions);
                        res=res+(fractions*valFraction);                   
                    }
                    return res+ccost.getTxService();
                }
            }catch(NullPointerException ex){
                return 0;
            }
        }
        return 0;
    }
    public float accountCall(int bill,TrunkCost ccost){
        if(bill>0){
            try{
                if(bill>ccost.getShortage()&&ccost.getFare()>0){
                
                    float res=ccost.getFare()*ccost.getCicle()/60;
                    //System.out.println(res+" ::::::::::::::::::::::::::::::::::");
                    float valFraction=ccost.getFare()*ccost.getFraction()/60;
                    //System.out.println("valor da fracao "+valFraction+ "");
                    float timeRest=bill-ccost.getCicle();
                    if(bill<=ccost.getCicle())
                        timeRest=0;
                    //System.out.println("time rest= "+timeRest+" fracao "+ccost.getFraction());
                    if(timeRest>0){
                        int fractions= (int) Math.ceil((timeRest/ccost.getFraction()));
                        //System.out.println("fracoes "+fractions);
                        res=res+(fractions*valFraction);                   
                    }
                    return res+ccost.getTxService();
                }
            }catch(NullPointerException ex){
                return 0;
            }
        }
        return 0;
    }
    public float accountCall(int bill,int ccostId){
        if(bill>0){
            CenterCost ccost=new CenterCostDAO().getSingleCCost(ccostId);
            try{
                if(bill>ccost.getShortage()&&ccost.getFare()>0){
                
                    float res=ccost.getFare()*ccost.getCicle()/60;
                    float valFraction=ccost.getFare()*ccost.getFraction()/60;
                    //System.out.println("valor da fracao "+valFraction+ "");
                    float timeRest=bill-ccost.getCicle();
                    if(bill<=ccost.getCicle())
                        timeRest=0;
                    //System.out.println("time rest= "+timeRest+" fracao "+ccost.getFraction());
                    if(timeRest>0){
                        int fractions= (int) Math.ceil((timeRest/ccost.getFraction()));
                        //System.out.println("fracoes "+fractions);
                        res=res+(fractions*valFraction);                   
                    }
                    return res+ccost.getTxService();
                }
            }catch(NullPointerException ex){
                return 0;
            }
        }
        return 0;
    }
}
