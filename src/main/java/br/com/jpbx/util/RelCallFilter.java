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
public class RelCallFilter implements Serializable{
    
    private Date startDate;
    private Date endDate;
    private List<String> accTarget;
    private List<String> acCodeTarget;
    private String status;
    private String peers;
    private boolean showAcCode;
    private int company;

    public String getPeers() {
        return peers;
    }

    public void setPeers(String peers) {
        this.peers = peers;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }
    

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<String> getAccTarget() {
        return accTarget;
    }

    public void setAccTarget(List<String> accTarget) {
        this.accTarget = accTarget;
    }

    public List<String> getAcCodeTarget() {
        return acCodeTarget;
    }

    public void setAcCodeTarget(List<String> acCodeTarget) {
        this.acCodeTarget = acCodeTarget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isShowAcCode() {
        return showAcCode;
    }

    public void setShowAcCode(boolean showAcCode) {
        this.showAcCode = showAcCode;
    }
    
    
    
}
