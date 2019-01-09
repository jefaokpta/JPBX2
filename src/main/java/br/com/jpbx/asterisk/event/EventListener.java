/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk.event;

import br.com.jpbx.asterisk.AsteriskAuthentication;
import br.com.jpbx.asterisk.SipShowPeer;
import java.io.IOException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.SipPeersAction;
import org.asteriskjava.manager.action.SipShowPeerAction;
import org.asteriskjava.manager.event.AgentCalledEvent;
import org.asteriskjava.manager.event.AgentCompleteEvent;
import org.asteriskjava.manager.event.AgentConnectEvent;
import org.asteriskjava.manager.event.AgentRingNoAnswerEvent;
import org.asteriskjava.manager.event.BridgeCreateEvent;
import org.asteriskjava.manager.event.BridgeDestroyEvent;
import org.asteriskjava.manager.event.BridgeEnterEvent;
import org.asteriskjava.manager.event.BridgeLeaveEvent;
import org.asteriskjava.manager.event.CdrEvent;
import org.asteriskjava.manager.event.ChallengeSentEvent;
import org.asteriskjava.manager.event.DeviceStateChangeEvent;
import org.asteriskjava.manager.event.DialEvent;
import org.asteriskjava.manager.event.ExtensionStatusEvent;
import org.asteriskjava.manager.event.HangupEvent;
import org.asteriskjava.manager.event.HangupRequestEvent;
import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.event.MusicOnHoldStartEvent;
import org.asteriskjava.manager.event.MusicOnHoldStopEvent;
import org.asteriskjava.manager.event.NewAccountCodeEvent;
import org.asteriskjava.manager.event.NewCallerIdEvent;
import org.asteriskjava.manager.event.NewChannelEvent;
import org.asteriskjava.manager.event.NewConnectedLineEvent;
import org.asteriskjava.manager.event.NewExtenEvent;
import org.asteriskjava.manager.event.NewStateEvent;
import org.asteriskjava.manager.event.PeerEntryEvent;
import org.asteriskjava.manager.event.PeerStatusEvent;
import org.asteriskjava.manager.event.QueueCallerAbandonEvent;
import org.asteriskjava.manager.event.QueueCallerJoinEvent;
import org.asteriskjava.manager.event.QueueCallerLeaveEvent;
import org.asteriskjava.manager.event.QueueEntryEvent;
import org.asteriskjava.manager.event.QueueMemberEvent;
import org.asteriskjava.manager.event.QueueMemberPauseEvent;
import org.asteriskjava.manager.event.QueueMemberRemovedEvent;
import org.asteriskjava.manager.event.QueueParamsEvent;
import org.asteriskjava.manager.event.QueueStatusCompleteEvent;
import org.asteriskjava.manager.event.QueueSummaryCompleteEvent;
import org.asteriskjava.manager.event.QueueSummaryEvent;
import org.asteriskjava.manager.event.RtcpReceivedEvent;
import org.asteriskjava.manager.event.RtcpSentEvent;
import org.asteriskjava.manager.event.SoftHangupRequestEvent;
import org.asteriskjava.manager.event.SuccessfulAuthEvent;
import org.asteriskjava.manager.event.VarSetEvent;
import org.asteriskjava.manager.response.SipShowPeerResponse;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class EventListener implements ManagerEventListener{

    private static ManagerConnection mc;

    public EventListener() {
        
    }

    public ManagerConnection getMc() {
        if(mc==null){
            AsteriskAuthentication auth=new AsteriskAuthentication();
            ManagerConnectionFactory factory=new ManagerConnectionFactory(
                    auth.getIp(),auth.getUserEvent(),auth.getPasswordEvent()
            );       
            mc=factory.createManagerConnection();
        }
        return mc;
    } 
    
    
    @Override
    public void onManagerEvent(ManagerEvent event) {
        
        if(event instanceof PeerStatusEvent){
            new HandleEvent().handle((PeerStatusEvent) event);
            return;
        }
        
        if(event instanceof NewChannelEvent){
            new HandleEvent().handle((NewChannelEvent) event);
            return;
        }
        
        if(event instanceof NewStateEvent){
            new HandleEvent().handle((NewStateEvent) event);
            return;
        }
            
        if(event instanceof HangupEvent){
            new HandleEvent().handle((HangupEvent) event);
            return;
        }
        if(event instanceof QueueSummaryEvent){
            new HandleQueue().handle((QueueSummaryEvent) event);
            return;
        }
        if(event instanceof QueueParamsEvent){
            new HandleQueue().handle((QueueParamsEvent) event);
            return;
        }
        if(event instanceof QueueMemberEvent){
            new HandleAgent().handle((QueueMemberEvent) event);
            return;
        }
        if(event instanceof QueueMemberRemovedEvent){
            new HandleAgent().handle((QueueMemberRemovedEvent) event);
            return;
        }
        if(event instanceof QueueEntryEvent){
            new HandleAgent().handle((QueueEntryEvent) event);
            return;
        }
        if(event instanceof AgentConnectEvent){
            new HandleAgent().handle((AgentConnectEvent) event);
            return;
        }
        if(event instanceof QueueCallerAbandonEvent){
            new HandleAgent().handle((QueueCallerAbandonEvent) event);
            return;
        }
        if(event instanceof QueueMemberPauseEvent){
            new HandleAgent().handle((QueueMemberPauseEvent) event);
            return;
        }
        // ANALISAR
        if(event instanceof AgentCalledEvent){  // PODE MOSTRAR ALGO AO CHAMAR AGENTE e SETAR INFOS DA CHAMADA (direction e callerid)
            new HandleAgent().handle((AgentCalledEvent) event);
            return;
        }
        if(event instanceof NewConnectedLineEvent){  // ACHO Q DA PRA USAR PRA RECEBER FILA OU INTERNA STATE 5 ENTRADA STATE 4 SAIDA
            //new HandleAgent().handle((QueueMemberPauseEvent) event);
            return;
        }
        if(event instanceof QueueCallerJoinEvent){
            //new HandleAgent().handle((QueueMemberPauseEvent) event);
            return;
        }
        if(event instanceof QueueCallerLeaveEvent){ // APARENTEMENTE SOH QND O CARA SAI DA FILA PRO AGENTE LEAVEWHENEMPTY SE ENCAIXA AQUI
            return;
        }
        if(event instanceof PeerEntryEvent){ 
            //new HandleAgent().handle((QueueMemberPauseEvent) event);
            return;
        }
        
        // ATE O MOMENTO INUTEIS
        if(event instanceof SoftHangupRequestEvent)
            return;
        if(event instanceof AgentCompleteEvent)
            return;
        if(event instanceof HangupRequestEvent)
            return;
        if(event instanceof BridgeCreateEvent)
            return;
        if(event instanceof BridgeDestroyEvent)
            return;
        if(event instanceof BridgeLeaveEvent)
            return;
        if(event instanceof BridgeEnterEvent)
            return;
        if(event instanceof DialEvent){
            new HandleEvent().handle((DialEvent) event);
            return;
        }
        if(event instanceof CdrEvent)
            return;
        if(event instanceof VarSetEvent)
            return;
        if(event instanceof ExtensionStatusEvent)
            return;
        if(event instanceof NewExtenEvent)
            return;
        if(event instanceof QueueSummaryCompleteEvent)
            return;
        if(event instanceof QueueStatusCompleteEvent)
            return;
        if(event instanceof RtcpSentEvent)
            return;
        if(event instanceof RtcpReceivedEvent)
            return;
        // DEPOIS DO GIT
        if(event instanceof ChallengeSentEvent)
            return;
        if(event instanceof DeviceStateChangeEvent)
            return;
        if(event instanceof SuccessfulAuthEvent)
            return;
        if(event instanceof NewCallerIdEvent)
            return;
        if(event instanceof NewAccountCodeEvent)
            return;
        if(event instanceof MusicOnHoldStartEvent)
            return;
        if(event instanceof MusicOnHoldStopEvent)
            return;
        if(event instanceof AgentRingNoAnswerEvent)
            return;
        
        System.out.println(event);
    }

}
