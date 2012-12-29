/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor.modelo.bd;

import gestor.modelo.descripcion.Descripcion;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class Tabla implements TableModel{
    private String nombre;
    private Descripcion descripcion;

    public Tabla(String nombre) {
        this.nombre = nombre;
        this.descripcion = null;
    }

    public Tabla(String nombre, Descripcion descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Descripcion getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Descripcion descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int getRowCount() {
       return this.descripcion.getFilas().size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0:
                return "Campo";
            case 1:
                return "Tipo";
            case 2:
                return "Nulo";
            case 3:
                return "Key";
            case 4:
                return "Por defecto";
            case 5:
                return "Extra";
            default: return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       switch(columnIndex){
            case 0:
                return this.descripcion.getFilas().get(rowIndex).getCampo();
            case 1:
                return this.descripcion.getFilas().get(rowIndex).getTipo();
            case 2:
                return this.descripcion.getFilas().get(rowIndex).getNulo();
            case 3:
                return this.descripcion.getFilas().get(rowIndex).getKey();
            case 4:
                return this.descripcion.getFilas().get(rowIndex).getPorDefecto();
            case 5:
                return this.descripcion.getFilas().get(rowIndex).getExtra();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                this.descripcion.getFilas().get(rowIndex).setCampo(aValue.toString());
            case 1:
                this.descripcion.getFilas().get(rowIndex).setTipo(aValue.toString());
            case 2:
                this.descripcion.getFilas().get(rowIndex).setNulo(aValue.toString());
            case 3:
                this.descripcion.getFilas().get(rowIndex).setKey(aValue.toString());
            case 4:
                this.descripcion.getFilas().get(rowIndex).setPorDefecto(aValue.toString());
            case 5:
                this.descripcion.getFilas().get(rowIndex).setExtra(aValue.toString());
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
