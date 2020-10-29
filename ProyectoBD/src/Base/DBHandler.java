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
	
    public ResultSet Listar(String nombreTabla){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+nombreTabla+";");
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
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE " + nombreTabla + " SET " + cambio + " WHERE "+clausula+";");
        }
        catch (Exception e){
            System.out.println("Error corrigiendo " + clausula + " de la tabla " + nombreTabla);
            System.out.println(e);  
        }
    }
    
    
    
    public ArrayList<Map> Imprimir(ResultSet rs){
        ArrayList<Map> resultado = new ArrayList<>();
        try {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int cantColumnas = rsMetaData.getColumnCount();
            while (rs.next()) {
                Map<String, String> fila = new HashMap<>();
                for(int i = 1; i<=cantColumnas; i++) {
                    fila.put(rsMetaData.getColumnName(i), rs.getObject(rsMetaData.getColumnName(i)).toString());
                }
                resultado.add(fila);
            }

        } catch (SQLException ex) {
        }
        return resultado;
    }
    
}
