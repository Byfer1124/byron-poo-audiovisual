
package persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.ContenidoAudiovisual;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.Documental;
import modelo.Noticiero;
import modelo.VideoEducativo;

public class ContenidoArchivo {

    private String rutaArchivo;

    public ContenidoArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    /*
     * Lee los contenidos audiovisuales desde un archivo CSV.
     * Omite los encabezados y las líneas vacías.
     * Identifica el tipo de contenido y crea el objeto correspondiente.
     */
    public List<ContenidoAudiovisual> leerContenidos() {

        List<ContenidoAudiovisual> contenidos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {

            String linea;

            // Saltar la primera línea (encabezados)
            br.readLine();

            while ((linea = br.readLine()) != null) {

                // Ignorar las líneas vacías del archivo CSV
                if (linea.isBlank()) {
                    continue;
                }

                // Separar las columnas del CSV
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
                        contenido = new Documental(
                            id, titulo, duracion, genero, datos[6], null
                        );
                        break;

                    case "Noticiero":
                        contenido = new Noticiero(
                            id, titulo, duracion, genero, datos[7], datos[8]
                        );
                        break;

                    case "VideoEducativo":
                        contenido = new VideoEducativo(
                            id, titulo, duracion, genero, datos[9], datos[10]
                        );
                        break;
                }

                // Agregar el contenido a la lista
                if (contenido != null) {
                    contenidos.add(contenido);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return contenidos;
    }
}
