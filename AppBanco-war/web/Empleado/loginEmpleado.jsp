<%-- 
    Document   : loginEmpleado
    Created on : 06-abr-2018, 11:20:46
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
        <title>Login Empleado</title>
    </head>
    <body>
        <div id="content-app-medium" >
            <form action="loginEmpleadoServlet" method="post" name="datos" accept-charset="UTF-8">
                <h1 >Acceso Autorizado</h1>
                <hr />               
                <p><span>Numero Empleado: </span><input type="text" name="numeroEmpleado" > </p>
                <p><span> Contraseña: </span><input type="password" name="password"></h4> </p>
                <hr />
                <div class="form-button-center">
                     <p><input type="submit" title="Entrar"> </p>
                </div>
            </form>
            <% String error = (String) request.getAttribute("error");
                if (error != null) {%>
                    <div class="error"> 
                        <p><%=error%></p>
                    </div>
            <%   }%>
            <a href="CambioEmpleado">Inicio cliente</a>
        </div>
    </body>
</html>