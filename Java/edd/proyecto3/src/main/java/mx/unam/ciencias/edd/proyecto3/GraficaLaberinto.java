package mx.unam.ciencias.edd.proyecto3;

import java.util.Iterator;
import mx.unam.ciencias.edd.Diccionario;

/* Clase para graficar los laberintos con su
 * solución. */
public class GraficaLaberinto {

    /* El inicio de un cada salida svg */
    private String inicio = "<?xml version='1.0' encoding='UTF-8' ?>\n";
    /* La salida a escribir. */
    StringBuffer salida = new StringBuffer(inicio);
    /* El final de cada salida svg */
    private String ultimo = "  </g>\n</svg>";
    /* Las dimensiones de cada svg */
    private String dimensiones = "<svg width='%d' height='%d'>\n  <g>\n";
    /* Las lineas para las puertas. */
    private String linea = "\t<line x1='%d' y1='%d' x2='%d' y2='%d' stroke-width='2' stroke='black'/>\n";
    /* Las lineas de la solucion. */
    private String explorador = "\t<line x1='%d' y1='%d' x2='%d' y2='%d' stroke-width='5' stroke='green'/>\n";
    /* El circulo para representar la entrada y la salida. */
    protected String circulo = "\t<circle cx='%d' cy='%d' r='12' stroke='black' stroke-width='2' fill='%s' />\n";
    /* El laberinto a graficar. */
    private Laberinto laberinto;

    /* Define el estado inicial de un graficador
     * de laberintos. */
    public GraficaLaberinto(Laberinto laberinto) {
        this.laberinto = laberinto;
    }

    /* Metodo para graficar un laberinto. */
    public void grafica() {
        dimensiones();
        int xCentro = 25;
        int yCentro = 25;
        int xSalida = 0;
        int ySalida = 0;
        int c = 0;
        int entrada = laberinto.getEntrada();
        int puertaSalida = laberinto.getSalida();
        Diccionario<Integer,Puertas> solucion = laberinto.solucion();
        Iterator<Integer> puertas = laberinto.iteradorPuertas();
        while (puertas.hasNext()) {
            int casilla = puertas.next();
            puerta(casilla,xCentro,yCentro,c);
            if (solucion.contiene(c)) {
                    trayectoria(xCentro,yCentro,solucion.get(c));
                if (entrada == c)
                    graficaCirculo("blue",xCentro,yCentro);
            }
            if (puertaSalida == c) {
                xSalida = xCentro;
                ySalida = yCentro;
            }
            if ((c+1) % laberinto.getColumnas() == 0) {
                yCentro += 30;
                xCentro = 25;
            }
            else {
                xCentro += 30;
            }
            c++;
        }
        graficaCirculo("red",xSalida,ySalida);
        System.out.println(salida.append(ultimo));
    }

    /* Metodo privado auxiliar para calcular
     * las dimensiones del svg. */
    private void dimensiones() {
        int ancho = (30*laberinto.getColumnas())+20;
        int largo = (30*laberinto.getRenglones())+20;
        salida.append(String.format(dimensiones,ancho,largo));
    }

    /* Metodo privado auxiliar para graficar al explorador. */
    private void trayectoria(int xCentro, int yCentro, Puertas direccion) {
        if (direccion == Puertas.NORTE)
            salida.append(String.format(explorador,xCentro,yCentro+2,xCentro,yCentro-32));
        else if (direccion == Puertas.SUR)
            salida.append(String.format(explorador,xCentro,yCentro-2,xCentro,yCentro+32));
        else if (direccion == Puertas.OESTE)
            salida.append(String.format(explorador,xCentro-32,yCentro,xCentro+2,yCentro));
        else
            salida.append(String.format(explorador,xCentro-2,yCentro,xCentro+32,yCentro));
    }

    /* Metodo para graficar las paredes de la casilla dada. */
    private void puerta(int puerta, int xCentro, int yCentro,int indice) {
        if (((puerta >>> 3) & 1) == 1)
            sur(xCentro,yCentro);
        if (((puerta >>> 2) & 1) == 1 && indice % laberinto.getColumnas() == 0)
            oeste(xCentro,yCentro);
        if (((puerta >>> 1) & 1) == 1 && indice <= (laberinto.getColumnas()-1))
            norte(xCentro,yCentro);
        if ((puerta & 1) == 1)
            este(xCentro,yCentro);
    }

    /* Metodo privado para graficar la entrada y la salida. */
    private void graficaCirculo(String color, int xCentro, int yCentro) {
        salida.append(String.format(circulo,xCentro,yCentro,color));
    }


    /* Metodo privado para graficar una pared sur. */
    private void sur(int xCentro, int yCentro) {
        salida.append(String.format(linea, xCentro-15,yCentro+15,xCentro+15,yCentro+15));
    }

    /* Metodo privado para graficar una pared sur. */
    private void este(int xCentro, int yCentro) {
        salida.append(String.format(linea, xCentro+15,yCentro-15,xCentro+15,yCentro+15));
    }

    /* Metodo privado para graficar una pared sur. */
    private void norte(int xCentro, int yCentro) {
        salida.append(String.format(linea, xCentro-15,yCentro-15,xCentro+15,yCentro-15));
    }

    /* Metodo privado para graficar una pared sur. */
    private void oeste(int xCentro, int yCentro) {
        salida.append(String.format(linea, xCentro-15,yCentro-15,xCentro-15,yCentro+15));
    }
}
