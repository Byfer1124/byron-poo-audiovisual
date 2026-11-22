
package vista;

import java.util.List;

import modelo.ContenidoAudiovisual;

public interface VistaContenido {

    void mostrarDetalles(ContenidoAudiovisual contenido);

    void mostrarMenu();

    int leerOpcion();

    void mostrarContenidos(List<ContenidoAudiovisual> contenidos);

    void mostrarMensaje(String mensaje);

    String leerTexto(String mensaje);

    int leerEntero(String mensaje);

    void cerrar();
}
