/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Lore
 */
public class DataBase {
    public static Connection connection = null;
    

    public Connection getCurrentConnection(String user, String password)
    {
        if(connection != null)
        {
            return connection; 
        }
        else
        {
            try{
                return connection = DriverManager.getConnection("jdbc:postgresql://192.168.56.7:5432/obli2", user, password);
            }catch (SQLException ex) {
                System.out.println("No conexion");
                return null;
            }
        }
    }

    
    public void  CrearDB(){
        
        Statement stmt = null;
        String sql ="";
        try {
            stmt = connection.createStatement();
        } catch (Exception e) {
            System.out.println("No abre bd");
        }
            
        this.Aplicacion();

        this.Menu();

        this.Rol();

        this.Funcionalidad();

        this.Aplicacion_Menu();

        this.Menu_Funcionalidad();

        this.Menu_Rol();

        this.Persona();

        this.Usuario();
       
        this.Rol_Usuario();
        
        this.Usuario_Aplicacion();

        this.Administrador_General();

        this.Administrador_Seguridad();

        this.Administrador_Auditoria();

        this.Autorizacion();
        
        this.User_Menu_View();
    }
    
    private void Aplicacion(){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Aplicacion (Aplicacion_ID serial PRIMARY KEY, "
                    + "Nombre varchar, "
                    + "Descripcion varchar, "
                    + "Habilitado boolean); ");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Aplicacion");
                System.out.println(e);
            }
        }
    }
    
    private void Menu(){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Menu ( "
                + "Menu_ID serial PRIMARY KEY, "
                + "Nombre varchar, "
                + "Descripcion varchar, "
                + "Habilitado boolean ); ");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Menu");
                System.out.println(e);
            }
        }
    }
    
    private void Rol(){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Rol (Rol_ID serial PRIMARY KEY, "
                + "Nombre varchar, "
                + "Descripcion varchar, "
                + "Habilitado boolean ); ");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Rol");
                System.out.println(e);
            }
        }
    }
    
    private void Funcionalidad(){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Funcionalidad ( "
                + "Funcionalidad_ID serial PRIMARY KEY, "
                + "Rol_ID_Autorizador serial, "
                + "Nombre varchar, "
                + "Descripcion varchar, "
                + "Habilitado boolean, "
                + "NombreTabla varchar, "
                + "Tipo varchar, "    
                + "FOREIGN KEY(Rol_ID_Autorizador) REFERENCES Rol(Rol_ID)); ");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Funcionalidad");
                System.out.println(e);
            }
        }
    }
    
    private void Aplicacion_Menu (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Aplicacion_Menu ("
                + "Menu_ID serial, "
                + "Aplicacion_ID serial, "
                + "CONSTRAINT PK_Menu_Aplicacion PRIMARY KEY( "
                + "Menu_ID, "
                + "Aplicacion_ID), "
                + "FOREIGN KEY(Menu_ID) REFERENCES Menu(Menu_ID) ON DELETE CASCADE, "
                + "Habilitado boolean, "    
                + "FOREIGN KEY(Aplicacion_ID) REFERENCES Aplicacion(Aplicacion_ID) ON DELETE CASCADE); "); 
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Aplicacion_Menu");
                System.out.println(e);
            }
        }
    }
    
    private void Menu_Funcionalidad (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Menu_Funcionalidad ( "
                + "Menu_ID serial, "
                + "Funcionalidad_ID serial, "
                + "CONSTRAINT PK_Menu_Funcionalidad PRIMARY KEY( "
                + "Menu_ID, "
                + "Funcionalidad_ID), "
                + "Habilitado boolean, "
                + "FOREIGN KEY(Menu_ID) REFERENCES Menu(Menu_ID) ON DELETE CASCADE, "
                + "FOREIGN KEY(Funcionalidad_ID) REFERENCES Funcionalidad(Funcionalidad_ID) ON DELETE CASCADE);" );
        } catch (Exception e) {
            if (e.getMessage().contains("already exists")){
                System.out.println("Menu_Funcionalidad");
                System.out.println(e);
            }
        }
    }
    
    private void Menu_Rol (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Menu_Rol ( "
                + "Rol_ID serial, "
                + "Menu_ID serial, "
                + "CONSTRAINT PK_Menu_Rol PRIMARY KEY( " 
                + "Menu_ID, "
                + "Rol_ID), "                    
                + "Habilitado boolean, "
                + "FOREIGN KEY(Menu_ID) REFERENCES Menu(Menu_ID) ON DELETE CASCADE, "
                + "FOREIGN KEY(Rol_ID) REFERENCES Rol(Rol_ID) ON DELETE CASCADE);"); 
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Menu_Rol");
                System.out.println(e);
            }
        }
    }
    
    private void Persona (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Persona ("
                + "Cedula serial PRIMARY KEY,"
                + "Nombre varchar, "
                + "Apellido varchar, "
                + "Telefono INTEGER, "
                + "Direccion varchar, "
                + "Habilitado boolean);");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Persona");
                System.out.println(e);
            }
        }
    }
    
    private void Usuario (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Usuario ( "
                + "Usuario_ID varchar PRIMARY KEY, " 
                + "Contrasena varchar, "
                + "Cedula serial, "
                + "Habilitado boolean, "
                + "FOREIGN KEY(Cedula) REFERENCES Persona(Cedula));");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Usuario");
                System.out.println(e);
            }
        }
    }
    
    private void Rol_Usuario (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Rol_Usuario ( "
                + "Usuario_ID varchar, "
                + "Rol_ID serial, " 
                + "CONSTRAINT PK_Rol_Usuario PRIMARY KEY( " 
                + "Usuario_ID, "
                + "Rol_ID), "
                + "Habilitado boolean, "
                + "FOREIGN KEY(Usuario_ID) REFERENCES Usuario(Usuario_ID) ON DELETE CASCADE, "
                + "FOREIGN KEY(Rol_ID) REFERENCES Rol(Rol_ID ON DELETE CASCADE));"); 
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Rol_Usuario");
                System.out.println(e);
            }
        }
    }
    
    private void Usuario_Aplicacion (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Usuario_Aplicacion ( "
                + "Usuario_ID varchar, "
                + "Aplicacion_ID serial, " 
                + "CONSTRAINT PK_Usuario_Aplicacion PRIMARY KEY( " 
                + "Usuario_ID, "
                + "Aplicacion_ID), "
                + "Habilitado boolean, "
                + "FOREIGN KEY(Usuario_ID) REFERENCES Usuario(Usuario_ID) ON DELETE CASCADE, "
                + "FOREIGN KEY(Aplicacion_ID) REFERENCES Aplicacion(Aplicacion_ID) ON DELETE CASCADE);"); 
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Usuario_Aplicacion");
                System.out.println(e);
            }
        }
    }

    private void Administrador_General (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Administrador_General ( Rol_ID serial PRIMARY KEY REFERENCES Rol(Rol_ID));");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Administrador_General");
                System.out.println(e);
            }
        }
    }

    private void Administrador_Seguridad (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Administrador_Seguridad(Rol_ID serial PRIMARY KEY REFERENCES Rol(Rol_ID));");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Administrador_Seguridad");
                System.out.println(e);
            }
        }
    }
    
    private void Administrador_Auditoria (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Administrador_Auditoria( Rol_ID serial PRIMARY KEY REFERENCES Rol(Rol_ID));");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Administrador_Auditoria");
                System.out.println(e);
            }
        }
    }
    
    private void Autorizacion (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Autorizacion ("
                + "Autorizacion_ID serial PRIMARY KEY, "
                + "Usuario_solicitante_ID varchar, "
                + "Usuario_validador_ID varchar, "
                + "Fecha date, "
                + "Referencia_ID varchar, "
                + "Descripcion varchar, "
                + "Funcionalidad_ID serial, "
                + "Estado varchar, "
                + "FOREIGN KEY(Usuario_validador_ID) REFERENCES Usuario(Usuario_ID), "
                + "FOREIGN KEY(Funcionalidad_ID) REFERENCES Funcionalidad(Funcionalidad_ID), "
                + "FOREIGN KEY(Usuario_solicitante_ID) REFERENCES Usuario(Usuario_ID)); ");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Autorizacion");
                System.out.println(e);
            }
        }
    }
    
    private void User_Menu_View(){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE VIEW user_menu_view" +
                "AS SELECT aplicacion_id FROM " +
                "INTER menu m, usuario u, rol r, aplicacion a " +
                "WHERE a.cust_code=b.cust_code" +
                "AND a.agent_code=c.agent_code;");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Aplicacion");
                System.out.println(e);
            }
        }
    }
 
}
