import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Francisco Manuel Rivao
 * @author  Alejandro Sanchez Millan
 * @version     1.0
 */
public class Utilidades {

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return int numero
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int numero;
        do {
            System.out.println(mensaje);
            numero = teclado.nextInt();

            if (numero > maximo || numero < minimo) {
                System.out.println("El numero debe ser mayor o igual que " + minimo + " y menor o igual que " + maximo);
            }
        } while (numero > maximo || numero < minimo);
        return numero;
    }

    /**
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return int numero
     */
    public static int leerNumeroSinMensajeError(Scanner teclado, String mensaje, int minimo, int maximo) {
        int numero;
        do {
            System.out.println(mensaje);
            numero = teclado.nextInt();
        } while (numero > maximo || numero < minimo);
        return numero;
    }

    /**
     * @param teclado
     * @param mensaje
     * @return int numero
     */
    public static int leerNumeroSinLimites(Scanner teclado, String mensaje) {
        System.out.println(mensaje);
        return teclado.nextInt();
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return long numero
     */
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo) {
        long numero;
        do {
            System.out.println(mensaje);
            numero = teclado.nextLong();

            if (numero > maximo || numero < minimo) {
                System.out.println("El numero debe ser mayor o igual que " + minimo + " y menor o igual que " + maximo);
            }
        } while (numero > maximo || numero < minimo);
        return numero;
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return double numero
     */
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo) {
        double numero;
        do {
            System.out.println(mensaje);
            numero = teclado.nextDouble();

            if (numero > maximo || numero < minimo) {
                System.out.println("El numero debe ser mayor o igual que " + minimo + " y menor o igual que " + maximo);
            }
        } while (numero > maximo || numero < minimo);
        return numero;
    }

    /**
     * TODO: TODO: Solicita una letra repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return char letra
     */
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo) {
        char letra;
        do {
            System.out.println(mensaje);
            letra = teclado.next().charAt(0);

            if (letra > maximo || letra < minimo) {
                System.out.println("La letra debe estar entre " + minimo + " y " + maximo);
            }
        } while (letra > maximo || letra < minimo);
        return letra;
    }

    /**
     * TODO: Solicita una fecha repetidamente hasta que se introduzca una correcta
     * @param teclado
     * @param mensaje
     * @return Fecha
     */
    public static Fecha leerFecha(Scanner teclado, String mensaje) {
        int dia;
        int mes;
        int anio;

        do {
            System.out.println(mensaje);
            System.out.print("Dia: ");
            dia = teclado.nextInt();
            System.out.print("Mes: ");
            mes = teclado.nextInt();
            System.out.print("Año: ");
            anio = teclado.nextInt();

            if (!Fecha.comprobarFecha(dia, mes, anio)) {
                System.out.println("La fecha no es valida");
            }
        } while (!Fecha.comprobarFecha(dia, mes, anio));

        return new Fecha(dia, mes, anio);
    }


    /**
     * TODO: Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
     * @param teclado
     * @param mensaje
     * @return Fecha
     */
    public static Fecha leerFechaHora(Scanner teclado, String mensaje) {
        int dia;
        int mes;
        int anio;
        int hora;
        int minuto;
        int segundo;

        do {
            System.out.println(mensaje);
            System.out.print("Dia: ");
            dia = teclado.nextInt();
            System.out.print("\nMes: ");
            mes = teclado.nextInt();
            System.out.print("\nAño: ");
            anio = teclado.nextInt();
            System.out.print("\nHora: ");
            hora = teclado.nextInt();
            System.out.print("\nMinuto: ");
            minuto = teclado.nextInt();
            System.out.print("\nSegundo: ");
            segundo = teclado.nextInt();
        } while (!Fecha.comprobarFecha(dia, mes, anio) || !Fecha.comprobarHora(hora, minuto, segundo));

        return new Fecha(dia, mes, anio, hora, minuto, segundo);
    }

    /**
     * TODO: Imprime por pantalla el String pasado por parámetro
     * @param teclado
     * @param s
     * @return String
     */
    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        return teclado.next();
    }

    public static char[] getLetras() {
        char letras[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        return letras;
    }

    /**
     * @param teclado
     * @return [dia, mes, año, segundo, minuto, hora]
     */
    public static int [] pedirFecha(Scanner teclado) {
        int fecha[] = new int[6];
        while (fecha[0] < 1 || fecha[0] > Fecha.DIAS_MES) {
            fecha[0] = Utilidades.leerNumeroSinMensajeError(teclado, "Ingrese día: ", 1, Fecha.DIAS_MES);
        }
        while (fecha[1] < 1 || fecha[1] > Fecha.MESES_ANIO) {
            fecha[1] = Utilidades.leerNumeroSinMensajeError(teclado, "Ingrese mes: ", 1, 12);
        }
        while (fecha[2] < Fecha.PRIMER_ANIO || fecha[2] > Fecha.ULTIMO_ANIO) {
            fecha[2] = Utilidades.leerNumeroSinMensajeError(teclado, "Ingrese año: ", Fecha.PRIMER_ANIO, Fecha.ULTIMO_ANIO);
        }
        while (fecha[3] < 1 || fecha[3] > Fecha.SEGUNDOS_MINUTO) {
            fecha[3] = Utilidades.leerNumeroSinMensajeError(teclado, "Ingrese segundo: ", 1, Fecha.SEGUNDOS_MINUTO);
        }
        while (fecha[4] < 1 || fecha[4] > Fecha.MINUTOS_HORA) {
            fecha[4] = Utilidades.leerNumeroSinMensajeError(teclado, "Ingrese minuto: ", 1, Fecha.MINUTOS_HORA);
        }
        while (fecha[5] < 1 || fecha[5] > Fecha.HORAS_DIA) {
            fecha[5] = Utilidades.leerNumeroSinMensajeError(teclado, "Ingrese hora: ", 1, Fecha.HORAS_DIA);
        }
        return fecha;
    }
}