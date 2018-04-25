/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppBanco.ejb;

import AppBanco.entity.Cuenta;
import AppBanco.entity.Empleado;
import AppBanco.entity.Movimiento;
import AppBanco.entity.Operacion;
import java.util.Date;
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
public class MovimientoFacade extends AbstractFacade<Movimiento> {

    @PersistenceContext(unitName = "AppBanco-ejbPU")
    private EntityManager em;

    @EJB
    private OperacionFacade opF;

    @EJB
    private CuentaFacade cuF;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovimientoFacade() {
        super(Movimiento.class);
    }

    /**
     * Busca en la base de datos movimientos de una cuenta filtrados por
     * concepto, ingresos y gastsos.
     *
     * Nota : Hace un XOR en gastos e ingresos. Si se pasan los dos a false, se
     * devuelven tanto ingresos como gastos.
     *
     * @param cuenta Cuenta del cliente para buscar los movimientos.
     * @param ingreso True si se quieren los ingresos.
     * @param gastos True si se quieren los gastos.
     * @param concepto Null si no importa o una cadena que contenga el concepto
     * fitlrar.
     * @author Víctor Manuel Ortiz Guardeño
     *
     * @return Los movimientos filtrados por los parámetros de la función.
     */
    public List<Movimiento> buscarPorCuentaOrderByFechaDesc(Cuenta cuenta, Boolean ingreso, Boolean gastos, String concepto) {
        String qq = "SELECT m FROM Movimiento m WHERE m.cuenta.numeroStr = :cuenta";

        if (concepto != null) {
            qq = qq + " AND m.concepto LIKE :concepto";
        }

        if (ingreso && !gastos) {
            qq = qq + " AND m.importe >= 0";
        }

        if (!ingreso && gastos) {
            qq = qq + " AND m.importe < 0";
        }

        qq = qq + " ORDER BY m.fecha DESC";

        Query query = getEntityManager()
                .createQuery(qq);
        query.setParameter("cuenta", cuenta.getNumeroStr());

        if (concepto != null) {
            query.setParameter("concepto", "%" + concepto + "%");
        }

        return query.getResultList();
    }

    /**
     * Crea un nuevo apunte de Ingreso o Reintegro en una cuenta ya existente
     *
     * @param operacion I (Ingreso) o R (Reintegro)
     * @param em Obejto Empleado, el que realiza la transaccion
     * @param cuenta Cuenta a la que se va a realizar el apunte
     * @param cantidad Cantidad referida al apunte, unsigned mayor que 0
     */
    public Movimiento nuevoApunte(String operacion, Empleado em, Cuenta cuenta, double cantidad) {
        if (em == null || cuenta == null || cantidad <= 0 || !(operacion.equals("I") || operacion.equals("R"))) {
            throw new RuntimeException("Parametros incorrectos");
        } else {
            String concepto;
            Movimiento mov;
            double saldo = cuF.getSaldoCuenta(cuenta.getNumeroStr());
            if (saldo - cantidad < 0 && operacion.equals("R")) {
                throw new RuntimeException("Reintegro mayor que saldo actual");
            }
            if (operacion.equals("I")) {
                concepto = "Ingreso";
                saldo += cantidad;
            } else {
                concepto = "Reintegro";
                saldo -= cantidad;
            }
            Operacion op = opF.crearOperacionIngresoOReintegro(concepto, em);
            mov = new Movimiento(0, concepto, new Date(), cantidad * (operacion.equals("I") ? 1 : -1), saldo);
            mov.setOperacion(op);
            mov.setCuenta(cuenta);
            create(mov);
            return mov;
        }
    }

    public void realizarTransferencia(Cuenta cuentaDestino, double cantidad, double saldoOrigen, Cuenta cuentaOrigen) {
        //Primera parte de la transferencia
        if (cantidad < saldoOrigen && cantidad > 0) {
            Operacion operacionResta = opF.crearOperacionIngresoOReintegro("TRASPASO", null);
            Movimiento movResta = new Movimiento(0, "Traspaso a " + cuentaDestino.getNumeroStr(), new Date(), cantidad * -1, saldoOrigen - cantidad);
            movResta.setOperacion(operacionResta);
            movResta.setCuenta(cuentaOrigen);
            create(movResta);

            //Segunda Parte de la transferencia, ingreso en la cuenta destino que ya hemos comprobado que existe 
            double saldoDestino = cuF.getSaldoCuenta(cuentaDestino.getNumeroStr());
            Operacion operacionSuma = opF.crearOperacionIngresoOReintegro("TRASPASO", null);
            Movimiento movSuma = new Movimiento(0, "Traspaso de " + cuentaOrigen.getNumeroStr(), new Date(), cantidad, saldoDestino + cantidad);
            movSuma.setOperacion(operacionSuma);
            movSuma.setCuenta(cuentaDestino);
            create(movSuma);
        }
    }

}
