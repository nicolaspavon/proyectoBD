/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Base.DBHandler;
import Pantallas.Actualizar;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Lore
 */
class ControladorActualizacion {
    
    private Actualizar pantallaAct;
    private ControladorDePantallas contrPantallas;
    private ControladorAutorizaciones contrAutorizaciones;
    private ResultSet objeto;
    private Map func;
    private String tabl;
    private String tablanombre;
    private Map ver;
    private String key;
    
    public ControladorActualizacion(ControladorDePantallas contrPantalla, ControladorAutorizaciones contrAut) {
        contrPantallas = contrPantalla;
        contrAutorizaciones = contrAut;
    }

        void activarActualizacion(String primKey, Map data, String tabl) {
            
        DBHandler manejador = new DBHandler();
        this.tablanombre = data.get("nombretabla").toString();
        String key = tablanombre+"."+tabl+"='"+primKey+"'";
        this.objeto = manejador.Listar(tablanombre,key , true);
        this.func = data;
        this.tabl=tabl;
        this.ver = manejador.PrimerElemento(objeto);
        
      //  ArrayList<Map> elem=manejador.Imprimir(objeto);
        
        ArrayList<String> tabla = traerTabla(this.tablanombre);
        tabla.remove("habilitado");
        
        pantallaAct = new Actualizar(tabla, ver);
        pantallaAct.getActualizar().addActionListener(e -> actualizarObjeto());
        pantallaAct.getAtras().addActionListener(e -> volverAtras());
        }
    
    public void volverAtras(){
        pantallaAct.apagarPantalla();
        contrPantallas.activarFuncionalidades();
    }
    
    private ArrayList<String> traerTabla(String nombreTabla) {
        DBHandler manejador = new DBHandler();
        return manejador.GetTabla(nombreTabla);
    }

    private void actualizarObjeto() {
        String datos = pantallaAct.getDatos();
        String[] da= datos.split(",");
        DBHandler manejador = new DBHandler();
        ArrayList<String> columnas=manejador.GetTabla(tablanombre);
        String clausula = columnas.get(0)+"="+da[0];
        manejador.Actualizar(tablanombre,"habilitado=false",clausula);
        this.contrAutorizaciones.generarAutorizacion(func, datos, clausula);
        this.volverAtras();
    }
    
}
