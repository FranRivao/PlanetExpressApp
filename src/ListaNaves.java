import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author
 * @author
 * @version     1.0
 */
public class ListaNaves {
    private Nave[] naves;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad cantidad de espacios en la nave
     */
    public ListaNaves(int capacidad) {
        this.naves = new Nave[capacidad];
    }
    // TODO: Devuelve el número de naves que hay en la lista
    public int getOcupacion() {
        int pos=0;
        while(naves[pos] != null){
            pos++;
        }
        return pos;

    }
    // TODO: ¿Está llena la lista de naves?
    public boolean estaLlena() {
        return getOcupacion() == naves.length;

    }
	// TODO: Devuelve nave dado un indice
    public Nave getNave(int posicion) {
        return naves[posicion] ;
    }

    /**
     * TODO: insertamos una nueva nave en la lista
     * @param nave objeto nave a insertar
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarNave(Nave nave) {
        boolean res = false;
        if(!estaLlena()){
            int pos=0;
            while(naves[pos] != null){
                pos++;
            }
            naves[pos]=nave;
            res=true;
        };

        return res;
    }
    /**
     * TODO: Buscamos la nave a partir de la matricula pasada como parámetro
     * @param matricula matricula de la nave buscada
     * @return la nave que encontramos o null si no existe
     */
    public Nave buscarNave(String matricula) {
        int posicion = 0;
        Nave nave = null;
        while(naves[posicion] != null && nave == null){
            if(naves[posicion].getMatricula().equals(matricula)){
                nave = naves[posicion];
            }
            posicion++;
        }
        return nave;
    }
    // TODO: Muestra por pantalla las naves de la lista con el formato indicado en el enunciado
    public void mostrarNaves() {
        for(int i = 0; i < naves.length; i++){
            if (naves[i] != null)
                System.out.printf("\t%s\n", naves[i].toString());
        }
    }



    /**
     * TODO: Permite seleccionar una nave existente a partir de su matrícula, y comprueba si dispone de un alcance
     *  mayor o igual que el pasado como argumento, usando el mensaje pasado como argumento para la solicitud y
     *  siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente la matrícula de la nave hasta que se introduzca una con alcance suficiente
     * @param teclado scanner
     * @param mensaje mensaje para solicitar la matricula de la nave
     * @param alcance alcance del puerto origen al puerto destino
     * @return objeto nave seleccionada
     */
    public Nave seleccionarNave(Scanner teclado, String mensaje, double alcance) {
        Nave nave = null;
        boolean cancelar = false;
        String matricula;
        while ((nave == null || nave.getAlcance() < alcance) && !cancelar) {
            matricula = Utilidades.leerCadena(teclado, mensaje);
            nave = buscarNave(matricula);

            if (matricula.equalsIgnoreCase(  "cancelar")) {
                cancelar = true;
            } else if (nave == null) {
                System.out.println("\tMatricula de nave no encontrada");
            } else if (nave.getAlcance() < alcance) {
                System.out.println("\tNave seleccionada con alcance insuficiente");
            }
        }
        return nave;
    }


    /**
     * TODO: Genera un fichero CSV con la lista de Naves, SOBREESCRIBIÉNDOLO
     * @param nombre nombre del fichero
     * @return escritura correcta o no
     */
    public boolean escribirNavesCsv(String nombre) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombre, false))) {
            for (int i = 0; i < getOcupacion(); i++) {
                pw.printf("%s;%s;%s;%s;%s;%s\n",
                        naves[i].getMarca(),
                        naves[i].getModelo(),
                        naves[i].getMatricula(),
                        naves[i].getFilas(),
                        naves[i].getColumnas(),
                        naves[i].getAlcance()
                );
            }
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     * TODO: Genera una lista de naves a partir del fichero CSV, usando el argumento como capacidad máxima de la lista
     * @param fichero nombre del fichero
     * @param capacidad cantidad maxima de naves
     * @return lista naves
     */
    public static ListaNaves leerNavesCsv(String fichero, int capacidad) {
        ListaNaves listaNaves = new ListaNaves(capacidad);
        String marca, modelo, matricula, filas, columnas, alcance;

        try (Scanner sc = new Scanner(new FileReader(fichero))) {
            int pos, puntoComas = 0;

            while (sc.hasNext()) {
                String cadena = sc.nextLine();
                marca = modelo = matricula = filas = columnas = alcance = "";
                pos = puntoComas = 0;

                while (pos < cadena.length()) {
                    if (cadena.charAt(pos) != ';') {
                        switch (puntoComas) {
                            case 0:
                                marca += cadena.charAt(pos);
                                break;
                            case 1:
                                modelo += cadena.charAt(pos);
                                break;
                            case 2:
                                matricula += cadena.charAt(pos);
                                break;
                            case 3:
                                filas += cadena.charAt(pos);
                                break;
                            case 4:
                                columnas += cadena.charAt(pos);
                                break;
                            case 5:
                                alcance += cadena.charAt(pos);
                                break;
                        }
                    } else puntoComas++;
                    pos++;
                }

                listaNaves.insertarNave(new Nave(marca, modelo, matricula, Integer.parseInt(columnas), Integer.parseInt(filas), Double.parseDouble(alcance)));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return listaNaves;
    }
}
