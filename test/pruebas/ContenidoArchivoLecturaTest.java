
package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import modelo.ContenidoAudiovisual;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.Documental;
import modelo.Noticiero;
import modelo.VideoEducativo;
import persistencia.ContenidoArchivo;

public class ContenidoArchivoLecturaTest {

	@TempDir
	Path carpetaTemporal;

	@Test
	public void testLeerContenidos() throws Exception {

		Path rutaContenidos = carpetaTemporal.resolve("contenidos.csv");

		Path rutaActores = carpetaTemporal.resolve("actores.csv");

		Path rutaInvestigadores = carpetaTemporal.resolve("investigadores.csv");

		Path rutaTemporadas = carpetaTemporal.resolve("temporadas.csv");

		Path rutaRelaciones = carpetaTemporal.resolve("pelicula_actores.csv");

		// Crear el archivo principal.
		Files.writeString(rutaContenidos, "id,tipo,titulo,duracion,genero,estudio,tema,"
				+ "presentador,cadenaTv,materia,nivelEducativo,temporadas\n"
				+ "0,Pelicula,Avatar,162,Ciencia ficcion,20th Century,,,,,,\n"
				+ "1,SerieDeTV,Game of Thrones,60,Fantasia,,,,,,,8\n"
				+ "2,Documental,Cosmos,45,Ciencia,,Astronomia,,,,,\n"
				+ "3,Noticiero,Noticias Estelar,30,Informativo,,,Carlos Perez,Estelar TV,,,\n"
				+ "4,VideoEducativo,Estructuras de Datos en Java,40,Educativo,,,,,Programacion,Universitario,\n");

		// Crear los archivos relacionados vacíos, con sus cabeceras.
		Files.writeString(rutaActores, "id,nombre\n");
		Files.writeString(rutaInvestigadores, "id,nombre,especialidad\n");
		Files.writeString(rutaTemporadas, "idSerie,anio\n");
		Files.writeString(rutaRelaciones, "idPelicula,idActor\n");

		ContenidoArchivo archivo = new ContenidoArchivo(rutaContenidos.toString(), rutaActores.toString(),
				rutaInvestigadores.toString(), rutaTemporadas.toString(), rutaRelaciones.toString());

		List<ContenidoAudiovisual> contenidos = archivo.leerContenidos();

		// Comprobar que se leyeron los cinco contenidos.
		assertEquals(5, contenidos.size());

		// Comprobar el tipo de cada contenido.
		assertInstanceOf(Pelicula.class, contenidos.get(0));
		assertInstanceOf(SerieDeTV.class, contenidos.get(1));
		assertInstanceOf(Documental.class, contenidos.get(2));
		assertInstanceOf(Noticiero.class, contenidos.get(3));
		assertInstanceOf(VideoEducativo.class, contenidos.get(4));

		// Comprobar algunos datos de los contenidos.
		assertEquals("Avatar", contenidos.get(0).getTitulo());
		assertEquals("Game of Thrones", contenidos.get(1).getTitulo());
		assertEquals("Cosmos", contenidos.get(2).getTitulo());
		assertEquals("Noticias Estelar", contenidos.get(3).getTitulo());
		assertEquals("Estructuras de Datos en Java", contenidos.get(4).getTitulo());
	}

	@Test
	public void testLeerContenidosArchivoInexistente() {

		Path rutaContenidos = carpetaTemporal.resolve("archivo_inexistente.csv");

		Path rutaActores = carpetaTemporal.resolve("actores.csv");

		Path rutaInvestigadores = carpetaTemporal.resolve("investigadores.csv");

		Path rutaTemporadas = carpetaTemporal.resolve("temporadas.csv");

		Path rutaRelaciones = carpetaTemporal.resolve("pelicula_actores.csv");

		ContenidoArchivo archivo = new ContenidoArchivo(rutaContenidos.toString(), rutaActores.toString(),
				rutaInvestigadores.toString(), rutaTemporadas.toString(), rutaRelaciones.toString());

		List<ContenidoAudiovisual> contenidos = archivo.leerContenidos();

		assertEquals(0, contenidos.size());
	}

}
