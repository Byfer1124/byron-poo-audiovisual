
package controlador;

import modelo.ContenidoAudiovisual;
import vista.VistaContenido;

public class ContenidoAudiovisualControlador {

    private VistaContenido vista;

    public ContenidoAudiovisualControlador(VistaContenido vista) {
        this.vista = vista;
    }

    public void mostrarContenidos(ContenidoAudiovisual[] contenidos) {

        for (ContenidoAudiovisual contenido : contenidos) {
            vista.mostrarDetalles(contenido);
        }
    }
}
