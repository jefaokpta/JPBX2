function update(){
    var out='';
    $.ajax({
        url:'/jpbx/MTOServlet',
        dataType: 'json',
        data:{
            company:company
        },
        success: function(data){
            for(var i in data){
                out+='<div class="col-xs-1 spaceLeft">\n\
                                <div '+((data[i].enableClick===1)?'onclick="callPeer('+data[i].peer+');"':'')+' type="button" class="btn  btn-'+data[i].color+'" spaceTop '+data[i].disable+' >\n\
                                    '+data[i].name.substr(0,14) +' <br />\n\
                                    <img src="../resources/img/'+data[i].phoneImg+'.png" width="15px" />\n\
                                    '+data[i].peer +' </div> </div>' ;
            }
            $('#screen').html(out);
        }
    });
}
function callPeer(peer){
    if($('#peer').val()!==''){
        $.ajax({
            url:'/jpbx/CallFromMtoServlet',
            data:{
               src: $('#peer').val(),
               dst: peer
            },
            success: function(data){
                if(data==='Success'){
                    if (Notification.permission === "granted")
                        var notification = new Notification("LIGANDO PARA O RAMAL "+peer,{
                            body: 'Chamando o ramal '+peer+'.',               
                            icon: "/jpbx/resources/img/handsetRing.png"
                        });
                }
                else{
                    if (Notification.permission === "granted")
                        var notification = new Notification("Ops! Deu algo errado.",{
                            body: 'Aparentemente você não atendeu seu ramal ou não está registrado. Precisa atender para completar a chamada.',               
                            icon: "/jpbx/resources/img/handsetRing.png"
                        });
                }
            }
        });
    }      
        //loadInternal('/jpbx/CallFromMtoServlet?call='+$('#peer').val()+"|"+peer,'screen');
}
function refresh(){
    update();
    setTimeout('refresh();',3000);
};    
$(function(){
    refresh();
    notifyMe();
});
