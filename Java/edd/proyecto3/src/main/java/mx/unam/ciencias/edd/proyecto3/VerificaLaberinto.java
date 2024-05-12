package mx.unam.ciencias.edd.proyecto3;

/* Clase para verificar que un archivo .mze 
 * tenga el formato requerido y que sea consistente. */
public class VerificaLaberinto {

    /* El inicio de cada laberinto. */
    private static Integer[] maze = {0x4d, 0x41, 0x5a, 0x45};
    /* Constructor privado para evitar instanciacion. */
    private VerificaLaberinto() {}
    
    /* Metodo para verificar que el inicio del archivo .mze
     * contenga los primeros 4 bytes asignados. */
    public static void verificaInicioFormato(Integer[] formato) {
        for (int i = 0; i < 4; i++) {
            if(formato[i] != maze[i])
                UtilEntrada.error("el archivo no inicia con el formato requerido");
        }
    }

    /*  Metodo para verificar que el laberinto sea valido .*/
    public static void verifica(Laberinto laberinto) {
        Integer renglones = laberinto.getRenglones();
        Integer columnas = laberinto.getColumnas();
        if (renglones < 2 || columnas < 2 || renglones > 255 ||
                columnas > 255)
            UtilEntrada.error("laberinto con dimensiones incorrectas.");
        if(!laberinto.esConsistente())
            UtilEntrada.error("laberinto es inconsistente entre casillas.");
    }
}
