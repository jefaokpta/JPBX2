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
public class DefineUserPriority {
    public int userPriority(String level){
        int userPriority=1;
        switch (level) {
            case "Supervisor":
                userPriority=2;
                break;
            case "Gerente":
                userPriority=3;
                break;
            case "Administrador":
                userPriority=4;
                break;
        }
        return userPriority;
    }
}
