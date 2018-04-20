/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppBanco.ejb;

import AppBanco.entity.Cuenta;
import AppBanco.entity.Movimiento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
