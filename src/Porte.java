import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author  Francisco Manuel Rivao
 * @author  Alejandro Sanchez Millan
 * @version     1.0
 */
public class Porte {
    private boolean[][] huecos;
    private String id;
    private Nave nave;
    private PuertoEspacial origen;
    private int muelleOrigen;
    private Fecha salida;
    private PuertoEspacial destino;
    private int muelleDestino;
    private Fecha llegada;
    private double precio;
    private ListaEnvios listaEnvios;

    /**
     * TODO: Completa el constructo de la clase
     * 
     * @param id id del porte
     * @param nave nave asociada
     * @param origen puerto origen
     * @param muelleOrigen muelle del puerto origen
     * @param salida fecha salida
     * @param destino puerto destino
     * @param muelleDestino muelle del puerto destino
     * @param llegada fecha llegado
     * @param precio precio
     */
    public Porte(String id, Nave nave, PuertoEspacial origen, int muelleOrigen, Fecha salida, PuertoEspacial destino, int muelleDestino, Fecha llegada, double precio) {
        this.id = id;
        this.nave = nave;
        this.origen = origen;
        this.muelleOrigen = muelleOrigen;
        this.salida = salida;
        this.destino = destino;
        this.muelleDestino = muelleDestino;
        this.llegada = llegada;
        this.precio = precio;
        this.huecos = new boolean[nave.getFilas()][nave.getColumnas()];
        this.listaEnvios = new ListaEnvios(nave.getFilas()*nave.getColumnas());
    }
    public String getID() {
        return id;
    }
    public Nave getNave(){
        return nave;
    }
    public PuertoEspacial getOrigen() {
        return origen;
    }
    public int getMuelleOrigen() {
        return muelleOrigen;
    }
    public Fecha getSalida(){
        return salida;
    }
    public PuertoEspacial getDestino() {
        return destino;
    }
    public int getMuelleDestino() {
        return muelleDestino;
    }
    public Fecha getLlegada() {
        return llegada;
    }
    public double getPrecio() {
        return precio;
    }
    // TODO: Devuelve el número de huecos libres que hay en el porte
    public int numHuecosLibres() {
        int fil = 0, col, contadorHuecos = 0;
        while(fil < huecos.length) {
            col = 0;
            while (col < huecos[fil].length) {
                if (!huecos[fil][col]) {
                    contadorHuecos++;
                }
                col++;
            }
            fil++;
        }
        return contadorHuecos;
    }
    // TODO: ¿Están llenos todos los huecos?
    public boolean porteLleno() {
        return numHuecosLibres() == 0;
    }
    // TODO: ¿Está ocupado el hueco consultado?
    public boolean huecoOcupado(int fila, int columna) {
        return huecos[fila-1][columna-1];
    }
    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }

    /**
     * TODO: Devuelve el objeto Envio que corresponde con una fila o columna,
     * @param fila fila de la nave
     * @param columna columna de la nave
     * @return el objeto Envio que corresponde, o null si está libre o se excede en el límite de fila y columna
     */
    public Envio buscarEnvio(int fila, int columna) {
        return listaEnvios.buscarEnvio(id, fila,columna);
    }

    /**
     * TODO: Método que Si está desocupado el hueco que indica el envio, lo pone ocupado y devuelve true,
     *  si no devuelve false
     * @param envio objeto envio a ingresar en el hueco
     * @return si se ocupa correctamente o no
     */
    public boolean ocuparHueco(Envio envio) {
        if (!huecoOcupado(envio.getFila(), envio.getColumna())) {
            huecos[envio.getFila()-1][envio.getColumna()-1] = true;
            envio.getCliente().aniadirEnvio(envio);
            listaEnvios.insertarEnvio(envio);
            return true;
        }
        return false;
    }

    /**
     * TODO: A través del localizador del envio, se desocupará el hueco
     * @param localizador localizador del envio
     * @return si se desocupa correctamente o no
     */
    public boolean desocuparHueco(String localizador) {
        Envio envio = buscarEnvio(localizador);
        if (huecoOcupado(envio.getFila(), envio.getColumna())) {
            huecos[envio.getFila()-1][envio.getColumna()-1] = false;
            envio.getCliente().cancelarEnvio(localizador);
            listaEnvios.eliminarEnvio(localizador);
            return true;
        }
        return false;
    }

    /**
     * TODO: Devuelve una cadena con información completa del porte
     * @return ejemplo del formato -> "Porte PM0066 de Gaia Galactic Terminal(GGT) M5 (01/01/2023 08:15:00) a
     *  Cidonia(CID) M1 (01/01/2024 11:00:05) en Planet Express One(EP-245732X) por 13424,56 SSD, huecos libres: 10"
     */
    public String toString() {
        return ("Porte " + id + " de " + origen.toStringSimple() + " " + origen.getCodigo() + " (" + salida + ") a " +
                destino.toStringSimple() + " " + destino.getCodigo() + " (" + llegada + ") en " + nave + " por " + precio + " SSD, huecos libres: " + numHuecosLibres());
    }

    /**
     * TODO: Devuelve una cadena con información abreviada del vuelo
     * @return ejemplo del formato -> "Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05)"
     */
    public String toStringSimple() {
        return "Porte " + id + " de " + origen.getIniciales() + " " + origen.getCodigo() + " (" + salida + ") a " + destino.getIniciales() + " " + destino.getCodigo() + " (" + llegada + ")";
    }

    /**
     * TODO: Devuelve true si el código origen, destino y fecha son los mismos que el porte
     * @param codigoOrigen codigo puerto origen
     * @param codigoDestino codigo puerto destino
     * @param fecha fecha salida
     * @return datos ingresados coinciden con el porte (true)
     */
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha) {
        return (codigoOrigen.equals(origen.getCodigo()) && codigoDestino.equals(destino.getCodigo()) && fecha.coincide(salida));
    }

    /**
     * TODO: Muestra la matriz de huecos del porte, ejemplo:
     *        A  B  C
     *      1[ ][ ][ ]
     *      2[X][X][X]
     *      3[ ][ ][ ]
     *      4[ ][X][ ]
     *     10[ ][ ][ ]
     */
    public void imprimirMatrizHuecos() {
        char val;

        // Letras superiores
        for (int o = 0; o < huecos[0].length; o++) {
            System.out.print(" ");
            System.out.printf("%3c", (char)o+'A');
        }
        System.out.println();

        for (int i = 0; i < huecos.length; i++) {
            // Numeros laterales
            System.out.printf(" %d", i+1);

            // Huecos
            for (int k = 0; k < huecos[i].length; k++) {
                val = huecos[i][k] ? 'X' : ' ';
                System.out.printf("[%c] ", val);
            }
            System.out.println();
        }
    }

    /**
     * TODO: Devuelve true si ha podido escribir en un fichero la lista de envíos del porte, siguiendo las indicaciones
     *  del enunciado
     * @param fichero nombre del fichero
     * @return escritura correcta o no
     */
    public boolean generarListaEnvios(String fichero) {
        PrintWriter pw = null;
        Envio envio;
        Cliente cliente;

        try {
            pw = new PrintWriter(new FileWriter("ficheros/"+fichero, false));

            pw.print("--------------------------------------------------\n");
            pw.print("-------- Lista de envíos del porte " + id + "--------\n");
            pw.print("--------------------------------------------------\n");
            pw.print("Hueco\tCliente\n");
            for (int i = 0; i < huecos.length; i++) {
                for (int k = 0; k < huecos[i].length; k++) {
                    if (huecos[i][k]) {
                        envio = buscarEnvio(i, k);
                        if (envio != null) {
                            cliente = envio.getCliente();
                            pw.printf("%2d%1c\t\t%s %s, %s\n",i+1,(char)k+'A',cliente.getNombre(),cliente.getApellidos(),cliente.getEmail());
                        }
                    } else {
                        pw.printf("%2d%1c\n",i+1,(char)k+'A');
                    }
                }
            }
            pw.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * TODO: Genera un ID de porte. Este consistirá en una cadena de 6 caracteres, de los cuales los dos primeros
     *  serán PM y los 4 siguientes serán números aleatorios.
     *  NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     * @param rand metodo random
     * @return ejemplo -> "PM0123"
     */
    public static String generarID(Random rand) {
        return String.format("PM%04d",rand.nextInt(10000));
    }

    /**
     * TODO: Crea y devuelve un objeto Porte de los datos que selecciona el usuario de puertos espaciales
     *  y naves y la restricción de que no puede estar repetido el identificador, siguiendo las indicaciones
     *  del enunciado.
     *  La función solicita repetidamente los parametros hasta que sean correctos
     * @param teclado scanner
     * @param rand metodo random
     * @param puertosEspaciales lista puertos espaciales
     * @param naves lista naves
     * @param portes lista portes
     * @return nuevo porte
     */
    public static Porte altaPorte(Scanner teclado, Random rand,
                                  ListaPuertosEspaciales puertosEspaciales,
                                  ListaNaves naves,
                                  ListaPortes portes) {
        boolean cancelar = false;
        // GENERAR ID
        String id;
        do {
            id = generarID(rand);
        } while(portes.buscarPorte(id) != null);

        // PUERTO ORIGEN
        PuertoEspacial puertoOrigen = puertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese código de puerto Origen: ");
        cancelar = puertoOrigen == null;

        // MUELLE ORIGEN
        int muelleOrigen = -1;
        while ((muelleOrigen < 1 || muelleOrigen > puertoOrigen.getMuelles()) && !cancelar) {
            muelleOrigen = Utilidades.leerNumero(teclado,"Ingrese el muelle de Origen (1 - " + puertoOrigen.getMuelles() + "): ",1,puertoOrigen.getMuelles());
        }

        // PUERTO DESTINO
        PuertoEspacial puertoDestino = null;
        if (!cancelar) {
            puertoDestino = puertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese código de puerto Destino: ");
            cancelar = puertoDestino == null;
        }

        // MUELLE DESTINO
        int muelleDestino = -1;
        while ((muelleDestino < 1 || muelleDestino > puertoDestino.getMuelles()) && !cancelar) {
            muelleDestino = Utilidades.leerNumero(teclado,"Ingrese el Terminal Destino (1 - " + puertoDestino.getMuelles() + "): ",1, puertoDestino.getMuelles());
        }

        // NAVE
        Nave nave = null;
        if (!cancelar){
            naves.mostrarNaves();
            nave = naves.seleccionarNave(teclado, "Ingrese matrícula de la nave: ", puertoOrigen.distancia(puertoDestino));
            cancelar = nave == null;
        }

        // FECHA SALIDA Y LLEGADA
        Fecha fechaSalida = null;
        Fecha fechaLlegada = null;
        if (!cancelar) {
            do{
                fechaSalida = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de salida:");
                fechaLlegada = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de llegada:");

                if (fechaLlegada.anterior(fechaSalida)){
                    System.out.println("\tLlegada debe ser posterior a salida");
                }
            } while (fechaLlegada.anterior(fechaSalida));
        }

        // PRECIO
        double precio = -1;
        while (precio < 0 && !cancelar) {
            precio = Utilidades.leerNumero(teclado, "Ingrese precio de pasaje: ", 1, Utilidades.maxPrecioEnvio);
        }

        // RETURN
        if (!cancelar) {
            return new Porte(id, nave, puertoOrigen, muelleOrigen, fechaSalida, puertoDestino, muelleDestino, fechaLlegada, precio);
        }
        return null;
    }
}