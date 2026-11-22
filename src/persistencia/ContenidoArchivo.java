
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private ActorArchivo actorArchivo;
    private InvestigadorArchivo investigadorArchivo;
    private TemporadaArchivo temporadaArchivo;
    private RelacionPeliculaActorArchivo relacionArchivo;

    public ContenidoArchivo(String rutaArchivo) {

        this.rutaArchivo = rutaArchivo;

        this.actorArchivo = new ActorArchivo("datos/actores.csv");

        this.investigadorArchivo = new InvestigadorArchivo("datos/investigadores.csv");

        this.temporadaArchivo = new TemporadaArchivo("datos/temporadas.csv");

        this.relacionArchivo = new RelacionPeliculaActorArchivo(
                "datos/pelicula_actores.csv", "datos/actores.csv");
    }

    public ContenidoArchivo(String rutaArchivo, String rutaActores,
            String rutaInvestigadores, String rutaTemporadas,
            String rutaRelaciones) {

        this.rutaArchivo = rutaArchivo;

        this.actorArchivo = new ActorArchivo(rutaActores);

        this.investigadorArchivo = new InvestigadorArchivo(rutaInvestigadores);

        this.temporadaArchivo = new TemporadaArchivo(rutaTemporadas);

        this.relacionArchivo = new RelacionPeliculaActorArchivo(
                rutaRelaciones, rutaActores);
    }

    public List<ContenidoAudiovisual> leerContenidos() {

        List<ContenidoAudiovisual> contenidos = new ArrayList<>();

        Map<Integer, Investigador> investigadores =
                investigadorArchivo.leerInvestigadores();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {

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

                int id = Integer.parseInt(datos[0]);
                String tipo = datos[1];
                String titulo = datos[2];
                int duracion = Integer.parseInt(datos[3]);
                String genero = datos[4];

                ContenidoAudiovisual contenido = null;

                switch (tipo) {

                    case "Pelicula":
                        contenido = new Pelicula(id, titulo, duracion, genero, datos[5]);
                        break;

                    case "SerieDeTV":
                        contenido = new SerieDeTV(
                                id, titulo, duracion, genero,
                                Integer.parseInt(datos[11]));
                        break;

                    case "Documental":
                        contenido = new Documental(
                                id, titulo, duracion, genero,
                                datos[6], investigadores.get(id));
                        break;

                    case "Noticiero":
                        contenido = new Noticiero(
                                id, titulo, duracion, genero,
                                datos[7], datos[8]);
                        break;

                    case "VideoEducativo":
                        contenido = new VideoEducativo(
                                id, titulo, duracion, genero,
                                datos[9], datos[10]);
                        break;

                    default:
                        System.out.println(
                                "Tipo de contenido desconocido: " + tipo);
                        break;
                }

                if (contenido != null) {
                    contenidos.add(contenido);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer contenidos: " + e.getMessage());
        }

        // Asignar automáticamente las relaciones de los contenidos.
        relacionArchivo.asignarActores(contenidos);
        temporadaArchivo.asignarTemporadas(contenidos);

        return contenidos;
    }

    public void guardarContenidos(List<ContenidoAudiovisual> contenidos) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {

            bw.write("id,tipo,titulo,duracion,genero,estudio,tema,"
                    + "presentador,cadenaTv,materia,nivelEducativo,temporadas");
            bw.newLine();

            for (ContenidoAudiovisual contenido : contenidos) {

                String tipo = contenido.getClass().getSimpleName();

                String estudio = "";
                String tema = "";
                String presentador = "";
                String cadenaTv = "";
                String materia = "";
                String nivelEducativo = "";
                String temporadas = "";

                if (contenido instanceof Pelicula) {
                    Pelicula pelicula = (Pelicula) contenido;
                    estudio = pelicula.getEstudio();
                }

                if (contenido instanceof SerieDeTV) {
                    SerieDeTV serie = (SerieDeTV) contenido;
                    temporadas = String.valueOf(serie.getTemporadas());
                }

                if (contenido instanceof Documental) {
                    Documental documental = (Documental) contenido;
                    tema = documental.getTema();
                }

                if (contenido instanceof Noticiero) {
                    Noticiero noticiero = (Noticiero) contenido;
                    presentador = noticiero.getPresentador();
                    cadenaTv = noticiero.getCadenaTv();
                }

                if (contenido instanceof VideoEducativo) {
                    VideoEducativo video = (VideoEducativo) contenido;
                    materia = video.getMateria();
                    nivelEducativo = video.getNivelEducativo();
                }

                String[] datos = {
                    String.valueOf(contenido.getId()),
                    tipo,
                    contenido.getTitulo(),
                    String.valueOf(contenido.getDuracionEnMinutos()),
                    contenido.getGenero(),
                    estudio,
                    tema,
                    presentador,
                    cadenaTv,
                    materia,
                    nivelEducativo,
                    temporadas
                };

                bw.write(convertirNulos(datos));
                bw.newLine();
            }

            System.out.println("Contenidos guardados correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar contenidos: " + e.getMessage());
        }
    }

    public Map<Integer, Investigador> leerInvestigadores() {
        return investigadorArchivo.leerInvestigadores();
    }

    public Map<Integer, Actor> leerActores() {
        return actorArchivo.leerActores();
    }

    public void guardarActores(List<ContenidoAudiovisual> contenidos) {

        Map<Actor, Integer> ids = new IdentityHashMap<>();
        Map<Integer, Actor> actores = new LinkedHashMap<>();

        int id = 0;

        for (ContenidoAudiovisual contenido : contenidos) {

            if (contenido instanceof Pelicula) {

                Pelicula pelicula = (Pelicula) contenido;

                for (Actor actor : pelicula.getActores()) {

                    if (actor != null && !ids.containsKey(actor)) {

                        ids.put(actor, id);
                        actores.put(id, actor);
                        id++;
                    }
                }
            }
        }

        actorArchivo.guardarActores(actores);
    }

    public void asignarActores(List<ContenidoAudiovisual> contenidos) {
        relacionArchivo.asignarActores(contenidos);
    }

    public void asignarTemporadas(List<ContenidoAudiovisual> contenidos) {
        temporadaArchivo.asignarTemporadas(contenidos);
    }

    public void guardarRelacionesPeliculaActores(
            List<ContenidoAudiovisual> contenidos) {

        relacionArchivo.guardarRelacionesPeliculaActores(contenidos);
    }

    public void guardarTemporadas(List<ContenidoAudiovisual> contenidos) {
        temporadaArchivo.guardarTemporadas(contenidos);
    }

    public void guardarInvestigadores(List<ContenidoAudiovisual> contenidos) {

        Map<Integer, Investigador> investigadores = new LinkedHashMap<>();

        for (ContenidoAudiovisual contenido : contenidos) {

            if (contenido instanceof Documental) {

                Documental documental = (Documental) contenido;

                Investigador investigador =
                        documental.getInvestigadorPrincipal();

                if (investigador != null) {
                    investigadores.put(documental.getId(), investigador);
                }
            }
        }

        investigadorArchivo.guardarInvestigadores(investigadores);
    }

    public void guardarTodo(List<ContenidoAudiovisual> contenidos) {

        guardarContenidos(contenidos);
        guardarActores(contenidos);
        guardarRelacionesPeliculaActores(contenidos);
        guardarTemporadas(contenidos);
        guardarInvestigadores(contenidos);

        System.out.println("Proceso de guardado finalizado.");
    }

    private String convertirNulos(String[] datos) {

        for (int i = 0; i < datos.length; i++) {

            if (datos[i] == null) {
                datos[i] = "";
            }
        }

        return String.join(",", datos);
    }
}
