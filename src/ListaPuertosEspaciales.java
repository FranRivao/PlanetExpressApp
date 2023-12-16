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
     * @param capacidad
     */
    public ListaPuertosEspaciales(int capacidad) {
        this.lista = new PuertoEspacial[capacidad];
    }
    // TODO: Devuelve el número de puertos espaciales que hay en la lista
    public int getOcupacion() {
        int pos = 0;
        while(lista[pos] != null && pos < lista.length) {
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
     * @param puertoEspacial
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
     * @param codigo
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
     * @param teclado
     * @param mensaje
     * @return
     */
    public PuertoEspacial seleccionarPuertoEspacial(Scanner teclado, String mensaje) {
        System.out.println(mensaje);
        return lista[teclado.nextInt()];
    }

    /**
     * TODO: Genera un fichero CSV con la lista de puertos espaciales, SOBREESCRIBIENDOLO
     * @param nombre
     * @return
     */
    public boolean escribirPuertosEspacialesCsv(String nombre) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(nombre,true));
            for (int i = 0; i < lista.length; i++) {
                pw.println(lista[i].toString());
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
     * TODO: Genera una lista de PuertosEspaciales a partir del fichero CSV, usando el argumento como capacidad máxima
     *  de la lista
     * @param fichero
     * @param capacidad
     * @return
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