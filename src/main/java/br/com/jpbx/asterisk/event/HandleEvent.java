/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk.event;

import java.util.Map;
import org.asteriskjava.manager.event.DialEvent;
import org.asteriskjava.manager.event.HangupEvent;
import org.asteriskjava.manager.event.NewChannelEvent;
import org.asteriskjava.manager.event.NewStateEvent;
import org.asteriskjava.manager.event.PeerStatusEvent;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class HandleEvent {
    
    private Map<String,PeerStatusControl> pscc;

    public HandleEvent() {
        pscc=new PeerStatusCenterControl().getPeerStatusController();
    }
       
    public void handle(PeerStatusEvent pse){      
        //System.out.println("::::: Ramal "+pse.getPeer()+" - STATUS "+pse.getPeerStatus());
        if(pscc.containsKey(pse.getPeer())){
            PeerStatusControl psc=pscc.get(pse.getPeer());
            psc.setRegisterStatus(pse.getPeerStatus());
            if(pse.getAddress()!=null)
                psc.setAddress(pse.getAddress());
            //System.out.println(":::::::::::::::::::::::: EDITADO DADO EM PSCC");
            return;
        }   
        pscc.put(pse.getPeer(),new PeerStatusControl(pse.getPeer(), pse.getPeerStatus(),pse.getAddress()));
        //System.out.println(":::::::::::::::::::::::: NOVO DADO EM PSCC");
    }

    public void handle(NewChannelEvent nce) {
        //System.out.println("::::: Ramal "+nce.getChannel()+" - "+nce.getChannelStateDesc()+" - "+nce.getChannelState()+" - "+nce.getExten()+" - "+nce.getUniqueId());
        if(nce.getContext().equals("Jpbx-Peers")){           
            if(pscc.containsKey(nce.getChannel().split("-")[0])){
                PeerStatusControl psc=pscc.get(nce.getChannel().split("-")[0]);
                if(!nce.getExten().equals("s")){
                    psc.setExten(nce.getExten());
                    psc.setDirection(0);
                    psc.setChannel(nce.getChannel());
                    psc.setStateDesc(nce.getChannelStateDesc());
                    psc.setState(nce.getChannelState());
                    psc.setUniqueid(nce.getUniqueId());
                }
                return;
            }
            pscc.put(nce.getChannel().split("-")[0],
                    new PeerStatusControl(nce.getChannel().split("-")[0], nce.getChannelStateDesc(), 
                            nce.getChannelState(), 
                            (nce.getExten().equals("s")?"":nce.getExten()),
                            nce.getUniqueId(), nce.getChannel(),
                            nce.getDateReceived()));
            return;
        }
        // SE FOR KHOMP
        if(nce.getChannel().split("/")[0].equals("Khomp")&&nce.getCallerIdNum().length()<5){
            if(pscc.containsKey(nce.getChannel().split("-")[0])){
                PeerStatusControl psc=pscc.get(nce.getChannel().split("-")[0]);
                psc.setExten(nce.getExten());
                psc.setDirection(0);
                psc.setChannel(nce.getChannel());
                psc.setStateDesc(nce.getChannelStateDesc());
                psc.setState(nce.getChannelState());
                psc.setUniqueid(nce.getUniqueId());
                psc.setPeer(nce.getCallerIdNum());
                return;
            }
            pscc.put(nce.getChannel().split("-")[0],
                    new PeerStatusControl(nce.getCallerIdNum(), nce.getChannelStateDesc(), 
                            nce.getChannelState(), 
                            (nce.getExten().equals("s")?"":nce.getExten()),
                            nce.getUniqueId(), nce.getChannel(),
                            nce.getDateReceived()));
        }  
    }

    public void handle(NewStateEvent nse) {
       // System.out.println("::::::::::::::: NOVO  ESTADO "+nse.getChannelStateDesc()+" "+nse.getChannelState());
        if(pscc.containsKey(nse.getChannel().split("-")[0])){
            PeerStatusControl psc=pscc.get(nse.getChannel().split("-")[0]);
            psc.setState(nse.getChannelState());
            psc.setStateDesc(nse.getChannelStateDesc());
            switch(nse.getChannelState()){
                case 5:
                    if(nse.getExten().contains("s")){
                        psc.setDirection(1);
                        psc.setExten(nse.getConnectedLineNum());
                        psc.setChannel(nse.getChannel());
                        psc.setUniqueid(nse.getUniqueId());
                        return;
                    }
                    psc.setDirection(0);
                    psc.setExten(nse.getExten());
                    return;
                case 6:
                    psc.setLastCall(nse.getDateReceived());
                    return;
                default:
                    psc.setDirection(0);
            }
        }
    }

    public void handle(HangupEvent he) {
        if(pscc.containsKey(he.getChannel().split("-")[0])){
            PeerStatusControl psc=pscc.get(he.getChannel().split("-")[0]);
            psc.setState(0);
            psc.setStateDesc("Down");
            if(he.getChannel().split("/")[0].equals("Khomp"))
                psc.setPeer("");
        }
    }

    void handle(DialEvent de) {
        if(de.getSubEvent().equals("Begin")&&de.getDestination().split("/")[0].equals("Khomp")){
            String peer=de.getDialString().split("/")[1];
            if(peer.length()<5){
                if(peer.length()==3)
                    peer="1"+peer;
                if(pscc.containsKey(de.getDestination().split("-")[0])){
                    PeerStatusControl psc=pscc.get(de.getDestination().split("-")[0]);
                    psc.setExten(de.getExten());
                    psc.setDirection(0);
                    psc.setChannel(de.getChannel());
                    psc.setStateDesc(de.getChannelStateDesc());
                    psc.setState(de.getChannelState());
                    psc.setUniqueid(de.getUniqueId());
                    psc.setPeer(peer);
                    return;
                }
                pscc.put(de.getDestination().split("-")[0],
                    new PeerStatusControl(peer, de.getChannelStateDesc(), 
                            de.getChannelState(), 
                            (de.getExten().equals("s")?"":de.getExten()),
                            de.getUniqueId(), de.getChannel(),
                            de.getDateReceived()));
            }
        }
    }

}
