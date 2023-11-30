import java.io.FileWriter;
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
     * @param capacidad
     */
    public ListaNaves(int capacidad) {
        this.naves=new Nave[capacidad];

        
		
		
    }
    // TODO: Devuelve el número de naves que hay en la lista
    public int getOcupacion() {
        int pos=0;
        while(naves[pos]!=null){
            pos++;
        }
        return pos;

    }
    // TODO: ¿Está llena la lista de naves?
    public boolean estaLlena() {
        return getOcupacion()== naves.length;

    }
	// TODO: Devuelve nave dado un indice
    public Nave getNave(int posicion) {
        return naves[posicion] ;
    }

    /**
     * TODO: insertamos una nueva nave en la lista
     * @param nave
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarNave(Nave nave) {
        boolean res=false;
        if(!estaLlena()){
            int pos=0;
            while(naves[pos]!=null){
                pos++;
            }
            naves[pos]=nave;
            res=true;
        } else res=false;
            return res;

    }
    /**
     * TODO: Buscamos la nave a partir de la matricula pasada como parámetro
     * @param matricula
     * @return la nave que encontramos o null si no existe
     */
    public Nave buscarNave(String matricula) {
        int posicion=0;
        Nave nave=null;
        while(naves[posicion]!=null && nave==null){
            if(naves[posicion].getMatricula()==matricula){
                nave=naves[posicion];
            }
            posicion++;
        }
        return nave;
    }
    // TODO: Muestra por pantalla las naves de la lista con el formato indicado en el enunciado
    public void mostrarNaves() {
        for(int i=0;i<naves.length;i++){
            System.out.println(naves[i].toString());
        }

    }



    /**
     * TODO: Permite seleccionar una nave existente a partir de su matrícula, y comprueba si dispone de un alcance
     *  mayor o igual que el pasado como argumento, usando el mensaje pasado como argumento para la solicitud y
     *  siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente la matrícula de la nave hasta que se introduzca una con alcance suficiente
     * @param teclado
     * @param mensaje
     * @param alcance
     * @return
     */
    public Nave seleccionarNave(Scanner teclado, String mensaje, double alcance) {
        System.out.println(mensaje);
        Nave nave =naves[teclado.nextInt()];
        return nave;

    }


    /**
     * TODO: Genera un fichero CSV con la lista de Naves, SOBREESCRIBIÉNDOLO
     * @param nombre
     * @return
     */
    public boolean escribirNavesCsv(String nombre) {
        PrintWriter pw = null;
        try {
            pw=new PrintWriter(new PrintWriter(new FileWriter(nombre,true)));
            for(int i=0; i< naves.length;i++){
                pw.println(naves[i].toString());
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if(pw!=null){
                pw.close();
            }

        }
    }


    /**
     * TODO: Genera una lista de naves a partir del fichero CSV, usando el argumento como capacidad máxima de la lista
     * @param fichero
     * @param capacidad
     * @return
     */
    public static ListaNaves leerNavesCsv(String fichero, int capacidad) {
        ListaNaves listaNaves = new ListaNaves(capacidad);
        Scanner sc = null;
        try {

        } catch (Exception e) {
            return null;
        } finally {

        }
        return listaNaves;
    }
}
