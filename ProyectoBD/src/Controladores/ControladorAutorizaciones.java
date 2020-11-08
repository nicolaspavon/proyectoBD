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
    
    public void generarAutorizacion(Map funcionalidad, String datos){
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
        
//      Setear objeto en false
        if (!funcionalidad.get("tipo").toString().equals("crear")){
            String nombTabla = funcionalidad.get("nombretabla").toString();
            // completar
        }
    }
    
    private void crearObjeto(String tabla, String datos) {
        System.out.println(datos);
        DBHandler manejador = new DBHandler();
        manejador.Insertar(tabla, datos.replace(" ", "="));
    }
    
    private void eliminarObjeto(String tabla, String datos) { 
        DBHandler manejador = new DBHandler();
        manejador.Borrar(datos.replace(" ", "="), tabla);
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
            // problema crear, necesito saber los nombres de las columnas que estoy ingresando ayudaaa
            this.crearObjeto(autorizacion.get("datos").toString(), Funcionalidad.get("nombretabla"));
        }else if (Funcionalidad.get("tipo").equals("eliminar")){
            this.eliminarObjeto(autorizacion.get("datos").toString(), Funcionalidad.get("nombretabla"));
        }else if (Funcionalidad.get("tipo").equals("actualizar")){
            // problema actualizar, necesito saber los nombres de las columnas que estoy ingresando ayudaaa
            System.out.println("aiuda");// completar
        }
        System.out.println("actualizar la autorizacion a estado autorizado y ingresar usuario actual en usuario autorizador id");// completar
    }
    
    public void denegar(Map autorizacion){
        System.out.println("denegado!" + autorizacion);
        DBHandler manejador = new DBHandler();
        Map<String, String> Funcionalidad = manejador.PrimerElemento(manejador.Listar("Funcionalidad", "id='" +autorizacion.get("funcionalidad_id").toString()+ "'"));
        System.out.println("ACA SE ACTUALIZA EL CAMPO del objeto a habilitado");// completar
        System.out.println("actualizar la autorizacion a estado denegado y ingresar usuario actual en usuario autorizador id");// completar
    }
    
    public ArrayList<Map> getAutorizaciones(){
        DBHandler manejador = new DBHandler();
        ArrayList<Map> listaRoles = manejador.Imprimir(manejador.Listar("rol_usuario", " usuario_id = '"+contrSesion.getUser()+"'"));
        ArrayList<Map> listaAutorizaciones = new ArrayList();
        for (Map rol_usuario : listaRoles){
            listaAutorizaciones.addAll(manejador.Imprimir(manejador.Listar("autorizacion", "rol_validador='"+rol_usuario.get("rol_id")+"' AND estado='pendiente' AND NOT usuario_solicitante_id='" +contrSesion.getUser()+"'" )));
        }
        System.out.println(listaAutorizaciones);
        return listaAutorizaciones;
    }
}
