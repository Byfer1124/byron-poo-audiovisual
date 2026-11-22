
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Pelicula;

public class RelacionPeliculaActorArchivo {

    private String rutaArchivo;
    private String rutaActores;

    public RelacionPeliculaActorArchivo(String rutaArchivo, String rutaActores) {
        this.rutaArchivo = rutaArchivo;
        this.rutaActores = rutaActores;
    }

    // Lee las relaciones y asigna los actores a sus respectivas películas.
    public void asignarActores(List<ContenidoAudiovisual> contenidos) {

        Map<Integer, Actor> actores = new ActorArchivo(rutaActores).leerActores();
        Map<Integer, Pelicula> peliculas = new HashMap<>();

        for (ContenidoAudiovisual contenido : contenidos) {

            if (contenido instanceof Pelicula pelicula) {
                peliculas.put(pelicula.getId(), pelicula);
            }
        }

        try (BufferedReader br = new BufferedReader(
                new FileReader(rutaArchivo))) {

            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {

                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                int idPelicula = Integer.parseInt(datos[0]);
                int idActor = Integer.parseInt(datos[1]);

                Pelicula pelicula = peliculas.get(idPelicula);
                Actor actor = actores.get(idActor);

                if (pelicula != null && actor != null) {
                    pelicula.agregarActor(actor);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al asignar actores: " + e.getMessage());
        }
    }

    // Guarda las relaciones entre películas y actores.
    public void guardarRelacionesPeliculaActores(
            List<ContenidoAudiovisual> contenidos) {

        Map<Actor, Integer> idsActores = construirIdsActores(contenidos);

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(rutaArchivo))) {

            bw.write("idPelicula,idActor");
            bw.newLine();

            for (ContenidoAudiovisual contenido : contenidos) {

                if (contenido instanceof Pelicula pelicula) {

                    for (Actor actor : pelicula.getActores()) {

                        Integer idActor = idsActores.get(actor);

                        if (idActor != null) {
                            bw.write(pelicula.getId() + "," + idActor);
                            bw.newLine();
                        }
                    }
                }
            }

            System.out.println(
                    "Relaciones entre películas y actores guardadas correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar relaciones: " + e.getMessage());
        }
    }

    // Construye un mapa para relacionar cada actor con su ID.
    private Map<Actor, Integer> construirIdsActores(
            List<ContenidoAudiovisual> contenidos) {

        Map<Actor, Integer> ids = new IdentityHashMap<>();
        int id = 0;

        for (ContenidoAudiovisual contenido : contenidos) {

            if (contenido instanceof Pelicula pelicula) {

                for (Actor actor : pelicula.getActores()) {

                    if (actor != null && !ids.containsKey(actor)) {
                        ids.put(actor, id);
                        id++;
                    }
                }
            }
        }

        return ids;
    }
}

