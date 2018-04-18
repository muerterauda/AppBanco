/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import AppBanco.ejb.ClienteFacade;
import AppBanco.ejb.CuentaFacade;
import AppBanco.ejb.MovimientoFacade;
import AppBanco.entity.Cliente;
import AppBanco.entity.Cuenta;
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
@WebServlet(name="MovimientoClienteServlet", urlPatterns = {"/Movimientos"})
public class MovimientoClienteServlet extends HttpServlet {

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
        Cliente cliente = null;
        Cuenta cuenta = null;
        List<Movimiento> movimientos;
        int saldo = 0;
        HttpSession session = request.getSession();
        
        // Obtengo la sesión del cliente.
        cliente = (Cliente) session.getAttribute("cliente");
        cuenta = (Cuenta) session.getAttribute("cuenta");
        
        // Si el cliente tiene sesión ...
        if (cliente != null) {
            String concepto = request.getParameter("concepto");
            String ingresos = request.getParameter("ingresos");
            String gastos = request.getParameter("gastos");
            
            cuenta = cliente.getCuentaList().get(0);
            movimientos = movBD.buscarPorCuentaOrderByFechaDesc(cuenta, ingresos != null, gastos != null, concepto == null ? "" : concepto);
            
            saldo = cuenBD.getSaldoCuenta(cuenta.getNumeroCuenta());
            
            request.setAttribute("cliente", cliente);
            request.setAttribute("movimientos", movimientos);
            request.setAttribute("saldo", saldo);
            request.setAttribute("concepto", concepto == null ? "" : concepto);
            request.setAttribute("ingresos", new Boolean(ingresos != null));
            request.setAttribute("gastos", new Boolean(gastos != null));
        }
        
        RequestDispatcher dispacher = getServletContext().getRequestDispatcher("/Cliente/movimientosCliente.jsp");
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
