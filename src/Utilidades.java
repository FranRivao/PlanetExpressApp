import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Francisco Manuel Rivao
 * @author  Alejandro Sanchez Millan
 * @version     1.0
 */
public class Utilidades {
    public static final double maxPrecioEnvio = 10000000;
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
            System.out.print(mensaje);
            numero = teclado.nextInt();
        } while (numero > maximo || numero < minimo);
        return numero;
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
            System.out.print(mensaje);
            numero = teclado.nextLong();
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
            System.out.print(mensaje);
            numero = teclado.nextDouble();
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
            System.out.print(mensaje);
            letra = teclado.next().charAt(0);
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
            System.out.print(mensaje);
            System.out.print("Ingrese día: ");
            dia = teclado.nextInt();
            System.out.print("Ingrese mes: ");
            mes = teclado.nextInt();
            System.out.print("Ingrese año: ");
            anio = teclado.nextInt();

            if (!Fecha.comprobarFecha(dia, mes, anio)) {
                System.out.println("\tFecha introducida incorrecta");
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
            dia = leerNumero(teclado, "Ingrese día: ", 1, Fecha.DIAS_MES);
            mes = leerNumero(teclado, "Ingrese mes: ", 1, 12);
            anio = leerNumero(teclado, "Ingrese año: ", Fecha.PRIMER_ANIO, Fecha.ULTIMO_ANIO);
            hora = leerNumero(teclado, "Ingrese segundo: ", 1, Fecha.SEGUNDOS_MINUTO);
            minuto = leerNumero(teclado, "Ingrese minuto: ", 1, Fecha.MINUTOS_HORA);
            segundo = leerNumero(teclado, "Ingrese hora: ", 1, Fecha.HORAS_DIA);
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
}