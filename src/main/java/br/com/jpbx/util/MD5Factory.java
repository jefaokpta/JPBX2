/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class MD5Factory {
    public String md5(String senha){  
        String sen = "";  
        MessageDigest md = null;  
        try {  
            md = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException ex) {  
            sen=ex.getMessage();
        }  
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
        sen = hash.toString(16);              
        String zeros="";
        if(sen.length()!=32){ //ajustar os 0 (zeros) nos MD5 q iniciam com 0
            int c=32-sen.length();         
            for(int i=0;i<c;i++)
                zeros+="0";
        }
        return zeros+sen; 
    }

}
