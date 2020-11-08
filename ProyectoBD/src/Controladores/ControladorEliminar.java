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
        opciones = manejador.ListarHab(tabla); 
        try {
            ResultSetMetaData rsMetaData = opciones.getMetaData();
            if(!tabla.contains("_")){
                
                pantallaEliminar.setLabel((rsMetaData.getColumnName(1)).toString());
                while (opciones.next()) {

                    String data=opciones.getObject(rsMetaData.getColumnName(1)).toString();
                    agregarElemento(data);
                }
            }
            else{
                
                pantallaEliminar.setLabel((rsMetaData.getColumnName(1)).toString()+" "+(rsMetaData.getColumnName(2)).toString());
                while (opciones.next()) {

                    String data=opciones.getObject(rsMetaData.getColumnName(1)).toString();
                    
                    String data2=opciones.getObject(rsMetaData.getColumnName(2)).toString();
                    agregarElemento(data +" "+ data2);
                }
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
