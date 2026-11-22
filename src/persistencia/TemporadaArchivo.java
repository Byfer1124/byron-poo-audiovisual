
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.ContenidoAudiovisual;
import modelo.SerieDeTV;
import modelo.Temporada;

public class TemporadaArchivo {

    private String rutaArchivo;

    public TemporadaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    // Lee las temporadas y las asigna a sus respectivas series.
    public void asignarTemporadas(List<ContenidoAudiovisual> contenidos) {

        Map<Integer, SerieDeTV> series = new HashMap<>();

        for (ContenidoAudiovisual contenido : contenidos) {

            if (contenido instanceof SerieDeTV serie) {
                series.put(serie.getId(), serie);
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

                int idSerie = Integer.parseInt(datos[0]);
                int anioEstreno = Integer.parseInt(datos[1]);
                int cantidadEpisodios = Integer.parseInt(datos[2]);

                SerieDeTV serie = series.get(idSerie);

                if (serie != null) {
                    serie.agregarTemporada(anioEstreno, cantidadEpisodios);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer temporadas: " + e.getMessage());
        }
    }

    // Guarda las temporadas de cada serie en el archivo CSV.
    public void guardarTemporadas(List<ContenidoAudiovisual> contenidos) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(rutaArchivo))) {

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
}
