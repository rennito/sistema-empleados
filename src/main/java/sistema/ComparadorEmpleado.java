package sistema;

import java.util.Comparator;

public class ComparadorEmpleado implements Comparator<Empleado> {
    @Override
    public int compare(Empleado e1, Empleado e2) {
        return e1.getNombreCompleto().compareToIgnoreCase(e2.getNombreCompleto());
    }
}

