<%-- 
    Document   : apuntesEmpleado
    Created on : 06-abr-2018, 11:21:24
    Author     : user
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="AppBanco.entity.Movimiento"%>
<%@page import="AppBanco.entity.Cliente"%>
<%
    Cliente cliente =(Cliente) request.getAttribute("cliente");
    Object numeroCuenta = request.getAttribute("numeroCuenta");
    SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/YY HH:mm:ss");
    Object error = request.getAttribute("error");
    Object saldo = request.getAttribute("saldo");
    List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id="content-app">
            <div id="content">
                <div id="content-header">
                    <form action="MovimientosEmpleado" method="post" name="search-mov" accept-charset="UTF-8">
                        <span>Número de cuenta : </span>
                        <input type="text" style="width: 20em;" name="numeroCuenta" value="<%=numeroCuenta == null ? "" : numeroCuenta%>" />
                        <input type="submit" value="Buscar" />
                        <a href="VolverPrincipalEmpleado" class="button float-right">Pantalla principal</a>
                    </form>

                </div>
                
                <% if (cliente != null) { %>
                <div id="content-main">
                    <table class="full-width">
                        <tr>
                            <th>
                                Concepto
                            </th>
                            <th>
                                Fecha de comprobante
                            </th>
                            <th>
                                Valor
                            </th>
                            <th>
                                Saldo después
                            </th>
                        </tr>
                        <% for (Movimiento mov : movimientos) { %>
                        <tr>
                            <td>
                                <%=mov.getConcepto()%>
                            </td>
                            <td>
                                <%=dformat.format(mov.getFecha())%>
                            </td>
                            
                            <% if (mov.getImporte() >= 0) { %>
                            <td>
                                <%=mov.getImporte()%>
                            </td>
                            <% } else { %>
                            <td style="color: red;">
                                <%=mov.getImporte()%>
                            </td>
                            <% } %>
                            
                            <td>
                                <%=mov.getSaldo()%>
                            </td>
                        </tr>
                        <% } %>
                    </table>
                </div>
                <div id="content-side">
                    <dl>
                        <dt>Saldo : </dt>
                        <dd><em><%=saldo%></em> </dd>
                        <dt>Nombre: </dt>
                        <dd><%=cliente.getNombre()%></dd>
                        <dt>Apellido : </dt>
                        <dd><%=cliente.getApellidos()%></dd>
                        <dt>DNI : </dt>
                        <dd><%=cliente.getDni()%></dd>
                        <dt>Teléfono : </dt>
                        <dd><%=cliente.getTelefono()%></dd>
                        <dt>Dirección : </dt>
                        <dd><%=cliente.getDireccion()%></dd>
                    </dl>
                    <a href="nuevoApunteRedire" class="button">Realizar apunte</a>
                </div>
                <div style="clear: both"></div>
                <% } else { %>
                <div id="content-main">
                    
                    <% if (error == null) { %>
                    <p>Introduce una cuenta del cliente válida.</p>
                    <% } else { %>
                    <div class="error">
                        <%=error%>
                    </div>
                    <% } %>
                </div>
                <div style="clear: both"></div>                                    
                <% } %>
            </div>
        </div>
    </body>
</html>
