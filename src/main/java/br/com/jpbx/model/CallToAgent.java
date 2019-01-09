/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class CallToAgent {
    private String tel;
    private String queue;
    private String txt;

    public CallToAgent() {
    }

    public CallToAgent(String tel, String queue, String txt) {
        this.tel = tel;
        this.queue = queue;
        this.txt = txt;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
    
    

}
