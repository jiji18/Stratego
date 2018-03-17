<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Antho
  Date: 21/11/2016
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="index.accueil"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="./img/Stratego-header.png">
    <sj:head/>
    <sb:head/>
    <link rel="stylesheet" media="screen" type="text/css" href="<s:url value="/css/style.css"/>">
</head>
<body>

<%@include file="navBar.jsp" %>

<div class="container-fluid">
    <div class="jumbotron">
        <h1><s:text name="index.stratego"/></h1>
        <p><s:text name="index.presentation"/></p>
    </div>
</div>
</body>
</html>
