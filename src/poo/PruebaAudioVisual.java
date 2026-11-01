package poo;

import uni1a.*;

public class PruebaAudioVisual {
	public static void main(String[] args) {
		System.out.println("Hello from Eclipse!");

		// Creacion del arreglo de ContenidoAudiovisual
		ContenidoAudiovisual[] contenidos = new ContenidoAudiovisual[5];

		/* Agregacion: Pelicula y Actor */

		// Crear actores
		Actor actor1 = new Actor("Leonardo DiCaprio", "Estadounidense");
		Actor actor2 = new Actor("Tom Hardy", "Británica");
		Actor actor3 = new Actor("Cillian Murphy", "Irlandesa");

		// Crear película
		Pelicula pelicula = new Pelicula("Avatar", 125, "Accion", "20th Century Studios");

		// Agregar actores a la película
		pelicula.agregarActor(actor1);
		pelicula.agregarActor(actor2);
		pelicula.agregarActor(actor3);

		contenidos[0] = pelicula;

		// -------------------------------------------------------------------------

		
		
		/* Composicion: SerieDeTV y Temporada */
		SerieDeTV serie = new SerieDeTV("Game of Thrones", 60, "Fantasy", 8);

		// Crear temporadas desde la propia serie
		serie.agregarTemporada(2008, 7);

		contenidos[1] = serie;

		// -------------------------------------------------------------------------

		
		
		/* Asociacion: Documental e Investigador */
		Investigador investigador = new Investigador("Carlos Pérez", "Historia");

		Documental documental = new Documental("Cosmos", 45, "Science", "Astronomy", investigador);

		/*
		 * documental.mostrarDetalles();
		 */

		contenidos[2] = documental;

		// -------------------------------------------------------------------------

		// Creacion de instancias de las clases nuevas: Noticiero, VideoEducativo
		Noticiero noticiero = new Noticiero("Noticias Estelar", 60, "Informativo", "Carlos Vera", "Teleamazonas");
	    VideoEducativo videoEducativo = new VideoEducativo("Estructuras de Datos en Java", 50, "Educativo", "Ingeniería de Software", "Universitario");
	    
	    contenidos[3] = noticiero;
	    contenidos[4] = videoEducativo;
		// -------------------------------------------------------------------------
		
				
	   // Mostrar los detalles de cada contenido audiovisual
		for (ContenidoAudiovisual contenido : contenidos) {
			contenido.mostrarDetalles();
		}
	}
}