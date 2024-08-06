package sistema;

public class Persona {
    // Variables privadas de la clase
    private String nombreCompleto;
    private int edad;
    private String ciudad;

    // Constructor de la clase
    public Persona(String nombreCompleto, int edad, String ciudad) {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.ciudad = ciudad;
    }
    // Métodos públicos para obtener los valores de las variables privadas
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getEdad() {
        return edad;
    }

    public String getCiudad() {
        return ciudad;
    }

    // Método público que devuelve una cadena de texto con la información de la persona
    public String obtenerInformacion() {
        return "Nombre completo: " + nombreCompleto + ", Edad: " + edad + ", Ciudad: " + ciudad;
    }
}
