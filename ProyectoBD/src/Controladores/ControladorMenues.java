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
import Pantallas.Menues;

/**
 *
 * @author Vale
 */
public class ControladorMenues {
    private Menues pantallaMenues;
    private ControladorDePantallas contrPantallas;
    public ControladorMenues(ControladorDePantallas contrPantalla) 
    {
       pantallaMenues = new Menues();
       contrPantallas = contrPantalla;
       pantallaMenues.getIngresarButton().addActionListener(e -> ingresarMenuSeleccionado(pantallaMenues.getMenuSeleccionado()));
        
    }
    
    public void obtenerMenues(String user, String aplicacion){
        DBHandler manejador = new DBHandler();
        
        ArrayList<Map> menues = manejador.Imprimir(manejador.ObtenerMenues(user, aplicacion));
        for (Map m : menues){
            System.out.println(m.get("menu_id"));
            agregarElemento((m.get("menu_id")).toString());
        }
    }
    
    public void ingresarMenuSeleccionado(String menu){
        System.out.println("alla vamos");
        System.out.println(menu);
        //System.out.println(pantallaApp.getAppSeleccionada());
        //pantallaApp.setVisible(false);
        
    }
    
    public void activarMenues(String user, String aplicacion){
        pantallaMenues.setVisible(true);
        obtenerMenues(user, aplicacion);
    }
    
    public void desactivarAplicaciones(){
        pantallaMenues.setVisible(false);
    }
    public void agregarElemento(String item){
        pantallaMenues.agregarItem(item);
    }
}
