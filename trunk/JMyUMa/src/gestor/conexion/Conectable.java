/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor.conexion;

/**
 *
 * @author Administrador
 */
public interface Conectable {

    /**
     *
     * @return el servidor del motor de Base de datos
     */
    String getServer();

    /**
     *
     * @return el usuario de la conexión a la base de datos
     */
    String getUser();

    /**
     *
     * @return la password de la conexión a la base de datos
     */
    String getPass();

    /**
     *
     * @return el nombre de la base de datos
     */
    String getBaseDeDatos();
}
