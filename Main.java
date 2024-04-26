import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Ciudad> ciudades = new ArrayList<>();
        String fileName = "ciudades.txt";
        
        try {
            BuscarCiudades.leerArchivo(ciudades, fileName);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        
        Ciudad.encontrarMaxMin(ciudades);
        BuscarCiudades.obtenerCoordenadas(ciudades);
    }
}
