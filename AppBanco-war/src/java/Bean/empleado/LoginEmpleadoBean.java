/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean.empleado;

import AppBanco.ejb.EmpleadoFacade;
import AppBanco.entity.Empleado;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author elias
 */
@Named(value = "loginEmpleadoBean")
@Dependent
public class LoginEmpleadoBean {

    /**
     * Creates a new instance of LoginEmpleadoBean
     */
    public LoginEmpleadoBean() {
    }
    
     @EJB
    private EmpleadoFacade empleadoFacade;

    /**
     * Creates a new instance of loginEmpleadoBean
     */
    private String idEmpleado;
    private String password;
    private Empleado empleado;
    

    
    @PostConstruct
    public void init(){
        
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public String getPassword() {
        return password;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    public String doLogin(){
        
        
        
        
        
        return "principalEmpleado";
    }
    
}
