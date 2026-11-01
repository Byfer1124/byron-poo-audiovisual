package uni1a;

public class Investigador {
	
	private String nombre;
    private String especialidad;
	
	public Investigador() {
		
	}
	
	public Investigador(String nombre, String especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
    }
	
	// Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public void mostrarDetalles() {
        System.out.println("Detalles de Investigador:");
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Especialidad: " + this. especialidad);
    }
}
