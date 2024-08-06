package sistema;

import java.time.LocalDateTime;  // Importa la clase LocalDateTime del paquete java.time

public class Asistencia {
    // Variables privadas de la clase
    private int numeroEmpleado;
    private LocalDateTime fechaHora;

    // Constructor de la clase
    public Asistencia(int numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
        this.fechaHora = LocalDateTime.now();
    }

    // Métodos públicos para obtener los valores de las variables privadas
    public int getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    // Método público que devuelve una cadena de texto con la información de la asistencia
    @Override
    public String toString() {
        return "Asistencia{" +
                "numeroEmpleado=" + numeroEmpleado +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
