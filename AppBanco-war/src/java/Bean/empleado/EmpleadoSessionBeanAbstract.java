/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean.empleado;

import AppBanco.ejb.ClienteFacade;
import AppBanco.ejb.CuentaFacade;
import AppBanco.entity.Cliente;
import AppBanco.entity.Cuenta;
import AppBanco.entity.Empleado;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author elias
 */
@Named(value = "EmpleadoSessionBeanAbstract")
@SessionScoped
public class EmpleadoSessionBeanAbstract implements Serializable {

    private Cliente cliente;
    private Cuenta cuenta;

    @Inject
    private LoginEmpleadoBean loginBean;
     
    /**
     * Creates a new instance of EmpleadoSessionBeanAbstract
     */
    public EmpleadoSessionBeanAbstract() {
    }

   

    public Cuenta getCuenta() {
        return cuenta;
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
    
    public Empleado getEmpleado(){
        return loginBean.getEmpleado();
    }
    
    
    
}
