package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            cola = new Cola<>();
            if (raiz != null)
                cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            Vertice vertice = cola.saca();
            if (vertice.izquierdo != null)
                cola.mete(vertice.izquierdo);
            if (vertice.derecho != null)
                cola.mete(vertice.derecho);
            return vertice.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento nulo");
        Vertice vertice = nuevoVertice(elemento);
        elementos++;
        if (raiz == null){
            raiz = vertice;
            return;
        }
        agrega(vertice);
    }

    /* Metodo privado auxiliar para agregar un elemento en bfs */
    private void agrega(Vertice vertice) {
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        Vertice verticeActual;
        while (!cola.esVacia()) {
            verticeActual = cola.saca();
            if (!verticeActual.hayIzquierdo()) {
                verticeActual.izquierdo = vertice;
                vertice.padre = verticeActual;
                return;
            } else
                cola.mete(verticeActual.izquierdo);
            if (!verticeActual.hayDerecho()) {
                verticeActual.derecho = vertice;
                vertice.padre = verticeActual;
                return;
            } else 
                cola.mete(verticeActual.derecho);
        }
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice vertice = vertice(busca(elemento));
        if (vertice == null)
            return;
        elementos--;
        if (elementos == 0) {
            raiz = null;
            return;
        }
        elimina(vertice);
    }

    /* Metodo privado auxiliar para eliminar un elemento */
    private void elimina(Vertice vertice) {
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        Vertice ultimo = eliminaBfs(cola,vertice);
        T ultimoElemento = ultimo.elemento;
        ultimo.elemento = vertice.elemento;
        vertice.elemento = ultimoElemento;
        if (ultimo == ultimo.padre.izquierdo)
            ultimo.padre.izquierdo = null;
        else ultimo.padre.derecho = null;
    }

    /* Metodo privado auxilliar para eliminar usando bfs */
    private Vertice eliminaBfs(Cola<Vertice> cola, Vertice vertice) {
        Vertice verticeActual = cola.saca();
        if (verticeActual.hayIzquierdo())
            cola.mete(verticeActual.izquierdo);
        if (verticeActual.hayDerecho())
            cola.mete(verticeActual.derecho);
        if (cola.esVacia())
            return verticeActual;
        return eliminaBfs(cola,verticeActual);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        return log(elementos);
    }

    /* Metodo auxiliar para calcular el logaritmo */
    private int log(int n) {
        if (n == 0)
            return -1;
        int c = 0;
        while (n > 1) {
            n /= 2;
            c++;
        }
        return c;
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null)
            return;
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        Vertice vertice;
        while (!cola.esVacia()) {
            vertice = cola.saca();
            accion.actua(vertice);
            if (vertice.izquierdo != null)
                cola.mete(vertice.izquierdo);
            if (vertice.derecho != null)
                cola.mete(vertice.derecho);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
