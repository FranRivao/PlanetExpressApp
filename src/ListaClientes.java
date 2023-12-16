import java.io.*;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version     1.0
 */
public class ListaClientes {
    private Cliente[] clientes;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaClientes(int capacidad) {
        this.clientes = new Cliente[capacidad];
    }
    // TODO: Devuelve el número de clientes que hay en la lista de clientes
    public int getOcupacion() {
        int pos = 0;
        while(clientes[pos] != null && pos < clientes.length) {
            pos++;
        }
        return pos;
    }
    // TODO: ¿Está llena la lista de clientes?
    public boolean estaLlena() {
        return getOcupacion() == clientes.length;
    }
	// TODO: Devuelve el cliente dada el indice
    public Cliente getCliente(int i) {
        return clientes[i];
    }
    // TODO: Inserta el cliente en la lista de clientes
    public boolean insertarCliente(Cliente cliente) {
        if (!estaLlena()){
            int pos = getOcupacion();
            clientes[pos] = cliente;
            return true;
        }
        return false;
    }
    // TODO: Devuelve el cliente que coincida con el email, o null en caso de no encontrarlo
    public Cliente buscarClienteEmail(String email) {
        int pos = 0;
        Cliente cliente = null;
        while(clientes[pos] != null && cliente == null) {
            if (clientes[pos].getEmail() == email) {
                cliente = clientes[pos];
            }
        }
        return cliente;
    }

    /**
     * TODO: Método para seleccionar un Cliente existente a partir de su email, usando el mensaje pasado como argumento
     *  para la solicitud y, siguiendo el orden y los textos mostrados en el enunciado.
     *  La función debe solicitar repetidamente hasta que se introduzca un email correcto
     * @param teclado
     * @param mensaje
     * @return
     */
    public Cliente seleccionarCliente(Scanner teclado, String mensaje) {
        Cliente cliente = null;
        String email;
        // PEDIR EMAIL
        do {
            email = Utilidades.leerCadena(teclado, "Email del cliente: ");
        } while(!Cliente.correctoEmail(email));

        // BUSCAR CLIENTE
        int pos = 0;
        while (cliente == null && pos < clientes.length) {
            if (clientes[pos].getEmail() == email) {
                cliente = clientes[pos];
            }
            pos++;
        }
        return cliente;
    }

    /**
     * TODO: Método para guardar la lista de clientes en un fichero .csv, sobreescribiendo la información del mismo
     *  fichero
     * @param fichero
     * @return
     */
    public boolean escribirClientesCsv(String fichero) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(fichero, true));
            for (int i = 0; i < clientes.length; i++) {
                pw.printf("%s;%s;%s\n", clientes[i].getNombre(), clientes[i].getApellidos(), clientes[i].getEmail());
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
     * TODO: Genera una lista de Clientes a partir del fichero CSV, usando los límites especificados como argumentos
     *  para la capacidad de la lista y el número de billetes máximo por pasajero
     * @param fichero
     * @param capacidad
     * @param maxEnviosPorCliente
     * @return lista de clientes
     */
    public static ListaClientes leerClientesCsv(String fichero, int capacidad, int maxEnviosPorCliente) {
        ListaClientes listaClientes = new ListaClientes(capacidad);
        Scanner sc = null;
        String nombre, apellidos, email;

        try {
            sc = new Scanner(new FileReader(fichero));
            int pos, puntoComas = 0;

            while (sc.hasNext()) {
                String cadena = sc.nextLine();
                nombre = apellidos = email = ""; pos = puntoComas = 0;

                while (pos < cadena.length()) {
                    if (cadena.charAt(pos) != ';') {
                        switch (puntoComas) {
                            case 0:
                                nombre += cadena.charAt(pos);
                                break;
                            case 1:
                                apellidos += cadena.charAt(pos);
                                break;
                            case 2:
                                email += cadena.charAt(pos);
                                break;
                        }
                    } else puntoComas++;
                    pos++;
                }

                listaClientes.insertarCliente(new Cliente(nombre, apellidos, email, maxEnviosPorCliente));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return listaClientes;
    }
}