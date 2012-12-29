/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor.modelo.bd;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class BD implements ListModel{
    private String nombre;
    private List<Tabla> tablas;

    public BD(String nombre) {
        this.nombre = nombre;
        this.tablas = new ArrayList<>();
        this.tablas.add(new Tabla("*"));
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void addTabla(Tabla tabla){
        tablas.add(tabla);
    }
    
    public List<Tabla> getTablas(){
        return tablas;
    }
    
    public void removeTabla(Tabla tabla){
        tablas.remove(tabla);
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int getSize() {
        return this.tablas.size();
    }

    @Override
    public Object getElementAt(int index) {
        return this.tablas.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
