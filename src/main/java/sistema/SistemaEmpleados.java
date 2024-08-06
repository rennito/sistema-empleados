package sistema;

import java.io.*;  // Importa las clases necesarias para manejar archivos y flujos de entrada/salida
import java.util.*;  // Importa las clases necesarias para manejar listas, colecciones y escáner

public class SistemaEmpleados {
    private static final String ARCHIVO_EMPLEADOS = "empleados.txt";// Define la constante para el nombre del archivo donde se guardan los empleados
    private List<Empleado> empleados = new ArrayList<>();  // Crea una lista para almacenar empleados
    private List<Asistencia> asistencias = new ArrayList<>();  // Crea una lista para almacenar asistencias
    private Scanner scanner = new Scanner(System.in);  // Crea un escáner para leer la entrada del usuario

    public SistemaEmpleados() {
        cargarEmpleados();// Carga los empleados desde el archivo al iniciar el sistema
    }

    public void iniciar() {
        while (true) {
            mostrarMenu();
            int opcion = obtenerOpcion();
            procesarOpcion(opcion);
        }
    }

    private void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Agregar empleado");
        System.out.println("2. Listar empleados");
        System.out.println("3. Checador");
        System.out.println("4. Asistencia de empleados");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private int obtenerOpcion() {
        int opcion = -1;
        try {
            opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer
        } catch (InputMismatchException e) {
            System.out.println("Opción inválida. Por favor, ingrese un número.");
            scanner.nextLine();  // Limpiar el buffer
        }
        return opcion;
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarEmpleado();
                break;
            case 2:
                listarEmpleados();
                break;
            case 3:
                checador();
                break;
            case 4:
                listarAsistencias();
                break;
            case 5:
                guardarEmpleados();
                System.out.println("Saliendo del sistema...");
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
        }
    }

    private void agregarEmpleado() {
        try {
            System.out.print("Nombre completo: ");
            String nombreCompleto = scanner.nextLine();
            System.out.print("Puesto: ");
            String puesto = scanner.nextLine();
            System.out.print("Edad: ");
            int edad = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer
            System.out.print("Ciudad: ");
            String ciudad = scanner.nextLine();

            Empleado empleado = new Empleado(nombreCompleto, puesto, edad, ciudad);
            empleados.add(empleado);
            System.out.println("Empleado agregado exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, intente nuevamente.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void listarEmpleados() {
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            Collections.sort(empleados, new ComparadorEmpleado());
            System.out.println("Lista de empleados:");
            for (Empleado empleado : empleados) {
                System.out.println(empleado.obtenerInformacion());
            }
        }
    }

    private void checador() {
        try {
            System.out.print("Ingrese el número de empleado: ");
            int numeroEmpleado = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer
            boolean empleadoExiste = false;

            for (Empleado empleado : empleados) {
                if (empleado.getNumeroEmpleado() == numeroEmpleado) {
                    Asistencia asistencia = new Asistencia(numeroEmpleado);
                    asistencias.add(asistencia);
                    System.out.println("Checado registrado exitosamente para el empleado número " + numeroEmpleado);
                    empleadoExiste = true;
                    break;
                }
            }

            if (!empleadoExiste) {
                System.out.println("Número de empleado no encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, intente nuevamente.");
            scanner.nextLine();  // Limpiar el buffer
        }
    }

    private void listarAsistencias() {
        if (asistencias.isEmpty()) {
            System.out.println("No hay asistencias registradas.");
        } else {
            System.out.println("Lista de asistencias:");
            for (Asistencia asistencia : asistencias) {
                System.out.println(asistencia);
            }
        }
    }

    private void guardarEmpleados() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_EMPLEADOS))) {
            for (Empleado empleado : empleados) {
                writer.println(empleado.getNumeroEmpleado() + "," + empleado.getNombreCompleto() + "," +
                        empleado.getPuesto() + "," + empleado.getEdad() + "," + empleado.getCiudad());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los empleados: " + e.getMessage());
        }
    }

    private void cargarEmpleados() {
        File archivo = new File(ARCHIVO_EMPLEADOS);// Crea un objeto File para el archivo de empleados
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(",");
                    int numeroEmpleado = Integer.parseInt(partes[0]);
                    String nombreCompleto = partes[1];
                    String puesto = partes[2];
                    int edad = Integer.parseInt(partes[3]);
                    String ciudad = partes[4];

                    Empleado empleado = new Empleado(nombreCompleto, puesto, edad, ciudad);
                    empleados.add(empleado);// Añade el empleado a la lista
                    if (numeroEmpleado >= Empleado.getContador()) {
                        Empleado.setContador(numeroEmpleado + 1);// Ajusta el contador si es necesario
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar los empleados: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SistemaEmpleados sistema = new SistemaEmpleados();
        sistema.iniciar();
    }
}
