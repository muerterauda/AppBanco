<%-- 
    Document   : operacionApunte
    Created on : 06-abr-2018, 11:21:56
    Author     : user
--%>
<%@page import="AppBanco.entity.Cuenta"%>
<%@page import="AppBanco.entity.Cliente"%>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    String error=(String) request.getAttribute("error");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo Apunte</title>
       <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
    </head>
    <body>
        <div id="content-app-medium">
           <form method="post" action="operacionApunte" name="datos" accept-charset="UTF-8">
               <h2><%=cliente.getNombre()%>,<%=cliente.getDni()%></h2>
               <hr/>
               <p><span>Cantidad: </span><input name="cantidad"></p>
               <p><span>Accion</span>
               <input type="radio" name="operacion" value="I" checked/>Ingreso
               <input type="radio" name="operacion" value="R" />Retirada
               </p>
               <hr/>
               <%
                     if (error != null) {%>
                    <div class="error"> 
                        <p><%=error%></p>
                    </div>
                   <%   }%>
                <div class="form-button-right">
                    <a href="MovimientosEmpleado"><input type="button" value="Cancelar"></a>
                    <input type="submit" value="Realizar Apunte">
                </div>
               <div style="clear: both;"></div>
           </form>
        </div>
    </body>
</html>
