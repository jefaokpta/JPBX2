/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk;

import br.com.jpbx.linux.AstLogFail;
import br.com.jpbx.model.Config;
import br.com.jpbx.model.ConfigDAO;
import br.com.jpbx.util.Email;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class VerifyAsteriskUp {
    
    public void astUp(){
        Config config=new ConfigDAO().getConfig();
        if(config.getAstup()>0){
            if(new Asterisk().getInfos("core show uptime").isEmpty()){
                AstLogFail fail=new AstLogFail();
                fail.killAllAst();
                fail.logfail();
                System.out.println(":::::::::::::::::::::::::::: PARANDO TODOS SERVICOS JPBX PARA RECOLHER LOGS :::::::::::::::::::::::::::::::::");
                fail.startAst();
                new Email().emailRecoverAstFail(config.getEmail().split(","), config.getName());
                System.out.println(":::::::::::::::::::::::::::: REINICIANDO TODOS SERVICOS JPBX  :::::::::::::::::::::::::::::::::");
            }
        }
    }

}
