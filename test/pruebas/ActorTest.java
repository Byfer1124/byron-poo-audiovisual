package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import modelo.Actor;

public class ActorTest {

	@Test
	public void testCrearActor() {

		Actor actor = new Actor("Leonardo DiCaprio", "Estadounidense");

		assertEquals("Leonardo DiCaprio", actor.getNombre());
		assertEquals("Estadounidense", actor.getNacionalidad());
	}

	@Test
	public void testModificarActor() {

		Actor actor = new Actor("Leonardo DiCaprio", "Estadounidense");

		actor.setNombre("Tom Hardy");
		actor.setNacionalidad("Británica");

		assertEquals("Tom Hardy", actor.getNombre());
		assertEquals("Británica", actor.getNacionalidad());
	}
}
