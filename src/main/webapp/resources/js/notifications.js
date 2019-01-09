function getCall(){
    $.ajax({
        url: '/jpbx/CallToAgentServlet',
        dataType: 'json',
        data: {
            company:company,
            agent:agent,
            chan:chan
        },
        success: function(data){
            if(data.exists===1){
                tel=data.tel;
                addCall(tel,0);
                queue=data.queue;
                //txt=data.txt;
                var notification = new Notification("CHAMADA DE "+data.tel,{
                    body: agentName+", ligação para você! \n Número: "+data.tel+"\n Fila: "+data.queue,               
                    icon: "resources/img/phoneRing.png",
                    sticky: true
                });
                notification.onclick = function(event) {
                    //event.preventDefault(); // prevent the browser from focusing the Notification's tab
                    //window.open('http://www.mozilla.org', '_blank');
                    alert('Agente: '+agent+' '+agentName+' \n Chamada: '+data.tel+'\n Fila: '+data.queue);
                };
            }
        }
    });
}

function verifyCall(){
    getCall();
    setTimeout('verifyCall();',6000);
};
$(function(){
    notifyMe();
    if (Notification.permission === "granted")
        verifyCall();
});