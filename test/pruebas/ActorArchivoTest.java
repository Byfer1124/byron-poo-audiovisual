
package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import modelo.Actor;
import persistencia.ActorArchivo;

public class ActorArchivoTest {

    @TempDir
    Path carpetaTemporal;

    @Test
    public void testGuardarYLeerActores() {

        Path rutaArchivo = carpetaTemporal.resolve("actores.csv");

        ActorArchivo archivo = new ActorArchivo(
                rutaArchivo.toString()
        );

        Map<Integer, Actor> actores = new LinkedHashMap<>();

        actores.put(
                0,
                new Actor("Leonardo DiCaprio", "Estadounidense")
        );

        actores.put(
                1,
                new Actor("Tom Hardy", "Británica")
        );

        // Guardar los actores en el archivo CSV.
        archivo.guardarActores(actores);

        assertTrue(rutaArchivo.toFile().exists());

        // Leer nuevamente los actores desde el CSV.
        Map<Integer, Actor> actoresLeidos = archivo.leerActores();

        assertEquals(2, actoresLeidos.size());

        assertEquals(
                "Leonardo DiCaprio",
                actoresLeidos.get(0).getNombre()
        );

        assertEquals(
                "Estadounidense",
                actoresLeidos.get(0).getNacionalidad()
        );

        assertEquals(
                "Tom Hardy",
                actoresLeidos.get(1).getNombre()
        );

        assertEquals(
                "Británica",
                actoresLeidos.get(1).getNacionalidad()
        );
    }
}
