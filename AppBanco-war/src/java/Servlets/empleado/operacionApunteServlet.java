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
import java.io.IOException;
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
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion= request.getSession();
        Empleado em=(Empleado)sesion.getAttribute("empleado");
        Cuenta cuenta=(Cuenta)sesion.getAttribute("cuenta");
        Cliente cliente=(Cliente)sesion.getAttribute("cliente");
        Double cantidad=null;
        try{
        cantidad=Double.parseDouble(request.getParameter("cantidad"));
        String operacion=request.getParameter("operacion");
        try{
            ConectorMovimiento.nuevoApunte(operacion,em,cuenta,cantidad);
            }catch (Exception e){
                request.setAttribute("error", "Cantidad erronea");
            }
        }catch(Exception e){
            request.setAttribute("error", "Cantidad erronea");
        }
        if(request.getAttribute("error")!=null){
            request.setAttribute("cliente", cliente);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Empleado/operacionApunte.jsp");
            rd.forward(request, response);
        }
        request.setAttribute("cliente", cliente);
        request.setAttribute("numeroCuenta", cuenta.getNumeroStr());
        request.setAttribute("movimientos", ConectorMovimiento.buscarPorCuentaOrderByFechaDesc(cuenta, true, true, null));
        request.setAttribute("saldo", (Double)cuenBD.getSaldoCuenta(cuenta.getNumeroStr()));
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Empleado/apuntesEmpleado.jsp");
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
