
package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import modelo.Investigador;
import persistencia.InvestigadorArchivo;

public class InvestigadorArchivoTest {

    @TempDir
    Path carpetaTemporal;

    @Test
    public void testGuardarYLeerInvestigadores() {

        Path rutaArchivo =
                carpetaTemporal.resolve("investigadores.csv");

        InvestigadorArchivo archivo =
                new InvestigadorArchivo(rutaArchivo.toString());

        Map<Integer, Investigador> investigadores =
                new LinkedHashMap<>();

        investigadores.put(
                2,
                new Investigador("Carl Sagan", "Astronomia")
        );

        // Guardar investigadores en el CSV.
        archivo.guardarInvestigadores(investigadores);

        assertTrue(rutaArchivo.toFile().exists());

        // Leer investigadores desde el CSV.
        Map<Integer, Investigador> investigadoresLeidos =
                archivo.leerInvestigadores();

        assertEquals(1, investigadoresLeidos.size());

        assertEquals(
                "Carl Sagan",
                investigadoresLeidos.get(2).getNombre()
        );

        assertEquals(
                "Astronomia",
                investigadoresLeidos.get(2).getEspecialidad()
        );
    }
}
