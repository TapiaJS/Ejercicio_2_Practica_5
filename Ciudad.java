import java.util.List;
import java.util.ArrayList;

public class Ciudad {
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

    /*
     * Verifica que ciudades pertenecen de acuerdo al rango y dominio proporcionado 
     */
    public static List<Ciudad> encontrarCoincidencias(List<Ciudad> ciudades, int coordenadaXMIN, int coordenadaXMAX, int coordenadaYMIN, int coordenadaYMAX) {
        List<Ciudad> ciudadesValidas = new ArrayList<>();
        for (Ciudad ciudad : ciudades) {
            boolean coincidenciaX = validarLocalizacion(ciudad.x, coordenadaXMIN, coordenadaXMAX);
            boolean coincidenciaY = validarLocalizacion(ciudad.y, coordenadaYMIN, coordenadaYMAX);
            if (coincidenciaX && coincidenciaY) {
                ciudadesValidas.add(ciudad);
            }
        }
        return ciudadesValidas;
    }

    public static boolean validarLocalizacion(int coordenada, int coordenadaMin, int coordenadaMax){
        return coordenada >= coordenadaMin && coordenada <= coordenadaMax;
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