package es.iessoterohernandez.daw.endes.Cuenta_Tarjeta;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditoTest {
	private Cuenta cuenta;
    private Credito credito;

    @BeforeEach
    public void setUp() {
        cuenta = new Cuenta("1234", "Titular de la cuenta");
        credito = new Credito("4321", "Titular de la tarjeta", new Date(), 1000.0);
        credito.setCuenta(cuenta);
    }
    @AfterEach
    public void finish() {
    	cuenta = null;
    	credito = null;
    }

    @Test
    public void testRetirarCredito() {
        assertThrows(Exception.class, () -> {
            credito.retirar(1100.0); // Intentar retirar más del crédito disponible
        });

        try {
            credito.retirar(500.0);
        } catch (Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
        assertEquals(500.0, credito.getSaldo());
    }

    @Test
    public void testIngresarCredito() {
        try {
            credito.ingresar(200.0);
        } catch (Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
        assertEquals(200.0, credito.getSaldo());
    }

    @Test
    public void testPagoEnEstablecimientoCredito() {
        try {
            credito.pagoEnEstablecimiento("Tienda de prueba", 100.0);
        } catch (Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
        assertEquals(100.0, credito.getSaldo());
    }

    @Test
    public void testLiquidarCredito() throws Exception {
        credito.ingresar(300.0);
        credito.pagoEnEstablecimiento("Tienda 1", 50.0);
        credito.pagoEnEstablecimiento("Tienda 2", 70.0);

        credito.liquidar(1, 2024); // Liquidar operaciones del mes 1 de 2024

        assertEquals(420.0, credito.getSaldo()); // Saldo debe ser 300 (ingreso) - 50 (tienda 1) - 70 (tienda 2) = 180 antes de liquidar
        assertEquals(180.0, cuenta.getSaldo()); // La cuenta asociada debe reflejar el importe liquidado
    }
}
