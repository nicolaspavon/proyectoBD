/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DataBase;
import Pantallas.Aplicaciones;
import Pantallas.Login;

/**
 *
 * @author nikok
 */
public class ProyectoBD {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        DataBase base = new DataBase();
        Login login = new Login();
        Aplicaciones app = new Aplicaciones();
        ControladorDeSesion controlador = new ControladorDeSesion(base, login, app);
        
//        //nueva.Imprimir(nueva.Listar("Usuario", "nombre=Juan"));
//        manejador.Insertar("'este','contrasena',46012394", "Usuario");
//        System.out.println("");
//        manejador.Imprimir(manejador.Listar("Usuario"));
//        
//        System.out.println("");
//        manejador.Actualizar("Usuario", "contrasena = 'cambio12'", "usuario_id = 'este'");
//       // nueva.Borrar("Usuario", "usuario_id = 'este'");
//        
//        manejador.Imprimir(manejador.Listar("Usuario"));

        
        
    }   
}
