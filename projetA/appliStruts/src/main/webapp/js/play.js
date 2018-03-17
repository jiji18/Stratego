var tempsRestants = 15;

function chrono() {
    var res='';
    if(tempsRestants == 0){
        //clearTimeout(intervalle);
        clearTimeout(compte);
        res = '<div class="alert alert-info">'+
            '<strong>'+ document.getElementById('tempsEcoule').value+'</strong>' +
            '</div>';
        $('#info').html(res);
        var tempsRedirect= setTimeout(function () {
            location.href='passerSonTour.action';
        },2000);
    }else{
        res = '<div class="alert alert-info">'+
            '<strong>'+document.getElementById('tempsRest').value+' '+ tempsRestants +' '+document.getElementById('sec').value+'</strong>' +
            '</div>';
        $('#info').html(res);
        tempsRestants --;
        compte = setTimeout(chrono, 1000);
    }
}

var x= null, y=null;
var toDeplace=null;
var canMove = new Array(10);

//init
for(var i = 0; i<10; i++){
    canMove[i] = new Array(10);
    for(var j=0; j<10; j++){
        canMove[i][j] = false;
    }
}

function deplacerPion(lig, col) {
    if(toDeplace != null){
        if(canMove[lig][col]){
            clearTimeout(compte);
            toDeplace.style.border = 'none';
            toDeplace = null;
            $.ajax({
                type: "POST",
                url: "./json/deplacerPion.action?x="+x+"&y="+y+"&newX="+lig+"&newY="+col,
                contentType: "application/json: charset=utf-8",
                dataType: "json",
                success: function (retu) {
                    //En cas de fin de partie
                    if(retu.msg != null){
                        var res='<div class="alert alert-info">'+
                                    '<strong>'+ retu.msg +'</strong>' +
                                '</div>';
                        $('#info').html(res);
                        var tempRedirect = setTimeout(function () {
                            location.href = "./goToLobby.action";
                        }, 3000);
                    }else{
                        //En cas d'affront de 2 pions
                        if(retu.srcVu != null){
                            eval('$("#'+retu.newX+'-'+retu.newY+'").attr("src", "'+ retu.srcVu+'")');
                            var tempRedirect = setTimeout(function () {
                                location.href="./goToPlay.action";
                            }, 3000);
                        }else{
                            location.href="./goToPlay.action";
                        }
                    }
                },
                error: function () {
                }
            });
        }
    }
}

function choisirPion(element, lig, col) {
    var callAjax = true;
    if(toDeplace != null){
        toDeplace.style.border = 'none';
        callAjax = (x!=lig || y!=col);
    }
    if(callAjax){
        toDeplace = element;
        toDeplace.style.border = '2px red solid';
        x=lig;
        y=col;
        $.ajax({
            type: "POST",
            url: "./json/canMove.action?x="+x+"&y="+y,
            contentType: "application/json: charset=utf-8",
            dataType: "json",
            success: function (retu) {
                var notMove = true;
                canMove = retu.canMove;
                for(var i = 0; i < Object.keys(retu.canMove).length; i++){
                    if(! notMove){
                        break;
                    }
                    for(var j = 0; j < Object.keys(retu.canMove[i]).length; j++){
                        if(retu.canMove[i][j]){
                            notMove = false;
                            break;
                        }
                    }
                }
                if(notMove){
                    toDeplace.style.border = 'none';
                    x = null;
                    y = null;
                }
            },
            error: function () {
            }
        });
    }else{
        toDeplace = null;
        x = null;
        y = null;
    }
}