/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import br.com.jpbx.util.HangupCauseFilter;
import br.com.jpbx.util.RelASR;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class HangupCauseDAO {

    private EntityManager em;
    
    public HangupCauseDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<RelASR> getRelASR(HangupCauseFilter form){       
        try{
            String formSql="";
            if(!form.getTrunks().isEmpty()){
                List<String> trunks=new ArrayList<>();
                for (String s : form.getTrunks()) {
                    trunks.add(s.split("-")[0].trim());
                }
                formSql=trunks.toString().replace("[", "(");
                formSql=formSql.replace("]", ")");
                formSql=" AND h.trunk IN "+formSql;
            }     
            String sql="SELECT h FROM HangupCause as h WHERE h.datetime BETWEEN :startDate AND :endDate "+formSql;
            TypedQuery <HangupCause> query=em.createQuery(sql,HangupCause.class);
            query.setParameter("startDate", form.getStart(),TemporalType.TIMESTAMP);
            query.setParameter("endDate", form.getEnd(),TemporalType.TIMESTAMP);
            List<HangupCause> hangs=query.getResultList();
            List<RelASR> asrs=new ArrayList<>();
            for (String s : form.getTrunks()) {
                RelASR asr=new RelASR();
                asr.setTrunkId(Integer.parseInt(s.split("-")[0].trim()));
                asr.setTrunkName(s.split("-")[1].trim());
                for (HangupCause h : hangs) {
                    if(asr.getTrunkId()==h.getTrunk()){
                        asr.setTotal(asr.getTotal()+1);
                        String code=String.valueOf(h.getCode());
                        if(code.matches("^16$|^18$|^102$|^31$|^127$"))
                            asr.setAnswer(asr.getAnswer()+1);
                        else if(code.matches("^0$|^1$|^19$|^20$"))
                            asr.setNoAnswer(asr.getNoAnswer()+1);
                        else
                            asr.setFail(asr.getFail()+1);
                    }
                }
                asrs.add(asr);
            }
            return asrs;
        }finally{em.close();}
  
    }

}
