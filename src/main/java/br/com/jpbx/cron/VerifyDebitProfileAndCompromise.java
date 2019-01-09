/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.cron;

import br.com.jpbx.asterisk.Asterisk;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.ProfileDAO;
import br.com.jpbx.model.RelCall;
import br.com.jpbx.model.RelCallDAO;
import br.com.jpbx.model.Route;
import br.com.jpbx.model.RouteDAO;
import br.com.jpbx.util.Biller;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class VerifyDebitProfileAndCompromise {
    
    public void verifyBoth(){
        for (Route r : new RouteDAO().getAllRoutesLimited()) {
            if(new Asterisk().getVar("GROUP_COUNT(route"+r.getId()+")").equals("0")){
                for (RelCall rc : new RelCallDAO().getCallsRouteToDebit(r.getId())) {
                    if(rc.getBillsec()>0){
                        r.setCurrentMin(r.getCurrentMin()+rc.getBillsec());
                        new RouteDAO().updateRouteCompromise(r);
                    }
                    rc.setDebitRoute(1);
                    new RelCallDAO().hideHistory(rc);
                }
            }
        }
        for (Profile p : new ProfileDAO().getProfilesLimited()) {
            if(new Asterisk().getVar("GROUP_COUNT(profile"+p.getId()+")").equals("0")){
                for (RelCall rc : new RelCallDAO().getCallsProfileToDebit(p.getId())) {
                    if(rc.getBillsec()>0){
                        p.setCurrentValue(p.getCurrentValue()+new Biller().accountCall(rc.getBillsec(), rc.getAccountcode()));
                        new ProfileDAO().updateProfileLimited(p);
                    }
                    rc.setDebitProfile(1);
                    new RelCallDAO().hideHistory(rc);
                }
            }
        }
    }

}
