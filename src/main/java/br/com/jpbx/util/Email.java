/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import br.com.jpbx.model.EmailDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class Email {
    
    private HtmlEmail email;
    private br.com.jpbx.model.Email emailConfig;

    public Email() {
        email=new HtmlEmail();
        emailConfig=new EmailDAO().getEmailConfig();
        // o servidor SMTP para envio do e-mail 
        email.setHostName(emailConfig.getSmtp());
            
        email.setAuthentication(emailConfig.getUser(), emailConfig.getPassword());
        email.setSmtpPort(emailConfig.getPort());
        email.setStartTLSEnabled(emailConfig.getTls()>0);
        email.setSSLOnConnect(emailConfig.getSsl()>0);
    }
    
    public String infoEmail(String[] emails){
        try {  
            // configura a mensagem para o formato HTML
            email.setHtmlMsg("<html>JPBX Informa</html>");
            // configure uma mensagem alternativa caso o servidor não suporte HTML  
            email.setTextMsg("Seu servidor de e-mail não suporta mensagens em HTML");           
            // Destinos
            email.addTo(emails);
            // remetente
            email.setFrom(emailConfig.getEmail(), "JPBX"); 
            // assunto do e-mail 
            email.setSubject("JPBX Teste do Servidor de Email");
            //conteudo do e-mail  
            email.setMsg(""
+ "<html>\n" +
"    <body style=\"background-color: whitesmoke; color: rgb(0, 0, 0);background-image: url(http://jpbx.com.br/img/backGroundEmails.jpg)\">\n" +
"        <div style=\"width: 70%;margin: 0 auto;text-align: center\">\n" +
"            <h2>Servidor de Email configurado com sucesso!</h2>\n" +
"            <p style=\"color: lightsteelblue\">Mensagem automatica</p>\n" +
"            <p style=\"color: lightsteelblue\">Developed by <a style=\"color: lightblue\" href=\"http://www.linkedin.com/pub/jefferson-alves-reis/61/73/0\">Jefaokpta</a> </p>\n" +
"        </div>\n" +
"    </body>\n" +
"</html>");
                     
            email.send(); 
            
        } catch (EmailException ex) {
            return "OPS EMAIL: "+ex.getMessage();
        }
        return "ok";
    }
    public void emailFax(String[] emails,String fax){
        try {  
            // configura a mensagem para o formato HTML
            email.setHtmlMsg("<html>JPBX Informa</html>");
            // configure uma mensagem alternativa caso o servidor não suporte HTML  
            email.setTextMsg("Seu servidor de e-mail não suporta mensagens em HTML");           
            // Destinos
            email.addTo(emails);
            // remetente
            email.setFrom(emailConfig.getEmail(), "JPBX"); 
            // assunto do e-mail 
            email.setSubject("JPBX - Fax para você");
            //conteudo do e-mail  
            email.setMsg(""
+ "<html>\n" +
"    <body style=\"background-color: whitesmoke; color: rgb(0, 0, 0);background-image: url(http://jpbx.com.br/img/backGroundEmails.jpg)\">\n" +
"        <div style=\"width: 70%;margin: 0 auto;text-align: center\">\n" +
"            <h2>Segue Fax recebido</h2>\n" +
"            <p style=\"color: lightsteelblue\">Mensagem automatica</p>\n" +
"            <p style=\"color: lightsteelblue\">Developed by <a style=\"color: lightblue\" href=\"http://www.linkedin.com/pub/jefferson-alves-reis/61/73/0\">Jefaokpta</a> </p>\n" +
"        </div>\n" +
"    </body>\n" +
"</html>");
                     
            email.embed(new File("/etc/asterisk/jpbx/pdfs/"+fax+".pdf"));
            
            email.send(); 
            
        } catch (EmailException ex) {
            System.out.println("OPS EMAIL FAX: "+ex.getMessage());
        }
    }
    public void emailVoicemail(String ctx,int peer,int msgNum,String[] emails, String name){
        String msg="msg00";
        if(msgNum<10)
            msg+="0"+(msgNum-1);
        else 
            msg+=(msgNum-1);
        try {  
            // configura a mensagem para o formato HTML
            email.setHtmlMsg("<html>JPBX Informa</html>");
            // configure uma mensagem alternativa caso o servidor não suporte HTML  
            email.setTextMsg("Seu servidor de e-mail não suporta mensagens em HTML");           
            // Destinos
            email.addTo(emails);
            // remetente
            email.setFrom(emailConfig.getEmail(), "JPBX"); 
            // assunto do e-mail 
            email.setSubject("JPBX - Correio de Voz para voce");
            String msgPath="/var/spool/asterisk/voicemail/"+ctx+"/"+peer+"/INBOX/"+msg;
            String src = null;
            BufferedReader file=new BufferedReader(new FileReader(new File(msgPath+".txt")));
            for (String line = file.readLine();  line!=null; line=file.readLine()) {
                if(line.contains("callerid")){
                    src=line.split("=")[1];
                    break;
                }
            }
            //conteudo do e-mail  
            email.setMsg(""
+ "<html>\n" +
"    <body style=\"background-color: whitesmoke; color: rgb(0, 0, 0);background-image: url(http://jpbx.com.br/img/backGroundEmails.jpg)\">\n" +
"        <div style=\"width: 70%;margin: 0 auto;text-align: center\">\n" +
"            <h1>Ola "+name+"</h1>\n" +
"            <h2>"+src+" deixou mensagem na sua caixa.</h2>\n" +
"            <h2>Atualmente existem "+msgNum+" mensagens na sua caixa.</h2>\n"+
"            <p>Para acessar todas as suas mensagens utilize o comando *30 do seu ramal.</p>\n" +
"            <p style=\"color: lightsteelblue\">Mensagem automatica</p>\n" +
"            <p style=\"color: lightsteelblue\">Developed by <a style=\"color: lightblue\" href=\"http://www.linkedin.com/pub/jefferson-alves-reis/61/73/0\">Jefaokpta</a> </p>\n" +
"        </div>\n" +
"    </body>\n" +
"</html>");
                     
            email.embed(new File(msgPath+".wav"));
            
            email.send(); 
            
        } catch (EmailException ex) {
            System.out.println("OPS EMAIL VOICEMAIL: "+ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println("OPS EMAIL VOICEMAIL: "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("OPS EMAIL VOICEMAIL: "+ex.getMessage());
        }
    }
    public void emailRecoverAstFail(String[] emails,String serverName){
        try {  
            // configura a mensagem para o formato HTML
            email.setHtmlMsg("<html>JPBX Informa</html>");
            // configure uma mensagem alternativa caso o servidor não suporte HTML  
            email.setTextMsg("Seu servidor de e-mail não suporta mensagens em HTML");           
            // Destinos
            email.addTo(emails);
            // remetente
            email.setFrom(emailConfig.getEmail(), "JPBX"); 
            // assunto do e-mail 
            email.setSubject("JPBX - Servidor "+serverName+" reiniciou");
            //conteudo do e-mail  
            email.setMsg(""
+ "<html>\n" +
"    <body style=\"background-color: whitesmoke; color: rgb(0, 0, 0);background-image: url(http://jpbx.com.br/img/backGroundEmails.jpg)\">\n" +
"        <div style=\"width: 70%;margin: 0 auto;text-align: center\">\n" +
"            <h2>Servidor "+serverName+" reiniciou pois algo deu errado.</h2>\n" +
                    "<p>Ops, algo parou o servidor "+serverName+", as atividades voltaram a operar.</p>\n"
                    + "<p>Segue logs para esclarecimentos.</p>\n"+
"            <p style=\"color: lightsteelblue\">Mensagem automatica</p>\n" +
"            <p style=\"color: lightsteelblue\">Developed by <a style=\"color: lightblue\" href=\"http://www.linkedin.com/pub/jefferson-alves-reis/61/73/0\">Jefaokpta</a> </p>\n" +
"        </div>\n" +
"    </body>\n" +
"</html>");
                     
            email.embed(new File("/tmp/failLog.tar"));
            
            email.send(); 
            
        } catch (EmailException ex) {
            System.out.println("OPS EMAIL RECOVER AST FAIL: "+ex.getMessage());
        }
    }
    /*
    cria o anexo 1.
                EmailAttachment anexo1 = new EmailAttachment();
                anexo1.setPath("/tmp/bashShell.pdf"); //caminho do arquivo (RAIZ_PROJETO/teste/teste.txt)
                anexo1.setDisposition(EmailAttachment.ATTACHMENT);
                anexo1.setDescription("Exemplo de arquivo anexo");
                anexo1.setName("bashShell.pdf");
                */
                //insere audio integrado no email
                //URL url = new URL("file:///"+audio);
                //String cid = email.embed(url, "Audio");
    
}
