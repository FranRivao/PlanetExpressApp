import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version 1.0
 */
public class ListaEnvios {
    private Envio[] envios;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaEnvios(int capacidad) {
        envios = new Envio[capacidad];


    }

    // TODO: Devuelve el número de envíos que hay en la lista
    public int getOcupacion() {
        int pos = 0;
        while (envios[pos] != null) {
            pos++;
        }
        return pos;

    }

    // TODO: ¿Está llena la lista de envíos?
    public boolean estaLlena() {
        return envios.length == getOcupacion();


    }

    //TODO: Devuelve el envio dado un indice
    public Envio getEnvio(int i) {

        return envios[i];
    }

    /**
     * TODO: insertamos un nuevo envío en la lista
     *
     * @param envio
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarEnvio(Envio envio) {
        boolean res = false;
        if (!estaLlena()) {
            int pos = 0;
            while (envios[pos] != null) {
                pos++;
            }
            envios[pos] = envio;
            res = true;
        } else res = false;
        return res;
    }

    /**
     * TODO: Buscamos el envio a partir del localizador pasado como parámetro
     *
     * @param localizador
     * @return el envio que encontramos o null si no existe
     */
    public Envio buscarEnvio(String localizador) {
        int pos = 0;
        Envio envio = null;
        while (envios[pos] != null && envio == null) {
            if (envios[pos].getLocalizador() == localizador) {
                envio = envios[pos];
            }
            pos++;
        }
        return envio;


    }

    /**
     * TODO: Buscamos el envio a partir del idPorte, fila y columna pasados como parámetros
     *
     * @param idPorte
     * @param fila
     * @param columna
     * @return el envio que encontramos o null si no existe
     */

    public Envio buscarEnvio(String idPorte, int fila, int columna) {
        int pos = 0;
        Envio envio = null;
        while (envios[pos] != null && envio == null) {
            if (envios[pos].getPorte().getID() == idPorte && envios[pos].getFila() == fila && envios[pos].getColumna() == columna) {
                envio = envios[pos];
            }
            pos++;
        }
        return envio;
    }

    /**
     * TODO: Eliminamos un envio a través del localizador pasado por parámetro
     *
     * @param localizador
     * @return True si se ha borrado correctamente, false en cualquier otro caso
     */
    public boolean eliminarEnvio(String localizador) {
        boolean res = true;
        int pos = 0;
        while (pos < envios.length && envios[pos].getLocalizador() != localizador) {
            pos++;
        }
        if (pos == envios.length) {
            res = false;
        } else {
            for (int i = pos; i < envios.length - 1; i++) {
                envios[i] = envios[i + 1];
            }
            envios[envios.length - 1] = null;
        }
        return res;
    }

    /**
     * TODO: Muestra por pantalla los Envios de la lista, con el formato que aparece
     * en el enunciado
     */


    public void listarEnvios() {
        for (int i = 0; i < envios.length; i++) {
            System.out.println(envios[i].toString());
        }
    }

    /**
     * TODO: Permite seleccionar un Envio existente a partir de su localizador, usando el mensaje pasado como argumento
     *  para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente hasta que se introduzca un localizador correcto
     *
     * @param teclado
     * @param mensaje
     * @return
     */
    public Envio seleccionarEnvio(Scanner teclado, String mensaje) {
        ;
        System.out.println(mensaje);
        Envio envio = envios[teclado.nextInt()];
        return envio;
    }


    /**
     * TODO: Añade los Envios al final de un fichero CSV, SIN SOBREESCRIBIR la información
     *
     * @param fichero
     * @return
     */
    public boolean aniadirEnviosCsv(String fichero) {
        PrintWriter pw = null;
        try {

            return true;
        } catch (Exception e) {
            return false;
        } finally {

        }
    }

    /**
     * TODO: Lee los Envios del fichero CSV y los añade a las listas de sus respectivos Portes y Clientes
     *
     * @param ficheroEnvios
     * @param portes
     * @param clientes
     */
    public static void leerEnviosCsv(String ficheroEnvios, ListaPortes portes, ListaClientes clientes) {
        Scanner sc = null;
        try {

        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el fichero de envíos");
        } finally {

        }
    }
}
