/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.asterisk.AsteriskActions;
import br.com.jpbx.asterisk.ServerStatus;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "astInfoBean")
@RequestScoped
public class AstInfoBean {
    private List<ServerStatus> statusServer;
    /**
     * Creates a new instance of AstInfoBean
     */
    public AstInfoBean() {
        statusServer=new AsteriskActions().getAstUptime();
    }

    public List<ServerStatus> getStatusServer() {
        return statusServer;
    }
    
}
