
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import modelo.Actor;

public class ActorArchivo {

    private String rutaArchivo;

    public ActorArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    // Lee los actores almacenados en el archivo CSV.
    public Map<Integer, Actor> leerActores() {

        Map<Integer, Actor> actores = new HashMap<>();

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

    // Guarda los actores en el archivo CSV.
    public void guardarActores(Map<Integer, Actor> actores) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(rutaArchivo))) {

            bw.write("id,nombre,nacionalidad");
            bw.newLine();

            for (Map.Entry<Integer, Actor> entrada : actores.entrySet()) {

                String[] datos = {
                    String.valueOf(entrada.getKey()),
                    convertirNulo(entrada.getValue().getNombre()),
                    convertirNulo(entrada.getValue().getNacionalidad())
                };

                bw.write(String.join(",", datos));
                bw.newLine();
            }

            System.out.println("Actores guardados correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar actores: " + e.getMessage());
        }
    }

    private String convertirNulo(String valor) {
        return valor == null ? "" : valor;
    }
}
