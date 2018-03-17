<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: jihad
  Date: 13/12/16
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="notPlay.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="./img/Stratego-header.png">
    <link rel="stylesheet" media="screen" type="text/css" href="<s:url value="/css/style2.css"/>">
    <sj:head/>
    <sb:head/>
    <script type="text/javascript" src="<s:url value="/js/gestionNotPlay.js"/>"></script>
</head>
<body onload="refresh();">
    <s:hidden id="joueur" value="%{#session.joueur.identifiant}"/>
    <s:hidden id="finPartie" value="%{getText('notPlay.finPartie')}"/>
    <s:url action="abandonner" var="abandonner"/>
    <s:url action="quitterObservation" var="quitObs"/>

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
                <s:a href="#" class="navbar-brand"><s:text name="pre-game.stratego"/></s:a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <s:if test="#session.joueur.statut.toString() == 'OBSERVING'">
                        <li><s:a href="%{quitObs}"><s:text name="notPlay.quitObs"/></s:a></li>
                    </s:if>
                    <s:else>
                        <li><s:a href="%{abandonner}"><s:text name="placePion.abandonner"/></s:a></li>
                    </s:else>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <div class="container-fluid" id="info"></div>

    <div class="container-fluid" align="center" id="damier"></div>
</body>
</html>
