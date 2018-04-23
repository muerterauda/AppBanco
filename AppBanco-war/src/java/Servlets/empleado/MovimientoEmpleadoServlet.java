package Servlets.empleado;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import AppBanco.ejb.CuentaFacade;
import AppBanco.ejb.MovimientoFacade;
import AppBanco.entity.Cliente;
import AppBanco.entity.Cuenta;
import AppBanco.entity.Empleado;
import AppBanco.entity.Movimiento;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vikou
 */
@WebServlet(name="MovimientoEmpleadoServlet", urlPatterns = {"/MovimientosEmpleado"})
public class MovimientoEmpleadoServlet extends HttpServlet {
    
    @EJB
    private MovimientoFacade movBD;
    @EJB
    private CuentaFacade cuenBD;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Cliente cliente = (Cliente)session.getAttribute("cliente");
        Cuenta cuenta = (Cuenta)session.getAttribute("cuenta");
        List<Movimiento> movimientos=null;
        Empleado empleado = (Empleado) session.getAttribute("empleado");
        double saldo = 0;
        String numeroCuenta=null;
        if(cliente!=null&&cuenta!=null){
            numeroCuenta=cuenta.getNumeroStr();
            movimientos=movBD.buscarPorCuentaOrderByFechaDesc(cuenta, true, true, null);
            saldo= cuenBD.getSaldoCuenta(cuenta.getNumeroStr());
            request.setAttribute("saldo", saldo);
            request.setAttribute("movimientos", movimientos);
            request.setAttribute("cliente", cliente);
        }else{
         numeroCuenta=(String)request.getParameter("numeroCuenta");
         if(numeroCuenta!=null&&!numeroCuenta.equals("")){
            cuenta= cuenBD.findCuentaNumeroStr(numeroCuenta);
            if(cuenta!=null){
                movimientos=movBD.buscarPorCuentaOrderByFechaDesc(cuenta, true, true, null);
                cliente = cuenta.getCliente();
                saldo= cuenBD.getSaldoCuenta(cuenta.getNumeroStr());
                session.setAttribute("cliente", cliente);
                session.setAttribute("cuenta", cuenta);
                session.setAttribute("ListaMovimientos", movimientos);
                request.setAttribute("saldo", saldo);
                request.setAttribute("movimientos", movimientos);
                request.setAttribute("cliente", cliente);
                request.removeAttribute("error");
            }else{
                session.removeAttribute("cliente");
                session.removeAttribute("cuenta");
                session.removeAttribute("ListaMovimientos");
                request.removeAttribute("saldo");
                request.removeAttribute("movimientos");
                request.removeAttribute("cliente");
                String error="Error: Numero de cuenta no encontrado";
                request.setAttribute("error", error);
            }
        }
        }
        request.setAttribute("numeroCuenta", numeroCuenta);
        RequestDispatcher dispacher = getServletContext().getRequestDispatcher("/Empleado/apuntesEmpleado.jsp");
        dispacher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
