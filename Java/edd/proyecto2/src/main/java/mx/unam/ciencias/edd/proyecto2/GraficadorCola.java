package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;

/** Clase para graficar las listas */
public class GraficadorCola extends Graficador {

    /* Cadena con las flechas(marcadores) de una cola */
    private String marcadores = "  <defs>\n" +
        "\t<marker id='flechaDerecha' markerWidth='13' markerHeight='13' refX='2' refY='6'\n" +
        "\t\torient='auto' markerUnits='strokeWidth'>\n" +
        "\t\t<path d='M2,1 L2,11 L10,6 L2,1' style='fill: #000000;' />\n" +
        "\t</marker>\n" +
        "\t<marker id='flechaIzquierda' markerWidth='13' markerHeight='13' refX='2' refY='6'\n" +
        "\t\torient='auto' markerUnits='strokeWidth'>\n" +
        "\t\t<path d='M2,1 L2,11 L10,6 L2,1' style='fill: #000000;' />\n" +
        "\t</marker>\n" +
        "  </defs>\n";
    /* Cadena para dibujar las cajas de la cola. */
    private String cajas = "\t<rect x='%d' y='20' width='90' height='50' style='fill: #e2e3e7; stroke: black;' stroke-width='0.7' rx='10' />\n";
    /* Cadena para dibujar los elementos de los nodos. */
    private String numeros = "\t<text fill='black' font-family='sans-serif' font-size='20' x='%d' y='52' text-anchor='middle'>%d</text>\n";
    /* Cadena para el relleno de la cola. */
    private String relleno = "\t<rect x='10' y='10' width='%d' height='70' style='fill: #f3f3f3; stroke: none;' stroke-width='2'/>\n";
    /* Cadena para el limite superior de la cola. */
    String limiteSuperior = "\t<path d='M10,10 L%d,10' style='fill: gray; stroke:black;' stroke-width='1.3'" + 
                            " marker-start='url(#flechaIzquierda)' marker-end='url(#flechaDerecha)' />\n";
    /* Cadena para el limite inferior de la cola. */
    String limiteInferior = "\t<path d='M10,80 L%d,80' style='fill: gray; stroke:black;' stroke-width='1.3'" + 
                            " marker-start='url(#flechaIzquierda)' marker-end='url(#flechaDerecha)' />\n";

    /** Define el estado inicial para graficar una cola.
     * @param elementos la lista con los elementos de la cola.
     */
    public GraficadorCola(Lista<Integer> elementos) {
        this.elementos = elementos;
    }

    /**
     * Metodo para graficar una cola en svg.
     */
    @Override public void grafica() {
        dimensiones();
        int xCaja = 25;
        int xCadena = 70;
        Lista<Integer> pila = this.elementos.reversa();
        for (Integer elemento : pila) {
            xml += String.format(cajas,xCaja);
            xml += String.format(numeros,xCadena,elemento);
            xCaja += 105;
            xCadena = xCaja + 45;
        }
        xml += ultimo;
        System.out.printf(xml);
    }

    /** Metodo privado auxiliar para calcular las dimensiones
     * de la representacion de una cola. */
    @Override protected void dimensiones() {
        int elementos = this.elementos.getElementos();
        int aux = 20;
        int largo = 0;
        if (elementos == 1) {
            xml += String.format(dimensiones,140.0,90);
            xml += String.format(fondo,140.0,90);
            xml += marcadores;
            xml += String.format(relleno,125);
            xml += String.format(limiteSuperior,125);
            xml += String.format(limiteInferior,125);
        }
        else {
            aux += (15*(elementos+1)) + (90*elementos);
            xml += String.format(dimensiones,(double)aux,90);
            xml += String.format(fondo,(double)aux,90);
            xml += marcadores;
            xml += String.format(relleno,aux-15);
            xml += String.format(limiteSuperior,aux-15);
            xml += String.format(limiteInferior,aux-15);
        }
    }
}

