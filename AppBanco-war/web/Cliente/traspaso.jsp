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
            <h1>Transferencias</h1>
            <div class="datosTransferencia" >
                <h2>Bienvenido: <%= cliente.getNombre() %> <%= cliente.getApellidos() %></h2>
                <h2>Saldo:<%= request.getAttribute("saldo") %> </h2>
            </div>
            
            <form action="confirmTraspasoServlet">    
                <h3>Cuenta destino: </h3> 
                <input type="text" name="cuentaDest" >
                <h3>Cantidad: </h3>
                <input type="text" name="cantidad">
                <input type="submit">
                
            </form>
                <% String error=(String)request.getAttribute("error");
                      if(error!=null){ %>
                         <div class="error"> 
                             <p><%=error%></p>
                        </div>
                   <%   } %>    
        </div>
    </body>
</html>
