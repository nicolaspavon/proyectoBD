/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Vale
 */
public class ControladorAplicaciones {
    
    public void obtenerApps(String user, ControladorDePantallas contrPantalla){
        DBHandler manejador = new DBHandler();
        
        ArrayList<Map> apps = manejador.Imprimir(manejador.Listar("usuario_aplicacion", "usuario_id = '"+ user + "'"));
        for (Map m : apps){
            contrPantalla.agregarElemento((m.get("aplicacion_id")).toString());
        }
    }
    
    public void ingresarAppSeleccionada(){
        System.out.println("alla vamos");
        //System.out.println(pantallaApp.getAppSeleccionada());
        //pantallaApp.setVisible(false);
        
    }
}
