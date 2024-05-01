import estructuras.lineales.List;
import estructuras.lineales.ArrayList;
import readerwriter.ReaderWriter;

import java.io.IOException;
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
        try {
            leerArchivo();
        } catch (IOException e) {
            Colors.println("Error al leer el archivo: " + e.getMessage(), Colors.RED);
        }

        StringBuilder menu = new StringBuilder();
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
        List<String> lineas = ReaderWriter.readLines(fileName);
        for (String linea : lineas) {
            String[] partes = linea.split(" ");
            if (partes.length >= 4) {
                String nombreCiudad = partes[0];
                String estadoCiudad = partes[1];
                int coordX = Integer.parseInt(partes[2]);
                int coordY = Integer.parseInt(partes[3]);
                Ciudad nuevaCiudad = new Ciudad(nombreCiudad, estadoCiudad, coordX, coordY);
    
                boolean exists = false;
                for (Ciudad ciudad : ciudades) {
                    if (ciudad.equals(nuevaCiudad)) {
                        exists = true;
                        break;
                    }
                }
                
                if (!exists) {
                    ciudades.add(0,nuevaCiudad);
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
        Ciudad nuevaCiudad = new Ciudad(ciudad, estado, x, y);
        try {
            ciudades.add(0, nuevaCiudad);
            ReaderWriter.writeLines(fileName, ciudades);
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }

    //Métodos para la opción 2 del menú
    public static void eliminarCiudad() {
        if (ciudades.isEmpty()) {
            Colors.println("No hay ciudades disponibles para eliminar.", Colors.RED);
            return;
        }

        StringBuilder menu = new StringBuilder();

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
            ReaderWriter.writeLines(fileName, ciudades);;    
        } catch (Exception e) {
            Colors.println("Error al eliminar la ciudad: " + e.getMessage(), Colors.RED);
        }
    }

    //Método para la opción 3 del menú
    public static List<Ciudad> obtenerCiudadesEnEstado(){
        Colors.println("¿Cual es el estado donde se encuentra la ciudad?", Colors.HIGH_INTENSITY);
        String estadoBuscado = m.nextLine();
    
        List<Ciudad> ciudadesEnEstado = new ArrayList<>();
        for (Ciudad ciudad : ciudades) {
            if (ciudad.getEstado().equalsIgnoreCase(estadoBuscado)) {
                ciudadesEnEstado.add(0,ciudad);
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