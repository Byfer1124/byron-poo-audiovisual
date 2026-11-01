package uni1a;

public class Temporada {

	private int anioEstreno;
    private int cantidadEpisodios;
    
    public Temporada() {
    	
    }
    
    public Temporada(int anioEstreno, int cantidadEpisodios) {
        this.anioEstreno = anioEstreno;
        this.cantidadEpisodios = cantidadEpisodios;
    }
    
 // Getters y Setters
    public int getAnioEstreno() {
        return anioEstreno;
    }

    public void setAnioEstreno(int anioEstreno) {
        this.anioEstreno = anioEstreno;
    }

    public int getCantidadEpisodios() {
        return cantidadEpisodios;
    }

    public void setCantidadEpisodios(int cantidadEpisodios) {
        this.cantidadEpisodios = cantidadEpisodios;
    }
    
    public void mostrarDetalles() {
        System.out.println("Año de estreno: " + anioEstreno);
        System.out.println("Cantidad de episodios: " + cantidadEpisodios);
    }
}
