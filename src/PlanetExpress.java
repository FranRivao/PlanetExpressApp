import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal de Planet Express App, la práctica de Taller de Programación
 *
 * @author      Francisco Manuel Rivao
 * @version     1.0
 */
public class PlanetExpress {
    private final int maxPortes;
    private final int maxNaves;
    private final int maxClientes;
    private final int maxEnviosPorCliente;
    private ListaPuertosEspaciales listaPuertosEspaciales;
    private final int maxPuertosEspaciales;
    private ListaNaves listaNaves;
    private ListaClientes listaClientes;
    private ListaPortes listaPortes;

    /**
     * TODO: Rellene el constructor de la clase
     *
     * @param maxPuertosEspaciales Máximo número de puertos espaciales que tendrá la lista de puertos espaciales de PlanetExpress App.
     * @param maxNaves Máximo número de naves que tendrá la lista de naves de PlanetExpress App.
     * @param maxPortes Máximo número de portes que tendrá la lista de portes de PlanetExpress App.
     * @param maxClientes Máximo número de clientes que tendrá la lista de clientes de PlanetExpress App.
     * @param maxEnviosPorCliente Máximo número de envíos por cliente.
     */
    public PlanetExpress(int maxPuertosEspaciales, int maxNaves, int maxPortes, int maxClientes, int maxEnviosPorCliente) {
        this.maxPuertosEspaciales = maxPuertosEspaciales;
        this.maxNaves = maxNaves;
        this.maxPortes = maxPortes;
        this.maxClientes = maxClientes;
        this.maxEnviosPorCliente = maxEnviosPorCliente;
    }


    /**
     * TODO: Metodo para leer los datos de los ficheros específicados en el enunciado y los agrega a
     *  la información de PlanetExpress (listaPuertosEspaciales, listaNaves, listaPortes, listaClientes)
     * @param ficheroPuertos nombre fichero puertos
     * @param ficheroNaves nombre fichero naves
     * @param ficheroPortes nombre fichero portes
     * @param ficheroClientes nombre fichero clientes
     * @param ficheroEnvios nombre fichero envios
     */
    public void cargarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {
        listaPuertosEspaciales = ListaPuertosEspaciales.leerPuertosEspacialesCsv(ficheroPuertos, maxPuertosEspaciales);
        listaNaves = ListaNaves.leerNavesCsv(ficheroNaves, maxNaves);
        listaClientes = ListaClientes.leerClientesCsv(ficheroClientes, maxClientes, maxEnviosPorCliente);
        listaPortes = ListaPortes.leerPortesCsv(ficheroPortes, maxPortes, listaPuertosEspaciales, listaNaves);
        ListaEnvios.leerEnviosCsv(ficheroEnvios, listaPortes, listaClientes);
    }


    /**
     * TODO: Metodo para almacenar los datos de PlanetExpress en los ficheros .csv especificados
     *  en el enunciado de la práctica
     * @param ficheroPuertos nombre fichero puertos
     * @param ficheroNaves nombre fichero naves
     * @param ficheroPortes nombre fichero portes
     * @param ficheroClientes nombre fichero clientes
     * @param ficheroEnvios nombre fichero envios
     */
    public void guardarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {
        listaClientes.escribirClientesCsv(ficheroClientes);
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(ficheroEnvios, false));
            pw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < listaClientes.getOcupacion(); i++) {
            listaClientes.getCliente(i).getListaEnvios().aniadirEnviosCsv(ficheroEnvios);
        }
        listaPortes.escribirPortesCsv(ficheroPortes);
        listaPuertosEspaciales.escribirPuertosEspacialesCsv(ficheroPuertos);
        listaNaves.escribirNavesCsv(ficheroNaves);
    }
    public boolean maxPortesAlcanzado() {
        return listaPortes.estaLlena();
    }
    public boolean insertarPorte (Porte porte) {
        return listaPortes.insertarPorte(porte);
    }
    public boolean maxClientesAlcanzado() {
        return listaClientes.estaLlena();
    }
    public boolean insertarCliente (Cliente cliente) {
        return listaClientes.insertarCliente(cliente);
    }

    /**
     * TODO: Metodo para buscar los portes especificados tal y como aparece en el enunciado de la práctica,
     *  Devuelve una lista de los portes entre dos puertos espaciales con una fecha de salida solicitados por teclado
     *  al usuario en el orden y con los textos establecidos (tomar como referencia los ejemplos de ejecución en el
     *  enunciado de la prática)
     * @param teclado scanner
     * @return lista portes encontrados
     */
    public ListaPortes buscarPorte(Scanner teclado) {
        String codigoOrigen = "", codigoDestino = "";
        Fecha fecha = null;
        boolean cancelar;
        do {
            codigoOrigen = Utilidades.leerCadena(teclado, "Ingrese código de puerto Origen: ");
            cancelar = codigoOrigen.equalsIgnoreCase("cancelar");
        } while (!codigoOrigen.matches("[A-Z][A-Z][/-][0-9]") && !cancelar);

        while (!codigoDestino.matches("[A-Z][A-Z][/-][0-9]") && !cancelar) {
            codigoDestino = Utilidades.leerCadena(teclado, "Ingrese código de puerto Destino: ");
            cancelar = codigoDestino.equalsIgnoreCase("cancelar");
        }

        while (fecha == null && !cancelar) {
            fecha = Utilidades.leerFecha(teclado, "Fecha de Salida: ");
        }

        if (!cancelar) {
            return listaPortes.buscarPortes(codigoOrigen, codigoDestino, fecha);
        } else return null;
    }


    /**
     * TODO: Metodo para contratar un envio tal y como se indica en el enunciado de la práctica. Se contrata un envio para un porte
     *  especificado, pidiendo por teclado los datos necesarios al usuario en el orden y con los textos (tomar como referencia los
     *  ejemplos de ejecución en el enunciado de la prática)
     * @param teclado scanner
     * @param rand metodo random
     * @param porte porte donde se contrata el envio
     */
    public void contratarEnvio(Scanner teclado, Random rand, Porte porte) {
        if (porte != null && !porte.porteLleno()) {
            porte.imprimirMatrizHuecos();
            char valorEntrada;
            do {
                 valorEntrada = Utilidades.leerLetra(teclado, "¿Comprar un billete para un nuevo pasajero (n), o para uno ya existente (e)? ", 'a', 'z');
                if (valorEntrada != 'n' && valorEntrada != 'e') {
                    System.out.println("El valor de la entrada debe ser 'n' o 'e'");
                }
            } while (valorEntrada != 'n' && valorEntrada != 'e');

            Cliente cliente;
            if (valorEntrada == 'e') {
                cliente = listaClientes.seleccionarCliente(teclado, "Email del cliente: ");
            } else {
                cliente = Cliente.altaCliente(teclado, listaClientes, maxEnviosPorCliente);
                insertarCliente(cliente);
            }

            if (cliente != null) {
                Envio.altaEnvio(teclado, rand, porte, cliente);
            }
        }
    }


    /**
     * TODO Metodo statico con la interfaz del menú de entrada a la App.
     * Tiene que coincidir con las trazas de ejecución que se muestran en el enunciado
     * @param teclado scanner
     * @return opción seleccionada
     */
    public static int menu(Scanner teclado) {
        System.out.print("1. Alta de Porte\n" +
                "2. Alta de Cliente\n" +
                "3. Buscar Porte\n" +
                "4. Mostrar envíos de un cliente\n" +
                "5. Generar lista de envíos\n" +
                "0. Salir\n");
        return Utilidades.leerNumero(teclado, "Seleccione opción: ", 0,5);
    }

    /**
     * TODO: Método Main que carga los datos de los ficheros CSV pasados por argumento (consola) en PlanetExpress,
     *  llama iterativamente al menú y realiza la opción especificada hasta que se indique la opción Salir. Al finalizar
     *  guarda los datos de PlanetExpress en los mismos ficheros CSV.
     * @param args argumentos de la línea de comandos, recibe **10 argumentos** estrictamente en el siguiente orden:
     * 1. Número máximo de puertos espaciales que tendrá la lista de puertos espaciales de PlanetExpress App.
     * 2. Número máximo de naves que tendrá la lista de naves de PlanetExpress App.
     * 3. Número máximo de portes que tendrá la lita de portes de PlanetExpress App.
     * 4. Número máximo de clientes que tendrá la lista de clientes de PlanetExpress App.
     * 5. Número máximo de envíos por pasajero.
     * 6. Nombre del fichero CSV que contiene la lista de puertos espaciales de PlanetExpress App (tanto para lectura como escritura).
     * 7. Nombre del fichero CSV que contiene la lista de naves de PlanetExpress App (tanto para lectura como escritura).
     * 8. Nombre del fichero CSV que contiene la lista de portes de PlanetExpress App (tanto para lectura como escritura).
     * 9. Nombre del fichero CSV que contiene la lista de clientes de PlanetExpress App (tanto para lectura como escritura).
     * 10. Nombre del fichero CSV que contiene los envíos adquiridos en PlanetExpress App (tanto para lectura como escritura).
     * En el caso de que no se reciban exactamente estos argumentos, el programa mostrará el siguiente mensaje
     * y concluirá la ejecución del mismo: `Número de argumentos incorrecto`.
     */
    public static void main(String [] args) {
        if (args.length != 10) {
            System.out.println("Número de argumentos incorrecto");
            return;
        }

        String ficheroPuertos = "ficheros/"+args[5], ficheroNaves = "ficheros/"+args[6], ficheroPortes = "ficheros/"+args[7], ficheroClientes = "ficheros/"+args[8], ficheroEnvios = "ficheros/"+args[9];
        PlanetExpress app = new PlanetExpress(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]));
        Scanner teclado = new Scanner(System.in);
        app.cargarDatos(ficheroPuertos, ficheroNaves, ficheroPortes, ficheroClientes, ficheroEnvios);

        int opcion;

        do {
            opcion = menu(teclado);
            switch (opcion) {
                case 1:     // TODO: Alta de Porte
                    if(!app.maxPortesAlcanzado()){
                        Porte porte = Porte.altaPorte(teclado,
                                new Random(),
                                app.listaPuertosEspaciales,
                                app.listaNaves,
                                app.listaPortes
                        );
                        if (porte != null) {
                            System.out.println(app.insertarPorte(porte) ?
                                    "\tPorte " + porte.getID() + " creado correctamente" :
                                    "Ha ocurrido un error al crear el porte");
                        }
                    } else System.out.println("No se pueden dar de alta mas portes");
                    break;
                case 2:     // TODO: Alta de Cliente
                    if(!app.maxClientesAlcanzado()) {
                        Cliente cliente = Cliente.altaCliente(teclado,
                                app.listaClientes,
                                app.maxEnviosPorCliente
                        );
                        if (cliente != null) {
                            app.insertarCliente(cliente);
                            System.out.println("\tCliente con email " + cliente.getEmail() + " creado correctamente");
                        }
                    } else System.out.println("No se pueden dar de alta mas clientes");
                    break;
                case 3:     // TODO: Buscar Porte
                    ListaPortes lista;
                    do {
                        lista = app.buscarPorte(teclado);
                        if (lista.getOcupacion() <= 0)
                            System.out.println("No existe ese porte");
                    } while (lista.getOcupacion() <= 0);
                    app.contratarEnvio(teclado, new Random(),lista.seleccionarPorte(teclado, "Seleccione un porte: ", "cancelar"));
                    break;
                case 4:     // TODO: Listado de envíos de un cliente
                    Cliente cliente = app.listaClientes.seleccionarCliente(teclado,"Email del cliente: ");
                    if (cliente != null){
                        Envio envio = cliente.getListaEnvios().seleccionarEnvio(teclado, "Seleccione un envío: ");
                        if (envio != null) {
                            char valorEntrada;
                            do {
                                valorEntrada = Utilidades.leerLetra(teclado, "¿Cancelar envío (c), o generar factura (f)? ", 'a', 'z');
                                if (valorEntrada != 'c' && valorEntrada != 'f') {
                                    System.out.println("El valor de la entrada debe ser 'c' o 'f'");
                                }
                            } while (valorEntrada != 'c' && valorEntrada != 'f');
                            if (valorEntrada == 'f') {
                                envio.generarFactura(Utilidades.leerCadena(teclado, "Nombre del fichero: "));
                            } else envio.cancelar();
                        }
                    }
                    break;
                case 5:     // TODO: Lista de envíos de un porte
                    Porte porte = app.listaPortes.seleccionarPorte(teclado, "Seleccione un porte: ", "cancelar");
                    if (porte != null) {
                        porte.generarListaEnvios(Utilidades.leerCadena(teclado, "Nombre del fichero: "));
                    }
                    break;
            }
        } while (opcion != 0);

        app.guardarDatos(ficheroPuertos, ficheroNaves, ficheroPortes, ficheroClientes, ficheroEnvios);
    }
}