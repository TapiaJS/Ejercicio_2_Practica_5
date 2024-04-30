import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class BuscarCiudades {
    private static Scanner m = new Scanner(System.in);
    private static List<Ciudad> ciudades = new ArrayList<>();
    private static String error = "Ingresa una opción válida.";
    private static String fileName = "";

    public static void setFileName(String fileName){
        BuscarCiudades.fileName = fileName;
    }

    //Métodos para el directorio
    public static int mostrarMenu() {
        StringBuilder menu = new StringBuilder();

        try {
            BuscarCiudades.leerArchivo();
        } catch (IOException e) {
            Colors.println("Error al leer el archivo: " + e.getMessage(), Colors.RED);
        }
        menu.append("\nSeleccione una acción:\n");
        menu.append("1. Agregar ciudad\n");
        menu.append("2. Eliminar ciudad\n");
        menu.append("3. Ciudades en estado\n");
        menu.append("4. Ciudades en rango de coordenadas\n");
        menu.append("5. Imprimir todas las ciudades\n");
        menu.append("0. Salir\n");

        int opcion = getInt(menu.toString(), error, 0, 5);
        return opcion;
    }

    public static int obtenerOpcion() {
        while (!m.hasNextInt()) {
            m.next(); 
            System.out.println("Opción no válida. Intente nuevamente.");
        }
        int opcion = m.nextInt();
        m.nextLine(); 
        return opcion;
    }

    public static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                BuscarCiudades.agregarCiudad();
                break;
            case 2:
                BuscarCiudades.eliminarCiudad();
                break;
            case 3:
                List<Ciudad> resultado = obtenerCiudadesEnEstado();
                imprimirCiudadesDeLaLista(resultado);
                break;
            case 4:
                BuscarCiudades.obtenerCoordenadas();
                break;
            case 5:
                BuscarCiudades.imprimirCiudades();
                break;
            case 0:
                Colors.println("¡Hasta luego!", Colors.BLUE);
                System.exit(0);
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
        }
    }

    public static int getInt(String mensaje, String error, int min, int max) {
        int val;
        while (true) {
            Colors.println(mensaje, Colors.HIGH_INTENSITY);
            if (m.hasNextInt()) {
                val = m.nextInt();
                m.nextLine(); 
                if (val < min || max < val) {
                    Colors.println(error, Colors.RED + Colors.HIGH_INTENSITY);
                } else {
                    return val;
                }
            } else {
                m.next();
                m.nextLine(); 
                Colors.println(error, Colors.RED + Colors.HIGH_INTENSITY);
            }
        }
    }

    public static void imprimirCantidadDeLineas(String mensaje){
        try {
            long contador = Files.lines(Paths.get(fileName)).count();
            System.out.println(mensaje + fileName + ": " + contador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imprimirCiudadesDeLaLista(List<Ciudad> ciudades){
        if (ciudades.isEmpty()) {
            Colors.println(Colors.HIGH_INTENSITY + "Sin ciudades.", Colors.RED);
        } else {
            for (int i = 0; i < ciudades.size(); i++) {
                Colors.println(Colors.HIGH_INTENSITY + (i + 1) + ". " + ciudades.get(i), Colors.CYAN);
            }
        }
    }

    public static void leerArchivo() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 4) {
                    String nombreCiudad = parts[0];
                    String estadoCiudad = parts[1];
                    int coordX = Integer.parseInt(parts[2]);
                    int coordY = Integer.parseInt(parts[3]);
                    Ciudad nuevaCiudad = new Ciudad(nombreCiudad, estadoCiudad, coordX, coordY);
    
                    boolean exists = false;
                    for (Ciudad ciudad : ciudades) {
                        if (ciudad.equals(nuevaCiudad)) {
                            exists = true;
                            break;
                        }
                    }
                    
                    if (!exists) {
                        ciudades.add(nuevaCiudad);
                    }
                }
            }
        }
    }

    //Métodos para la opción 1 del menú
    public static void agregarCiudad(){
        Colors.println("¿Cuál es el nombre de la ciudad? ", Colors.HIGH_INTENSITY);
        String estado = m.nextLine();
        Colors.println("¿Cuál es el estado en el que se encuentra la ciudad? ", Colors.HIGH_INTENSITY);
        String ciudad = m.nextLine();

        String longitud = "¿Cuál es su coordenada X?";
        String latitud = "¿Cuál es su coordenada Y?";
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;

        int equis1 = getInt(longitud, error, min, max);

        int equis3 = getInt(latitud, error, min, max);
        
        agregarCiudadAlArchivo(estado, ciudad, equis1, equis3);
    }

    public static void agregarCiudadAlArchivo(String ciudad, String estado, int x, int y) {
        Path path = Paths.get(fileName); // Ruta al archivo
        String nuevaLinea = ciudad + " " + estado + " " + x + " " + y; // Formato de la nueva ciudad
        Ciudad nuevaCiudad = new Ciudad(ciudad, estado, x, y);
    
        try {
            // Lee todas las líneas existentes del archivo
            List<String> lineas = Files.readAllLines(path);
    
            // Agrega la nueva ciudad al inicio de la lista
            lineas.add(0, nuevaLinea);

            ciudades.add(0, nuevaCiudad);
    
            // Escribe de nuevo todas las líneas en el archivo, incluyendo la nueva ciudad al inicio
            Files.write(path, lineas, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            imprimirCantidadDeLineas("WROTE ");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }

    //Métodos para la opción 2 del menú
    public static void eliminarCiudad() {
        StringBuilder menu = new StringBuilder();
        if (ciudades.isEmpty()) {
            Colors.println("No hay ciudades disponibles para eliminar.", Colors.RED);
            return;
        }

        menu.append("Selecciona una opción.\n");
        for (int i = 0; i < ciudades.size(); i++) {
            menu.append((i + 1) + ". " + ciudades.get(i) + "\n");
        }
        menu.append("0. Salir");
        int seleccion = getInt(menu.toString(), error, 0, ciudades.size());
        
        if (seleccion == 0) return;
        
        int indiceReal = seleccion - 1;
        
        try {
            ciudades.remove(indiceReal);
            actualizarArchivo();    
        } catch (Exception e) {
            Colors.println("Error al eliminar la ciudad: " + e.getMessage(), Colors.RED);
        }
    }
    
    private static void actualizarArchivo() throws IOException {
        Path path = Paths.get(fileName);
        List<String> lineas = new ArrayList<>();
        
        for (Ciudad ciudad : ciudades) {
            lineas.add(ciudad.toString());
        }
        
        Files.write(path, lineas, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        imprimirCantidadDeLineas("WROTE ");
    }

    //Método para la opción 3 del menú
    public static List<Ciudad> obtenerCiudadesEnEstado(){
        Colors.println("¿Cual es el estado donde se encuentra la ciudad?", Colors.HIGH_INTENSITY);
        String estadoBuscado = m.nextLine();
    
        List<Ciudad> ciudadesEnEstado = new ArrayList<>();
        for (Ciudad ciudad : ciudades) {
            if (ciudad.getEstado().equalsIgnoreCase(estadoBuscado)) {
                ciudadesEnEstado.add(ciudad);
            }
        }
        return ciudadesEnEstado;
    }    

    //Métodos para la opción 4 del menú
    public static void obtenerCoordenadas() {
        Ciudad.encontrarMaxMin(ciudades);
        String mensaje;
        int coordenadaXMIN = 0;
        int coordenadaXMAX = 0;
        int coordenadaYMIN = 0;
        int coordenadaYMAX = 0;
        int valorAuxiliarX = 0;
        int valorAuxiliarY = 0;

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    Ciudad.setMaxX(Ciudad.getMaxX() - 1);
                    mensaje = "¿Cuál es la primera coordenada X?" + Ciudad.mostrarMinMaxX() + ".";

                    coordenadaXMIN = getInt(mensaje, error, Ciudad.getMinX(), Ciudad.getMaxX());

                    valorAuxiliarX = Ciudad.getMinX();
                    Ciudad.setMinX(coordenadaXMIN);
                    break;
                case 1:
                    Ciudad.setMaxX(Ciudad.getMaxX() + 1);
                    mensaje = "¿Cuál es la segunda coordenada X?" + Ciudad.mostrarMinMaxX() + ".";

                    coordenadaXMAX = getInt(mensaje, error, Ciudad.getMinX(), Ciudad.getMaxX());

                    Ciudad.setMinX(valorAuxiliarX);
                    break;
                case 2:
                    Ciudad.setMaxY(Ciudad.getMaxY() - 1);
                    mensaje = "¿Cuál es la primera coordenada Y?" + Ciudad.mostrarMinMaxY() + ".";

                    coordenadaYMIN = getInt(mensaje, error, Ciudad.getMinY(), Ciudad.getMaxY());

                    valorAuxiliarY = Ciudad.getMinY();
                    Ciudad.setMinY(coordenadaYMIN);
                    break;
                case 3:
                    Ciudad.setMaxY(Ciudad.getMaxY() + 1);
                    mensaje = "¿Cuál es la segunda coordenada Y?" + Ciudad.mostrarMinMaxY() + ".";

                    coordenadaYMAX = getInt(mensaje, error, Ciudad.getMinY(), Ciudad.getMaxY());

                    Ciudad.setMinX(valorAuxiliarY);
                    break;
            }
        }
        List<Ciudad> resultado = Ciudad.encontrarCoincidencias(ciudades, coordenadaXMIN, coordenadaXMAX, coordenadaYMIN, coordenadaYMAX);
        imprimirCiudadesDeLaLista(resultado);
    }

    //Método para la opción 5 del menú
    public static void imprimirCiudades() {
        if (ciudades.isEmpty()) {
            Colors.println("No hay ciudades en el archivo", Colors.RED);
        } else {
            for (int i = 0; i < ciudades.size(); i++) {
                Colors.println(Colors.HIGH_INTENSITY + (i + 1) + ". " + ciudades.get(i), Colors.CYAN);
            }
        }
    }
}