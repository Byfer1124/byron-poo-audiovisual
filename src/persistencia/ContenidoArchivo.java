
package persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import modelo.ContenidoAudiovisual;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.Documental;
import modelo.Noticiero;
import modelo.VideoEducativo;
import modelo.Investigador;
import modelo.Actor;

public class ContenidoArchivo {

    private String rutaArchivo;

    public ContenidoArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    // ==================================================
    // LECTURA DE CONTENIDOS
    // ==================================================

    public List<ContenidoAudiovisual> leerContenidos() {

        List<ContenidoAudiovisual> contenidos = new ArrayList<>();

        Map<Integer, Investigador> investigadores = leerInvestigadores();

        try (BufferedReader br = new BufferedReader(
                new FileReader(rutaArchivo))) {

            String linea;

            // Saltar encabezado
            br.readLine();

            while ((linea = br.readLine()) != null) {

                // Ignorar líneas vacías
                if (linea.isBlank()) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                int id = Integer.parseInt(datos[0]);
                String tipo = datos[1];
                String titulo = datos[2];
                int duracion = Integer.parseInt(datos[3]);
                String genero = datos[4];

                ContenidoAudiovisual contenido = null;

                switch (tipo) {

                    case "Pelicula":

                        contenido = new Pelicula(
                            id, titulo, duracion, genero, datos[5]
                        );
                        break;

                    case "SerieDeTV":

                        contenido = new SerieDeTV(
                            id, titulo, duracion, genero,
                            Integer.parseInt(datos[11])
                        );
                        break;

                    case "Documental":

                        Investigador investigador = investigadores.get(id);

                        contenido = new Documental(
                            id, titulo, duracion, genero,
                            datos[6], investigador
                        );
                        break;

                    case "Noticiero":

                        contenido = new Noticiero(
                            id, titulo, duracion, genero,
                            datos[7], datos[8]
                        );
                        break;

                    case "VideoEducativo":

                        contenido = new VideoEducativo(
                            id, titulo, duracion, genero,
                            datos[9], datos[10]
                        );
                        break;
                }

                if (contenido != null) {
                    contenidos.add(contenido);
                }
            }

        } catch (IOException e) {

            System.out.println(
                "Error al leer contenidos: " + e.getMessage()
            );
        }

        return contenidos;
    }

    // ==================================================
    // LECTURA DE INVESTIGADORES
    // ==================================================

    public Map<Integer, Investigador> leerInvestigadores() {

        Map<Integer, Investigador> investigadores = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("datos/investigadores.csv"))) {

            String linea;

            // Saltar encabezado
            br.readLine();

            while ((linea = br.readLine()) != null) {

                // Ignorar líneas vacías
                if (linea.isBlank()) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                int idDocumental = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                String especialidad = datos[2];

                Investigador investigador = new Investigador(
                    nombre, especialidad
                );

                investigadores.put(idDocumental, investigador);
            }

        } catch (IOException e) {

            System.out.println(
                "Error al leer investigadores: " + e.getMessage()
            );
        }

        return investigadores;
    }

    // ==================================================
    // LECTURA DE ACTORES
    // ==================================================

    public Map<Integer, Actor> leerActores() {

        Map<Integer, Actor> actores = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("datos/actores.csv"))) {

            String linea;

            // Saltar encabezado
            br.readLine();

            while ((linea = br.readLine()) != null) {

                // Ignorar líneas vacías
                if (linea.isBlank()) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                int id = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                String nacionalidad = datos[2];

                Actor actor = new Actor(
                    nombre, nacionalidad
                );

                actores.put(id, actor);
            }

        } catch (IOException e) {

            System.out.println(
                "Error al leer actores: " + e.getMessage()
            );
        }

        return actores;
    }

    // ==================================================
    // ASIGNAR ACTORES A LAS PELÍCULAS
    // ==================================================

    public void asignarActores(List<ContenidoAudiovisual> contenidos) {

        Map<Integer, Actor> actores = leerActores();

        // Crear un mapa de películas por ID
        Map<Integer, Pelicula> peliculas = new HashMap<>();

        for (ContenidoAudiovisual contenido : contenidos) {

            if (contenido instanceof Pelicula pelicula) {

                peliculas.put(pelicula.getId(), pelicula);
            }
        }

        try (BufferedReader br = new BufferedReader(
                new FileReader("datos/pelicula_actores.csv"))) {

            String linea;

            // Saltar encabezado
            br.readLine();

            while ((linea = br.readLine()) != null) {

                // Ignorar líneas vacías
                if (linea.isBlank()) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                int idPelicula = Integer.parseInt(datos[0]);
                int idActor = Integer.parseInt(datos[1]);

                Pelicula pelicula = peliculas.get(idPelicula);
                Actor actor = actores.get(idActor);

                // Comprobar que ambos existan
                if (pelicula != null && actor != null) {

                    pelicula.agregarActor(actor);
                }
            }

        } catch (IOException e) {

            System.out.println(
                "Error al leer relaciones película-actor: " + e.getMessage()
            );
        }
    }

    // ==================================================
    // ASIGNAR TEMPORADAS A LAS SERIES
    // ==================================================

    public void asignarTemporadas(List<ContenidoAudiovisual> contenidos) {

        // Crear un mapa de series por ID
        Map<Integer, SerieDeTV> series = new HashMap<>();

        for (ContenidoAudiovisual contenido : contenidos) {

            if (contenido instanceof SerieDeTV serie) {

                series.put(serie.getId(), serie);
            }
        }

        try (BufferedReader br = new BufferedReader(
                new FileReader("datos/temporadas.csv"))) {

            String linea;

            // Saltar encabezado
            br.readLine();

            while ((linea = br.readLine()) != null) {

                // Ignorar líneas vacías
                if (linea.isBlank()) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                int idSerie = Integer.parseInt(datos[0]);
                int anioEstreno = Integer.parseInt(datos[1]);
                int cantidadEpisodios = Integer.parseInt(datos[2]);

                SerieDeTV serie = series.get(idSerie);

                // Comprobar que la serie exista
                if (serie != null) {

                    serie.agregarTemporada(
                        anioEstreno, cantidadEpisodios
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                "Error al leer temporadas: " + e.getMessage()
            );
        }
    }
}
