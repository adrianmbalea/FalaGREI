package programaServidor.baseDatos;

import java.io.FileInputStream;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Esta clase permite una conexion con una base de datos
 * a partir de un fichero .properties
 * @author pablo garcia fuentes
 * @author adrian martinez balea
 */

public class ConexionBD {
    private Connection conexion;

    /**
     * Constructor de la conexion con la base de datos
     * @param propertiesPath path al fichero con las propiedades de la BD
     */
    public ConexionBD(String propertiesPath) {
        try {
            Properties configuracion = new Properties();
            FileInputStream arqConfiguracion = new FileInputStream(propertiesPath);
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));

            conexion = DriverManager.getConnection("jdbc:" + gestor + "://" +
                            configuracion.getProperty("servidor") + ":" +
                            configuracion.getProperty("puerto") + "/" +
                            configuracion.getProperty("baseDatos"),
                    usuario);

            conexion.setAutoCommit(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo para cerrar la conexion con la base de datos
     */
    public void cerrarConexion() {
        try {
            // Cerrar la conexión
            if (conexion != null) {
                conexion.close();
                System.out.println("Conexión con la base de datos cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }



    /* CONSULTAS */

    /**
     * Comprueba si existe un usuario a partir de su nombre de usuario
     * @param nombreUsuario nombre de usuario del usuario a comprobar si existe
     * @return true si existe el usuario, false si no existe
     */
    public boolean existeUsuario(String nombreUsuario){
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;
        boolean resultado = false;

        try {
            stmUsuario=this.conexion.prepareStatement("SELECT nombreUsuario\n" +
                    "FROM usuarios\n" +
                    "WHERE nombreUsuario = ?;");
            stmUsuario.setString(1, nombreUsuario);
            rsUsuario=stmUsuario.executeQuery();
            resultado = rsUsuario.next(); // el resultado es true si existe una fila
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmUsuario.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    /**
     * Comprueba que un usuario se haya identificado correctamente
     * @param nombreUsuario nombre de usuario del usuario a validar
     * @param contrasenha contrasenha del usuario a validar
     * @return true si existe un usuario con esos atributos, false si no existe
     */
    public boolean validarUsuario(String nombreUsuario, String contrasenha){
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;
        boolean resultado = false;

        try {
            stmUsuario=this.conexion.prepareStatement("SELECT nombreUsuario FROM usuarios WHERE nombreUsuario=? AND contrasenha=hash_password(?);");
            stmUsuario.setString(1, nombreUsuario);
            stmUsuario.setString(2, contrasenha);
            rsUsuario=stmUsuario.executeQuery();
            resultado = rsUsuario.next(); // el resultado es true si existe una fila
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmUsuario.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }


    /**
     * Comprueba si existe una solicitud de amistad pendiente entre dos usuarios
     * @param nombreUsuarioRemitente nombre de usuario del usuario que envio la solicitud
     * @param nombreUsuarioReceptor nombre de usuario del usuario que recibio la solicitud
     * @return true si existe esa solicitud, false si no existe
     */
    public boolean existeSolicitudPendiente(String nombreUsuarioRemitente, String nombreUsuarioReceptor){
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;
        boolean resultado = false;

        try {
            stmUsuario=this.conexion.prepareStatement("SELECT *\n" +
                    "FROM serAmigos\n" +
                    "WHERE (remitente = ? AND receptor = ? AND esAceptada = false);");
            stmUsuario.setString(1, nombreUsuarioRemitente);
            stmUsuario.setString(2, nombreUsuarioReceptor);
            rsUsuario=stmUsuario.executeQuery();
            resultado = rsUsuario.next();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmUsuario.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    /**
     * Comprueba si dos usuarios son amigos, independientemente de quien
     * haya enviado la solicitud a quien
     * @param nombreUsuario1 nombre de usuario del primero de los usuarios a comprobar si son amigos
     * @param nombreUsuario2 nombre de usuario del segundo de los usuarios a comprobar si son amigos
     * @return true si son amigos, false si no lo son
     */
    public boolean sonAmigos(String nombreUsuario1, String nombreUsuario2){
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;
        boolean resultado = false;

        try {
            stmUsuario=this.conexion.prepareStatement("SELECT *\n" +
                    "FROM serAmigos\n" +
                    "WHERE (remitente = ? AND receptor = ? AND esAceptada = true)\n" +
                    "       OR (remitente = ? AND receptor = ? AND esAceptada = true);");
            stmUsuario.setString(1, nombreUsuario1);
            stmUsuario.setString(2, nombreUsuario2);
            stmUsuario.setString(3, nombreUsuario2);
            stmUsuario.setString(4, nombreUsuario1);
            rsUsuario=stmUsuario.executeQuery();
            resultado = rsUsuario.next();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmUsuario.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    /**
     * Lista todos los nombres de usuario de los amigos de un usuario dado
     * @param nombreUsuario nombre de usuario del usuario cuyos amigos se listan
     * @return lista de nombres de usuario de los amigos del usuario dado por parametro
     */
    public Set<String> listarAmigos(String nombreUsuario){
        PreparedStatement stmAmigos=null;
        ResultSet rsAmigos;
        Set<String> amigos = null;

        try {
            stmAmigos=this.conexion.prepareStatement("SELECT CASE\n" +
                    "         WHEN remitente = ? THEN receptor\n" +
                    "         ELSE remitente\n" +
                    "       END AS amigo\n" +
                    "FROM serAmigos\n" +
                    "WHERE (remitente = ? OR receptor = ?)\n" +
                    "  AND esAceptada = true;");
            stmAmigos.setString(1, nombreUsuario);
            stmAmigos.setString(2, nombreUsuario);
            stmAmigos.setString(3, nombreUsuario);
            rsAmigos=stmAmigos.executeQuery();
            amigos = new HashSet<>();
            while (rsAmigos.next()) {
                amigos.add(rsAmigos.getString("amigo"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmAmigos.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return amigos;
    }

    /**
     * Lista todas las solicitudes de amistad pendientes enviadas por un usuario dado
     * @param nombreUsuario nombre de usuario del usuario a listar sus solicitudes enviadas
     * @return lista de los nombres de usuario a los cuales el usuario dado ha enviado una solicitud y esta pendiente
     */
    public Set<String> listarSolicitudesEnviadas(String nombreUsuario){
        PreparedStatement stmSolicitudesEnviadas=null;
        ResultSet rsSolicitudesEnviadas;
        Set<String> solicitudesEnviadas = null;

        try {
            stmSolicitudesEnviadas=this.conexion.prepareStatement("SELECT receptor\n" +
                    "FROM serAmigos\n" +
                    "WHERE remitente = ? AND esAceptada = false;");
            stmSolicitudesEnviadas.setString(1, nombreUsuario);
            rsSolicitudesEnviadas=stmSolicitudesEnviadas.executeQuery();
            solicitudesEnviadas = new HashSet<>();
            while (rsSolicitudesEnviadas.next()) {
                solicitudesEnviadas.add(rsSolicitudesEnviadas.getString("receptor"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmSolicitudesEnviadas.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return solicitudesEnviadas;
    }


    /**
     * Lista todas las solicitudes de amistad pendientes que ha recibido un usuario dado
     * @param nombreUsuario nombre de usuario del usuario a listar sus solicitudes recibidas
     * @return lista de los nombres de usuario que han enviado una solicitud (y esta pendiente) al usuario dado
     */
    public Set<String> listarSolicitudesRecibidas(String nombreUsuario){
        PreparedStatement stmSolicitudesRecibidas=null;
        ResultSet rsSolicitudesRecibidas;
        Set<String> solicitudesRecibidas = null;

        try {
            stmSolicitudesRecibidas=this.conexion.prepareStatement("SELECT remitente\n" +
                    "FROM serAmigos\n" +
                    "WHERE receptor = ? AND esAceptada = false;");
            stmSolicitudesRecibidas.setString(1, nombreUsuario);
            rsSolicitudesRecibidas=stmSolicitudesRecibidas.executeQuery();
            solicitudesRecibidas = new HashSet<>();
            while (rsSolicitudesRecibidas.next()) {
                solicitudesRecibidas.add(rsSolicitudesRecibidas.getString("remitente"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmSolicitudesRecibidas.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }
        return solicitudesRecibidas;
    }


    /* TRANSACCIONES */

    /**
     * Envia una solicitud de amistad de un usuario a otro
     * @param nombreUsuarioRemitente nombre de usuario del usuario que desea enviar la solicitud
     * @param nombreUsuarioReceptor nombre de usuario del usuario que recibe la solicitud
     * @return true si la solicitud se ha enviado correctamente, false en cualquier otro caso
     */
    public boolean enviarSolicitudAmistad(String nombreUsuarioRemitente, String nombreUsuarioReceptor){
        // Si el nombre de usuario del receptor no existe, sale
        if(!existeUsuario(nombreUsuarioReceptor)){
            return false;
        }

        // Si ya son amigos o ya se han enviado una solicitud, no se permite enviar la solicitud
        if(sonAmigos(nombreUsuarioRemitente, nombreUsuarioReceptor) ||
                existeSolicitudPendiente(nombreUsuarioRemitente, nombreUsuarioReceptor) ||
                    existeSolicitudPendiente(nombreUsuarioRemitente, nombreUsuarioReceptor)){
                        return false;
        }

        int resultado = 0;
        PreparedStatement stmSolicitud=null;

        try {
            stmSolicitud =this.conexion.prepareStatement("INSERT INTO serAmigos (remitente, receptor, esAceptada)\n" +
                    "    VALUES (?, ?, false);");

            stmSolicitud.setString(1, nombreUsuarioRemitente);
            stmSolicitud.setString(2, nombreUsuarioReceptor);

            resultado = stmSolicitud.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmSolicitud.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado==1; // devuelve true si se ha actualizado 1 fila de la tabla
    }

    /**
     * Permite a un usuario aceptar una solicitud de amistad que haya recibido
     * @param nombreUsuarioRemitente nombre de usuario del usuario que ha enviado la solicitud
     * @param nombreUsuarioReceptor nombre de usuario del usuario que ha recibido la solicitud
     * @return true si se ha podido aceptar correctamente, false en cualquier otro caso
     */
    public boolean aceptarSolicitudAmistad(String nombreUsuarioRemitente, String nombreUsuarioReceptor){
        // Si no hay una solicitud pendiente, se devuelve false
        if(!existeSolicitudPendiente(nombreUsuarioRemitente, nombreUsuarioReceptor)){
            return false;
        }

        int resultado = 0;
        PreparedStatement stmSolicitud=null;

        try {
            stmSolicitud =this.conexion.prepareStatement("""
                        UPDATE serAmigos
                        SET esAceptada = true
                        WHERE (remitente = ? AND receptor = ? AND esAceptada = false);""");

            stmSolicitud.setString(1, nombreUsuarioRemitente); stmSolicitud.setString(2, nombreUsuarioReceptor);

            resultado = stmSolicitud.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmSolicitud.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado==1; // devuelve true si se ha actualizado 1 fila de la tabla
    }

    /**
     * Permite a un usuario rechazar una solicitud de amistad que haya recibido
     * NOTA: Cabe destacar que tambien sirve para cancelar una solicitud de amistad enviada,
     * si quien llama a este metodo es el usuario que envio la solicitud
     * @param nombreUsuarioRemitente nombre de usuario del usuario que ha enviado la solicitud
     * @param nombreUsuarioReceptor nombre de usuario del usuario que ha recibido la solicitud
     * @return true si se ha podido rechazar correctamente, false en cualquier otro caso
     */
    public boolean rechazarSolicitudAmistad(String nombreUsuarioRemitente, String nombreUsuarioReceptor){
        // Si no hay una solicitud pendiente, se devuelve false
        if(!existeSolicitudPendiente(nombreUsuarioRemitente, nombreUsuarioReceptor)){
            return false;
        }

        int resultado = 0;
        PreparedStatement stmSolicitud=null;

        try {
            stmSolicitud =this.conexion.prepareStatement("DELETE FROM serAmigos\n" +
                    "WHERE (remitente = ? AND receptor = ? AND esAceptada = false);");

            stmSolicitud.setString(1, nombreUsuarioRemitente); stmSolicitud.setString(2, nombreUsuarioReceptor);

            resultado = stmSolicitud.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmSolicitud.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado==1; // devuelve true si se ha actualizado 1 fila de la tabla
    }

    /**
     * Permite a un usuario cancelar su relacion de amistad con otro usuario
     * @param nombreUsuario1 nombre de usuario del usuario que desea cancelar su amistad con otro usuario
     * @param nombreUsuario2 nombre de usuario con el que se quiere romper la amistad
     * @return true si se ha podido eliminar la amistad correctamente, false en cualquier otro caso
     */
    public boolean eliminarAmistad(String nombreUsuario1, String nombreUsuario2){
        // Si no son amigos, devuelve false
        if(!sonAmigos(nombreUsuario1, nombreUsuario2)){
            return false;
        }

        int resultado = 0;
        PreparedStatement stmSolicitud=null;

        try {
            stmSolicitud =this.conexion.prepareStatement("DELETE FROM serAmigos\n" +
                    "WHERE (remitente = ? AND receptor = ? AND esAceptada = true)\n" +
                    "   OR (remitente = ? AND receptor = ? AND esAceptada = true);");

            stmSolicitud.setString(1, nombreUsuario1);
            stmSolicitud.setString(2, nombreUsuario2);
            stmSolicitud.setString(3, nombreUsuario2);
            stmSolicitud.setString(4, nombreUsuario1);

            resultado = stmSolicitud.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmSolicitud.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado==1; // devuelve true si se ha actualizado 1 fila de la tabla
    }

    /**
     * Permite registrar a un usuario y anadirlo a la base de datos
     * @param nombreUsuario nombre de usuario del nuevo usuario
     * @param contrasenha contrasenha del nuevo usuario
     * @return true si se ha anadido correctamente, false en otro caso
     */
    public boolean registrarUsuario(String nombreUsuario, String contrasenha){
        // Si ya existe un usuario con ese nombre, devuelve false
        if(existeUsuario(nombreUsuario)){
            return false;
        }

        int resultado = 0;
        PreparedStatement stmSolicitud=null;

        try {
            stmSolicitud =this.conexion.prepareStatement("INSERT INTO usuarios(nombreUsuario, contrasenha) VALUES\n" +
                    "(?, hash_password(?))");

            stmSolicitud.setString(1, nombreUsuario);
            stmSolicitud.setString(2, contrasenha);

            resultado = stmSolicitud.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try {
                stmSolicitud.close();
            } catch (SQLException e){
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado==1; // devuelve true si se ha actualizado 1 fila de la tabla
    }
}
