/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Entity @Table(name = "queues")
public class Queue implements Serializable{
    @Column(name = "queues_id")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(name = "web_name")
    private String webName;
    @Column(name = "musiconhold")
    private String moh;
    private String announce;
    private String context;
    private int timeout;
    private String type;
    @Column(name = "monitor_type")
    private int monitorType;
    @Column(name = "monitor_format")
    private String monitorFormat;
    @Column(name = "queue_youarenext")
    private String queueYouAreNext;
    @Column(name = "queue_thereare")
    private String queueThereAre;
    @Column(name = "queue_callswaiting")
    private String queueCallsWaiting;
    @Column(name = "queue_holdtime")
    private String queueHoldTime;
    @Column(name = "queue_minutes")
    private String queueMinutes;
    @Column(name = "queue_seconds")
    private String queueSeconds;
    @Column(name = "queue_lessthan")
    private String queueLessThan;
    @Column(name = "queue_thankyou")
    private String queueThankYou;
    @Column(name = "queue_reporthold")
    private String queueReportHold;
    @Column(name = "announce_frequency")
    private int announceFrequency;
    @Column(name = "announce_round_seconds")
    private int announceRoundSeconds;
    @Column(name = "announce_holdtime")
    private String announceHoldTime;
    private int retry;
    private int wrapuptime;
    private int maxlen;
    private int servicelevel;
    private String strategy;
    private String joinempty;
    private String leavewhenempty;
    private int eventmemberstatus;
    private int eventwhencalled;
    private int reportholdtime;
    private int memberdelay;
    private int weight;
    private int timeoutrestart;   
    @Column(name = "periodic_announce")
    private String periodicAnnounce;
    @Column(name = "periodic_announce_frequency")
    private int periodicAnnounceFreq;
    @Column(name = "max_call_queue")
    private int queueMaxCalls;
    @Column(name = "max_time_call")
    private int queueMaxTimeCall;
    @Column(name = "alert_mail")
    private String alertMail;
    private String autopause;
    private int ringinuse;
    private int record;
    @Column(name = "pickupgroup")
    private int pickupGroup;
    private int company;
    
    @OneToMany(cascade = javax.persistence.CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "queues_id")
    List<QueueMember> queueMembers;

    public List<QueueMember> getQueueMembers() {
        return queueMembers;
    }

    public void setQueueMembers(List<QueueMember> queueMembers) {
        this.queueMembers = queueMembers;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getMoh() {
        return moh;
    }

    public void setMoh(String moh) {
        this.moh = moh;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(int monitorType) {
        this.monitorType = monitorType;
    }

    public String getMonitorFormat() {
        return monitorFormat;
    }

    public void setMonitorFormat(String monitorFormat) {
        this.monitorFormat = monitorFormat;
    }

    
    public int getAnnounceFrequency() {
        return announceFrequency;
    }

    public void setAnnounceFrequency(int announceFrequency) {
        this.announceFrequency = announceFrequency;
    }

    public int getAnnounceRoundSeconds() {
        return announceRoundSeconds;
    }

    public void setAnnounceRoundSeconds(int announceRoundSeconds) {
        this.announceRoundSeconds = announceRoundSeconds;
    }

    public String getAnnounceHoldTime() {
        return announceHoldTime;
    }

    public void setAnnounceHoldTime(String announceHoldTime) {
        this.announceHoldTime = announceHoldTime;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public int getWrapuptime() {
        return wrapuptime;
    }

    public void setWrapuptime(int wrapuptime) {
        this.wrapuptime = wrapuptime;
    }

    public int getMaxlen() {
        return maxlen;
    }

    public void setMaxlen(int maxlen) {
        this.maxlen = maxlen;
    }

    public int getServicelevel() {
        return servicelevel;
    }

    public void setServicelevel(int servicelevel) {
        this.servicelevel = servicelevel;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getJoinempty() {
        return joinempty;
    }

    public void setJoinempty(String joinempty) {
        this.joinempty = joinempty;
    }

    public String getLeavewhenempty() {
        return leavewhenempty;
    }

    public void setLeavewhenempty(String leavewhenempty) {
        this.leavewhenempty = leavewhenempty;
    }

    public int getEventmemberstatus() {
        return eventmemberstatus;
    }

    public void setEventmemberstatus(int eventmemberstatus) {
        this.eventmemberstatus = eventmemberstatus;
    }

    public int getEventwhencalled() {
        return eventwhencalled;
    }

    public void setEventwhencalled(int eventwhencalled) {
        this.eventwhencalled = eventwhencalled;
    }

    public int getReportholdtime() {
        return reportholdtime;
    }

    public void setReportholdtime(int reportholdtime) {
        this.reportholdtime = reportholdtime;
    }

    public int getMemberdelay() {
        return memberdelay;
    }

    public void setMemberdelay(int memberdelay) {
        this.memberdelay = memberdelay;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTimeoutrestart() {
        return timeoutrestart;
    }

    public void setTimeoutrestart(int timeoutrestart) {
        this.timeoutrestart = timeoutrestart;
    }

    public String getPeriodicAnnounce() {
        return periodicAnnounce;
    }

    public void setPeriodicAnnounce(String periodicAnnounce) {
        this.periodicAnnounce = periodicAnnounce;
    }

    public int getPeriodicAnnounceFreq() {
        return periodicAnnounceFreq;
    }

    public void setPeriodicAnnounceFreq(int periodicAnnounceFreq) {
        this.periodicAnnounceFreq = periodicAnnounceFreq;
    }

  
    public String getAlertMail() {
        return alertMail;
    }

    public void setAlertMail(String alertMail) {
        this.alertMail = alertMail;
    }

    public String getAutopause() {
        return autopause;
    }

    public void setAutopause(String autopause) {
        this.autopause = autopause;
    }

    public int getRinginuse() {
        return ringinuse;
    }

    public void setRinginuse(int ringinuse) {
        this.ringinuse = ringinuse;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public int getPickupGroup() {
        return pickupGroup;
    }

    public void setPickupGroup(int pickupGroup) {
        this.pickupGroup = pickupGroup;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getQueueYouAreNext() {
        return queueYouAreNext;
    }

    public void setQueueYouAreNext(String queueYouAreNext) {
        this.queueYouAreNext = queueYouAreNext;
    }

    public String getQueueThereAre() {
        return queueThereAre;
    }

    public void setQueueThereAre(String queueThereAre) {
        this.queueThereAre = queueThereAre;
    }

    public String getQueueCallsWaiting() {
        return queueCallsWaiting;
    }

    public void setQueueCallsWaiting(String queueCallsWaiting) {
        this.queueCallsWaiting = queueCallsWaiting;
    }

    public String getQueueHoldTime() {
        return queueHoldTime;
    }

    public void setQueueHoldTime(String queueHoldTime) {
        this.queueHoldTime = queueHoldTime;
    }

    public String getQueueMinutes() {
        return queueMinutes;
    }

    public void setQueueMinutes(String queueMinutes) {
        this.queueMinutes = queueMinutes;
    }

    public String getQueueSeconds() {
        return queueSeconds;
    }

    public void setQueueSeconds(String queueSeconds) {
        this.queueSeconds = queueSeconds;
    }

    public String getQueueLessThan() {
        return queueLessThan;
    }

    public void setQueueLessThan(String queueLessThan) {
        this.queueLessThan = queueLessThan;
    }

    public String getQueueThankYou() {
        return queueThankYou;
    }

    public void setQueueThankYou(String queueThankYou) {
        this.queueThankYou = queueThankYou;
    }

    public String getQueueReportHold() {
        return queueReportHold;
    }

    public void setQueueReportHold(String queueReportHold) {
        this.queueReportHold = queueReportHold;
    }

    public int getQueueMaxCalls() {
        return queueMaxCalls;
    }

    public void setQueueMaxCalls(int queueMaxCalls) {
        this.queueMaxCalls = queueMaxCalls;
    }

    public int getQueueMaxTimeCall() {
        return queueMaxTimeCall;
    }

    public void setQueueMaxTimeCall(int queueMaxTimeCall) {
        this.queueMaxTimeCall = queueMaxTimeCall;
    }
    
}
