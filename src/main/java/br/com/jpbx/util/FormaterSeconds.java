/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.util;

/**
 *
 * @author jefaokpta
 */
public class FormaterSeconds {
    public String secondsToHours(int secs){
        int h,m,secsAux;
        String rh,rm,rs;
        
        h=(int) Math.floor(secs/ 3600);
        rh=String.valueOf(h);
        if(h<10)
            rh="0"+String.valueOf(h);
        
        secsAux=secs-(h*3600);
        m=(int) Math.floor(secsAux/ 60);
        rm=String.valueOf(m);
        if(m<10)
            rm="0"+String.valueOf(m);
        
        secsAux=secsAux-(m*60);
        rs=String.valueOf(secsAux);
        if(secsAux<10)
            rs="0"+String.valueOf(secsAux);
        
        return rh+":"+rm+":"+rs;
    }
}
