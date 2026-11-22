
package vista;

import modelo.ContenidoAudiovisual;
import modelo.Pelicula;
import modelo.Documental;
import modelo.SerieDeTV;
import modelo.Noticiero;
import modelo.VideoEducativo;
import modelo.Actor;
import modelo.Investigador;
import modelo.Temporada;

public class ContenidoAudiovisualVista implements VistaContenido {

    @Override
    public void mostrarDetalles(ContenidoAudiovisual contenido) {

        System.out.println("ID: " + contenido.getId());
        System.out.println("Título: " + contenido.getTitulo());
        System.out.println("Duración en minutos: " + contenido.getDuracionEnMinutos());
        System.out.println("Género: " + contenido.getGenero());

        if (contenido instanceof Pelicula pelicula) {

            System.out.println("Estudio: " + pelicula.getEstudio());
            System.out.println("Actores:");

            for (Actor actor : pelicula.getActores()) {
                System.out.println("Nombre: " + actor.getNombre());
                System.out.println("Nacionalidad: " + actor.getNacionalidad());
            }

        } else if (contenido instanceof Documental documental) {

            System.out.println("Tema: " + documental.getTema());

            Investigador investigador = documental.getInvestigadorPrincipal();

            if (investigador != null) {
                System.out.println("Investigador principal:");
                System.out.println("Nombre: " + investigador.getNombre());
                System.out.println("Especialidad: " + investigador.getEspecialidad());
            } else {
                System.out.println("No tiene investigador asignado.");
            }

        } else if (contenido instanceof SerieDeTV serie) {

            System.out.println("Cantidad de temporadas: " + serie.getTemporadas());
            System.out.println("Detalles de las temporadas:");

            for (Temporada temporada : serie.getDetallesTemporadas()) {
                System.out.println("Año de estreno: " + temporada.getAnioEstreno());
                System.out.println("Cantidad de episodios: " + temporada.getCantidadEpisodios());
            }

        } else if (contenido instanceof Noticiero noticiero) {

            System.out.println("Presentador: " + noticiero.getPresentador());
            System.out.println("Cadena de TV: " + noticiero.getCadenaTv());

        } else if (contenido instanceof VideoEducativo video) {

            System.out.println("Materia: " + video.getMateria());
            System.out.println("Nivel educativo: " + video.getNivelEducativo());
        }

        System.out.println("------------------------");
    }
}
