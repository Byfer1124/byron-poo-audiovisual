
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
import modelo.Documental;
import modelo.Investigador;
import modelo.Noticiero;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.VideoEducativo;
import persistencia.ContenidoArchivo;

public class ContenidoArchivoTest {

	@TempDir
	Path carpetaTemporal;

	@Test
	public void testGuardarContenidos() throws Exception {

		Path rutaArchivo = carpetaTemporal.resolve("contenidos.csv");

		ContenidoArchivo archivo = new ContenidoArchivo(rutaArchivo.toString());

		List<ContenidoAudiovisual> contenidos = new ArrayList<>();

		// Crear una película.
		Pelicula pelicula = new Pelicula(0, "Avatar", 162, "Ciencia ficcion", "20th Century Studios");

		contenidos.add(pelicula);

		// Crear una serie.
		SerieDeTV serie = new SerieDeTV(1, "Game of Thrones", 60, "Fantasia", 8);

		contenidos.add(serie);

		// Crear un documental.
		Investigador investigador = new Investigador("Carl Sagan", "Astronomia");

		Documental documental = new Documental(2, "Cosmos", 45, "Ciencia", "Astronomia", investigador);

		contenidos.add(documental);

		// Crear un noticiero.
		Noticiero noticiero = new Noticiero(3, "Noticias Estelar", 30, "Informativo", "Carlos Vera", "Teleamazonas");

		contenidos.add(noticiero);

		// Crear un video educativo.
		VideoEducativo video = new VideoEducativo(4, "Estructuras de Datos en Java", 40, "Educativo", "Programacion",
				"Universitario");

		contenidos.add(video);

		// Guardar los contenidos en el CSV temporal.
		archivo.guardarContenidos(contenidos);

		assertTrue(Files.exists(rutaArchivo));

		List<String> lineas = Files.readAllLines(rutaArchivo);

		// Comprobar la cabecera y los cinco registros.
		assertEquals(6, lineas.size());

		assertTrue(lineas.get(0).startsWith("id,tipo,titulo"));

		assertTrue(lineas.get(1).contains("Avatar"));
		assertTrue(lineas.get(2).contains("Game of Thrones"));
		assertTrue(lineas.get(3).contains("Cosmos"));
		assertTrue(lineas.get(4).contains("Noticias Estelar"));
		assertTrue(lineas.get(5).contains("Estructuras de Datos en Java"));
	}

	@Test
	public void testGuardarContenidosListaVacia() throws Exception {

		Path rutaArchivo = carpetaTemporal.resolve("contenidos_vacios.csv");

		ContenidoArchivo archivo = new ContenidoArchivo(rutaArchivo.toString());

		List<ContenidoAudiovisual> contenidos = new ArrayList<>();

		archivo.guardarContenidos(contenidos);

		// Comprobar que el archivo fue creado.
		assertTrue(Files.exists(rutaArchivo));

		// Comprobar que solamente contiene la cabecera.
		List<String> lineas = Files.readAllLines(rutaArchivo);

		assertEquals(1, lineas.size());

		assertEquals(
				"id,tipo,titulo,duracion,genero,estudio,tema,presentador,cadenaTv,materia,nivelEducativo,temporadas",
				lineas.get(0));
	}

}
