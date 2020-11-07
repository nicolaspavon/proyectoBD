/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Pantallas.EnDesarrollo;

/**
 *
 * @author Lore
 */
class ControladorEnDesarrollo {
    private EnDesarrollo pantallaEnDesarrollo;
    private ControladorDePantallas contrPantallas;
    public ControladorEnDesarrollo(ControladorDePantallas contrPantalla) 
    {
       pantallaEnDesarrollo = new EnDesarrollo();
       contrPantallas = contrPantalla;
       pantallaEnDesarrollo.getAtrasButton().addActionListener(e -> volverLogin());
    }

    private void volverLogin() {
        pantallaEnDesarrollo.setVisible(false);
        contrPantallas.activarFuncionalidades();
    }
    void activarEnProceso() {
        pantallaEnDesarrollo.setVisible(true);
    }
}

