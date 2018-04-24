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
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/transferencias.jsp");
            rd.forward(request, response);
        }
        if(request.getParameter("cantidad")==null || Double.parseDouble(request.getParameter("cantidad"))<0){
            request.setAttribute("error", "La cantidad es null o menor que cero");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/transferencias.jsp");
            rd.forward(request, response);
        }
        HttpSession session=request.getSession();
        
        double cantidad=Double.parseDouble(request.getParameter("cantidad"));
        Cuenta cuentaDestino= cuentaf.findCuentaNumeroStr(request.getParameter("cuentaDest"));
        Cuenta cuentaOrigen= (Cuenta) session.getAttribute("cuenta");
        
        if(cuentaDestino==null){
            request.setAttribute("error", "La cuenta introducida no es valida");
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/transferencias.jsp");
            rd.forward(request, response);
        }
        
        //Primera parte de la transferencia
        double saldoOrigen= cuentaf.getSaldoCuenta(cuentaOrigen.getNumeroStr());
        Operacion operacionResta= operacionf.crearOperacionIngresoOReintegro("TRASPASO", null);
        Movimiento movResta=new Movimiento(0, "Traspaso a "+cuentaDestino.getNumeroStr(), new Date(), cantidad*-1, saldoOrigen-cantidad );
        movResta.setOperacion(operacionResta);
        movResta.setCuenta(cuentaOrigen);
        movimientof.create(movResta);
        
        //Segunda Parte de la transferencia, ingreso en la cuenta destino que ya hemos comprobado que existe 
        double saldoDestino=cuentaf.getSaldoCuenta(cuentaDestino.getNumeroStr());
        Operacion operacionSuma=operacionf.crearOperacionIngresoOReintegro("TRASPASO", null);
        Movimiento movSuma= new Movimiento(0, "Traspaso de "+cuentaOrigen.getNumeroStr(), new Date(), cantidad, saldoDestino+cantidad);
        movSuma.setOperacion(operacionSuma);
        movSuma.setCuenta(cuentaDestino);
        movimientof.create(movSuma);

        
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
