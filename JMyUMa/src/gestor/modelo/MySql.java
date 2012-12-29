/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor.modelo;

import gestor.conexion.Conectable;
import gestor.conexion.Conexion;
import gestor.modelo.bd.BD;
import gestor.modelo.bd.Tabla;
import gestor.modelo.descripcion.Descripcion;
import gestor.modelo.descripcion.Fila;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class MySql implements ListModel {

    private List<BD> bds;
    private Conexion c;
    private String rootPass;

    public MySql(final String rootPass) throws ClassNotFoundException, SQLException {
        bds = new ArrayList<>();
        bds.add(new BD("*"));
        this.rootPass = rootPass;
        c = new Conexion(new Conectable() {

            @Override
            public String getServer() {
                return "localhost";
            }

            @Override
            public String getUser() {
                return "root";
            }

            @Override
            public String getPass() {
                return rootPass;
            }

            @Override
            public String getBaseDeDatos() {
                return "";
            }
        });

        c.conectar();
        cargarBaseDedatos();
    }

    public void addBD(BD bd) {
        bds.add(bd);
    }

    public List<BD> getBaseDeDatos() {
        return this.bds;
    }

    private void cargarBaseDedatos() throws ClassNotFoundException {
        try {
            Conexion cBD;
            BD bd;
            Descripcion des;
            String nombreBD;
            c.sentencia = c.con.createStatement();
            c.rs = c.sentencia.executeQuery("show databases");

            while (c.rs.next()) {
                nombreBD = c.rs.getString(1);
                bd = new BD(nombreBD);
                cBD = getConexion(nombreBD);
                cBD.conectar();
                //listar tablas de la bd
                cBD.sentencia = cBD.con.createStatement();
                cBD.rs = cBD.sentencia.executeQuery("show tables");
                while (cBD.rs.next()) {
                    bd.addTabla(new Tabla(cBD.rs.getString(1)));
                }
                cBD.sentencia.close();

                //listar descripcion por cada tabla

                cBD.sentencia = cBD.con.createStatement();
                for (Tabla t : bd.getTablas()) {
                    if (!t.getNombre().equalsIgnoreCase("*")) {
                        cBD.rs = cBD.sentencia.executeQuery("describe " + t.getNombre());
                        des = new Descripcion();
                        while (cBD.rs.next()) {
                            des.addColumna(new Fila(
                                    cBD.rs.getString(1),
                                    cBD.rs.getString(2),
                                    cBD.rs.getString(3),
                                    cBD.rs.getString(4),
                                    cBD.rs.getString(5),
                                    cBD.rs.getString(6)));
                        }
                        t.setDescripcion(des);
                    }
                }
                cBD.sentencia.close();

                this.bds.add(bd);
            }
            c.sentencia.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Conexion getConexion(final String nombreBD) {
        return new Conexion(new Conectable() {

            @Override
            public String getServer() {
                return "localhost";
            }

            @Override
            public String getUser() {
                return "root";
            }

            @Override
            public String getPass() {
                return rootPass;
            }

            @Override
            public String getBaseDeDatos() {
                return nombreBD;
            }
        });
    }

    @Override
    public int getSize() {
        return this.bds.size();
    }

    @Override
    public Object getElementAt(int index) {
        return this.bds.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void crearUsuario(Usuario usuario) throws SQLException {
        c.sentencia = c.con.createStatement();
        c.sentencia.execute(
                "CREATE USER '" + usuario.getNombre() + "'@'" + usuario.getHost() + "' "
                + "IDENTIFIED BY '" + usuario.getPass() + "'");
        c.sentencia.close();
    }

    public void borrarUsuario(Usuario usuario) throws SQLException {
        c.sentencia = c.con.createStatement();
        c.sentencia.execute("DROP USER '" + usuario.getNombre() + "'@'" + usuario.getHost() + "'");
        c.sentencia.close();
    }

    public void actualizarUsuario(Usuario antiguo, Usuario nuevo) throws SQLException {
        c.sentencia = c.con.createStatement();
        if(antiguo.compareTo(nuevo) == -1){//si no son iguales
            if(!antiguo.isNombreIgual(nuevo)){
                c.sentencia.execute("RENAME USER '" + antiguo.getNombre() + "'@'" + antiguo.getHost() + "' "
                    + "TO '" + nuevo.getNombre() + "'@'" + nuevo.getHost() + "'");
            }
            c.sentencia.execute("SET PASSWORD FOR '" + nuevo.getNombre() + "'@'" + nuevo.getHost() + "' = PASSWORD('" + nuevo.getPass() + "')");
        }
         c.sentencia.close();
    }

    public List<Usuario> getUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        c.sentencia = c.con.createStatement();
        c.rs = c.sentencia.executeQuery("SELECT User,Host,Password FROM mysql.user");
        while (c.rs.next()) {
            usuarios.add(new Usuario(
                    c.rs.getString("User"),
                    c.rs.getString("Password"),
                    c.rs.getString("Host")));
        }
        c.sentencia.close();
        return usuarios;
    }

    /**
     * si el usuario no esta, se crea
     *
     * @param bd
     * @param usuario
     */
    public void otorgarPrivilegio(List<Privilegio> privilegios, Usuario usuario, BD bd) throws SQLException {
        if (!isUsuario(usuario)) {
            crearUsuario(usuario);
        }
        
        String sentencia = getSentencia(privilegios);
        c.sentencia = c.con.createStatement();
        c.sentencia.execute("GRANT " + sentencia + " ON " + bd.getNombre() + ".* "
                + " TO '" + usuario.getNombre() + "'@'" + usuario.getHost() + "'");
        c.sentencia.execute("FLUSH PRIVILEGES");
        c.sentencia.close();
    }

    /**
     * 
     * @param privilegios
     * @param usuario
     * @param bd
     * @param tabla
     * @throws SQLException 
     */
    public void otorgarPrivilegio(List<Privilegio> privilegios, Usuario usuario, BD bd, Tabla tabla) throws SQLException {
        
        if (!isUsuario(usuario)) {
            crearUsuario(usuario);
        }
        String sentencia = getSentencia(privilegios);
        c.sentencia = c.con.createStatement();
        c.sentencia.execute("GRANT " + sentencia + " ON " + bd.getNombre() + "." + tabla.getNombre() + " "
                + " TO '" + usuario.getNombre() + "'@'" + usuario.getHost() + "'");
        c.sentencia.execute("FLUSH PRIVILEGES");
        c.sentencia.close();
    }

    public void otorgarPrivilegios(List<Privilegio> privilegios, Usuario usuario, BD bd, List<Tabla> tablas) throws SQLException {
        if (!isUsuario(usuario)) {
            crearUsuario(usuario);
        }
        for(Tabla t : tablas){
            otorgarPrivilegio(privilegios, usuario, bd, t);
        }
    }

    public void actualizarPrivilegios(List<Privilegio> privilegios, Usuario usuario, BD bd, List<Tabla> tablas, boolean sobreEscribirPrivilegios) throws SQLException{
        if(sobreEscribirPrivilegios){
            quitarTodosLosPrivilegio(usuario);
        }
        otorgarPrivilegios(privilegios, usuario, bd, tablas);
    }
    
    public void quitarPrivilegio(List<Privilegio> privilegios, Usuario usuario, BD bd) throws SQLException {
        c.sentencia = c.con.createStatement();
        String sentencia = getSentencia(privilegios);
        System.out.println("REVOKE " + sentencia + " ON " + bd.getNombre() + ".* "
                + "FROM '" + usuario.getNombre() + "'@'" + usuario.getHost() + "'");
        c.sentencia.execute("REVOKE " + sentencia + " ON " + bd.getNombre() + ".* "
                + "FROM '" + usuario.getNombre() + "'@'" + usuario.getHost() + "'");
        c.sentencia.execute("FLUSH PRIVILEGES");
        c.sentencia.close();
    }
    
    public void quitarTodosLosPrivilegio(Usuario usuario) throws SQLException {
        c.sentencia = c.con.createStatement();
        c.sentencia.execute("REVOKE ALL PRIVILEGES, GRANT OPTION FROM '" + usuario.getNombre() + "'@'" + usuario.getHost() + "'");
        c.sentencia.execute("FLUSH PRIVILEGES");
        c.sentencia.close();
    }

    public boolean isUsuario(Usuario usuario) throws SQLException {
        for (Usuario u : this.getUsuarios()) {
            if (u.getNombre().equalsIgnoreCase(usuario.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public void actualizarPassword(Usuario nuevo) throws SQLException {
        c.sentencia = c.con.createStatement();
        c.sentencia.execute("SET PASSWORD FOR '" + nuevo.getNombre() + "'@'" + nuevo.getHost() + "' = PASSWORD('" + nuevo.getPass() + "')");
        c.sentencia.close();
    }

    private String getSentencia(List lista) {
        String s = "";
        for (Object o : lista) {
            if (o instanceof Privilegio) {
                s += ((Privilegio) o).name() + ",";
                if(((Privilegio) o).name().equalsIgnoreCase("ALL")){
                    break;
                }
            } 
        }
        s = s.substring(0, s.length() - 1);
        return s;
    }
}
