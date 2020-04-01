/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import br.com.jpbx.asterisk.Asterisk;
import br.com.jpbx.asterisk.AsteriskActions;
import br.com.jpbx.linux.Writer;
import br.com.jpbx.util.MD5Factory;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class PeerDAO {

    EntityManager em;

    public PeerDAO() {
        em=new ConnectionFactory().getEm();
    }
    public String verifyTrunkRec(int peer){
        try{
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            for (Peer p : query.getResultList()) {
                if(p.getName()==peer){
                    if(p.getRecord()>0)
                        return "1";
                    return "0";
                }
            }
        }finally{em.close();}
        return "1";
    }
    public String verifyPeersRec(String src,String dstChannel){
        String sql="select p from Peer as p";
        TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
        List<Peer> ret=query.getResultList();
        em.close();
        int dstRec=0;
        int srcRec=0;
        boolean peer=false;
        for (Peer p : ret) {
            if(String.valueOf(p.getName()).equals(src)){
                srcRec=p.getRecord();
                peer=true;
                break;
            }
        }
        for (Peer p : ret) {
            if(p.getCanal().equals(dstChannel)){
                dstRec=p.getRecord();
                break;
            }
        }
        if(peer){
            if(srcRec==1&&dstRec==1)
                return "1";
            return "0";
        }
        return dstRec==1?"1":"0";
    }
    public Peer getSinglePeer(int id){
        try{
            return em.find(Peer.class, id);
        }finally{em.close();}
    }
    public List<Peer> getAllPeers(){    
        try{
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            List ret=query.getResultList();
            Collections.sort(ret);
            return ret;
        }finally{em.close();}
    }
    public Peer getPeerByName(int peer){    
        try{
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            for (Peer p : query.getResultList()) {
                if(p.getName()==peer)
                    return p;
            }
        }finally{em.close();}
        return null;
    }
    public Peer getPeerByChannel(String peer){    
        try{
            String sql="select p from Peer as p WHERE p.canal='"+peer+"'";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            return query.getSingleResult();
        }finally{em.close();}
    }
    public List<Peer> getAllPeersOfCompany(int id){    
        try{
            String sql="select p from Peer as p where p.company="+id;
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            List ret=query.getResultList();
            Collections.sort(ret);
            return ret;
        }finally{em.close();}
    }
    public List<Peer> getAllPeersWeb(){
        try{
            String sql="select p from Peer as p where p.peerType='WEB'";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String persistNewPeer(Peer p){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Peer> peers = null;
        try{
            tx.begin();
            p.setSecret(new MD5Factory().md5(p.getName()+":asterisk:"+p.getSecret()));
            em.persist(p);
            tx.commit();
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            peers=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();
 
        }
        if(ret.equals("ok"))
            ret=new Writer().writePeersSIP(peers);
        if(ret.equals("ok"))
            ret=new Writer().writeVoicemail(peers);
        if(ret.equals("ok"))
            new AsteriskActions().reloadSipVoicemailDialplan();
      //  try {
            Asterisk ast=new Asterisk();
            for (Peer peer : peers) {
                ast.astDBAdd("PeerId", String.valueOf(peer.getName()), String.valueOf(peer.getId()));
                peerData(peer);
            }
//        } catch (IOException ex) {
//            ret="FALHA AST: "+ex.getMessage();
//        }
        return ret;
    }
    public String persistNewPeersLot(List<Peer> peersLot) {
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Peer> peers=null;
        try{
            tx.begin();
            for (Peer p : peersLot) {
                p.setSecret(new MD5Factory().md5(p.getName()+":asterisk:"+p.getSecret()));
                em.persist(p);
            }           
            tx.commit();
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            peers=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
 
        }
        if(ret.equals("ok"))
            ret=new Writer().writePeersSIP(peers);
        if(ret.equals("ok"))
            ret=new Writer().writeVoicemail(peers);
        if(ret.equals("ok"))
            ret=new AsteriskActions().reloadSipVoicemailDialplan();
       // try {
            Asterisk ast=new Asterisk();
            for (Peer peer : peers) {
                ast.astDBAdd("PeerId", String.valueOf(peer.getName()), String.valueOf(peer.getId()));
                peerData(peer);
            }
//        } catch (IOException ex) {
//            ret="FALHA AST: "+ex.getMessage();
//        }
        return ret;
    }
    public String persistNewPeerWEB(Peer p) {
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Peer> peers=null;
        try{
            tx.begin();
            p.setSecret(new MD5Factory().md5(p.getName()+":asterisk:"+p.getSecret()));
            p.setUsername(new MD5Factory().md5(p.getName()+":"+p.getUsername()));
            em.persist(p);
            tx.commit();
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            peers=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
 
        }
        if(ret.equals("ok"))
            ret=new Writer().writePeersWEB(peers);
        if(ret.equals("ok"))
            ret=new Writer().writeVoicemail(peers);
        if(ret.equals("ok"))
            ret=new AsteriskActions().reloadSipVoicemailDialplan();
     //   try {
            Asterisk ast=new Asterisk();
            for (Peer peer : peers) {
                ast.astDBAdd("PeerId", String.valueOf(peer.getName()), String.valueOf(peer.getId()));
                peerData(peer);
            }
//        } catch (IOException ex) {
//            ret="FALHA AST: "+ex.getMessage();
//        }
        return ret;
    }
    public String persistNewPeersWEBLot(List<Peer> peersLot) {
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Peer> peers=null;
        try{
            tx.begin();
            for (Peer p : peersLot) {
                p.setSecret(new MD5Factory().md5(p.getName()+":asterisk:"+p.getSecret()));
                p.setUsername(new MD5Factory().md5(p.getName()+":"+p.getUsername()));
                em.persist(p);
            }
            tx.commit();
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            peers=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();
 
        }
        if(ret.equals("ok"))
            ret=new Writer().writePeersWEB(peers);
        if(ret.equals("ok"))
            ret=new Writer().writeVoicemail(peers);
        if(ret.equals("ok"))
            ret=new AsteriskActions().reloadSipVoicemailDialplan();
     //   try {
            Asterisk ast=new Asterisk();
            for (Peer peer : peers) {
                ast.astDBAdd("PeerId", String.valueOf(peer.getName()), String.valueOf(peer.getId()));
                peerData(peer);
            }
//        } catch (IOException ex) {
//            ret="FALHA AST: "+ex.getMessage();
//        }
        return ret;
    }
    public String persistNewPeerVIRTUAL(Peer p) {
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Peer> peers = null;
        try{
            tx.begin();
            em.persist(p);
            tx.commit();
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            peers=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();
 
        }
     //   try {
            Asterisk ast=new Asterisk();
            for (Peer peer : peers) {
                ast.astDBAdd("PeerId", String.valueOf(peer.getName()), String.valueOf(peer.getId()));
                peerData(peer);
            }
//        } catch (IOException ex) {
//            ret="FALHA AST: "+ex.getMessage();
//        }
        if(ret.equals("ok"))
            ret=new Writer().writeVoicemail(peers);
        if(ret.equals("ok"))
            ret=new AsteriskActions().reloadVoicemail();
        return ret;
    }

    public String deletePeer(Peer p){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Peer> peers=null;
        try{
            tx.begin();
            Peer end=em.find(Peer.class, p.getId());
            em.remove(end);
            tx.commit();
            if(p.getPeerType().equals("SIP")||p.getPeerType().equals("WEB")){
                String sql="select p from Peer as p";
                TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
                peers=query.getResultList();
            }
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();
  
        }
        if(ret.equals("ok"))
            switch(p.getPeerType()){
                case "SIP":
                    ret=new Writer().writePeersSIP(peers);
                    if(ret.equals("ok"))
                        ret=new Writer().writeVoicemail(peers);
                    if(ret.equals("ok"))
                        ret=new AsteriskActions().reloadSipVoicemailDialplan();
                    break;
                case "WEB":
                    ret=new Writer().writePeersWEB(peers);
                    if(ret.equals("ok"))
                        ret=new Writer().writeVoicemail(peers);
                    if(ret.equals("ok"))
                        ret=new AsteriskActions().reloadSipVoicemailDialplan();  
                    break;
            }
      //  try {
            new Asterisk().astDBDEL("PeerId", String.valueOf(p.getName()));
            new Asterisk().astDBDEL("PeerData", String.valueOf(p.getId()));
//        } catch (IOException ex) {
//            ret="FALHA AST: "+ex.getMessage();
//        }
        return ret;
    }
    public String updatePeer(Peer p,boolean edit){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Peer> peers = null;
        try{
            tx.begin();           
            if(edit)
                p.setSecret(new MD5Factory().md5(p.getName()+":asterisk:"+p.getSecret()));           
            em.merge(p);
            tx.commit();
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            peers=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
        }
        peerData(p);
        if(ret.equals("ok"))
            ret=new Writer().writePeersSIP(peers);
        if(ret.equals("ok"))
            ret=new Writer().writeVoicemail(peers);
        if(ret.equals("ok"))
            ret=new AsteriskActions().reloadSipVoicemailDialplan();
        return ret;
    }

    public String updatePeerWEB(Peer p,boolean editpass) {
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Peer> peers=null;
        try{
            tx.begin();
            if(editpass){
                p.setUsername(new MD5Factory().md5(p.getName()+":"+p.getUsername()));
                p.setSecret(new MD5Factory().md5(p.getName()+":asterisk:"+p.getSecret()));
            }
            em.merge(p);
            tx.commit();
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            peers=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();
  
        }
        peerData(p);
        if(ret.equals("ok"))
            ret=new Writer().writePeersWEB(peers);
        if(ret.equals("ok"))
            ret=new Writer().writeVoicemail(peers);
        if(ret.equals("ok"))
            ret=new AsteriskActions().reloadSipVoicemailDialplan();
        return ret;
    }
    public String updatePeerWEBrtcpMux(Peer p) {
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Peer> peers=null;
        try{
            tx.begin();
            em.merge(p);
            tx.commit();
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            peers=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();
  
        }
        peerData(p);
        if(ret.equals("ok"))
            ret=new Writer().writePeersWEB(peers);
        if(ret.equals("ok"))
            ret=new AsteriskActions().reloadSip();
        return ret;
    }

    public String updatePeerVIRTUAL(Peer p) {
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Peer> peers=null;
        try{
            tx.begin();
            Peer end=em.find(Peer.class, p.getId());
            p.setDnd(end.getDnd());
            p.setFwd(end.getFwd());
            p.setLock(end.getLock());
            em.merge(p);
            tx.commit();
            String sql="select p from Peer as p";
            TypedQuery <Peer> query=em.createQuery(sql,Peer.class);
            peers=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();
  
        }
        peerData(p);
        if(ret.equals("ok"))
            ret=new Writer().writeVoicemail(peers);
        if(ret.equals("ok"))
            ret=new AsteriskActions().reloadVoicemail();
        return ret;
    }

    public void updatePeerVoicemailPass(Peer p){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();                  
            em.merge(p);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            System.out.println("DAO: "+ex.getMessage()); 
        }finally{
           em.close();
        }
    }

    private void peerData(Peer p){
        new Asterisk().astDBAdd("PeerData", String.valueOf(p.getId()), 
                p.getCompany()+","+p.getContext()+","+p.getLock()+","+p.getAuthorization()+","
                    +(p.getAuthorization()>0?new ProfileDAO().getSingleProfile(p.getAuthorization()).getLimited():"0"));
    }
    

    

    

    
}
