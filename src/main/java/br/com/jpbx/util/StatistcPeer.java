/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.util.Date;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class StatistcPeer {
    
    private int id;
    private Date day;
    private String type;
    private int callTotal;
    private int answered;
    private int notAnswered;
    private int busy;
    private int callTimeTotal;
    private int callTimeSpeak;
    private int callTimeWait;
    private int meddleCall;
    private int meddleSpeak;
    private int meddleWait;
    private String label;

    public StatistcPeer(Date day, String type) {
        this.day = day;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCallTotal() {
        return callTotal;
    }

    public void setCallTotal(int callTotal) {
        this.callTotal = callTotal;
    }

    public int getAnswered() {
        return answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }

    public int getNotAnswered() {
        return notAnswered;
    }

    public void setNotAnswered(int notAnswered) {
        this.notAnswered = notAnswered;
    }

    public int getBusy() {
        return busy;
    }

    public void setBusy(int busy) {
        this.busy = busy;
    }

    public int getCallTimeTotal() {
        return callTimeTotal;
    }

    public void setCallTimeTotal(int callTimeTotal) {
        this.callTimeTotal = callTimeTotal;
    }

    public int getCallTimeSpeak() {
        return callTimeSpeak;
    }

    public void setCallTimeSpeak(int callTimeSpeak) {
        this.callTimeSpeak = callTimeSpeak;
    }

    public int getCallTimeWait() {
        return callTimeWait;
    }

    public void setCallTimeWait(int callTimeWait) {
        this.callTimeWait = callTimeWait;
    }

    public int getMeddleCall() {
        return meddleCall;
    }

    public void setMeddleCall(int meddleCall) {
        this.meddleCall = meddleCall;
    }

    public int getMeddleSpeak() {
        return meddleSpeak;
    }

    public void setMeddleSpeak(int meddleSpeak) {
        this.meddleSpeak = meddleSpeak;
    }

    public int getMeddleWait() {
        return meddleWait;
    }

    public void setMeddleWait(int meddleWait) {
        this.meddleWait = meddleWait;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    

}
