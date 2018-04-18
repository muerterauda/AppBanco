package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import AppBanco.ejb.CuentaFacade;
import AppBanco.ejb.EmpleadoFacade;
import AppBanco.ejb.MovimientoFacade;
import AppBanco.ejb.OperacionFacade;
import AppBanco.entity.Cuenta;
import AppBanco.entity.Empleado;
import AppBanco.entity.Movimiento;
import AppBanco.entity.Operacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
 * @author elias
 */
@WebServlet(name="operacionApunteServlet",urlPatterns = {"/operacionApunte"})
public class operacionApunteServlet extends HttpServlet {

    @EJB
    private MovimientoFacade ConectorMovimiento;
    
    @EJB
    private EmpleadoFacade ConectorEmpleado;
    
    @EJB
    private CuentaFacade ConectorCuenta;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion= request.getSession();
        //Empleado em=(Empleado)sesion.getAttribute("empleado");
        Empleado em= ConectorEmpleado.find(1);
        
        //Cuenta cuenta=(Cuenta)sesion.getAttribute("cuenta");
        
        Cuenta cuenta= ConectorCuenta.find("AAAA");
        Double cantidad=Double.parseDouble(request.getParameter("cantidad"));
        String operacion=request.getParameter("operacion");
        ConectorMovimiento.nuevoApunte(operacion,em,cuenta,cantidad.intValue()); //cambiar futuro por int
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Empleado/loginEmpleado.jsp");
        //RequestDispatcher rd = this.getServletContext().getRequestDispatcher("apuntesEmpleadoServlet");
        rd.forward(request, response);
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
