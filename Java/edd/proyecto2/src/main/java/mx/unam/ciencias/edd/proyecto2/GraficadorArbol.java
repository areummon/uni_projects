package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import java.lang.Math;

/** Clase abstracta para graficar los arboles */
public abstract class GraficadorArbol extends Graficador {

    protected class Vertice {

        /** El vertice */
        protected VerticeArbolBinario<Integer> vertice;
        /** El vertice padre */
        protected Vertice padre;
        /** El hijo izquierdo */
        protected Vertice izquierdo;
        /* El hijo derecho */
        protected Vertice derecho;
        /* La coordenada x del vertice */
        protected double coordenadaX;
        /* La coordenada y del vertice */
        protected int coordenadaY;

        /* Define el estado inicial de un vertice */
        protected Vertice(VerticeArbolBinario<Integer> vertice, double coordenadaX) {
            this.vertice = vertice;
            this.coordenadaX = coordenadaX;
            coordenadaY = coordenadaY();
        }

        /* Regresa la altura del vertice */
        public int altura() {
            return vertice.altura();
        }
        
        /* Regresa la profundidad del vertice */
        public int profundidad() {
            return vertice.profundidad();
        }

        /** Metodo para verificar si hay un elemento izquierdo */
        public boolean hayIzquierdo() {
            return vertice.hayIzquierdo();
        }

        /** Metodo para verificar si hay un elemento izquierdo */
        public boolean hayDerecho() {
            return vertice.hayDerecho();
        }

        /** Metodo para regresar el elemento del vertice */
        public int get() {
            return vertice.get();
        }
        /** Metodo privado auxiliar para calcular la coordenada y de un vertice */
        protected int coordenadaY() {
            return (vertice.profundidad()*119)+21;
        }
    }

    /* Los circulos de los arboles */
    protected String circulos = "\t<circle cx='%f' cy='%d' r='20' stroke='black' stroke-width='2' fill='white' />\n";
    /* Los numeros de los vertices */
    protected String numeros = "\t<text fill='black' font-family='sans-serif' font-size='20' x='%f' y='%d'\n" +
                         "\ttext-anchor='middle'>%d</text>\n";
    /* Las lineas del arbol */ 
    protected String lineas = "\t<line x1='%f' y1='%d' x2='%f' y2='%d' stroke='black' stroke-width='2' />\n";
    /* La anchura del svg */
    protected double anchura;
    /* La altura del svg */
    protected int altura;
    /* El arbol */
    ArbolBinario<Integer> arbol;

    /**
     * Metodo para dibujar los vertices de un arbol
     */
    protected abstract String dibujaVertice(Vertice vertice);

    /** Metodo para graficar un arbol ordenado */
    @Override public void grafica() {
        dimensiones();
        Lista<Vertice> vertices = new Lista<>();
        vertices = guardaVertices(vertices);
        graficaVertices(vertices);
        xml += ultimo;
        System.out.printf(xml);
    }

    /** Metodo privado auxliar para agregar agregar los elementos del arbol
     * a una lista con sus coordenadas. **/
    protected Lista<Vertice> guardaVertices(Lista<Vertice> vertices) {
        Cola<Vertice> cola = new Cola<>();
        Vertice actual;
        double xCirculo = anchura/2;
        int profundidad = 1;
        Vertice raiz = vertice(arbol.raiz(),xCirculo);
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
            vertices.agregaFinal(izquierdo);
        }
        if (raiz.hayDerecho()) {
            derecho = vertice(arbol.raiz().derecho(),xCirculo*3);
            raiz.derecho = derecho;
            derecho.padre = raiz;
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
                vertices.agregaFinal(hijoIzquierdo);
                cola.mete(hijoIzquierdo);
            }
            if (actual.hayDerecho()) {
                hijoDerecho = new Vertice(actual.vertice.derecho(),actual.coordenadaX+xCirculo);
                actual.derecho = hijoDerecho;
                hijoDerecho.padre = actual;
                vertices.agregaFinal(hijoDerecho);
                cola.mete(hijoDerecho);
            }
        }
        return vertices;
    }

    /** Metodo protegido auxiliar para recorrer los vertices
     * de la estructura y graficarlos junto a sus hijos. */
    protected void graficaVertices(Lista<Vertice> vertices) {
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

    /** Metodo privado auxliliar para calcular las dimensiones
     * de un arbol binario dependiendo del numero de elementos */
    @Override protected void dimensiones() {
        if (arbol.altura() == 0 ) {
            anchura = 42.0;
            altura = 42;
            xml += String.format(dimensiones,anchura,altura);
            xml += String.format(fondo,anchura,altura);
        }
        else if (arbol.altura() == 1) {
            anchura = 220.0;
            altura = 162;
            xml += String.format(dimensiones,anchura,altura);
            xml += String.format(fondo,anchura,altura);
        }
        else {
            anchura = Math.pow(2,arbol.altura())*80.0;
            altura = arbol.altura()*80 + 40*(arbol.altura()+1);
            xml += String.format(dimensiones,anchura,altura);
            xml += String.format(fondo,anchura,altura);
        }
    }

    /** Metodo auxiliar para hacer un nuevo vertice a partir de verticeArbolBinario */
    protected Vertice vertice(VerticeArbolBinario<Integer> vertice, double coordenada) {
        if (vertice == null)
            return null;
        return new Vertice(vertice,coordenada);
    }
}
