function countTime(secs){
    hour=Math.floor(secs/3600);
    secs%=3600;
    if(hour<10)
        rHour='0'+hour;
    else
        rHour=hour;
    min=Math.floor(secs/60);
    if(min<10)
        rmin='0'+min;
    else
        rmin=min;
    rsec=secs%60;
    if(rsec<10)
        rsec='0'+rsec;
    
    var formated=rHour+':'+rmin+':'+rsec;
    if(formated.match(/-/)=='-')
        return '';
    return formated;
}
