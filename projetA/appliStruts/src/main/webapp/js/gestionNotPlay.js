var intervalle;

function refresh() {
    $.ajax({
        type: "GET",
        url: "./json/loadNotPlay.action",
        contentType: "application/json: charset=utf-8",
        dataType: "json",
        success: function (retu) {

            if(retu.srcVu != null){
                clearTimeout(intervalle);
                document.getElementById(retu.xDefier+'-'+retu.yDefier).src = retu.srcVu;

            }

            // Permet de savoir si c'est au joueur courant de jouer ou non
            if(retu.canPlay != null){
                if(retu.canPlay){
                    if(retu.srcVu != null){
                        var tempRedirect = setTimeout(function () {
                            location.href="./goToPlay.action";
                        }, 3000);
                    }else{
                        location.href="./goToPlay.action";
                    }
                }
            }
            var res='';

            //En cas de fin de partie
            if(retu.msg != null){
                clearTimeout(intervalle);
                res = '<div class="alert alert-info">'+
                    '<strong>'+ retu.msg +'</strong>' +
                    '</div>';
                $('#info').html(res);
                var tempRedirect = setTimeout(function () {
                    location.href = "./goToLobby.action";
                }, 3000);
            }

            //Raifraichissement du damier
            res='<table>';
            if (retu.damierActif != null) {
                for (var i = 0; i < Object.keys(retu.damierActif).length; i++) {
                    res +='<tr>';
                    for(var j = 0; j < Object.keys(retu.damierActif[i]).length; j++) {
                        res +='<td>';
                        if(retu.damierActif[i][j].caseObstacle){
                            res +='<img src="./img/CaseLac-'+i+''+j+'.jpg" style="height: 80px; width: 80px;"/>';
                        }else if(retu.damierActif[i][j].caseVide || retu.damierActif[i][j].valeur == -1){
                            res += '<img src="./img/CaseHerbe.jpg" style="height: 80px; width: 80px;"/>';
                        }else{
                            if(document.getElementById('joueur').value != retu.joueur1 && document.getElementById('joueur').value != retu.joueur2){
                                res+='<img id="'+i+'-'+j+'" src="./img/'+(retu.damierActif[i][j].joueur.identifiant == retu.joueur1?'j1':'j2')
                                    +'_pion_cachee.bmp" style="height: 80px; width: 80px;"/>';
                            }else if(document.getElementById('joueur').value == retu.joueur1){
                                res+='<img id="'+i+'-'+j+'" src="./img/'+(retu.damierActif[i][j].joueur.identifiant == retu.joueur1?'j1_pion_'+retu.damierActif[i][j].valeur:'j2_pion_cachee')
                                    +'.bmp" style="height: 80px; width: 80px;"/>';
                            }else{
                                res+='<img id="'+i+'-'+j+'" src="./img/'+(retu.damierActif[i][j].joueur.identifiant == retu.joueur2?'j2_pion_'+retu.damierActif[i][j].valeur:'j1_pion_cachee')
                                    +'.bmp" style="height: 80px; width: 80px;"/>';
                            }
                        }
                        res +='</td>';
                    }
                    res += '</tr>';
                }
            }
            res += '</table>';
            if(retu.srcVu == null){
                $('#damier').html(res);
            }

        },
        error: function () {
        }
    });

    intervalle = setTimeout(refresh, 1000);
}