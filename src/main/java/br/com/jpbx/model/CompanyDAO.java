/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import br.com.jpbx.asterisk.AsteriskActions;
import br.com.jpbx.linux.WriteExtensions;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class CompanyDAO {
    private EntityManager em;
    public CompanyDAO(){
        em=new ConnectionFactory().getEm();
    }
    public Company getSingleCompany(int id){
        try{
            return em.find(Company.class, id);
        }finally{em.close();}
    } 
    public List<Company> getAllCompanys(){
        try{
            String sql="select c from Company as c";
            TypedQuery <Company> query=em.createQuery(sql,Company.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String insertCompany(Company c){
        EntityTransaction tx=em.getTransaction();
        List<Company> companys = null;
        try{
            tx.begin();
            em.persist(c);
            em.persist(new CenterCost("Entradas", 0, 60, 6, 0, 1.00f, 0, c.getId()));
            em.persist(new CenterCost("FIXO LOCAL", 0, 60, 6, 0, 2.00f, 0, c.getId()));
            em.persist(new CenterCost("FIXO DDD", 0, 60, 6, 0, 3.00f, 0, c.getId()));
            em.persist(new CenterCost("VC1", 0, 60, 6, 0, 4.00f, 0, c.getId()));
            em.persist(new CenterCost("VC2", 0, 60, 6, 0, 5.00f, 0, c.getId()));
            em.persist(new CenterCost("VC3", 0, 60, 6, 0, 6.00f, 0, c.getId()));
            em.persist(new CenterCost("DDI", 0, 60, 6, 0, 7.00f, 0, c.getId()));
            em.persist(new CenterCost("Funcionalidades", 0, 60, 6, 0, 8.00f, 0, c.getId()));
            em.persist(new CenterCost("Internas", 0, 60, 6, 0, 9.00f, 0, c.getId()));
            tx.commit();
            String sql="select c from Company as c";
            TypedQuery <Company> query=em.createQuery(sql,Company.class);
            companys=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO: "+ex.getMessage();
        }finally{
            em.close();        
        }   
        String res=new WriteExtensions().writeRules(companys);
        if(!res.equals("ok"))
            return "ESCRITA DIALPLAN: "+res;
        res=new AsteriskActions().dialplanReload();
        return res;
    }
    public String updateCompany(Company c){
        String res="ok";
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            em.merge(c);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            res="DAO: "+ex.getMessage();
        }finally{
           em.close();
         
        }
        return res;
    }
    public String updateCompanyMoh(String moh,int id){
        String res="Salvo com sucesso.";
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            Company end=em.find(Company.class, id);
            end.setMoh(moh);
            em.merge(end);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            res="DAO: "+ex.getMessage();
        }finally{
            em.close();
         
        }
        return res;
    }
    public String deleteCompany(Company c){
        EntityTransaction tx=em.getTransaction();
        List<Company> companys = null;
        
        try{
            tx.begin();
            Company companyEnd=em.find(Company.class, c.getId());           
            em.remove(companyEnd);
            tx.commit();
            String sql="select c from Company as c";
            TypedQuery <Company> query=em.createQuery(sql,Company.class);
            companys=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO: "+ex.getMessage();
        }finally{
            em.close();     
        }
        String res=new WriteExtensions().writeRules(companys);
        if(!res.equals("ok"))
            return "ESCRITA DIALPLAN: "+res;
        res=new AsteriskActions().dialplanReload();
        for (Trunk t : new TrunkDAO().getAllTrunks()) {
            if(t.getCompany()==c.getId())
                new TrunkDAO().deleteTrunk(t.getId());
        }
        for (Peer p : new PeerDAO().getAllPeers()) {
            if(p.getCompany()==c.getId())
                new PeerDAO().deletePeer(p);
        }
        for (Route r : new RouteDAO().getAllRoutes()) {
            if(r.getCompany()==c.getId())
                new RouteDAO().deleteRoute(r);
        }
        for (Ddr d : new DdrDAO().getAllDdrs()) {
            if(d.getCompany()==c.getId())
                new DdrDAO().deleteDdr(d);
        }
        for (PickupGrp p : new PickupGrpDAO().getAllGrps()) {
            if(p.getCompany()==c.getId())
                new PickupGrpDAO().deleteGrp(p);
        }
        for (Queue q : new QueueDAO().getAllCallGrps()) {
            if(q.getCompany()==c.getId())
                new QueueDAO().deleteQueue(q);
        }
        for (Ura u : new UraDAO().getAllUras()) {
            if(u.getCompany()==c.getId())
                new UraDAO().deleteUra(u);
        }
        for (AcCode ac : new AcCodeDAO().getAllAcCodes()) {
            if(ac.getCompany()==c.getId())
                new AcCodeDAO().deleteAcCode(ac);
        }
        for (User us : new UserDAO().getAllUsers()) {
            if(us.getCompany()==c.getId())
                new UserDAO().deleteUser(us);
        }
        return res;
    }
}
