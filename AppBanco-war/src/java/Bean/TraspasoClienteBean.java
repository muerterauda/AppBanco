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
        Cuenta cuenta = cuentaEJB.findCuentaNumeroStr(cuentaStr);
        String paginaFin = null;
        
        // Si la cuenta existe...
        if (cuenta != null) {
            
            // Hago la transferencia en la base de datos.
            try {
                cuentaEJB.transferencia(super.getCuenta(), cuenta, cantidad);
            }
            catch(EJBException ex) {
                setErrorMsg(ex.getMessage());
            } catch (Exception ex) {
                setErrorMsg(ex.getMessage());                
            }
            
        }
        else 
            setErrorMsg("El benefactor no existe.");
        
        return paginaFin;
    }
    
    
}
