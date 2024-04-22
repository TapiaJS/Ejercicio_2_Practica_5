import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Ciudad {
    /*
     * Variables de instancia
     */
    private String nombre;
    private String estado;
    private int x;
    private int y;
    /*
     * Variables de clase
     */
    private static int minX;
    private static int maxX;
    private static int minY;
    private static int maxY;

    /*
     * Constructor de un objeto tipo Ciudad
     */
    public Ciudad(String nombre, String estado, int x, int y) {
        this.nombre = nombre;
        this.estado = estado;
        this.x = x;
        this.y = y;
    }

    /*
     * Setters de la instancia del objeto Ciudad
     */
    public static void setMinX(int minX){
        Ciudad.minX = minX;
    }

    public static void setMaxX(int maxX){
        Ciudad.maxX = maxX;
    }

    public static void setMinY(int minY){
        Ciudad.minY = minY;
    }

    public static void setMaxY(int maxY){
        Ciudad.maxY = maxY;
    }

    /*
     * Getters de la instancia del objeto Ciudad
     */
    public static int getMinX(){
        return Ciudad.minX;
    }

    public static int getMaxX(){
        return Ciudad.maxX;
    }

    public static int getMinY(){
        return Ciudad.minY;
    }

    public static int getMaxY(){
        return Ciudad.maxY;
    }
    
    /*
     * Métodos de la clase Ciudad
     */
    public static void encontrarMaxMin(List<Ciudad> ciudades){
        Ciudad.minX = Integer.MAX_VALUE;
        Ciudad.maxX = Integer.MIN_VALUE;
        Ciudad.minY = Integer.MAX_VALUE;
        Ciudad.maxY = Integer.MIN_VALUE;

        for (Ciudad ciudad : ciudades) {
            Ciudad.minX = Math.min(minX, ciudad.x);
            Ciudad.maxX = Math.max(maxX, ciudad.x);
            Ciudad.minY = Math.min(minY, ciudad.y);
            Ciudad.maxY = Math.max(maxY, ciudad.y);
        }
    }

    public static boolean validarCordenada(int cordenada, int eje){
        return (eje == 0 && validarCordenadaX(minX, maxX, cordenada)) || (eje == 1 && validarCordenadaY(minY, maxY, cordenada));
    }

    public static boolean validarCordenadaX(int minX, int maxX, int cordenada){
        boolean valido = true;
        if(cordenada < minX || cordenada > maxX) {
            valido = false;
        }
        return valido;
    }
    
    public static boolean validarCordenadaY(int minY, int maxY, int cordenada){
        boolean valido = true;
        if(cordenada < minY || cordenada > maxY) {
            valido = false;
        }
        return valido;
    }

    /*
     * Verifica que ciudades pertenecen de acuerdo al rango y dominio proporcionado 
     */
    public static void encontrarCoincidencias(List<Ciudad> ciudades, int coordenadaXMIN, int coordenadaXMAX, int coordenadaYMIN, int coordenadaYMAX){
        int i = 1;
        for (Ciudad ciudad : ciudades) {
            boolean coincidenciaX = validarLocalizacion(ciudad.x, coordenadaXMIN, coordenadaXMAX);
            boolean coincidenciaY = validarLocalizacion(ciudad.y, coordenadaYMIN, coordenadaYMAX);
            if(coincidenciaX && coincidenciaY){
                Colors.println(Colors.HIGH_INTENSITY + i + ". " + ciudad, Colors.CYAN);
                i++;
            } 
        }
        if(i == 1) Colors.println(Colors.HIGH_INTENSITY + "Sin ciudades.", Colors.RED);
    }

    public static boolean validarLocalizacion(int coordenada, int coordenadaMin, int coordenadaMax){
        if(coordenada >= coordenadaMin && coordenada <= coordenadaMax){
            return true;
        } 
        return false;
    } 

    public static String mostrarMinMaxX(){
        return "[" + Ciudad.minX + ", " + Ciudad.maxX + "]";
    }

    public static String mostrarMinMaxY(){
        return "[" + Ciudad.minY + ", " + Ciudad.maxY + "]";
    }

    @Override
    public String toString() {
        return nombre + " " + estado + " " + x + " " + y;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner m = new Scanner(System.in);

        List<Ciudad> ciudades = new ArrayList<>();
        String fileName ="ciudades.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) { 

            long contador = Files.lines(Paths.get(fileName)).count();
            System.out.println("READ " + fileName + ": " + contador);
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 4) {
                    ciudades.add(new Ciudad(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
                    Colors.println(Colors.HIGH_INTENSITY + ciudades.size() + ". " + line, Colors.CYAN);
                    
                } else {
                    Colors.println(Colors.HIGH_INTENSITY + "Formato de línea incorrecto: " + line, Colors.RED);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir coordenadas a números: " + e.getMessage());
        }

        Ciudad.encontrarMaxMin(ciudades);
        String error = Colors.HIGH_INTENSITY + "Ingresa una opción válida";
        boolean datoErroneo = false;
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
                    do {
                        Colors.println("¿Cuál es la primera coordenada X?" + Ciudad.mostrarMinMaxX(), Colors.HIGH_INTENSITY);
                        try {
                            coordenadaXMIN = m.nextInt();
                            if (!Ciudad.validarCordenada(coordenadaXMIN, 0)) {
                                Colors.println(error, Colors.RED);
                                datoErroneo = true;
                            } else {
                                datoErroneo = false;
                                valorAuxiliarX = Ciudad.getMinX();
                                Ciudad.setMinX(coordenadaXMIN);
                            }
                        } catch (InputMismatchException e) {
                            Colors.println(error, Colors.RED);
                            m.nextLine(); // Limpiar el buffer
                            datoErroneo = true;
                        }
                    } while (datoErroneo);
                    break;
                case 1:
                Ciudad.setMaxX(Ciudad.getMaxX() + 1);
                    do {
                        Colors.println("¿Cuál es la segunda coordenada X?" + Ciudad.mostrarMinMaxX(), Colors.HIGH_INTENSITY);
                        try {
                            coordenadaXMAX = m.nextInt();
                            if (!Ciudad.validarCordenada(coordenadaXMAX, 0)) {
                                Colors.println(error, Colors.RED);
                                datoErroneo = true;
                            } else {
                                datoErroneo = false;
                                Ciudad.setMinX(valorAuxiliarX);
                            }
                        } catch (InputMismatchException e) {
                            Colors.println(error, Colors.RED);
                            m.nextLine(); // Limpiar el buffer
                            datoErroneo = true;
                        }
                    } while (datoErroneo);
                    break;
                case 2:
                Ciudad.setMaxY(Ciudad.getMaxY() - 1);
                    do {
                        Colors.println("¿Cuál es la primera coordenada Y?" + Ciudad.mostrarMinMaxY(), Colors.HIGH_INTENSITY);
                        try {
                            coordenadaYMIN = m.nextInt();
                            if (!Ciudad.validarCordenada(coordenadaYMIN, 1)) {
                                Colors.println(error, Colors.RED);
                                datoErroneo = true;
                            } else {
                                datoErroneo = false;
                                valorAuxiliarY = Ciudad.getMinY();
                                Ciudad.setMinY(coordenadaYMIN);
                            }
                        } catch (InputMismatchException e) {
                            Colors.println(error, Colors.RED);
                            m.nextLine(); // Limpiar el buffer
                            datoErroneo = true;
                        }
                    } while (datoErroneo);
                    break;
                case 3:
                Ciudad.setMaxY(Ciudad.getMaxY() + 1);
                    do {
                        Colors.println("¿Cuál es la segunda coordenada Y?" + Ciudad.mostrarMinMaxY(), Colors.HIGH_INTENSITY);
                        try {
                            coordenadaYMAX = m.nextInt();
                            if (!Ciudad.validarCordenada(coordenadaYMAX, 1)) {
                                Colors.println(error, Colors.RED);
                                datoErroneo = true;
                            } else {
                                datoErroneo = false;
                                Ciudad.setMinY(valorAuxiliarY);
                            }
                        } catch (InputMismatchException e) {
                            Colors.println(error, Colors.RED);
                            m.nextLine(); // Limpiar el buffer
                            datoErroneo = true;
                        }
                    } while (datoErroneo);
                    break;
            }
        }
        Ciudad.encontrarCoincidencias(ciudades, coordenadaXMIN, coordenadaXMAX, coordenadaYMIN, coordenadaYMAX);
    }
}
