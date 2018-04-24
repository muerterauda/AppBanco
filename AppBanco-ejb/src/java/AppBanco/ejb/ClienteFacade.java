/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppBanco.ejb;

import AppBanco.entity.Cliente;
import AppBanco.entity.Cuenta;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "AppBanco-ejbPU")
    private EntityManager em;
    
    @EJB
    private CuentaFacade cuentafa;
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }
    
    public boolean clienteYaExiste(String DNI){
        return !em.createQuery("SELECT c FROM Cliente c WHERE c.dni LIKE :DNI").setParameter("DNI", DNI).getResultList().isEmpty();
    }
    
    public Cliente insertarCliente(String nombre,String apellidos, String DNI, String email,String direccion,String telefono){
        Cliente c = new Cliente();
        c.setNombre(nombre);
        c.setApellidos(apellidos);
        c.setDni(DNI);
        c.setEmail(email);
        c.setTelefono(telefono);
        c.setDireccion(direccion);
        c.setContrasenya(DNI);
        List<Cuenta> lista = new ArrayList<Cuenta>();
        Cuenta cuenta =cuentafa.crearCuenta(c);
        lista.add(cuenta);
        c.setCuentaList(lista);
        em.persist(c);
        return c;   
    }
    
    public Cuenta getCuenta(String dni){
        Query q = em.createQuery("SELECT c FROM Cliente c WHERE c.dni LIKE :dni").setParameter("dni", dni);
        Cliente c = (Cliente)q.getResultList().get(0);
        Cuenta cuenta = c.getCuentaList().get(0);
        return cuenta;
        
    }
        
   
   
   
   
}
