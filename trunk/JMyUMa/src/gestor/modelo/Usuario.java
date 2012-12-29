/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor.modelo;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class Usuario implements Comparable<Usuario>{
    private String nombre;
    private String pass;
    private String host;

    public Usuario(String nombre, String pass) {
        this.nombre = nombre;
        this.pass = pass;
        this.host = "%";
    }
    
    public Usuario(String nombre, String pass, String host) {
        this.nombre = nombre;
        this.pass = pass;
        this.host = host;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return this.nombre + " ("+this.host+")";
    }

    @Override
    public int compareTo(Usuario o) {
        if(this.nombre.equalsIgnoreCase(o.nombre) && 
                this.host.equalsIgnoreCase(o.host) &&
                this.pass.equalsIgnoreCase(o.pass)){
            return 1;
        }else return -1;
    }

    public boolean isNombreIgual(Usuario u){
        return this.nombre.equalsIgnoreCase(u.nombre);
    }
    
}
