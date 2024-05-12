package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;

/** Clase para graficar las listas */
public class GraficadorArreglo extends Graficador {

    /* Cadena para dibujar el rectangulo del arreglo. */
    private String rectangulo = "\t<rect x='%d' y='10' width='%f' height='60' style='fill: white; stroke: black;' stroke-width='2'/>\n";
    /* Cadena para dibujar las lineas que dividen al arreglo en rejas. */
    private String lineas = "\t<line x1='%d' y1='10' x2='%d' y2='70' stroke='black' stroke-width='2' />\n";
    /* Cadena para dibujar los elementos de las rejas. */
    private String numeros = "\t<text fill='black' font-family='sans-serif' font-size='25' x='%d' y='48'" +
          " text-anchor='middle'>%d</text>\n";

    /** Define el estado inicial para graficar una lista.
     * @param elementos la lista con los elementos de la lista.
     */
    public GraficadorArreglo(Lista<Integer> elementos) {
        this.elementos = elementos;
    }

    /* Metodo para graficar un arreglo en xml */
    @Override public void grafica() {
        dimensiones();
        int xCajas = 10;
        int numElementos = this.elementos.getElementos();
        if (numElementos != 1) {
            for (Integer elemento : this.elementos) {
                xCajas += 62;
                xml += String.format(lineas,xCajas,xCajas);
            }
        }
        int xLetras = 41;
        for (Integer elemento : this.elementos) {
            xml += String.format(numeros,xLetras,elemento);
            xLetras += 62;
        }
        xml += ultimo;
        System.out.printf(xml);
    }

    /** Metodo privado auxiliar para calcular las dimensiones
     * de la representacion de un arreglo*/
    @Override protected void dimensiones() {
        int elementos = this.elementos.getElementos();
        double aux = 62*elementos;
        double largoAux = aux+20;
        xml += String.format(dimensiones,largoAux,80);
        xml += String.format(fondo,largoAux,80);
        xml += String.format(rectangulo,10,aux);
    }
}

