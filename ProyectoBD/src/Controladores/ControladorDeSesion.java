/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import Base.DataBase;
import Pantallas.Aplicaciones;
import java.sql.Connection;
import Pantallas.Login;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author nikok
 */
public class ControladorDeSesion 
{
    private String Usuario;
    private DataBase base;
    
    public ControladorDeSesion() 
    {
       base = new DataBase();
    }
    
    public void connect(ControladorDePantallas contrPantallas, String user, String pass){
        Connection con = base.getCurrentConnection(user, pass);
        
        if (con == null){
            System.out.println("Datos incorrectos");
            contrPantallas.mensajeErrorConexion();
        }
        else{
            
            DBHandler manejador = new DBHandler();
            if(!manejador.Imprimir(manejador.Listar("Usuario", "usuario_id = '"+user+"'")).isEmpty()){
                    
                Usuario = manejador.Imprimir(manejador.Listar("Usuario", "usuario_id = '"+user+"'")).get(0).get("usuario_id").toString();  
                System.out.println("Ingresado con usuario: " + Usuario);
                contrPantallas.desactivarLogin();
                contrPantallas.activarAplicaciones(user);
                
            }
            else{
                System.out.println("no existe en la tabla Usuario");
                contrPantallas.mensajeErrorUsuario();
            }
            
        }
    }
}
