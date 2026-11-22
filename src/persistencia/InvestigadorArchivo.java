
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import modelo.Investigador;

public class InvestigadorArchivo {

    private String rutaArchivo;

    public InvestigadorArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    // Lee los investigadores almacenados en el archivo CSV.
    public Map<Integer, Investigador> leerInvestigadores() {

        Map<Integer, Investigador> investigadores = new HashMap<>();

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

    // Guarda los investigadores en el archivo CSV.
    public void guardarInvestigadores(
            Map<Integer, Investigador> investigadores) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(rutaArchivo))) {

            bw.write("idDocumental,nombre,especialidad");
            bw.newLine();

            for (Map.Entry<Integer, Investigador> entrada : investigadores.entrySet()) {

                Investigador investigador = entrada.getValue();

                String[] datos = {
                    String.valueOf(entrada.getKey()),
                    convertirNulo(investigador.getNombre()),
                    convertirNulo(investigador.getEspecialidad())
                };

                bw.write(String.join(",", datos));
                bw.newLine();
            }

            System.out.println("Investigadores guardados correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar investigadores: " + e.getMessage());
        }
    }

    private String convertirNulo(String valor) {
        return valor == null ? "" : valor;
    }
}

