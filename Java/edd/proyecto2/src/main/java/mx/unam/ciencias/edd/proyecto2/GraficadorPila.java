package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;

/** Clase para graficar las listas */
public class GraficadorPila extends Graficador {

    /* Cadena para dibujar los cajas de la pila */
    private String cajas = "\t<rect x='12.6' y='%d' width='75' height='38' style='fill: #e7e7ff; stroke: #322c4e;' stroke-width='1'/>\n";
    /* Cadena para dibujar los elementos de los cajas */
    private String numeros = "\t<text fill='#322c4e' font-family='sans-serif' font-size='20' x='50' y='%d' text-anchor='middle'>%d</text>\n";
    /* Cadena para dibujar los delimitadores de la pila */
    private String path = "\t<path d='M5,5 L5,%d L95,%d L95,5' style='fill: white; stroke:#322c4e;' stroke-width='2' />\n";

    /* La altura de la pila */
    private int altura = 0;

    /** Define el estado inicial para graficar una pila.
     * @param elementos la lista con los elementos de la pila.
     */
    public GraficadorPila(Lista<Integer> elementos) {
        this.elementos = elementos;
    }

    /* Metodo para graficar una pila en xml */
    @Override public void grafica() {
        int elementos = this.elementos.getElementos();
        dimensiones();
        int yCajas = altura-45;
        int yCadenas = yCajas+27;
        for (Integer elemento : this.elementos) {
            xml += String.format(cajas,yCajas);
            yCajas -= 45;
            xml += String.format(numeros,yCadenas,elemento);
            yCadenas = yCajas+27;
        }
        xml += ultimo;
        System.out.printf(xml);
    }

    /** Metodo privado auxiliar para calcular las dimensiones
     * de la representacion de una pila. */
    @Override protected void dimensiones() {
        int aux = 10;
        int elementos = this.elementos.getElementos();
        if (elementos == 1) {
            xml += String.format(dimensiones,100.0,62);
            xml += String.format(fondo,100.0,62);
            xml += String.format(path,57,57);
            altura = 57;
        } 
        else {
            aux += (38*elementos)+(7*(elementos+1));
            xml += String.format(dimensiones,100.0,aux); 
            xml += String.format(fondo,100.0,aux);
            xml += String.format(path,aux-5,aux-5);
            altura = aux-5;
        }
    }
}

