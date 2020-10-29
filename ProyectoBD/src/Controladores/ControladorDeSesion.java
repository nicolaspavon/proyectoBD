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
    
    public String Usuario;
    public Login login;
    public Aplicaciones pantallaApp;
    
    public ControladorDeSesion(DataBase base, Login log, Aplicaciones app) 
    {
       login = log;
       login.setVisible(true);
       login.getIngresarButton().addActionListener(e -> connect(base));
       pantallaApp = app;
       pantallaApp.getIngresarButton().addActionListener(e -> ingresarAppSeleccionada());
    }
    
    public void obtenerApps(){
        DBHandler manejador = new DBHandler();
        
        ArrayList<Map> apps = manejador.Imprimir(manejador.Listar("usuario_aplicacion", "usuario_id = '"+ Usuario + "'"));
        for (Map m : apps){
            pantallaApp.agregarItem((m.get("aplicacion_id")).toString());
        }
    }
    
    public void ingresarAppSeleccionada(){
        System.out.println("alla vamos");
        System.out.println(pantallaApp.getAppSeleccionada());
        //pantallaApp.setVisible(false);
        
    }
    
    public void connect(DataBase base){
        
        Connection con = base.getCurrentConnection(login.getUser(), login.getPass());
        
        if (con == null){
            System.out.println("Datos incorrectos");
            login.datosIncorrectos();
        }else{
            
            DBHandler manejador = new DBHandler();
            if(!manejador.Imprimir(manejador.Listar("Usuario", "usuario_id = '"+login.getUser()+"'")).isEmpty()){
                    
                Usuario = manejador.Imprimir(manejador.Listar("Usuario", "usuario_id = '"+login.getUser()+"'")).get(0).get("usuario_id").toString();  
                System.out.println("Ingresado con usuario: " + Usuario);
                login.setVisible(false);
                pantallaApp.setVisible(true);
                obtenerApps();
            }
            else{
                System.out.println("no existe en la tabla Usuario");
                login.errorUsuario();
            }
            
        }
    }
}
