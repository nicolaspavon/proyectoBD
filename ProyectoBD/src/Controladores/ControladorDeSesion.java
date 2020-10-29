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

/**
 *
 * @author nikok
 */
public class ControladorDeSesion 
{
    
    public String Usuario;
    
    public ControladorDeSesion(DataBase base, Login login) 
    {
       login.setVisible(true);
       login.getIngresarButton().addActionListener(e -> connect(base, login));    
    }
    
    public void connect(DataBase base, Login login){
        
        Connection con = base.getCurrentConnection(login.getUser(), login.getPass());
        
        if (con == null){
            System.out.println("Datos incorrectos");
            login.datosIncorrectos();
        }else{
            
            DBHandler manejador = new DBHandler();
            if(!manejador.Imprimir(manejador.Listar("Usuario", "usuario_id = '"+login.getUser()+"'")).isEmpty()){
                    
                Usuario = manejador.Imprimir(manejador.Listar("Usuario", "usuario_id = '"+login.getUser()+"'")).get(0).get("usuario_id").toString();  
                System.out.println("Ingresado con usuario: " + Usuario);
            }
            else{
                System.out.println("no existe en la tabla Usuario");
                login.errorUsuario();
            }
            
        }
    }
}
