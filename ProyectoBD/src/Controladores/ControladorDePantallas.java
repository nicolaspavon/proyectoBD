/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Pantallas.Aplicaciones;
import Pantallas.Login;

/**
 *
 * @author Vale
 */
public class ControladorDePantallas {
    private Login login;
    private Aplicaciones pantallaApp;
    private ControladorDeSesion contrSesion;
    private ControladorAplicaciones contrAplicaciones;
    
    public ControladorDePantallas(Aplicaciones app, Login log){
        contrSesion = new ControladorDeSesion();
        contrAplicaciones = new ControladorAplicaciones();
        login = log;
        pantallaApp = app;
        
        init();
        
    }
    
    public void init(){
        this.activarLogin();
        
        login.getIngresarButton().addActionListener(e -> contrSesion.connect(this, login.getUser(),login.getPass()));
        
        pantallaApp.getIngresarButton().addActionListener(e -> contrAplicaciones.ingresarAppSeleccionada());
    }
    
    public void activarLogin(){
        login.setVisible(true);
    }
    public void desactivarLogin(){
        login.setVisible(false);
    }
    public void mensajeErrorConexion(){
        login.datosIncorrectos();
    }
    public void mensajeErrorUsuario(){
        login.errorUsuario();
    }
    
    public void activarAplicaciones(String user){
        pantallaApp.setVisible(true);
        contrAplicaciones.obtenerApps(user, this);
    }
    public void desactivarAplicaciones(){
        pantallaApp.setVisible(false);
    }
    public void agregarElemento(String item){
        pantallaApp.agregarItem(item);
    }
}
