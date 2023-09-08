import java.util.HashMap;
import java.util.Map;
public class Objeto {
    public String nombre;
    public Map<String, String> atributos;
    public Objeto(String nombre){
        this.nombre=nombre;
        this.atributos = new HashMap<String, String>();
    }
}