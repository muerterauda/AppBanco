<%-- 
    Document   : loginCliente
    Created on : 06-abr-2018, 11:16:07
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/style/estilo_banco.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
        <title>Login Cliente</title>
    </head>
    <body>
        
        <main id="wallpaper" class="fondo-banco-dia">
            <aside class="nav-menu-login background-white opacity-80">
                <header class="background-green">
                    BANCO
                </header>
                <form method="post" action="loginClienteServlet" name="datos">
                    <p>
                        <span>Nombre :</span>
                        <input type="text" name="dni">
                    </p>
                    <p>
                        <span>Contraseña :</span>
                        <input type="password" name="password">
                    </p>
                    <input type="submit" value="Log in" />
                    <a href="Cambio" class="button">Empleado</a> 
                    <% String error = (String) request.getAttribute("error");
                        if (error != null) {%>
                    <div class="error"> 
                        <p><%=error%></p>
                    </div>
                    <%   }%>
                </form>
            </aside>

        </main>
    </body>
</html>

