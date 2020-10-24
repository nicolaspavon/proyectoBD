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
import java.util.Scanner;
/**
 *
 * @author Lore
 */
public class DataBase {
    public static Connection connection = null;

    public Connection getCurrentConnection()
    {
        if(connection != null)
        {
            return connection;
        }
        else
        {
            try{
                Scanner teclado = new Scanner(System.in);
                System.out.println("seleccione la configuracion:");
                System.out.println("1: Lore");
                System.out.println("2: Vale");
                System.out.println("3: Nico");
                String seleccion = teclado.nextLine();
                if (seleccion.equals("1")){
                    return connection = DriverManager.getConnection("jdbc:postgresql://192.168.56.7:5432/obli", "postgres", "hola1234");
                } else if (seleccion.equals("2")){
                    return connection = DriverManager.getConnection("jdbc:postgresql://192.168.56.102:5432/postgres", "postgres", "holaquetal");
                } else if (seleccion.equals("3")){
                    return connection = DriverManager.getConnection("jdbc:postgresql://192.168.56.2:5432/obli", "postgres", "qweqweqwe");
                } else {
                    System.out.println(" :( ");
                }
            }catch (SQLException ex) {
                Logger lgr = Logger.getLogger(ProyectoBD.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
            return null;
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
            
        this.Aplicacion(stmt);

        this.Menu(stmt);

        this.Rol(stmt);

        this.Funcionalidad(stmt);

        this.Aplicacion_Menu(stmt);

        this.Menu_Funcionalidad(stmt);

        this.Menu_Rol(stmt);

        this.Persona(stmt);

        this.Usuario(stmt);
        
        this.Rol_Usuario(stmt);
        
        this.Usuario_Aplicacion(stmt);

        this.Administrador_General(stmt);

        this.Administrador_Seguridad(stmt);

        this.Administrador_Auditoria(stmt);

        this.Autorizacion(stmt);
    }
    
    private void Aplicacion(Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Aplicacion (Aplicacion_ID serial PRIMARY KEY); ");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Aplicacion");
                System.out.println(e);
            }
        }
    }
    public static void insertar(String datos, String nombreTabla, Statement stmt){
        try {
            String stringDatos = "INSERT INTO " + nombreTabla + " VALUES " 
                    + "(" + datos + ");";

            stmt.executeUpdate(stringDatos);
            stmt.close();
        }
        catch (Exception e){
            System.out.println("Error en insertar en " + nombreTabla);
            System.out.println(e);
        }
    }
	
    public String obtener(String nombreTabla, Statement stmt){
        try{

        ResultSet rs = stmt.executeQuery("SELECT * FROM "+nombreTabla+";");
        String busqueda ="";
            while (rs.next()) {
                
                int id = rs.getInt("id");
                String name = rs.getString("nombre");
            }
        }
        catch (Exception x){
                
        }
        return "";
    }
    
    private void Menu(Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Menu ( "
                +"Menu_ID serial PRIMARY KEY ); ");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Menu");
                System.out.println(e);
            }
        }
    }
    
    private void Rol(Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Rol (Rol_ID serial PRIMARY KEY); ");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Rol");
                System.out.println(e);
            }
        }
    }
    
    private void Funcionalidad(Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Funcionalidad ( "
            + "Funcionalidad_ID serial PRIMARY KEY, "
            + "Rol_ID_Autorizador serial, "
            + "Descripcion varchar, "
            + "FOREIGN KEY(Rol_ID_Autorizador) REFERENCES Rol(Rol_ID)); ");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Funcionalidad");
                System.out.println(e);
            }
        }
    }
    
    private void Aplicacion_Menu (Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Aplicacion_Menu ("
            + "Menu_ID serial, "
            + "Aplicacion_ID serial, "
            + "CONSTRAINT PK_Menu_Aplicacion PRIMARY KEY( "
            + "Menu_ID, "
            + "Aplicacion_ID), "
            + "FOREIGN KEY(Menu_ID) REFERENCES Menu(Menu_ID), "
            + "FOREIGN KEY(Aplicacion_ID) REFERENCES Aplicacion(Aplicacion_ID)); "); 
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Aplicacion_Menu");
                System.out.println(e);
            }
        }
    }
    
    private void Menu_Funcionalidad (Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Menu_Funcionalidad ( "
            + "Menu_ID serial, "
            + "Funcionalidad_ID serial, "
            + "CONSTRAINT PK_Menu_Funcionalidad PRIMARY KEY( "
            + "Menu_ID, "
            + "Funcionalidad_ID), "
            + "FOREIGN KEY(Menu_ID) REFERENCES Menu(Menu_ID), "
            + "FOREIGN KEY(Funcionalidad_ID) REFERENCES Funcionalidad(Funcionalidad_ID));" );
        } catch (Exception e) {
            if (e.getMessage().contains("already exists")){
                System.out.println("Menu_Funcionalidad");
                System.out.println(e);
            }
        }
    }
    
    private void Menu_Rol (Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Menu_Rol ( "
            + "Rol_ID serial, "
            + "Menu_ID serial, "
            + "CONSTRAINT PK_Menu_Rol PRIMARY KEY( " 
            + "Menu_ID, "
            + "Rol_ID), "
            + "FOREIGN KEY(Menu_ID) REFERENCES Menu(Menu_ID), "
            + "FOREIGN KEY(Rol_ID) REFERENCES Rol(Rol_ID));"); 
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Menu_Rol");
                System.out.println(e);
            }
        }
    }
    
    private void Persona (Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Persona ("
            + "Cedula serial PRIMARY KEY,"
            + "Nombre varchar(15), "
            + "Apellido varchar(15), "
            + "Telefono INTEGER, "
            + "Direccion varchar(30));");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Persona");
                System.out.println(e);
            }
        }
    }
    
    private void Usuario (Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Usuario ( "
            + "Usuario_ID varchar PRIMARY KEY, " 
            + "Contrasena varchar(15), "
            + "Cedula serial, "
            + "FOREIGN KEY(Cedula) REFERENCES Persona(Cedula));");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Usuario");
                System.out.println(e);
            }
        }
    }
    
    private void Rol_Usuario (Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Rol_Usuario ( "
            + "Usuario_ID varchar, "
            + "Rol_ID serial, " 
            + "CONSTRAINT PK_Rol_Usuario PRIMARY KEY( " 
            + "Usuario_ID, "
            + "Rol_ID), "
            + "FOREIGN KEY(Usuario_ID) REFERENCES Usuario(Usuario_ID), "
            + "FOREIGN KEY(Rol_ID) REFERENCES Rol(Rol_ID));"); 
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Rol_Usuario");
                System.out.println(e);
            }
        }
    }
    
    private void Usuario_Aplicacion (Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Usuario_Aplicacion ( "
            + "Usuario_ID varchar, "
            + "Aplicacion_ID serial, " 
            + "CONSTRAINT PK_Usuario_Aplicacion PRIMARY KEY( " 
            + "Usuario_ID, "
            + "Aplicacion_ID), "
            + "FOREIGN KEY(Usuario_ID) REFERENCES Usuario(Usuario_ID), "
            + "FOREIGN KEY(Aplicacion_ID) REFERENCES Aplicacion(Aplicacion_ID));"); 
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Usuario_Aplicacion");
                System.out.println(e);
            }
        }
    }
    private void Administrador_General (Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Administrador_General ( Rol_ID serial PRIMARY KEY REFERENCES Rol(Rol_ID));");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Administrador_General");
                System.out.println(e);
            }
        }
    }

    private void Administrador_Seguridad (Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Administrador_Seguridad(Rol_ID serial PRIMARY KEY REFERENCES Rol(Rol_ID));");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Administrador_Seguridad");
                System.out.println(e);
            }
        }
    }
    
    private void Administrador_Auditoria (Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Administrador_Auditoria( Rol_ID serial PRIMARY KEY REFERENCES Rol(Rol_ID));");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Administrador_Auditoria");
                System.out.println(e);
            }
        }
    }
    
    private void Autorizacion (Statement stmt){
        try {
        stmt.executeUpdate("CREATE TABLE Autorizacion ("
            + "Autorizacion_ID serial PRIMARY KEY, "
            + "Usuario_solicitante_ID varchar, "
            + "Usuario_validador_ID varchar, "
            + "Fecha date, "
            + "Referencia_ID varchar, "
            + "Descripcion varchar, "
            + "Funcionalidad_ID serial, "
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
 
}
