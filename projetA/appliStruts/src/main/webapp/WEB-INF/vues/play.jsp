<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: jihad
  Date: 27/11/16
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="play.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="./img/Stratego-header.png">
    <link rel="stylesheet" media="screen" type="text/css" href="<s:url value="/css/style2.css"/>">
    <sj:head/>
    <sb:head/>
    <script type="text/javascript" src="<s:url value="/js/play.js"/>"></script>
</head>
<body onload="chrono();" style="height: 1400px">
    <s:hidden id="tempsRest" value="%{getText('chrono.tempsRest')}"/>
    <s:hidden id="sec" value="%{getText('chrono.sec')}"/>
    <s:hidden id="tempsEcoule" value="%{getText('chrono.tempsEcoule2')}"/>
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
        <table>
            <s:iterator value="#session.monDamier" var="lignes" status="lig">
                <tr>
                    <s:iterator value="#lignes" var="lacase" status="col">
                        <td>
                        <%--CASE OBSTACLE, ON NE FAIT RIEN AVEC--%>
                        <s:if test="#lacase.isCaseObstacle">
                            <td>
                                <img src="./img/CaseLac-<s:property value="#lig.index"/><s:property
                                                value="#col.index"/>.jpg" style="height: 80px; width: 80px;"/>
                            </td>
                        </s:if>

                        <%--CASE VIDE : BESOIN D'UN BOOLEAN POUR ACCESSIBILITE (Init : faux) --%>
                        <s:elseif test="#lacase.isCaseVide">
                            <td>
                                <img src="./img/CaseHerbe.jpg" style="height: 80px; width: 80px;cursor: pointer;" onclick="deplacerPion(<s:property value="#lig.index"/> , <s:property value="#col.index"/>);"/>
                            </td>
                        </s:elseif>

                        <%--CASE PION : CLIQUE SUR NOS PION POUR CHARGER LA TAB DE BOOL, ET SUR LES PIONS ADVERSE, COMPORTEMENT IDENTIQUE A CASE VIDE--%>
                        <s:elseif test="#lacase.isCasePion">
                            <td>
                                <s:if test="#session.joueur.equals(#lacase.joueur)">
                                    <img src="./img/<s:property value="#session.quelJoueur"/>_pion_<s:property value="#lacase.valeur"/>.bmp"
                                         onclick="choisirPion(this, <s:property value="#lig.index"/> , <s:property value="#col.index"/>)" style="height: 80px; width: 80px;cursor: pointer;"/>
                                </s:if>
                                <s:else>
                                    <s:if test="#session.quelJoueur.equals('j1')">
                                        <img src="./img/j2_pion_cachee.bmp" id="<s:property value="#lig.index"/>-<s:property value="#col.index"/>" onclick="deplacerPion(<s:property value="#lig.index"/> , <s:property value="#col.index"/>);" style="height: 80px; width: 80px; cursor: pointer"/>
                                    </s:if>
                                    <s:else>
                                        <img src="./img/j1_pion_cachee.bmp" id="<s:property value="#lig.index"/>-<s:property value="#col.index"/>" onclick="deplacerPion(<s:property value="#lig.index"/> , <s:property value="#col.index"/>);" style="height: 80px; width: 80px; cursor: pointer;"/>
                                    </s:else>
                                </s:else>
                            </td>
                        </s:elseif>
                        </td>
                    </s:iterator>
                </tr>
            </s:iterator>
        </table>
    </div>

    <div class="container-fluid" align="center">
        <h4><s:text name="play.restants"/></h4>
        <table>
            <tr>
                <s:iterator value="#session.pionsATuer" var="pion" status="i">
                    <s:if test="#session.quelJoueur.equals('j1')">
                        <td><img src="./img/j2_pion_<s:property value="#pion.valeur"/>.bmp" style="height: 80px; width: 80px;"></td>
                    </s:if>
                    <s:else>
                        <td><img src="./img/j1_pion_<s:property value="#pion.valeur"/>.bmp" style="height: 80px; width: 80px;"></td>
                    </s:else>
                    <s:if test="(#i.index + 1) % 10 == 0">
                        </tr><tr>
                    </s:if>
                </s:iterator>
            </tr>
        </table>
    </div>

</body>
</html>
