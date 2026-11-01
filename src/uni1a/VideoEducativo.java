package uni1a;

public class VideoEducativo extends ContenidoAudiovisual {
    private String materia;
    private String nivelEducativo;

    public VideoEducativo(String titulo, int duracionEnMinutos, String genero, String materia, String nivelEducativo) {
        super(titulo, duracionEnMinutos, genero);
        this.materia = materia;
        this.nivelEducativo = nivelEducativo;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getNivelEducativo() {
        return nivelEducativo;
    }

    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles del Video Educativo:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Materia: " + materia);
        System.out.println("Nivel educativo: " + nivelEducativo);
        System.out.println();
        System.out.println("------------------------");
    }
}
