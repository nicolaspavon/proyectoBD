/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import Pantallas.Autorizar;
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
    private Autorizar pantallaAutorizaciones;
    private ControladorDePantallas contrPantallas; 
    
    public ControladorAutorizaciones(ControladorDeSesion contrSes, ControladorDePantallas contrPant){
        contrSesion = contrSes;
        contrPantallas = contrPant;
    }
    
    public void generarAutorizacion(Map funcionalidad, String datos, String clausula){
        DBHandler manejador = new DBHandler();
        
//        Limpiar y formatear datos para la autorizacion
        String id_funcionalidad = funcionalidad.get("id").toString();
        String rol_validador = funcionalidad.get("rol_id_autorizador").toString();
        String id_usuario = contrSesion.getUser();
        String clausulaFormateada = clausula.replace(",", "-").replace("'", ":");
        String datosFormateados = datos.replace(",", "-").replace("'", ":");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
        
//        Crear autorizacion
        String columnas = "usuario_solicitante_id, fecha, referencia_id, datos, funcionalidad_id, rol_validador, estado";
        String autorizacion = "'"+id_usuario+"', '" + now +"', '"+ clausulaFormateada +"', '"  + datosFormateados + "', '" + id_funcionalidad + "', '"+rol_validador + "', 'pendiente'";
        Map nuevaAut = manejador.PrimerElemento(manejador.InsertarYDevolverId(columnas, autorizacion, "autorizacion"));
        nuevaAut.get("id");
        
        if(rol_validador.isEmpty()){
            this.autorizar(manejador.PrimerElemento(manejador.Listar("autorizacion", "id="+nuevaAut.get("id"))));
        }
    }
    
    
    private void crearObjeto(String columnas, String datos, String tabla) {
        DBHandler manejador = new DBHandler();
        manejador.Insertar(columnas, datos.replace(":", "'").replace("-", ","), tabla);
    }
    
    private void eliminarObjeto(String tabla, String clausula) { 
        DBHandler manejador = new DBHandler();
        manejador.Borrar(tabla, clausula.replace(":", "'").replace("-", ","));
    }
    
    private void actualizarObjeto(String tabla, String datos, String clausula) { 
        DBHandler manejador = new DBHandler();
        manejador.Actualizar(tabla, datos.replace(":", "'").replace("-", ","), clausula.replace(":", "'").replace("-", ","));
    }
    
    public void activarAut() {
        pantallaAutorizaciones = new Autorizar(this.getAutorizaciones());
        pantallaAutorizaciones.getAutorizar().addActionListener(e -> autorizar(pantallaAutorizaciones.getSeleccionado()));
        pantallaAutorizaciones.getDenegar().addActionListener(e -> denegar(pantallaAutorizaciones.getSeleccionado()));
        pantallaAutorizaciones.getAtras().addActionListener(e -> volverAtras());
    }
    
    public void volverAtras(){
        pantallaAutorizaciones.apagarPantalla();
        contrPantallas.activarFuncionalidades();
    }
    
    public void autorizar(Map autorizacion){
        System.out.println("autorizado!" + autorizacion);
        DBHandler manejador = new DBHandler();
        Map<String, String> Funcionalidad = manejador.PrimerElemento(manejador.Listar("Funcionalidad", "id='" +autorizacion.get("funcionalidad_id").toString()+ "'"));
        if(Funcionalidad.get("tipo").equals("crear")){
            ArrayList<String> tabla = manejador.GetTabla(Funcionalidad.get("nombretabla"));
            tabla.remove("id");
            String datos = autorizacion.get("datos").toString() + "-:true:";
            System.out.println(datos);
            this.crearObjeto(tabla.toString().replace("[", "").replace("]", ""), datos, Funcionalidad.get("nombretabla"));
        }else if (Funcionalidad.get("tipo").equals("eliminar")){
            this.eliminarObjeto(Funcionalidad.get("nombretabla"), autorizacion.get("referencia_id").toString());
        }else if (Funcionalidad.get("tipo").equals("actualizar")){
            String datos = autorizacion.get("datos").toString() + ", habilitado='true'";
            this.actualizarObjeto(Funcionalidad.get("nombretabla"), datos ,autorizacion.get("referencia_id").toString());
        }
        manejador.Actualizar("autorizacion","estado='autorizado', usuario_validador_id='"+contrSesion.getUser()+"'","id="+autorizacion.get("id"));
    }
    
    public void denegar(Map autorizacion){
        System.out.println("denegado!" + autorizacion);
        DBHandler manejador = new DBHandler();
        Map<String, String> Funcionalidad = manejador.PrimerElemento(manejador.Listar("Funcionalidad", "id='" +autorizacion.get("funcionalidad_id").toString()+ "'"));
        manejador.Actualizar("autorizacion","estado='denegado', usuario_validador_id='"+contrSesion.getUser()+"'","id="+autorizacion.get("id"));
        if(!Funcionalidad.get("tipo").toString().equals("crear")){
            manejador.Actualizar(Funcionalidad.get("nombretabla"), "habilitado=true",autorizacion.get("referencia_id").toString());
        }
    }
    
    public ArrayList<Map> getAutorizaciones(){
        DBHandler manejador = new DBHandler();
        ArrayList<Map> listaRoles = manejador.Imprimir(manejador.Listar("rol_usuario", " usuario_id = '"+contrSesion.getUser()+"'"));
        System.out.println(contrSesion.getUser());
        ArrayList<Map> listaAutorizaciones = new ArrayList();
        for (Map rol_usuario : listaRoles){
            listaAutorizaciones.addAll(manejador.Imprimir(manejador.Listar("autorizacion", "rol_validador='"+rol_usuario.get("rol_id")+"' AND estado='pendiente' AND NOT usuario_solicitante_id='" +contrSesion.getUser()+"'" )));
        }
        return listaAutorizaciones;
    }
}
