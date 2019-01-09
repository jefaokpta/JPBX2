/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.util.regex.Pattern;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class VerifyPassword {
    
    public boolean verifyPassword(String pass){
        boolean res=true;
        int l=0,n=0;       
        for(int i=0;i<pass.length();i++){
            if(Pattern.matches("[a-zA-Z]", String.valueOf(pass.charAt(i))))
                l++;
            else if(Pattern.matches("[0-9]", String.valueOf(pass.charAt(i))))
                n++;
        }
        if(l<2||n<2)
            res=false;
        return res;
    }
    public boolean verifyPasswordRange(int pass){
        return pass>99&&pass<10000;
    }
}
