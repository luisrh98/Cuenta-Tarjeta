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
	
	
	//En este Test devuelve solo la comision de la tarjeta!!
	@Test
	public void testRetirarCredito() {
		try {
			credito.retirar(500);
			assertEquals(25, credito.getSaldo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// Saldo negativo
		try {
			credito.retirar(1100.0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals("Credito insuficiente", e.getMessage());
		}

		
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
		// Realizamos algunas operaciones con la tarjeta de crédito
        credito.ingresar(300.0);
        credito.pagoEnEstablecimiento("Tienda 1", 50.0);
        credito.pagoEnEstablecimiento("Tienda 2", 70.0);

        // Liquidamos las operaciones del mes 1 de 2024
        credito.liquidar(1, 2024);

        // Verificamos que el saldo de la tarjeta sea correcto después de la liquidación
        assertEquals(420.0, credito.getSaldo());

        // Verificamos que el saldo de la cuenta asociada sea correcto después de la liquidación
        assertEquals(580.0, cuenta.getSaldo());
    }
}
