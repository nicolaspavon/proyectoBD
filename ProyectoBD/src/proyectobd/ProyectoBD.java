/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd;

import baseDeDatos.DBHandler;
import baseDeDatos.DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectobd.ui.Login;

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
        ControladorDeSesion controlador = new ControladorDeSesion(base, login);
        
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
