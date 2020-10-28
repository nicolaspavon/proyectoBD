/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static proyectobd.DataBase.connection;
;

/**
 *
 * @author Vale
 */
public class GUI implements ActionListener{

    private JPanel panel;
    private JFrame frame;
    private JTextField userText;
    private JLabel label;
    private JLabel label2;
    private JPasswordField passText;
    private JButton button;
    
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.Login();
        
    }
    
    public void Login(){
        panel = new JPanel();
        
        frame = new JFrame(); 
        
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        label = new JLabel ("Usuario");
        label.setBounds(10,20,80,25);
        panel.add(label);
        
        userText = new JTextField(20); //cantidad de caracteres
        userText.setBounds(100,20,165,25);
        panel.add(userText);
        
        label2 = new JLabel ("Contraseña");
        label2.setBounds(10,50,80,25);
        panel.add(label2);
        
        passText = new JPasswordField(20); //cantidad de caracteres
        passText.setBounds(100,50,165,25);
        panel.add(passText);
        
        button = new JButton("Ingresar");
        button.setBounds(10, 80, 80, 25);
        button.addActionListener(this); //accionar del boton
        panel.add(button);
        
        frame.add(panel);
        frame.setVisible(true);
        frame.setTitle("Login");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Si los datos son correctos 
        String user = userText.getText();
        String  pass = passText.getText(); //deprecated, no se si igual lo podemos usar
        
        if(this.conectar(user,pass)){
            panel.setVisible(false);
            this.menu();
        }
        else{
            
        }
    }
    
    public void menu(){
        panel.removeAll();
        
        label.setText("Bien ahi");
        panel.add(label);
        
        panel.setVisible(true);
        
    }
    
    public boolean conectar(String user, String pass)
    {
        
            try{
                connection = DriverManager.getConnection("jdbc:postgresql://192.168.56.102:5432/obli", user, pass);
                return true;
                
            }catch (SQLException ex) {
                Logger lgr = Logger.getLogger(ProyectoBD.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
                return false;
            }
            
        
    }
    
    
}
