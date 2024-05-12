package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;

/* Clase abstracta para graficar las estructuras */
public abstract class Graficador {

    /* El inicio de un cada salida xml */
    protected String xml = "<?xml version='1.0' encoding='UTF-8' ?>\n";
    /* El final de cada salida xml */
    protected String ultimo = "  </g>\n</svg>";
    /* Las dimensiones de cada svg */
    protected String dimensiones = "<svg width='%f' height='%d'>\n  <g>\n";
    /* El fondo blanco del svg */
    protected String fondo = "\t<rect x='0' y='0' width='%f' height='%d' style='fill: white;'/>\n";

    /* La lista con los elementos */
    protected Lista<Integer> elementos;

    /* Metodo para graficar la estructura */
    public void grafica() {}

    /* Metodo para calcular las dimensiones del svg */
    protected void dimensiones() {}
}


