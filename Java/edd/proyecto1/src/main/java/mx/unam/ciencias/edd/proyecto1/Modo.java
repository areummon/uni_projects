package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Arreglos;
import mx.unam.ciencias.edd.Lista;
import java.io.IOException;

/**
 * Clase para manejar los modos del ordenador lexicografico.
 */
public class Modo {

    /* La lista de las lineas de los archivos*/
    private Lista<Linea> archivos;
    /* EL identificador del archivo */
    private String identificador;
    
    /**
     * Constructor para definir el estado inicial del Modo.
     * @param archivos lista con los archivos de texto dados.
     * @param identificador el identificador para guardar en disco duro.
     */
    public Modo(Lista<Linea> archivos, String identificador) {
        if(archivos != null)
            archivos = Lista.mergeSort(archivos);
        this.archivos = archivos;
        this.identificador = identificador;
    }

    /**
     * Metodo para ejecutar el modo dado por las banderas de la entrada.
     * @param opcion la opcion dada por la entrada del programa.
     */
    public void ejecutaModo(Opciones opcion) {
        switch(opcion) {
            case REVERSA_SIN_IDENTIFICADOR:
                reversaSinIdentificador(archivos);
                return;
            case REVERSA_CON_IDENTIFICADOR: 
                reversaConIdentificador(archivos,identificador);
                return;
            case IDENTIFICADOR:
                identificador(archivos,identificador);
                return;
            case IMPRIME:
                imprime(archivos);
                return;
            default: return;
        }
    }

    /* Metodo para el modo en el que se da la bandera de reversa sin una identificador */ 
    private void reversaSinIdentificador(Lista<Linea> archivos) {
        Lista<Linea> reversa = archivos.reversa();
        for (Linea elemento : reversa)
            System.out.println(elemento.getLinea());
    }

    /**
     * Metodo para el modo en el que se da la bandera de reversa con una identificador.
     * @param archivos la lista con las lineas de los archivos de texto.
     * @param identificador el identificador dado para guardar en disco duro.
     */
    private void reversaConIdentificador(Lista<Linea> archivos,String identificador) {
        Lista<Linea> reversa = archivos.reversa();
            try {
                Entrada.guarda(reversa, identificador);
            } catch (IOException ioe) {
                System.out.printf("Error al guardar el archivo");
                System.exit(0);
            }
    }

    /**
     * Metodo para el modo en el que se da la identificador con los archivos
     * @param archivos la lista con las lineas de los archivos de texto.
     * @param identificador el identificador dado para guardar en disco duro.
     */
    private void identificador(Lista<Linea> archivos, String identificador) {
        try {
            Entrada.guarda(archivos, identificador);
        } catch (IOException ioe) {
            System.out.printf("Error al guardar el archivo");
            System.exit(0);
        }
    }

    /**
     * Metodo para el modo en el que unicamente se dan los archivos de texto.
     * @param archivos la lista con las lineas de los archivos de texto.
     */
    private void imprime(Lista<Linea> archivos) {
        for (Linea elemento : archivos)
            System.out.println(elemento.getLinea());
    }

}
