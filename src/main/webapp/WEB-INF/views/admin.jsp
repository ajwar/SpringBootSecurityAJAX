<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta login="_csrf" content="${_csrf.token}"/>
    <!-- default header login is X-CSRF-TOKEN -->
    <meta login="_csrf_header" content="${_csrf.headerName}"/>
    <span id="token" style="display: none">${_csrf.token}</span>
    <span id="header" style="display: none">${_csrf.headerName}</span>
    <link href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/admin.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/static/css/flip_table.css"/>" rel="stylesheet"/>
    <script src="<c:url value="/static/js/jquery-3.2.1.js" />"></script>
    <script src="<c:url value="https://npmcdn.com/tether@1.2.4/dist/js/tether.min.js"/>"></script>
    <script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/static/js/mainAppJs.js" />"></script>
    <title>Admin page</title>
    <%--<sec:csrfMetaTags />--%>
</head>
<body>
<a href="" id="createFile"></a>
<span id="interval" style="display: none">${interval}</span>

Dear <strong>${user}</strong>, Welcome to Admin Page.

<a href="<c:url value="/logout" />">Logout</a>
<div id="leftDiv" class="table-responsive container-fluid">
    <table class="table table-bordered " style="width: 100%" id="serverTable">
        <thead class="thead-inverse">
        <tr>
            <th id="numServers">№</th>
            <th id="nameServers">Name Server</th>
            <th id="version">Version</th>
            <th id="port">Port</th>
            <th id="adminPort">Admin port</th>
            <th id="count">Count active channels</th>
            <th id="memory">Memory, Mb</th>
            <th id="onlineServers">Station</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="info" items="${list}">
            <tr <c:if test="${info.station eq '0'}">bgcolor="red"</c:if>>
                <td>${info.index}</td>
                <td>${info.name}</td>
                <td>${info.version}</td>
                <td>${info.port}</td>
                <td>${info.adminPort}</td>
                <td>${info.count}</td>
                <td>${info.memory}</td>
                <td>${info.station}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <button type="button" onclick="updateTableInfoServers()">Update</button>

    <select class="form-control" id="cmdSelect" onchange="selectCmd(this)">
        <option value="get_total_info_system">Get total info system</option>
        <option value="get_options_server">Get options server</option>
        <option value="send_options_server">Send options server</option>
        <option value="stop">Stop server</option>
        <option value="run">Run server</option>
    </select>
    <span id="textServers" style="display: none">Servers:</span>
    <select class="form-control" id="indexSelect" style="display: none">
        <c:forEach var="info" items="${list}">
            <option value="${info.index}">${info.index}</option>
        </c:forEach>

    </select>
    <div>
        <form method="post" name="upload" id="sendFileForm" style="display: none" enctype="multipart/form-data">
            <input type="file" id="sendFileSetup" name="sendFileSetup" value="Choose file:">
        </form>
    </div>
    <button type="button" class="btn btn-outline-success" onclick="runCmd('${user}')">Run the command</button>
</div>

<div id="rightDiv" class="table-responsive container-fluid">
    <table class="table rtable rtable--flip" id="dataFromServer">
        <thead>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
</body>
</html>