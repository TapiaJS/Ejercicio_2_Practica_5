import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner m = new Scanner(System.in);
        List<Ciudad> ciudades = new ArrayList<>();
        String fileName = "ciudades.txt";
        
        try {
            BuscarCiudades.leerArchivo(ciudades, fileName);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        
        Ciudad.encontrarMaxMin(ciudades);
        BuscarCiudades.obtenerCoordenadas(ciudades, m);
    }
}
