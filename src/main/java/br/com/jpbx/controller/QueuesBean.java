/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.DialPlanAction;
import br.com.jpbx.model.DialPlanRule;
import br.com.jpbx.model.DialPlanRuleDAO;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueDAO;
import br.com.jpbx.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "queuesBean")
@ViewScoped
public class QueuesBean implements Serializable{

    private Queue queue;
    private List<Queue> queues;
    
    public QueuesBean() {
        User userSession=(User) javax.faces.context.FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user");
        if(!userSession.getNivel().equals("Administrador")){ //CARREGA APENAS A PROPRIA EMPRESA
            queues=new ArrayList<>();
            for(Queue q:new QueueDAO().getAllQueues()){
                if(q.getCompany()==userSession.getCompany())
                    queues.add(q);
            }
        }   
        else
            queues=new QueueDAO().getAllQueues();
    }
    public String editQueue(Queue q){
        FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put("editQueue", q);
        return "/restricted/editQueue";
    }
    public String queueMembers(Queue q){
        FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put("queueMembers", q);
        return "/restricted/queueMembers";
    }
    public void alertDelete(Queue q){
        queue=q;
        RequestContext.getCurrentInstance()
            .execute("PF('alertQueue').show()"); 
    }
    public String deleteQueue(){
        for (DialPlanRule dr : new DialPlanRuleDAO().getAllDialplans(queue.getCompany())) {
            for (DialPlanAction da : dr.getActions()) {
                if(da.getAct().equals("queue")&&da.getArg1().equals(String.valueOf(queue.getId()))){
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Esta Fila não pode ser apagado pois é usado na regra: "
                                        +dr.getId()+ " - "+dr.getName()+", Empresa "+dr.getCompany(), ""));
                    return "/restricted/queues";
                }
            }
        }
        String ret=new QueueDAO().deleteQueue(queue);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/queues";
    }

    public List<Queue> getQueues() {
        return queues;
    }

    public Queue getQueue() {
        return queue;
    }
    
}
