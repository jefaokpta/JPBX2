function notifyMe() {
  // Let's check if the browser supports notifications
  if (!("Notification" in window)) {
    //console.log("This browser does not support desktop notification");
    alert('Seu navegador não suporta Notificações.');
  }

  // Let's check whether notification permissions have alredy been granted
  else if (Notification.permission === "granted") {
    // If it's okay let's create a notification
     var notification = new Notification("Notificações ativadas!",{
        body: "Informações sobre as ligações aparecerão aqui.",
        icon: "/jpbx/resources/img/megaphone.png"
    });
  }

  // Otherwise, we need to ask the user for permission
  else if (Notification.permission !== 'denied' || Notification.permission === "default") {
    Notification.requestPermission(function (permission) {
        // If the user accepts, let's create a notification
        if (permission === "granted") {
          var notification = new Notification("Notificações ativadas!",{
              body: "Informações sobre as ligações aparecerão aqui.",
              icon: "/jpbx/resources/img/megaphone.png"
          });
        }
      });
  }

  // At last, if the user has denied notifications, and you 
  // want to be respectful there is no need to bother them any more.
}