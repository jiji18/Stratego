<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: jolan
  Date: 11/22/16
  Time: 1:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="lobby.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="./img/Stratego-header.png">
    <sj:head/>
    <sb:head/>
    <link rel="stylesheet" media="screen" type="text/css" href="<s:url value="/css/style.css"/>">
    <script type="text/javascript" src="<s:url value="/js/gestionMenu.js"/>"></script>

</head>
<body onload="refresh();">
    <s:hidden id="CONNECTED" value="%{getText('lobby.statut.connected')}"/>
    <s:hidden id="WAITINGPLAYER" value="%{getText('lobby.statut.waitingplayer')}"/>
    <s:hidden id="WAITINGPLAY" value="%{getText('lobby.statut.play')}"/>
    <s:hidden id="PLAYING" value="%{getText('lobby.statut.play')}"/>
    <s:hidden id="OBSERVING" value="%{getText('lobby.statut.observe')}"/>
    <s:hidden id="creer" value="%{getText('lobby.creer')}"/>
    <s:hidden id="joueurInviteur" value="%{getText('lobby.joueurInviteur')}"/>
    <s:hidden id="privee" value="%{getText('lobby.createPart.prive')}"/>
    <s:hidden id="refuse" value="%{getText('lobby.refuse')}"/>
    <s:hidden id="join" value="%{getText('lobby.join')}"/>
    <s:hidden id="invit" value="%{getText('lobby.createPart.user.invit')}"/>
    <s:hidden id="observe" value="%{getText('lobby.observe')}"/>
    <s:hidden name="continue" value="%{getText('pre-game.continue')}"/>
    <s:hidden name="retourMenu" value="%{getText('pre-game.emptyInvit')}"/>
    <s:hidden name="wait" value="%{getText('pre-game.wait')}"/>
    <s:hidden name="annuler" value="%{getText('pre-game.annuler')}"/>
    <s:hidden name="pasDispo" value="%{getText('lobby.error.joueurInivter.occuper')}"/>
    <s:hidden name="noPart" value="%{getText('lobby.error.noPart')}"/>
    <s:hidden name="partNotExist" value="%{getText('lobby.error.partNotExist')}"/>
    <s:hidden name="timeout" value="%{getText('lobby.error.timeout')}"/>

    <s:url action="logout" var="logout"/>

    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <s:a href="#" class="navbar-brand"><span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;<s:text
                        name="lobby.title"/></s:a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li><s:a href="%{logout}"><span class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;<s:text
                            name="lobby.logout"/> <s:property value="#session.joueur.identifiant"/></s:a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <s:fielderror theme="bootstrap"/>
    <s:actionerror theme="bootstrap"/>

    <div class="container-fluid" id="info"></div>

    <div class="container-fluid">
        <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#menu"><s:text name="index.accueil"/></a></li>
            <li><a data-toggle="tab" href="#classement"><s:text name="lobby.classement"/></a></li>
            <li><a data-toggle="tab" href="#compte"><s:text name="lobby.compte"/></a></li>
            <li><a data-toggle="tab" href="#notif"><s:text name="lobby.notif"/> <span class="badge" id="nb-invitations"></span></a></li>
        </ul>

        <div class="tab-content">
            <div id="menu" class="tab-pane fade in active" align="center">
                <h3><s:text name="index.accueil"/></h3>

                <div class="container" style="border-radius: 1%; height: 800px;background-color: #eee;">
                    <div class="row">
                        <div class="col-sm-6 col-md-6 col-lg-6 col-xs-6" style="border-radius: 1%;border: solid 1px black;height: 500px;">
                            <h3><s:text name="lobby.publicPart"/></h3>
                            <div class="table-responsive">
                                <table class="table">
                                    <tbody id="lesPartiesPubliques">
                                    <%-- Ici se trouvera notre liste des parties publiques --%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-6 col-lg-6 col-xs-6" style="border-radius: 1%;border: solid 1px black;height: 500px;">
                            <h3><s:text name="lobby.joueurCo"/></h3>
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th><s:text name="lobby.joueurs"/></th>
                                        <th><s:text name="lobby.statut"/></th>
                                    </tr>
                                    </thead>
                                    <tbody id="joueursConnectes">
                                    <%-- Ici se trouvera notre liste des joueurs connectés --%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="row" id="creerPartiePublique">
                        <div class="col-md-12 col-xs-12 col-lg-12 col-sm-12" style="border-radius: 1%;border: solid 1px black;height: 300px;">
                            <div class="col-md-offset-2 col-md-8 text-center title">
                                <h3><s:text name="lobby.createPartPublic"/></h3>
                            </div>
                            <div class="col-md-offset-1 col-md-10 formulai-out">
                                <div class="well well-sm formulaire-in" style="height: 75px;">
                                    <div class="form-group">
                                        <div class="text-center col-md-offset-2 col-md-8">
                                            <button onclick="createPart('');" class="btn btn-primary btn-lg"><s:text name="lobby.createPart.creer"/></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="classement" class="tab-pane fade" align="center">
                <h3><s:text name="lobby.classement"/></h3>
                <div class="table-responsive" align="center">
                    <table class="table">
                        <thead>
                        <th><s:text name="lobby.position"/></th>
                        <th><s:text name="lobby.joueurs"/></th>
                        <th><s:text name="lobby.ratio"/></th>
                        </thead>
                        <tbody id="classements">
                        <%-- Ici se trouvera le classement général --%>
                        </tbody>
                    </table>
                </div>
            </div>
            <div id="compte" class="tab-pane fade" align="center">
                <div class="row">
                    <h3><s:text name="lobby.compte"/></h3>
                    <div class="col-md-offset-1 col-md-10 formulai-out">
                        <div class="well well-sm formulaire-in">
                            <s:form enctype="multipart/form-data" theme="bootstrap" cssClass="form-horizontal">
                                <div class="form-group">
                                    <div class="col-md-offset-2 col-md-8">
                                        <s:textfield label="%{getText('lobby.compte.identifiant')}" value="%{#session.joueur.identifiant}" readonly="true" disabled="true"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-2 col-md-8">
                                        <s:textfield label="%{getText('lobby.compte.nbParties')}" value="%{#session.joueur.nbVictoires + #session.joueur.nbDefaites}" readonly="true" disabled="true"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-2 col-md-8">
                                        <s:textfield label="%{getText('lobby.compte.partieG')}" value="%{#session.joueur.nbVictoires}" readonly="true" disabled="true"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-2 col-md-8">
                                        <s:textfield label="%{getText('lobby.compte.partieP')}" value="%{#session.joueur.nbDefaites}" readonly="true" disabled="true"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-2 col-md-8">
                                        <s:a href="#" cssClass="btn btn-primary btn-lg" onclick="showModificationPswd();"><s:text name="lobby.modify2.password"/></s:a>
                                    </div>
                                </div>
                            </s:form>
                        </div>
                    </div>
                </div>
                <div class="row" id="modificationPswd" style="visibility: hidden;">
                    <div class="col-md-offset-1 col-md-10 formulai-out">
                        <div class="well well-sm formulaire-in">
                            <s:form action="account" enctype="multipart/form-data" theme="bootstrap" cssClass="form-horizontal">
                                <div class="form-group">
                                    <div class="col-md-offset-2 col-md-8">
                                        <s:textfield type="password" label="%{getText('lobby.label.motDePasse')}"
                                                     name="motDePasse" placeholder="%{getText('lobby.motDePasse')}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-2 col-md-8">
                                        <s:textfield type="password" label="%{getText('lobby.label.motDePasse2')}"
                                                     name="motDePasse2" placeholder="%{getText('lobby.motDePasse2')}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="text-center col-md-offset-2 col-md-8">
                                        <s:submit key="lobby.modify.password" cssClass="btn btn-primary btn-lg"/>
                                    </div>
                                </div>
                            </s:form>
                        </div>
                    </div>
                </div>
            </div>
            <div id="notif" class="tab-pane fade" align="center">
                <h3><s:text name="lobby.notif"/></h3>
                <div class="table-responsive" align="center">
                    <table class="table">
                        <thead>
                        <th><s:text name="lobby.notifType"/></th>
                        <th><s:text name="lobby.joueurs"/></th>
                        </thead>
                        <tbody id="invitations">
                        <%-- Ici se trouvera notre liste des invitations --%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
