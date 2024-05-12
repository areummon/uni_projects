package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        private Iterador() {
            pila = new Pila<>();
            Vertice vertice = raiz;
            while(vertice != null) {
                pila.mete(vertice);
                vertice = vertice.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !pila.esVacia(); 
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            Vertice vertice = pila.saca();
            T elemento = vertice.elemento;
            if (vertice.derecho != null) {
                pila.mete(vertice.derecho);
                vertice = vertice.derecho;
                while(vertice != null) {
                    if(vertice.izquierdo != null)
                        pila.mete(vertice.izquierdo);
                    vertice = vertice.izquierdo;
                }
            }
            return elemento;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento nulo");
        Vertice vertice = nuevoVertice(elemento);
        ultimoAgregado = vertice;
        elementos++;
        if (raiz == null) {
            raiz = vertice;
            return;
        }
        agrega(raiz,vertice);
    }

    /* Metodo privado auxiliar para agregar elementos */
    private void agrega(Vertice verticeActual, Vertice vertice) {
        if (vertice.elemento.compareTo(verticeActual.elemento) <= 0) {
            if (verticeActual.izquierdo == null) {
                verticeActual.izquierdo = vertice;
                vertice.padre = verticeActual;
                return;
            } else agrega(verticeActual.izquierdo,vertice);
        }
        else {
            if (verticeActual.derecho == null) {
                verticeActual.derecho = vertice;
                vertice.padre = verticeActual;
                return;
            } else agrega(verticeActual.derecho, vertice);
        }
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice vertice = vertice(busca(elemento));
        if (vertice == null)
            return;
        elementos--;
        if (vertice.izquierdo != null && vertice.derecho != null) {
            vertice = intercambiaEliminable(vertice);
            eliminaVertice(vertice);
        } else eliminaVertice(vertice);
    }

    /* Metodo privado auxiliar para encontrar el maximo del subarbol derecho */
    private Vertice maximoEnSubarbol(Vertice vertice) {
        if (vertice.derecho == null)
            return vertice;
        return maximoEnSubarbol(vertice.derecho);
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        Vertice intercambiado = maximoEnSubarbol(vertice.izquierdo);
        T elementoHoja = intercambiado.elemento;
        intercambiado.elemento = vertice.elemento;
        vertice.elemento = elementoHoja;
        return intercambiado;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        Vertice hijo = null;
        Vertice padre = vertice.padre;
        if (vertice.izquierdo != null)
            hijo = vertice.izquierdo;
        else if (vertice.derecho != null)
            hijo = vertice.derecho;
        if (padre != null) {
            if (padre.izquierdo == vertice)
                padre.izquierdo = hijo;
            else 
                padre.derecho = hijo;
        } else if (padre == null) 
            raiz = hijo;
        if (hijo != null)
            hijo.padre = vertice.padre;
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        return busca(elemento,raiz);
    }

    /* Metodo privado auxiliar para buscar un elemento */
    private Vertice busca(T elemento, Vertice vertice) {
        if (vertice == null)
            return null;
        if (elemento.compareTo(vertice.elemento) == 0)
            return vertice;
        else if (elemento.compareTo(vertice.elemento) < 0)
            return busca(elemento,vertice.izquierdo);
        else 
            return busca(elemento,vertice.derecho);
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        Vertice girado = vertice(vertice);
        Vertice hijo = girado.izquierdo;
        Vertice derecho = hijo.derecho;
        Vertice padre = girado.padre;
        if(hijo == null)
            return;
        girado.padre = hijo;
        hijo.derecho = girado;
        if (derecho != null) {
            derecho.padre = girado;
            girado.izquierdo = derecho;
        } else girado.izquierdo = null;
        if (padre == null) {
            raiz = hijo;
            hijo.padre = null;
        }
        else {
            if (padre.izquierdo == girado) {
                padre.izquierdo = hijo;
                hijo.padre = padre;
            }
            else {
                padre.derecho = hijo;
                hijo.padre = padre;
            }
        }
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        Vertice girado = vertice(vertice);
        Vertice hijo = girado.derecho;
        Vertice izquierdo = hijo.izquierdo;
        Vertice padre = girado.padre;
        if(hijo == null)
            return;
        girado.padre = hijo;
        hijo.izquierdo = girado;
        if (izquierdo != null) {
            izquierdo.padre = girado;
            girado.derecho = izquierdo;
        } else girado.derecho = null;
        if (padre == null) {
            raiz = hijo;
            hijo.padre = null;
        }
        else {
            if (padre.izquierdo == girado) {
                padre.izquierdo = hijo;
                hijo.padre = padre;
            }
            else {
                padre.derecho = hijo;
                hijo.padre = padre;
            }
        }
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPreOrder(accion,raiz);
    }

    /* Metodo privado auxiliar para dfsPreOrder */
    private void dfsPreOrder(AccionVerticeArbolBinario<T> accion, Vertice vertice) {
        if (vertice == null)
            return;
        accion.actua(vertice);
        dfsPreOrder(accion,vertice.izquierdo);
        dfsPreOrder(accion,vertice.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        dfsInOrder(accion,raiz);
    }

    /* Metodo privado auxiliar para dfsInOrder*/
    private void dfsInOrder(AccionVerticeArbolBinario<T> accion, Vertice vertice) {
        if (vertice == null)
            return;
        dfsInOrder(accion,vertice.izquierdo);
        accion.actua(vertice);
        dfsInOrder(accion,vertice.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPostOrder(accion,raiz);
    }

    /* Metodo privado auxiliar para dfsPostOrder*/
    private void dfsPostOrder(AccionVerticeArbolBinario<T> accion, Vertice vertice) {
        if (vertice == null)
            return;
        dfsPostOrder(accion,vertice.izquierdo);
        dfsPostOrder(accion,vertice.derecho);
        accion.actua(vertice);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
