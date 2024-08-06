package sistema;

import java.io.*;  // Importa las clases necesarias para manejar archivos y flujos de entrada/salida
import java.time.LocalDateTime;
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

    // Método para agregar un empleado
    private void agregarEmpleado() {
        try {
            System.out.print("Nombre completo: ");
            String nombreCompleto = scanner.nextLine();  // Lee el nombre completo del empleado

            // Verifica si el nombre completo ya existe en la lista de empleados
            for (Empleado empleado : empleados) {
                if (empleado.getNombreCompleto().equalsIgnoreCase(nombreCompleto)) {
                    System.out.println("El empleado con el nombre completo '" + nombreCompleto + "' ya está registrado.");
                    return;  // Sale del método si el empleado ya existe
                }
            }

            System.out.print("Puesto: ");
            String puesto = scanner.nextLine();  // Lee el puesto del empleado
            System.out.print("Edad: ");
            int edad = scanner.nextInt();  // Lee la edad del empleado
            scanner.nextLine();  // Limpia el buffer
            System.out.print("Ciudad: ");
            String ciudad = scanner.nextLine();  // Lee la ciudad del empleado

            Empleado empleado = new Empleado(nombreCompleto, puesto, edad, ciudad);  // Crea una nueva instancia de Empleado
            empleados.add(empleado);  // Añade el empleado a la lista
            System.out.println("Empleado agregado exitosamente.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, intente nuevamente.");
            scanner.nextLine();  // Limpia el buffer
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
            boolean yaChequeadoHoy = false;

            // Verificar si el empleado existe en la lista
            for (Empleado empleado : empleados) {
                if (empleado.getNumeroEmpleado() == numeroEmpleado) {
                    empleadoExiste = true;

                    // Verificar si el empleado ya ha registrado su asistencia hoy
                    LocalDateTime inicioDelDia = LocalDateTime.now().toLocalDate().atStartOfDay();
                    for (Asistencia asistencia : asistencias) {
                        if (asistencia.getNumeroEmpleado() == numeroEmpleado &&
                                asistencia.getFechaHora().toLocalDate().isEqual(inicioDelDia.toLocalDate())) {
                            yaChequeadoHoy = true;
                            break;
                        }
                    }

                    if (yaChequeadoHoy) {
                        System.out.println("El empleado número " + numeroEmpleado + " ya ha registrado su asistencia hoy.");
                    } else {
                        Asistencia asistencia = new Asistencia(numeroEmpleado);
                        asistencias.add(asistencia);
                        System.out.println("Checado registrado exitosamente para el empleado número " + numeroEmpleado);
                    }
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
        File archivoEmpleados = new File(ARCHIVO_EMPLEADOS);
        if (archivoEmpleados.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivoEmpleados))) {
                String linea;
                int maxNumeroEmpleado = 0;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(",");
                    int numeroEmpleado = Integer.parseInt(partes[0]);
                    String nombreCompleto = partes[1];
                    String puesto = partes[2];
                    int edad = Integer.parseInt(partes[3]);
                    String ciudad = partes[4];

                    Empleado empleado = new Empleado(nombreCompleto, puesto, edad, ciudad);
                    empleados.add(empleado);

                    if (numeroEmpleado > maxNumeroEmpleado) {
                        maxNumeroEmpleado = numeroEmpleado;
                    }
                }
                // Actualiza el contador con el valor más alto + 1
                Empleado.setContador(maxNumeroEmpleado + 1);

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
