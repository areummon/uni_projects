package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        Nodo aux = cabeza;
        String cadena = "";
        while (aux != null){
            cadena += aux.elemento.toString() + ",";
            aux = aux.siguiente;
        }
        return cadena;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento es nulo");
        Nodo nuevo = new Nodo(elemento);
        if (rabo == null) {
            rabo = cabeza = nuevo;
        } else {
            rabo.siguiente = nuevo;
            rabo = nuevo;
        }
    }
}
