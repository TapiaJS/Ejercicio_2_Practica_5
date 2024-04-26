import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class BuscarCiudades {

    public static void leerArchivo(List<Ciudad> ciudades, String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            long contador = Files.lines(Paths.get(fileName)).count();
            System.out.println("\nREAD " + fileName + ": " + contador);

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
        }
    }

    public static int getInt(String mensaje, String error, int min, int max) {
        int val;
        Scanner m = new Scanner(System.in);
        while (true) {
            Colors.println(mensaje, Colors.HIGH_INTENSITY);
            if (m.hasNextInt()) {
                val = m.nextInt();
                if (val < min || max < val) {
                    Colors.println(error, Colors.RED + Colors.HIGH_INTENSITY);
                } else {
                    return val;
                }
            } else {
                m.next();
                Colors.println(error, Colors.RED + Colors.HIGH_INTENSITY);
            }
        }
    }

    public static void obtenerCoordenadas(List<Ciudad> ciudades) {
        String error = Colors.HIGH_INTENSITY + "Ingresa una opción válida";
        String mensaje = "";
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
                    mensaje = "¿Cuál es la primera coordenada X?" + Ciudad.mostrarMinMaxX();

                    coordenadaXMIN = getInt(mensaje, error, Ciudad.getMinX(), Ciudad.getMaxX());

                    valorAuxiliarX = Ciudad.getMinX();
                    Ciudad.setMinX(coordenadaXMIN);
                    break;
                case 1:
                    Ciudad.setMaxX(Ciudad.getMaxX() + 1);
                    mensaje = "¿Cuál es la segunda coordenada X?" + Ciudad.mostrarMinMaxX();

                    coordenadaXMAX = getInt(mensaje, error, Ciudad.getMinX(), Ciudad.getMaxX());

                    Ciudad.setMinX(valorAuxiliarX);
                    break;
                case 2:
                    Ciudad.setMaxY(Ciudad.getMaxY() - 1);
                    mensaje = "¿Cuál es la primera coordenada Y?" + Ciudad.mostrarMinMaxY();

                    coordenadaYMIN = getInt(mensaje, error, Ciudad.getMinY(), Ciudad.getMaxY());

                    valorAuxiliarY = Ciudad.getMinY();
                    Ciudad.setMinY(coordenadaYMIN);
                    break;
                case 3:
                    Ciudad.setMaxY(Ciudad.getMaxY() + 1);
                    mensaje = "¿Cuál es la segunda coordenada Y?" + Ciudad.mostrarMinMaxY();

                    coordenadaYMAX = getInt(mensaje, error, Ciudad.getMinY(), Ciudad.getMaxY());

                    Ciudad.setMinX(valorAuxiliarY);
                    break;
            }
        }
        Ciudad.encontrarCoincidencias(ciudades, coordenadaXMIN, coordenadaXMAX, coordenadaYMIN, coordenadaYMAX);
    }
}