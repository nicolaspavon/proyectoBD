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
    private Login login;
    private String appID;
    private ControladorDePantallas contrPantallas;
    
    public ControladorDeSesion(ControladorDePantallas contrPantalla, String nombreAplicacion) 
    {
       appID = nombreAplicacion;
       login = new Login();
       base = new DataBase();
       contrPantallas = contrPantalla;
       login.getIngresarButton().addActionListener(e -> connect(login.getUser(),login.getPass()));
        
    }
    
    public boolean obtenerApps(String user){
        boolean appPermitida = false;
        DBHandler manejador = new DBHandler();
        ArrayList<Map> apps = manejador.Imprimir(manejador.Listar("usuario_aplicacion", "usuario_id = '"+ user + "'"));
        
        for (Map m : apps){
            if(m.get("aplicacion_id").equals(appID)){
                appPermitida = true;
            }
            
        }
        return appPermitida;
    }
    
    public void connect(String user, String pass){
        
        Connection con = base.getCurrentConnection(user, pass);
        
        if (con == null){
            System.out.println("Datos incorrectos");
            this.mensajeErrorConexion();
        }
        else{
            
            DBHandler manejador = new DBHandler();
            if(!manejador.Imprimir(manejador.Listar("Usuario", "usuario_id = '"+user+"'",true)).isEmpty()){
                    
                Usuario = manejador.Imprimir(manejador.Listar("Usuario", "usuario_id = '"+user+"'")).get(0).get("usuario_id").toString();  
                if (obtenerApps(user)){
                    this.desactivarLogin();
                    contrPantallas.activarMenues(appID);
                }
                else{
                    System.out.println("Aplicacion no permitida");
                }

            }
            else{
                System.out.println("no existe en la tabla Usuario");
                base.noConnect();
                this.mensajeErrorUsuario();
            }
            
        }
    }
    
    public String getUser(){
        return this.Usuario;
    }

    public void activarLogin(){
        base.setNullConnection();
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

    void activarPantallaLogin() {
        login.setVisible(true);
    }
}
