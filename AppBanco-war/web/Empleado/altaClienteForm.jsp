<%-- 
    Document   : altaClienteForm
    Created on : 06-abr-2018, 11:23:22
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String nombre = (String) request.getAttribute("nombre");
    String apellidos = (String) request.getAttribute("apellidos");
    String DNI = (String) request.getAttribute("DNI");
    String email = (String) request.getAttribute("email");
    String direccion = (String) request.getAttribute("direccion");
    String telefono = (String) request. getAttribute("telefono");
%>
<html
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <title>Alta de cliente</title>
    </head>
    <body>
        <div id="content-app-medium">
            <form name="altacliente" action="AltaCliente" >
                <h1>Nuevo cliente</h1>
                <hr />
                <p><span> Nombre: </span><input type="text" name ="nombre"  value = <%=nombre%>></p>
                <p><span> Apellidos: </span><input type="text" name ="apellidos" value = <%=apellidos%>></p>
                <p><span> DNI: </span><input type="text" name ="DNI" value = <%= DNI%>></p>
                <p><span> Email: </span><input type="email" name ="email" value = <%= email%>></p>
                <p><span> Dirección:</span><input type="text" name ="direccion" value = <%= direccion%>></p>
                <p><span> Teléfono: </span><input type="text" name ="telefono" value = <%= telefono%>></p>
                <hr />
                <div class="form-button-right">
                    <a href="VolverPrincipalEmpleado" class="button">Cancelar</a>
                    <input type="submit" value="Registrar">
                    <div style="clear: both;"></div>
                </div>
            </form>
        </div>
    </body>
</html>

