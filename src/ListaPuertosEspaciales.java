import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Francisco Manuel Rivao
 * @author Alejandro Sanchez Millan
 * @version     1.0
 */
public class ListaPuertosEspaciales {
    private PuertoEspacial[] lista;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad cantidad maxima de puertos espaciales
     */
    public ListaPuertosEspaciales(int capacidad) {
        this.lista = new PuertoEspacial[capacidad];
    }
    // TODO: Devuelve el número de puertos espaciales que hay en la lista
    public int getOcupacion() {
        int pos = 0;
        while(lista[pos] != null) {
            pos++;
        }
        return pos;
    }
    // TODO: ¿Está llena la lista?
    public boolean estaLlena() {
        return getOcupacion() == lista.length;
    }
    // TODO: Devuelve un puerto espacial dado un indice
    public PuertoEspacial getPuertoEspacial(int i) {
        return lista[i];
    }

    /**
     * TODO: insertamos un Puerto espacial nuevo en la lista
     * @param puertoEspacial objeto puerto espacial a insertar en la lista
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarPuertoEspacial(PuertoEspacial puertoEspacial) {
        if (!estaLlena()){
            int pos = getOcupacion();
            lista[pos] = puertoEspacial;
            return true;
        }
        return false;
    }

    /**
     * TODO: Buscamos un Puerto espacial a partir del codigo pasado como parámetro
     * @param codigo codigo del puerto buscado
     * @return Puerto espacial que encontramos o null si no existe
     */
    public PuertoEspacial buscarPuertoEspacial(String codigo) {
        int pos = 0;
        PuertoEspacial puerto = null;
        while(lista[pos] != null && puerto == null) {
            if (lista[pos].getCodigo().equalsIgnoreCase(codigo)) {
                puerto = lista[pos];
            }
            pos++;
        }
        return puerto;
    }

    /**
     * TODO: Permite seleccionar un puerto espacial existente a partir de su código, usando el mensaje pasado como
     *  argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente el código hasta que se introduzca uno correcto
     * @param teclado scanner
     * @param mensaje mensaje para pedir el puerto espacial
     * @return objeto puerto espacial seleccionado
     */
    public PuertoEspacial seleccionarPuertoEspacial(Scanner teclado, String mensaje) {
        String codigo;
        boolean cancelar = false;
        PuertoEspacial puerto = null;

        while (puerto == null && !cancelar) {
            codigo = Utilidades.leerCadena(teclado,mensaje);
            puerto = buscarPuertoEspacial(codigo);

            if (codigo.equals("cancelar")) {
                cancelar = true;
            } else if (puerto == null) {
                System.out.println("\tCódigo de puerto no encontrado.");
            }
        }

        return  puerto;
    }

    /**
     * TODO: Genera un fichero CSV con la lista de puertos espaciales, SOBREESCRIBIENDOLO
     * @param nombre nombre del fichero
     * @return escritura correcta o no
     */
    public boolean escribirPuertosEspacialesCsv(String nombre) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombre, false))) {
            for (int i = 0; i < getOcupacion(); i++) {
                pw.printf("%s;%s;%s;%s;%s;%s\n", lista[i].getNombre(), lista[i].getCodigo(), lista[i].getRadio(), lista[i].getAzimut(), lista[i].getPolar(), lista[i].getMuelles());
            }
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     * TODO: Genera una lista de PuertosEspaciales a partir del fichero CSV, usando el argumento como capacidad máxima
     *  de la lista
     * @param fichero nombre del fichero
     * @param capacidad cantidad maxima de puertos
     * @return lista puertos espaciales
     */
    public static ListaPuertosEspaciales leerPuertosEspacialesCsv(String fichero, int capacidad) {
        ListaPuertosEspaciales listaPuertosEspaciales = new ListaPuertosEspaciales(capacidad);
        Scanner sc = null;
        String nombre, codigo, radio, azimutm, polar, numMuelles;

        try {
            sc = new Scanner(new FileReader(fichero));
            int pos, puntoComas;

            while (sc.hasNext()) {
                String cadena = sc.nextLine();
                nombre = ""; codigo = ""; radio = ""; azimutm = ""; polar = ""; numMuelles = ""; pos = 0; puntoComas = 0;

                while (pos < cadena.length()) {
                    if (cadena.charAt(pos) != ';') {
                        switch (puntoComas) {
                            case 0:
                                nombre += cadena.charAt(pos);
                                break;
                            case 1:
                                codigo += cadena.charAt(pos);
                                break;
                            case 2:
                                radio += cadena.charAt(pos);
                                break;
                            case 3:
                                azimutm += cadena.charAt(pos);
                                break;
                            case 4:
                                polar += cadena.charAt(pos);
                                break;
                            case 5:
                                numMuelles += cadena.charAt(pos);
                                break;
                        }
                    } else puntoComas++;
                    pos++;
                }

                listaPuertosEspaciales.insertarPuertoEspacial(
                        new PuertoEspacial(nombre, codigo, Double.parseDouble(radio), Double.parseDouble(azimutm), Double.parseDouble(polar), Integer.parseInt(numMuelles))
                );
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

        return listaPuertosEspaciales;
    }
}