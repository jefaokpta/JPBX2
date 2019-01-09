// INICIALIZANDO WEB STORAGE
var lastCalls=localStorage.getItem('callHistory');
if(lastCalls)
    lastCalls=JSON.parse(lastCalls);
else
    lastCalls=[];
function clearCallList(){
    lastCalls=[];
    localStorage.setItem('callHistory',JSON.stringify(lastCalls));
    $('#lastCalls').empty();
}
function addCall(dst,sense){
    today=new Date();
    lastCalls.push({
        dateTime:(today.getDate()<10?'0'+today.getDate():today.getDate())+'/'+((today.getMonth()+1)<10?'0'+today.getMonth():today.getMonth())+' '+
                (today.getHours()<10?'0'+today.getHours():today.getHours())+':'+(today.getMinutes()<10?'0'+today.getMinutes():today.getMinutes()),
        number:dst,
        sense:sense
    });
    localStorage.setItem('callHistory',JSON.stringify(lastCalls));
}
function buildListCalls(){
    for(i=lastCalls.length;i>0;i--){
        $('#lastCalls').append(
            '<li>\n\
                <div class="row borderRedonda" onclick="$(\'#tel\').val('+lastCalls[i-1].number+');">\n\
                    <div class="col-md-6">\n\
                        <strong>'+lastCalls[i-1].number+'</strong>\n\
                        <h5>'+lastCalls[i-1].dateTime +'</h5>\n\
                    </div>\n\
                    <div class="col-md-5" style="text-align: right">\n\
                        <span class="label label-primary">'+(lastCalls[i-1].sense>0?'Efetuada':'Recebida')+'</span>\n\
                    </div>\n\
                </div>\n\
            </li>'
        );
    }
}
function dial(src,dst,chan){
    if(dst!==''){
        $('#callBtn').html('<i class="fa fa-circle-o-notch fa-spin"></i> Ligando...');
        $('#callBtn').attr('onclick','');
        addCall(dst,1);
        $.ajax({
            url:'/jpbx/CallFromAgentServlet',
            data:{
               src: src,
               dst: dst,
               chan: chan
            },
            success: function(data){
                if(data==='Success'){
                    if (Notification.permission === "granted")
                        var notification = new Notification("Ligação atendida!",{
                            body: 'O número '+dst+' atendeu.',               
                            icon: "/jpbx/resources/img/handsetRing.png"
                        });
                }
                else if(data==='Error'){
                    if (Notification.permission === "granted")
                        var notification = new Notification("Ligação não completada.",{
                            body: 'Por favor verifique o número discado.',               
                            icon: "/jpbx/resources/img/handsetRing.png"
                        });
                }
                else{
                    if (Notification.permission === "granted")
                        var notification = new Notification("Não atendida.",{
                            body: 'O número '+dst+' não atendeu.',               
                            icon: "/jpbx/resources/img/handsetRing.png"
                        });                   
                }
                $('#callBtn').html('<i class="fa fa-phone"></i> Discar');
                $('#callBtn').attr('onclick','dial(src,$("#tel").val(),chan);');
            }
        });        
    }      
}

//$('#tel').keypress(function(e) {
//    if(e.which == 13) {
//        dial(src,$('#tel').val(),chan);
//    }
//});
