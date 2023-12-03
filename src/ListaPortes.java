import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Francisco Manuel Rivao
 * @author  Alejandro Sanchez Millan
 * @version     1.0
 */
public class ListaPortes {
    private Porte[] portes;
    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaPortes(int capacidad) {
		this.portes = new Porte[capacidad];
    }
    // TODO: Devuelve el número de portes que hay en la lista
    public int getOcupacion() {
        int pos = 0;
        while(portes[pos] != null) {
            pos++;
        }
        return pos;
    }
    // TODO: ¿Está llena la lista?
    public boolean estaLlena() {
        return getOcupacion() == portes.length;
    }

	//TODO: devuelve un porte dado un indice
    public Porte getPorte(int i) {
        return portes[i];
    }


    /**
     * TODO: Devuelve true si puede insertar el porte
     * @param porte
     * @return false en caso de estar llena la lista o de error
     */
    public boolean insertarPorte(Porte porte) {
        if (!estaLlena()){
            int pos = getOcupacion();
            portes[pos] = porte;
            return true;
        }
        return false;
    }


    /**
     * TODO: Devuelve el objeto Porte que tenga el identificador igual al parámetro id
     * @param id
     * @return el objeto Porte que encontramos o null si no existe
     */
    public Porte buscarPorte(String id) {
        int pos = 0;
        Porte porte = null;
        while(portes[pos] != null && porte == null) {
            if (portes[pos].getID() == id) {
                porte = portes[pos];
            }
        }
        return porte;
    }

    /**
     * TODO: Devuelve un nuevo objeto ListaPortes conteniendo los Portes que vayan de un puerto espacial a otro
     *  en una determinada fecha
     * @param codigoOrigen
     * @param codigoDestino
     * @param fecha
     * @return
     */
    public ListaPortes buscarPortes(String codigoOrigen, String codigoDestino, Fecha fecha) {
        int pos = 0;
        ListaPortes listaPortes = new ListaPortes(portes.length);
        while (portes[pos] != null && pos < portes.length) {
            if (portes[pos].getOrigen().getCodigo() == codigoOrigen && portes[pos].getDestino().getCodigo() == codigoDestino && portes[pos].getSalida() == fecha) {
                listaPortes.insertarPorte(portes[pos]);
            }
            pos++;
        }
        return listaPortes;
    }

    /**
     * TODO: Muestra por pantalla los Portes siguiendo el formato de los ejemplos del enunciado
     */
    public void listarPortes() {
        for (int i = 0; i < portes.length; i++) {
            System.out.println(portes[i].toString());
        }
    }


    /**
     * TODO: Permite seleccionar un Porte existente a partir de su ID, usando el mensaje pasado como argumento para
     *  la solicitud y siguiendo el orden y los textos mostrados en el enunciado, y usando la cadena cancelar para
     *  salir devolviendo null.
     *  La función solicita repetidamente hasta que se introduzca un ID correcto
     * @param teclado
     * @param mensaje
     * @param cancelar
     * @return
     */
    public Porte seleccionarPorte(Scanner teclado, String mensaje, String cancelar) {
        listarPortes();
        String id;
        do {
            id = Utilidades.leerCadena(teclado,mensaje);
        } while (id != cancelar);

        Porte porte = buscarPorte(id);
        return porte;
    }

    /**
     * TODO: Ha de escribir la lista de Portes en la ruta y nombre del fichero pasado como parámetro.
     *  Si existe el fichero, SE SOBREESCRIBE, si no existe se crea.
     * @param fichero
     * @return
     */
    public boolean escribirPortesCsv(String fichero) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(fichero, true));
            for (int i = 0; i < portes.length; i++) {
                pw.printf("%s;%s;%s;%d;%s;%s;%d;%s;%f\n",
                        portes[i].getID(),
                        portes[i].getNave().getMatricula(),
                        portes[i].getOrigen().getCodigo(),
                        portes[i].getMuelleOrigen(),
                        portes[i].getSalida().toString(),
                        portes[i].getDestino().getCodigo(),
                        portes[i].getMuelleDestino(),
                        portes[i].getLlegada().toString(),
                        portes[i].getPrecio()
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
     * TODO: Genera una lista de Portes a partir del fichero CSV, usando los límites especificados como argumentos para
     *  la capacidad de la lista
     * @param fichero
     * @param capacidad
     * @param puertosEspaciales
     * @param naves
     * @return
     */
    public static ListaPortes leerPortesCsv(String fichero, int capacidad, ListaPuertosEspaciales puertosEspaciales, ListaNaves naves) {
        ListaPortes listaPortes = new ListaPortes(capacidad);
        try {

        } catch (Exception e) {
            return null;
        }
        return listaPortes;
    }
}
