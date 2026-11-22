
package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import modelo.ContenidoAudiovisual;
import modelo.SerieDeTV;
import modelo.Temporada;
import persistencia.TemporadaArchivo;

public class TemporadaArchivoTest {

    @TempDir
    Path carpetaTemporal;

    @Test
    public void testGuardarYAsignarTemporadas() {

        Path rutaArchivo =
                carpetaTemporal.resolve("temporadas.csv");

        TemporadaArchivo archivo =
                new TemporadaArchivo(rutaArchivo.toString());

        List<ContenidoAudiovisual> contenidos = new ArrayList<>();

        SerieDeTV serie = new SerieDeTV(
                1,
                "Game of Thrones",
                60,
                "Fantasia",
                8
        );

        serie.agregarTemporada(2011, 10);
        serie.agregarTemporada(2012, 10);

        contenidos.add(serie);

        // Guardar las temporadas en el CSV.
        archivo.guardarTemporadas(contenidos);

        // Crear una nueva serie sin temporadas.
        SerieDeTV serieLeida = new SerieDeTV(
                1,
                "Game of Thrones",
                60,
                "Fantasia",
                8
        );

        List<ContenidoAudiovisual> contenidosLeidos =
                new ArrayList<>();

        contenidosLeidos.add(serieLeida);

        // Asignar las temporadas leyendo el CSV.
        archivo.asignarTemporadas(contenidosLeidos);

        assertEquals(2, serieLeida.getDetallesTemporadas().size());

        Temporada primera =
                serieLeida.getDetallesTemporadas().get(0);

        assertEquals(2011, primera.getAnioEstreno());
        assertEquals(10, primera.getCantidadEpisodios());

        Temporada segunda =
                serieLeida.getDetallesTemporadas().get(1);

        assertEquals(2012, segunda.getAnioEstreno());
        assertEquals(10, segunda.getCantidadEpisodios());
    }
}
