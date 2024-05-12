package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeGrafica;
import java.util.Iterator;
import java.lang.Math;

/* Clase para graficar las gráficas */
public class GraficadorGrafica extends Graficador {

    protected class Vertice {

        /* El vertice. */
        private VerticeGrafica<Integer> vertice;
        /* La coordenada x del vértice */
        double coordenadaX;
        /* La coordenada y del vértice */
        double coordenadaY;

        /** Define el estado inicial de un Vertice.
         * @param vertice el VerticeGrafica
         * @param vecinos los vecinos del vertice.
         */
        public Vertice(VerticeGrafica<Integer> vertice,
                       double coordenadaX, double coordenadaY) {
            this.vertice = vertice;
            this.coordenadaX = coordenadaX;
            this.coordenadaY = coordenadaY;
        }

        /* Metodo para obtener el elemento del vertice */
        public Integer get() {
            return vertice.get();
        }
    }

    /* Los circulos de los arboles */
    private String circulos = "\t<circle cx='%f' cy='%f' r='20' stroke='black' stroke-width='2' fill='white' />\n";
    /* Los numeros de los vertices */
    private String numeros = "\t<text fill='black' font-family='sans-serif' font-size='20' x='%f' y='%f'\n" +
                         "\ttext-anchor='middle'>%d</text>\n";
    /* Las lineas del arbol */ 
    private String lineas = "\t<line x1='%f' y1='%f' x2='%f' y2='%f' stroke='black' stroke-width='2' />\n";
    /* La anchura del svg */
    private double anchura;
    /* La altura del svg */
    private int altura;
    /* El numero de elementos de la gráfica */
    private int numElementos;
    /* El radio del perimetro del circulo sobre el que 
     * colocaran los vertices */
    private double radio;

    /* La grafica a graficar. */
    Grafica<Integer> grafica;

    /* Define el estado inicial de un graficador para graficas. */
    public GraficadorGrafica(Lista<Integer> elementos) {
        if (elementos.getElementos() % 2 != 0) {
            System.err.printf("Error: el número de elementos no es par");
            System.exit(0);
        }
        this.elementos = elementos;
        grafica = new Grafica<>();
        agregaElementos();
    }

    /* Metodo privado auxiliar para agregar los elementos 
     * de la lista a la gráfica y conectarlos */
    private void agregaElementos() {
        Lista<Integer> aislados = new Lista<>();
        Integer aux = 0;
        int contador = 0;
        for (Integer elemento : elementos) {
            if (contador == 0) {
                aux = elemento;
                contador++;
            }
            else if (contador % 2 != 0) {
                if (elemento == aux) {
                    if (grafica.contiene(elemento)) {
                        System.err.printf("Error: el elemento %d es aislado" + 
                                " y no puede tener vecinos",elemento);
                        System.exit(0);
                    }
                    grafica.agrega(elemento);
                    aislados.agregaFinal(elemento);
                }
                else {
                    if (grafica.contiene(aux) && grafica.contiene(elemento)) {
                        if (grafica.sonVecinos(aux,elemento)) {
                            System.err.printf("Error: los elemementos %d y %d"+
                                    " ya estan conectados",aux,elemento);
                            System.exit(0);
                        }
                    }
                    else if (grafica.contiene(aux)) {
                        grafica.agrega(elemento);
                    }
                    else if (grafica.contiene(elemento)) {
                        grafica.agrega(aux);
                    }
                    else {
                        grafica.agrega(aux);
                        grafica.agrega(elemento);
                    }
                        grafica.conecta(elemento,aux);
                }
                contador++;
            }
            else {
                aux = elemento;
                contador++;
            }
        }
        revisaDesconectados(aislados);
        numElementos = grafica.getElementos();
    }

    /** Metodo privado auxiliar para evitar que un
     * vértice desconectado tenga vecinos. */
    private void revisaDesconectados(Lista<Integer> lista) {
        for (Integer elemento : lista) {
            if(tieneVecinos(elemento)) {
                System.err.printf("Error: el elemento %d es aislado" +
                        " y no puede tener vecinos",elemento);
                System.exit(0);
            }
        }
    }

    /* Metodo privado auxiliar para checar si un vertice aislado
     * tiene vecinos */
    private boolean tieneVecinos(Integer elemento) {
        VerticeGrafica<Integer> vertice = grafica.vertice(elemento);
        int contador = 0;
        for (VerticeGrafica<Integer> vecino : vertice.vecinos()) {
            contador++;
        }
        return contador != 0;
    }

    /** Metodo privado auxiliar para calcular la coordenada X
     * de un vertice */
    private double coordenadaX(int numVertice) {
        return (radio * Math.cos((2*3.1416*numVertice)/numElementos))+radio;
    }

    /** Metodo privado auxiliar para calcular la coordenada Y 
     * de un vertice */
    private double coordenadaY(int numVertice) {
        return (radio * Math.sin((2*3.1416*numVertice)/numElementos))+radio;
    }

    /**
     * Metodo para graficar una gráfica.
     */
    @Override public void grafica() {
        dimensiones();
        double centro;
        int numVertice;
        if (numElementos == 1) {
            double aux = anchura/2;
            xml += String.format(circulos,aux,22.5);
            xml += String.format(numeros,aux,21.0+8.5,elementos.getPrimero());
        }
        else {
            centro = anchura/2;
            radio = centro;
            numVertice = 0;
            Lista<Vertice> vertices = new Lista<>();
            for (Integer elemento : grafica) {
                vertices.agregaFinal(new Vertice(grafica.vertice(elemento),
                            coordenadaX(numVertice)+22.5,coordenadaY(numVertice)+22.5));
                numVertice++;
            }
            for (Vertice vertice : vertices) {
                for (VerticeGrafica<Integer> vecino : vertice.vertice.vecinos()) {
                    Vertice vecinoActual = buscaVertice(vecino,vertices);
                    xml += String.format(lineas,vertice.coordenadaX,vertice.coordenadaY,
                            vecinoActual.coordenadaX,vecinoActual.coordenadaY);
                    grafica.desconecta(vertice.get(),vecinoActual.get());
                }
                xml += String.format(circulos,vertice.coordenadaX,vertice.coordenadaY);
                xml += String.format(numeros,vertice.coordenadaX,vertice.coordenadaY+8,vertice.get());
            }
        }
        xml += ultimo;
        System.out.printf(xml);
    }

    /* Metodo privado auxiliar para buscar un Vertice recibiendo un 
     * VerticeGrafica */
    private Vertice buscaVertice(VerticeGrafica<Integer> vertice, Lista<Vertice> vertices) {
        for (Vertice elemento : vertices) {
            if (vertice.get().equals(elemento.get()))
                return elemento;
        }
        return null;
    }

    /**
     * Metodo para calcular las dimensiones del svg.
     */
    @Override protected void dimensiones() {
        int aux;
        if (numElementos == 1) {
            anchura = 45.0;
            altura = 45;
            xml += String.format(dimensiones,anchura,altura);
            xml += String.format(fondo,anchura,altura);
        }
        else {
            aux = 200*((numElementos/9)+1);
            anchura = aux*1.0;
            altura = aux;
            xml += String.format(dimensiones,anchura+45,altura+45);
            xml += String.format(fondo,anchura+45,altura+45);
        }
    }
}
