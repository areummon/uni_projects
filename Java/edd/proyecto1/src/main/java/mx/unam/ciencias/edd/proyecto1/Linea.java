package mx.unam.ciencias.edd.proyecto1;

import java.text.Normalizer;

/** 
 * Clase para representar las lineas de un texto. Un texto tiene varias lineas 
 * que pueden tener palabras o simplemente lineas vacias.
 */
public class Linea implements Comparable<Linea> {

    /* Linea de texto ya sea en blanco o con palabras */
    private String linea;

    /** Define el estado inicial de una linea.
     * @param Linea la linea de texto.
     */
    public Linea(String linea) {
        this.linea = linea;
    }

    /** Regresa la linea de texto. 
     * @return la linea de texto.
     */
    public String getLinea() {
        return linea;
    }

    /** Metodo toString de la linea de texto.
     * @return representacion en cadena de la linea.
     */
    public String toString(){
        return linea;
    }

    /**
     * Metodo auxiliar para darle formato a la linea y poder comparar.
     * @param linea la linea a la cual darle formato
     * @return la linea con el formato requerido.
     */
    private String formato(String linea) {
        linea = linea.toLowerCase();
        linea = Normalizer.normalize(linea, Normalizer.Form.NFKD);
        linea = linea.replaceAll("\\P{L}", "");
        return linea;
    }

    /**
     * Implementacion del metodo compareTo de la interfaz Comparable para la linea de texto.
     * La linea se compara lexicograficamente, tomando los acentos y 
     * diéresis como vocales, la ñ como n e ignorando los símbolos.
     * @param siguiente la linea con la cual se comparará.
     * @return -1 si es menor, 0 si son iguales, 1 si es mayor.
     */
    @Override public int compareTo(Linea siguiente){
        String normalizada1 = formato(linea.toString());
        String normalizada2 = formato(siguiente.toString());
        System.out.printf("Estas son las lineas: " + normalizada1 + "\n" + normalizada2);
        return normalizada1.compareTo(normalizada2); 
    }
}
