/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.empleado;

import AppBanco.ejb.EmpleadoFacade;
import AppBanco.entity.Empleado;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "loginEmpleadoServlet", urlPatterns = {"/loginEmpleadoServlet"})
public class loginEmpleadoServlet extends HttpServlet {

    @EJB
    private EmpleadoFacade ConectorEmpleado;

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
        int idEmpleado = -1;
        String password = "";

        try {

            if (request.getParameter("numeroEmpleado").equals("")) {
                request.setAttribute("error", "Error: El usuario esta vacio");
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Empleado/loginEmpleado.jsp");
                rd.forward(request, response);
            }

            if (request.getParameter("password").equals("")) {
                request.setAttribute("error", "Error: La contraseña está vacia");
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Empleado/loginEmpleado.jsp");
                rd.forward(request, response);
            }

            if (request.getParameter("numeroEmpleado") != null || !request.getParameter("numeroEmpleado").equals("")) {
                idEmpleado = Integer.parseInt(request.getParameter("numeroEmpleado"));
                password = request.getParameter("password");
            }

            Empleado empleado = ConectorEmpleado.find(idEmpleado);
            if (empleado == null || !empleado.getContrasenya().equals(password)) {
                if (empleado == null) {
                    request.setAttribute("error", "Error: No existe el usuario");
                } else {
                    request.setAttribute("error", "Error: La contrase&ntilde;a no coincide");
                }
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Empleado/loginEmpleado.jsp");
                rd.forward(request, response);
            } else {
                //Metemos al empleado en la sesion para poder realizar apuntes con el 
                HttpSession session= request.getSession();
                session.setAttribute("empleado", empleado);
                
                request.removeAttribute("error");
                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Empleado/principalEmpleado.jsp");
                rd.forward(request, response);
            }
            
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Error: El usuario no es un numero");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Empleado/loginEmpleado.jsp");
            rd.forward(request, response);

        }
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
