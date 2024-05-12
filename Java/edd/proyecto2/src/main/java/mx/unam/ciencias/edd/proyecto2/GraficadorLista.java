package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;

/** Clase para graficar las listas */
public class GraficadorLista extends Graficador {

    /* Las lineas(marcadores) de una lista doblemente ligada */
    private String marcadores = "  <defs>\n" +
        "\t<marker id='flechaDerecha' markerWidth='13' markerHeight='13' refX='2' refY='6'\n" +
        "\t\torient='auto' markerUnits='strokeWidth'>\n" +
        "\t\t<path d='M2,1 L2,11 L10,6 L2,1' style='fill: #000000;' />\n" +
        "\t</marker>\n" +
        "\t<marker id='flechaIzquierda' markerWidth='13' markerHeight='13' refX='2' refY='6'\n" +
        "\t\torient='180' markerUnits='strokeWidth'>\n" +
        "\t\t<path d='M2,1 L2,11 L10,6 L2,1' style='fill: #000000;' />\n" +
        "\t</marker>\n" +
        "  </defs>\n";
    /* Cadena para dibujar los nodos de la lista */
    private String nodos = "\t<rect x='%d' y='10' width='90' height='50' style='fill: white; stroke: black;' stroke-width='2'/>\n";
    /* Cadena para dibujar las lineas que unen a los nodos */
    private String lineas = "\t<line x1='%d' y1='35' x2='%d' y2='35' stroke='black' stroke-width='1.3' " +
            "marker-start='url(#flechaIzquierda)' marker-end='url(#flechaDerecha)' />\n";
    /* Cadena para dibujar los elementos de los nodos */
    private String numeros = "\t<text fill='black' font-family='sans-serif' font-size='25' x='%d' y='44'" +
          " text-anchor='middle'>%d</text>\n";

    /** Define el estado inicial para graficar una lista.
     * @param elementos la lista con los elementos de la lista.
     */
    public GraficadorLista(Lista<Integer> elementos) {
        this.elementos = elementos;
    }

    /* Metodo para graficar una lista en xml */
    @Override public void grafica() {
        dimensiones();
        xml += marcadores;
        int xCajas = 10;
        int xFlechas = 114;
        int numElementos = this.elementos.getElementos();
        for (Integer elemento : this.elementos) {
            if (numElementos == 1 || elemento.equals(elementos.getUltimo())) {
                xml += String.format(nodos,xCajas);
                break;
            }
            xml += String.format(nodos,xCajas);
            xml += String.format(lineas,xFlechas,xFlechas+14);
            xFlechas += 28;
            xCajas += 132;
            xFlechas += 104;
        }
        int xLetras = 56;
        for (Integer elemento : this.elementos) {
            xml += String.format(numeros,xLetras,elemento);
            xLetras += 132;
        }
        xml += ultimo;
        System.out.printf(xml);
    }

    /** Metodo privado auxiliar para calcular las dimensiones
     * de la representacion de una lista */
    @Override protected void dimensiones() {
        int elementos = this.elementos.getElementos();
        double largoAux;
        int altoAux;
        largoAux = (90*elementos)+(42*(elementos-1))+1.49;
        xml += String.format(dimensiones,largoAux+18,70);
        xml += String.format(fondo,largoAux+20,70);
    }
}

