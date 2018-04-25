/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import AppBanco.ejb.ClienteFacade;
import AppBanco.ejb.CuentaFacade;
import AppBanco.ejb.MovimientoFacade;
import AppBanco.ejb.OperacionFacade;
import AppBanco.entity.Cuenta;
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
 * @author GRJuanjo
 */
@WebServlet(name="confirmTraspasoServlet",urlPatterns = {"/confirmTraspasoServlet"})
public class confirmTraspasoServlet extends HttpServlet {
    @EJB
    ClienteFacade clientef;
    
    @EJB
    CuentaFacade cuentaf;
    
    @EJB
    MovimientoFacade movimientof;
    
    @EJB
    OperacionFacade operacionf;
    
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
        
        if(request.getParameter("cuentaDest")==null || request.getParameter("cuentaDest").equals("")){
            request.setAttribute("error", "El numero de cuenta es null o está vacio");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/traspasoServlet");
            rd.forward(request, response);
        }
        
        if(request.getParameter("cantidad")==null || request.getParameter("cantidad").equals("") ){
            request.setAttribute("error", "La cantidad es null o esta vacia");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/traspasoServlet");
            rd.forward(request, response);
        }
        
        double cantidad=0;
        
        try{
            
            cantidad=Double.parseDouble(request.getParameter("cantidad"));
            
        }catch(NumberFormatException ex){
            request.setAttribute("error", "La cantidad no es un numero");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/traspasoServlet");
            rd.forward(request, response);
        }
        
        if(cantidad<0){
            request.setAttribute("error", "La cantidad es menor que cero");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/traspasoServlet");
            rd.forward(request, response);
        }
        
        HttpSession session=request.getSession();
        Cuenta cuentaDestino= cuentaf.findCuentaNumeroStr(request.getParameter("cuentaDest"));
        Cuenta cuentaOrigen= (Cuenta) session.getAttribute("cuenta");
        double saldoOrigen= cuentaf.getSaldoCuenta(cuentaOrigen.getNumeroStr());
        
        if(cuentaDestino==null){
            request.setAttribute("error", "La cuenta introducida no es valida");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/traspasoServlet");
            rd.forward(request, response);
        }
        
        if(saldoOrigen-cantidad<0){
            request.setAttribute("error", "La cantidad introducida es mayor al saldo que tienes");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/traspasoServlet");
            rd.forward(request, response);
        }
        
        if(cuentaDestino.getNumeroStr().equals(cuentaOrigen.getNumeroStr())){
            request.setAttribute("error", "La cuenta origen es la misma que la destino");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/traspasoServlet");
            rd.forward(request, response);
        }
        
        if(cantidad==0){
            request.setAttribute("error", "La cantidad es cero");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/traspasoServlet");
            rd.forward(request, response);
        }
        
        movimientof.realizarTransferencia(cuentaDestino, cantidad, saldoOrigen, cuentaOrigen);

        
        //tengo que comprobar que no me quedo sin dinero
        
        RequestDispatcher dispacher = getServletContext().getRequestDispatcher("/Movimientos");
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
