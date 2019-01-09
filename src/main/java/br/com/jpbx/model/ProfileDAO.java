/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import br.com.jpbx.asterisk.AsteriskActions;
import br.com.jpbx.linux.Writer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class ProfileDAO {

    private EntityManager em;
    public ProfileDAO() {
        em=new ConnectionFactory().getEm();
    }

    public List<Profile> getAllProfiles(){   
        try{
            String sql="select p from Profile as p";
            TypedQuery <Profile> query=em.createQuery(sql,Profile.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public List<Profile> getAllProfilesForms(){       
        try{
            String sql="select p from Profile as p";
            TypedQuery <Profile> query=em.createQuery(sql,Profile.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public List<Profile> getProfilesLimited(){
        try{
            String sql="select p from Profile as p where p.limited=1";
            TypedQuery <Profile> query=em.createQuery(sql,Profile.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public Profile getSingleProfile(int id){
        try{
            return em.find(Profile.class, id);
        }finally{em.close();}
    }
    public String persistNewProfile(Profile p){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Profile> profiles = null;
        try{
            tx.begin();
            em.persist(p);
            tx.commit();
            String sql="select p from Profile as p";
            TypedQuery <Profile> querySelect=em.createQuery(sql,Profile.class);
            profiles=querySelect.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
          em.close();
        }     
        if(ret.equals("ok"))
            ret=new Writer().writeProfile(profiles);
        if(ret.equals("ok"))
            ret=new AsteriskActions().reloadSip();
        return ret;
    }
    public String updateProfile(Profile p){
        EntityTransaction tx=em.getTransaction();
        String sql;
        String ret="ok";
        List<Profile> profiles = null;
        try{
            tx.begin();
            em.merge(p);
            tx.commit();
            sql="select p from Profile as p";
            TypedQuery <Profile> querySelect=em.createQuery(sql,Profile.class);
            profiles=querySelect.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
  
        }
        if(ret.equals("ok"))
            ret=new Writer().writeProfile(profiles);
        if(ret.equals("ok"))
            ret=new AsteriskActions().reloadSip();
        return ret;
    }
    public void writeProfile(){
        String ret=new Writer().writeProfile(getAllProfiles());
        if(ret.equals("ok"))
            new AsteriskActions().reloadSip();
    }
    public void updateProfileLimited(Profile p){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            em.merge(p);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            System.out.println("DEU RUIM ATUALIZAR PERFIL LIMITADO "+p.getId()+": "+ex.getMessage());
        }finally{
           em.close();
        }
    }
    public String deleteProfile(Profile p){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Profile> profiles = null;
        try{
            tx.begin();
            Profile end=em.find(Profile.class, p.getId());
            em.remove(end);
            tx.commit();
            String sql="select p from Profile as p";
            TypedQuery <Profile> querySelect=em.createQuery(sql,Profile.class);
            profiles=querySelect.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
  
        }
        if(ret.equals("ok"))
            ret=new Writer().writeProfile(profiles);
        if(ret.equals("ok"))
            ret=new AsteriskActions().reloadSip();
        return ret;
    }
}
