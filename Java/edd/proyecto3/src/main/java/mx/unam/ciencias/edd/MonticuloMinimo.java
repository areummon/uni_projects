package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return indice < elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            if (indice >= elementos)
                throw new NoSuchElementException("No existe el indice");
            else {
                return arbol[indice++];
            }
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            this.elemento = elemento;
            indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        arbol = nuevoArreglo(100);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        arbol = nuevoArreglo(n);
        int i = 0;
        for (T elemento : iterable) {
            arbol[i] = elemento;
            elemento.setIndice(i++);
        }
        elementos = n;
        for (i = elementos/2; i >= 0; i--) {
            acomodaAbajo(i);
        }
    }

    /* Metodo privado auxiliar para acomodar hacia arriba */
    private void acomodaArriba(int indice) {
        int padre = (indice-1)/2;
        while(indice >= 0 && arbol[indice].compareTo(arbol[padre]) < 0) {
            intercambiaDos(arbol[padre],arbol[indice]);
            indice = padre;
            padre = (padre-1)/2;
        }
    }

    /* Metodo privado auxiliar para acomodar hacia abajo. */
    private void acomodaAbajo(int indice) {
        T elemento = arbol[indice];
        T izquierdo = null;        
        T derecho = null;
        if (hayIzquierdo(elemento))
            izquierdo = arbol[(2*indice)+1];
        if (hayDerecho(elemento))
            derecho = arbol[(2*indice)+2];
        while (comparaAbajo(elemento,izquierdo) > 0 || comparaAbajo(elemento,derecho) > 0) {
            intercambia(elemento,izquierdo,derecho);
            izquierdo = derecho = null;
            if (hayIzquierdo(elemento))
                izquierdo = arbol[(2*elemento.getIndice())+1];
            if (hayDerecho(elemento))
                derecho = arbol[(2*elemento.getIndice())+2];
        }
    }

    /* Metodo privado auxliar para ver si existe izquierdo. */
    private boolean hayIzquierdo(T elemento) {
        return (2*elemento.getIndice())+1 < elementos ? true : false;
    }

    /* Metodo privado auxliar para ver si existe derecho. */
    private boolean hayDerecho(T elemento) {
        return (2*elemento.getIndice())+2 < elementos ? true : false;
    }

    /* Metodo privado auxiliar para comparar hacia abajo. */
    private int comparaAbajo(T elemento, T hijo) {
        return hijo == null ? -1 : elemento.compareTo(hijo);
    }

    /* Metodo privado auxiliar para intercambiar un vertice con otro*/
    private void intercambiaDos(T vertice,T intercambiado) {
        int indiceVertice = vertice.getIndice();
        int indiceIntercambiado = intercambiado.getIndice();
        arbol[indiceVertice] = intercambiado;
        arbol[indiceIntercambiado] = vertice;
        intercambiado.setIndice(indiceVertice);
        vertice.setIndice(indiceIntercambiado);
    }

    /* Metodo privado auxiliar para intercambiar un vertice entre el y sus
     * dos hijos */
    private void intercambia(T vertice, T izquierdo, T derecho) {
        if (izquierdo == null && derecho == null)
            return;
        if (izquierdo == null) {
            if (vertice.compareTo(derecho) > 0) 
                intercambiaDos(vertice,derecho);
        }
        else if (derecho == null) {
            if (vertice.compareTo(izquierdo) > 0)
                intercambiaDos(vertice,izquierdo);
        }
        else {
            if (izquierdo.compareTo(derecho) > 0) {
                if (vertice.compareTo(derecho) > 0)
                    intercambiaDos(vertice,derecho);
            }
            else {
                if (vertice.compareTo(izquierdo) > 0)
                    intercambiaDos(vertice,izquierdo);
            }
        }
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        if (elementos == arbol.length) {
            T[] nuevoArreglo = nuevoArreglo(2*arbol.length);
            for (int i = 0; i < arbol.length; i++) 
                nuevoArreglo[i] = arbol[i];
            arbol = nuevoArreglo;
        }
        arbol[elementos] = elemento;
        elemento.setIndice(elementos);
        elementos++;
        acomodaArriba(elemento.getIndice());
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        if (elementos == 0) 
            throw new IllegalStateException("El montículo es vacío");
        T raiz = arbol[0];
        intercambiaDos(raiz,arbol[elementos-1]);
        elementos--;
        acomodaAbajo(0);
        raiz.setIndice(-1);
        return raiz;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        if (elemento.getIndice() < 0 || elemento.getIndice() >= elementos)
            return;
        T ultimo = arbol[elementos-1];
        intercambiaDos(ultimo,elemento);
        elementos--;
        acomodaArriba(ultimo.getIndice());
        acomodaAbajo(ultimo.getIndice());
        elemento.setIndice(-1);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        if (elemento.getIndice() < 0 || elemento.getIndice() >= elementos)
            return false;
        return arbol[elemento.getIndice()] == elemento;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        return elementos == 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        elementos = 0;
        arbol = null;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        acomodaArriba(elemento.getIndice());
        acomodaAbajo(elemento.getIndice());
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        if (i < 0 || i >= elementos) 
            throw new NoSuchElementException("El indice está fuera del rango");
        return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        String s = "";
        for (T elemento : arbol)
            s += String.format("%s, ",elemento.toString());
        return s;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
        for (int i = 0; i < arbol.length; i++) {
            if (i >= monticulo.elementos)
                return false;
            if (arbol[i].compareTo(monticulo.arbol[i]) != 0)
                return false;
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        Lista<Adaptador<T>> adaptadores = new Lista<>();
        Lista<T> coleccionElementos = new Lista<>();
        for (T elemento : coleccion) {
            adaptadores.agregaInicio(new Adaptador<T>(elemento));
        }
        MonticuloMinimo<Adaptador<T>> monticulo = new MonticuloMinimo<>(adaptadores);
        T raiz = null;
        while (!monticulo.esVacia()) {
            raiz = monticulo.elimina().elemento;
            coleccionElementos.agregaFinal(raiz);
        }
        return coleccionElementos;
    }
}
