/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean.empleado;

import AppBanco.ejb.EmpleadoFacade;
import AppBanco.entity.Empleado;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
/**
 *
 * @author elias
 */
@Named(value = "loginEmpleadoBean")
@SessionScoped
public class LoginEmpleadoBean implements Serializable {

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
    private String error;
    private int idEmpleadoparse;

    @PostConstruct
    public void init() {
       
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
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

    public String doLogin() {
        String ret = "loginEmpleado";
        try {
            if (idEmpleado.equals("")) {
                setError("errorEmpleado1");
            }

            if (password.equals("")) {
                setError("errorEmpleado2");
            }

            if (idEmpleado != null) {
                idEmpleadoparse = Integer.parseInt(idEmpleado);
            }

            empleado = empleadoFacade.find(idEmpleadoparse);

            if (empleado == null || !empleado.getContrasenya().equals(password)) {
                if (empleado == null) {
                    setError("errorEmpleado3");
                } else {
                    setError("errorEmpleado4");
                }
            } else {
                ret="principalEmpleado.xhtml";
                setEmpleado(empleado);
                setError(new String());
            }
            

        } catch (NumberFormatException ex) {
            setError("errorEmpleado5");
        }

        
        
        return ret;
    }
    public String doLogout(){
        setEmpleado(null);
        setError(null);
        return "loginEmpleado";
    }
}
