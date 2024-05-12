package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /** Metodo privado auxiliar que regresa un entero
     * en big endian */
    private static int be(byte a, byte b, byte c, byte d) {
        return ((a & 0xFF) << 24 | (b & 0xFF) << 16 |
                (c & 0xFF) << 8 | (d & 0xFF));
    }

    /** Metodo privado auxiliar que regresa un entero
     * en little endian */
    private static int le(byte a, byte b, byte c, byte d) {
        return ((d & 0xFF) << 24 | (c & 0xFF) << 16 |
                (b & 0xFF) << 8 | (a & 0xFF));
    }

    /* Metodo privado auxiliar para deplazar un byte 
     * n veces */
    private static int pos(byte a, int n) {
        return (a & 0xFF) << (8*n);
    }

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        int r = 0;
        int i = 0;
        int l = llave.length;
        while(l >= 4) {
            r ^= be(llave[i],llave[i+1],llave[i+2],llave[i+3]);
            i += 4;
            l -= 4;
        }
        int t = 0;
        int s = 3;
        i = llave.length-l;
        switch(l) {
            case 3:
                t |= pos(llave[i++],s--);
            case 2:
                t |= pos(llave[i++],s--);
            case 1:
                t |= pos(llave[i],s);
        }
        return r^t;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        int a = 0x9E3779B9;
        int b = 0x9E3779B9;
        int c = 0xFFFFFFFF;
        int r = 0;
        int i = 0;
        int l = llave.length;
        int[] aux = new int[3];
        while(l >= 12) {
            a += le(llave[i],llave[i+1],llave[i+2],llave[i+3]);
            b += le(llave[i+4],llave[i+5],llave[i+6],llave[i+7]);
            c += le(llave[i+8],llave[i+9],llave[i+10],llave[i+11]);
            mezcla(a,b,c,aux);
            a = aux[0];
            b = aux[1];
            c = aux[2];
            i += 12;
            l -= 12;
        }
        i = llave.length-1;
        c += llave.length;
        switch(l) {
            case 11: c += pos(llave[i--],3); 
            case 10: c += pos(llave[i--],2);
            case 9:  c += pos(llave[i--],1);
            case 8: b += pos(llave[i--],3);
            case 7: b += pos(llave[i--],2);
            case 6: b += pos(llave[i--],1);
            case 5: b += pos(llave[i--],0);
            case 4: a += pos(llave[i--],3);
            case 3: a += pos(llave[i--],2);
            case 2: a += pos(llave[i--],1);
            case 1: a += pos(llave[i--],0);
        }
        mezcla(a,b,c,aux);
        return aux[2];
    }

    /* Metodo privado auxiliar para mezclar 3 bytes */
    private static void mezcla(int a, int b, int c, int[] arreglo) {
        // parte 1;
        a -= b + c;
        a ^= (c >>> 13);
        b -= c + a;
        b ^= (a << 8);
        c -= a + b;
        c ^= (b >>> 13);
        // parte 2;
        a -= b + c;
        a ^= (c >>> 12);
        b -= c + a;
        b ^= (a << 16);
        c -= a + b;
        c ^= (b >>> 5);
        // parte 3;
        a -= b + c;
        a ^= (c >>> 3);
        b -= c + a;
        b ^= (a << 10);
        c -= a + b;
        c ^= (b >>> 15);
        arreglo[0] = a;
        arreglo[1] = b;
        arreglo[2] = c;
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        int h = 5381;
        for (int i = 0; i < llave.length; i++) 
            h += (h << 5) + (llave[i] & 0xFF);
        return h;
    }
}
