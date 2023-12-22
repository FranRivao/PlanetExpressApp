import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Francisco Manuel Rivao
 * @author Alejandro Sanchez Millan
 * @version     1.0
 */
public class Cliente {
    private final ListaEnvios listaEnvios;
    private final String nombre;
    private final String apellidos;
    private final String email;

    /**
     * Constructor of the class
     *
     * @param nombre Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @param email Email del cliente
     * @param maxEnvios Número máximo de envíos que puede tener el cliente
     */
    public Cliente(String nombre, String apellidos, String email, int maxEnvios) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.listaEnvios = new ListaEnvios(maxEnvios);
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public String getEmail() {
        return email;
    }
    // TODO: Texto que debe generar: Zapp Brannigan, zapp.brannigan@dop.gov
    public String toString() {
        return nombre + " " + apellidos + ", " + email;
    }
    // TODO: Devuelve un booleano que indica si se ha alcanzado el número máximo de envíos
    public boolean maxEnviosAlcanzado() {
        return listaEnvios.estaLlena();
    }
    // TODO: Devuelve un envío en función de su posición
    public Envio getEnvio(int i) {
        return listaEnvios.getEnvio(i);
    }
    public ListaEnvios getListaEnvios() {
        return listaEnvios;
    }
    // TODO: Añade un envío al cliente
    public boolean aniadirEnvio(Envio envio) {
        return listaEnvios.insertarEnvio(envio);
    }
    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }
    // TODO: Elimina el envío de la lista de envíos del pasajero
    public boolean cancelarEnvio(String localizador) {
        return listaEnvios.eliminarEnvio(localizador);
    }
    public void listarEnvios() {
        listaEnvios.listarEnvios();
    }
    // Encapsula la funcionalidad seleccionarEnvio de ListaEnvios
    public Envio seleccionarEnvio(Scanner teclado, String mensaje) {
        return listaEnvios.seleccionarEnvio(teclado, mensaje);
    }


    /**
     * TODO: Método estático para crear un nuevo cliente "no repetido", se pide por teclado los datos necesarios
     * al usuario en el orden y con los textos indicados en los ejemplos de ejecución del enunciado
     * La función tiene que solicitar repetidamente los parámetros hasta que sean correctos
     * @param teclado
     * @param clientes
     * @param maxEnvios
     * @return Cliente
     */
    public static Cliente altaCliente(Scanner teclado, ListaClientes clientes, int maxEnvios) {
        String nombre, apellidos = "", email = "";
        boolean cancelar;
        // NOMBRE
        do {
            nombre = Utilidades.leerCadena(teclado, "Nombre: ");
            cancelar = nombre.equalsIgnoreCase("cancelar");
        } while (!nombre.matches("[A-Za-z]+") && !cancelar);

        // APELLIDOS
        while (!apellidos.matches("[A-Za-z]+") && !cancelar) {
            apellidos = Utilidades.leerCadena(teclado, "Apellidos: ");
            cancelar = apellidos.equalsIgnoreCase("cancelar");
        }

        // EMAIL
        while ((clientes.buscarClienteEmail(email) != null || !correctoEmail(email)) && !cancelar) {
            email = Utilidades.leerCadena(teclado, "Email: ");


            if (apellidos.equalsIgnoreCase("cancelar")) {
                cancelar = true;
            } else if (!correctoEmail(email)) {
                System.out.println("El email no es valido");
            } else if (clientes.buscarClienteEmail(email) != null) {
                System.out.println("El email ya esta en uso");
            }
        }

        // RETURN
        if (!cancelar) {
            Cliente cliente = new Cliente(nombre, apellidos, email, maxEnvios);
            clientes.insertarCliente(cliente);
            return cliente;
        }
        return null;
    }


    /**
     * TODO: Metodo para comprobar que el formato de email introducido sea correcto
     * @param email
     * @return
     */
    public static boolean correctoEmail(String email) {
        String [] emailSplit = email.split("@");
        return emailSplit.length > 1 && emailSplit[0].matches("[A-Za-z1-9/.]+") && (emailSplit[1].equals("planetexpress.com") || emailSplit[1].equals("upm.es"));
    }
}
