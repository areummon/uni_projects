package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;

/** Clase abstracta para graficar los arboles */
public class GraficadorArbolOrdenado extends GraficadorArbol {
    
    /** Define el estado inicial de un arbol completo */
    public GraficadorArbolOrdenado(Lista<Integer> elementos) {
        this.elementos = elementos;
        arbol = new ArbolBinarioOrdenado<>(elementos);
    }

    /**
     * Implementacion concreta para dibujar vertices de un arbol completo.
     * @param vertice el vertice a graficar.
     * @param vertice.coordenadaX la coordenada x del vertice a dibujar.
     */
    @Override protected String dibujaVertice(Vertice vertice) {
        return String.format(circulos,vertice.coordenadaX,vertice.coordenadaY) +
               String.format(numeros,vertice.coordenadaX,vertice.coordenadaY+7,vertice.get());
    }
}
