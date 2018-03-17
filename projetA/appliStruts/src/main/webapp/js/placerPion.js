var tab = new Array(10);
initialiserTab();
var toDeplace = null;

var tempsRestants = 120;

function chrono() {
    var res='';
    if(tempsRestants == 0){
        clearTimeout(intervalle);
        clearTimeout(compte);
        res = '<div class="alert alert-info">'+
            '<strong>'+ document.getElementById('tempsEcoule').value+'</strong>' +
            '</div>';
        $('#info').html(res);
        placementRandom();
        var tempsRedirect= setTimeout(function () {
            envoyer();
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

//Initialisation du tableau de pions en entiers à renvoyer

function initialiserTab() {
    for(var i=0; i<6; i++){
        tab[i] = new Array(10);
        for(var j=0; j<10; j++){
            tab[i][j] = 0;
        }
    }
    for(var x=6; x<10; x++){
        tab[x] = new Array(10);
        for(var y=0; y<10; y++){
            tab[x][y] = -1;
        }
    }
}

//Fonctions de Randoms

function randomInt(mini, maxi)
{
    var nb = mini + (maxi+1-mini)*Math.random();
    return Math.floor(nb);
}

Array.prototype.shuffle = function(n)
{
    if(!n)
        n = this.length;
    if(n > 1)
    {
        var i = randomInt(0, n-1);
        var tmp = this[i];
        this[i] = this[n-1];
        this[n-1] = tmp;
        this.shuffle(n-1);
    }
}

function placementRandom(){
    var nbRdm = new Array(40);
    for(var z=0;z<40;z++){
          nbRdm[z] = z;
    }
    nbRdm.shuffle();
    var compteur = 0;
    var a = 6;
    while(a<10 && compteur<40){
        var b = 0;
        while (b<10 && compteur<40){
            var isPionToPlace = !document.getElementById('Aplacer'+nbRdm[compteur]).firstElementChild.getAttribute('src').toString().match('/img/'+document.getElementById("joueur").value+'_pion_cachee.bmp');
            var isCaseVide = document.getElementById('placer'+a+'-'+b).getAttribute('src').toString().match('/img/CaseHerbe.jpg');
            if(isPionToPlace && isCaseVide) {
                tab[a][b] = document.getElementById('Aplacer'+nbRdm[compteur]).firstElementChild.getAttribute('id');
                document.getElementById('placer'+a+'-'+b).src = './img/'+document.getElementById("joueur").value+'_pion_'+tab[a][b]+'.bmp';
                compteur++;
                b++;
            }else if(isPionToPlace && !isCaseVide){
                b++;
            }else if(!isPionToPlace && isCaseVide){
                compteur++;
            }else if(!isPionToPlace && !isCaseVide){
                compteur++;
                b++;
            }
        }
        a++;
    }
    $('.Aplacer').attr('src', "./img/"+document.getElementById("joueur").value+"_pion_cachee.bmp");
    document.getElementById('random').style.visibility = 'hidden';
    document.getElementById('envoyer').style.visibility = 'visible';
}

//Fonctions des placements, déplacements, et replacements des pions sur le damier

function placer(element, lig, col) {
    if(toDeplace != null && element.getAttribute('src').toString().match('/img/CaseHerbe.jpg')){
        if(toDeplace.className != element.className){
            element.src = toDeplace.src;
            tab[lig][col] = toDeplace.id;
            toDeplace.src = './img/'+document.getElementById('joueur').value+'_pion_cachee.bmp';
            toDeplace.style.border = 'none';
            toDeplace = null;
            document.getElementById('envoyer').style.visibility = (canSend()?'visible':'hidden');
            document.getElementById('random').style.visibility = (canSend()?'hidden':'visible');
        }
    }else {
        deplacerPionDamier(element, lig, col);
    }
}

function deplacer(element) {
    if(!element.getAttribute('src').toString().match('/img/'+document.getElementById('joueur').value+'_pion_cachee.bmp')){
        element.style.border = '2px red solid';
        if(toDeplace != null){
            toDeplace.style.border = 'none';
        }
        toDeplace = element;
    }else{
        replacer(element);
    }
}

var x = 0, y = 0;

function replacer(element) {
    if(toDeplace != null && (toDeplace.className != element.className)) {
        element.src = toDeplace.src;
        element.id = tab[x][y];
        tab[x][y] = -1;
        toDeplace.src = './img/CaseHerbe.jpg';
        toDeplace.style.border = 'none';
        toDeplace = null;
        document.getElementById('envoyer').style.visibility = 'hidden';
    }
}

function canSend() {
    for(var x=6; x<10; x++){
        for(var y=0; y<10; y++){
            if(tab[x][y].toString() == -1) return false;
        }
    }
    return true;
}

function deplacerPionDamier(element, lig, col) {
    if (!element.getAttribute('src').toString().match('/img/CaseHerbe.jpg')){
        element.style.border = '2px red solid';
        if(toDeplace != null){
            toDeplace.style.border = 'none';
        }
        toDeplace = element;
        x = lig;
        y = col;
    }
}

//Fonction d'envoie lorsque tous les pions sont placés (grâce à canSend()

function envoyer() {
    var params = '';
    for(var i=0; i<10; i++){
        for(var j=0; j<10; j++){
            params += 'valeurs='+tab[i][j]+((i==9 && j==9)?'':'&');
        }
    }
    location.href = './placerPion.action?'+params;
}