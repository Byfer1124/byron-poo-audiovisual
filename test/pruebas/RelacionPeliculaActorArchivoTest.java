
package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Pelicula;
import persistencia.ActorArchivo;
import persistencia.RelacionPeliculaActorArchivo;

public class RelacionPeliculaActorArchivoTest {

    @TempDir
    Path carpetaTemporal;

    @Test
    public void testGuardarYAsignarActores() {

        Path rutaRelaciones =
                carpetaTemporal.resolve("pelicula_actores.csv");

        Path rutaActores =
                carpetaTemporal.resolve("actores.csv");

        Actor actor1 = new Actor(
                "Leonardo DiCaprio",
                "Estadounidense"
        );

        Actor actor2 = new Actor(
                "Tom Hardy",
                "Británica"
        );

        // Guardar los actores en el CSV.
        Map<Integer, Actor> mapaActores = new LinkedHashMap<>();

        mapaActores.put(0, actor1);
        mapaActores.put(1, actor2);

        ActorArchivo archivoActores =
                new ActorArchivo(rutaActores.toString());

        archivoActores.guardarActores(mapaActores);

        // Crear la película y asignar sus actores.
        Pelicula pelicula = new Pelicula(
                0,
                "Avatar",
                162,
                "Ciencia ficcion",
                "20th Century Studios"
        );

        pelicula.agregarActor(actor1);
        pelicula.agregarActor(actor2);

        List<ContenidoAudiovisual> contenidos = new ArrayList<>();
        contenidos.add(pelicula);

        RelacionPeliculaActorArchivo archivo =
                new RelacionPeliculaActorArchivo(
                        rutaRelaciones.toString(),
                        rutaActores.toString()
                );

        // Guardar las relaciones entre película y actores.
        archivo.guardarRelacionesPeliculaActores(contenidos);

        // Crear una película nueva sin actores.
        Pelicula peliculaLeida = new Pelicula(
                0,
                "Avatar",
                162,
                "Ciencia ficcion",
                "20th Century Studios"
        );

        List<ContenidoAudiovisual> contenidosLeidos =
                new ArrayList<>();

        contenidosLeidos.add(peliculaLeida);

        // Recuperar los actores desde los archivos CSV.
        archivo.asignarActores(contenidosLeidos);

        assertEquals(2, peliculaLeida.getActores().size());

        assertEquals(
                "Leonardo DiCaprio",
                peliculaLeida.getActores().get(0).getNombre()
        );

        assertEquals(
                "Tom Hardy",
                peliculaLeida.getActores().get(1).getNombre()
        );
    }
}
