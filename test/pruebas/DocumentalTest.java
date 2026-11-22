package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import modelo.Documental;
import modelo.Investigador;

public class DocumentalTest {

    @Test
    public void testCrearDocumental() {

        Investigador investigador = new Investigador(
                "Carl Sagan",
                "Astronomia"
        );

        Documental documental = new Documental(
                "Cosmos",
                45,
                "Ciencia",
                "Astronomia",
                investigador
        );

        assertEquals("Cosmos", documental.getTitulo());
        assertEquals(45, documental.getDuracionEnMinutos());
        assertEquals("Ciencia", documental.getGenero());
        assertEquals("Astronomia", documental.getTema());

        assertNotNull(documental.getInvestigadorPrincipal());

        assertEquals(
                "Carl Sagan",
                documental.getInvestigadorPrincipal().getNombre()
        );
    }
}
