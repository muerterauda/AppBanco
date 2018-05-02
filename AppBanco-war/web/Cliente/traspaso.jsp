<%-- 
    Document   : transferencias.jsp
    Created on : 23-abr-2018, 10:43:51
    Author     : elias
--%>

<%@page import="AppBanco.entity.Cuenta"%>
<%@page import="AppBanco.entity.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <title>Transferencias</title>
    </head>
    <body>
        <%
        Cliente cliente= (Cliente) session.getAttribute("cliente");
        Cuenta cuenta= (Cuenta) session.getAttribute("cuenta");
        
        %>
        <div id="content-app" >
            <jsp:include page="header.jsp" flush="true">
                <jsp:param name="title" value="Movimientos" />
            </jsp:include>
            <div id="content">
                <h3>Bienvenido: <%= cliente.getNombre() %> <%= cliente.getApellidos() %></h3>
                <h3>Saldo: <%= request.getAttribute("saldo") %> </h3>
            <form name="confirmTraspaso" action="confirmTraspasoServlet" >
                <p><span> Cuenta destino: </span><input type="text" name ="cuentaDest"></p>
                <p><span> Cantidad:  </span><input type="text" name ="cantidad"</p>
                <hr />
                 <% String error=(String)request.getAttribute("error");
                      if(error!=null){ %>
                         <div class="error"> 
                             <p><%=error%></p>
                        </div>
                   <%   } %>    
                <div class="form-button-center">
                    <input type="submit" style="margin-left: 450px;" value="Enviar">
                    <div style="clear: both;"></div>
                </div>
            </form>
        </div>
    </body>
</html>
