/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobd;

import baseDeDatos.DBHandler;
import baseDeDatos.DataBase;
import proyectobd.ui.Login;

/**
 *
 * @author nikok
 */
public class ControladorDeSesion {
    public ControladorDeSesion(DataBase base, Login login) {
        login.setVisible(true);
        login.getLastnameSaveButton().addActionListener(e -> connect(base, login));
    }
    
    public void connect(DataBase base, Login login){
        base.getCurrentConnection(login.getUserTextfield(), login.getPassTextfield());
        DBHandler manejador = new DBHandler();
        manejador.Imprimir(manejador.Listar("Usuario"));
    }
}
