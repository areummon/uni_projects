package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return iterador.next().get();
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        private T elemento;
        /* El color del vértice. */
        private Color color;
        /* La distancia del vértice. */
        private double distancia;
        /* El índice del vértice. */
        private int indice;
        /* El diccionario de vecinos del vértice. */
        private Diccionario<T, Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            color = Color.NINGUNO;
            vecinos = new Diccionario<>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            if (this.distancia < 0)
                return 1;
            if (vertice.distancia < 0)
                return -1;
            if (this.distancia == vertice.distancia)
                return 0;
            return this.distancia < vertice.distancia ? -1 : 1;
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            return vecino.color;
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecino.vecinos;
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino<T> {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica<T>.Vertice v, Grafica<T>.Vecino a);
    }

    private Lista<VerticeGrafica<T>> reconstruye(Vertice origen, Vertice destino, 
            BuscadorCamino<T> buscador) {
        Lista<VerticeGrafica<T>> lista = new Lista<>();
        lista.agregaInicio(destino);
        Vertice actual = null;
        while (lista.getPrimero() != origen) {
            actual = verticeAudicion(lista.getPrimero());
            for (Vecino vecino : actual.vecinos) {
                if (buscador.seSiguen(actual,vecino)) {
                    lista.agregaInicio(vecino.vecino);
                    break;
                }
            }
        }
        return lista;
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Diccionario<>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null || contiene(elemento))
            throw new IllegalArgumentException("El elemento es null o ya existe");
        Vertice vertice = new Vertice(elemento);
        vertices.agrega(elemento,vertice);
    }

    /**
     * Metodo privado auxiliar para hacer audiciones de un VerticeGrafica a un
     * vertice.
     */
    private Vertice verticeAudicion(VerticeGrafica<T> vertice) {
        if (Vertice.class != vertice.getClass())
            throw new IllegalArgumentException("El vertice no es de una gráfica");
        return (Vertice)vertice;
    }

    /** Metodo privado auxiliar para regresar al vecino buscado de un vertice */
    private Vecino vecino(Vertice vertice, T b) {
        Vecino vecino = null;
        try { 
            vecino = vertice.vecinos.get(b);
        } catch (NoSuchElementException nsee) {
            return null;
        }
        return vecino;
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        conecta(a,b,1);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException("El elemento no está en la gráfica");
        if (a.equals(b))
            throw new IllegalArgumentException("El elemento es el mismo");
        if (sonVecinos(a,b))
            throw new IllegalArgumentException("Los elementos ya están conectados");
        if (peso < 0)
            throw new IllegalArgumentException("El peso no es positivo");
        Vertice verticeA = verticeAudicion(vertice(a));
        Vertice verticeB = verticeAudicion(vertice(b));
        verticeA.vecinos.agrega(b,new Vecino(verticeB,peso));
        verticeB.vecinos.agrega(a,new Vecino(verticeA,peso));
        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        Vertice verticeA = verticeAudicion(vertice(a));
        Vertice verticeB = verticeAudicion(vertice(b));
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException("El elemento no está en la gráfica");
        if (!sonVecinos(a,b))
            throw new IllegalArgumentException("Los elementos no están conectados");
        verticeA.vecinos.elimina(b);
        verticeB.vecinos.elimina(a);
        aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return vertices.contiene(elemento);
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        Vertice vertice = verticeAudicion(vertice(elemento));
        vertices.elimina(elemento);
        for (Vecino vecino : vertice.vecinos) {
            vecino.vecino.vecinos.elimina(elemento);
            aristas--;
        }
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException("El elemento no está en la gráfica");
        return vecino(verticeAudicion(vertice(a)),b) != null;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException("El elemento no está en la gráfica");
        if (!sonVecinos(a,b))
            throw new IllegalArgumentException("Los elementos no están conectados");
        Vecino vecino = vecino(verticeAudicion(vertice(a)),b);
        return vecino.peso;
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException("El elemento no está en la gráfica");
        if (!sonVecinos(a,b))
            throw new IllegalArgumentException("Los elementos no están conectados");
        if (peso <= 0)
            throw new IllegalArgumentException("El peso es menor o igual que cero");
        Vecino vecinoA = vecino(verticeAudicion(vertice(a)),b);
        Vecino vecinoB = vecino(verticeAudicion(vertice(b)),a);
        vecinoA.peso = peso;
        vecinoB.peso = peso;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        Vertice verticeContiene = vertices.get(elemento);
        return verticeContiene;
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice == null || vertice.getClass() != Vertice.class &&
                vertice.getClass() != Vecino.class) {
            throw new IllegalArgumentException("Vértice invalido");
        }
        if (vertice.getClass() == Vertice.class) {
            Vertice v = (Vertice)vertice;
            v.color = color;
        }
        if (vertice.getClass() == Vecino.class) {
            Vecino v = (Vecino)vertice;
            v.vecino.color = color;
        }
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        Vertice primero = null;
        for (Vertice vertice : vertices) {
            primero = vertice;
            break;
        }
        Integer[] contador = {0};
        bfs(primero.get(),(v) -> contador[0]++);
        return vertices.getElementos() == contador[0];    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for (Vertice vertice : vertices)
            accion.actua(vertice);
    }

    /** Metodo privado auxiliar para bfs y dfs. */
    private void breadthDepth(MeteSaca<Vertice> estructura, AccionVerticeGrafica<T> accion) {
        Vertice actual = null;
        while (!estructura.esVacia()) {
            actual = estructura.saca();
            accion.actua(actual);
            actual.color = Color.NINGUNO;
            for (Vecino vecino : actual.vecinos) {
                if (vecino.vecino.color == Color.ROJO) {
                    vecino.vecino.color = Color.NEGRO;
                    estructura.mete(vecino.vecino);
                }
            }
        }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        Vertice vertice = verticeAudicion(vertice(elemento));
        paraCadaVertice((v) -> setColor(v,Color.ROJO));
        Cola<Vertice> cola = new Cola<>();
        cola.mete(vertice);
        breadthDepth(cola,accion);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        Vertice vertice = verticeAudicion(vertice(elemento));
        paraCadaVertice((v) -> setColor(v,Color.ROJO));
        Pila<Vertice> pila = new Pila<>();
        pila.mete(vertice);
        breadthDepth(pila,accion);
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return vertices.getElementos() == 0;
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        vertices.limpia();
        aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        Diccionario<T, Vertice> aristasRepetidas = new Diccionario<>();
        String cadena = "{";
        for (Vertice vertice : vertices) {
            cadena += String.format("%s, ",vertice.get().toString());
        }
        cadena += "}, {";
        for (Vertice vertice : vertices) {
            for (Vecino vecino : vertice.vecinos) {
                if (aristasRepetidas.contiene(vecino.vecino.elemento))
                    continue;
                    cadena += String.format("(%s, %s), ",vertice.get().toString(),
                            vecino.get().toString());
            }
            aristasRepetidas.agrega(vertice.elemento,vertice);
        }
        cadena += "}";
        return cadena;
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        if (getElementos() != grafica.getElementos() || getAristas() != grafica.getAristas())
            return false;
        Vertice aux = null;
        for (Vertice vertice : vertices) {
            aux = buscaVertice(grafica.vertice(vertice.get()));
            if (aux == null)
                return false;
            for (Vecino vecino : vertice.vecinos) {
                if (!grafica.contiene(vecino.get()))
                    return false;
                if (!grafica.sonVecinos(aux.get(),vecino.get()))
                    return false;
            }
        }
        return true;
    }

    /** Metodo privado auxiliar para encontrar un vertice
     * y regresarlo, null si no existe el elemento. */
    private Vertice buscaVertice(VerticeGrafica<T> vertice) {
        Vertice buscado = null;
        try {
            buscado = verticeAudicion(vertice);
        } catch (NoSuchElementException nse) {
            return null;
        }
        return buscado;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        if (!contiene(origen) || !contiene(destino))
            throw new NoSuchElementException("El elemento no está en la gráfica");
        Vertice verticeOrigen = verticeAudicion(vertice(origen));
        Vertice verticeDestino = verticeAudicion(vertice(destino));
        Lista<VerticeGrafica<T>> lista = new Lista<>();
        if (origen == destino) {
            lista.agregaFinal(verticeOrigen);
            return lista;
        }
        for (Vertice vertice : vertices) {
            if (vertice.equals(verticeOrigen))
                continue;
            vertice.distancia = -1;
        }
        Cola<Vertice> cola = new Cola<>();
        cola.mete(verticeOrigen);
        Vertice actual = null;
        while(!cola.esVacia()) {
            actual = cola.saca();
            for (Vecino vecino : actual.vecinos) {
                if (vecino.vecino.distancia < 0) {
                    vecino.vecino.distancia = actual.distancia+1;
                    cola.mete(vecino.vecino);
                }
            }
        }
        if (verticeDestino.distancia < 0)
            return lista;
        return reconstruye(verticeOrigen,verticeDestino,
                (v,a) -> v.distancia-1 == a.vecino.distancia);
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        if (!contiene(origen) || !contiene(destino))
            throw new NoSuchElementException("El elemento no está en la gráfica");
        Vertice verticeOrigen = verticeAudicion(vertice(origen));
        Vertice verticeDestino = verticeAudicion(vertice(destino));
        for (Vertice vertice : vertices) {
            if (vertice == verticeOrigen)
                continue;
            vertice.distancia = -1;
        }
        long n = vertices.getElementos();
        MonticuloDijkstra<Vertice> monticulo = null;
        if (aristas > ((n*(n-1))/2)-n) 
            monticulo = new MonticuloArreglo<>(vertices,vertices.getElementos());
        else 
            monticulo = new MonticuloMinimo<>(vertices,vertices.getElementos());
        Vertice actual = null;
        while(!monticulo.esVacia()) {
            actual = monticulo.elimina();
            for (Vecino vecino : actual.vecinos) {
                if (vecino.vecino.distancia > actual.distancia+vecino.peso ||
                        vecino.vecino.distancia < 0) {
                    vecino.vecino.distancia = actual.distancia+vecino.peso;
                    monticulo.reordena(vecino.vecino);
                    }
            }
        }
        if (verticeDestino.distancia < 0)
            return new Lista<VerticeGrafica<T>>();
        return reconstruye(verticeOrigen,verticeDestino,
                (v,a) -> a.vecino.distancia == v.distancia-a.peso);
    }
}
