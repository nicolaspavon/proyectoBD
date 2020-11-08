/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base;

import Base.DataBase;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Base.DataBase.connection;
import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nikok
 */
public class DBHandler {
    
    public void cambiarPassword(String usuario_id, String pass){
        try {
            Statement stmt = connection.createStatement();
            String stringDatos = "UPDATE " + "usuario" + " SET contrasena='" + pass + "' WHERE "+"usuario_id='"+usuario_id+"';";
            
            stmt.executeUpdate("ALTER ROLE "+ usuario_id+ " WITH PASSWORD '"+pass+"';");
            stmt.executeUpdate(stringDatos);
            stmt.close(); 
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    public void Insertar(String datos, String nombreTabla){
        try {
            Statement stmt = connection.createStatement();
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
    
    public void Insertar(String columnas, String datos, String nombreTabla){
        try {
            Statement stmt = connection.createStatement();
            String stringDatos = "INSERT INTO " + nombreTabla + "(" + columnas + ")"+ " VALUES " 
                    + "(" + datos + ");";

            stmt.executeUpdate(stringDatos);
            stmt.close();              
            
        }
        catch (Exception e){
            System.out.println("Error en insertar en " + nombreTabla);
            System.out.println(e);
        }
    }
	
    public ResultSet ListarHab(String nombreTabla){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+nombreTabla+" WHERE "+nombreTabla+".habilitado=true;");
            return rs;
        }
        catch (Exception e){
            System.out.println("Error listando " + nombreTabla);
            System.out.println(e);  
        }
        return null;
    }
    
    public ResultSet listarFiltrado(String nombreTabla,String filtro){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+nombreTabla+" ORDER BY " + filtro+";");
            return rs;
        }
        catch (Exception e){
            System.out.println("Error listando " + nombreTabla);
            System.out.println(e);  
        }
        return null;
    }
    
    public ResultSet Listar(String nombreTabla, String clausula){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+nombreTabla+" WHERE "+clausula+";");
            return rs;
        }
        catch (Exception e){
            System.out.println("Error listando " + nombreTabla);
            System.out.println(e);  
        }
        return null;
    }
    
    public ResultSet Listar(String nombreTabla, String clausula,boolean bool){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+nombreTabla+" WHERE "+clausula+" AND "+nombreTabla+ ".habilitado=true;");
            return rs;
        }
        catch (Exception e){
            System.out.println("Error listando " + nombreTabla);
            System.out.println(e);  
        }
        return null;
    }
    public void Borrar(String nombreTabla, String clausula){
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM "+nombreTabla+" WHERE "+clausula+";");
        }
        catch (Exception e){
            System.out.println("Error borrando " + clausula);
            System.out.println(e);  
        }
    }
    
     public void Actualizar(String nombreTabla,  String cambio, String clausula){
         System.out.println("UPDATE " + nombreTabla + " SET " + cambio + " WHERE "+clausula+";");
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE " + nombreTabla + " SET " + cambio + " WHERE "+clausula+";");
        }
        catch (Exception e){
            System.out.println("Error corrigiendo " + clausula + " de la tabla " + nombreTabla);
            System.out.println(e);  
        }
    }
    
    public Map PrimerElemento(ResultSet rs){
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int cantColumnas = rsMetaData.getColumnCount();
            Map<String, String> fila = new HashMap<>();
            rs.next();
            for(int i = 1; i<=cantColumnas; i++) {
                try{
                    fila.put(rsMetaData.getColumnName(i), rs.getObject(rsMetaData.getColumnName(i)).toString()); 
                }
                catch(Exception e){
                    fila.put(rsMetaData.getColumnName(i), ""); 
                
                }
            } 
            return fila;
        } catch (SQLException ex) {
            System.out.println("Error al obtener primer elemento");
        }
        return null;
    }
    
    public ArrayList<Map> Imprimir(ResultSet rs){
        ArrayList<Map> resultado = new ArrayList<>();
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int cantColumnas = rsMetaData.getColumnCount();
            while (rs.next()) {
                Map<String, String> fila = new HashMap<>();
                for(int i = 1; i<=cantColumnas; i++) {
                    if (rs.getObject(rsMetaData.getColumnName(i)) != null){
                        fila.put(rsMetaData.getColumnName(i), rs.getObject(rsMetaData.getColumnName(i)).toString());
                    }else{
                        fila.put(rsMetaData.getColumnName(i), "null");
                    }
                    
                }
                resultado.add(fila);
            }

        } catch (SQLException ex) {
        }
        return resultado;
    }
    
    public ResultSet ObtenerMenues(String usuario, String aplicacion){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT menu_rol.menu_id,menu.nombre " +
                    "FROM menu_rol, rol_usuario, aplicacion_menu , menu " +
                    "WHERE menu_rol.rol_id=rol_usuario.rol_id AND rol_usuario.usuario_id='" + usuario + "'" +
                    "AND menu_rol.menu_id = aplicacion_menu.menu_id AND menu.id = aplicacion_menu.menu_id "+ 
                    "AND menu_rol.habilitado=rol_usuario.habilitado AND rol_usuario.habilitado=aplicacion_menu.habilitado " +
                    "AND aplicacion_menu.habilitado = true AND menu.habilitado=true "+
                    "AND aplicacion_menu.aplicacion_id=" + aplicacion +";");
            return rs;
        }
        catch (Exception e){
            System.out.println("Error obteniendo menues para el usuario " + usuario);
            System.out.println(e);  
        }
        return null;
    }
    
    public ArrayList<String> GetTabla(String nombreTabla){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+nombreTabla.toLowerCase()+" LIMIT 1;");
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int cantColumnas = rsMetaData.getColumnCount();
            ArrayList<String> resultado = new ArrayList<String>();
            while (rs.next()) {
                for(int i = 1; i<=cantColumnas; i++) {
                    resultado.add(rsMetaData.getColumnName(i));
                }
            }
            return resultado;
        }
        catch (Exception e){
            System.out.println("Error listando " + nombreTabla);
            System.out.println(e);  
        }
        return null;
    }

    public ArrayList ListarFuncionalidades( String menu_id){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT menu_funcionalidad.funcionalidad_id, funcionalidad.nombre FROM funcionalidad, menu_funcionalidad " +
                " WHERE funcionalidad.habilitado=true AND menu_funcionalidad.menu_id="+menu_id +
                " AND menu_funcionalidad.habilitado=true AND funcionalidad.id = menu_funcionalidad.funcionalidad_id");
            ArrayList<Map> resultado = new ArrayList<>();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int cantColumnas = rsMetaData.getColumnCount();
            while (rs.next()) {
                Map<String, String> fila = new HashMap<>();
                for(int i = 1; i<=cantColumnas; i++) {
                    try {
                        fila.put(rsMetaData.getColumnName(i), rs.getObject(rsMetaData.getColumnName(i)).toString());
                    }
                    catch (Exception e){
                        System.out.println("error obteniendo datos");
                   }
                }
                resultado.add(fila);
            }
            return resultado;
        }
        catch (Exception e){
            System.out.println("Error listando " + menu_id);
            System.out.println(e);  
        }
        return null;
    }
    
   public ResultSet ListarAplicaciones(String usuario, String columnas){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT aplicacion.nombre,usuario_aplicacion.aplicacion_id FROM aplicacion, usuario_aplicacion WHERE aplicacion.id = usuario_aplicacion.aplicacion_id AND usuario_aplicacion.usuario_id='"+usuario+"';");
            return rs;
        }
        catch (Exception e){
            System.out.println("Error listando apps de " + usuario);
            System.out.println(e);  
        }
        return null;
    }
   
   public ResultSet ObtenerAutorizaciones(String usuario){
       
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT autorizacion.id FROM autorizacion, rol_usuario, usuario "+
                    " WHERE usuario.usuario_id='"+ usuario+"' AND NOT (usuario.usuario_id = autorizacion.usuario_solicitante_id) "+
                    " AND autorizacion.rol_validador=rol_usuario.rol_id AND usuario.usuario_id=rol_usuario.usuario_id " +
                    " AND autorizacion.estado='pendiente' AND rol_usuario.habilitado=true AND usuario.habilitado=true; ");
            
                    return rs;
        }
        catch (Exception e){
            System.out.println("Error listando autorizaciones de " + usuario);
            System.out.println(e);  
        }
        return null;
   }
   
}
