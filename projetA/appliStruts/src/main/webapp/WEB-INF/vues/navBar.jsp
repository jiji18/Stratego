<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Antho
  Date: 30/11/2016
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<s:url action="index" var="index"/>
<s:url action="gotoLogin" var="login"/>
<s:url action="gotoRegister" var="register"/>

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
            <s:a href="%{index}" class="navbar-brand">
                <span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;
                <s:text name="index.accueil"/></s:a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li id="loginId" class="li">
                    <s:a href="%{login}">
                        <span class="glyphicon glyphicon-log-in"></span>&nbsp;&nbsp;
                        <s:text name="index.connexion"/>
                    </s:a>
                </li>
                <li id="registerId" class="li">
                    <s:a href="%{register}">
                        <span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;
                        <s:text name="index.inscription"/>
                        <span class="sr-only">(current)</span>
                    </s:a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
