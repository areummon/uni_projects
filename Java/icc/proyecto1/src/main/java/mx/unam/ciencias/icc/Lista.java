package mx.unam.ciencias.icc;

import java.util.NoSuchElementException;

/**
 * <p>Clase para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas son iterables utilizando sus nodos. Las listas no aceptan a
 * <code>null</code> como elemento.</p>
 */
public class Lista {

    /**
     * Clase interna para nodos.
     */
    public class Nodo {

        /* El elemento del nodo. */
        private Object elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(Object elemento) {
            this.elemento = elemento;
        }

        /**
         * Regresa el nodo anterior del nodo.
         * @return el nodo anterior del nodo.
         */
        public Nodo getAnterior() {
            return anterior;
        }

        /**
         * Regresa el nodo siguiente del nodo.
         * @return el nodo siguiente del nodo.
         */
        public Nodo getSiguiente() {
            return siguiente;
        }

        /**
         * Regresa el elemento del nodo.
         * @return el elemento del nodo.
         */
        public Object get() {
            return elemento;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    public boolean esVacia() {
        return cabeza == null;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(Object elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento es null"); 

        Nodo n = new Nodo(elemento);
        longitud++;
        if (rabo == null) {
            cabeza = rabo = n;
        } else {
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
        }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(Object elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento es null");

	Nodo n = new Nodo(elemento);
	longitud++;
	if (cabeza == null){
	    rabo = cabeza = n;
	} else {
            cabeza.anterior = n;
	    n.siguiente = cabeza;
            cabeza = n;
	}
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, Object elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento es null");

	    if (i < 1) {
		    agregaInicio(elemento);
	    } else if (i > longitud -1) {
		    agregaFinal(elemento);
	    } else {
		    longitud ++;
		    Nodo n = new Nodo(elemento);
		    Nodo aux = cabeza;
		    int c = 0;
		    while (c++ < i){
			    aux = aux.siguiente;
		    }
		    n.anterior = aux.anterior;
		    aux.anterior.siguiente = n;
		    n.siguiente = aux;
		    aux.anterior = n;
            }
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    public void elimina(Object elemento) {
        Nodo nodo = buscaNodo(elemento);
        if (nodo == null){
            return;
        } else {
            eliminaNodo(nodo);
        }
    }

    /*
     * Busca un Nodo cualquiera con un elemento de un Object dado
     */
    private Nodo buscaNodo(Object elemento){
        if (elemento == null)
            return null;
        if (cabeza == null && rabo == null) {
            return null;
        } else {
            Nodo aux = cabeza;
            while (aux != null) {
                if (aux.elemento.equals(elemento)){
                    return aux;
                }
                aux = aux.siguiente;
            }
            return null;
        }
    }

    /*
     * Elimina cualquier nodo que se le de como parametro
     */
    private void eliminaNodo(Nodo nodo){
        longitud--;
	    if (cabeza == rabo){
		    cabeza = rabo = null;
	    } else if (nodo == cabeza){
		    nodo.siguiente.anterior = null;
		    cabeza = nodo.siguiente;
	    } else if (nodo == rabo){
		    nodo.anterior.siguiente = null;
		    rabo = nodo.anterior;
	    } else {
		    nodo.anterior.siguiente = nodo.siguiente;
		    nodo.siguiente.anterior = nodo.anterior;
	    }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public Object eliminaPrimero() {
        if (cabeza == null) {
            throw new NoSuchElementException("La lista es vacía");
	} else {
		Object regreso = cabeza.elemento;
		eliminaNodo(cabeza);
		return regreso;
	}
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public Object eliminaUltimo() {
        if (cabeza == null){
            throw new NoSuchElementException("La lista es vacía");
	}
	else {
		Object regreso = rabo.elemento;
		eliminaNodo(rabo);
		return regreso;
	}
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean contiene(Object elemento) {
        return buscaNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista reversa() {
        Lista reversa = new Lista();
        for (Nodo n = this.cabeza; n != null; n = n.siguiente){
            reversa.agregaInicio(n.elemento);
        }
        return reversa;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista copia() {
        Lista copia = new Lista();
        for (Nodo n = this.cabeza; n != null; n = n.siguiente){
            copia.agregaFinal(n.elemento);
        }
        return copia;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    public void limpia() {
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public Object getPrimero() {
        if (cabeza == null)
            throw new NoSuchElementException("La lista es vacía");
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public Object getUltimo() {
        if (rabo == null)
            throw new NoSuchElementException("La lista es vacía");
        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public Object get(int i) {
        if (i < 0 || i >= longitud){
            throw new ExcepcionIndiceInvalido("El indice es menor que cero o mayor o igual al numero de elementos de la lista");
        } else {
		Nodo aux = cabeza; 
		for (int c = 0; c < i; c++){
			aux = aux.siguiente;
		}
		return aux.elemento;
	}
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(Object elemento) {
        if (elemento == null)
		    return -1;
	    Nodo aux = cabeza;
	    int i = 0;
	    while (aux != null) {
		    if (aux.elemento == elemento){
			    return i;
		    }
		    aux = aux.siguiente;
		    i++;
	    }
	    return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        if (cabeza == null)
            return "[]";

        StringBuffer sb = new StringBuffer();
        Nodo aux = cabeza.siguiente;
        sb.append("[")
            .append(cabeza.elemento.toString());
        while (aux != null){
            sb.append(", ")
                .append(aux.elemento.toString());
            aux = aux.siguiente;
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null)
            return false;
        if (objeto instanceof Lista){
            Lista lista = (Lista) objeto;
            if (this.longitud != lista.longitud){
                return false;
            } else {
                Nodo aux = this.cabeza;
                Nodo temp = lista.cabeza;
                while (aux != null) {
                    if (aux.elemento.equals(temp.elemento)){
                        aux = aux.siguiente;
                        temp = temp.siguiente;
                    }
                    else {
                        return false;
                    }
                }
                return true;
                }
            } else {
                return false;
            }
    }

    /**
     * Regresa el nodo cabeza de la lista.
     * @return el nodo cabeza de la lista.
     */
    public Nodo getCabeza() {
        return cabeza;
    }

    /**
     * Regresa el nodo rabo de la lista.
     * @return el nodo rabo de la lista.
     */
    public Nodo getRabo() {
        return rabo;
    }
}
