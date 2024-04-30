import java.util.*;
import java.io.IOException;

public class DirectorioCiudades {
    public static void main(String[] args) {
    Scanner m = new Scanner(System.in);
    String fileName = "ciudades.txt";
    BuscarCiudades.setFileName(fileName);
    BuscarCiudades.imprimirCantidadDeLineas("READ");
    
    while (true) {
        try {
            BuscarCiudades.leerArchivo();
        } catch (IOException e) {
            Colors.println("Error al leer el archivo: " + e.getMessage(), Colors.RED);
        }
        System.out.println("Seleccione una acción:");
        System.out.println("1. Agregar ciudad");
        System.out.println("2. Eliminar ciudad");
        System.out.println("3. Ciudades en estado");
        System.out.println("4. Ciudades en rango de coordenadas");
        System.out.println("5. Imprimir todas las ciudades");
        System.out.println("0. Salir\n");

        int opcion = m.nextInt();
        m.nextLine(); // Consumir el salto de línea

        switch (opcion) {
            case 1:
                BuscarCiudades.agregarCiudad();
                break;
            case 2:
                BuscarCiudades.eliminarCiudad();
                break;
            case 3:
                BuscarCiudades.opcionTres();
                break;
            case 4:
                BuscarCiudades.obtenerCoordenadas();
                break;
            case 5:
                BuscarCiudades.imprimirCiudades();
                break;
            case 0:
                System.out.println("¡Hasta luego!");
                System.exit(0);
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
        }
    }
    }
}
