package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;

/** Clase para manejar la salida del programa */
public class Salida {

    /* El conjunto de elementos a agregar a la estructura */
    private Lista<Integer> elementos;
    /* La estructura a crear */
    private Estructuras estructura;
    /* La estructura graficada */
    private Graficador graficador;

    /* Define el estado inicial de la salida. */
    public Salida(Lista<Integer> elementos, Estructuras estructura) {
        if (elementos.getElementos() == 0) {
            System.out.printf("Error: la estructura no tiene elementos");
            System.exit(0);
        }
        this.elementos = elementos;
        this.estructura = estructura;
    }

    /**
     * Metodo para manejar la salida del programa. 
    */
    public void manejaSalida() {
        switch(estructura) {
            case ARREGLO:
                arreglo();
                return;
            case LISTA:
                lista();
                return;
            case PILA:
                pila();
                return;
            case COLA:
                cola();
                return;
            case ARBOL_COMPLETO:
                arbolCompleto();
                return;
            case ARBOL_ORDENADO:
                arbolOrdenado();
                return;
            case ARBOL_ROJINEGRO:
                arbolRojinegro();
                return;
            case ARBOL_AVL:
                arbolAVL();
                return;
            case GRAFICA:
                grafica();
                return;
            default: return;
        }
    }

    /**
     * Metodo para manejar la salida de un arreglo
     */
    public void arreglo() {
        graficador = new GraficadorArreglo(elementos);
        graficador.grafica();
    }

    /** 
     * Metodo para manejar la salida de una Lista.
     */
    public void lista() {
        graficador = new GraficadorLista(elementos);
        graficador.grafica();
    }

    /** 
     * Metodo para manejar la salida de una Pila.
     */
    public void pila() {
        graficador = new GraficadorPila(elementos);
        graficador.grafica();
    }

    /** 
     * Metodo para manejar la salida de una Cola.
     */
    public void cola() {
        graficador = new GraficadorCola(elementos);
        graficador.grafica();
    }

    /** 
     * Metodo para manejar la salida de un arbol completo.
     */
    public void arbolCompleto() {
        graficador = new GraficadorArbolCompleto(elementos);
        graficador.grafica();
    }

    /** 
     * Metodo para manejar la salida de un arbol ordenado.
     */
    public void arbolOrdenado() {
        graficador = new GraficadorArbolOrdenado(elementos);
        graficador.grafica();
    }

    /** 
     * Metodo para manejar la salida de un arbol rojinegro.
     */
    public void arbolRojinegro() {
        graficador = new GraficadorArbolRojinegro(elementos);
        graficador.grafica();
    }

    /** 
     * Metodo para manejar la salida de un arbol AVL.
     */
    public void arbolAVL() {
        graficador = new GraficadorArbolAvl(elementos);
        graficador.grafica();
    }

    /** 
     * Metodo para manejar la salida de una gráfica.
     */
    public void grafica() {
        graficador = new GraficadorGrafica(elementos);
        graficador.grafica();
    }
}
