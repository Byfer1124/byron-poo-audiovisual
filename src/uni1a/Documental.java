/**
 * Class Documental
 */
package uni1a;

// Subclase Documental que extiende de ContenidoAudiovisual
public class Documental extends ContenidoAudiovisual {
    private String tema;

    //Asociación: Un documental tiene un investigador principal)
    private Investigador investigadorPrincipal;
    
 
    public Documental(String titulo, int duracionEnMinutos, String genero, String tema, Investigador investigadorPrincipal) {
        super(titulo, duracionEnMinutos, genero);
        this.tema = tema;
        
        //Asignacion del valor al atribuo investigadorPrincipal
        this.investigadorPrincipal = investigadorPrincipal;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
 
	@Override
    public void mostrarDetalles() {
        System.out.println("Detalles del documental:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Tema: " + this.tema);
        System.out.println();
        //Detalles del investigador principal

        if (investigadorPrincipal != null) {
            investigadorPrincipal.mostrarDetalles();
        } else {
            System.out.println("No tiene investigador asignado.");
        }
        
        System.out.println("------------------------");
        System.out.println();
    }
}