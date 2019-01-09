/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jefaokpta
 */
@Entity @Table(name = "dialplan_actions")
public class DialPlanAction implements Serializable, Comparable<DialPlanAction>{
    @Column(name = "action_id")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String act;
    private int priority;
    private String arg1;
    private String arg2;
    private String arg3;
    private String arg4;
    private String arg5;
    @Column(name = "rule_id")
    private int ruleId;

    public DialPlanAction() {
    }

    public DialPlanAction(int priority,String act, String arg1, String arg2, String arg3, String arg4, String arg5) {
        this.act = act;
        this.priority=priority;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
        this.arg4 = arg4;
        this.arg5 = arg5;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getArg3() {
        return arg3;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    public String getArg4() {
        return arg4;
    }

    public void setArg4(String arg4) {
        this.arg4 = arg4;
    }

    public String getArg5() {
        return arg5;
    }

    public void setArg5(String arg5) {
        this.arg5 = arg5;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public int compareTo(DialPlanAction da) {
        if(this.priority>da.priority)
            return 1;
        return -1;
    }
    
    
    
}
