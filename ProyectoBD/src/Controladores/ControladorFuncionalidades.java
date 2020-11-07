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
    private ArrayList<Map> listaFunc;
    
    public ControladorFuncionalidades(ControladorDePantallas contrPantalla){
        pantallaFunc = new Funcionalidades();
        contrPantallas = contrPantalla;
        pantallaFunc.getSeleccionarButton().addActionListener(e -> ingresarFuncionalidad(pantallaFunc.getFuncSeleccionada()));
        pantallaFunc.getAtrasButton().addActionListener(e -> volverMenus());
    }

    public void volverMenus(){
        pantallaFunc.setVisible(false);
        listaFunc.clear();
        pantallaFunc.vaciarItems();
        contrPantallas.activarMenus();
    }
    
    void activarFunc(String menu) {
        pantallaFunc.setVisible(true);
        obtenerFuncionalidades(menu);
    }
    
    public void obtenerFuncionalidades(String menu){
        DBHandler manejador = new DBHandler();
        listaFunc = manejador.ListarFuncionalidades( menu ); // usar prim elemento
        for (Map m : listaFunc){
          agregarElemento((m.get("funcionalidad_id")).toString()+" "+(m.get("nombre")).toString());
        }
    }

    private void ingresarFuncionalidad(String funcSeleccionada) {
        
        DBHandler manejador = new DBHandler();
        Map<String, String> func = manejador.PrimerElemento(manejador.Listar("funcionalidad", "funcionalidad_id = '"+ funcSeleccionada.split(" ")[0] + "'"),true);
        
        if (func.get("tipo").equals("crear")){
            pantallaFunc.setVisible(false);
            contrPantallas.activarCrear(func);
        }
        else if (func.get("tipo").equals("listar")){
            pantallaFunc.setVisible(false);
            contrPantallas.activarListar(func);
        }
        else if (func.get("tipo").equals("eliminar")){
            pantallaFunc.setVisible(false);
            contrPantallas.activarEliminar(func);
        }
        else if (func.get("tipo").equals("pass")){
            pantallaFunc.setVisible(false);
            contrPantallas.activarCambiarPass();
        }
        else if (func.get("tipo").equals("auditoria")){
            pantallaFunc.setVisible(false);
            contrPantallas.activarAudi();
        }
        else{
            pantallaFunc.setVisible(false);
            contrPantallas.activarEnDesarrollo();  
        }
    
    }
    
    public void agregarElemento(String item){
        pantallaFunc.agregarItem(item);
    }

    void activarFunc() {
        pantallaFunc.setVisible(true);
    }
    
}
