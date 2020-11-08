/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import Pantallas.Auditoria;
import Pantallas.Listar;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Vale
 */
public class ControladorAuditoria {
            
    private Auditoria pantallaAudi;
    private Listar pantallaListar;
    private ControladorDePantallas contrPantallas;
    private String user;
    
    public ControladorAuditoria(ControladorDePantallas contrPantalla){
        pantallaAudi = new Auditoria();
        contrPantallas = contrPantalla;
        pantallaAudi.getSeleccionarButton().addActionListener(e -> activarListar(pantallaAudi.getFiltrarSeleccionado()));
        pantallaAudi.getAtrasButton().addActionListener(e -> volverAtras());
        
    }
    
    private void filtrados(){
        pantallaAudi.agregarItem("fecha");
        pantallaAudi.agregarItem("estado");
        pantallaAudi.agregarItem("id");
    }
    
    void activarListar(String filtro) {
        ArrayList<String> tabla = traerTabla("autorizacion");
        String linea = generarPrimeraLinea(tabla);
        
        DBHandler manejador = new DBHandler();
        ArrayList<Map> listaFilas;
        if(pantallaAudi.DescOAsc().equals("Descendente")){
            listaFilas = manejador.Imprimir(manejador.listarFiltradoDesc("autorizacion", filtro));
        }
        else{
            listaFilas = manejador.Imprimir(manejador.listarFiltrado("autorizacion", filtro));
        }
        
        
        
        pantallaAudi.setVisible(false);
        pantallaListar = new Listar(linea, tabla, "Listar", "autorizacion", listaFilas);
        pantallaListar.getAtras().addActionListener(e -> volverAtrasListar());
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
        pantallaAudi.setVisible(false);
        
        contrPantallas.activarFuncionalidades();
    }
    

    void activarPantalla(String user) {
        pantallaAudi.vaciarItems();
        filtrados();
        pantallaAudi.setVisible(true);
    }

    private void volverAtrasListar() {
        pantallaListar.apagarPantalla();
        pantallaAudi.setVisible(true);
    }
}
