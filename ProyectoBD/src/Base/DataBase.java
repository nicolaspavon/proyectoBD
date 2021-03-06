/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    
    public void setNullConnection(){
        try {
            connection.close();
            connection = null;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getCurrentConnection(String user, String password)
    {
        if(connection != null)
        {
            return connection; 
        }
        else
        {
            try{
                return connection = DriverManager.getConnection("jdbc:postgresql://192.168.56.7:5432/obli", user, password);
            }catch (SQLException ex) {
                System.out.println("No conexion");
                return null;
            }
        }
    }
    
    public void noConnect(){
            try {
            connection.close();
            connection = null;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
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
        
        this.TriggerRol();

        this.Funcionalidad();

        this.Aplicacion_Menu();

        this.Menu_Funcionalidad();

        this.Menu_Rol();

        this.Persona();

        this.Usuario();
        
        this.TriggerUsuario();
       
        this.Rol_Usuario();
        
        this.TriggerRol_Usuario();
        
        this.Usuario_Aplicacion();

        this.Autorizacion();
        
        this.Mesas();
        
        this.Stock();
        
    }
    
    private void Aplicacion(){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Aplicacion (id SERIAL PRIMARY KEY, "
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
                + "id SERIAL PRIMARY KEY, "
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
            stmt.executeUpdate("CREATE TABLE Rol (Rol_ID varchar PRIMARY KEY, "
                + "Descripcion varchar, "
                + "Habilitado boolean ); ");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Rol");
                System.out.println(e);
            }
        }
    }
    
    private void TriggerRol (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE OR REPLACE FUNCTION mirror_roles_groupRoles() RETURNS TRIGGER AS $$\n" +
                "    BEGIN\n" +
                "        IF (TG_OP = 'DELETE') THEN\n" +
                "			execute 'drop role ' || quote_ident(old.rol_id);\n" +
                "			RETURN OLD;\n" +
                "        ELSIF (TG_OP = 'UPDATE') THEN\n" +
                "			execute 'alter role ' || quote_ident(old.rol_id);\n" +
                "			RETURN NEW;\n" +
                "        ELSIF (TG_OP = 'INSERT') THEN\n" +
                "			execute ' create role ' || quote_ident(new.rol_id) || ' with superuser inherit';\n" +
                "                       execute ' grant all privileges on all tables in schema public to ' || quote_ident(new.rol_id);\n" +
                "                       execute ' grant usage, select on all sequences in schema public to ' || quote_ident(new.rol_id);\n" +
                "			RETURN NEW;\n" +
                "        END IF;\n" +
                "    END;\n" +
                "$$ LANGUAGE plpgsql;\n" +
                "\n" +
                "CREATE TRIGGER mirror_roles_groupRoles_trigger\n" +
                "BEFORE INSERT OR UPDATE OR DELETE ON rol\n" +
                "    FOR EACH ROW EXECUTE FUNCTION mirror_roles_groupRoles();");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void Funcionalidad(){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Funcionalidad ( "
                + "id SERIAL PRIMARY KEY, "
                + "Rol_ID_Autorizador varchar, "
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
                + "FOREIGN KEY(Menu_ID) REFERENCES Menu(id) ON DELETE CASCADE, "
                + "Habilitado boolean, "    
                + "FOREIGN KEY(Aplicacion_ID) REFERENCES Aplicacion(id) ON DELETE CASCADE); "); 
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
                + "FOREIGN KEY(Menu_ID) REFERENCES Menu(id) ON DELETE CASCADE, "
                + "FOREIGN KEY(Funcionalidad_ID) REFERENCES Funcionalidad(id) ON DELETE CASCADE);" );
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Menu_Funcionalidad");
                System.out.println(e);
            }
        }
    }
    
    private void Menu_Rol (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Menu_Rol ( "
                + "Rol_ID varchar, "
                + "Menu_ID serial, "
                + "CONSTRAINT PK_Menu_Rol PRIMARY KEY( " 
                + "Menu_ID, "
                + "Rol_ID), "                    
                + "Habilitado boolean, "
                + "FOREIGN KEY(Menu_ID) REFERENCES Menu(id) ON DELETE CASCADE, "
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
    
    private void TriggerUsuario (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE OR REPLACE FUNCTION mirror_user_roles() RETURNS TRIGGER AS $$\n" +
                "    BEGIN\n" +
                "        --\n" +
                "        -- Perform the required operation on emp, and create a row in emp_audit\n" +
                "        -- to reflect the change made to emp.\n" +
                "        --\n" +
                "        IF (TG_OP = 'DELETE') THEN\n" +
                "			execute 'drop user ' || quote_ident(old.usuario_id);\n" +
                "			RETURN OLD;\n" +
                "        ELSIF (TG_OP = 'UPDATE') THEN\n" +
                "			execute 'alter user ' || quote_ident(old.usuario_id);\n" +
                "			RETURN NEW;\n" +
                "        ELSIF (TG_OP = 'INSERT') THEN\n" +
                "			execute ' create user ' || quote_ident(new.usuario_id) || ' password ''' || quote_ident(new.contrasena) || '''';\n" +
                "			RETURN NEW;\n" +
                "        END IF;\n" +
                "    END;\n" +
                "$$ LANGUAGE plpgsql;\n" +
                "\n" +
                "CREATE TRIGGER mirror_user_roles_trigger\n" +
                "BEFORE INSERT OR UPDATE OR DELETE ON usuario\n" +
                "    FOR EACH ROW EXECUTE FUNCTION mirror_user_roles();");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void Rol_Usuario (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Rol_Usuario ( "
                + "Usuario_ID varchar, "
                + "Rol_ID varchar, " 
                + "CONSTRAINT PK_Rol_Usuario PRIMARY KEY( " 
                + "Usuario_ID, "
                + "Rol_ID), "
                + "Habilitado boolean, "
                + "FOREIGN KEY(Usuario_ID) REFERENCES Usuario(Usuario_ID) ON DELETE CASCADE, "
                + "FOREIGN KEY(Rol_ID) REFERENCES Rol(Rol_ID) ON DELETE CASCADE);"); 
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Rol_Usuario");
                System.out.println(e);
            }
        }
    }
    
    private void TriggerRol_Usuario (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE OR REPLACE FUNCTION mirror_rol_usuario_groupRoles_users() RETURNS TRIGGER AS $$\n" +
            "    BEGIN\n" +
            "        IF (TG_OP = 'UPDATE') THEN\n" +
            "			execute ' revoke ' || quote_ident(new.rol_id) || ' from ' || quote_ident(new.usuario_id);\n" +
            "			RETURN NEW;\n" +
            "        ELSIF (TG_OP = 'INSERT') THEN\n" +
            "			execute ' grant ' || quote_ident(new.rol_id) || ' to ' || quote_ident(new.usuario_id);\n" +
            "                   IF (new.rol_id = 'administrador_general' or new.rol_id = 'administrador_seguridad') THEN\n" +
            "                       execute ' alter role ' || quote_ident(new.usuario_id) || ' WITH SUPERUSER CREATEROLE';\n" +
            "                   END IF;\n" +
            "			RETURN NEW;\n" +
            "        END IF;\n" +
            "    END;\n" +
            "$$ LANGUAGE plpgsql;\n" +
            "\n" +
            "CREATE TRIGGER mirror_rol_usuario_groupRoles_users_trigger\n" +
            "BEFORE INSERT OR UPDATE ON rol_usuario\n" +
            "    FOR EACH ROW EXECUTE FUNCTION mirror_rol_usuario_groupRoles_users();");
        } catch (Exception e) {
            System.out.println(e);
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
                + "FOREIGN KEY(Aplicacion_ID) REFERENCES Aplicacion(id) ON DELETE CASCADE);"); 
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Usuario_Aplicacion");
                System.out.println(e);
            }
        }
    }

    private void Autorizacion (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Autorizacion ("
                + "id SERIAL PRIMARY KEY, "
                + "Usuario_solicitante_ID varchar, "
                + "Usuario_validador_ID varchar, "
                + "Fecha date, "
                + "Referencia_ID varchar, "
                + "Datos varchar, "
                + "Funcionalidad_ID serial, "
                + "Rol_validador varchar, "
                + "Estado varchar, "
                + "FOREIGN KEY(Usuario_validador_ID) REFERENCES Usuario(Usuario_ID), "
                + "FOREIGN KEY(Funcionalidad_ID) REFERENCES Funcionalidad(id), "
                + "FOREIGN KEY(Usuario_solicitante_ID) REFERENCES Usuario(Usuario_ID)); ");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Autorizacion");
                System.out.println(e);
            }
        }
    }
    
    private void Mesas (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Mesa ("
                + "id SERIAL PRIMARY KEY, "
                + "asientos serial, "
                + "ocupada boolean, "
                + "ubicacion varchar, "
                + "Habilitado boolean);");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Mesas");
                System.out.println(e);
            }
        }
    }
    
    private void Stock (){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Stock ("
                + "id SERIAL PRIMARY KEY, "
                + "nombre varchar, "
                + "cantidad serial, "
                + "estado varchar, "
                + "Habilitado boolean);");
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")){
                System.out.println("Stock");
                System.out.println(e);
            }
        }
    }

    private void InsertarDatos(String archivo, String nombreTabla) {
        String datos;
        FileReader f;
        DBHandler handler = new DBHandler();
        try {
            f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            String tabla = b.readLine();
            while((datos = b.readLine())!=null) {
                handler.Insertar(tabla, datos, nombreTabla);
            }
            b.close();
        } catch (Exception ex) {
            System.out.println("No se encuentra archivo "+archivo + nombreTabla);
        } 
        
    }
    
    public void cargarDatos(){
    
        this.InsertarDatos("src/DumpInicial/aplicacion.csv","Aplicacion");
        
        this.InsertarDatos("src/DumpInicial/menu.csv", "Menu");
        
        this.InsertarDatos("src/DumpInicial/rol.csv", "Rol");
        
        this.InsertarDatos("src/DumpInicial/aplicacion_menu.csv", "Aplicacion_Menu");
        
        this.InsertarDatos("src/DumpInicial/menu_rol.csv", "Menu_Rol");
        
        this.InsertarDatos("src/DumpInicial/persona.csv", "Persona");
        
        this.InsertarDatos("src/DumpInicial/usuario.csv", "Usuario");
            
        this.InsertarDatos("src/DumpInicial/rol_usuario.csv", "Rol_Usuario");
        
        this.InsertarDatos("src/DumpInicial/usuario_aplicacion.csv", "Usuario_Aplicacion");
        
        this.InsertarDatos("src/DumpInicial/funcionalidad.csv", "Funcionalidad");
        
        this.InsertarDatos("src/DumpInicial/menu_funcionalidad.csv", "Menu_Funcionalidad");
        
        this.InsertarDatos("src/DumpInicial/autorizacion.csv", "Autorizacion");
        
        this.InsertarDatos("src/DumpInicial/mesas.csv", "Mesa");
        
        this.InsertarDatos("src/DumpInicial/stock.csv", "Stock");
        
    }
}
