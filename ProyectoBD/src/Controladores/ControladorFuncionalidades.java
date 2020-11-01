/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import Pantallas.Funcionalidades;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Vale
 */
public class ControladorFuncionalidades {
        
    private Funcionalidades pantallaFunc;
    private ControladorDePantallas contrPantallas;
    
    public ControladorFuncionalidades(ControladorDePantallas contrPantalla){
        pantallaFunc = new Funcionalidades();
        contrPantallas = contrPantalla;
        pantallaFunc.getSeleccionarButton().addActionListener(e -> ingresarFuncionalidad(pantallaFunc.getFuncSeleccionada()));
    }

    void activarFunc(String menu) {
        pantallaFunc.setVisible(true);
        obtenerFuncionalidades(menu);
    }
    
    public void obtenerFuncionalidades(String menu){
        DBHandler manejador = new DBHandler();
        ArrayList<Map> funcs = manejador.Imprimir(manejador.Listar("menu_funcionalidad", "menu_id = '"+ menu + "'"));
        for (Map m : funcs){
            agregarElemento((m.get("funcionalidad_id")).toString());
        }
    }

    private void ingresarFuncionalidad(String funcSeleccionada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void agregarElemento(String item){
        pantallaFunc.agregarItem(item);
    }
    
}