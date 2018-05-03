/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import AppBanco.ejb.ClienteFacade;
import AppBanco.ejb.CuentaFacade;
import AppBanco.entity.Cliente;
import AppBanco.entity.Cuenta;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
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
 * @author GRJuanjo
 */
@WebServlet(name = "AltaClienteServlet", urlPatterns = {"/AltaCliente"})
public class altaClienteServlet extends HttpServlet {
    @EJB
    private ClienteFacade clientefa;
    @EJB
    private CuentaFacade cuentafa;
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

        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String DNI = request.getParameter("DNI");
        String email = request.getParameter("email");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        RequestDispatcher rd;
        
         if (!dniCorrecto(DNI) ||isNullOrWhitespace(nombre) || isNullOrWhitespace(apellidos) || isNullOrWhitespace(DNI) || isNullOrWhitespace(email) || isNullOrWhitespace(direccion) || isNullOrWhitespace(telefono) || clientefa.clienteYaExiste(DNI)) {
            rd = (RequestDispatcher) this.getServletContext().getRequestDispatcher("/Empleado/altaClienteForm.jsp");
            request.setAttribute("nombre", nombre== null ? "" : nombre);
            request.setAttribute("apellidos", apellidos== null ? "" : apellidos);
            request.setAttribute("DNI", DNI== null ? "" : DNI);
            request.setAttribute("email", email== null ? "" : email);
            request.setAttribute("direccion", direccion== null ? "" : direccion);
            request.setAttribute("telefono", telefono== null ? "" : telefono);
            rd.forward(request, response);
            return;
        }else {
             Cliente c = clientefa.insertarCliente(nombre, apellidos, DNI, email, direccion, telefono);
            session.setAttribute("cliente", c);
            session.setAttribute("cuenta", clientefa.getCuenta(DNI));
             rd = (RequestDispatcher) this.getServletContext().getRequestDispatcher("/resultAltaClienteFormServlet");
             rd.forward(request, response);
        }
        
        
    }
    
    private static boolean isNullOrWhitespace(String s) {
        return s == null || s.trim().isEmpty();
    }
    
    private static boolean dniCorrecto(String s){
        if(s!=null){
            if(Pattern.matches("[0-9]{8}[A-Z]", s)){
              return true;
            }else{
              return false;
            }
        }
        return false;
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
