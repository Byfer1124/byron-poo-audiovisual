package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import modelo.VideoEducativo;

public class VideoEducativoTest {

    @Test
    public void testCrearVideoEducativo() {

        VideoEducativo video = new VideoEducativo(
                "Estructuras de Datos en Java",
                40,
                "Educativo",
                "Programacion",
                "Universitario"
        );

        assertEquals(
                "Estructuras de Datos en Java",
                video.getTitulo()
        );

        assertEquals(40, video.getDuracionEnMinutos());
        assertEquals("Educativo", video.getGenero());
        assertEquals("Programacion", video.getMateria());
        assertEquals("Universitario", video.getNivelEducativo());
    }

    @Test
    public void testModificarVideoEducativo() {

        VideoEducativo video = new VideoEducativo(
                "Estructuras de Datos en Java",
                40,
                "Educativo",
                "Programacion",
                "Universitario"
        );

        video.setMateria("Ingenieria de Software");
        video.setNivelEducativo("Avanzado");

        assertEquals(
                "Ingenieria de Software",
                video.getMateria()
        );

        assertEquals(
                "Avanzado",
                video.getNivelEducativo()
        );
    }
}
