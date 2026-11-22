
package vista;

import java.util.List;
import java.util.Scanner;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Documental;
import modelo.Noticiero;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.Temporada;
import modelo.VideoEducativo;

public class ContenidoAudiovisualVista implements VistaContenido {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE CONTENIDO AUDIOVISUAL =====");
        System.out.println("1. Mostrar contenidos");
        System.out.println("2. Leer contenidos desde CSV");
        System.out.println("3. Crear contenido");
        System.out.println("4. Guardar contenidos en CSV");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    @Override
    public int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    @Override
    public int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    @Override
    public void mostrarContenidos(List<ContenidoAudiovisual> contenidos) {
        if (contenidos.isEmpty()) {
            System.out.println("\nNo hay contenidos registrados.");
            return;
        }

        for (ContenidoAudiovisual contenido : contenidos) {
            mostrarDetalles(contenido);
        }
    }

    @Override
    public void mostrarDetalles(ContenidoAudiovisual contenido) {
        System.out.println("\n----------------------------------------");
        System.out.println("ID: " + contenido.getId());
        System.out.println("Título: " + contenido.getTitulo());
        System.out.println("Duración: " + contenido.getDuracionEnMinutos() + " minutos");
        System.out.println("Género: " + contenido.getGenero());

        if (contenido instanceof Pelicula) {
            Pelicula pelicula = (Pelicula) contenido;
            System.out.println("Estudio: " + pelicula.getEstudio());
            System.out.println("Actores:");

            for (Actor actor : pelicula.getActores()) {
                System.out.println("- " + actor.getNombre());
            }

        } else if (contenido instanceof Documental) {
            Documental documental = (Documental) contenido;
            System.out.println("Tema: " + documental.getTema());

            if (documental.getInvestigadorPrincipal() != null) {
                System.out.println("Investigador: "
                        + documental.getInvestigadorPrincipal().getNombre());
            } else {
                System.out.println("Investigador: No asignado");
            }

        } else if (contenido instanceof SerieDeTV) {
            SerieDeTV serie = (SerieDeTV) contenido;
            System.out.println("Temporadas: " + serie.getDetallesTemporadas().size());

            for (Temporada temporada : serie.getDetallesTemporadas()) {
                System.out.println("- Año: " + temporada.getAnioEstreno()
                        + ", episodios: " + temporada.getCantidadEpisodios());
            }

        } else if (contenido instanceof Noticiero) {
            Noticiero noticiero = (Noticiero) contenido;
            System.out.println("Presentador: " + noticiero.getPresentador());
            System.out.println("Cadena de TV: " + noticiero.getCadenaTv());

        } else if (contenido instanceof VideoEducativo) {
            VideoEducativo video = (VideoEducativo) contenido;
            System.out.println("Materia: " + video.getMateria());
            System.out.println("Nivel educativo: " + video.getNivelEducativo());
        }

        System.out.println("----------------------------------------");
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println("\n" + mensaje);
    }

    @Override
    public void cerrar() {
        scanner.close();
    }
}
