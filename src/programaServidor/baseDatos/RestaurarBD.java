package programaServidor.baseDatos;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Esta clase funciona como herramienta para crear una base de datos de pruebas
 * y restaurarla a sus valores iniciales.
 * @author pablo garcia fuentes
 * @author adrian martinez balea
 */

public class RestaurarBD {
    Connection conexion;
    public void conectarse(String propertiesPath){
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

            System.out.println("Conexión exitosa con la base de datos.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void desconectarse(){
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

    // Método para eliminar y recrear las tablas
    public void resetTables() {
        try{
            // Eliminar datos de serAmigos
            deleteAllRowsFromTable(conexion, "serAmigos");

            // Eliminar datos de usuarios
            deleteAllRowsFromTable(conexion, "usuarios");

            // Eliminar la tabla serAmigos
            dropTable(conexion, "serAmigos");

            // Eliminar la tabla usuarios
            dropTable(conexion, "usuarios");

            // Crear la tabla usuarios
            createUsuariosTable(conexion);

            // Crear la tabla serAmigos
            createSerAmigosTable(conexion);

            // Insertar datos en la tabla usuarios
            insertDataIntoUsuariosTable(conexion);

            // Insertar datos en la tabla serAmigos
            insertDataIntoSerAmigosTable(conexion);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar todos los datos de una tabla
    private static void deleteAllRowsFromTable(Connection connection, String tableName) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableName)) {
            statement.executeUpdate();
        }
    }

    // Método para eliminar una tabla
    private static void dropTable(Connection connection, String tableName) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS " + tableName)) {
            statement.executeUpdate();
        }
    }

    // Método para crear la tabla usuarios
    private static void createUsuariosTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE usuarios (" +
                        "nombreUsuario VARCHAR(50) NOT NULL," +
                        "contrasenha CHAR(128) NOT NULL," +
                        "PRIMARY KEY(nombreUsuario)" +
                        ")"
        )) {
            statement.executeUpdate();
        }
    }

    // Método para crear la tabla serAmigos
    private static void createSerAmigosTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE serAmigos (" +
                        "remitente VARCHAR(50) NOT NULL," +
                        "receptor VARCHAR(50) NOT NULL," +
                        "esAceptada BOOLEAN NOT NULL," +
                        "PRIMARY KEY (remitente, receptor)," +
                        "FOREIGN KEY (remitente) REFERENCES usuarios(nombreUsuario) ON DELETE NO ACTION ON UPDATE CASCADE," +
                        "FOREIGN KEY (receptor) REFERENCES usuarios(nombreUsuario) ON DELETE NO ACTION ON UPDATE CASCADE" +
                        ")"
        )) {
            statement.executeUpdate();
        }
    }

    // Método para insertar datos en la tabla usuarios
    private static void insertDataIntoUsuariosTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO usuarios(nombreUsuario, contrasenha) VALUES (?, hash_password(?))"
        )) {
            statement.setString(1, "adrianmartinezbalea");
            statement.setString(2, "amb123");
            statement.executeUpdate();

            statement.setString(1, "pablogarciafuentes");
            statement.setString(2, "pgf123");
            statement.executeUpdate();

            statement.setString(1, "aarongarciafilgueira");
            statement.setString(2, "agf123");
            statement.executeUpdate();

            statement.setString(1, "pablogeb");
            statement.setString(2, "pgeb123");
            statement.executeUpdate();
        }
    }

    // Método para insertar datos en la tabla serAmigos
    private static void insertDataIntoSerAmigosTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO serAmigos(remitente, receptor, esAceptada) VALUES (?, ?, ?)"
        )) {
            statement.setString(1, "adrianmartinezbalea");
            statement.setString(2, "pablogarciafuentes");
            statement.setBoolean(3, true);
            statement.executeUpdate();

            statement.setString(1, "adrianmartinezbalea");
            statement.setString(2, "aarongarciafilgueira");
            statement.setBoolean(3, false);
            statement.executeUpdate();

            statement.setString(1, "pablogarciafuentes");
            statement.setString(2, "aarongarciafilgueira");
            statement.setBoolean(3, false);
            statement.executeUpdate();

            statement.setString(1, "pablogarciafuentes");
            statement.setString(2, "pablogeb");
            statement.setBoolean(3, true);
            statement.executeUpdate();
        }
    }


    public static void main(String args[]) {
        RestaurarBD restaurar = new RestaurarBD();
        restaurar.conectarse("dataBaseFiles/baseDatos.properties");

        restaurar.resetTables();

        restaurar.desconectarse();

        System.out.println("Base de datos restaurada correctamente");

    }
}
