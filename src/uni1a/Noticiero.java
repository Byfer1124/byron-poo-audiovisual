package uni1a;

public class Noticiero extends ContenidoAudiovisual {
    private String presentador;
    private String cadenaTv;

    public Noticiero(String titulo, int duracionEnMinutos, String genero, String presentador, String cadenaTv) {
        super(titulo, duracionEnMinutos, genero);
        this.presentador = presentador;
        this.cadenaTv = cadenaTv;
    }

    public String getPresentador() {
        return presentador;
    }

    public void setPresentador(String presentador) {
        this.presentador = presentador;
    }

    public String getCadenaTv() {
        return cadenaTv;
    }

    public void setCadenaTv(String cadenaTv) {
        this.cadenaTv = cadenaTv;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles del Noticiero:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Presentador: " + presentador);
        System.out.println("Cadena de TV: " + cadenaTv);
        System.out.println();
        System.out.println("------------------------");
    }
}