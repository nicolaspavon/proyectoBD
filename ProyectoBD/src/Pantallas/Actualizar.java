/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Actualizar{
    public JButton actualizar;
    public ArrayList<JTextField> texts;
    public JButton atras;
    private JFrame frame;
    private JLabel id;
    
    public Actualizar(ArrayList<String> tabla, Map nombre){
        frame = new JFrame("Actualizacion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        BoxLayout boxLayoutManager = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayoutManager);
        ArrayList<JLabel> labels = new ArrayList<JLabel>();
        String etiqueta =tabla.remove(0);
        String cosa =nombre.get(etiqueta).toString();
        id= new JLabel(etiqueta + ": "+ cosa);
        labels.add(id);
        panel.add(id);
        texts = new ArrayList<JTextField>();
        for(int i=0; i< tabla.size();i++){
            JLabel temp = new JLabel(tabla.get(i).toString());
            labels.add(temp);
            panel.add(temp);
            JTextField textArea = new JTextField(nombre.get(tabla.get(i).toString()).toString());
            texts.add(textArea);
            panel.add(textArea);
        }

        actualizar = new JButton("Actualizar");
        panel.add(actualizar);
        
        atras = new JButton("Atras");
        panel.add(atras);

        frame.add(panel);

        frame.setSize(300, 300);	
        frame.setVisible(true);
    }
    
    public JButton getActualizar(){
        return actualizar;
    }
    
    
    public JButton getAtras(){
        return atras;
    }
    
    public void apagarPantalla(){
        frame.setVisible(false);
    }
    
    public String getDatos(){
        String datos = "";
        datos="'"+id.getText().split(" ")[1]+"'";
        for(JTextField campo:texts){
            if(datos.equals("")){
                datos = "'" + campo.getText() + "'";
            }else{
                datos = datos +","+ "'" + campo.getText()+ "'";
            }
        }
        return datos;
    }
}