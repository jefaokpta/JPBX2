/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class UserTest {
    //@Test
    public void testRellCalls(){
        for(RelCall r:new RelCallDAO().getRelCalls(1)){
            Calendar day=Calendar.getInstance();
            day.setTime(r.getCalldate());
            System.out.println("relcalls "+r.getUniqueid()+" dia "+day.get(Calendar.DAY_OF_MONTH));
            System.out.println("history??? "+r.getCallHistory().size());
            for(RelCallHistory h:r.getCallHistory()){               
                System.out.println("history "+h.getUniqueid()+" dia "+h.getCalltime().getTime());
            }
        }
    }
    public void testOrderList(){
        List<Peer> peers=new PeerDAO().getAllPeers();
        for (Peer p : peers) {
            System.out.println(p.getId()+" - "+p.getName());
        }
        Collections.sort(peers);
        System.out.println("ORDENANDO...");
        for (Peer p : peers) {
            System.out.println(p.getId()+" - "+p.getName());
        }
    }
    //@Test
    public void testMemberInList(){
        Queue calgrp=new QueueDAO().getSingleQueue(12);
        System.out.println(calgrp.getQueueMembers().size());
        List<QueueMember> aux=new ArrayList<>(calgrp.queueMembers);
        //List<QueueMember> aux=calgrp.queueMembers;
        for (QueueMember qm : aux) {
            if(qm.getPeerId()==33)
                calgrp.queueMembers.remove(qm);
        }
        System.out.println(calgrp.getQueueMembers().size());
        System.out.println(aux.size());
    }
    
}
