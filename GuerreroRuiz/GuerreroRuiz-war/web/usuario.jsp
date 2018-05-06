<%-- 
    Document   : usuario
    Created on : 03-may-2018, 11:18:33
    Author     : GRJuanjo
--%>

<%@page import="GuerreroRuiz.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    Usuario u = (Usuario)request.getAttribute("usuario");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <h1><%=u.getNombre()%></h1>
        <h4><%=u.getDescripcion() %></h4>
        <h4>País: <%=u.getPais().getIdpais() %></h4>
        <h4><%=u.getUrl()%></h4>
        <h4>Fecha de inicio: <%=u.getFecha()%></h4>
        <h4>Seguidores: <%=u.getUsuarioList1().size() %></h4>
        <h4>Seguidos: <%=u.getUsuarioList().size() %></h4>
        <h4>Me gusta: <%=u.getTuitList1().size() %></h4>
        
    </body>
</html>
