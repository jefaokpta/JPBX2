/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class HangupCauseFilter implements Serializable{
    
    private Date start;
    private Date end;
    private List<String> trunks;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<String> getTrunks() {
        return trunks;
    }

    public void setTrunks(List<String> trunks) {
        this.trunks = trunks;
    }
    
    
}
