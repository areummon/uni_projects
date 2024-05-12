package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Color;

/** Clase para graficar los arboles rojinegros. */
public class GraficadorArbolRojinegro extends GraficadorArbol {

    ArbolRojinegro<Integer> rojinegro;

    /* Los circulos de los arboles rojinegros */
    protected String circulos = "\t<circle cx='%f' cy='%d' r='20' stroke='black' stroke-width='2' fill='%s' />\n";
    /* Los numeros de los vertices */
    protected String numeros = "\t<text fill='white' font-family='sans-serif' font-size='20' x='%f' y='%d'\n" +
                         "\ttext-anchor='middle'>%d</text>\n";
    
    /** Define el estado inicial de un arbol rojinegro */
    public GraficadorArbolRojinegro(Lista<Integer> elementos) {
        this.elementos = elementos;
        arbol = new ArbolRojinegro<>(elementos);
        rojinegro = (ArbolRojinegro<Integer>)arbol;
    }

    /** Metodo para regresar el color de un vertice para graficarlo */
    private String color(Vertice vertice) {
        return rojinegro.getColor(vertice.vertice) == Color.ROJO ? "red" : "black";
    }

    /**
     * Implementacion concreta para dibujar vertices de un arbol completo.
     * @param vertice el vertice a graficar.
     */
    @Override protected String dibujaVertice(Vertice vertice) {
        return String.format(circulos,vertice.coordenadaX,vertice.coordenadaY,color(vertice)) +
               String.format(numeros,vertice.coordenadaX,vertice.coordenadaY+7,vertice.get());
    }
}
