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
public class ValidatePeer {

    public boolean validatePeerNumber(int peer){
        return String.valueOf(peer).matches(
                "^([1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])$");
    }
}
