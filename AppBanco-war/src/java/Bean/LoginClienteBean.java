/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import AppBanco.ejb.ClienteFacade;
import AppBanco.entity.Cliente;
import AppBanco.entity.Cuenta;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author user
 */
@Named(value = "LoginClienteBean")
@SessionScoped
public class LoginClienteBean implements Serializable {

    @EJB
    private ClienteFacade Conectorcliente;
    private String dni;
    private String password;
    private String error;
    private Cliente cliente;
    /**
     * Creates a new instance of LoginClienteBean
     */
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public Cliente getCliente(){
        return cliente;
    }
    
    public String doLogin(){
        cliente=Conectorcliente.find(dni);
        String page="MovimientosCliente.xhtml";
        if(cliente==null||!cliente.getContrasenya().equals(password)){
              if(cliente==null){
                  setError("Error: No existe el usuario");
              }else if(!cliente.getContrasenya().equals(password)){
                  setError("Error: La contrase&ntilde;a no coincide");
              }else{
                  setError("Error: Error con la cuenta contacta con el banco");
              }
         }else{
              page="MovimientosCliente.xhtml"; //Cambiar al bueno una vez este hecho
         }
         return page;
    }
    
    public LoginClienteBean() {
    }
    
}
