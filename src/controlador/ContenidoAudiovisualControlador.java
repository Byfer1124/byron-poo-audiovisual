package controlador;

import modelo.ContenidoAudiovisual;
import vista.ContenidoAudiovisualVista;

public class ContenidoAudiovisualControlador {

    private ContenidoAudiovisualVista vista;

    public ContenidoAudiovisualControlador(ContenidoAudiovisualVista vista) {
        this.vista = vista;
    }

    public void mostrarContenidos(ContenidoAudiovisual[] contenidos) {

        for (ContenidoAudiovisual contenido : contenidos) {
            vista.mostrarDetalles(contenido);
        }
    }
}
