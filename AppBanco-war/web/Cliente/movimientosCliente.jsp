<%-- 
    Document   : movimientosCliente
    Created on : 06-abr-2018, 11:19:00
    Author     : user
--%>
<%@page import="java.util.List"%>
<%@page import="AppBanco.entity.Movimiento"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="AppBanco.entity.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    Object saldo = request.getAttribute("saldo");
    SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/YY HH:mm:ss");
    List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
    String filtrar_concepto = (String) request.getAttribute("concepto");
    Object aux = request.getAttribute("ingresos");
    boolean ingresos_checked = ((Boolean) aux).booleanValue();
    aux = request.getAttribute("gastos");
    boolean gastos_checked = ((Boolean) aux).booleanValue();
    
%>

<html
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id="content-app">
            <jsp:include page="header.jsp" flush="true">
                <jsp:param name="title" value="Movimientos" />
            </jsp:include>
            <div id="content">
                <div id="content-header">
                    <p>
                        Hola <%=cliente.getNombre()%>, tienes un saldo de
                         <%=saldo%>.
                    </p>
                </div>
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
                    <form name="search-mov" action="Movimientos">
                        <input type="text" name="concepto" value="<%=filtrar_concepto%>"/>
                        <input type="submit" value="Filtrar" />
                        <div class="checkbox-rounded">
                            <label class="switch">
                                <% if (ingresos_checked) { %>
                                <input type="checkbox" name="ingresos" value="si" checked/>
                                <% } else { %>
                                <input type="checkbox" name="ingresos" value="si" />
                                <% } %>
                                <span class="slider round"></span>
                            </label>
                            <p>Ingresos</p>
                        </div>
                        <div class="checkbox-rounded">
                            <label class="switch">
                                <% if (gastos_checked) { %>
                                <input type="checkbox" name="gastos" value="si" checked/>
                                <% } else { %>
                                <input type="checkbox" name="gastos" value="si" />
                                <% } %>
                                <span class="slider round"></span>
                            </label>
                            <p>Gastos</p>
                        </div>
                    </form>
                </div>
                <div style="clear: both;"></div>
            </div>
        </div>
    </body>
</html>

