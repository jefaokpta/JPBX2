/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.model;

import br.com.jpbx.asterisk.Asterisk;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author jefaokpta
 */
public class RouteDAO {
    
    private EntityManager em;
    private Asterisk ast;

    public RouteDAO() {
        em=new ConnectionFactory().getEm();
        ast = new Asterisk();
    }
    public List<Route> getAllRoutes(){  
        try{          
            String sql="select r from Route as r";
            TypedQuery <Route> query=em.createQuery(sql,Route.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public List<Route> getAllRoutes(int company){  
        try{          
            String sql="select r from Route as r where r.company="+company;
            TypedQuery <Route> query=em.createQuery(sql,Route.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public List<Route> getAllRoutesLimited(){  
        try{          
            String sql="select r from Route as r where r.limitBol = 1";
            TypedQuery <Route> query=em.createQuery(sql,Route.class);           
            return query.getResultList();
        }finally{em.close();}
    }
    public void updateRouteCompromise(Route route){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            em.merge(route);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            System.out.println("DEU RUIM ATUALIZAR COMPROMISSO ROTA "+route.getId()+": "+ex.getMessage());
        }finally{
           em.close();
        }  
    }
    public String updateRouteCompromise(int id,int bill){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            Route up=em.find(Route.class, id);
            up.setCurrentMin(up.getCurrentMin()+bill);
            em.merge(up);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "NAO ATUALIZOU O VALOR: "+ex.getMessage();
        }finally{
           em.close();
        }       
        return "ok";
    }
    public Route getSingleRoute(int id){
        try{
            return em.find(Route.class, id);
        }finally{em.close();}
    }
    public String persistNewRoute(Route r){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            em.persist(r);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
        }       
        addRouteAstDB(r);
        return ret;
    }
    public String updateRoute(Route r){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            em.merge(r);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
        }
        addRouteAstDB(r);
        return ret;
    }
    public String deleteRoute(Route r){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            Route end=em.find(Route.class, r.getId());
            em.remove(end);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
        }    
        deleteRouteAstDB(r);
        return ret;
    }
    
    private void addRouteAstDB(Route route){
        Trunk tr1, tr2, tr3;
        String moh = new CompanyDAO().getSingleCompany(route.getCompany()).getMoh();
        for (RouteTrunk r : route.getRoutes()) {
            tr1 = new Trunk();
            if(r.getTrunkId1()>0)
                tr1=new TrunkDAO().getSingleTrunk(r.getTrunkId1());
            tr2 = new Trunk();
            if(r.getTrunkId2()>0)
                tr2=new TrunkDAO().getSingleTrunk(r.getTrunkId2());
            tr3 = new Trunk();
            if(r.getTrunkId3()>0)
                tr3=new TrunkDAO().getSingleTrunk(r.getTrunkId3());
                        
            ast.astDBAdd("Route", 
                    String.valueOf(route.getId())+String.valueOf(r.getId()), 
                            tr1.getCanal()+","+tr1.getId()+","+tr1.getTechPrefix()+","+
                            tr2.getCanal()+","+tr2.getId()+","+tr2.getTechPrefix()+","+
                            tr3.getCanal()+","+tr3.getId()+","+tr3.getTechPrefix()+","+
                                    route.getLimitBol()+","+(route.getLimitControl()-route.getCurrentMin())+","+
                                    route.getChanLimit()+","+route.getFlags()+","+route.getTimeout()+","+
                                    moh
                        );
        }     
    }
    
    private void deleteRouteAstDB(Route route){
        for (RouteTrunk r : route.getRoutes()) {
            ast.astDBDEL("Route", String.valueOf(route.getId())+String.valueOf(r.getId()));
        }
    }
}
