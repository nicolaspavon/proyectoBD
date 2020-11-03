/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import Pantallas.Listar;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Vale
 */
public class ControladorListar {

    private Listar pantallaListar;
    private ControladorDePantallas contrPantallas;
    private Map func;
    
    public ControladorListar(ControladorDePantallas contrPantalla){
        contrPantallas = contrPantalla;
    }
    
    void activarListar(Map<String, String> mapFunc) {
        func = mapFunc;
        ArrayList<String> tabla = traerTabla(func.get("nombretabla").toString());
        String linea = generarPrimeraLinea(tabla);
        
        DBHandler manejador = new DBHandler();
        manejador.Listar(func.get("nombretabla").toString());
        ArrayList<Map> listaFilas = manejador.Imprimir(manejador.Listar(func.get("nombretabla").toString()));
        
        pantallaListar = new Listar(linea, tabla, func.get("nombre").toString(), func.get("nombretabla").toString(), listaFilas);
        pantallaListar.getAtras().addActionListener(e -> volverAtras());
    }
    
    public String generarPrimeraLinea(ArrayList<String> tabla){
        String linea ="";
        for (String elemento : tabla){
            linea+= elemento + "   |   ";
        }
        return linea;
    }
   
    private ArrayList<String> traerTabla(String nombreTabla) {
        DBHandler manejador = new DBHandler();
        return manejador.GetTabla(nombreTabla);
    }

    private void volverAtras() {
        pantallaListar.apagarPantalla();
        func.clear();
        contrPantallas.activarFuncionalidades();
    }


    
}
