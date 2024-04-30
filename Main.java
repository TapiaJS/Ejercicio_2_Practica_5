import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Ciudad> ciudades = new ArrayList<>();
        String fileName = "ciudades.txt";
        BuscarCiudades.setFileName(fileName);
        BuscarCiudades.imprimirCantidadDeLineas("READ ");
        
        try {
            BuscarCiudades.leerArchivo();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        BuscarCiudades.imprimirCiudades();
        Ciudad.encontrarMaxMin(ciudades);
        BuscarCiudades.obtenerCoordenadas();
    }
}