package Controladores;

import Base.DBHandler;
import Pantallas.Eliminar;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vale
 */
public class ControladorEliminar {
        
    private Eliminar pantallaEliminar;
    private ControladorDePantallas contrPantallas;
    private ResultSet opciones;
    private ControladorAutorizaciones contrAutorizaciones;
    private Map<String, String> funcionalidad;
    
    public ControladorEliminar(ControladorDePantallas contrPantalla, ControladorAutorizaciones contrAut){
        contrPantallas = contrPantalla;
        pantallaEliminar = new Eliminar();
        pantallaEliminar.getSeleccionarButton().addActionListener(e -> contrAutorizaciones.generarAutorizacion(funcionalidad, pantallaEliminar.getEliminar()));
        pantallaEliminar.getAtrasButton().addActionListener(e -> volverAtras());
        contrAutorizaciones = contrAut;
    }

    void activarEliminar(Map<String, String> func) {
        funcionalidad = func;
        pantallaEliminar.setVisible(true);
        obtenerOpciones(func.get("nombretabla"));
    }
    
    public void obtenerOpciones(String tabla){
        DBHandler manejador = new DBHandler();
        opciones = manejador.Listar(tabla); // usar prim elemento
        try {
            ResultSetMetaData rsMetaData = opciones.getMetaData();
            while (opciones.next()) {
                String data=opciones.getObject(rsMetaData.getColumnName(1)).toString();
                agregarElemento(data);
            }
        } catch (SQLException ex) {
        }
    }
    
    public void volverAtras(){
        pantallaEliminar.setVisible(false);
        pantallaEliminar.vaciarItems();
        contrPantallas.activarFuncionalidades();
    }
    
    public void agregarElemento(String item){
        pantallaEliminar.agregarItem(item);
    }

    
}
