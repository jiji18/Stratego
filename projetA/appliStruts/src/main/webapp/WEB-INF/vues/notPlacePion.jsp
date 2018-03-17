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
    <title><s:text name="notPlacePion.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="./img/Stratego-header.png">
    <link rel="stylesheet" media="screen" type="text/css" href="<s:url value="/css/style2.css"/>">
    <sj:head/>
    <sb:head/>
    <script type="text/javascript" src="<s:url value="/js/gestionNotPlacerPion.js"/>"></script>
</head>
<body onload="refresh();">
    <s:hidden id="abandon" value="%{getText('loadPlay.error.abandon')}"/>
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

    <div class="row">
        <div class="container-fluid">
            <div align="center" class="embed-responsive embed-responsive-16by9">
                <iframe class="embed-responsive-item" src="http://www.youtube.com/embed/nq0xn5fOTz4?autoplay=0" frameborder="0" height="400px"></iframe>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="container-fluid">
            <iframe class="col-xs-offset-1 col-xs-10 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10" src="http://fr.wikipedia.org/wiki/Stratego" frameborder="0" height="800px"></iframe>
        </div>
    </div>
</body>
</html>
