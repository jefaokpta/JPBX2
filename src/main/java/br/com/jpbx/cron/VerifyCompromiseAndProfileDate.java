/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.cron;

import br.com.jpbx.asterisk.Asterisk;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.ProfileDAO;
import br.com.jpbx.model.Route;
import br.com.jpbx.model.RouteDAO;
import java.util.Calendar;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class VerifyCompromiseAndProfileDate {

    public void verifyBoth(){
        // ulimit descriptors 65536.
        new Asterisk().getInfos("ulimit descriptors 65536");
        
        Calendar day=Calendar.getInstance();
        //out.print(day.get(Calendar.DAY_OF_MONTH));
        for (Profile p : new ProfileDAO().getProfilesLimited()) {
            if(day.get(Calendar.DAY_OF_MONTH)==p.getCutDate()){
                p.setCurrentValue(0);
                new ProfileDAO().updateProfileLimited(p);
            }
        }
        for (Route r : new RouteDAO().getAllRoutesLimited()) {
            if(day.get(Calendar.DAY_OF_MONTH)==r.getCutDay()){
                r.setCurrentMin(0);
                new RouteDAO().updateRouteCompromise(r);
            }
        }
    } 
}
