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
    private Map objeto;
    private String tabla;
    private String columna;
    
    public ControladorEliminar(ControladorDePantallas contrPantalla){
        contrPantallas = contrPantalla;
        pantallaEliminar = new Eliminar();
        pantallaEliminar.getSeleccionarButton().addActionListener(e -> eliminarObjeto(pantallaEliminar.getEliminar()));
        pantallaEliminar.getAtrasButton().addActionListener(e -> volverAtras());
        
    }

    void activarEliminar(String tabla) {
        pantallaEliminar.setVisible(true);
        this.tabla=tabla;
        obtenerOpciones(tabla);
    }
    
    public void obtenerOpciones(String tabla){
        DBHandler manejador = new DBHandler();
        opciones = manejador.Listar(tabla); // usar prim elemento
        try {
            ResultSetMetaData rsMetaData = opciones.getMetaData();
            columna = rsMetaData.getColumnName(1);
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
    

    private void eliminarObjeto(String datos) {
        
        DBHandler manejador = new DBHandler();
        String clausula = columna+"="+datos;
        
        manejador.Borrar(tabla, clausula);
        this.volverAtras();
    }
    
    public void agregarElemento(String item){
        pantallaEliminar.agregarItem(item);
    }

    
}
