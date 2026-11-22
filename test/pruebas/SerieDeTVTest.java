package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import modelo.SerieDeTV;
import modelo.Temporada;

public class SerieDeTVTest {

    @Test
    public void testCrearSerie() {

        SerieDeTV serie = new SerieDeTV(
                "Game of Thrones",
                60,
                "Fantasia",
                8
        );

        assertEquals("Game of Thrones", serie.getTitulo());
        assertEquals(60, serie.getDuracionEnMinutos());
        assertEquals("Fantasia", serie.getGenero());
        assertEquals(8, serie.getTemporadas());
    }

    @Test
    public void testAgregarTemporadas() {

        SerieDeTV serie = new SerieDeTV(
                "Game of Thrones",
                60,
                "Fantasia",
                8
        );

        serie.agregarTemporada(2011, 10);
        serie.agregarTemporada(2012, 10);

        assertNotNull(serie.getDetallesTemporadas());
        assertEquals(2, serie.getDetallesTemporadas().size());

        Temporada primera = serie.getDetallesTemporadas().get(0);

        assertEquals(2011, primera.getAnioEstreno());
        assertEquals(10, primera.getCantidadEpisodios());
    }
}
