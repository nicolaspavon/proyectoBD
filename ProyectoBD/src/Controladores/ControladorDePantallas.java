/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.sql.ResultSet;
import java.util.Map;

/**
 *
 * @author Vale
 */
public class ControladorDePantallas {
    private ControladorDeSesion contrSesion;
    private ControladorMenues contrMenues;
    private ControladorFuncionalidades contrFunc;
    private ControladorCrear contrCrear;
    private ControladorAutorizaciones contrAut;
    private ControladorListar contrListar;
    private ControladorEliminar contrEliminar;
    private ControladorCambiarPass contrCambiarPass;
    private ControladorEnDesarrollo contrEnDesarrollo;
    private ControladorAuditoria contrAudi;
    private ControladorActualizar contrActualizar;
    private ControladorActualizacion contrAct;
    private static String app;
    
    public ControladorDePantallas(String aplicacion){
        app = aplicacion;
        contrAudi = new ControladorAuditoria(this);
        contrSesion = new ControladorDeSesion(this,app);
        contrAut = new ControladorAutorizaciones(contrSesion, this);
        contrMenues = new ControladorMenues(this);
        contrFunc = new ControladorFuncionalidades(this);
        contrCrear = new ControladorCrear(this, contrAut);
        contrListar = new ControladorListar(this, contrAut);
        contrEliminar = new ControladorEliminar(this, contrAut);
        contrSesion.activarPantallaLogin();
        contrEnDesarrollo = new ControladorEnDesarrollo(this);
        contrActualizar = new ControladorActualizar(this);
        contrAct = new ControladorActualizacion(this,contrAut);
    }
    
    public void activarLogin(){
       // contrSesion = new ControladorDeSesion(this,app);
        contrSesion.activarLogin();
    }
    
    public void activarMenues(String app){
        contrMenues.activarMenues(contrSesion.getUser(), app);
    }
    
    public void activarFuncionalidades (String menu){
        contrFunc.activarFunc(menu);
    }

    public void activarMenus() { //para boton atras
        contrMenues.activarPantalla();
    }
    
    public void activarCrear (Map func){
        contrCrear.activarCrear(func);
    }
    public void activarEliminar (Map func){
        contrEliminar.activarEliminar(func);
    }
    
    public void activarListar(Map<String,String> func){
        contrListar.activarListar(func);
    }

    void activarFuncionalidades() { //para volver atras
        contrFunc.activarFunc();
    }
    
    void activarAutorizaciones() {
        contrAut.activarAut();
    }

    void activarCambiarPass() {
        contrCambiarPass =  new ControladorCambiarPass(this, contrSesion.getUser());
        contrCambiarPass.activarCambiarPass(contrSesion.getUser());
        
    }
    
    void activarEnDesarrollo(){
        contrEnDesarrollo.activarEnProceso();
    }

    void activarAudi() {
        contrAudi.activarPantalla(contrSesion.getUser());
    }

    void activarActualizar(Map func) {
        contrActualizar.activarActualizar(func);
    }

    void activarActualizacion(String opcion, Map data, String func) {
        contrAct.activarActualizacion(opcion, data, func);
    }

}
