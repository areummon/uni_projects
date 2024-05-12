package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            return String.format("%s %d/%d",elemento.toString(), 
                                 altura, balance(this));
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            return (altura == vertice.altura && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
    }

    /* Metodo privado auxiliar para hacer audiciones a un VerticeAVL */
    private VerticeAVL avl(VerticeArbolBinario<T> vertice) {
        if (vertice == null)
            return null;
        VerticeAVL nuevo = (VerticeAVL)vertice;
        return nuevo;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        rebalanceo(avl(ultimoAgregado.padre));
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeAVL vertice = avl(busca(elemento));
        if (vertice == null)
            return;
        if (vertice.izquierdo != null && vertice.derecho != null)
            vertice = avl(intercambiaEliminable(vertice));
        elementos--;
        eliminaVertice(vertice);
        rebalanceo(avl(vertice.padre));
    }

    /* Metodo privado auxiliar para obtener la altura de un vértice */
    private int altura(VerticeAVL vertice) {
        return vertice == null ? -1 : vertice.altura;
    }

    /* Metodo privado auxiliar para obtener el balance de un vertice. */
    private int balance(VerticeAVL vertice) {
        return altura(avl(vertice.izquierdo)) - altura(avl(vertice.derecho));
    }

    /* Metodo privado auxiliar para obtener la alura maxima entre dos vertices. */
    private int alturaMax(VerticeAVL izquierdo, VerticeAVL derecho) {
        return altura(izquierdo) < altura(derecho) ? altura(derecho)+1 : altura(izquierdo)+1;
    }

    /**
     * Metodo privado auxiliar para rebalancear arboles avl.
     */
    private void rebalanceo(VerticeAVL vertice) {
        if (vertice == null)
            return;
        VerticeAVL padre = avl(vertice.padre);
        VerticeAVL izquierdo = avl(vertice.izquierdo);
        VerticeAVL derecho = avl(vertice.derecho);
        vertice.altura = alturaMax(izquierdo,derecho);
        int balance = balance(vertice);
        if (balance == -2) {
            int altura = altura(vertice);
            VerticeAVL izquierdoHijo = avl(derecho.izquierdo);
            VerticeAVL derechoHijo = avl(derecho.derecho);
            if (balance(derecho) == 1) {
                super.giraDerecha(derecho);
                izquierdoHijo.altura = altura-1;
                derecho.altura = altura-2;
                derecho = avl(vertice.derecho);
                izquierdoHijo = avl(derecho.izquierdo);
                derechoHijo = avl(derecho.derecho);
            }
            altura = alturaMax(izquierdo,derecho);
            super.giraIzquierda(vertice);
            if (altura(izquierdoHijo) == altura-2) {
                vertice.altura = altura - 1;
                derecho.altura = altura;
            }
            else {
                vertice.altura = altura - 2;
                derecho.altura = altura - 1;
            }
        }
        else if (balance == 2) {
            int altura = altura(vertice);
            VerticeAVL izquierdoHijo = avl(izquierdo.izquierdo);
            VerticeAVL derechoHijo = avl(izquierdo.derecho);
            if (balance(izquierdo) == -1) {
                super.giraIzquierda(izquierdo);
                derechoHijo.altura = altura-1;
                izquierdo.altura = altura-2;
                izquierdo = avl(vertice.izquierdo);
                izquierdoHijo = avl(izquierdo.izquierdo);
                derechoHijo = avl(izquierdo.derecho);
            }
            altura = alturaMax(izquierdo,derecho);
            super.giraDerecha(vertice);
            if (altura(derechoHijo) == altura - 2) {
                vertice.altura = altura - 1;
                izquierdo.altura = altura;
            }
            else {
                vertice.altura = altura - 2;
                izquierdo.altura = altura - 1;
            }
        }
        rebalanceo(padre);
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
