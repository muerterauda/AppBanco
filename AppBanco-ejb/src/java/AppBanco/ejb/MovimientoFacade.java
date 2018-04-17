/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppBanco.ejb;

import AppBanco.entity.Cuenta;
import AppBanco.entity.Movimiento;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class MovimientoFacade extends AbstractFacade<Movimiento> {

    @PersistenceContext(unitName = "AppBanco-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovimientoFacade() {
        super(Movimiento.class);
    }
    /**
    @param tipo Debe ser Ingreso o Reintegro
    @param em No debe ser null
    */
    public Movimiento crearMovimiento(int cantidad, Cuenta c, String concepto){
        Movimiento m= null;
        //CuentaFacade r = null;
       // int saldo=r.getSaldoCuenta(c.getNumeroCuenta());
        //m= new Movimiento(0,concepto, new Date(), cantidad, saldo);
        return null;
    }
}
