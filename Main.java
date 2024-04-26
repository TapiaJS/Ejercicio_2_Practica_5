import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner m = new Scanner(System.in);

        List<Ciudad> ciudades = new ArrayList<>();
        String fileName ="ciudades.txt";

        /*
        * try-with-resources garantiza que cada recurso se cierre al final de la declaración
        */
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) { 

            /*
             * lee todas las líneas del archivo y luego cuenta el número total de líneas en el archivo
             */
            long contador = Files.lines(Paths.get(fileName)).count();
            System.out.println("READ " + fileName + ": " + contador);
            
            /*
             * se inicializa una cadena line con las líneas leídas del archivo
             */
            String line;
            while ((line = reader.readLine()) != null) {
                /*
                 * mientras haya una línea disponible en el archivo. Se divide la línea leída en partes utilizando el espacio como delimitador
                 */
                String[] parts = line.split(" ");
                /*
                 * si la línea tiene exactamente 4 partes. Se asume que la línea contiene información válida para crear un objeto tipo Ciudad 
                 */
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
                        try {//Utilizar getInt
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
