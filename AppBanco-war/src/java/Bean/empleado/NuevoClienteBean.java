/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean.empleado;

import AppBanco.ejb.ClienteFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author GRJuanjo
 */
@Named(value = "NuevoClienteBean") 
@RequestScoped
public class NuevoClienteBean {

    @Inject
    private EmpleadoSessionBeanAbstract session;

    @EJB
    private ClienteFacade clientefa;
    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private String direccion;
    private String telefono;
    private List<String> error = new ArrayList<String>();

    public String doRegister() {
        boolean clienteExistente = clientefa.clienteYaExiste(dni);
        String page;
        if (!dniCorrecto(dni) || isNullOrWhitespace(nombre) || isNullOrWhitespace(apellidos) || isNullOrWhitespace(dni) || isNullOrWhitespace(email) || isNullOrWhitespace(direccion) || isNullOrWhitespace(telefono) || clienteExistente) {
            
            if (isNullOrWhitespace(nombre)) {
                error.add("campoclientevacio");
            } 
            if (isNullOrWhitespace(apellidos)) {
                error.add("campoapellidosvacio");
            } 
            if (isNullOrWhitespace(dni)) {
                error.add("campoDNIvacio");
            } else if (!clienteExistente) {
                if (!dniCorrecto(dni)) {
                    error.add("DNIincorrecto");
                }
            } else {
                error.add("DNIexistente");
            }
            if (isNullOrWhitespace(email)) {
                error.add("campoemailvacio");
            } 
            if (isNullOrWhitespace(direccion)) {
                error.add("campodireccionvacio");
            } 
            if (isNullOrWhitespace(telefono)) {
                error.add("campotelefonovacio");
            } 
            
            page = "altaClienteForm.xhtml";
        }else{
            session.setCliente(clientefa.insertarCliente(nombre, apellidos, dni, email, direccion, telefono)); 
           page="resultAltaClienteForm.xhtml";
        }
        return page;
    }

    private static boolean dniCorrecto(String s) {
        boolean dniCorrecto = false;
        if (s != null) {
            if (Pattern.matches("[0-9]{8}[A-Z]", s)) {
                dniCorrecto = true;
            } else {
                dniCorrecto = false;
            }
        }
        return dniCorrecto;
    }

    private static boolean isNullOrWhitespace(String s) {
        return s == null || s.trim().isEmpty();
    }

    public NuevoClienteBean() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }
}
