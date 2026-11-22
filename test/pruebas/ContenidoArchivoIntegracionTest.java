
package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Investigador;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.Temporada;
import modelo.Documental;
import persistencia.ContenidoArchivo;

public class ContenidoArchivoIntegracionTest {

    @TempDir
    Path carpetaTemporal;

    @Test
    public void testGuardarTodo() throws Exception {

        // Crear las rutas temporales de los cinco archivos.
        Path rutaContenidos =
                carpetaTemporal.resolve("contenidos.csv");

        Path rutaActores =
                carpetaTemporal.resolve("actores.csv");

        Path rutaInvestigadores =
                carpetaTemporal.resolve("investigadores.csv");

        Path rutaTemporadas =
                carpetaTemporal.resolve("temporadas.csv");

        Path rutaRelaciones =
                carpetaTemporal.resolve("pelicula_actores.csv");

        // Crear el objeto de persistencia con las rutas temporales.
        ContenidoArchivo archivo = new ContenidoArchivo(
                rutaContenidos.toString(),
                rutaActores.toString(),
                rutaInvestigadores.toString(),
                rutaTemporadas.toString(),
                rutaRelaciones.toString()
        );

        List<ContenidoAudiovisual> contenidos =
                new ArrayList<>();

        // Crear una película con dos actores.
        Pelicula pelicula = new Pelicula(
                0,
                "Avatar",
                162,
                "Ciencia ficcion",
                "20th Century Studios"
        );

        Actor actor1 =
                new Actor("Leonardo DiCaprio", "Estadounidense");

        Actor actor2 =
                new Actor("Tom Hardy", "Britanico");

        pelicula.agregarActor(actor1);
        pelicula.agregarActor(actor2);

        contenidos.add(pelicula);

        // Crear una serie con dos temporadas.
        SerieDeTV serie = new SerieDeTV(
                1,
                "Game of Thrones",
                60,
                "Fantasia",
                2
        );

        serie.agregarTemporada(2011, 10);
        serie.agregarTemporada(2012, 10);

        contenidos.add(serie);

        // Crear un documental con investigador.
        Investigador investigador =
                new Investigador("Carl Sagan", "Astronomia");

        Documental documental = new Documental(
                2,
                "Cosmos",
                45,
                "Ciencia",
                "Astronomia",
                investigador
        );

        contenidos.add(documental);

        // Guardar todos los archivos.
        archivo.guardarTodo(contenidos);

        // Comprobar que se crearon los cinco archivos.
        assertTrue(Files.exists(rutaContenidos));
        assertTrue(Files.exists(rutaActores));
        assertTrue(Files.exists(rutaInvestigadores));
        assertTrue(Files.exists(rutaTemporadas));
        assertTrue(Files.exists(rutaRelaciones));

        // Comprobar la cantidad de registros de cada archivo.
        assertEquals(4, Files.readAllLines(rutaContenidos).size());
        assertEquals(3, Files.readAllLines(rutaActores).size());
        assertEquals(2, Files.readAllLines(rutaInvestigadores).size());
        assertEquals(3, Files.readAllLines(rutaTemporadas).size());
        assertEquals(3, Files.readAllLines(rutaRelaciones).size());

        // Comprobar datos de los archivos.
        assertTrue(Files.readString(rutaActores)
                .contains("Leonardo DiCaprio"));

        assertTrue(Files.readString(rutaActores)
                .contains("Tom Hardy"));

        assertTrue(Files.readString(rutaInvestigadores)
                .contains("Carl Sagan"));

        assertTrue(Files.readString(rutaTemporadas)
                .contains("1,2011,10"));

        assertTrue(Files.readString(rutaTemporadas)
                .contains("1,2012,10"));

        assertTrue(Files.readString(rutaRelaciones)
                .contains("0,0"));

        assertTrue(Files.readString(rutaRelaciones)
                .contains("0,1"));
    }
}
