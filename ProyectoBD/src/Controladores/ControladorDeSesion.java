/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import Base.DataBase;
import java.sql.Connection;
import Pantallas.Login;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikok
 */
public class ControladorDeSesion 
{
    private String Usuario;
    private DataBase base;
    private Login login;
    private ControladorDePantallas contrPantallas;
    
    public ControladorDeSesion(ControladorDePantallas contrPantalla) 
    {
       login = new Login();
       base = new DataBase();
       contrPantallas = contrPantalla;
       login.getIngresarButton().addActionListener(e -> connect(login.getUser(),login.getPass()));
        
    }
    
    public void connect(String user, String pass){
        Connection con = base.getCurrentConnection(user, pass);
        
        if (con == null){
            System.out.println("Datos incorrectos");
            this.mensajeErrorConexion();
        }
        else{
            
            DBHandler manejador = new DBHandler();
            System.out.println(user);
            if(!manejador.Imprimir(manejador.Listar("Usuario", "usuario_id = '"+user+"'")).isEmpty()){
                    
                Usuario = manejador.Imprimir(manejador.Listar("Usuario", "usuario_id = '"+user+"'")).get(0).get("usuario_id").toString();  
                System.out.println("Ingresado con usuario: " + Usuario);
                this.desactivarLogin();
                contrPantallas.activarAplicaciones(user);
                
            }
            else{
                System.out.println("no existe en la tabla Usuario");
                this.mensajeErrorUsuario();
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.out.println("No puedo cerrar");
                }
            }
            
        }
    }
    
    public String getUser(){
        return this.Usuario;
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
}
