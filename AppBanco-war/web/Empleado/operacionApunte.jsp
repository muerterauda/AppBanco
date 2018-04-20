<%-- 
    Document   : operacionApunte
    Created on : 06-abr-2018, 11:21:56
    Author     : user
--%>

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
               <h2>Nombre, DNI, Numero de cuenta</h2>
               <hr/>
               <p><span>Cantidad: </span><input name="cantidad"></p>
               <p><span>Accion</span>
               <input type="radio" name="operacion" value="I" checked/>Ingreso
               <input type="radio" name="operacion" value="R" />Retirada
               </p>
               <hr/>
                <div class="form-button-right">
                    <a href="../MovimientosEmpleado"><input type="button" value="Cancelar"></a>
                    <input type="submit" value="Realizar Apunte">
                    <div style="clear: both;"></div>
                </div>
           </form>
        </div>
    </body>
</html>
