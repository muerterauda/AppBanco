/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean.empleado;

import AppBanco.entity.Cliente;
import java.io.Serializable;
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
    @Inject
    private LoginEmpleadoBean loginBean;
    
    private Cliente cliente;
    
    
    
    
    /**
     * Creates a new instance of EmpleadoSessionBeanAbstract
     */
    public EmpleadoSessionBeanAbstract() {
    }

    public LoginEmpleadoBean getLoginBean() {
        return loginBean;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setLoginBean(LoginEmpleadoBean loginBean) {
        this.loginBean = loginBean;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    
    
    
}
