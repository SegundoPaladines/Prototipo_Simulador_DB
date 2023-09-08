import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Tabla {
    public String nombre;
    public Map<String, String> columnas;
    ArrayList <Objeto> objetos;
    public Tabla(String nombre){
        this.nombre=nombre;
        this.objetos = new ArrayList<Objeto>();
        this.columnas = new HashMap<String, String>();
    }
}