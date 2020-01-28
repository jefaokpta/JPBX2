/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class CurrentMonthTest {
    //@Test
    public void testBreak(){
        String[] a={"200","1000","201"};
        String[] b={"200","1000","201"};
        for (int i=0;i<a.length;i++) { // ADD CONHECIDO OU NOVO
            for (int j=0;j<b.length;j++) {
                if(a[i].equals(b[j])){
                    System.out.println("Dentro "+a[i]+" "+b[j]);
                    break;
                }
                System.out.println("FORA "+a[i]+" "+b[j]);
                
            }          
        }
    }
    //@Test
    public void testDate(){
        Calendar hj=Calendar.getInstance();
        hj.set(Calendar.HOUR_OF_DAY, 23);
        hj.set(Calendar.MINUTE, 59);
        Date d=hj.getTime();
        System.out.println(d.toString());
        System.out.println(new Timestamp(d.getTime()).toString().split("\\.")[0]);
        System.out.println(new Timestamp(d.getTime()).toString().split(" ")[0]);
    }
    //@Test
    public void testList(){
        List<String> l=new ArrayList<>();
        l.add("1");
        l.add("2");
        l.add("3");
        l.add("4");
        String t=l.toString().replace("[", "(");
        t=t.replace("]", ")");
        System.out.println(" IN "+t);
    }
    public void testAliasS(){
        Pattern patt;
        Matcher matt;
        patt=Pattern.compile(new TranslateEL().translateELAst("s"));
        matt=patt.matcher("s");
        System.out.println(new TranslateEL().translateELAst("s"));
        System.out.println(matt.matches());
    }
    public void testSeekString(){
        String ch="Khomp/b1c29";
        if(ch.split("/")[0].toUpperCase().equals("KHOMP")){
            String b=ch.substring(ch.indexOf("b")+1, ch.indexOf("c"));
            String c=ch.substring(ch.indexOf("c")+1);
            System.out.println("placa "+b+" canal "+c);
        }
    }
    public void testUtils(){
        float test= 2.0F;
        float test2= 2.01F;
        DecimalFormat df=new DecimalFormat("0.00");
        System.out.println(df.format(test).replace(",", "."));
        System.out.println(df.format(test2).replace(",", "."));
    }
    
}
