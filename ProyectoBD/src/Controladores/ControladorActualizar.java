/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import Pantallas.Update;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Vale
 */
public class ControladorActualizar {
    private Update pantallaActualizar;
    private ControladorDePantallas contrPantallas;
    private ResultSet opciones;
    private ControladorAutorizaciones contrAut;
    private Map<String, String> tabla; 
    
    public ControladorActualizar(ControladorDePantallas contrPantalla){
        contrPantallas = contrPantalla;
        pantallaActualizar = new Update();
        pantallaActualizar.getSeleccionarButton().addActionListener(e -> ingresarActualizar(pantallaActualizar.getActualizar()));
        pantallaActualizar.getAtrasButton().addActionListener(e -> volverAtras());
    }

    void activarActualizar(Map<String, String> opcion) {
        tabla = opcion;
        pantallaActualizar.setVisible(true);
        obtenerOpciones(opcion.get("nombretabla"));
    }
    
    public void obtenerOpciones(String tabla){
        DBHandler manejador = new DBHandler();
        pantallaActualizar.vaciarItems();
        
        opciones = manejador.ListarHab(tabla); // usar prim elemento
        try {
            ResultSetMetaData rsMetaData = opciones.getMetaData();
            while (opciones.next()) {
                String data=opciones.getObject(rsMetaData.getColumnName(1)).toString();
                agregarElemento(data);
            }
        } catch (SQLException ex) {
        }
        
        opciones = manejador.ListarHab(tabla);
    }
    
    public void volverAtras(){
        pantallaActualizar.setVisible(false);
        pantallaActualizar.vaciarItems();
        contrPantallas.activarFuncionalidades();
    }
    
    public void agregarElemento(String item){
        pantallaActualizar.agregarItem(item);
    }
    
    public void ingresarActualizar(String opcion){
        DBHandler manejador = new DBHandler();
        ArrayList<String> columnas= manejador.GetTabla(tabla.get("nombretabla").toString());
        try {
            ResultSetMetaData rsMetaData = opciones.getMetaData();
            while (opciones.next()) {
                String data=opciones.getObject(rsMetaData.getColumnName(1)).toString();
                if(data.equals(opcion)){
               //     ResultSet elegido = manejador.Listar(tabla,)
                    pantallaActualizar.setVisible(false);
                    contrPantallas.activarActualizacion(data, tabla, columnas.get(0));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
//        ArrayList<Map> op = manejador.Imprimir(opciones);
//        for (Map m : op){
//            if(m.containsKey(opcion)){
//                
//                pantallaActualizar.setVisible(false);
//                
//                contrPantallas.activarActualizacion(m);
//            }
////            if(m.get("menu_id").toString().equals(menuElegido)){
////                pantallaMenues.setVisible(false);
////                contrPantallas.activarFuncionalidades(menuElegido);
////
////            }
//        }
    }
}
