public class DirectorioCiudades {
        public static void main(String[] args) {
            String fileName = "ciudades.txt";
            BuscarCiudades.setFileName(fileName);
    
            int opcion;
            do {
                opcion = BuscarCiudades.mostrarMenu();
                BuscarCiudades.procesarOpcion(opcion);
            } while (opcion != 0);
    }
}