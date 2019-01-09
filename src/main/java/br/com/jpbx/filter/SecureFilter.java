/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.filter;

import br.com.jpbx.model.User;
import br.com.jpbx.model.WebPage;
import br.com.jpbx.util.DefineUserPriority;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@WebFilter(urlPatterns = "/jpbx/restricted/*")
public class SecureFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse) response;
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        
        //System.out.println("::::: VERIFICANDO SESSAO request "+req.getRequestURI()+" :::::");
        try{
            User user=(User) session.getAttribute("user");
            boolean noMapping=true;
            for(WebPage wp:(List<WebPage>)session.getAttribute("pages"))
                if(req.getRequestURI().contains(wp.getOutcome())){
                    if(new DefineUserPriority().userPriority(user.getNivel())
                            >= wp.getPriority()){
//                        System.out.println("::::: EXECUTANDO USUARIO "+user.getName()+" "
//                            + "PARA "+req.getRequestURI()+" IP "+req.getLocalAddr()+
//                                " URL "+req.getRequestURL().toString().split("/")[2]+" :::::");
                        noMapping=false;
                    }
                    else{
                        System.out.println("::::: NAO AUTORIZADO :::::");
                        res.sendRedirect(req.getContextPath()+"/jpbx/index.xhtml");
                        return;                 
                    }
                    break;
                }
            if(noMapping){
                System.out.println("::::: PAGINA NAO MAPEADA "+req.getRequestURI()+" :::::");
                res.sendRedirect(req.getContextPath()+"/jpbx/index.xhtml");
                return;
            }
        }catch(NullPointerException ex){
            System.out.println("::::: SEM SESSAO :::::");
            res.sendRedirect(req.getContextPath()+"/jpbx/index.xhtml");
            return;
        }
        chain.doFilter(request, response);   
    }

    @Override
    public void destroy() {
        //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
