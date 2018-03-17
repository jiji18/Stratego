var monIntervalle;
var tempsRestants = 120;

function showModificationPswd() {
    document.getElementById('modificationPswd').style.visibility = 'visible';
}

function createPart(joueurInviter) {
    tempsRestants = 120;
    var typeOfPart = (joueurInviter == "" ?"":document.getElementById('privee').value);
    var error = false;
    $.ajax({
        type: "POST",
        url: "./json/createPart.action?typeOfPart="+typeOfPart+"&joueurInviter="+joueurInviter+"",
        contentType: "application/json: charset=utf-8",
        dataType: "json",
        success: function (retu) {
            monIntervalle = setTimeout(refresh, 1000);
        },
        error: function () {
            var res = '<div class="alert alert-danger">'+
                '<strong>'+ document.getElementById("pasDispo").value +'</strong>' +
                '</div>';
            $('#info').html(res);
            setTimeout(function () {
                monIntervalle = setTimeout(refresh, 1000);
            }, 1000);
        }
    });
}

function joinPart(idJoueur1) {
    tempsRestants = 120;
    $.ajax({
        type: "POST",
        url: "./json/joinPart.action?idJoueur1="+idJoueur1,
        contentType: "application/json: charset=utf-8",
        dataType: "json",
        success: function (retu) {
            location.href = "./goToPlacerPion.action";
        },
        error: function () {
            var res = '<div class="alert alert-danger">'+
                '<strong>'+ document.getElementById("noPart").value +'</strong>' +
                '</div>';
            $('#info').html(res);
            setTimeout(function () {
                monIntervalle = setTimeout(refresh, 1000);
            }, 1000);
        }
    });
}

function observePart(idJoueur1) {
    tempsRestants = 120;
    $.ajax({
        type: "POST",
        url: "./json/observePart.action?idJoueur1="+idJoueur1,
        contentType: "application/json: charset=utf-8",
        dataType: "json",
        success: function (retu) {
            location.href = "./goToPlay.action";
        },
        error: function () {
            var res = '<div class="alert alert-danger">'+
                '<strong>'+ document.getElementById("partNotExist").value +'</strong>' +
                '</div>';
            $('#info').html(res);
            setTimeout(function () {
                monIntervalle = setTimeout(refresh, 1000);
            }, 1000);
        }
    });
}

function declinePart(idJoueur1){
    tempsRestants = 120;
    $.ajax({
        type: "POST",
        url: "./json/declinePart.action?idJoueur1="+idJoueur1,
        contentType: "application/json: charset=utf-8",
        dataType: "json",
        success: function (retu) {

        },
        error: function () {

        }
    });
    monIntervalle = setTimeout(refresh, 1000);
}

function removePart() {
    tempsRestants = 120;
    $.ajax({
        type: "POST",
        url: "./json/removePart.action",
        contentType: "application/json: charset=utf-8",
        dataType: "json",
        success: function (retu) {

        },
        error: function () {
        }
    });
    monIntervalle = setTimeout(refresh, 1000);
}

//Refresh toutes les 1 secondes des données du menu

function refresh() {

    if(tempsRestants==0){
        var res = '<div class="alert alert-danger">'+
            '<strong>'+ document.getElementById('timeout').value +'</strong>' +
            '</div>';
        $('#info').html(res);
        var tempAction = setTimeout(function () {
            location.href = './logout.action';
        },3000);
    }
    else{

        tempsRestants--;

        $.ajax({
            type: "GET",
            url: "./json/loadData.action",
            contentType: "application/json: charset=utf-8",
            dataType: "json",
            success: function (retu) {
                //On cache le formulaire de création de partie publique si l'utilisateur en a déjà créé une
                document.getElementById('creerPartiePublique').style.visibility = (retu.dejaCreerPartie?'hidden':'visible');

                //Rafraichissement des joueurs connectés
                var res = '';
                if (retu.joueursConnectes != null) {
                    for (var i = 0; i < Object.keys(retu.joueursConnectes).length; i++) {
                        if (retu.joueursConnectes[i].statut == 'CONNECTED') {
                            //On vérifie si le joueur connecté n'a pas déjà été invité
                            var dejaInvite = false;
                            if(retu.dejaInvite != null){
                                for (var x = 0; x < Object.keys(retu.dejaInvite).length; x++) {
                                    if (retu.joueursConnectes[i].identifiant == retu.dejaInvite[x].identifiant) dejaInvite = true;
                                }
                            }
                            if(dejaInvite || retu.dejaCreerPartiePublique){
                                res += '<tr class="success"><td>' + retu.joueursConnectes[i].identifiant + '</td><td>' + document.getElementById(retu.joueursConnectes[i].statut).value + '</td></tr>';
                            }else{
                                res += '<tr class="success">' +
                                    '<td>' +
                                    '<button class="popup-open" style="border: none; text-decoration: underline" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#joueur-'+retu.joueursConnectes[i].identifiant+'">' + retu.joueursConnectes[i].identifiant + '</button>' +
                                    '<div id="joueur-'+retu.joueursConnectes[i].identifiant+'" class="modal fade" role="dialog" align="center">'+
                                    '<div class="modal-dialog">'+
                                    '<div class="modal-content">'+
                                    '<div class="modal-header">'+
                                    '<button type="button" class="close click" data-dismiss="modal">&times;</button>'+
                                    '<h4 class="modal-title">'+retu.joueursConnectes[i].identifiant+'</h4>'+
                                    '</div>'+
                                    '<div class="modal-body">'+
                                    '<div class="row">'+
                                    '<button id="invit-'+retu.joueursConnectes[i].identifiant+'" data-dismiss="modal" class="btn-primary btn-lg col-sm-offset-5 col-xs-offset-5 col-md-offset-5 col-lg-offset-5 col-sm-2 col-xs-2 col-md-2 col-lg-2">'+document.getElementById('invit').value+'</button>'+
                                    '</div>'+
                                    '</div>'+
                                    '</div>'+
                                    '</div>'+
                                    '</div>' +
                                    '</td>' +
                                    '<td>' + document.getElementById(retu.joueursConnectes[i].statut).value + '</td>' +
                                    '</tr>';
                            }
                        } else if (retu.joueursConnectes[i].statut == 'WAITINGPLAYER') {
                            res += '<tr class="warning"><td>' + retu.joueursConnectes[i].identifiant + '</td><td>' + document.getElementById(retu.joueursConnectes[i].statut).value + '</td></tr>';
                        } else {
                            res += '<tr class="danger"><td>' + retu.joueursConnectes[i].identifiant + '</td><td>' + document.getElementById(retu.joueursConnectes[i].statut).value + '</td></tr>';
                        }
                    }
                }
                $('#joueursConnectes').html(res);

                //On ajoute les évènnements de chacun des boutons de cet div
                if (retu.joueursConnectes != null) {
                    for (var i = 0; i < Object.keys(retu.joueursConnectes).length; i++) {
                        eval('$("#invit-'+retu.joueursConnectes[i].identifiant+'").click(function () {' +
                            'createPart("'+retu.joueursConnectes[i].identifiant+'");' +
                            '});');
                    }
                }

                //Rafraichissement des parties publiques
                res = '';
                if (retu.partiesPubliques != null) {
                    for (var i = 0; i < Object.keys(retu.partiesPubliques).length; i++) {
                        res += '<tr class="active">' +
                            '<td>' + document.getElementById('creer').value + '</td>';
                        if(retu.dejaCreerPartie){
                            res += '<td>'+retu.partiesPubliques[i].j1.identifiant+'</td></tr>';
                        }else{
                            res += '<td>' +
                                '<button class="popup-open" style="border: none; text-decoration: underline" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#partie-'+retu.partiesPubliques[i].j1.identifiant+'">'+retu.partiesPubliques[i].j1.identifiant +'</button>' +
                                '<div id="partie-'+retu.partiesPubliques[i].j1.identifiant+'" class="modal fade" role="dialog" align="center">'+
                                '<div class="modal-dialog">'+
                                '<div class="modal-content">'+
                                '<div class="modal-header">'+
                                '<button type="button" class="close click" data-dismiss="modal">&times;</button>'+
                                '<h4 class="modal-title">'+retu.partiesPubliques[i].j1.identifiant+'</h4>'+
                                '</div>'+
                                '<div class="modal-body">'+
                                '<div class="row">';
                            if(retu.partiesPubliques[i].j2 == null) {
                                res += '<button id="partiePub-'+retu.partiesPubliques[i].j1.identifiant+'" data-dismiss="modal" class="btn-primary btn-lg col-sm-offset-5 col-xs-offset-5 col-md-offset-5 col-lg-offset-5 col-sm-2 col-xs-2 col-md-2 col-lg-2">'+document.getElementById('join').value+'</button>';
                            }
                            else{
                                res += '<button id="partiePub-'+retu.partiesPubliques[i].j1.identifiant+'" data-dismiss="modal" class="btn-primary btn-lg col-sm-offset-5 col-xs-offset-5 col-md-offset-5 col-lg-offset-5 col-sm-2 col-xs-2 col-md-2 col-lg-2">'+document.getElementById('observe').value+'</button>';
                            }
                            res += '</div>'+
                                '</div>'+
                                '</div>'+
                                '</div>'+
                                '</div>' +
                                '</td>'+
                                '</tr>';
                        }
                    }
                }
                $('#lesPartiesPubliques').html(res);

                //On ajoute les évènnements de chacun des boutons de cet div
                if (retu.partiesPubliques != null) {
                    for (var i = 0; i < Object.keys(retu.partiesPubliques).length; i++) {
                        if(retu.partiesPubliques[i].j2 == null){
                            eval('$("#partiePub-'+retu.partiesPubliques[i].j1.identifiant+'").click(function () {' +
                                'joinPart("'+retu.partiesPubliques[i].j1.identifiant+'");' +
                                '});');
                        }else{
                            eval('$("#partiePub-'+retu.partiesPubliques[i].j1.identifiant+'").click(function () {' +
                                'observePart("'+retu.partiesPubliques[i].j1.identifiant+'");' +
                                '});');
                        }
                    }
                }

                //Rafraichissement du classement général
                res = '';
                if (retu.classement != null) {
                    for (var i = 0; i < Object.keys(retu.classement).length; i++) {
                        var nbV = retu.classement[i].nbVictoires;
                        var nbD = retu.classement[i].nbDefaites;
                        var ratio = (eval('nbV+nbD')!=0?eval('(nbV-nbD)/(nbV+nbD)'):0);
                        res += '<tr class="active"><td>' + (i + 1 == 1 ? '1er' : (i + 1) + 'e') + '</td><td>' + retu.classement[i].identifiant + '</td><td>' + (ratio.toString().length > 5?ratio.toString().substring(0, 5):ratio.toString()) + '</td></tr>';
                    }
                }
                $('#classements').html(res);

                //Rafraichissement des invitations
                res = '';
                if (retu.invitations != null) {
                    $('#nb-invitations').html(Object.keys(retu.invitations).length);
                    for (var i = 0; i < Object.keys(retu.invitations).length; i++) {
                        res += '<tr class="info">' +
                            '<td>' + document.getElementById('joueurInviteur').value + '</td>' +
                            '<td> ' +
                            '<button class="popup-open" style="border: none; text-decoration: underline" data-toggle="modal" data-backdrop="static" data-keyboard="false" data-target="#invitation-'+retu.invitations[i].j1.identifiant+'">' + retu.invitations[i].j1.identifiant + '</button>' +
                            '<div id="invitation-'+retu.invitations[i].j1.identifiant+'" class="modal fade" role="dialog" align="center">'+
                            '<div class="modal-dialog">'+
                            '<div class="modal-content">'+
                            '<div class="modal-header">'+
                            '<button type="button" class="close click" data-dismiss="modal">&times;</button>'+
                            '<h4 class="modal-title">'+retu.invitations[i].j1.identifiant+'</h4>'+
                            '</div>'+
                            '<div class="modal-body">'+
                            '<div class="row">'+
                            '<button id="join-'+retu.invitations[i].j1.identifiant+'" data-dismiss="modal" class="btn-primary btn-lg col-sm-offset-5 col-xs-offset-5 col-md-offset-5 col-lg-offset-5 col-sm-2 col-xs-2 col-md-2 col-lg-2">'+document.getElementById('join').value+'</button>' +
                            '<button id="decline-'+retu.invitations[i].j1.identifiant+'" data-dismiss="modal" class="col-xs-offset-1 col-sm-offset-1 col-md-offset-1 col-lg-offset-1 btn-primary btn-lg">'+document.getElementById('refuse').value+'</button>' +
                            '</div>'+
                            '</div>'+
                            '</div>'+
                            '</div>'+
                            '</div>' +
                            '</td> ' +
                            '</tr>';
                    }
                } else $('#nb-invitations').html(res);

                $('#invitations').html(res);

                //On ajoute les évènnements de chacun des boutons de cet div
                if (retu.invitations != null) {
                    for (var i = 0; i < Object.keys(retu.invitations).length; i++) {
                        eval('$("#join-'+retu.invitations[i].j1.identifiant+'").click(function () {' +
                            'joinPart("'+retu.invitations[i].j1.identifiant+'");' +
                            '});');
                        eval('$("#decline-'+retu.invitations[i].j1.identifiant+'").click(function () {' +
                            'declinePart("'+retu.invitations[i].j1.identifiant+'");' +
                            '});');
                    }
                }

                //Rafraichissement des infos
                res = '';
                if(retu.reponse != null){
                    if(retu.reponse == document.getElementById('continue').value){
                        clearTimeout(monIntervalle);
                        res +='<div class="alert alert-info">'+
                            '<strong>'+retu.reponse+'</strong> '+
                            '</div>';
                        var tempsRedirect = setTimeout(function () {
                            location.href="./goToPlacerPion.action";
                        }, 3000);
                    }else if(retu.reponse == document.getElementById('retourMenu').value){
                        clearTimeout(monIntervalle);
                        res +='<div class="alert alert-info">'+
                            '<strong>'+retu.reponse+'</strong>'+
                            '</div>';
                        var tempsRedirect = setTimeout(function () {
                            removePart();
                        }, 3000);
                    }else if(retu.reponse == document.getElementById('wait').value) {
                        res += '<div class="alert alert-info">'+
                            '<strong>'+retu.reponse+'</strong> &nbsp;&nbsp;&nbsp;&nbsp;<button id="remove">'+document.getElementById('annuler').value+'</button>'+
                            '</div>';
                    }
                }
                $('#info').html(res);

                $('#remove').click(function () {
                    clearTimeout(monIntervalle);
                    removePart();
                });

                //Dans le cas où l'on ouvre un pop-up, on arrête le rafraichissement des données
                $('.popup-open').click(function () {
                    clearTimeout(monIntervalle);
                });

                //À la fermeture d'un pop-up ou d'un lien, on redéclenche le rafraichissement
                $('.click').click(function () {
                    monIntervalle = setTimeout(refresh, 1000);
                });
            },
            error: function () {
            }
        });
        monIntervalle = setTimeout(refresh, 1000);
    }
}