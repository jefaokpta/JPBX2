var session;
var host=window.location.href.toString().split('/')[2];
host=host.split(':')[0];
//host='18.206.231.151'; //teste
//host='aws.jpbx.com.br';
var userAgent;
var peer;
var pass='jefaokpta666thebadass';
var ring=new Audio('resources/songs/webRing.mp3');
var secs=0;
var timeCtrl=1;
var answered=0;
var tel;
var vid=document.getElementById('remoteVideo');
vid.volume=0.5;
var vol=document.getElementById('volume');
var mute=document.getElementById('muteAudio');
var videoDiv=document.getElementById('showVideoDiv');
var videoCheck=document.getElementById('showVideo');

var lastCalls=localStorage.getItem('calls');
if(lastCalls)
    lastCalls=JSON.parse(lastCalls);
else
    lastCalls=[];
function clearCallList(){
    lastCalls=[];
    localStorage.setItem('calls',JSON.stringify(lastCalls));
    $('#lastCalls').empty();
}
var today=new Date();

function authentication(){
    sessionStorage.setItem('peer',$('#peer').val());
    sessionStorage.setItem('pass',$('#pass').val());
}
function register(){
    peer=sessionStorage.getItem('peer');
    pass=sessionStorage.getItem('pass');
    var config = {
        uri: 'sip:'+peer+'@'+host,
        wsServers: ['wss://'+host+':8089/ws'],
        authorizationUser: peer,  
        password: peer+':'+pass,
        userAgentString: 'JPBX_WEB_PHONE',
        hackIpInContact: false,
        hackWssInTransport: true
        //stunServers:'null'
    };
    userAgent = new SIP.UA(config);
    sessionStorage.removeItem('peer');
    sessionStorage.removeItem('pass');
    userAgent.on('invite', function (incomingSession) {       
        //ring.play();
        answered=0;
        today=new Date();
        session=incomingSession;
        session.on('terminated',function(){
            $('#keyBoard').hide(800);
            $('#timeCall').hide(800);  
            $('#localVideo').css('width','300px');
            $('#localVideo').css('height','300px');
            $('#localVideo').hide(800);
            $('#remoteVideo').hide(800);
            $('#showVideo').prop('checked',false);
            $('#showVideoDiv').hide();
            $('#localVideoDiv').css('position','relative');
            ring.pause();
            timeCtrl=1;
            lastCalls.push({
                    callerId:tel,
                    dateTime:(today.getDate()<10?'0'+today.getDate():today.getDate())+'/'+((today.getMonth()+1)<10?'0'+today.getMonth():today.getMonth())+' '+
                    (today.getHours()<10?'0'+today.getHours():today.getHours())+':'+(today.getMinutes()<10?'0'+today.getMinutes():today.getMinutes()),
                    duration:secs,
                    sense:0,    
                    answer:answered
            });
            localStorage.setItem('calls',JSON.stringify(lastCalls));
            $('#lastCalls').empty();
            buildListCalls();
        });
        secs=0;
        tel=session.remoteIdentity.displayName;
        if(!document.getElementById('autoAnswer').checked){
            ring.play();
            if(!confirm('Ligação de '+session.remoteIdentity.displayName+' atender?')){
                ring.pause();           
                $('#keyBoard').hide(800);    
                $('#timeCall').hide(800);  
                session.terminate();
                return ;
            }
        }
        ring.pause();      
        session.accept({
            media: {     
                constraints: {
                    audio: true,
                    video: true
                },
                render: {
                    remote: document.getElementById('remoteVideo'),
                    local: document.getElementById('localVideo')
                }
            }
        });   
        answered=1;
        $('#keyBoard').show(800);
        $('#timeCall').show(800); 
        $('#showVideoDiv').show();
        $('#localVideoDiv').css('position','absolute');
        $('#localVideo').css('width','100px');
        $('#localVideo').css('height','100px');
        startCount();
//        session.on('bye',function(){
//            $('#keyBoard').hide(800);
//            $('#timeCall').hide(800);  
//            ring.pause();
//            timeCtrl=1;
//            today=new Date();
//            lastCalls.push({
//                    callerId:session.remoteIdentity.displayName,
//                    dateTime:(today.getDate()<10?'0'+today.getDate():today.getDate())+'/'+((today.getMonth()+1)<10?'0'+today.getMonth():today.getMonth())+' '+
//                    (today.getHours()<10?'0'+today.getHours():today.getHours())+':'+(today.getMinutes()<10?'0'+today.getMinutes():today.getMinutes()),
//                    duration:secs,
//                    sense:0,
//                    answer:answered
//            });
//            localStorage.setItem('calls',JSON.stringify(lastCalls));
//            $('#lastCalls').empty();
//            session = null; //teste
//            buildListCalls();
//        });
    });
    
    userAgent.on('registered',function(){
        $('#status').html('<img src="resources/img/circleGreenNop.png" width="25" /> <strong>Registrado</strong>');
    });
    userAgent.on('unregistered',function(){
       $('#status').html('<img src="resources/img/circleRedNop.png" width="25" /> <strong>Não Registrado</strong>');
    });
    document.getElementById('endCall').addEventListener("click", function () {
        $('#keyBoard').hide(800);
        $('#timeCall').hide(800);  
        timeCtrl=1;
        session.terminate();       
    }, false);
    document.getElementById('transfer').addEventListener("click", function() {
        session.dtmf('*');
        session.dtmf(2);
    }, false); 
    document.getElementById('d1').addEventListener("click", function() {
        session.dtmf(1);
    }, false); 
    document.getElementById('d2').addEventListener("click", function() {
        session.dtmf(2);
    }, false); 
    document.getElementById('d3').addEventListener("click", function() {
        session.dtmf(3);
    }, false); 
    document.getElementById('d4').addEventListener("click", function() {
        session.dtmf(4);
    }, false); 
    document.getElementById('d5').addEventListener("click", function() {
        session.dtmf(5);
    }, false); 
    document.getElementById('d6').addEventListener("click", function() {
        session.dtmf(6);
    }, false); 
    document.getElementById('d7').addEventListener("click", function() {
        session.dtmf(7);
    }, false); 
    document.getElementById('d8').addEventListener("click", function() {
        session.dtmf(8);
    }, false); 
    document.getElementById('d9').addEventListener("click", function() {
        session.dtmf(9);
    }, false); 
    document.getElementById('d0').addEventListener("click", function() {
        session.dtmf(0);
    }, false); 
    document.getElementById('ast').addEventListener("click", function() {
        session.dtmf('*');
    }, false); 
    document.getElementById('squ').addEventListener("click", function() {
        session.dtmf('#');
    }, false); 
}
//ABAIXO OQ PRECISA PRA FAZER LIGAÇÂO
function call(){//here you determine whether the call has video and audio
    var options = {
        media: {
            constraints: {
                audio: true,
                video: false
            },
            render: {
                remote: document.getElementById('remoteVideo'),
                local: document.getElementById('localVideo')
            }
        }
        //inviteWithoutSdp: true
    };
    //makes the call  
    $('#keyBoard').show(800);
    //startCount();
    today=new Date();
    answered=0;
    secs=0;
    tel=$('#tel').val().trim();
    session = userAgent.invite('sip:'+tel+'@'+host, options);
//    session.on('bye',function(){
//        $('#keyBoard').hide(800);
//        timeCtrl=1;
//        lastCalls.push({
//                callerId:tel,
//                dateTime:(today.getDate()<10?'0'+today.getDate():today.getDate())+'/'+((today.getMonth()+1)<10?'0'+today.getMonth():today.getMonth())+' '+
//                (today.getHours()<10?'0'+today.getHours():today.getHours())+':'+(today.getMinutes()<10?'0'+today.getMinutes():today.getMinutes()),
//                duration:secs,
//                sense:1,    
//                answer:answered
//        });
//        localStorage.setItem('calls',JSON.stringify(lastCalls));
//        $('#lastCalls').empty();
//        buildListCalls();
//    });
    session.on('accepted',function(){
        answered=1;
        startCount();
        $('#timeCall').show(800);  
    });
    session.on('terminated',function(){
        $('#keyBoard').hide(800);
        $('#timeCall').hide(800);  
        timeCtrl=1;
        lastCalls.push({
                callerId:tel,
                dateTime:(today.getDate()<10?'0'+today.getDate():today.getDate())+'/'+((today.getMonth()+1)<10?'0'+today.getMonth():today.getMonth())+' '+
                (today.getHours()<10?'0'+today.getHours():today.getHours())+':'+(today.getMinutes()<10?'0'+today.getMinutes():today.getMinutes()),
                duration:secs,
                sense:1,    
                answer:answered
        });
        localStorage.setItem('calls',JSON.stringify(lastCalls));
        $('#lastCalls').empty();
        buildListCalls();
    });
}
function callVideo(){//here you determine whether the call has video and audio
    var options = {
        media: {
            constraints: {
                audio: true,
                video: true
            },
            render: {
                remote: document.getElementById('remoteVideo'),
                local: document.getElementById('localVideo')
            }
        }
        //inviteWithoutSdp: true
    };
    //makes the call  
    $('#keyBoard').show(800);
    $('#localVideo').css('width','300px');
    $('#localVideo').css('height','300px');
    $('#localVideo').show(800);
    
    //startCount();
    today=new Date();
    answered=0;
    secs=0;
    tel=$('#tel').val().trim();
    session = userAgent.invite('sip:'+tel+'@'+host, options);
//    session.on('bye',function(){
//        $('#keyBoard').hide(800);
//        timeCtrl=1;
//        lastCalls.push({
//                callerId:tel,
//                dateTime:(today.getDate()<10?'0'+today.getDate():today.getDate())+'/'+((today.getMonth()+1)<10?'0'+today.getMonth():today.getMonth())+' '+
//                (today.getHours()<10?'0'+today.getHours():today.getHours())+':'+(today.getMinutes()<10?'0'+today.getMinutes():today.getMinutes()),
//                duration:secs,
//                sense:1,    
//                answer:answered
//        });
//        localStorage.setItem('calls',JSON.stringify(lastCalls));
//        $('#lastCalls').empty();
//        buildListCalls();
//    });
    session.on('accepted',function(){
        answered=1;
        startCount();
        $('#timeCall').show(800); 
        $('#remoteVideo').show(800);
        $('#localVideoDiv').css('position','absolute');
        $('#localVideo').css('width','100px');
        $('#localVideo').css('height','100px');
    });
    session.on('terminated',function(){
        $('#keyBoard').hide(800);
        $('#timeCall').hide(800);  
        $('#remoteVideo').hide(800);
        $('#localVideo').css('width','300px');
        $('#localVideo').css('height','300px');
        $('#localVideo').hide(800);
        $('#localVideoDiv').css('position','relative');
        
        timeCtrl=1;
        lastCalls.push({
                callerId:tel,
                dateTime:(today.getDate()<10?'0'+today.getDate():today.getDate())+'/'+((today.getMonth()+1)<10?'0'+today.getMonth():today.getMonth())+' '+
                (today.getHours()<10?'0'+today.getHours():today.getHours())+':'+(today.getMinutes()<10?'0'+today.getMinutes():today.getMinutes()),
                duration:secs,
                sense:1,    
                answer:answered
        });
        localStorage.setItem('calls',JSON.stringify(lastCalls));
        $('#lastCalls').empty();
        buildListCalls();
    });
}

$('#tel').keyup(function(event){
    if(event.keyCode===13)
        call();
});
var keysPermited=[96,97,98,99,100,101,102,103,104,105,106,48,49,50,51,52,53,54,55,56,57,46,8,13,37,39];
$('#tel').keyup(function(event){
    for (i=0;i<keysPermited.length;i++){
        if(event.keyCode===keysPermited[i-1])
            return ;
    }
    $('#tel').val($('#tel').val().substring(0,($('#tel').val().length-1)));
});

$('#status').html('<a href="https://'+host+':8089">Desconectado</a>');


function startCount(){
    secs=0;
    timeCtrl=0;
    count();
}
function countTime(){
    hour=Math.floor(secs/3600);
    if(hour<10)
        rHour='0'+hour;
    else
        rHour=hour;
    min=Math.floor(secs/60);
    if(min<10)
        rmin='0'+min;
    else
        rmin=min;
    rsec=secs-(min*60);
    if(rsec<10)
        rsec='0'+rsec;
    return rHour+':'+rmin+':'+rsec;
}
function countTimeCallLog(secs){
    hour=Math.floor(secs/3600);
    if(hour<10)
        rHour='0'+hour;
    else
        rHour=hour;
    min=Math.floor(secs/60);
    if(min<10)
        rmin='0'+min;
    else
        rmin=min;
    rsec=secs-(min*60);
    if(rsec<10)
        rsec='0'+rsec;
    return rHour+':'+rmin+':'+rsec;
}
function count(){
    if(timeCtrl>0)
        return ;
    $('#timeCall').html('<h4>'+countTime()+'</h4>');
    secs+=1;
    setTimeout("count();", 1000);
}
function buildListCalls(){
    for(i=lastCalls.length;i>0;i--){
        $('#lastCalls').append(
            '<li>\n\
                <div class="row borderRedonda" onclick="$(\'#tel\').val('+lastCalls[i-1].callerId+');">\n\
                    <div class="col-md-6">\n\
                        <span title="'+(lastCalls[i-1].sense>0?'Saída':'Entrada')+'" class="label label-'+(lastCalls[i-1].answer>0?'success':'danger')+'"><i class="fa fa-arrow-'+(lastCalls[i-1].sense>0?'right':'left')+'" /></span>\n\
                        <strong>'+lastCalls[i-1].callerId+'</strong>\n\
                        <h5>'+lastCalls[i-1].dateTime +'</h5>\n\
                    </div>\n\
                    <div class="col-md-5" style="text-align: right">\n\
                        <span class="label label-primary">'+(lastCalls[i-1].answer>0?'Atendida':'Não Atendida')+'</span>\n\
                        <h5>'+countTimeCallLog(lastCalls[i-1].duration) +'</h5>\n\
                    </div>\n\
                </div>\n\
            </li>'
        );
    }
}
buildListCalls();

vol.addEventListener('input',function(e){
    vid.volume=e.currentTarget.value/100;
});
mute.addEventListener('change',function(){
    if(mute.checked)
        session.mute();
    else
        session.unmute();
});
videoCheck.addEventListener('change',function(){
   if(videoCheck.checked){
       $('#localVideo').show(800);
       $('#remoteVideo').show(800);
   }
   else{
       $('#localVideo').hide(800);
       $('#remoteVideo').hide(800);
   }
});
