
package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import modelo.Investigador;

public class InvestigadorTest {

    @Test
    public void testCrearInvestigador() {

        Investigador investigador = new Investigador(
                "Carl Sagan",
                "Astronomia"
        );

        assertEquals("Carl Sagan", investigador.getNombre());
        assertEquals("Astronomia", investigador.getEspecialidad());
    }

    @Test
    public void testModificarInvestigador() {

        Investigador investigador = new Investigador(
                "Carl Sagan",
                "Astronomia"
        );

        investigador.setNombre("Neil deGrasse Tyson");
        investigador.setEspecialidad("Astrofisica");

        assertEquals("Neil deGrasse Tyson", investigador.getNombre());
        assertEquals("Astrofisica", investigador.getEspecialidad());
    }
}

