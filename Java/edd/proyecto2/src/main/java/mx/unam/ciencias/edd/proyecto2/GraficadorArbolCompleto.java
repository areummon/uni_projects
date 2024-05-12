package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;

/** Clase abstracta para graficar los arboles */
public class GraficadorArbolCompleto extends GraficadorArbol {
    
    /** Define el estado inicial de un arbol completo */
    public GraficadorArbolCompleto(Lista<Integer> elementos) {
        this.elementos = elementos;
        arbol = new ArbolBinarioCompleto<>(elementos);
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
