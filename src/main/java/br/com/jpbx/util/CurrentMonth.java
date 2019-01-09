/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class CurrentMonth {

    public String currentMonth(int month){
        String ret="Indefinido";
        switch(month){
            case 0:
                ret="Janeiro";
                break;
            case 1:
                ret="Fevereiro";
                break;
            case 2:
                ret="Março";
                break;
            case 3:
                ret="Abril";
                break;
            case 4:
                ret="Maio";
                break;
            case 5:
                ret="Junho";
                break;
            case 6:
                ret="Julho";
                break;
            case 7:
                ret="Agosto";
                break;
            case 8:
                ret="Setembro";
                break;
            case 9:
                ret="Outubro";
                break;
            case 10:
                ret="Novembro";
                break;
            case 11:
                ret="Dezembro";
                break;
        }
        return ret;
    }
}
