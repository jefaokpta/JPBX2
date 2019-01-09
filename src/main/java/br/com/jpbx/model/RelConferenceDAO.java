/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import br.com.jpbx.util.RelConferenceFilter;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.primefaces.model.SortOrder;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class RelConferenceDAO {
    
    private EntityManager em;

    public RelConferenceDAO() {
        em=new ConnectionFactory().getEm();
    } 
    
    public List<RelConference> getRelConferences(RelConferenceFilter form,int firstReg, int qtdeReg,String orderField,SortOrder sortOrder,Map<String, Object> filters){       
        try{
            String order=""; // ORDENACAO
            if(orderField!=null){
                order=" ORDER BY c."+orderField+" DESC";
                if(SortOrder.ASCENDING.equals(sortOrder))
                    order=" ORDER BY c."+orderField+" ASC";
            }          
            String filter=""; // AGORA FILTROS DA TABELA
            for (Map.Entry<String,Object> f :filters.entrySet())
                filter+=" AND c."+f.getKey()+" LIKE '%"+f.getValue()+"%'";
            String formSql="";
            if(!form.getRooms().isEmpty())
                formSql=" AND c.room IN ("+form.getRooms()+")";         
            String sql="SELECT c FROM RelConference as c WHERE c.datetime BETWEEN :startDate AND :endDate "+formSql+filter+order;
            TypedQuery <RelConference> query=em.createQuery(sql,RelConference.class);
            query.setParameter("startDate", form.getStartDate(),TemporalType.TIMESTAMP);
            query.setParameter("endDate", form.getEndDate(),TemporalType.TIMESTAMP);
            query.setFirstResult(firstReg);
            query.setMaxResults(qtdeReg);
            
            return query.getResultList();
        }finally{em.close();}
  
    }
    public int getRelConfCount(Map<String,Object> filters,RelConferenceFilter form){   
        try{          
            String filter=""; // AGORA FILTROS DA TABELA
            for (Map.Entry<String,Object> f :filters.entrySet()) {
                filter+=" AND c."+f.getKey()+" LIKE '%"+f.getValue()+"%'";
            }        
            String formSql="";
            if(!form.getRooms().isEmpty())
                formSql=" AND c.room IN ("+form.getRooms()+")";    
            String sql="SELECT COUNT(c.id) FROM  RelConference as c WHERE c.datetime  BETWEEN :startDate AND :endDate AND c.company="+form.getCompany()+formSql+filter;
            Query query=em.createQuery(sql);
            query.setParameter("startDate", form.getStartDate(),TemporalType.TIMESTAMP);
            query.setParameter("endDate", form.getEndDate(),TemporalType.TIMESTAMP);
            return Integer.parseInt(query.getSingleResult().toString());
        }finally{em.close();}
    }
    public void deleteConference(RelConference c){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            RelConference end=em.find(RelConference.class, c.getId());
            em.remove(end);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            System.out.println(":::::::::::::::::: DAO: "+ex.getMessage());
        } finally{
            em.close();  
        }
    }
}
