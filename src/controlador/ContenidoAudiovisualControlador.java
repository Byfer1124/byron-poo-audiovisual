
package controlador;

import java.util.ArrayList;
import java.util.List;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Documental;
import modelo.Investigador;
import modelo.Noticiero;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.VideoEducativo;
import persistencia.ContenidoArchivo;
import vista.VistaContenido;

public class ContenidoAudiovisualControlador {

    private VistaContenido vista;
    private ContenidoArchivo archivo;
    private List<ContenidoAudiovisual> contenidos;

    public ContenidoAudiovisualControlador(
            VistaContenido vista, ContenidoArchivo archivo) {

        this.vista = vista;
        this.archivo = archivo;
        this.contenidos = new ArrayList<>();
    }

    public void iniciar() {
        int opcion;

        do {
            vista.mostrarMenu();
            opcion = vista.leerOpcion();

            switch (opcion) {
                case 1:
                    vista.mostrarContenidos(contenidos);
                    break;

                case 2:
                    contenidos = archivo.leerContenidos();
                    vista.mostrarMensaje("Contenidos cargados correctamente.");
                    break;

                case 3:
                    crearContenido();
                    break;

                case 4:
                    archivo.guardarTodo(contenidos);
                    vista.mostrarMensaje("Cambios guardados.");
                    break;

                case 5:
                    vista.mostrarMensaje("Saliendo del sistema...");
                    break;

                default:
                    vista.mostrarMensaje("Opción no válida. Intente nuevamente.");
            }

        } while (opcion != 5);

        vista.cerrar();
    }

    private void crearContenido() {

        vista.mostrarMensaje("===== CREAR CONTENIDO =====");
        vista.mostrarMensaje("1. Película");
        vista.mostrarMensaje("2. Serie de televisión");
        vista.mostrarMensaje("3. Documental");
        vista.mostrarMensaje("4. Noticiero");
        vista.mostrarMensaje("5. Video educativo");

        int tipo = vista.leerEntero("Seleccione el tipo de contenido: ");

        if (tipo < 1 || tipo > 5) {
            vista.mostrarMensaje("Tipo de contenido no válido.");
            return;
        }

        String titulo = vista.leerTexto("Ingrese el título: ");
        int duracion = vista.leerEntero("Ingrese la duración en minutos: ");
        String genero = vista.leerTexto("Ingrese el género: ");

        if (titulo.isBlank() || genero.isBlank() || duracion <= 0) {
            vista.mostrarMensaje("Los datos ingresados no son válidos.");
            return;
        }

        ContenidoAudiovisual nuevoContenido = null;

        switch (tipo) {
            case 1:
                nuevoContenido = crearPelicula(titulo, duracion, genero);
                break;

            case 2:
                nuevoContenido = crearSerie(titulo, duracion, genero);
                break;

            case 3:
                nuevoContenido = crearDocumental(titulo, duracion, genero);
                break;

            case 4:
                nuevoContenido = crearNoticiero(titulo, duracion, genero);
                break;

            case 5:
                nuevoContenido = crearVideoEducativo(titulo, duracion, genero);
                break;
        }

        if (nuevoContenido != null) {
            contenidos.add(nuevoContenido);
            vista.mostrarMensaje("Contenido registrado correctamente.");
            vista.mostrarDetalles(nuevoContenido);
        }
    }

    private Pelicula crearPelicula(String titulo, int duracion, String genero) {

        String estudio = vista.leerTexto("Ingrese el estudio cinematográfico: ");

        Pelicula pelicula = new Pelicula(titulo, duracion, genero, estudio);

        int cantidadActores = vista.leerEntero("¿Cuántos actores desea agregar? ");

        if (cantidadActores < 0) {
            vista.mostrarMensaje("La cantidad de actores no puede ser negativa.");
            return pelicula;
        }

        for (int i = 0; i < cantidadActores; i++) {
            vista.mostrarMensaje("Actor " + (i + 1));

            String nombre = vista.leerTexto("Nombre: ");
            String nacionalidad = vista.leerTexto("Nacionalidad: ");

            Actor actor = new Actor(nombre, nacionalidad);
            pelicula.agregarActor(actor);
        }

        return pelicula;
    }

    private SerieDeTV crearSerie(String titulo, int duracion, String genero) {

        int cantidadTemporadas = vista.leerEntero("Ingrese la cantidad de temporadas: ");

        if (cantidadTemporadas < 0) {
            vista.mostrarMensaje("La cantidad de temporadas no puede ser negativa.");
            return null;
        }

        SerieDeTV serie = new SerieDeTV(
                titulo, duracion, genero, cantidadTemporadas
        );

        for (int i = 0; i < cantidadTemporadas; i++) {
            vista.mostrarMensaje("Temporada " + (i + 1));

            int anio = vista.leerEntero("Año de estreno: ");
            int episodios = vista.leerEntero("Cantidad de episodios: ");

            if (anio <= 0 || episodios <= 0) {
                vista.mostrarMensaje("Datos de temporada no válidos.");
                return null;
            }

            serie.agregarTemporada(anio, episodios);
        }

        return serie;
    }

    private Documental crearDocumental(String titulo, int duracion, String genero) {

        String tema = vista.leerTexto("Ingrese el tema del documental: ");
        String nombreInvestigador = vista.leerTexto("Nombre del investigador: ");
        String especialidad = vista.leerTexto("Especialidad del investigador: ");

        Investigador investigador = new Investigador(
                nombreInvestigador, especialidad
        );

        return new Documental(
                titulo, duracion, genero, tema, investigador
        );
    }

    private Noticiero crearNoticiero(String titulo, int duracion, String genero) {

        String presentador = vista.leerTexto("Ingrese el nombre del presentador: ");
        String cadenaTv = vista.leerTexto("Ingrese la cadena de televisión: ");

        return new Noticiero(
                titulo, duracion, genero, presentador, cadenaTv
        );
    }

    private VideoEducativo crearVideoEducativo(
            String titulo, int duracion, String genero) {

        String materia = vista.leerTexto("Ingrese la materia: ");
        String nivelEducativo = vista.leerTexto("Ingrese el nivel educativo: ");

        return new VideoEducativo(
                titulo, duracion, genero, materia, nivelEducativo
        );
    }
}
