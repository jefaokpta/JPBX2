/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
 * @author jefaokpta
 */
@Entity @Table(name = "dialplan_rules")
public class DialPlanRule implements Serializable{

    @Column(name = "rule_id")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "rule_name")
    private String name;
    private String src;
    private String dst;
    private int active;
    private int priority;
    @Column(name = "src_action")
    private String srcAction;
    @Column(name = "src_action_param")
    private String srcActionParam;
    private int fulltime;
    @Column(name = "hstart")
    private String timeStart;
    @Column(name = "hend")
    private String timeEnd;
    private int seg;
    private int ter;
    private int qua;
    private int qui;
    private int sex;
    private int sab;
    private int dom;
    @Column(name = "src_alias")
    private int srcAlias;
    @Column(name = "dst_alias")
    private int dstAlias;   
    private int company;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "rule_id")
    private List<DialPlanAction> actions;

    public List<DialPlanAction> getActions() {
        return actions;
    }

    public void setActions(List<DialPlanAction> actions) {
        this.actions = actions;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.src);
        hash = 37 * hash + Objects.hashCode(this.dst);
        hash = 37 * hash + Objects.hashCode(this.srcAction);
        hash = 37 * hash + this.fulltime;
        hash = 37 * hash + Objects.hashCode(this.timeStart);
        hash = 37 * hash + Objects.hashCode(this.timeEnd);
        hash = 37 * hash + this.seg;
        hash = 37 * hash + this.ter;
        hash = 37 * hash + this.qua;
        hash = 37 * hash + this.qui;
        hash = 37 * hash + this.sex;
        hash = 37 * hash + this.sab;
        hash = 37 * hash + this.dom;
        hash = 37 * hash + this.srcAlias;
        hash = 37 * hash + this.dstAlias;
        hash = 37 * hash + this.company;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DialPlanRule other = (DialPlanRule) obj;
        if (this.fulltime != other.fulltime) {
            return false;
        }
        if (this.seg != other.seg) {
            return false;
        }
        if (this.ter != other.ter) {
            return false;
        }
        if (this.qua != other.qua) {
            return false;
        }
        if (this.qui != other.qui) {
            return false;
        }
        if (this.sex != other.sex) {
            return false;
        }
        if (this.sab != other.sab) {
            return false;
        }
        if (this.dom != other.dom) {
            return false;
        }
        if (this.srcAlias != other.srcAlias) {
            return false;
        }
        if (this.dstAlias != other.dstAlias) {
            return false;
        }
        if (this.company != other.company) {
            return false;
        }
        if (!Objects.equals(this.src, other.src)) {
            return false;
        }
        if (!Objects.equals(this.dst, other.dst)) {
            return false;
        }
        if (!Objects.equals(this.srcAction, other.srcAction)) {
            return false;
        }
        if (!Objects.equals(this.timeStart, other.timeStart)) {
            return false;
        }
        if (!Objects.equals(this.timeEnd, other.timeEnd)) {
            return false;
        }
        return true;
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

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getSrcAction() {
        return srcAction;
    }

    public void setSrcAction(String srcAction) {
        this.srcAction = srcAction;
    }

    public String getSrcActionParam() {
        return srcActionParam;
    }

    public void setSrcActionParam(String srcActionParam) {
        this.srcActionParam = srcActionParam;
    }

    public int getFulltime() {
        return fulltime;
    }

    public void setFulltime(int fulltime) {
        this.fulltime = fulltime;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getSeg() {
        return seg;
    }

    public void setSeg(int seg) {
        this.seg = seg;
    }

    public int getTer() {
        return ter;
    }

    public void setTer(int ter) {
        this.ter = ter;
    }

    public int getQua() {
        return qua;
    }

    public void setQua(int qua) {
        this.qua = qua;
    }

    public int getQui() {
        return qui;
    }

    public void setQui(int qui) {
        this.qui = qui;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSab() {
        return sab;
    }

    public void setSab(int sab) {
        this.sab = sab;
    }

    public int getDom() {
        return dom;
    }

    public void setDom(int dom) {
        this.dom = dom;
    }

    public int getSrcAlias() {
        return srcAlias;
    }

    public void setSrcAlias(int srcAlias) {
        this.srcAlias = srcAlias;
    }

    public int getDstAlias() {
        return dstAlias;
    }

    public void setDstAlias(int dstAlias) {
        this.dstAlias = dstAlias;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    
    
}
