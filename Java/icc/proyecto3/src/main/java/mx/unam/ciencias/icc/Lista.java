package mx.unam.ciencias.icc;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para listas genéricas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas implementan la interfaz {@link Iterable}, y por lo tanto se
 * pueden recorrer usando la estructura de control <em>for-each</em>. Las listas
 * no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Iterable<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if (siguiente == null)
                throw new NoSuchElementException();
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if (anterior == null)
                throw new NoSuchElementException();
            siguiente = anterior;
            anterior = anterior.anterior;
            return siguiente.elemento; 
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            siguiente = cabeza;
            anterior = null;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            siguiente = null;
            anterior = rabo;
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
    public void agregaFinal(T elemento) {
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
    public void agregaInicio(T elemento) {
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
    public void inserta(int i, T elemento) {
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
    public void elimina(T elemento) {
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
    public T eliminaPrimero() {
        if (cabeza == null) {
            throw new NoSuchElementException("La lista es vacía");
	} else {
		T regreso = cabeza.elemento;
		eliminaNodo(cabeza);
		return regreso;
	}
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if (cabeza == null){
            throw new NoSuchElementException("La lista es vacía");
	}
	else {
		T regreso = rabo.elemento;
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
    public boolean contiene(T elemento) {
        return buscaNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> reversa = new Lista<>();
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
    public Lista<T> copia() {
        Lista<T> copia = new Lista<>();
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
    public T getPrimero() {
        if (cabeza == null)
            throw new NoSuchElementException("La lista es vacía");
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
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
    public T get(int i) {
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
    public int indiceDe(T elemento) {
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
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        if (this.longitud != lista.longitud)
            return false;
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

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        if (longitud <= 1)
            return copia();
        Lista<T> lista1 = new Lista<>();
        Lista<T> lista2 = new Lista<>();
        Nodo aux = cabeza;
        int m = 0;
        if (longitud % 2 == 0){
            m = longitud/2;
            for (int i = 0; i < m; i++){
                lista1.agregaFinal(aux.elemento);
                aux = aux.siguiente;
            }
            for (int i = m; i < longitud; i++){
                lista2.agregaFinal(aux.elemento);
                aux = aux.siguiente;
            }
        }
        else if (longitud % 2 == 1){
            m = (longitud-1)/2;
            for (int i = 0; i < m; i++){
                lista1.agregaFinal(aux.elemento);
                aux = aux.siguiente;
            }
            for (int i = m; i < longitud; i++){
                lista2.agregaFinal(aux.elemento);
                aux = aux.siguiente;
            }
        }
        lista1 = lista1.mergeSort(comparador);
        lista2 = lista2.mergeSort(comparador);
        return mezcla(lista1, lista2, comparador);
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Metodo auxiliar Mezcla para Mergesort 
     */
    private Lista<T> mezcla(Lista<T> lista1, Lista<T> lista2, Comparator <T> comparador){
        Lista<T> l = new Lista<>();
        Nodo i = lista1.cabeza;
        Nodo j = lista2.cabeza;
        while (i != null && j != null) {
            if (comparador.compare(i.elemento,j.elemento) <= 0) {
                l.agregaFinal(i.elemento);
                i = i.siguiente;
            }
            else if (comparador.compare(i.elemento,j.elemento) > 0) {
                l.agregaFinal(j.elemento);
                j = j.siguiente;
            }

            if (i == null){
                while(j != null){
                    l.agregaFinal(j.elemento);
                    j = j.siguiente;
                }
            }
            else if (j == null){
                while (i != null){
                    l.agregaFinal(i.elemento);
                    i = i.siguiente;
                }
            }
        }
        return l;
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        if (comparador.compare(elemento,cabeza.elemento) < 0)
            return false;
        if (comparador.compare(elemento,rabo.elemento) > 0)
            return false;
        Nodo aux = cabeza;
        while (aux != null){
            if (comparador.compare(elemento,aux.elemento) == 0)
                return true;
            aux = aux.siguiente;
        }
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
