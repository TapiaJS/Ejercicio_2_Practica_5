import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String fileName = "ciudades.txt";
        BuscarCiudades.setFileName(fileName);
        BuscarCiudades.imprimirCantidadDeLineas("READ ");
        
        try {
            BuscarCiudades.leerArchivo();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        BuscarCiudades.imprimirCiudades();
        BuscarCiudades.obtenerCoordenadas();
    }
}