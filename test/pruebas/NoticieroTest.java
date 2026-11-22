package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import modelo.Noticiero;

public class NoticieroTest {

    @Test
    public void testCrearNoticiero() {

        Noticiero noticiero = new Noticiero(
                "Noticias Estelar",
                30,
                "Informativo",
                "Carlos Vera",
                "Teleamazonas"
        );

        assertEquals("Noticias Estelar", noticiero.getTitulo());
        assertEquals(30, noticiero.getDuracionEnMinutos());
        assertEquals("Informativo", noticiero.getGenero());
        assertEquals("Carlos Vera", noticiero.getPresentador());
        assertEquals("Teleamazonas", noticiero.getCadenaTv());
    }

    @Test
    public void testModificarNoticiero() {

        Noticiero noticiero = new Noticiero(
                "Noticias Estelar",
                30,
                "Informativo",
                "Carlos Vera",
                "Teleamazonas"
        );

        noticiero.setPresentador("Andrés López");
        noticiero.setCadenaTv("Ecuavisa");

        assertEquals("Andrés López", noticiero.getPresentador());
        assertEquals("Ecuavisa", noticiero.getCadenaTv());
    }
}
