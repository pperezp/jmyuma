/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor.modelo.descripcion;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class Descripcion {
    private List<Fila> filas;
    
    public Descripcion(){
        this.filas = new ArrayList<>();
    }
    
    public void addColumna(Fila c){
        filas.add(c);
    }
    
    public void removeColumna(Fila c){
        filas.remove(c);
    }

    @Override
    public String toString() {
        return "Descripcion{" + "columnas=" + filas + '}';
    }

    public List<Fila> getFilas() {
        return filas;
    }

    public void setColumnas(List<Fila> columnas) {
        this.filas = columnas;
    }
    
    
}
