
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
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
import modelo.Temporada;

public class ContenidoArchivo {

    private String rutaArchivo;

    public ContenidoArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    // =====================================================
    // METODOS DE LECTURA
    // =====================================================

    public List<ContenidoAudiovisual> leerContenidos() {

        List<ContenidoAudiovisual> contenidos = new ArrayList<>();

        Map<Integer, Investigador> investigadores = leerInvestigadores();

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
                        contenido = new Pelicula(
                                id, titulo, duracion, genero, datos[5]);
                        break;

                    case "SerieDeTV":
                        contenido = new SerieDeTV(
                                id, titulo, duracion, genero,
                                Integer.parseInt(datos[11]));
                        break;

                    case "Documental":
                        Investigador investigador = investigadores.get(id);

                        contenido = new Documental(
                                id, titulo, duracion, genero,
                                datos[6], investigador);
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
                }

                if (contenido != null) {
                    contenidos.add(contenido);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer contenidos: " + e.getMessage());
        }

        return contenidos;
    }

    public Map<Integer, Investigador> leerInvestigadores() {

        Map<Integer, Investigador> investigadores = new java.util.HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("datos/investigadores.csv"))) {

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

                int idDocumental = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                String especialidad = datos[2];

                Investigador investigador =
                        new Investigador(nombre, especialidad);

                investigadores.put(idDocumental, investigador);
            }

        } catch (IOException e) {
            System.out.println("Error al leer investigadores: " + e.getMessage());
        }

        return investigadores;
    }

    public Map<Integer, Actor> leerActores() {

        Map<Integer, Actor> actores = new java.util.HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("datos/actores.csv"))) {

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
                String nombre = datos[1];
                String nacionalidad = datos[2];

                Actor actor = new Actor(nombre, nacionalidad);

                actores.put(id, actor);
            }

        } catch (IOException e) {
            System.out.println("Error al leer actores: " + e.getMessage());
        }

        return actores;
    }

    public void asignarActores(List<ContenidoAudiovisual> contenidos) {

        Map<Integer, Actor> actores = leerActores();
        Map<Integer, Pelicula> peliculas = new java.util.HashMap<>();

        for (ContenidoAudiovisual contenido : contenidos) {

            if (contenido instanceof Pelicula pelicula) {
                peliculas.put(pelicula.getId(), pelicula);
            }
        }

        try (BufferedReader br = new BufferedReader(
                new FileReader("datos/pelicula_actores.csv"))) {

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

    public void asignarTemporadas(List<ContenidoAudiovisual> contenidos) {

        Map<Integer, SerieDeTV> series = new java.util.HashMap<>();

        for (ContenidoAudiovisual contenido : contenidos) {

            if (contenido instanceof SerieDeTV serie) {
                series.put(serie.getId(), serie);
            }
        }

        try (BufferedReader br = new BufferedReader(
                new FileReader("datos/temporadas.csv"))) {

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

                int idSerie = Integer.parseInt(datos[0]);
                int anioEstreno = Integer.parseInt(datos[1]);
                int cantidadEpisodios = Integer.parseInt(datos[2]);

                SerieDeTV serie = series.get(idSerie);

                if (serie != null) {
                    serie.agregarTemporada(anioEstreno, cantidadEpisodios);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al asignar temporadas: " + e.getMessage());
        }
    }

    // =====================================================
    // METODOS DE ESCRITURA
    // =====================================================

    // Guarda los datos generales de todos los contenidos.
    public void guardarContenidos(List<ContenidoAudiovisual> contenidos) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {

            bw.write("id,tipo,titulo,duracion,genero,estudio,tema,presentador,cadenaTv,materia,nivelEducativo,temporadas");
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

                if (contenido instanceof Pelicula pelicula) {
                    estudio = pelicula.getEstudio();
                }

                else if (contenido instanceof Documental documental) {
                    tema = documental.getTema();
                }

                else if (contenido instanceof Noticiero noticiero) {
                    presentador = noticiero.getPresentador();
                    cadenaTv = noticiero.getCadenaTv();
                }

                else if (contenido instanceof VideoEducativo video) {
                    materia = video.getMateria();
                    nivelEducativo = video.getNivelEducativo();
                }

                else if (contenido instanceof SerieDeTV serie) {
                    temporadas = String.valueOf(serie.getTemporadas());
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

                bw.write(String.join(",", convertirNulos(datos)));
                bw.newLine();
            }

            System.out.println("Contenidos guardados correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar contenidos: " + e.getMessage());
        }
    }

    // Guarda todos los actores sin repetir la misma instancia.
    public void guardarActores(List<ContenidoAudiovisual> contenidos) {

        List<Actor> actores = obtenerActoresUnicos(contenidos);

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("datos/actores.csv"))) {

            bw.write("id,nombre,nacionalidad");
            bw.newLine();

            for (int i = 0; i < actores.size(); i++) {

                Actor actor = actores.get(i);

                String[] datos = {
                    String.valueOf(i),
                    actor.getNombre(),
                    actor.getNacionalidad()
                };

                bw.write(String.join(",", convertirNulos(datos)));
                bw.newLine();
            }

            System.out.println("Actores guardados correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar actores: " + e.getMessage());
        }
    }

    // Guarda la relación entre cada película y sus actores.
    public void guardarRelacionesPeliculaActores(
            List<ContenidoAudiovisual> contenidos) {

        Map<Actor, Integer> idsActores = construirIdsActores(contenidos);

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("datos/pelicula_actores.csv"))) {

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

            System.out.println("Relaciones entre películas y actores guardadas correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar relaciones: " + e.getMessage());
        }
    }

    // Guarda las temporadas de cada serie.
    public void guardarTemporadas(List<ContenidoAudiovisual> contenidos) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("datos/temporadas.csv"))) {

            bw.write("idSerie,anioEstreno,cantidadEpisodios");
            bw.newLine();

            for (ContenidoAudiovisual contenido : contenidos) {

                if (contenido instanceof SerieDeTV serie) {

                    for (Temporada temporada : serie.getDetallesTemporadas()) {

                        bw.write(
                            serie.getId() + ","
                            + temporada.getAnioEstreno() + ","
                            + temporada.getCantidadEpisodios()
                        );

                        bw.newLine();
                    }
                }
            }

            System.out.println("Temporadas guardadas correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar temporadas: " + e.getMessage());
        }
    }

    // Guarda los investigadores asociados a los documentales.
    public void guardarInvestigadores(List<ContenidoAudiovisual> contenidos) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("datos/investigadores.csv"))) {

            bw.write("idDocumental,nombre,especialidad");
            bw.newLine();

            for (ContenidoAudiovisual contenido : contenidos) {

                if (contenido instanceof Documental documental) {

                    Investigador investigador =
                            documental.getInvestigadorPrincipal();

                    if (investigador != null) {

                        bw.write(
                            documental.getId() + ","
                            + convertirNulo(investigador.getNombre()) + ","
                            + convertirNulo(investigador.getEspecialidad())
                        );

                        bw.newLine();
                    }
                }
            }

            System.out.println("Investigadores guardados correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar investigadores: " + e.getMessage());
        }
    }

    // Ejecuta todos los métodos de escritura.
    public void guardarTodo(List<ContenidoAudiovisual> contenidos) {

        guardarContenidos(contenidos);
        guardarActores(contenidos);
        guardarRelacionesPeliculaActores(contenidos);
        guardarTemporadas(contenidos);
        guardarInvestigadores(contenidos);

        System.out.println("Proceso de guardado finalizado.");
    }

    // =====================================================
    // METODOS AUXILIARES
    // =====================================================

    // Obtiene los actores sin duplicar una misma instancia.
    private List<Actor> obtenerActoresUnicos(
            List<ContenidoAudiovisual> contenidos) {

        List<Actor> actores = new ArrayList<>();
        Map<Actor, Boolean> registrados = new IdentityHashMap<>();

        for (ContenidoAudiovisual contenido : contenidos) {

            if (contenido instanceof Pelicula pelicula) {

                for (Actor actor : pelicula.getActores()) {

                    if (actor != null && !registrados.containsKey(actor)) {
                        actores.add(actor);
                        registrados.put(actor, true);
                    }
                }
            }
        }

        return actores;
    }

    // Construye un mapa para relacionar cada actor con su ID.
    private Map<Actor, Integer> construirIdsActores(
            List<ContenidoAudiovisual> contenidos) {

        List<Actor> actores = obtenerActoresUnicos(contenidos);
        Map<Actor, Integer> ids = new IdentityHashMap<>();

        for (int i = 0; i < actores.size(); i++) {
            ids.put(actores.get(i), i);
        }

        return ids;
    }

    // Evita errores cuando un atributo de texto es null.
    private String convertirNulo(String valor) {
        return valor == null ? "" : valor;
    }

    private String[] convertirNulos(String[] datos) {

        String[] resultado = new String[datos.length];

        for (int i = 0; i < datos.length; i++) {
            resultado[i] = convertirNulo(datos[i]);
        }

        return resultado;
    }
}
