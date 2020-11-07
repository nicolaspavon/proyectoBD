/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        DBHandler manejador = new DBHandler();
        
//        Limpiar y formatear datos para la autorizacion
        String id_funcionalidad = funcionalidad.get("funcionalidad_id").toString();
        String id_usuario = contrSesion.getUser();
        String idObjeto = datos.split(",")[0];
        String datosFormateados = datos.replace(",", "-").replace("'", ":");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
        
//        Crear autorizacion
        String columnas = "usuario_solicitante_id, fecha, referencia_id, query, funcionalidad_id, estado";
        String autorizacion = "'"+id_usuario+"', '" + now +"', "+ idObjeto +", '"  + datosFormateados + "', '" + id_funcionalidad + "', 'pendiente'";
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
}
