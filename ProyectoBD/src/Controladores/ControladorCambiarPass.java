/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import Base.DataBase;
import Pantallas.CambiarPass;

/**
 *
 * @author Vale
 */
public class ControladorCambiarPass {
    private String Usuario;
    private ControladorDePantallas contrPantallas;
    private CambiarPass pantallaPass;
    
    public ControladorCambiarPass(ControladorDePantallas contrPantalla,String user) 
    {
       Usuario = user;
       pantallaPass = new CambiarPass();
       contrPantallas = contrPantalla;
       pantallaPass.getConfirmarButton().addActionListener(e -> cambiarPass());
    }
    

    void activarCambiarPass(String user) {
        pantallaPass.setVisible(true);
        
    }
    
    private void cambiarPass(){
        DBHandler manejador = new DBHandler();
        //Editar la tabla usuario con la nueva pass, comparar ambas introducidas si son iwales
        //Registrar en autorizaciones (opcional)
        if (passCorrecta()){
            manejador.cambiarPassword(Usuario, pantallaPass.getPass1());
            pantallaPass.setVisible(false);
            contrPantallas.activarFuncionalidades();
        }
    }
    
    boolean passCorrecta(){
        return (pantallaPass.getPass1().equals(pantallaPass.getPass2()));
    }
    
    
}
