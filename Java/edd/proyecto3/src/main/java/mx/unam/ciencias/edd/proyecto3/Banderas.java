package mx.unam.ciencias.edd.proyecto3;

/* Clase para obtener las banderas del programa */
public class Banderas {

    /* Los argumentos de la entrada */
    private String[] entrada;
    /* Bandera para generar un laberinto */
    private boolean genera;
    /* Bandera para generar un numero aleatorio */
    private boolean random;
    /* La semilla para el numero aleatorio */
    private long semilla; 
    /* Numero de columnas */
    private int columnas;
    /* Numero de renglones */
    private int renglones;

    /* Define el estado inicial de las banderas 
     * @param entrada la entrada el programa
     */
    public Banderas(String[] entrada) {
        this.entrada = entrada;
    }

    /* Regresa el número de columnas dado */
    public int getColumnas() {
        return columnas;
    }

    /* Regresa el número de renglones dado */
    public int getRenglones() {
        return renglones;
    }

    /* Regresa la semilla */
    public long getSemilla() {
        return semilla;
    }
    
    /**
     * Metodo para leer de la linea de comandos
     */
    public void leeArgumentos() throws Exception {
        for (int i = 0; i < entrada.length; i++) {
            switch(entrada[i]) {
                case "-g":
                    if(genera)
                        UtilEntrada.error("bandera -g repetida");
                    genera = true;
                    break;
                case "-s":
                    if(random)
                        UtilEntrada.error("bandera -s repetida");
                    random = true;
                    semilla = revisaSiguiente(i);
                    break;
                case "-w":
                    if(columnas > 0)
                        UtilEntrada.error("bandera -w repetida");
                    columnas = revisaSiguiente(i);
                    break;
                case "-h":
                    if(renglones > 0)
                        UtilEntrada.error("bandera -h repetida");
                    renglones = revisaSiguiente(i);
                    break;
                default: break;
            }
        }
        revisaBanderas();
    }

    /* Metodo privado auxiliar para revisar
     * el valor dado */
    private int revisaValor(String valor) throws Exception {
        int valorRevisado = 0;
        try {
            valorRevisado = Integer.parseInt(valor);
        } catch(NumberFormatException nfe) {
            throw nfe;
        }
        return valorRevisado;
    }

    /* Metodo privado auxiliar para revisar
     * que el siguiente elemento sea un entero */
    private int revisaSiguiente(int i) throws Exception {
        return i + 1 < entrada.length ? revisaValor(entrada[i+1]) : -1;
    }

    /* Metodo privado auxiliar para revisar
     * que la entrada tenga las banderas y 
     * argumentos requeridos.
     */
    private void revisaBanderas() {
        if(!genera)
            throw new IllegalArgumentException("ioe");
        if (semilla < 0)
            UtilEntrada.error("semilla menor a 0");
        if (columnas == -1 && renglones == -1)
            throw new IllegalArgumentException("ioe");
        if (columnas < 2 || renglones < 2
                || columnas > 255 || renglones > 255)
            UtilEntrada.error("numero de columnas o entradas no estan" +
                    " en el rango 2-255");
        if(!random)
            semilla = System.currentTimeMillis();
    }
}
