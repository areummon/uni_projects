package mx.unam.ciencias.icc.proyecto2;

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
        banderas.revisaBanderas(entrada);
        banderas.verificaBanderas();
    }
}
