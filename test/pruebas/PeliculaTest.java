package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import modelo.Actor;
import modelo.Pelicula;

public class PeliculaTest {

    @Test
    public void testCrearPelicula() {

        Pelicula pelicula = new Pelicula(
                "Avatar",
                162,
                "Ciencia ficcion",
                "20th Century Studios"
        );

        assertEquals("Avatar", pelicula.getTitulo());
        assertEquals(162, pelicula.getDuracionEnMinutos());
        assertEquals("Ciencia ficcion", pelicula.getGenero());
        assertEquals("20th Century Studios", pelicula.getEstudio());
    }

    @Test
    public void testAgregarActores() {

        Pelicula pelicula = new Pelicula(
                "Avatar",
                162,
                "Ciencia ficcion",
                "20th Century Studios"
        );

        Actor actor1 = new Actor(
                "Leonardo DiCaprio",
                "Estadounidense"
        );

        Actor actor2 = new Actor(
                "Tom Hardy",
                "Británica"
        );

        pelicula.agregarActor(actor1);
        pelicula.agregarActor(actor2);

        assertNotNull(pelicula.getActores());
        assertEquals(2, pelicula.getActores().size());

        assertEquals(
                "Leonardo DiCaprio",
                pelicula.getActores().get(0).getNombre()
        );

        assertEquals(
                "Tom Hardy",
                pelicula.getActores().get(1).getNombre()
        );
    }
}
