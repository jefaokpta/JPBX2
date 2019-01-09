/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.linux;

import br.com.jpbx.model.Company;
import br.com.jpbx.model.DialPlanAction;
import br.com.jpbx.model.DialPlanRule;
import br.com.jpbx.model.MohDAO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jefaokpta
 */
public class WriteExtensions {
    
    public String writeContext(List<DialPlanRule> rules){
        if(rules.isEmpty())
            return "ok";
        int company=rules.get(0).getCompany();
        File makeFile=new File("/etc/asterisk/jpbx/rule_"+company+".conf"); 
        try {
            if(!makeFile.exists())           
                makeFile.createNewFile();
            FileWriter fw = new FileWriter(makeFile);
            fw.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            fw.write("[jpbxRoute"+company+"]\n\n");
            
            fw.write("include => hangupActions\n\n");
            
            for (DialPlanRule r : rules) {
                String days="";
                if(r.getFulltime()==0){ // ajustando dias e tempo de função da regra
                    days="&&${CURL(${WEB_APP}:${WEB_PORT}/jpbx/VerifyDayServlet,"
                            + "seg="+r.getSeg()+"&ter="+r.getTer()+"&qua="+r.getQua()+""
                            + "&qui="+r.getQui()+"&sex="+r.getSex()+"&sab="+r.getSab()+"&dom="+r.getDom()+""
                            + ")}&&${IFTIME("+r.getTimeStart()+"-"+r.getTimeEnd()+",*,*,*?1:0)}";
                }
                String aliasDst="";  // verifica se usara Alias
                if(r.getDstAlias()>0){
                    aliasDst="&&${CURL(${WEB_APP}:${WEB_PORT}/jpbx/VerifyAliasDstSevlet,"
                            + "alias="+r.getDstAlias()+"&exten=${DST}&chan=${CHANNEL})}";
                }
                String dst=r.getDst().equals("ALIAS")?"ALIAS":"_"+r.getDst(); // UNDERLINE se não for ALIAS
                String prio=r.getPriority()>1?"n(alias"+r.getPriority()+")":"1"; // DEFINE SE REGRA EH PRIORIDADE 1 OU n
                // 1º prioridade da regra origens
                fw.write("exten => "+dst+","+prio+",GotoIf($["  
                        + "${CURL(${WEB_APP}:${WEB_PORT}/jpbx/RuleActiveServlet,id="+r.getId()+")}"
                        + "&&"+srcDefinitions(r)+days+aliasDst+"]?:auxDialPlan,"+(r.getPriority()+1)+","+
                        (r.getDst().equals("ALIAS")?"testAgain":"1")+")\n"); // MANDA PRA ANALISE NUMERICA OU ALIASES
                // 2ª prio
                fw.write("same => n,Noop(:::::::::: EXECUTANDO REGRA: "+r.getName()+" - "+r.getId()+"  ::::::::::)\n");
                fw.write("same => n,Set(ODBC_TRANSFER_DATA(${MASTER_UNIQUEID},${ORG},${DST})=1);\n");
                //fw.write("same => n,AelSub(TransferAjust,"+r.getName()+","+r.getId()+")\n");               
                // AÇÕES fw.write("same => n,Noop(EXEMPLO)");
                Collections.sort(r.getActions());
                for (DialPlanAction a : r.getActions()) {
                    for(String exten:actionDefinitions(a)){
                        fw.write("same => n,"+exten+"\n");
                    }                   
                }
                fw.write("\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            return "FALHA AO ESCREVER REGRA: "+ex.getMessage();
        }              
        return "ok";
    }
    List<String> actionDefinitions(DialPlanAction a){
        List<String> actions=new ArrayList<>();
        switch(a.getAct()){
            case "dialPeer":
                if(a.getArg1().equals("")){
                    actions.add("AelSub(dialPeer,${DST},"+a.getArg2()+","+a.getArg3()+","+a.getArg4()+")");
                    return actions;
                }
                actions.add("AelSub(dialPeer,"+a.getArg1()+","+a.getArg2()+","+a.getArg3()+","+a.getArg4()+")");
                return actions;
            case "dialAgent":
                if(a.getArg1().equals("")){
                    actions.add("AelSub(dialAgent,${DST},"+a.getArg2()+","+a.getArg3()+","+a.getArg4()+")");
                    return actions;
                }
                actions.add("AelSub(dialAgent,"+a.getArg1()+","+a.getArg2()+","+a.getArg3()+","+a.getArg4()+")");
                return actions;
            case "trunkRoute":
                actions.add("AelSub(dialRoute,"+a.getArg1()+")");
                return actions;
            case "callgrp":
                actions.add("AelSub(callGrp,"+a.getArg1()+","+a.getArg2()+")");
                return actions;
            case "queue":
                actions.add("AelSub(callQueue,"+a.getArg1()+","+a.getArg2()+")");
                return actions;
            case "editDst":
                if(a.getArg2().equals("1")){
                    actions.add("Set(DST="+a.getArg1()+")");
                    return actions;
                }
                if(a.getArg4().equals("1")){
                    actions.add("SET(DST="+a.getArg1()+"${DST:${ALIASCUT}})");
                    return actions;
                }
                actions.add("SET(DST="+a.getArg1()+"${DST:"+a.getArg3()+"})");
                return actions;
            case "editSrc":
                if(a.getArg2().equals("1")){
                    //actions.add("Set(ORG="+a.getArg1()+")");
                    actions.add("Set(CALLERID(num)="+a.getArg1()+")");
                    return actions;
                }
                //actions.add("SET(ORG="+a.getArg1()+"${ORG:"+a.getArg3()+"})");
                actions.add("SET(CALLERID(num)="+a.getArg1()+"${ORG:"+a.getArg3()+"})");
                return actions;
            case "hangup":
                actions.add("Hangup");
                return actions;
            case "answer":
                actions.add("Answer");
                return actions;
            case "playbacks":
                String moh=new MohDAO().getSingleMoh(Integer.parseInt(a.getArg1())).getMoh();
                actions.add("PlayBack(/etc/asterisk/jpbx/moh/"+moh+"/"+moh+")");
                return actions;
            case "receiveFax":
                actions.add("Set(FAX_EMAIL="+(a.getArg1().equals("1")?a.getArg2():"")+")");
                actions.add("Goto(receiveFax,${DST},1)");
                return actions;
            case "sendFax":
                actions.add("Noop(::::: EXECUTANDO ENVIAR FAX :::::)");
                return actions;
            case "ignoreVM":
                actions.add("Set(IGNOREVM=1)");
                return actions;
            case "ura":
                actions.add("Goto(URA_"+a.getArg1()+",s,1)");
                return actions;
            case "accode":
                actions.add("AelSub(acCode,"+a.getArg1()+","+a.getArg2()+")");
                return actions;
            case "pickupGrp":
                actions.add("Set(__PICKUPMARK="+a.getArg1()+")");
                return actions;
            case "command":
                actions.add(a.getArg1()+"("+a.getArg2()+")");
                return actions;
            case "rollbackDst":
                actions.add("Set(DST=${CDR(dstfinal)})");
                return actions;
            case "rollbackSrc":
                actions.add("Set(CALLERID(num)=${CDR(srcfinal)})");
                return actions;
            case "ccost":
                actions.add("Set(CHANNEL(accountcode)="+a.getArg1()+")");
                return actions;
            case "voicemail":
                //actions.add("AelSub(goVoicemail,"+a.getArg1()+")");
                actions.add("VoiceMail("+a.getArg1()+",s)");
                return actions;
            case "company":
                actions.add("Set(CDR(company)="+a.getArg1()+")");
                actions.add("SET(EMP="+a.getArg1()+")");
                actions.add("Set(DPLAN=jpbxRoute"+a.getArg1()+")");
                actions.add("Goto(auxDialPlanForRules,${DST},1)");
                return actions;
            case "bdo":
                actions.add("AelSub(webServiceBDO,"+a.getArg1()+")");
                actions.add("Goto(auxDialPlanForRules,${DST},1)");
                return actions;
            default:
                actions.add("Noop(::::: TEM ALGO ERRADO NA REGRA "+a.getRuleId()+" PRIORIDADE "+a.getPriority()+" :::::)");
                return actions;
        }
    }
    String srcDefinitions(DialPlanRule d){ //USERFIELDS 1-INTERNA 2-EXTERNA 3-FAX
        switch(d.getSrc()){
            case "peer":
                if(d.getSrcAction().equals("all"))
                    return "${CDR(userfield)}==1&&${CURL(${WEB_APP}:${WEB_PORT}/jpbx/VerifyPeersServlet,"
                            + "peer=${ORG})}";
                return "${CDR(userfield)}==1&&${ORG}=="+d.getSrcAction();
            case "qlqr":
                return "1";
            case "fax":
                return "${CDR(userfield)}==3";
            case "regex":
                return "${REGEX(\""+d.getSrcAction()+"\" ${ORG})}";
            case "pickupgrp":
                return "${CURL(${WEB_APP}:${WEB_PORT}/jpbx/VerifyPickupGrpsServlet,"
                        + "pickup="+d.getSrcAction()+"&peer=${DB(PeerId/${ORG})})}";
            case "trunk":
                if(d.getSrcAction().equals("all"))
                    return "${CDR(userfield)}==2";
                return "${CDR(userfield)}==2&&\"${TRUNKID}\"==\""+d.getSrcAction()+"\"";
            case "alias":
                return "${CURL(${WEB_APP}:${WEB_PORT}/jpbx/VerifyAliasSrcServlet,"
                        + "alias="+d.getSrcAction()+"&src=${ORG})}";
            default:
                return "";
        }
    }
    public String writeRules(List<Company> companys){
        File makeRules=new File("/etc/asterisk/jpbx/rules.conf"); 
        File makeRule;
        try {
            if(!makeRules.exists())           
                makeRules.createNewFile();
            FileWriter fw = new FileWriter(makeRules);
            fw.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            for (Company c : companys) {
                fw.write("#include jpbx/rule_"+c.getId()+".conf\n");
                makeRule=new File("/etc/asterisk/jpbx/rule_"+c.getId()+".conf");
                if(!makeRule.exists()){
                    makeRule.createNewFile();
                    FileWriter fwRule = new FileWriter(makeRule);
                    fwRule.write("[jpbxRoute"+c.getId()+"]\n\n");
                    fw.write("include => hangupActions\n\n");
                    fwRule.flush();
                    fwRule.close();
                }
            }
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            return "FALHA AO ESCREVER REGRA: "+ex.getMessage();
        }
        return "ok";
    }
}
