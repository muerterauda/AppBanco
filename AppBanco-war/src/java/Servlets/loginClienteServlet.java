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
@WebServlet(name = "loginClienteServlet", urlPatterns = {"/loginClienteServlet"})
public class loginClienteServlet extends HttpServlet {
    @EJB
    private ClienteFacade Conectorcliente;
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
          String dni= request.getParameter("dni");
          String password= request.getParameter("password");
          Cliente cliente=Conectorcliente.find(dni);
          Cuenta cuenta=null;
          if(cliente!=null){
              cuenta=cliente.getCuentaList().get(0);
          }
          if(cliente==null||!cliente.getContrasenya().equals(password)||cuenta==null){
              if(cliente==null){
                   request.setAttribute("error", "Error: No existe el usuario");
              }else if(!cliente.getContrasenya().equals(password)){
                  request.setAttribute("error", "Error: La contrase&ntilde;a no coincide");
              }else{
                  request.setAttribute("error", "Error: Error con la cuenta contacta con el banco");
              }
              RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Cliente/loginCliente.jsp");
              rd.forward(request, response);
          }else{
              HttpSession sesion= request.getSession();
              
              sesion.setAttribute("cuenta", cuenta);
              sesion.setAttribute("cliente", cliente);
              request.removeAttribute("error");
              RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/Cliente/movimientosCliente.jsp");
              //RequestDispatcher rd = this.getServletContext().getRequestDispatcher("movimientosClienteServlet");
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
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
    public void doPost(HttpServletRequest request, HttpServletResponse response)
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
