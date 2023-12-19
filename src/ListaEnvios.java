import java.io.*;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

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
            int pos = getOcupacion();
            envios[pos] = envio;
            res = true;
        }
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
            if (envios[pos].getLocalizador().equals(localizador)) {
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
            if (envios[pos].getPorte().getID().equals(idPorte) && envios[pos].getFila() == fila && envios[pos].getColumna() == columna) {
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
        while (pos < envios.length && !envios[pos].getLocalizador().equals(localizador)) {
            pos++;
        }
        for (int i = pos; i < envios.length - 1; i++) {
            if (i+1 != envios.length) {
                envios[i] = envios[i+1];
            } else {
                envios[i] = null;
            }
        }
        return res;
    }

    /**
     * TODO: Muestra por pantalla los Envios de la lista, con el formato que aparece
     * en el enunciado
     */
    public void listarEnvios() {
        for (int i = 0; i < envios.length; i++) {
            if (envios[i] != null)
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
        String localizador;
        do {
            localizador = Utilidades.leerCadena(teclado, "Seleccione un envío: ");

            if (buscarEnvio(localizador) == null)
                System.out.println("Localizador incorrecto");
        } while(buscarEnvio(localizador) == null);
        return buscarEnvio(localizador);
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
            pw = new PrintWriter(new FileWriter(fichero,false));
            for (int i = 0; i < getOcupacion(); i++) {
                System.out.println(envios[i].getLocalizador());
                System.out.println(envios[i].getPorte().getID());
                System.out.println(envios[i].getCliente().getEmail());
                System.out.println(envios[i].getColumna());
                System.out.println(envios[i].getFila());
                System.out.println(envios[i].getPrecio());
                pw.printf("%s;%s;%s;%s;%s;%s\n",
                    envios[i].getLocalizador(),
                    envios[i].getPorte().getID(),
                    envios[i].getCliente().getEmail(),
                    envios[i].getFila(),
                    envios[i].getColumna(),
                    envios[i].getPrecio()
                );
            }
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (pw != null){
                pw.close();
            }
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

        String localizador, idPorte, email, filas, columnas, precio;
        try (Scanner sc = new Scanner(new FileReader(ficheroEnvios))) {
            int pos, puntoComas, lineas = 0;

            while (sc.hasNext()) {
                lineas++;
            }
            ListaEnvios listaEnvios = new ListaEnvios(lineas);

            while (sc.hasNext()) {
                String cadena = sc.nextLine();
                localizador = idPorte = email = filas = columnas = precio = "";
                pos = puntoComas = 0;

                while (pos < cadena.length()) {
                    if (cadena.charAt(pos) != ';') {
                        switch (puntoComas) {
                            case 0:
                                localizador += cadena.charAt(pos);
                                break;
                            case 1:
                                idPorte += cadena.charAt(pos);
                                break;
                            case 2:
                                email += cadena.charAt(pos);
                                break;
                            case 3:
                                filas += cadena.charAt(pos);
                                break;
                            case 4:
                                columnas += cadena.charAt(pos);
                                break;
                            case 5:
                                precio += cadena.charAt(pos);
                                break;
                        }
                    } else puntoComas++;
                    pos++;
                }

                listaEnvios.insertarEnvio(new Envio(localizador, portes.buscarPorte(idPorte), clientes.buscarClienteEmail(email), Integer.getInteger(filas), Integer.getInteger(columnas), Integer.getInteger(precio)));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
