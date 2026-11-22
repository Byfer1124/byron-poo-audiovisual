
package poo;

import controlador.ContenidoAudiovisualControlador;
import persistencia.ContenidoArchivo;
import vista.ContenidoAudiovisualVista;

public class PruebaAudioVisual {

    public static void main(String[] args) {

        // Crear la vista
        ContenidoAudiovisualVista vista = new ContenidoAudiovisualVista();

        // Crear el objeto encargado de la persistencia
        ContenidoArchivo archivo = new ContenidoArchivo(
                "datos/contenidos.csv"
        );

        // Crear el controlador
        ContenidoAudiovisualControlador controlador =
                new ContenidoAudiovisualControlador(vista, archivo);

        // Iniciar el sistema
        controlador.iniciar();
    }
}
