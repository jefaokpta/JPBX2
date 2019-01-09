/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class UserScreenFactory {
    public Map<String,String> buildUserScreen(String nivel){
        Map<String,String> res=new HashMap<>();
        if(nivel.equals("Administrador")){
            res.put("Site", "1");
            res.put("Dashboard", "2");
            res.put("Servidor", "3");
        }
        else{
            res.put("Site", "1");
            res.put("Dashboard", "2");
        }
        return res;
    }
}
