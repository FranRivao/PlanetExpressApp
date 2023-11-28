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

            if (!validaFecha(dia, mes) || anio <= 0) {
                System.out.println("La fecha no es valida");
            }
        } while (!validaFecha(dia, mes) || anio <= 0);

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
        } while (!validaFecha(dia, mes) || anio <= 0);

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

    /**
     * Devuelve si la fecha es válida
     * @param dia
     * @param mes
     * @return boolean
     */
    private static boolean validaFecha(int dia, int mes) {
        boolean valido = false;
        if (dia > 0 && mes >= 1 && mes <= 12) {
            switch (mes){
                case 1, 3, 5, 7, 8, 10, 11:
                    valido = dia <= 31;
                    break;
                case 2:
                    valido = dia <= 28;
                    break;
                default:
                    valido = dia <= 30;
                    break;
            }
        }
        return valido;
    }
}