package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;

/* Clase concreta para manejar una entrada con un Archivo */
public class EntradaEstandar extends Entrada {

    /* Procesa el laberinto recibido y revisa el formato */
    @Override public void procesaEntrada() throws Exception {
        byte[] laberintoBytes = System.in.readAllBytes();
        Integer[] formato = new Integer[4];
        for (int i = 0; i < 4; i++)
            formato[i] = Byte.toUnsignedInt(laberintoBytes[i]);
        VerificaLaberinto.verificaInicioFormato(formato);
        Integer renglones = Byte.toUnsignedInt(laberintoBytes[4]);
        Integer columnas = Byte.toUnsignedInt(laberintoBytes[5]);
        Integer[] casillas = UtilEntrada.casillas(laberintoBytes);
        laberinto = new Laberinto(casillas,renglones,columnas,null);
    }
}
