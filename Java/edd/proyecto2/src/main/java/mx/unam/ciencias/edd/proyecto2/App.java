package mx.unam.ciencias.edd.proyecto2;

/** 
 * Clase para la aplicacion del graficador. 
 */
public class App {

    /* La entrada */
    Entrada entrada;
    /* La salida */
    Salida salida;

    /** 
     * Constructor de la aplicación.
     */
    public App() {
    }
    
    /**
     * La ejecucion de la aplicacion.
     * @param args el arreglo de las entradas pasadas al programa.
     */
    public void ejecuta(String[] args){
        if (args.length == 0) {
            entrada = new EntradaEstandar();
            entrada.manejaEntrada();
        }
        else {
            entrada = new EntradaArchivo(args[0]);
            entrada.manejaEntrada();
        }
        salida = new Salida(entrada.getElementos(),entrada.getEstructura());
        salida.manejaSalida();
    }
}
