/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.cron;

import br.com.jpbx.model.Acl;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.ProfileDAO;
import br.com.jpbx.util.ResolvDNS;
import java.util.Calendar;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class RenewDnsProfile {
    
    public void renew(){
        boolean reload=false;
        for (Profile p : new ProfileDAO().getAllProfiles()) {
            for (Acl a : p.getAcls()) {
                if(!a.getDns().isEmpty()){
                    String ip=new ResolvDNS().getIpAddress(a.getDns(), a.getIp());
                    if(!ip.equals(a.getIp())){
                        a.setIp(ip);
                        a.setDatetime(Calendar.getInstance().getTime());
                        new ProfileDAO().updateProfileLimited(p);
                        reload=true;
                    }
                }
            }
        }
        if(reload)
            new ProfileDAO().writeProfile();
    }

}
