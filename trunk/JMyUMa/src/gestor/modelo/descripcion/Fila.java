/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor.modelo.descripcion;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class Fila {
    private String campo;
    private String tipo;
    private String nulo;
    private String key;
    private String porDefecto;
    private String extra;

    public Fila(String campo, String tipo, String nulo, String key, String porDefecto, String extra) {
        this.campo = campo;
        this.tipo = tipo;
        this.nulo = nulo;
        this.key = key;
        this.porDefecto = porDefecto;
        this.extra = extra;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNulo() {
        return nulo;
    }

    public void setNulo(String nulo) {
        this.nulo = nulo;
    }

    public String getPorDefecto() {
        return porDefecto;
    }

    public void setPorDefecto(String porDefecto) {
        this.porDefecto = porDefecto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Columna{" + "campo=" + campo + ", tipo=" + tipo + ", nulo=" + nulo + ", key=" + key + ", porDefecto=" + porDefecto + ", extra=" + extra + '}';
    }
    
    
}
