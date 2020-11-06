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
    private ArrayList<Map> listaMenues;
    public ControladorMenues(ControladorDePantallas contrPantalla) 
    {
       pantallaMenues = new Menues();
       contrPantallas = contrPantalla;
       pantallaMenues.getIngresarButton().addActionListener(e -> ingresarMenuSeleccionado(pantallaMenues.getMenuSeleccionado()));
       pantallaMenues.getAtrasButton().addActionListener(e -> volverLogin());
    }
    
    public void volverLogin(){
        pantallaMenues.setVisible(false);
        pantallaMenues.vaciarMenus();
        listaMenues.clear();
        contrPantallas.activarLogin();
    }
    
    
    public void obtenerMenues(String user, String aplicacion){
        DBHandler manejador = new DBHandler();        
        listaMenues = manejador.Imprimir(manejador.ObtenerMenues(user, aplicacion));
        for (Map m : listaMenues){
            
            agregarElemento((m.get("menu_id")).toString()+" "+(m.get("nombre")).toString());
        }
    }
    
    public void ingresarMenuSeleccionado(String menu){
        String menuElegido = menu.split(" ")[0];
        for (Map m : listaMenues){
            if(m.get("menu_id").toString().equals(menuElegido)){
                System.out.println(m.get("menu_id"));
                System.out.println(m.get("nombre"));
                System.out.println(m.get("descripcion"));
                
                pantallaMenues.setVisible(false);
                contrPantallas.activarFuncionalidades(menuElegido);
            }
        }
        pantallaMenues.setVisible(false);
        
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

    void activarPantalla() {
        pantallaMenues.setVisible(true);
    }
}
