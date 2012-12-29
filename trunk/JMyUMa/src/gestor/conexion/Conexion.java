/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor.conexion;

import java.sql.*;

/**
 *
 * @author Administrador
 */
public final class Conexion {

    /**
     * 
     */
    public Connection con;
    /**
     * 
     */
    public Statement sentencia;
    /**
     * 
     */
    public ResultSet rs;
    
    private Conectable dc;

    public Conexion(Conectable dc) {
        this.dc = dc;
    }
    
    

    /**
     * Método para conectar a una base de datos MySQL
     *
     * @param datos un objeto del tipo DatosConexion (es una interface)
     * @throws ClassNotFoundException
     * @throws SQLException
     *
     * @see clases.BD.mysql.DatosConexion
     */
    public void conectar() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://" + dc.getServer() + ":3306/" + dc.getBaseDeDatos() + "";
        con = DriverManager.getConnection(url, dc.getUser(), dc.getPass());
    }
}
