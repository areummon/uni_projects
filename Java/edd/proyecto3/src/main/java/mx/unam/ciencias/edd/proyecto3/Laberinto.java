package mx.unam.ciencias.edd.proyecto3;

import java.util.Iterator;
import java.util.Random;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Conjunto;
import mx.unam.ciencias.edd.VerticeGrafica;
import mx.unam.ciencias.edd.Pila;

/* Clase que representa un laberinto. */
public class Laberinto implements Iterable<Integer> {

    /* Clase interna privada para iteradores
     * de laberintos por renglones. */
    private class Iterador implements Iterator<Integer> {

        /* El indice posicion de la casilla actual. */
        int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return indice < casillas.length;
        }

        /* Regresa el siguiente elemento. */
        @Override public Integer next() {
            return ((casillas[indice].puntaje & 0xF) << 4) | casillas[indice++].puertas;
        }
    }

    /* Clase interna privada para iteradores de puertas. */
    private class IteradorPuertas extends Iterador {
        /* Regresa el siguiente elemento. */
        @Override public Integer next() {
            return casillas[indice++].puertas;
        }
    }

    /* Clase interna privada para las casillas. */
    private class Casilla {

        /* La casilla vecina norte. */
        Casilla norte;
        /* La casilla vecina sur. */
        Casilla sur;
        /* La casilla vecina este. */
        Casilla este;
        /* La casilla vecina Oeste. */
        Casilla oeste;
        /* El puntaje de la casilla. */
        Integer puntaje;
        /* El valor en 4 bits de las paredes de la casilla. */
        Integer puertas;
        /* El indice de la casilla dada. */
        int indice;

        /* Crea una nueva casilla a partir de un numero.
         * @param casilla el numero de 8 bits que representa
         * la casilla.
         * @param indice el indice de la casilla.
         */
        public Casilla(Integer casilla, int indice) {
            puntaje = (casilla >>> 4) & 0xF;
            puertas = casilla & 0xF;
            this.indice = indice;
        }
    }

    /* Las casillas del laberinto. Sea n el numero de renglones
     * y m el numero de columnas del laberinto.
     * el indice 0 representa la casilla (0,0), el indice 1:(1,0)
     * el indice 0+m-1:(m-1,0), 0+m:(m,0) hasta el indice 0+mxn:(m,n)
     */
    private Casilla[] casillas;
    /* La gráfica del laberinto con los puntajes de cada casilla. */
    private Grafica<Casilla> laberinto;
    /* Los renglones del laberinto. */
    private Integer renglones;
    /* Las columnas del laberinto. */
    private Integer columnas;
    /* El generador de numeros aleatorios. */
    private Random random;
    /* Diccionario con la entrada y salida del laberinto
     * con su indice. */
    private Diccionario<Integer,Casilla> entradas;
    
    /* Define el estado inicial de un laberinto.
     * @param casillas las casillas del laberinto.
     * @param renglones el numero de renglones del laberinto.
     * @param columnas el numero de columnas del laberinto.
     * @param random el random dado.
     */
    public Laberinto(Integer[] casillas, Integer renglones, Integer columnas, Random random) {
        this.renglones = renglones;
        this.columnas = columnas;
        this.casillas = new Casilla[renglones*columnas];
        this.random = random;
        laberinto = new Grafica<>();
        if (casillas != null) {
            for (int i = 0; i < casillas.length; i++) {
                Casilla casilla = new Casilla(casillas[i],i);
                this.casillas[i] = casilla;
                laberinto.agrega(casilla);
            }
            consistente();
            conecta();
            entradas = entradaSalida();
        }
        else {
            generaLaberinto();
        }
    }

    /* Regresa el número de columnas dado */
    public int getColumnas() {
        return columnas;
    }

    /* Regresa el número de renglones dado */
    public int getRenglones() {
        return renglones;
    }

    /* Metodo privado auxiliar para regresar
     * el valor del bit de la puerta norte. */
    private int norte(Casilla casilla) {
        return (casilla.puertas >>> 1) & 1;
    }
    
    /* Metodo privado auxiliar para regresar
     * el valor del bit de la puerta sur. */
    private int sur(Casilla casilla) {
        return (casilla.puertas >>> 3) & 1;
    }
    
    /* Metodo privado auxiliar para regresar
     * el valor del bit de la puerta oeste. */
    private int oeste(Casilla casilla) {
        return (casilla.puertas >>> 2) & 1;
    }
    
    /* Metodo privado auxiliar para regresar
     * el valor del bit de la puerta este. */
    private int este(Casilla casilla) {
        return casilla.puertas & 1;
    }

    /* Metodo publico para obtener el indice
     * de la entrada de un laberinto. */
    public int getEntrada() {
        return entradas.get(0).indice;
    }
    
    /* Metodo publico para obtener el indice
     * de la salida de un laberinto. */
    public int getSalida() {
        return entradas.get(1).indice;
    }

    /* Regresa un diccionario con una entrada y una salida. */
    private Diccionario<Integer,Casilla> entradaSalida() {
        Diccionario<Integer,Casilla> entradas = new Diccionario<>();
        Casilla casilla = casillas[0];
        int i = 0;
        while(casilla.sur != null) {
            if (oeste(casilla) == 0) 
                entradas.agrega(i++,casilla);
            casilla = casilla.sur;
            if (casilla.sur == null)
                if (oeste(casilla) == 0) 
                entradas.agrega(i++,casilla);
        }
        while(casilla.este != null) {
            if (sur(casilla) == 0) 
                entradas.agrega(i++,casilla);
            casilla = casilla.este;
            if (casilla.este == null)
                if (sur(casilla) == 0) 
                entradas.agrega(i++,casilla);
        }
        while(casilla.norte != null) {
            if (este(casilla) == 0) 
                entradas.agrega(i++,casilla);
            casilla = casilla.norte;
            if (casilla.norte == null)
                if (este(casilla) == 0) 
                entradas.agrega(i++,casilla);
        }
        while(casilla.oeste != null) {
            if (norte(casilla) == 0) 
                entradas.agrega(i++,casilla);
            casilla = casilla.oeste;
            if (casilla.oeste == null)
                if (norte(casilla) == 0) 
                entradas.agrega(i++,casilla);
        }
        return entradas;
    }

    /* Metodo privado auxiliar para generar un laberinto
     * consistente con todos los bits prendidos menos 
     * el bit con su vecino.
     */
    private void laberintoCerrado() {
        for (int i = 0; i < casillas.length; i++)
            casillas[i] = new Casilla(random.nextInt() | 0xF,i);
    }

    /** Metodo privado auxiliar para conectar las casillas del 
     * laberinto al crear uno. */
    private void conectaInicio(int i) {
        Casilla casilla = casillas[i];
        if (i > (columnas-1))
            casilla.norte = casillas[i-columnas];
        if (i < ((renglones-1)*columnas))
            casilla.sur = casillas[i+columnas];
        if ((i+1) % columnas != 0)
            casilla.este = casillas[i+1];
        if (i % columnas != 0) 
            casilla.oeste = casillas[i-1];
    }

    /* Metodo privado auxiliar para conectar un laberinto
     * dependiendo si hay puertas abiertas. */
    private void conecta() {
        laberinto.paraCadaVertice((v) -> conecta(v));
    }

    /** Metodo para desconectar dos casillas 
     * del laberinto si hay o no puerta. */
    private void conecta(VerticeGrafica<Casilla> actual) {
        Casilla casilla= actual.get();
        if (casilla.norte != null) {
            if(hayPuerta(casilla,casilla.norte) && 
                    !laberinto.sonVecinos(casilla,casilla.norte)) {
                laberinto.conecta(casilla,casilla.norte,
                        (casilla.puntaje+casilla.norte.puntaje)+1);
            }
        }
        if (casilla.sur != null &&
                    !laberinto.sonVecinos(casilla,casilla.sur)) {
            if(hayPuerta(casilla,casilla.sur)) {
                laberinto.conecta(casilla,casilla.sur,
                        (casilla.puntaje+casilla.sur.puntaje)+1);
            }
        }
        if (casilla.este != null &&
                    !laberinto.sonVecinos(casilla,casilla.este)) {
            if(hayPuerta(casilla,casilla.este)) {
                laberinto.conecta(casilla,casilla.este,
                        (casilla.puntaje+casilla.este.puntaje)+1);
            }
        }
        if (casilla.oeste != null &&
                    !laberinto.sonVecinos(casilla,casilla.oeste)) {
            if(hayPuerta(casilla,casilla.oeste)) {
                laberinto.conecta(casilla,casilla.oeste,
                        (casilla.puntaje+casilla.oeste.puntaje)+1);
            }
        }
    }

    /* Metodo privado auxiliar para saber si hay un puerta
     * entre dos casillas. */
    private boolean hayPuerta(Casilla actual, Casilla vecino) {
        if (actual.norte == vecino) {
            if (norte(actual) == 1)
                return false;
        }
        else if (actual.sur == vecino) {
            if (sur(actual) == 1)
                return false;
        }
        else if (actual.oeste == vecino) {
            if (oeste(actual) == 1)
                return false;
        }
        else if (actual.este == vecino) {
            if (este(actual) == 1)
                return false;
        }
        return true;
    }

    /* Metodo privado auxiliar para hacer consistente 
     * un laberinto. */
    private void consistente() {
        for (int i = 0; i < casillas.length;i++) 
           conectaInicio(i);
    }

    /* Metodo para generar una solución del laberinto. */
    public Diccionario<Integer,Puertas> solucion() {
        Casilla entrada = casillas[getEntrada()]; 
        Casilla salida = casillas[getSalida()];
        Lista<VerticeGrafica<Casilla>> solucion = laberinto.dijkstra(entrada,salida);
        if (solucion.esVacia())
            UtilEntrada.error("laberinto no tiene solucion");
        Diccionario<Integer,Puertas> casillaSolucion = new Diccionario<Integer,Puertas>();
        while(!solucion.esVacia()) {
            Casilla actual = solucion.eliminaPrimero().get();
            if (solucion.esVacia())
                break;
            Casilla vecino = solucion.getPrimero().get();
            casillaSolucion.agrega(actual.indice,direccion(actual,vecino));
        }
        return casillaSolucion;
    }

    /* Metodo privado auxiliar para obtener la direccion de un vecino
     * en Dijkstra. */
    private Puertas direccion(Casilla actual, Casilla vecino) {
        if(actual.norte == vecino)
            return Puertas.NORTE;
        else if(actual.sur == vecino)
            return Puertas.SUR;
        else if(actual.oeste == vecino)
            return Puertas.OESTE;
        else
            return Puertas.ESTE;
    }

    /* Metodo privado auxiliar para generar un laberinto 
     * aleatorio. */
    private void generaLaberinto() {
        laberintoCerrado();
        consistente();
        int origen = random.nextInt(renglones)*columnas;
        int destino = (random.nextInt(renglones)*columnas)+(columnas-1);
        casillas[origen].puertas &= Puertas.OESTE.valor();
        casillas[destino].puertas &= Puertas.ESTE.valor();
        Pila<Casilla> pila = new Pila<>();
        pila.mete(casillas[origen]);
        Conjunto<Casilla> visitados = new Conjunto<>();
        visitados.agrega(casillas[origen]);
        while (!pila.esVacia()) {
            Casilla actual = pila.saca();
            Casilla vecino = vecino(actual, visitados);
            if (vecino != null) {
                pila.mete(actual);
                eliminaPuerta(actual,vecino);
                visitados.agrega(vecino);
                pila.mete(vecino);
            }
        }
    }

    /** Metodo privado auxiliar para modificar una casilla en dfs. */
    private void modificaCasilla(Casilla casilla, Conjunto<Casilla> visitados) {
        if (visitados.contiene(casilla))
            return;
        Casilla vecino = vecino(casilla,visitados);
        if (vecino != null) {
            eliminaPuerta(casilla,vecino);
            visitados.agrega(vecino);
        }
    }

    /* Metodo privado auxiliar que regresa un vecino de una casilla
     * distinto de null. */
    private Casilla vecino(Casilla casilla, Conjunto<Casilla> visitados) {
        int vecino = random.nextInt(4);
        while (sinVisitar((casilla),visitados)) {
            switch(vecino) {
                case 0:
                    if (casilla.norte != null && !visitados.contiene(casilla.norte))
                        return casilla.norte;
                case 1:
                    if (casilla.sur != null && !visitados.contiene(casilla.sur))
                        return casilla.sur;
                case 2:
                    if (casilla.oeste != null && !visitados.contiene(casilla.oeste))
                        return casilla.oeste;
                case 3:
                    if (casilla.este != null && !visitados.contiene(casilla.este))
                        return casilla.este;
                default: vecino = 0;
            }
        }
        return null;
    }

    /** Metodo privado auxiliar para saber si una casilla tiene vecinos sin 
     * visitar. */
    private boolean sinVisitar(Casilla casilla, Conjunto<Casilla> visitados) {
        int c = 0;
        if (casilla.norte == null || visitados.contiene(casilla.norte))
            c++;
        if (casilla.sur == null || visitados.contiene(casilla.sur))
            c++;
        if (casilla.oeste == null || visitados.contiene(casilla.oeste))
            c++;
        if (casilla.este == null || visitados.contiene(casilla.este))
            c++;
        return c < 4;
    }

    /* Metodo privado auxiliar para eliminar la puerta entre dos casillas. */
    private void eliminaPuerta(Casilla actual, Casilla vecino) {
        if (actual.norte == vecino) {
            modificaCasilla(actual,Puertas.NORTE);
        }
        else if (actual.sur == vecino) {
            modificaCasilla(actual,Puertas.SUR);
        }
        else if (actual.oeste == vecino) {
            modificaCasilla(actual,Puertas.OESTE);
        }
        else {
            modificaCasilla(actual,Puertas.ESTE);
        }
    }

    /** Metodo privado auxiliar para modificar una casilla y por consecuente
     * mantener consistente un laberinto. */
    private void modificaCasilla(Casilla casilla, Puertas puerta) {
        switch(puerta) {
            case NORTE:
                casilla.norte.puertas &= Puertas.SUR.valor();
                casilla.puertas &= Puertas.NORTE.valor();
                break;
            case SUR:
                casilla.sur.puertas &= Puertas.NORTE.valor();
                casilla.puertas &= Puertas.SUR.valor();
                break;
            case OESTE:
                casilla.oeste.puertas &= Puertas.ESTE.valor();
                casilla.puertas &= Puertas.OESTE.valor();
                break;
            case ESTE:
                casilla.este.puertas &= Puertas.OESTE.valor();
                casilla.puertas &= Puertas.ESTE.valor();
                break;
        }
    }

    /* Metodo para saber si un laberinto es consistente. */
    public boolean esConsistente() {
        return entradas.getElementos() == 2 && compruebaCasillas();
    }

    /* Metodo privado auxiliar para verificar que las
     * casillas sean consistentes con sus vecinos. */
    private boolean compruebaCasillas() {
        for (Casilla casilla : casillas) {
            if (casilla.norte != null) {
                if (sur(casilla.norte) != norte(casilla))
                    return false;
            }
            if (casilla.sur != null) {
                if (norte(casilla.sur) != sur(casilla))
                    return false;
            }
            if (casilla.este != null) {
                if (oeste(casilla.este) != este(casilla))
                    return false;
            }
            if (casilla.oeste != null) {
                if (este(casilla.oeste) != oeste(casilla))
                    return false;
            }
        }
        return true;
    }

    /**
     * Regresa un iterador de las casillas del arbol por renglones.
     * @return un iterador para iterar el laberinto.
     */
    @Override public Iterator<Integer> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para iterar las puertas del laberinto. 
     * @return un iterador para iterar las puertas del laberinto.
     */
    public Iterator<Integer> iteradorPuertas() {
        return new IteradorPuertas();
    }
}
