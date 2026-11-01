/**
 * Class Pelicula
 */


package uni1a;

import java.util.ArrayList;
import java.util.List;

public class Pelicula extends ContenidoAudiovisual {

    private String estudio;

    // Agregación: Una película tiene actores.
    private List<Actor> actores;

    public Pelicula(String titulo, int duracionEnMinutos, String genero, String estudio) {

        super(titulo, duracionEnMinutos, genero);
        this.estudio = estudio;

        this.actores = new ArrayList<>();
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public List<Actor> getActores() {
        return actores;
    }

    // Agregar actores a la película
    public void agregarActor(Actor actor) {
        actores.add(actor);
    }

    @Override
    public void mostrarDetalles() {

        System.out.println("Detalles de la película:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Estudio: " + estudio);
        System.out.println();
        //Detalles de los actores

        actores.forEach(a -> a.mostrarDetalles());

        System.out.println();
        System.out.println("------------------------");
    }
    
    //Método agregado para el Commit 4
    public void reproducirPelicula() {
        System.out.println("Reproduciendo la película en alta definición");
    }
}

