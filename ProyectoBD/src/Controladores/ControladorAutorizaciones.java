/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import Pantallas.Crear;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author nikok
 */
public class ControladorAutorizaciones {
    private ControladorDeSesion contrSesion;
    
    public ControladorAutorizaciones(ControladorDeSesion contrSes){
        contrSesion = contrSes;
    }
    
    public void generarAutorizacion(Map funcionalidad, String datos){
        
        activarAut();
        DBHandler manejador = new DBHandler();
        
//        Limpiar y formatear datos para la autorizacion
        String id_funcionalidad = funcionalidad.get("id").toString();
        String rol_validador = funcionalidad.get("rol_id_autorizador").toString();
        String id_usuario = contrSesion.getUser();
        String idObjeto = datos.split(",")[0];
        String datosFormateados = datos.replace(",", "-").replace("'", ":");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
        
//        Crear autorizacion
        String columnas = "usuario_solicitante_id, fecha, referencia_id, datos, funcionalidad_id, rol_validador, estado";
        String autorizacion = "'"+id_usuario+"', '" + now +"', "+ idObjeto +", '"  + datosFormateados + "', '" + id_funcionalidad + "', '"+rol_validador + "', 'pendiente'";
        manejador.Insertar(columnas, autorizacion, "autorizacion");
    }
    
    private void crearObjeto(String datos) {
        DBHandler manejador = new DBHandler();
    }
    
    private void eliminarObjeto(String datos) { 
//        DBHandler manejador = new DBHandler();
//        String clausula = columna+"="+datos;
//        //        this.contrAutorizaciones.generarAutorizacion()
//        manejador.Borrar(tabla, clausula);
//        this.volverAtras();
    }
    
    public void activarAut() {
        
//        ArrayList<String> tabla = traerTabla(objeto.get("nombretabla").toString());
//        tabla.remove("habilitado");
//        tabla.remove("id");
//        System.out.println(tabla);
//        pantallaCrear = new Crear(tabla, objeto.get("nombre").toString());
//        pantallaCrear.getCrear().addActionListener(e -> crearObjeto(pantallaCrear.getDatos()));
//        pantallaCrear.getAtras().addActionListener(e -> volverAtras());
    }
    
    public ArrayList<Map> getAutorizaciones(){
        DBHandler manejador = new DBHandler();
        ArrayList<Map> listaRoles = manejador.Imprimir(manejador.Listar("rol_usuario", " usuario_id = '"+contrSesion.getUser()+"'"));
        ArrayList<Map> listaAutorizaciones = new ArrayList();
        for (Map rol_usuario : listaRoles){
            listaAutorizaciones.addAll(manejador.Imprimir(manejador.Listar("autorizacion", "rol_validador='"+rol_usuario.get("rol_id")+"' AND estado='pendiente'")));
        }
        return listaAutorizaciones;
    }
}
