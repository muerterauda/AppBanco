/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppBanco.ejb;

import AppBanco.entity.Cuenta;
import AppBanco.entity.Cliente;
import AppBanco.entity.Movimiento;
import AppBanco.entity.Operacion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.datatypes.NumeroCuentaBancaria;

/**
 *
 * @author user
 */
@Stateless
public class CuentaFacade extends AbstractFacade<Cuenta> {

    @PersistenceContext(unitName = "AppBanco-ejbPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuentaFacade() {
        super(Cuenta.class);
    }
    /**
     * Devuelve el saldo de la cuenta
     * @param numeroCuenta Debe ser numeroStr
     * @return Devuelve 0 si no existen movimientos en la cuenta.
     */
    public double getSaldoCuenta(String numeroCuenta) {
        Movimiento result;
        Query q = em.createQuery("SELECT m FROM Movimiento m WHERE m.cuenta.numeroStr = :p ORDER BY m.fecha DESC");
        q.setParameter("p", numeroCuenta);
        List <Movimiento> list= q.getResultList();
        if(list.isEmpty()||list==null){
            result=null;
        }else{
          result = list.get(0);   
        }
        return result == null ? 0 : result.getSaldo();
    }

    public Cuenta findCuentaNumeroStr(String numeroCuenta) {
        Cuenta result;
        Query q = em.createQuery("SELECT c FROM Cuenta c WHERE c.numeroStr= :c");
        q.setParameter("c", numeroCuenta);
        List<Cuenta> listC=q.getResultList();
        if(listC.isEmpty()||listC==null){
            result=null;
        }else{
          result = listC.get(0);   
        }
        return result;
    }
    
    public Cuenta crearCuenta(Cliente c){
        Cuenta cuenta = new Cuenta();
        cuenta.setCliente(c);
        cuenta.setMovimientoList(new ArrayList<Movimiento>());
        int numero =((int) em.createQuery("SELECT MAX(c.numero) FROM Cuenta c").getResultList().get(0))+1;
        cuenta.setNumeroStr(new NumeroCuentaBancaria(numero).toString());
        cuenta.setNumero(numero);
        em.persist(cuenta);
        return cuenta;
    }
    
    public void transferencia(Cuenta ordenante, Cuenta benefactor, double cantidad,
                              double saldoOrd, double saldoBen) {
        Movimiento movOrd;
        Movimiento movBen;
        Operacion operacion;

        // Creo lo dos movimientos
        operacion = new Operacion(0, "TRASPASO");
        em.persist(operacion);
        
        movOrd = new Movimiento(0, "Traspaso a " + benefactor.getNumeroStr(), 
                                new Date(), cantidad, saldoOrd - cantidad);
        movBen = new Movimiento(0, "Traspaso desde " + ordenante.getNumeroStr(),
                                new Date(), cantidad, saldoBen + cantidad);
        movOrd.setCuenta(ordenante);
        movBen.setCuenta(benefactor);
        movOrd.setOperacion(operacion);
        movBen.setOperacion(operacion);
        
        em.persist(movOrd);
        em.persist(movBen);
    }
    
}
