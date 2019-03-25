/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.util;

import br.com.jpbx.model.CenterCost;
import org.junit.Test;

/**
 *
 * @author jefaokpta
 */
public class BillerTest {
    /**
     * Test of accountCall method, of class Biller.
     */
    //@Test
    public void testAccountCall() {
        System.out.println("::::: VALOR: "+new Biller().accountCall(30,new CenterCost("bla", 1.00f, 30, 6, 0, 4.00f, 0, 1)));
    }
    //@Test
    public void testSplitBar(){
        String b="/Agent/1000                                         : 2000";
        System.out.println(b.substring(7, b.indexOf(" ")));
        System.out.println(b.split("/")[0]+"fim");
    }
    
}
