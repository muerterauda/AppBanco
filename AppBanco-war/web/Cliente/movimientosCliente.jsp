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
        <link href="${pageContext.request.contextPath}/style/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <main>
            
            <aside class="nav-menu-aside100">
                <header class="background-green">
                    MENU
                </header>
                <nav>
                    <a href="#" class="nav-active ion-clipboard">Movimientos</a>
                    <a href="#" class="ion-ios-list-outline">Transferencias</a>
                    <a href="ayuda" class="ion-help-circled">Ayuda</a>
                    <a href="cerrarSesion" class="ion-log-out">Cerrar sesión</a>
                </nav>
            </aside>
            
            <section>
                <header class="background-black">
                    MOVIMIENTOS
                </header>

                <article>

                    <div class="float-container">
                        <p class="float-item-left">
                            Hola <b><%=cliente.getNombre()%></b>, tienes un saldo de
                            <b><%=saldo%> €</b>.
                        </p>

                        <form name="search-mov" action="#" class="float-item-right">
                            <input type="text" style="width: 200px;" name="concepto" value="<%=filtrar_concepto%>"/>
                            <input type="submit" value="Filtrar" />

                            <div class="float-container">
                                <div class="checkbox-rounded float-item-left">
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

                                <div class="checkbox-rounded float-item-left">
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
                            </div>
                        </form>
                        <div class="float-clear"></div>
                    </div>
                    
                    <table class="full-width">
                        <tr>
                            <th>
                                Fecha 
                            </th>
                            <th>
                                Concepto
                            </th>
                            <th>
                                Importe
                            </th>
                            <th>
                                Saldo 
                            </th>
                        </tr>
                        <% if (movimientos.isEmpty()) { 
                        %>
                        <tr>
                            <td colspan="4">
                                No hay movimientos.
                            </td>
                        </tr>
                        <%
                        } else {
                          for (Movimiento mov : movimientos) { %>
                        <tr>
                            <td>
                                <%=dformat.format(mov.getFecha())%>
                            </td>
                            <td>
                                <%=mov.getConcepto()%>
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
                        <% } } %>
                    </table>
                    
                </article>
                
            </section>
            
        </main>
    </body>
</html>

