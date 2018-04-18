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
    
    public List<Movimiento> buscarPorCuentaOrderByFechaDesc(Cuenta cuenta, Boolean ingreso, Boolean gastos, String concepto) {
        String qq = "SELECT m FROM Movimiento m WHERE m.cuenta.numeroCuenta = :cuenta";
        
        if (concepto != null)
            qq = qq + " AND m.concepto LIKE :concepto";
        
        if (ingreso && !gastos)
            qq = qq + " AND m.importe >= 0";

        if (!ingreso && gastos)
            qq = qq + " AND m.importe < 0";
        
        qq = qq + " ORDER BY m.fecha DESC";
        
        Query query = getEntityManager()
              .createQuery(qq);
        query.setParameter("cuenta", cuenta.getNumeroCuenta());
        
        if (concepto != null)
            query.setParameter("concepto", "%" + concepto + "%");
        
        return query.getResultList();
    }
    
}
