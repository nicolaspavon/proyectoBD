/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import java.util.ArrayList;
import java.util.Map;
import Pantallas.Aplicaciones;

/**
 *
 * @author Vale
 */
public class ControladorAplicaciones {
    private Aplicaciones pantallaAplicaciones;
    private ControladorDePantallas contrPantallas;
    public ControladorAplicaciones(ControladorDePantallas contrPantalla) 
    {
       pantallaAplicaciones = new Aplicaciones();
       contrPantallas = contrPantalla;
       pantallaAplicaciones.getSeleccionarAppButton().addActionListener(e -> ingresarAppSeleccionada(pantallaAplicaciones.getAppSeleccionada()));
        
    }
    
    public void obtenerApps(String user){
        DBHandler manejador = new DBHandler();
        ArrayList<Map> apps = manejador.Imprimir(manejador.Listar("usuario_aplicacion", "usuario_id = '"+ user + "'"));
        for (Map m : apps){
            Map app = manejador.PrimerElemento(manejador.Listar("aplicacion", "aplicacion_id = '"+(m.get("aplicacion_id")).toString() + "'",true));
            agregarElemento((app.get("aplicacion_id")).toString()+" "+(app.get("nombre")).toString() );
        }
    }
    
    public void ingresarAppSeleccionada(String app){
        String[] aplicacion= app.split(" ");
        contrPantallas.activarMenues(aplicacion[0]);
        pantallaAplicaciones.setVisible(false);
        
    }
    
    public void activarAplicaciones(String user){
        pantallaAplicaciones.setVisible(true);
        obtenerApps(user);
    }
    public void desactivarAplicaciones(){
        pantallaAplicaciones.setVisible(false);
    }
    public void agregarElemento(String item){
        pantallaAplicaciones.agregarItem(item);
    }
}
