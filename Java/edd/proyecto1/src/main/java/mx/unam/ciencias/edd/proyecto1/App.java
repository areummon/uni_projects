package mx.unam.ciencias.edd.proyecto1;

/** 
 * Clase para la aplicacion del lexicografico. 
 */
public class App {

    /* El identificador y manejador de las banderas */
    private Bandera banderas;

    /** 
     * Define el estado inicial de la aplicacion. 
     */
    public App() {
        banderas = new Bandera();
    }
    
    /**
     * La ejecucion de la aplicacion.
     * @param entrada el arreglo de las entradas pasadas al programa.
     */
    public void ejecuta(String[] entrada){
        if (entrada.length == 0) {
            EntradaEstandar.manejaEntrada();
            return; 
        }
        banderas.revisaBanderas(entrada);
        banderas.verificaBanderas();
    }
}
