/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lore
 */
public class CrearBase {
    
    public void  Creador(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://192.168.56.7:5432/prueba_datos",
                    "postgres", "hola1234");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql ="";
            
            this.Aplicacion(sql);
            stmt.executeUpdate(sql);
            
            this.Menu(sql);
            stmt.executeUpdate(sql);
            
            this.Rol(sql);
            stmt.executeUpdate(sql);
            
            this.Funcionalidad(sql);
            stmt.executeUpdate(sql);
            
            this.Aplicacion_Menu(sql);
            stmt.executeUpdate(sql);
            
            this.Menu_Funcionalidad(sql);
            stmt.executeUpdate(sql);
            
            this.Menu_Rol(sql);
            stmt.executeUpdate(sql);
            
            this.Persona(sql);
            stmt.executeUpdate(sql);
            
            this.Usuario(sql);
            stmt.executeUpdate(sql);
            
            this.Administrador_General(sql);
            stmt.executeUpdate(sql);
    
            this.Administrador_Seguridad(sql);
            stmt.executeUpdate(sql);
            
            this.Administrador_Auditoria(sql);
            stmt.executeUpdate(sql);
            
            this.Autorizacion(sql);
            stmt.executeUpdate(sql);
            
            
        } catch (Exception e) {
            System.out.println("Tabla1 ya creada ");
        }
    }
    private void Aplicacion(String base){
        base = "CREATE TABLE Aplicacion (Aplicacion_ID serial PRIMARY KEY); ";
    }  
    private void Menu(String base){
        base= "CREATE TABLE Menu ( "
                +"Menu_ID serial PRIMARY KEY ); ";
    }
    private void Rol(String base){
        base= "CREATE TABLE Rol (Rol_ID serial PRIMARY KEY); ";
    }
    private void Funcionalidad(String base){
        base="CREATE TABLE Funcionalidad ( "
            + "Funcionalidad_ID serial PRIMARY KEY, "
            + "Rol_ID_Autorizador serial, "
            + "Descripcion varchar, "
            + "FOREIGN KEY(Rol_ID_Autorizador) REFERENCES Rol(Rol_ID)); ";
    }
    
    private void Aplicacion_Menu (String base){
        base ="CREATE TABLE Aplicacion_Menu ("
            + "Menu_ID serial, "
            + "Aplicacion serial, "
            + "CONSTRAINT PK_Menu_Funcionalidad PRIMARY KEY( "
            + "Menu_ID, "
            + "Funcionalidad_ID), "
            + "FOREIGN KEY(Menu_ID) REFERENCES Menu(Menu_ID), "
            + "FOREIGN KEY(Aplicacion_ID) REFERENCES Aplicacion(Aplicacion_ID)); "; 
    }
    private void Menu_Funcionalidad (String base){
        base = "CREATE TABLE Menu_Funcionalidad ( "
            + "Menu_ID serial, "
            + "Funcionalidad_ID serial, "
            + "CONSTRAINT PK_Menu_Funcionalidad PRIMARY KEY( "
            + "Menu_ID, "
            + "Funcionalidad_ID), "
            + "FOREIGN KEY(Menu_ID) REFERENCES Menu(Menu_ID), "
            + "FOREIGN KEY(Funcionalidad_ID) REFERENCES Funcionalidad(Funcionalidad_ID));" ;
    }
    private void Menu_Rol (String base){
        base = "CREATETABLE Menu_Rol ( "
            + "Rol_ID serial, "
            + "Menu_ID serial, "
            + "CONSTRAINT PK_Menu_Rol PRIMARY KEY( " 
            + "Menu_ID, "
            + "Rol_ID), "
            + "FOREIGN KEY(Menu_ID) REFERENCES Menu(Menu_ID), "
            + "FOREIGN KEY(Rol_ID) REFERENCES Rol(Rol_ID));"; 
    }
    


    private void Persona (String base){
        base="CREATE TABLE Persona ("
            + "Cedula serial PRIMARY KEY,"
            + "Nombre varchar(15), "
            + "Apellido varchar(15), "
            + "Telefono INTEGER, "
            + "Direccion varchar(30));";
    }
    
    private void Usuario (String base){
        base="CREATE TABLE Usuario ( "
            + "Usuario_ID varchar PRIMARY KEY, " 
            + "Contrasena varchar(15), "
            + "Cedula serial, "
            + "FOREIGN KEY(Cedula) REFERENCES Persona(Cedula));";
    }
    
    private void Administrador_General (String base){
        base = "CREATE TABLE Administrador_General ( Rol_ID serial PRIMARY KEY REFERENCES Rol(Rol_ID));";
    }

    private void Administrador_Seguridad (String base){
        base = "CREATE TABLE Administrador_Seguridad(Rol_ID serial PRIMARY KEY REFERENCES Rol(Rol_ID));";
    }
    
    private void Administrador_Auditoria (String base){
        base = "CREATE TABLE Administrador_Auditoria( Rol_ID serial PRIMARY KEY REFERENCES Rol(Rol_ID));";
    }
    
    private void Autorizacion (String base){
        base = "CREATE TABLE Autorizacion ("
            + "Autorizacion_ID serial PRIMARY KEY, "
            + "Usuario_solicitante_ID varchar, "
            + "Usuario_validador_ID varchar, "
            + "Fecha date, "
            + "Referencia_ID varchar, "
            + "Descripcion varchar, "
            + "Funcionalidad_ID serial, "
            + "FOREIGN KEY(Usuario_validador_ID) REFERENCES Usuario(Usuario_ID), "
            + "FOREIGN KEY(Funcionalidad_ID) REFERENCES Funcionalidad(Funcionalidad_ID), "
            + "FOREIGN KEY(Usuario_solicitante_ID)); ";
    }
 
}
