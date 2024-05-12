package mx.unam.ciencias.edd.proyecto3;

/* Clase para varios metodos utiles para los diferentes
 * tipos de entradas. */
public class UtilEntrada {

    /* Código de terminación por error de argumento. */
    private static final int ERROR_USO = 1;

    /* Constructor privado para evitar instanciacion. */
    private UtilEntrada() {}

    /* Metodo para regresar las casillas de un 
     * arreglo de bytes dado.
     * @param laberinto el laberinto dado en bytes. */
    public static Integer[] casillas(byte[] laberinto) {
        Integer[] casillas = new Integer[laberinto.length-6];
        for (int i = 0; i < casillas.length; i++) {
            casillas[i] = Byte.toUnsignedInt(laberinto[i+6]);
        }
        return casillas;
    }

    /* Metodo para mostrar un mensaje en la salida. 
     * @param error el error a mostrar. */
    public static void error(String error) {
        System.out.printf("Error: %s",error);
        System.exit(ERROR_USO);
    }
}
