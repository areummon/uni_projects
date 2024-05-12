package mx.unam.ciencias.edd.proyecto2;

/** Enumeracion para las estructuras del programa */
public enum Estructuras {

    /* Estructura Arreglo */
    ARREGLO,
    /* Estructura pila */
    PILA,
    /* Estructura cola */
    COLA,
    /* Estructura de lista */
    LISTA,
    /* Estructura de un arbol binario completo */
    ARBOL_COMPLETO,
    /* Estructura de un arbol binario ordenado */
    ARBOL_ORDENADO,
    /* Estructura de un arbol binario rojinegro */
    ARBOL_ROJINEGRO,
    /* Estructura de un arbol binario AVL */
    ARBOL_AVL,
    /* Estructura de una grafica */
    GRAFICA;

    /**
     * Metodo para identificar la estructura dada.
     * @param estructura la estructura dada.
     */
    public static Estructuras identificaEstructura(String estructura) {
        switch(estructura.replaceAll("\\s+","").toLowerCase()) {
            case "arreglo":
                return ARREGLO;
            case "pila":
                return PILA;
            case "cola":
                return COLA;
            case "lista":
                return LISTA;
            case "arbolcompleto":
                return ARBOL_COMPLETO;
            case "arbolordenado":
                return ARBOL_ORDENADO;
            case "arbolrojinegro":
                return ARBOL_ROJINEGRO;
            case "arbolavl":
                return ARBOL_AVL;
            case "grafica":
                return GRAFICA;
            default: return null;
        }
    }
}
