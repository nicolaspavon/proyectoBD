/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas;

import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;

public class Crear{
    public JButton crear;
    public ArrayList<JTextField> texts;
    
    public Crear(ArrayList<String> tabla, String nombre){
        JFrame frame = new JFrame(nombre);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        BoxLayout boxLayoutManager = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayoutManager);

        ArrayList<JLabel> labels = new ArrayList<JLabel>();
        texts = new ArrayList<JTextField>();
        for(String key : tabla) {
            JLabel temp = new JLabel((String) key);
            labels.add(temp);
            panel.add(temp);
            JTextField textArea = new JTextField();
            texts.add(textArea);
            panel.add(textArea);
        }

        crear = new JButton("Crear");
        panel.add(crear);

        frame.add(panel);

        frame.setSize(300, 300);	
        frame.setVisible(true);
    }
    
    public JButton getCrear(){
        return crear;
    }
    
    public String getDatos(){
        String datos = "";
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