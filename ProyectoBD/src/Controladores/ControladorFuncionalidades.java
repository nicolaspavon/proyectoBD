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
    
    void desactivar(){
        pantallaFunc.setVisible(false);
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

        Map<String, String> func = manejador.PrimerElemento(manejador.Listar("funcionalidad", "id = '"+ funcSeleccionada.split(" ")[0] + "'",true));

        
        if (func.get("tipo").equals("crear")){
            this.desactivar();
            contrPantallas.activarCrear(func);
        }
        else if (func.get("tipo").equals("listar")){
            this.desactivar();
            contrPantallas.activarListar(func);
        }
        else if (func.get("tipo").equals("eliminar")){
            this.desactivar();
            contrPantallas.activarEliminar(func);
        }
        else if (func.get("tipo").equals("pass")){
            this.desactivar();
            contrPantallas.activarCambiarPass();
        }
        else if (func.get("tipo").equals("auditoria")){
            this.desactivar();
            contrPantallas.activarAudi();
        }
        else if (func.get("tipo").equals("actualizar")){
            this.desactivar();
            contrPantallas.activarActualizar(func);
        }
        else if (func.get("tipo").equals("autorizar")){
            this.desactivar();
            contrPantallas.activarAutorizaciones();
        }
        else{
            this.desactivar();
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
