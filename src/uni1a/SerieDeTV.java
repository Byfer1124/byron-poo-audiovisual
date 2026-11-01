/**
 * Class SerieDeTV
 */
package uni1a;

import java.util.ArrayList;
import java.util.List;

public class SerieDeTV extends ContenidoAudiovisual {

    private int temporadas;
    
    //Composicion con Temporada
    private List<Temporada> detallesTemporadas;

    public SerieDeTV(String titulo, int duracionEnMinutos,String genero, int temporadas) {

        super(titulo, duracionEnMinutos, genero);
        this.temporadas = temporadas;
        
        
        this.detallesTemporadas = new ArrayList<>();
    }

    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }

    // Crear y agregar una temporada
    public void agregarTemporada(int anioEstreno,int cantidadEpisodios) {

        Temporada temporada = new Temporada(anioEstreno, cantidadEpisodios);

        detallesTemporadas.add(temporada);
    }

    @Override
    public void mostrarDetalles() {

        System.out.println("Detalles de la serie:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Cantidad de temporadas: " + temporadas);

        System.out.println("\nDetalles de las temporadas:");

        detallesTemporadas.forEach(t -> t.mostrarDetalles());
        System.out.println();
        System.out.println("------------------------");
    }
}
