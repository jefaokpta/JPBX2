/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import br.com.jpbx.util.MD5Factory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class UserDAO {

    private EntityManager em;
    
    public UserDAO() {
        em=new ConnectionFactory().getEm();
    }
    public User getSingleUser(int id){
        try{
            return em.find(User.class, id);
        }finally{em.close();}
    }
    public List<User> getAllUsers(){  
        try{
            String sql="select u from User as u";
            TypedQuery <User> query=em.createQuery(sql,User.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String persistNewUser(User u){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            if(u.getSite()==null)
                u.setSite("");
            u.setPassword(new MD5Factory().md5(u.getName()+":"+u.getPassword()));
            em.persist(u);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
 
        }
        
        return ret;
    }
    public String updateUser(User u,boolean edit){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            if(edit)
                u.setPassword(new MD5Factory().md5(u.getName()+":"+u.getPassword()));
            em.merge(u);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
  
        }
        return ret;
    }
    public String updateUserWebLinks(User u){
        EntityTransaction tx=em.getTransaction();
        String sql="delete from pages_users where idusers="+u.getId();
        String ret="ok";
        try{
            tx.begin();
            Query query=em.createNativeQuery(sql);
            query.executeUpdate();
            em.flush();
            em.clear();
            em.merge(u);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
          em.close();
  
        }
        return ret;
    }
    public String deleteUser(User u){
        EntityTransaction tx=em.getTransaction();
        //String sql="delete from pages_users where id_user="+u.getId();
        String ret="ok";
        try{
            tx.begin();
            //Query query=em.createNativeQuery(sql);
            //query.executeUpdate();
            //em.flush();
           // em.clear();
            User end=em.find(User.class, u.getId());
            em.remove(end);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
          em.close();
  
        }
        
        return ret;
    }
    public User doLogin(String name,String pass){
        String sql="select u from User u where name='"+name+"'";
        TypedQuery <User> query=em.createQuery(sql,User.class);
        List<User> users=query.getResultList();
        em.close();
 
        if(users.isEmpty()){
            //System.out.println("::::: USUARIO "+name+" NAO EXISTE :::::");
            return null;
            //return false;
        }
        for(User u:users)
            if(u.getPassword().equals(pass))
                return u;
        //System.out.println("::::: SENHA NAO CONFERE :::::");
        return null;
    }
}
