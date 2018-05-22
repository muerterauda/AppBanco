/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import AppBanco.ejb.CuentaFacade;
import AppBanco.entity.Cuenta;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Vikour
 */
@Named(value = "traspasoClienteBean")
@RequestScoped
public class TraspasoClienteBean extends ClienteSessionBeanAbstract {

    @EJB
    private CuentaFacade cuentaEJB;
    
    // Atributos de la vista
    private double saldo;
    private String cuentaStr;
    private double cantidad;
    private String errorMsg;
    
    public TraspasoClienteBean() {
    }
    
    @PostConstruct
    public void init() {
        saldo = cuentaEJB.getSaldoCuenta(super.getCuenta().getNumeroStr());
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getCuentaStr() {
        return cuentaStr;
    }

    public void setCuentaStr(String cuenta) {
        this.cuentaStr = cuenta;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public String doTransfer() {
        Cuenta benefactor = cuentaEJB.findCuentaNumeroStr(cuentaStr);
        String paginaFin = null;
        
        // Si la cuenta existe...
        if (benefactor != null) {
            double saldoOrd = cuentaEJB.getSaldoCuenta(super.getCuenta().getNumeroStr());
            double saldoBen = cuentaEJB.getSaldoCuenta(cuentaStr);
            
            try {
                // Valido las precondiciones para poder hacer la transferencia ....
                comprobarValidezTransferencia(super.getCuenta(), benefactor, cantidad, saldoOrd);
                // Hago la transferencia en la base de datos.
                cuentaEJB.transferencia(super.getCuenta(), benefactor, cantidad, saldoOrd, saldoBen);
                paginaFin = "MovimientosCliente.xhtml";
            }
            catch (RuntimeException ex) {
                setErrorMsg(ex.getMessage());
            }

        } else
            setErrorMsg("El benefactor no existe.");
        
        return paginaFin;
    }

    private void comprobarValidezTransferencia(Cuenta ordenante, Cuenta benefactor, double cantidad,
                                               double saldoOrd) {
        if (ordenante == null)
            throw new NullPointerException("Se esperaba un ordenante no nulo");
        
        if (benefactor == null)
            throw new NullPointerException("Se esperaba un benefactor no nulo");
        
        if (cantidad <= 0)
            throw new IllegalArgumentException("Se esperaba un importe positivo mayor que 0");
        
        if (ordenante.equals(benefactor))
            throw new IllegalArgumentException("El benefactor y el ordenante no puede ser el mismo");
        
        if (saldoOrd < cantidad) 
            throw new RuntimeException("El ordenante no tiene suficiente saldo");

    }
    
    
}
