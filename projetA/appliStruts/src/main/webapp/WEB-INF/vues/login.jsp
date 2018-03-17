<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Antho
  Date: 06/10/2016
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="login.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="./img/Stratego-header.png">
    <sj:head/>
    <sb:head/>
    <link rel="stylesheet" media="screen" type="text/css" href="<s:url value="/css/style.css"/>">
</head>
<body>

<%@include file="navBar.jsp" %>

<script>
    var element = document.getElementById("loginId");
    element.classList.add("active");
</script>

<s:actionerror theme="bootstrap"/>

<div class="container" id="addremove">
    <div class="row">
        <div class="col-md-offset-2 col-md-8 text-center title">
            <h1><s:text name="login.title"/></h1>
        </div>
        <div class="col-md-12 formulaire-out">
            <div class="well well-sm formulaire-in">
                <s:form action="login" enctype="multipart/form-data" theme="bootstrap" cssClass="form-horizontal">
                    <div class="form-group">
                        <div class="col-md-offset-2 col-md-8">
                            <s:textfield label="%{getText('login.label.identifiant')}" name="identifiant"
                                         placeholder="%{getText('login.identifiant')}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-offset-2 col-md-8">
                            <s:textfield type="password" label="%{getText('login.label.motDePasse')}" name="motDePasse"
                                         placeholder="%{getText('login.motDePasse')}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="text-center col-md-offset-2 col-md-8">
                            <s:submit key="login.connecter" cssClass="btn btn-primary btn-lg"/>
                        </div>
                    </div>
                </s:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
