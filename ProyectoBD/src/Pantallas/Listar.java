/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas;

import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;

public class Listar{
    public JButton crear;
    public ArrayList<JTextField> texts;
    private String primeraLinea;
    
    public Listar(String linea,ArrayList<String> tabla, String nombreFunc, String nombreTabla, ArrayList<Map> filas){ //Recorrer las filas y ponerlas en la lista
        JFrame frame = new JFrame(nombreFunc);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        BoxLayout boxLayoutManager = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayoutManager);
        
        JList listaFilas = new JList();
        DefaultListModel modelo = new DefaultListModel();
        
        modelo.addElement(linea);
        
        String line;
        for (Map<String,String> m : filas){
            line="";
            for(int i=0; i<tabla.size(); i++){
                line += m.get(tabla.get(i)) + "   |   ";
            }
            modelo.addElement(line);
        }
        
        listaFilas.setModel(modelo);
        panel.add(listaFilas);
        
        frame.add(panel);

        frame.setSize(550, 200);	
        frame.setVisible(true);
    }
    
    public JButton getCrear(){
        return crear;
    }

}