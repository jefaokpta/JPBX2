/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class RelASR {
    
    private int trunkId;
    private String trunkName;
    private int answer;
    private int noAnswer;
    private int fail;
    private int total;

    public int getTrunkId() {
        return trunkId;
    }

    public void setTrunkId(int trunkId) {
        this.trunkId = trunkId;
    }

    public String getTrunkName() {
        return trunkName;
    }

    public void setTrunkName(String trunkName) {
        this.trunkName = trunkName;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getNoAnswer() {
        return noAnswer;
    }

    public void setNoAnswer(int noAnswer) {
        this.noAnswer = noAnswer;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    

}
