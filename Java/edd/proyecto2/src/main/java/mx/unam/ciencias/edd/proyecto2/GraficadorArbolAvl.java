package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import java.lang.Math;

/** Clase para graficar los arboles avl. */
public class GraficadorArbolAvl extends GraficadorArbol {

    /* La cadena con la altura y el balance de un vertice de un arbol avl. */
    protected String alturaBalance = "\t<text fill='black' font-family='sans-serif' font-size='18' x='%f' y='%d'\n" +
                         "\ttext-anchor='middle'>(%d/%d)</text>\n";

    /** Define el estado inicial de un arbol avl. 
     * @param elementos una lista con los elementos del arbol.
     */
    public GraficadorArbolAvl(Lista<Integer> elementos) {
        this.elementos = elementos;
        arbol = new ArbolAVL<>(elementos);
    }

    /** Metodo para obtener el balance de un vertice avl */
    private int balance(Vertice vertice) {
        if (!vertice.hayIzquierdo() && !vertice.hayDerecho())
            return 0;
        else if (vertice.hayIzquierdo() && vertice.hayDerecho())
            return altura(vertice.vertice.izquierdo()) - altura(vertice.vertice.derecho());
        else if (vertice.hayIzquierdo())
            return altura(vertice.vertice.izquierdo()) - (-1);
        else
            return (-1) - altura(vertice.vertice.derecho());
    }

    /** Metodo privado auxliar para obtener la altura de un vertice avl */
    private int altura(VerticeArbolBinario<Integer> vertice) {
        return vertice == null ? -1 : vertice.altura();
    }

    /** Metodo privado auxiliar para saber si un vertice es izquierdo */
    private boolean esIzquierdo(Vertice vertice) {
        if (vertice.padre == null)
            return false;
        return vertice.padre.izquierdo == vertice ? true : false;
    }

    /**
     * Implementacion concreta para dibujar vertices de un arbol completo.
     * @param vertice el vertice a graficar.
     * @param vertice.coordenadaX la coordenada x del vertice a dibujar.
     */
    @Override protected String dibujaVertice(Vertice vertice) {
        if (vertice.vertice == arbol.raiz()) {
            return String.format(circulos,vertice.coordenadaX,vertice.coordenadaY) +
                   String.format(alturaBalance,vertice.coordenadaX,vertice.coordenadaY-25,vertice.altura(),balance(vertice)) +
                   String.format(numeros,vertice.coordenadaX,vertice.coordenadaY+7,vertice.get());
        }
        else if (esIzquierdo(vertice)) {
            return String.format(circulos,vertice.coordenadaX,vertice.coordenadaY+8) +
                   String.format(alturaBalance,vertice.coordenadaX-16,vertice.coordenadaY-19,vertice.altura(),balance(vertice)) +
                   String.format(numeros,vertice.coordenadaX,vertice.coordenadaY+15,vertice.get());
        }
        else {
            return String.format(circulos,vertice.coordenadaX,vertice.coordenadaY+8) +
                   String.format(alturaBalance,vertice.coordenadaX+16,vertice.coordenadaY-19,vertice.altura(),balance(vertice)) +
                   String.format(numeros,vertice.coordenadaX,vertice.coordenadaY+15,vertice.get());
        }
    }

    /** Metodo para graficar un arbol ordenado */
    @Override public void grafica() {
        dimensiones();
        Lista<Vertice> vertices = new Lista<>();
        vertices = guardaVertices(vertices);
        graficaVertices(vertices);
        xml += ultimo;
        System.out.printf(xml);
    }

    /** Metodo privado auxliar para agregar agregar los elementos 
     * de un arbol avl a una lista con sus coordenadas. **/
    @Override protected Lista<Vertice> guardaVertices(Lista<Vertice> vertices) {
        Cola<Vertice> cola = new Cola<>();
        Vertice actual;
        double xCirculo = anchura/2;
        int profundidad = 1;
        Vertice raiz = vertice(arbol.raiz(),xCirculo);
        raiz.coordenadaY = 43;
        vertices.agregaFinal(raiz);
        xCirculo /= 2;
        Vertice izquierdo = null;
        Vertice derecho = null;
        Vertice hijoDerecho = null;
        Vertice hijoIzquierdo = null;
        if (raiz.hayIzquierdo()) {
            izquierdo = vertice(arbol.raiz().izquierdo(),xCirculo);
            raiz.izquierdo = izquierdo;
            izquierdo.padre = raiz;
            izquierdo.coordenadaY += 22;
            vertices.agregaFinal(izquierdo);
        }
        if (raiz.hayDerecho()) {
            derecho = vertice(arbol.raiz().derecho(),xCirculo*3);
            raiz.derecho = derecho;
            derecho.padre = raiz;
            derecho.coordenadaY += 22;
            vertices.agregaFinal(derecho);
        }
        if (izquierdo != null)
            cola.mete(izquierdo);
        if (derecho != null)
            cola.mete(derecho);
        xCirculo /= 2;
        while(!cola.esVacia()) {
            actual = cola.saca();
            if (actual.profundidad() > profundidad) {
                xCirculo /= 2;
                profundidad++;
            }
            if (actual.hayIzquierdo()) {
                hijoIzquierdo = new Vertice(actual.vertice.izquierdo(),actual.coordenadaX-xCirculo);
                actual.izquierdo = hijoIzquierdo;
                hijoIzquierdo.padre = actual;
                hijoIzquierdo.coordenadaY += 22;
                vertices.agregaFinal(hijoIzquierdo);
                cola.mete(hijoIzquierdo);
            }
            if (actual.hayDerecho()) {
                hijoDerecho = new Vertice(actual.vertice.derecho(),actual.coordenadaX+xCirculo);
                actual.derecho = hijoDerecho;
                hijoDerecho.padre = actual;
                hijoDerecho.coordenadaY += 22;
                vertices.agregaFinal(hijoDerecho);
                cola.mete(hijoDerecho);
            }
        }
        return vertices;
    }

    /** Metodo protegido auxiliar para recorrer los vertices
     * de la estructura y graficarlos junto a sus hijos. */
    @Override protected void graficaVertices(Lista<Vertice> vertices) {
        for (Vertice vertice : vertices) {
            if (vertice.hayDerecho()) {
                xml += String.format(lineas,vertice.coordenadaX,vertice.coordenadaY
                        ,vertice.derecho.coordenadaX,vertice.derecho.coordenadaY);
            }
            if (vertice.hayIzquierdo()) {
                xml += String.format(lineas,vertice.coordenadaX,vertice.coordenadaY
                        ,vertice.izquierdo.coordenadaX,vertice.izquierdo.coordenadaY);
            }
            xml += dibujaVertice(vertice);
        }
    }

    /**
     * Metodo para calcular las dimensiones de un arbol avl.
     */
    @Override protected void dimensiones() {
        if (arbol.altura() == 0 ) {
            anchura = 62.0;
            altura = 65;
            xml += String.format(dimensiones,anchura,altura);
            xml += String.format(fondo,anchura,altura);
        }
        else if (arbol.altura() == 1) {
            anchura = 230.0;
            altura = 192;
            xml += String.format(dimensiones,anchura,altura);
            xml += String.format(fondo,anchura,altura);
        }
        else {
            anchura = Math.pow(2,arbol.altura())*80.0;
            altura = (arbol.altura()*80 + 40*(arbol.altura()+1))+31;
            xml += String.format(dimensiones,anchura,altura);
            xml += String.format(fondo,anchura,altura);
        }
    }
}
