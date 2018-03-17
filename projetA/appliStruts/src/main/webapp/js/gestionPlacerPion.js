var intervalle;

function refresh() {
    $.ajax({
        type: "GET",
        url: "./json/loadAbandon.action",
        contentType: "application/json: charset=utf-8",
        dataType: "json",
        success: function (retu) {
            if(retu.abandon){
                clearTimeout(intervalle);
                clearTimeout(compte);
                var res = '<div class="alert alert-info">'+
                    '<strong>'+ document.getElementById("abandon").value +'</strong>' +
                    '</div>';
                $('#info').html(res);
                var tempRedirect = setTimeout(function () {
                    location.href = "./goToLobby.action";
                }, 3000);
            }
        },
        error: function () {
        }
    });
    intervalle = setTimeout(refresh, 1000);
}