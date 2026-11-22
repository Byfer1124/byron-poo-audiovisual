
package poo;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Documental;
import modelo.Investigador;
import modelo.Noticiero;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.VideoEducativo;

import vista.ContenidoAudiovisualVista;
import controlador.ContenidoAudiovisualControlador;
import persistencia.ContenidoArchivo;

import java.util.List;
import java.util.Map;
import modelo.Temporada;

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
        Pelicula pelicula = new Pelicula(
            "Avatar", 125, "Accion", "20th Century Studios"
        );

        // Agregar actores a la película
        pelicula.agregarActor(actor1);
        pelicula.agregarActor(actor2);
        pelicula.agregarActor(actor3);

        contenidos[0] = pelicula;

        // --------------------------------------------------

        /* Composicion: SerieDeTV y Temporada */

        SerieDeTV serie = new SerieDeTV(
            "Game of Thrones", 60, "Fantasy", 8
        );

        // Crear temporadas desde la propia serie
        serie.agregarTemporada(2008, 7);

        contenidos[1] = serie;

        // --------------------------------------------------

        /* Asociacion: Documental e Investigador */

        Investigador investigador = new Investigador(
            "Carlos Pérez", "Historia"
        );

        Documental documental = new Documental(
            "Cosmos", 45, "Science", "Astronomy", investigador
        );

        contenidos[2] = documental;

        // --------------------------------------------------

        // Crear Noticiero y VideoEducativo

        Noticiero noticiero = new Noticiero(
            "Noticias Estelar", 60, "Informativo",
            "Carlos Vera", "Teleamazonas"
        );

        VideoEducativo videoEducativo = new VideoEducativo(
            "Estructuras de Datos en Java", 50, "Educativo",
            "Ingeniería de Software", "Universitario"
        );

        contenidos[3] = noticiero;
        contenidos[4] = videoEducativo;

        // --------------------------------------------------

        // Crear vista y controlador

        ContenidoAudiovisualVista vista =
            new ContenidoAudiovisualVista();

        ContenidoAudiovisualControlador controlador =
            new ContenidoAudiovisualControlador(vista);

        // Mostrar contenidos creados inicialmente
        controlador.mostrarContenidos(contenidos);

        // ==================================================
        // LECTURA DE ARCHIVOS CSV
        // ==================================================

        ContenidoArchivo archivo =
            new ContenidoArchivo("datos/contenidos.csv");

        List<ContenidoAudiovisual> contenidosLeidos =
            archivo.leerContenidos();

        System.out.println("\nCONTENIDOS LEÍDOS DESDE EL CSV:");

        for (ContenidoAudiovisual contenido : contenidosLeidos) {

            System.out.println(
                contenido.getId() + " - " +
                contenido.getTitulo() + " - " +
                contenido.getClass().getSimpleName()
            );
        }

        // ==================================================
        // LECTURA DE INVESTIGADORES
        // ==================================================

        Map<Integer, Investigador> investigadores =
            archivo.leerInvestigadores();

        System.out.println("\nINVESTIGADORES LEÍDOS DESDE EL CSV:");

        for (Map.Entry<Integer, Investigador> entrada
                : investigadores.entrySet()) {

            System.out.println(
                "ID del documental: " + entrada.getKey()
                + " - Investigador: " + entrada.getValue().getNombre()
                + " - Especialidad: " + entrada.getValue().getEspecialidad()
            );
        }

        // ==================================================
        // COMPROBAR INVESTIGADOR ASIGNADO AL DOCUMENTAL
        // ==================================================

        System.out.println("\nCOMPROBACIÓN DE INVESTIGADOR ASIGNADO:");

        for (ContenidoAudiovisual contenido : contenidosLeidos) {

            if (contenido instanceof Documental documentalLeido) {

                System.out.println(
                    "Documental: " + documentalLeido.getTitulo()
                );

                if (documentalLeido.getInvestigadorPrincipal() != null) {

                    System.out.println(
                        "Investigador: " +
                        documentalLeido.getInvestigadorPrincipal().getNombre()
                    );

                    System.out.println(
                        "Especialidad: " +
                        documentalLeido.getInvestigadorPrincipal().getEspecialidad()
                    );

                } else {

                    System.out.println(
                        "No tiene investigador asignado."
                    );
                }
            }
        }

        // ==================================================
        // LECTURA DE ACTORES
        // ==================================================

        Map<Integer, Actor> actores = archivo.leerActores();

        System.out.println("\nACTORES LEÍDOS DESDE EL CSV:");

        for (Map.Entry<Integer, Actor> entrada : actores.entrySet()) {

            System.out.println(
                "ID: " + entrada.getKey()
                + " - Nombre: " + entrada.getValue().getNombre()
                + " - Nacionalidad: " + entrada.getValue().getNacionalidad()
            );
        }

        // ==================================================
        // ASIGNAR ACTORES A LAS PELÍCULAS LEÍDAS
        /*
         Esta instrucción envía la lista de contenidos que se leyó desde contenidos.
         csv al método asignarActores() de ContenidoArchivo.
         Después, el último bloque recorre los contenidos,
         identifica las películas usando instanceof y muestra sus actores.
         */
        // ==================================================
        
        /*archivo.asignarActores(contenidosLeidos);*/ //E

        // ==================================================
        // COMPROBAR ACTORES ASIGNADOS A LAS PELÍCULAS
        // ==================================================

        System.out.println("\nCOMPROBACIÓN DE ACTORES ASIGNADOS:");

        for (ContenidoAudiovisual contenido : contenidosLeidos) {

            if (contenido instanceof Pelicula peliculaLeida) {

                System.out.println(
                    "\nPelícula: " + peliculaLeida.getTitulo()
                );

                System.out.println("Actores:");

                for (Actor actor : peliculaLeida.getActores()) {

                    System.out.println(
                        "- " + actor.getNombre() +
                        " (" + actor.getNacionalidad() + ")"
                    );
                }
            }
        }
        
     // ==================================================
     // ASIGNAR TEMPORADAS A LAS SERIES LEÍDAS
     // ==================================================

     /*archivo.asignarTemporadas(contenidosLeidos);*/ //E

     // ==================================================
     // COMPROBAR TEMPORADAS ASIGNADAS
     // ==================================================

     System.out.println("\nCOMPROBACIÓN DE TEMPORADAS ASIGNADAS:");

     for (ContenidoAudiovisual contenido : contenidosLeidos) {

         if (contenido instanceof SerieDeTV serieLeida) {

             System.out.println("\nSerie: " + serieLeida.getTitulo());

             for (Temporada temporada : serieLeida.getDetallesTemporadas()) {

                 System.out.println(
                     "- Año de estreno: " + temporada.getAnioEstreno()
                     + " | Episodios: " + temporada.getCantidadEpisodios()
                 );
             }
         }
     }
     
     // ==================================================
     // GUARDAR INFORMACIÓN EN LOS ARCHIVOS CSV
     // ==================================================

     System.out.println("\nGUARDANDO INFORMACION EN LOS ARCHIVOS CSV:");

     archivo.guardarTodo(contenidosLeidos);
  }
}
