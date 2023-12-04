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
     * @param id
     * @param nave
     * @param origen
     * @param muelleOrigen
     * @param salida
     * @param destino
     * @param muelleDestino
     * @param llegada
     * @param precio
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
        return huecos[fila][columna];
    }
    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }

    /**
     * TODO: Devuelve el objeto Envio que corresponde con una fila o columna,
     * @param fila
     * @param columna
     * @return el objeto Envio que corresponde, o null si está libre o se excede en el límite de fila y columna
     */
    public Envio buscarEnvio(int fila, int columna) {
        return listaEnvios.buscarEnvio(id, fila,columna);
    }

    /**
     * TODO: Método que Si está desocupado el hueco que indica el envio, lo pone ocupado y devuelve true,
     *  si no devuelve false
     * @param envio
     * @return
     */
    public boolean ocuparHueco(Envio envio) {
        if (!huecoOcupado(envio.getFila(), envio.getColumna())) {
            huecos[envio.getFila()][envio.getColumna()] = true;
            listaEnvios.insertarEnvio(envio);
            return true;
        }
        return false;
    }

    /**
     * TODO: A través del localizador del envio, se desocupará el hueco
     * @param localizador
     * @return
     */
    public boolean desocuparHueco(String localizador) {
        Envio envio = buscarEnvio(localizador);
        if (huecoOcupado(envio.getFila(), envio.getColumna())) {
            huecos[envio.getFila()][envio.getColumna()] = false;
            listaEnvios.eliminarEnvio(envio.getLocalizador());
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
     * @param codigoOrigen
     * @param codigoDestino
     * @param fecha
     * @return
     */
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha) {
        return (codigoOrigen == origen.getCodigo() && codigoDestino == origen.getCodigo() && fecha == salida);
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
        char val, letras[] = Utilidades.getLetras();

        // Letras superiores
        System.out.print("  ");
        for (int o = 0; o < huecos[0].length; o++) {
            System.out.printf("%3c ", letras[o]);
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
     * @param fichero
     * @return
     */
    public boolean generarListaEnvios(String fichero) {
        PrintWriter pw = null;
        Envio envio = null;
        Cliente cliente = null;
        char letras[] = Utilidades.getLetras();

        try {
            pw = new PrintWriter(new FileWriter(fichero, true));

            pw.println("--------------------------------------------------");
            pw.println("-------- Lista de envíos del porte " + id + "--------");
            pw.println("--------------------------------------------------");
            pw.println("Hueco\t\tCliente");
            for (int i = 0; i < huecos.length; i++) {
                for (int k = 0; k < huecos[i].length; k++) {
                    if (huecos[i][k]) {
                        envio = listaEnvios.buscarEnvio(id, i, k);
                        cliente = envio.getCliente();
                        pw.printf("%2d%1c\t\t%s %s, %s\n",envio.getFila(),letras[envio.getColumna()],cliente.getNombre(),cliente.getApellidos(),cliente.getEmail());
                    } else {
                        pw.printf("%2d%1c\n",i+1,letras[k]);
                    }
                }
            }
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
     * @param rand
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
     * @param teclado
     * @param rand
     * @param puertosEspaciales
     * @param naves
     * @param portes
     * @return
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
        String origen;
        PuertoEspacial puertoOrigen = null;
        while (puertoOrigen == null && !cancelar) {
            origen = Utilidades.leerCadena(teclado,"Ingrese código de puerto Origen: ");
            puertoOrigen = puertosEspaciales.buscarPuertoEspacial(origen);

            if (origen.toLowerCase() == "cancelar") {
                cancelar = true;
            } else if (puertoOrigen == null) {
                System.out.println("\tCódigo de puerto no encontrado.");
            }
        }

        // MUELLE ORIGEN
        int muelleOrigen = -1;
        while ((muelleOrigen < 1 || muelleOrigen > puertoOrigen.getMuelles()) && !cancelar) {
            muelleOrigen = Utilidades.leerNumero(teclado,"Ingrese el muelle de Origen (1 - " + puertoOrigen.getMuelles() + "): ",1,4);
        }

        // PUERTO DESTINO
        String destino;
        PuertoEspacial puertoDestino = null;
        while (puertoDestino == null && !cancelar) {
            destino = Utilidades.leerCadena(teclado,"Ingrese código de puerto Destino: ");
            puertoDestino = puertosEspaciales.buscarPuertoEspacial(destino);

            if (destino.toLowerCase() == "cancelar") {
                cancelar = true;
            } else if (puertoDestino == null) {
                System.out.println("\tCódigo de puerto no encontrado.");
            }
        }

        // MUELLE DESTINO
        int muelleDestino = -1;
        while ((muelleDestino < 1 || muelleDestino > puertoDestino.getMuelles()) && !cancelar) {
            muelleDestino = Utilidades.leerNumero(teclado,"Ingrese el Terminal Destino (1 - " + puertoDestino.getMuelles() + "): ",1,4);
        }

        // NAVE
        String matricula;
        Nave nave = null;
        while ((nave == null || nave.getAlcance() < puertoOrigen.distancia(puertoDestino)) && !cancelar) {
            matricula = Utilidades.leerCadena(teclado, "Ingrese matrícula de la nave");
            nave = naves.buscarNave(matricula);

            if (matricula.toLowerCase() == "cancelar") {
                cancelar = true;
            } else if (nave == null) {
                System.out.println("\tMatricula de nave no encontrada");
            } else if (nave.getAlcance() < puertoOrigen.distancia(puertoDestino)) {
                System.out.println("\tNave seleccionada con alcance insuficiente");
            }
        }

        // FECHA SALIDA Y LLEGADA
        int fechaSalidaArr[];
        Fecha fechaSalida = null;
        int fechaLlegadaArr[];
        Fecha fechaLlegada = null;
        while (fechaSalida.anterior(fechaLlegada)) {
            System.out.println("Introduzca la fecha de salida:");
            fechaSalidaArr = Utilidades.pedirFecha(teclado);
            fechaSalida = new Fecha(fechaSalidaArr[0], fechaSalidaArr[1], fechaSalidaArr[2], fechaSalidaArr[3], fechaSalidaArr[4], fechaSalidaArr[5]);

            System.out.println("Introduzca la fecha de llegada:");
            fechaLlegadaArr = Utilidades.pedirFecha(teclado);
            fechaLlegada = new Fecha(fechaLlegadaArr[0], fechaLlegadaArr[1], fechaLlegadaArr[2], fechaLlegadaArr[3], fechaLlegadaArr[4], fechaLlegadaArr[5]);

            if (fechaSalida.anterior(fechaLlegada)){
                System.out.println("\tLlegada debe ser posterior a salida");
            }
        }

        // PRECIO
        double precio = -1;
        while (precio < 0) {
            precio = Utilidades.leerNumeroSinLimites(teclado, "Ingrese precio de pasaje: ");
        }

        // RETURN
        if (!cancelar) {
            Porte porte = new Porte(id, nave, puertoOrigen, muelleOrigen, fechaSalida, puertoDestino, muelleDestino, fechaLlegada, precio);
            System.out.println("\tPorte " + id + " creado correctamente");
            return porte;
        } else {
            return null;
        }
    }
}