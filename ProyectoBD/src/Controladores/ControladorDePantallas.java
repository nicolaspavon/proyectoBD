/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Pantallas.Aplicaciones;

/**
 *
 * @author Vale
 */
public class ControladorDePantallas {
    private Aplicaciones pantallaApp;
    private ControladorDeSesion contrSesion;
    private ControladorAplicaciones contrAplicaciones;
    private ControladorMenues contrMenues;
    
    public ControladorDePantallas(){
        contrSesion = new ControladorDeSesion(this);
        contrAplicaciones = new ControladorAplicaciones(this);
        contrMenues = new ControladorMenues(this);
        contrSesion.activarLogin();
    }
    
    public void activarAplicaciones(String user){
        contrAplicaciones.activarAplicaciones(user);
    }
    
    public void activarMenues(String app){
        contrMenues.activarMenues(contrSesion.getUser(), app);
    }
    

    

}
