package Controladores;

import Base.DBHandler;
import Pantallas.Crear;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Vale
 */
public class ControladorCrear {
        
    private Crear pantallaCrear;
    private ControladorDePantallas contrPantallas;
    private Map objeto;
    
    public ControladorCrear(ControladorDePantallas contrPantalla){
        contrPantallas = contrPantalla;
    }

    void activarCrear(Map objeto) {
        this.objeto = objeto;
        ArrayList<String> tabla = traerTabla(objeto.get("nombretabla").toString());
        System.out.println(tabla);
        pantallaCrear = new Crear(tabla, objeto.get("nombre").toString());
        pantallaCrear.getCrear().addActionListener(e -> crearObjeto(pantallaCrear.getDatos()));
        pantallaCrear.getAtras().addActionListener(e -> volverAtras());
    }
    
    public void volverAtras(){
        pantallaCrear.apagarPantalla();
        objeto.clear();
        contrPantallas.activarFuncionalidades();
    }
    
    private ArrayList<String> traerTabla(String nombreTabla) {
        DBHandler manejador = new DBHandler();
        return manejador.GetTabla(nombreTabla);
    }

    private void crearObjeto(String datos) {
        DBHandler manejador = new DBHandler();
        manejador.Insertar(datos, objeto.get("nombretabla").toString());
    }
    
    
}
