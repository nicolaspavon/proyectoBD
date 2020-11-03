/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Pantallas.Aplicaciones;
import java.util.Map;

/**
 *
 * @author Vale
 */
public class ControladorDePantallas {
    private Aplicaciones pantallaApp;
    private ControladorDeSesion contrSesion;
    private ControladorAplicaciones contrAplicaciones;
    private ControladorMenues contrMenues;
    private ControladorFuncionalidades contrFunc;
    private ControladorCrear contrCrear;
    private ControladorListar contrListar;
    
    public ControladorDePantallas(){
        contrSesion = new ControladorDeSesion(this);
        contrAplicaciones = new ControladorAplicaciones(this);
        contrMenues = new ControladorMenues(this);
        contrFunc = new ControladorFuncionalidades(this);
        contrCrear = new ControladorCrear(this);
        contrListar = new ControladorListar(this);
        contrSesion.activarLogin();
    }
    
    public void activarLogin(){
        contrSesion = new ControladorDeSesion(this);
        contrAplicaciones = new ControladorAplicaciones(this);
        contrSesion.activarLogin();
    }
    
    public void activarAplicaciones(String user){
        contrAplicaciones.activarAplicaciones(user);
    }
    
    public void activarMenues(String app){
        contrMenues.activarMenues(contrSesion.getUser(), app);
    }
    
    public void activarFuncionalidades (String menu){
        contrFunc.activarFunc(menu);
    }

    public void activarAplicaciones() {
        contrAplicaciones.activarPantalla();
    }

    public void activarMenus() {
        contrMenues.activarPantalla();
    }
    
    public void activarCrear (Map func){
        contrCrear.activarCrear(func);
    }
    
    public void activarListar(Map<String,String> func){
        contrListar.activarListar(func);
    }

  
}
