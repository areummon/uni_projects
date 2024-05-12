package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        protected T elemento;
        /** El padre del vértice. */
        protected Vertice padre;
        /** El izquierdo del vértice. */
        protected Vertice izquierdo;
        /** El derecho del vértice. */
        protected Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        protected Vertice(T elemento) {
            this.elemento = elemento;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <code>true</code> si el vértice tiene padre,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayPadre() {
            return padre != null;
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <code>true</code> si el vértice tiene izquierdo,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
            return izquierdo != null;
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <code>true</code> si el vértice tiene derecho,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayDerecho() {
            return derecho != null;
        }

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
            if (padre == null)
                throw new NoSuchElementException("No existe padre");
            return padre;
        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
            if (izquierdo == null)
                throw new NoSuchElementException("No existe hijo izquierdo");
            return izquierdo;

        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
            if (derecho == null)
                throw new NoSuchElementException("No existe hijo derecho");
            return derecho;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            return altura(this);
        }

        /* Metodo auxiliar privado para saber la altura de un vertice */
        private int altura(Vertice vertice){
            if (vertice == null)
                return -1;
            return maximo(altura(vertice.izquierdo)+1,altura(vertice.derecho)+1);
        }


        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
            return profundidad(this);
        }

        /* Metodo auxiliar privado para saber la profundidad de un vertice */
        private int profundidad(Vertice vertice){
            if (vertice.padre == null)
                return 0;
            return profundidad(vertice.padre)+1;
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
            return this.elemento;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)objeto;
            if (!this.elemento.equals(vertice.elemento))
                return false;
            return equals(this.izquierdo,vertice.izquierdo) && 
                equals(this.derecho,vertice.derecho);
        }
        
        /* Metodo privado auxiliar para comparar un arbol con otro */
        private boolean equals(Vertice original, Vertice nuevo){
            if (original == null && nuevo == null)
                return true;
            if (original == null || nuevo == null)
                return false;
            if (!original.elemento.equals(nuevo.elemento))
                return false;
            else return equals(original.izquierdo,nuevo.izquierdo) &&
                        equals(original.derecho,nuevo.derecho);
        }

        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        @Override public String toString() {
            return elemento.toString();
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        for (T elemento : coleccion)
            agrega(elemento);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        Vertice vertice = new Vertice(elemento);
        return vertice;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
        return altura(raiz);
    }
    
    /* Metodo auxiliar privado para saber la altura del arbol */
    private int altura(Vertice vertice){
        if (vertice == null)
            return -1;
        return maximo(altura(vertice.izquierdo)+1,altura(vertice.derecho)+1);
    }

    /* Metodo privado auxiliar para saber el maximo de dos enteros */
    private int maximo(int n, int n2){
        if (n > n2)
            return n;
        else if (n < n2)
            return n2;
        else return n;
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
        return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return busca(elemento,raiz) != null;
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <code>null</code>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        return busca(elemento,raiz);
    }
    
    /* Metodo privado auxiliar para buscar en un arbol */
    private VerticeArbolBinario<T> busca(T elemento, Vertice vertice) {
        if (vertice == null)
            return null;
        if (elemento.equals(vertice.elemento))
            return vertice;
        VerticeArbolBinario<T> izquierdo = busca(elemento,vertice.izquierdo);
        VerticeArbolBinario<T> derecho = busca(elemento,vertice.derecho);
        if (izquierdo == null && derecho == null)
            return null;
        else if (izquierdo == null)
            return derecho;
        else return izquierdo;
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
        if (raiz == null)
            throw new NoSuchElementException("El árbol es vacío");
        return raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return raiz == null;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        raiz = null;
        elementos = 0;
    }

    /**
     * Compara el árbol con un objeto.
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)objeto;
        return equals(raiz,arbol.raiz);
    }
   
    /* Metodo privado auxiliar para comparar un arbol con otro */
    private boolean equals(Vertice original, Vertice nuevo){
        if (original == null && nuevo == null)
            return true;
        if (original == null || nuevo == null)
            return false;
        if (!original.elemento.equals(nuevo.elemento))
            return false;
        else return equals(original.izquierdo,nuevo.izquierdo) &&
                    equals(original.derecho,nuevo.derecho);
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        if (raiz == null)
            return "";
        int[] arreglo = new int[altura() + 1];
        for (int i = 0; i < altura() + 1; i++)
            arreglo[i] = 0;
        return toString(raiz,0,arreglo);
    }

    /* Metodo privado auxiliar para la representación en cadena de un árbol. */
    private String toString(Vertice vertice, int nivel, int[] arreglo){
        String s = vertice.toString() + "\n";
        arreglo[nivel] = 1;
        if (vertice.izquierdo != null && vertice.derecho != null) {
            s += dibujaEspacios(nivel,arreglo);
            s += "├─›";
            s += toString(vertice.izquierdo,nivel+1,arreglo);
            s += dibujaEspacios(nivel,arreglo);
            s += "└─»";
            arreglo[nivel] = 0;
            s += toString(vertice.derecho,nivel+1,arreglo);
        }
        else if (vertice.izquierdo != null) {
            s += dibujaEspacios(nivel,arreglo);
            s += "└─›";
            arreglo[nivel] = 0;
            s += toString(vertice.izquierdo,nivel+1,arreglo);
        }
        else if (vertice.derecho != null) {
            s += dibujaEspacios(nivel,arreglo);
            s += "└─»";
            arreglo[nivel] = 0;
            s += toString(vertice.derecho,nivel+1,arreglo);
        }
        return s;
    }

    /* Metodo privado auxiliar para dibujar la cadena antes de un vértice*/
    private String dibujaEspacios(int nivel, int[] arreglo){
        String s = "";
        for (int i = 0; i < nivel; i++) {
            if (arreglo[i]==1)
                s += "│  ";
            else 
                s += "   ";
        }
        return s;
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice)vertice;
    }
}
