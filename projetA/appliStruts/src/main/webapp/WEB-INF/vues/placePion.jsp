<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: jihad
  Date: 02/12/16
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="placePion.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<s:url value="/img/Stratego-header.png"/>">
    <link rel="stylesheet" media="screen" type="text/css" href="<s:url value="/css/style2.css"/>">
    <sj:head/>
    <sb:head/>
    <script type="text/javascript" src="<s:url value="/js/placerPion.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/gestionPlacerPion.js"/>"></script>
</head>
<body onload="refresh();chrono();" style="height: 1400px;">
<s:hidden id="joueur" value="%{#session.quelJoueur}"/>
<s:hidden id="abandon" value="%{getText('loadPlay.error.abandon')}"/>
<s:hidden id="tempsRest" value="%{getText('chrono.tempsRest')}"/>
<s:hidden id="sec" value="%{getText('chrono.sec')}"/>
<s:hidden id="tempsEcoule" value="%{getText('chrono.tempsEcoule')}"/>

<s:url action="abandonner" var="abandonner"/>

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
                <li><s:a href="%{abandonner}"><s:text name="placePion.abandonner"/></s:a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div class="container-fluid" id="info"></div>

<div class="container-fluid" align="center">
    <div class="row">
        <div class="table-responsive">
            <table>
                <s:iterator value="#session.monDamier" var="lignes" status="lig">
                    <tr>
                        <s:iterator value="#lignes" var="lacase" status="col">
                            <td>
                            <s:if test="#lacase.isCaseObstacle">
                                <td>
                                    <img src="./img/CaseLac-<s:property value="#lig.index"/><s:property
                                                value="#col.index"/>.jpg" style="height: 80px; width: 80px;"/>
                                </td>
                            </s:if>
                            <s:elseif test="#lacase.valeur == -1">
                                <td style="cursor: pointer;">
                                    <img class="placer"
                                         src="./img/CaseHerbe.jpg" style="height: 80px; width: 80px;"
                                         id="placer<s:property value="#lig.index"/>-<s:property value="#col.index"/>"
                                         onclick="placer(this, <s:property value="#lig.index"/> , <s:property value="#col.index"/>);"/>
                                </td>
                            </s:elseif>
                            <s:elseif test="#lacase.valeur == -2">
                                <td>
                                    <img src="./img/CaseHerbe.jpg"
                                         style="height: 80px; width: 80px;"/>
                                </td>
                            </s:elseif>
                            <s:else>
                                <td>
                                    <img src="./img/j1_pion_cachee.bmp"
                                         style="height: 80px; width: 80px;"/>
                                </td>
                            </s:else>
                            </td>
                        </s:iterator>
                    </tr>
                </s:iterator>
            </table>
            <div class="text-center" id="envoyer" style="visibility: hidden; padding-top: 10px; padding-bottom: 10px;">
                <button onclick="envoyer();" class="btn btn-primary btn-lg"><s:text name="placePion.envoyer"/></button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="table-responsive">
            <table>
                <tr>
                    <s:iterator value="#session.pionsAPlacer" var="pion" status="i">
                    <td style="cursor: pointer;" id="Aplacer<s:property value="#i.index"/>">
                        <img class="Aplacer" src="./img/<s:property value="#session.quelJoueur"/>_pion_<s:property value="#pion.valeur"/>.bmp"
                             style="height: 80px; width: 80px;"
                             id="<s:property value="#pion.valeur"/>"
                             onclick="deplacer(this);"/>
                    </td>
                    <s:if test="(#i.index + 1) % 10 == 0">
                </tr><tr>
                </s:if>
                </s:iterator>
            </tr>
            </table>
            <div class="text-center" id="random" style="padding-top: 10px;">
                <button onclick="placementRandom();" class="btn btn-primary btn-lg"><s:text name="placePion.random"/></button>
            </div>
        </div>
    </div>
</div>


</body>
</html>