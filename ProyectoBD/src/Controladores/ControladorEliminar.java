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
    private String nombreTabla;
    
    public ControladorEliminar(ControladorDePantallas contrPantalla, ControladorAutorizaciones contrAut){
        contrAutorizaciones = contrAut;
        contrPantallas = contrPantalla;
        pantallaEliminar = new Eliminar();
        pantallaEliminar.getSeleccionarButton().addActionListener(e -> enviarAutorizacion());
        pantallaEliminar.getAtrasButton().addActionListener(e -> volverAtras());
    }

    void activarEliminar(Map<String, String> func) {
        funcionalidad = func;
        pantallaEliminar.setVisible(true);
        nombreTabla=func.get("nombretabla");
        obtenerOpciones(nombreTabla);
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
    
    public void enviarAutorizacion(){
        DBHandler manejador = new DBHandler();
        String data = pantallaEliminar.getEliminar();
        String[] datos = data.split(" ");
        try {
            ResultSetMetaData rsMetaData = opciones.getMetaData();
            datos[0]= rsMetaData.getColumnName(1).toString()+"="+datos[0];
            data = datos[0];
            if(nombreTabla.contains("_")){
                datos[1]= rsMetaData.getColumnName(2).toString()+"="+datos[1];
                data = data + " AND "+datos[1];
            }
        } catch (SQLException ex) {
        }
        manejador.Actualizar(nombreTabla,"habilitado=false",data );
        String datosVacios = "";
        contrAutorizaciones.generarAutorizacion(funcionalidad, datosVacios, data);
        volverAtras();
    }
    
}
