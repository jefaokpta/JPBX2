/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.cron;

import br.com.jpbx.model.ConfigDAO;
import br.com.jpbx.model.InvasionDAO;
import br.com.jpbx.model.WhiteList;
import br.com.jpbx.model.WhiteListDAO;
import br.com.jpbx.util.ValidateIP;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class VerifyInvasions {
    
    public void antiInvasion(){
        String line,cut;
        List<String> ips=new ArrayList<>();
        try {
            BufferedReader  br = new BufferedReader(new FileReader("/var/log/asterisk/block"));
            while(br.ready()){  // POE SOMENTE IPS
                line = br.readLine();  
                if(line.contains("Wrong password")||line.contains("matching peer")){
                    cut=line.substring(line.indexOf("failed"));
                    ips.add(cut.substring(cut.indexOf("'")+1, cut.indexOf(":")));
                    //System.out.println("register "+cut.substring(cut.indexOf("'")+1, cut.indexOf(":")));
                    //System.out.println(line.split("([0-9]{1,3}\\.){3}[0-9]{1,3}")[1]);  
                continue;
                }
                if(line.contains("tried to authenticate")){
                    cut=line.split(" ")[5];
                    if(!cut.matches("([0-9]{1,3}\\.){3}[0-9]{1,3}"))
                        cut=line.split(" ")[6];
                        ips.add(cut);
                    //System.out.println("manager "+cut);
                }
            }
            br.close();
            FileWriter clearLog=new FileWriter(new File("/var/log/asterisk/block")); // LIMPA LOG
            clearLog.write("zerado");
            clearLog.close();
            // VERIFICA SE ESTA ATIVO
            if(new ConfigDAO().getConfig().getInvasion()==0){
                System.out.println(":::::::::: ANTI INVASAO DESATIVADO ::::::::::::::::");
                return;
            }
            FileWriter out=new FileWriter(new File("/tmp/blockIps")); // ARQ DE IPS PRA SER CONTADO AS OCORRENCIAS
            for (String ip : ips) {
                out.write(ip+"\n");
            }
            out.close();
            String[] cmd=new String[3];
            cmd[0]="/bin/bash";
            cmd[1]="-c";
            cmd[2]="cat /tmp/blockIps | sort | uniq -c";
            Process ls_proc=Runtime.getRuntime().exec(cmd);
            BufferedReader ls_in = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));
            String str;
            List<String> block=new ArrayList<>();
            while ((str = ls_in.readLine()) != null) {
                str=str.trim();
                if(Integer.parseInt(str.split(" ")[0])>2)
                    if(new ValidateIP().validIP(str.split(" ")[1]))
                        block.add(str.split(" ")[1]);
            }
            List<String> blockAux=new ArrayList<>(block);
            List<WhiteList> whiteList=new WhiteListDAO().getAllWhitelists();
            if(!whiteList.isEmpty()){
                for (String ip : blockAux) {
                    System.out.println("CANDIDATO A BLOQUEIO :::::"+ip+":::::::::::::::::::::::::::::::::::::::::::");
                    for (WhiteList w : whiteList) {
                        //if(w.getIp().equals(ip.substring(0, w.getIp().length()))){  // problema do ip ser menor q a descricao whitelist
                        if(ip.contains(w.getIp())){
                            block.remove(ip);
                            break;
                        }
                    }
                }
            }
            if(!block.isEmpty()){
                TimeZone tz=TimeZone.getTimeZone("America/Sao_Paulo");
                TimeZone.setDefault(tz);
                Calendar today=Calendar.getInstance(tz);
                new InvasionDAO().persistNewInvasion(block,today.getTime());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ops : "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("ops : "+ex.getMessage());
        }
    }

}
