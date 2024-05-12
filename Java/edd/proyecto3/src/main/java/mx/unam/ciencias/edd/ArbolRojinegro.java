package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override public String toString() {
            if (color == Color.ROJO)
                return "R{" + elemento.toString() + "}";
            else 
                return "N{" + elemento.toString() + "}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            return (color == vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro verticeRojo = rojinegro(vertice);
        return verticeRojo.color;
    }

    /* Metodo auxiliar privado para verificar si un VerticeRojinegro es rojo */
    private boolean esRojo(VerticeRojinegro vertice) {
        return vertice != null && vertice.color == Color.ROJO;
    }

    /* Metodo auxiliar privado para verificar si un VerticeRojinegro es negro */
    private boolean esNegro(VerticeRojinegro vertice) {
        return vertice == null || vertice.color == Color.NEGRO;
    }

    /* Metodo privado auxiliar para hacer audiciones a un VerticeRojinegro */
    private VerticeRojinegro rojinegro(VerticeArbolBinario<T> vertice) {
        if (vertice == null)
            return null;
        VerticeRojinegro nuevo = (VerticeRojinegro)vertice;
        return nuevo;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeRojinegro ultimo = rojinegro(ultimoAgregado);
        ultimo.color = Color.ROJO;
        rebalanceaAgrega(ultimo);
    }

    /* Metodo auxiliar privado para obtener al padre de un vertice */
    private VerticeRojinegro padre(VerticeRojinegro vertice) {
        return rojinegro(vertice.padre);
    }

    /* Metodo auxiliar privado para obtener al tio de un vertice */
    private VerticeRojinegro tio(VerticeRojinegro vertice) {
        VerticeRojinegro padre = padre(vertice);
        VerticeRojinegro abuelo = abuelo(vertice);
        if (abuelo.izquierdo == padre)
            return rojinegro(abuelo.derecho);
        else
            return rojinegro(abuelo.izquierdo);
    }

    /* Metodo auxiliar privado para obtener al hermano de un vertice */
    private VerticeRojinegro hermano(VerticeRojinegro vertice) {
        if (vertice.padre.izquierdo == vertice)
            return rojinegro(vertice.padre.derecho);
        else
            return rojinegro(vertice.padre.izquierdo);
    }

    /* Metodo auxiliar privado para obtener al abuelo de un vertice */
    private VerticeRojinegro abuelo(VerticeRojinegro vertice) {
        return rojinegro(vertice.padre.padre);
    }

    /* Metodo auxiliar privado para rebalancear el arbol al agregar elemento */
    private void rebalanceaAgrega(VerticeRojinegro vertice) {
        VerticeRojinegro aux;
        if (vertice.padre == null) {
            vertice.color = Color.NEGRO;
            return;
        }
        VerticeRojinegro padre = padre(vertice);
        if (esNegro(padre))
            return;
        VerticeRojinegro abuelo = abuelo(vertice);
        VerticeRojinegro tio = tio(vertice);
        if (esRojo(tio)) {
            tio.color = Color.NEGRO;
            padre.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            rebalanceaAgrega(abuelo);
            return;
        }
        if (sonCruzados(abuelo,padre,vertice)) {
            if (abuelo.izquierdo == padre)
                super.giraIzquierda(padre);
            else 
                super.giraDerecha(padre);
            aux = padre;
            padre = vertice;
            vertice = aux;
        }
        padre.color = Color.NEGRO;
        abuelo.color = Color.ROJO;
        if (padre.izquierdo == vertice)
            super.giraDerecha(abuelo);
        else
            super.giraIzquierda(abuelo);
    }

    /* Metodo privado auxiliar para saber si dos vertices estan cruzados */
    private boolean sonCruzados(VerticeRojinegro abuelo, VerticeRojinegro padre,
                                VerticeRojinegro hijo) {
        return abuelo.derecho == padre && padre.izquierdo == hijo ||
               abuelo.izquierdo == padre && padre.derecho == hijo;
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeRojinegro vertice = rojinegro(busca(elemento));
        VerticeRojinegro fantasma = null;
        if (vertice == null)
            return;
        elementos--;
        if (vertice.hayIzquierdo() && vertice.hayDerecho()) 
            vertice = rojinegro(intercambiaEliminable(vertice));
        if (!vertice.hayIzquierdo() && !vertice.hayDerecho()) {
            fantasma = rojinegro(nuevoVertice(null));
            fantasma.color = Color.NEGRO;
            vertice.izquierdo = fantasma;
            fantasma.padre = vertice;
        }
        VerticeRojinegro hijo = null;
        if (vertice.izquierdo != null)
            hijo = rojinegro(vertice.izquierdo);
        else
            hijo = rojinegro(vertice.derecho);
        VerticeRojinegro padre = rojinegro(vertice.padre);
        eliminaVertice(vertice);
        if (esRojo(hijo)) {
            hijo.color = Color.NEGRO;
            return;
        }
        else if (esRojo(vertice)) {}
        else if (esNegro(vertice) && esNegro(hijo)) {
            rebalanceoElimina(hijo);
        }
        if (fantasma != null)
            eliminaVertice(fantasma);
    }

    /* Metodo privado auxiliar para rebalancear despues de eliminar */
    private void rebalanceoElimina(VerticeRojinegro vertice) {
        if (vertice.padre == null) {
            return;
        }
        VerticeRojinegro padre = padre(vertice);
        VerticeRojinegro hermano = hermano(vertice);
        if (esRojo(hermano)) {
            padre.color = Color.ROJO;
            hermano.color = Color.NEGRO;
            if (padre.izquierdo == vertice)
                super.giraIzquierda(padre);
            else
                super.giraDerecha(padre);
            hermano = hermano(vertice);
        }
        VerticeRojinegro hijoIzquierdo = rojinegro(hermano.izquierdo);
        VerticeRojinegro hijoDerecho = rojinegro(hermano.derecho);
        if (esNegro(padre) && esNegro(hermano) && esNegro(hijoIzquierdo) &&
            esNegro(hijoDerecho)) {
            hermano.color = Color.ROJO;
            rebalanceoElimina(padre);
            return;
        }
        if (esNegro(hermano) && esNegro(hijoIzquierdo) &&
                 esNegro(hijoDerecho) && esRojo(padre)) {
            hermano.color = Color.ROJO;
            padre.color = Color.NEGRO;
            return;
        }
        if (padre.izquierdo == vertice && esRojo(hijoIzquierdo) && esNegro(hijoDerecho) ||
            padre.derecho == vertice && esNegro(hijoIzquierdo) && esRojo(hijoDerecho)) {
            hermano.color = Color.ROJO;
            if (esRojo(hijoIzquierdo))
                hijoIzquierdo.color = Color.NEGRO;
            else if (esRojo(hijoDerecho))
                hijoDerecho.color = Color.NEGRO;
            if (padre.izquierdo == vertice)
                super.giraDerecha(hermano);
            else if (padre.derecho == vertice)
                super.giraIzquierda(hermano);
            hermano = hermano(vertice);
        }
        hijoIzquierdo = rojinegro(hermano.izquierdo);
        hijoDerecho = rojinegro(hermano.derecho);
        hermano.color = padre.color;
        padre.color = Color.NEGRO;
        if (padre.izquierdo == vertice) {
            hijoDerecho.color = Color.NEGRO;
            super.giraIzquierda(padre);
        }
        else {
            hijoIzquierdo.color = Color.NEGRO;
            super.giraDerecha(padre);
        }
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");

    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
