package modelo.datatypes;


import java.math.BigInteger;


/**
 * Esta representa una cuenta bancaria, para un IBAN español y una ENTIDAD y 
 * SUCURSAL fija.
 *
 * @author Víctor Manuel Ortiz Guardeño
 */

public class NumeroCuentaBancaria {
    
    private final String ENTIDAD = "0420";
    private final String SUCURSAL = "1234";
    private int dc; // Dígito de control
    private long cuenta; // Num cuenta.
    private final String IBAN_PAIS = "ES";
    private int ibanDC; // Dígito de control del IBAN.
    
    /**
     * Construye el número de cuenta bancaria, desde un número de cuenta pasado
     * como argumento. Calculando su número de dígito de control, y su dígito de
     * IBAN.
     * 
     * @param cuenta Número de cuenta de la cuenta bancaria.
     */
    
    public NumeroCuentaBancaria(long cuenta) {
        String cuentaStr = String.format("%010d", cuenta);
        this.cuenta = cuenta;
        dc = calcularPrimerDigito() * 10;
        dc += calcularSegundoDigito(cuentaStr);
        ibanDC = calcularIbanDC();

    }
    
    private int calcularPrimerDigito() {
        return 1; // Precalculado para la sucursal y entidad fijadas.
    }
    
    private int calcularSegundoDigito(String cuenta) {
        int aux = 0;
        int [] productos = {1,2,4,8,5,10,9,7,3,6};
        
        for (int i = 0 ; i < cuenta.length() ; i++)
            aux += extractInteger(cuenta.charAt(i)) * productos[i];
        
        aux = 11 - (aux % 11);
        
        return aux == 10 ? 1 : aux == 11 ? 0 : aux;
    }

    @Override
    public String toString() {
        return IBAN_PAIS + String.format("%02d",ibanDC) + ENTIDAD + SUCURSAL + dc + String.format("%010d", cuenta);
    }

    private int calcularIbanDC() {
        // 142800 -> 14 = E, 28 = S del IBAN "ES" y 00 son los dígitos que hay 
        // que tener antes de calcular el número.
        BigInteger bi = new BigInteger("142800"+ENTIDAD+SUCURSAL+dc+String.format("%010d", cuenta));
        
        bi = bi.mod(new BigInteger("97"));
        bi = (new BigInteger("98").subtract(bi));
        return bi.intValue();
    }

    private int extractInteger(char i) {
        return (int) (i - '0');
    }
    
}
