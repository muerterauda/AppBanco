/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean.empleado;

import AppBanco.ejb.CuentaFacade;
import AppBanco.entity.Cliente;
import AppBanco.entity.Cuenta;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author elias
 */
@Named(value = "empleadoSessionBeanAbstract")
@SessionScoped
public class EmpleadoSessionBeanAbstract implements Serializable {

    @EJB
    private CuentaFacade cuentaFacade;
    
    
    @Inject
    private LoginEmpleadoBean loginBean;
    
    private Cuenta cuenta;
    private Cliente cliente;
    
    
    
    
    /**
     * Creates a new instance of EmpleadoSessionBeanAbstract
     */
    public EmpleadoSessionBeanAbstract() {
    }

    public LoginEmpleadoBean getLoginBean() {
        return loginBean;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setLoginBean(LoginEmpleadoBean loginBean) {
        this.loginBean = loginBean;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    
    
    
}