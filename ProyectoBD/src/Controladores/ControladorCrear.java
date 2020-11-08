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
    private ControladorAutorizaciones contrAutorizaciones;
    private Map objeto;
    
    public ControladorCrear(ControladorDePantallas contrPantalla, ControladorAutorizaciones contrAut){
        contrPantallas = contrPantalla;
        contrAutorizaciones = contrAut;
    }

    void activarCrear(Map objeto) {
        this.objeto = objeto;
        ArrayList<String> tabla = traerTabla(objeto.get("nombretabla").toString());
        tabla.remove("habilitado");
        tabla.remove("id");
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
        String idObjeto = datos.split(",")[0].replaceAll("'", "");
        System.out.println(datos);
        this.contrAutorizaciones.generarAutorizacion(this.objeto, datos, idObjeto);
        this.volverAtras();
    }
    
    
}
