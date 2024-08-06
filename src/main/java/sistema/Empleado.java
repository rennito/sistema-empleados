package sistema;

import java.util.concurrent.atomic.AtomicInteger;

public class Empleado extends Persona {
    private static final AtomicInteger contador = new AtomicInteger(1);  // Contador atómico para generar números de empleado únicos
    private int numeroEmpleado;// Número de empleado único para cada instancia
    private String puesto;// Puesto del empleado

    // Constructor que inicializa los campos del empleado
    public Empleado(String nombreCompleto, String puesto, int edad, String ciudad) {
        super(nombreCompleto, edad, ciudad);
        this.numeroEmpleado = contador.getAndIncrement(); // Asigna y luego incrementa el número de empleado
        this.puesto = puesto;
    }

    // Método estático para obtener el valor actual del contador
    public static int getContador() {
        return contador.get();
    }

    // Método estático para establecer el valor del contador
    public static void setContador(int valor) {
        contador.set(valor);
    }

    // Método para obtener el número de empleado
    public int getNumeroEmpleado() {
        return numeroEmpleado;
    }

    // Método para obtener el puesto del empleado
    public String getPuesto() {
        return puesto;
    }

    // Método sobrescrito de la clase Persona para obtener información completa del empleado
    @Override
    public String obtenerInformacion() {
        return super.obtenerInformacion() + ", Número de empleado: " + numeroEmpleado + ", Puesto: " + puesto;
    }
    // Método sobrescrito para convertir el objeto a una cadena representativa
    @Override
    public String toString() {
        return obtenerInformacion();
    }
}
