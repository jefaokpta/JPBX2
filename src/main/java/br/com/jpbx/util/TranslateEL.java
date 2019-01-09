/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.util;

/**
 *
 * @author jefaokpta
 */
public class TranslateEL {
    public String translateELAst(String el){
        String res=el;
        //res=res.toUpperCase();
        res=res.replaceAll("_", "");
        res=res.replaceAll("\\|", "");
        res=res.replaceAll("X", "[0-9]");
        res=res.replaceAll("x", "[0-9]");
        res=res.replaceAll("Z", "[1-9]");
        res=res.replaceAll("z", "[1-9]");
        res=res.replaceAll("N", "[2-9]");
        res=res.replaceAll("n", "[2-9]");
        res=res.replaceAll("\\.", "[0-9].*");
        res=res.replaceAll("!", ".*");
        
        return res;
    }
}
