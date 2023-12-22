import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version 1.0
 */
public class Envio {

    private String localizador;
    private Porte porte;
    private Cliente cliente;
    private int fila;
    private int columna;
    private double precio;

    /**
     * Constructor of the class
     *
     * @param localizador
     * @param porte
     * @param cliente
     * @param fila
     * @param columna
     * @param precio
     */
    public Envio(String localizador, Porte porte, Cliente cliente, int fila, int columna, double precio) {
        this.localizador = localizador;
        this.porte = porte;
        this.cliente = cliente;
        this.fila = fila;
        this.columna = columna;
        this.precio = precio;
    }

    public String getLocalizador() {
        return localizador;
    }

    public Porte getPorte() {
        return porte;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    // TODO: Ejemplos: "1A" para el hueco con fila 1 y columna 1, "3D" para el hueco con fila 3 y columna 4
    public String getHueco() {
        return ((fila+1) + " A " + (char)(columna+1+'A'));
    }

    public double getPrecio() {
        return precio;
    }

    //TODO: Texto que debe generar: Envío PM1111AAAABBBBC para Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05) en hueco 6C por 13424,56 SSD
    public String toString() {
        return "Envío "+localizador+" para Porte "+porte.getID()+" de "
                +porte.getOrigen().getIniciales()+" "+porte.getOrigen().getCodigo()+" ("+porte.getSalida().toString()+") a "+porte.getDestino().getIniciales()+" "+porte.getDestino().getCodigo()
                +" ("+porte.getLlegada().toString()+") en hueco "+getHueco()+" por "+precio+" SSD ";

    }

    // TODO: Cancela este envío, eliminándolo de la lista de envíos del porte y del cliente correspondiente
    public boolean cancelar() {
        return porte.desocuparHueco(localizador);
    }

    /**
     * TODO: Método para imprimir la información de este envío en un fichero que respecta el formato descrito en el
     *  enunciado
     *
     * @param fichero
     * @return Devuelve la información con el siguiente formato como ejemplo ->
     * -----------------------------------------------------
     * --------- Factura del envío PM1111AAAABBBBC ---------
     * -----------------------------------------------------
     * Porte: PM0066
     * Origen: Gaia Galactic Terminal (GGT) M5
     * Destino: Cidonia (CID) M1
     * Salida: 01/01/2023 08:15:00
     * Llegada: 01/01/2024 11:00:05
     * Cliente: Zapp Brannigan, zapp.brannigan@dop.gov
     * Hueco: 6C
     * Precio: 13424,56 SSD
     */
    public boolean generarFactura(String fichero) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileWriter("ficheros/"+fichero,true));
            pw.printf("-----------------------------------------------------\n");
            pw.printf("--------- Factura del envío " + localizador + " ---------\n");
            pw.printf("-----------------------------------------------------\n");
            pw.printf("Porte: %s\n", porte.getID());
            pw.printf("Origen: %s\n", porte.getOrigen().toStringSimple());
            pw.printf("Destino: %s\n", porte.getDestino().toStringSimple());
            pw.printf("Salida: %s\n", porte.getSalida().toString());
            pw.printf("Llegada: %s\n", porte.getLlegada().toString());
            pw.printf("Cliente: %s\n", cliente.toString());
            pw.printf("Hueco: %s\n", fila + "" + (char)((columna-1)+'A'));
            pw.printf("Precio: %s\n", precio + " SSD");
            pw.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     * TODO: Genera un localizador de envío. Este consistirá en una cadena de 15 caracteres, de los cuales los seis
     *   primeros será el ID del porte asociado y los 9 siguientes serán letras mayúsculas aleatorias. Ejemplo: PM0123ABCD
     *   NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     *
     * @param rand
     * @param idPorte
     * @return
     */
    public static String generarLocalizador(Random rand, String idPorte) {
        StringBuilder localizador = new StringBuilder(idPorte);
        for (int i = 0; i < 9; i++) {
            int numLetra = rand.nextInt(26);
            localizador.append((char)(numLetra+'A'));
        }
        return localizador.toString();
    }


    /**
     * TODO: Método para crear un nuevo envío para un porte y cliente específico, pidiendo por teclado los datos
     *  necesarios al usuario en el orden y con los textos indicados en los ejemplos de ejecución del enunciado
     *  La función solicita repetidamente los parámetros hasta que sean correctos
     *
     * @param teclado
     * @param rand
     * @param porte
     * @param cliente
     * @return Envio para el porte y cliente especificados
     */
    public static Envio altaEnvio(Scanner teclado, Random rand, Porte porte, Cliente cliente) {
        Envio envio = null;
        int fila, columna;
        do {
            fila = Utilidades.leerNumero(teclado, "Fila del hueco : ", 1, porte.getNave().getFilas());
            columna = Utilidades.leerNumero(teclado, "Columna del hueco: ", 1, porte.getNave().getColumnas());
        } while (porte.huecoOcupado(fila,columna));

        double precio = Utilidades.leerNumero(teclado, "Precio del envío: ",1, Utilidades.maxPrecioEnvio);
        String localizador = Envio.generarLocalizador(rand, porte.getID());
        envio = new Envio(localizador, porte, cliente,fila, columna, precio);
        if (porte.ocuparHueco(envio)){
            System.out.println("Envio " + localizador + " creado correctamente");
        } else System.out.println("Hubo un error al crear el envio");

        return envio;
    }
}