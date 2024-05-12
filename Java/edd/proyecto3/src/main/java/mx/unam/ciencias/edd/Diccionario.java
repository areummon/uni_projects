package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, mapeando un conjunto de <em>llaves</em> a una colección
 * de <em>valores</em>.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase interna privada para entradas. */
    private class Entrada {

        /* La llave. */
        public K llave;
        /* El valor. */
        public V valor;

        /* Construye una nueva entrada. */
        public Entrada(K llave, V valor) {
            this.llave = llave;
            this.valor = valor;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador {

        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        public Iterador() {
            for (int i = 0; i < entradas.length;i++) {
                if (entradas[i] != null) {
                    indice = i;
                    iterador = entradas[i].iterator();
                    break;
                }
            }
        }

        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext() {
            return iterador != null;
        }

        /* Regresa la siguiente entrada. */
        public Entrada siguiente() {
            if (iterador == null)
                throw new NoSuchElementException("No hay iterador");
            Entrada entrada = iterador.next();
            int aux = indice;
            if (!iterador.hasNext()) {
                for (int i = indice+1; i < entradas.length;i++) {
                    if (entradas[i] != null) {
                        indice = i;
                        iterador = entradas[i].iterator();
                        break;
                    }
                }
                if (indice == aux)
                    iterador = null;
            }
            return entrada;
        }
    }

    /* Clase interna privada para iteradores de llaves. */
    private class IteradorLlaves extends Iterador
        implements Iterator<K> {

        /* Regresa el siguiente elemento. */
        @Override public K next() {
            return siguiente().llave;
        }
    }

    /* Clase interna privada para iteradores de valores. */
    private class IteradorValores extends Iterador
        implements Iterator<V> {

        /* Regresa el siguiente elemento. */
        @Override public V next() {
            return siguiente().valor;
        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores. */
    private int elementos;

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[])Array.newInstance(Lista.class, n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
        this(MINIMA_CAPACIDAD, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
        this(capacidad, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
        this(MINIMA_CAPACIDAD, dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(int capacidad, Dispersor<K> dispersor) {
        this.dispersor = dispersor;
        entradas = nuevoArreglo(primeraPotencia(capacidad));
    }

    /* Metodo privado auxiliar para encontrar la primera potencia
     * de 2 que sea mayor o igual al doble de la capacidad recibida.
     */
    private int primeraPotencia(int capacidad) {
        capacidad = (capacidad < MINIMA_CAPACIDAD) ? MINIMA_CAPACIDAD : capacidad;
        int potencia = 1;
        while (potencia < capacidad * 2)
            potencia *= 2;
        return potencia;

    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
    public void agrega(K llave, V valor) {
        if (llave == null || valor == null)
            throw new IllegalArgumentException("Llave o valor es nulo");
        int indice = dispersor.dispersa(llave) & (entradas.length-1);
        if (entradas[indice] == null) 
            entradas[indice] = new Lista<Entrada>();
        Entrada entrada = buscaEntrada(llave,entradas[indice]);
        if (entrada != null)
            entrada.valor = valor;
        else {
            entradas[indice].agrega(new Entrada(llave,valor));
            elementos++;
        }
        if (carga() >= MAXIMA_CARGA) {
            Lista<Entrada>[] capacidad = entradas;
            Lista<Entrada>[] nuevaCapacidad = nuevoArreglo(entradas.length*2);
            entradas = nuevaCapacidad;
            nuevaLista(capacidad);
        }
    }

    /** Metodo privado auxiliar para agregar las entradas 
     * del anterior arreglo al nuevo. */
    private void nuevaLista(Lista<Entrada>[] lista) {
        elementos = 0;
        for (Lista<Entrada> l : lista) {
            if (l != null) {
                for (Entrada e : l) {
                    agrega(e.llave,e.valor);
                }
            }
        }
    }

    /** Metodo privado auxiliar para buscar una entrada por llave.
     * regresa la llave o null si no esta en la lista dada.
     */
    private Entrada buscaEntrada(K llave, Lista<Entrada> lista) {
        for (Entrada e : lista)
            if (e.llave.equals(llave))
                return e;
        return null;
    }

    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) {
        if (llave == null)
            throw new IllegalArgumentException("La llave recibida es nula");
        int indice = dispersor.dispersa(llave) & (entradas.length-1);
        if (entradas[indice] == null)
            throw new NoSuchElementException("La llave no esta en el diccionario");
        Entrada entrada = buscaEntrada(llave,entradas[indice]);
        if (entrada == null)
            throw new NoSuchElementException("La llave no esta en el diccionario");
        return entrada.valor;
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <code>true</code> si la llave está en el diccionario,
     *         <code>false</code> en otro caso.
     */
    public boolean contiene(K llave) {
        if (llave == null)
            return false;
        int indice = dispersor.dispersa(llave) & (entradas.length-1);
        if (entradas[indice] == null)
            return false;
        Entrada entrada = buscaEntrada(llave,entradas[indice]);
        return entrada == null ? false : true;
    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
        if (llave == null)
            throw new IllegalArgumentException("La llave recibida es nula");
        int indice = dispersor.dispersa(llave) & (entradas.length-1);
        if (entradas[indice] == null)
            throw new NoSuchElementException("La llave no esta en el diccionario");
        Entrada entrada = buscaEntrada(llave,entradas[indice]);
        if (entrada == null)
            throw new NoSuchElementException("La llave no esta en el diccionario");
        entradas[indice].elimina(entrada);
        if (entradas[indice].getElementos() == 0)
            entradas[indice] = null;
        elementos--;
    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        int contador = 0;
        for (Lista<Entrada> l : entradas) {
            if (l != null)
                contador += (l.getElementos()-1);
        }
        return contador;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        int colisionMaxima = 0;
        for (Lista<Entrada> l : entradas) {
            if (l != null && l.getElementos() > colisionMaxima)
                colisionMaxima = l.getElementos();
        }
        return colisionMaxima-1;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
        return (double)elementos/entradas.length;
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        return elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
        return elementos == 0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        entradas = nuevoArreglo(entradas.length);
        elementos = 0;
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * @return una representación en cadena del diccionario.
     */
    @Override public String toString() {
        if (elementos == 0)
            return "{}";
        String s = "{ ";
        Iterador iterador = new Iterador();
        Entrada entrada = null;
        while(iterador.hasNext()) {
            entrada = iterador.siguiente();
            s += String.format("'%s': '%s', ",entrada.llave.toString(),entrada.valor.toString());
        }
        return s + "}";
    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Diccionario<K, V> d =
            (Diccionario<K, V>)o;
        if (elementos != d.getElementos())
            return false;
        Iterador iterador = new Iterador();
        Entrada entrada = null;
        V valor = null;
        while(iterador.hasNext()) {
            try {
                entrada = iterador.siguiente();
                valor = d.get(entrada.llave);
            } catch (NoSuchElementException nse) {
                return false;
            }
            if(!valor.equals(entrada.valor))
                return false;
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new IteradorValores();
    }
}
