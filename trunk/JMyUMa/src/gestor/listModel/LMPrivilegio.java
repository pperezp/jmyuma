/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor.listModel;

import gestor.modelo.Privilegio;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class LMPrivilegio implements ListModel{
    private List<Privilegio> privilegios;
    
    public LMPrivilegio(){
        privilegios = new ArrayList<>();
        privilegios.addAll(Arrays.asList(Privilegio.values()));
    }

    @Override
    public int getSize() {
        return this.privilegios.size();
    }

    @Override
    public Object getElementAt(int index) {
        return this.privilegios.get(index);
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
