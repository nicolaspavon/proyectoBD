/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas;

import Base.DBHandler;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Autorizar{
    public JButton autorizar;
    public JButton denegar;
    private JComboBox<String> autComboBox;
    public JButton atras;
    private JFrame frame;
    private ArrayList<Map> Autorizaciones;
    
    public Autorizar(ArrayList<Map> Aut){
        Autorizaciones = Aut;
        frame = new JFrame("Autorizaciones");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        BoxLayout boxLayoutManager = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayoutManager);
        
        autComboBox = new JComboBox();
        panel.add(autComboBox);
        DBHandler manejador = new DBHandler();

        for(Map aut : Autorizaciones){
            String solicitante = aut.get("usuario_solicitante_id").toString();
            String id = aut.get("id").toString();
            String funcionalidad_id = aut.get("funcionalidad_id").toString();
            Map<String, String> Funcionalidad = manejador.PrimerElemento(manejador.Listar("Funcionalidad", "id='" +funcionalidad_id+ "'"));
            String nombreAut = id + "/ "+solicitante+" " + Funcionalidad.get("tipo").toString()+" " + Funcionalidad.get("nombretabla").toString()+" " + aut.get("datos").toString().replace(":", "").replace("-", " | ");
            agregarItem(nombreAut);
        }

        autorizar = new JButton("Autorizar");
        panel.add(autorizar);
        
        denegar = new JButton("Denegar");
        panel.add(denegar);
        
        atras = new JButton("Atras");
        panel.add(atras);

        frame.add(panel);

        frame.setSize(600, 120);	
        frame.setVisible(true);
    }
    
    public void agregarItem(String item){
        System.out.println(item);
        autComboBox.addItem(item);
    }
    
    public JButton getAutorizar(){
        return autorizar;
    }
    
    public JButton getDenegar(){
        return denegar;
    }
    
    
    public JButton getAtras(){
        return atras;
    }
    
    public void apagarPantalla(){
        frame.setVisible(false);
    }
    
    public Map getSeleccionado() {
        String selected = autComboBox.getSelectedItem().toString();
        String id = selected.split("/")[0];
        for(Map aut : Autorizaciones){
            if(aut.get("id").equals(id)){
                return aut;
            }
        }
        return null;
    }
}