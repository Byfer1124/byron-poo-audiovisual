
package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import modelo.Temporada;

public class TemporadaTest {

    @Test
    public void testCrearTemporada() {

        Temporada temporada = new Temporada(2011, 10);

        assertEquals(2011, temporada.getAnioEstreno());
        assertEquals(10, temporada.getCantidadEpisodios());
    }

    @Test
    public void testModificarTemporada() {

        Temporada temporada = new Temporada(2011, 10);

        temporada.setAnioEstreno(2012);
        temporada.setCantidadEpisodios(12);

        assertEquals(2012, temporada.getAnioEstreno());
        assertEquals(12, temporada.getCantidadEpisodios());
    }
}
