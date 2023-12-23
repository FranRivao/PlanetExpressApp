/**
 * Description of the class
 *
 * @author  Francisco Manuel Rivao
 * @author  Alejandro Sanchez Millan
 * @version     1.0
 */
public class PuertoEspacial {

    private String nombre;
    private String codigo;
    private double radio;
    private double azimut;
    private double polar;
    private int numMuelles;

    /**
     * Constructor of the class
     *
     * @param nombre nombre del puerto
     * @param codigo codigo del puerto
     * @param radio coordenada radio
     * @param azimut coordenada azimut
     * @param polar coordenado polar
     * @param numMuelles cantidad de muelles
     */
    public PuertoEspacial(String nombre, String codigo, double radio, double azimut, double polar, int numMuelles) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.radio = radio;
        this.azimut = azimut;
        this.polar = polar;
        this.numMuelles = numMuelles;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCodigo() {
        return codigo;
    }
    public double getRadio() {
        return radio;
    }
    public double getAzimut() {
        return azimut;
    }
    public double getPolar() {
        return polar;
    }
    public int getMuelles() {
        return numMuelles;
    }
    public String getIniciales() {

            String iniciales = "";
            String [] array = nombre.split(" ");
            for(int i = 0; i < array.length; i++){
                iniciales = iniciales + array[i].charAt(0);
            }
            return iniciales;

    }
    /**
     * TODO: Método para calcular la distancia entre el puerto espacial que recibe el mensaje y el puerto
     *  espacial "destino" siguiendo las ecuaciones del enunciado (Las formulas se encuentran en el enunciado)
     * @param destino objeto puerto espacial destino
     * @return distancia entre puertos
     */
    public double distancia(PuertoEspacial destino) {
        // TODO: Para calcular la distancia entre dos Puertos Espaciales, se transforman sus coordenadas esféricas a cartesianas
        // TODO: Una vez se tienen las coordenadas cartesianas, basta con calcular la distancia euclídea entre ellas:
        double x = Math.pow(destino.getRadio()-radio, 2), y = Math.pow(destino.getAzimut()-azimut, 2), z = Math.pow(destino.getPolar()-polar, 2);
        return Math.sqrt(x+y+z);
    }

    /**
     * TODO: Método que crea un String con los datos de un puerto espacial con el siguiente formato:
     * @return ejemplo -> "Gaia Galactic Terminal(GGT), en (1.0 90.0 0.0), con 8 muelles" (Radio, Azimut, Polar)
     */
    public String toString() {
        return nombre + " (" + getIniciales() + "), en (" + radio + " " + azimut + " " + polar + "), con " + numMuelles + " muelles";
    }

    /**
     * TODO: Método que crea un String con los datos de un aeropuerto con el siguiente formato:
     * @return ejemplo -> "Gaia Galactic Terminal (GGT)"
     */
    public String toStringSimple() {
        return nombre + " (" + getIniciales() + ")";
    }
}
