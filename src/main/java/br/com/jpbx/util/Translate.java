/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.util;

import br.com.jpbx.model.Peer;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.Ura;
import java.util.List;

/**
 *
 * @author jefaokpta
 */
public class Translate {
    public String transUraParam(List whats,String act,String param){
        switch(act){
            case "hangup":
                return "";
            case "begin":
                return "";
            case "peer":
                for (Object what : whats) {
                    Peer p=(Peer) what;
                    if(param.equals(String.valueOf(p.getName())))
                        return p.getCallerid()+" - "+p.getName();
                }
                return "Inválido";
            case "callGrp":
                for (Object what : whats) {
                    Queue q=(Queue) what;
                    if(param.equals(String.valueOf(q.getId())))
                        return q.getWebName();
                }
                return "Inválido";
            case "subUra":
                for (Object what : whats) {
                    Ura u=(Ura) what;
                    if(param.equals(u.getName()))
                        return u.getWebName();
                }
                return "Inválido";
            case "queue":
                for (Object what : whats) {
                    Queue q=(Queue) what;
                    if(param.equals(String.valueOf(q.getId())))
                        return q.getWebName();
                }
                return "Inválido";
            case "rule":
                return param; 
            default:
                return "Indefinido";
        }
    }
    public String transUraOption(String act){
        switch(act){
            case "hangup":
                return "Desligar";
            case "begin":
                return "Voltar ao Início";
            case "peer":
                return "Discar para Ramal";
            case "callGrp":
                return "Grupo de Chamada";
            case "subUra":
                return "Sub URA";
            case "queue":
                return "Fila de Atendimento";
            case "rule":
                return "Regra Especial"; 
            default:
                return "Indefinido";
        }
    }
    
    public String translateDialplanAction(String act){
        switch(act){
            case "hangup":
                return "Desligar";
            case "answer":
                return "Atender";
            case "playbacks":
                return "Tocar Audio";
            case "dialPeer":
                return "Discar Ramal";
            case "dialAgent":
                return "Discar Agente";
            case "editDst":
                return "Editar Destino";
            case "editSrc":
                return "Editar Origem"; 
            case "rollbackDst":
                return "Restaurar Destino"; 
            case "rollbackSrc":
                return "Restaurar Origem"; 
            case "command":
                return "Comando Livre"; 
            case "ccost":
                return "Definir Custo"; 
            case "trunkRoute":
                return "Enviar para Rota"; 
            case "callgrp":
                return "Enviar para Grupo"; 
            case "queue":
                return "Enviar para Fila"; 
            case "ura":
                return "Enviar para URA"; 
            case "voicemail":
                return "Enviar para Voicemail"; 
            case "company":
                return "Enviar para Empresa"; 
            case "sendFax":
                return "Enviar FAX"; 
            case "receiveFax":
                return "Receber FAX"; 
            case "accode":
                return "Código de Conta"; 
            case "pickupGrp":
                return "Definir Captura"; 
            case "ignoreVM":
                return "Ignorar Voicemail"; 
            case "bdo":
                return "Consultar BDO"; 
            
            default:
                return "Indefinido";
        }
    }
    
}
