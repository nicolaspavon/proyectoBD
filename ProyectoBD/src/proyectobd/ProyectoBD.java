/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikok
 */
public class ProyectoBD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DataBase nueva = new DataBase(); 
        Connection con = nueva.getCurrentConnection(); //PA QUE ESTA ACA?
        //nueva.Imprimir(nueva.Listar("Usuario", "nombre=Juan"));
        nueva.Insertar("'este','contrasena',46012394", "Usuario");
        System.out.println("");
        nueva.Imprimir(nueva.Listar("Usuario"));
        
        System.out.println("");
        nueva.Actualizar("Usuario", "contrasena = 'cambio12'", "usuario_id = 'este'");
       // nueva.Borrar("Usuario", "usuario_id = 'este'");
        
        nueva.Imprimir(nueva.Listar("Usuario"));
        
    }
    
}
