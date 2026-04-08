/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD instanciaConexion;
    private Connection conexion;

    // Datos de tu PostgreSQL
    private final String URL = "jdbc:postgresql://localhost:5432/bd_farmacias";
    private final String USUARIO = "erick";
    private final String CONTRASEÑA = "erick";

    // Constructor privado
    private ConexionBD() {
        try {
            // Cargar el driver JDBC de PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("Conexión a PostgreSQL establecida con éxito");

        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver JDBC de PostgreSQL");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Error al conectar con PostgreSQL");
            e.printStackTrace();
        }
    }

    // Singleton - obtener única instancia
    public static ConexionBD getInstancia() {
        if (instanciaConexion == null) {
            instanciaConexion = new ConexionBD();
        }
        return instanciaConexion;
    }

    // Retornar la conexión
    public Connection getConexion() {
        return conexion;
    }

    public String getURL() {
        return URL;
    }

    public String getDriver() {
        return "org.postgresql.Driver";
    }

    // Método para ejecutar INSERT, UPDATE, DELETE
    public boolean ejecutarInstruccionLMD(String sql, Object... datos) {
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            for (int i = 0; i < datos.length; i++) {
                pstmt.setObject(i + 1, datos[i]);
            }

            return pstmt.executeUpdate() >= 1;

        } catch (SQLException e) {
            System.out.println("Error al ejecutar instrucción LMD en PostgreSQL");
            e.printStackTrace();
            return false;
        }
    }

    // Método para ejecutar SELECT
    public ResultSet ejecutarConsultaSQL(String sql, Object... datos) {
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);

            for (int i = 0; i < datos.length; i++) {
                pstmt.setObject(i + 1, datos[i]);
            }

            return pstmt.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al ejecutar SELECT en PostgreSQL");
            e.printStackTrace();
            return null;
        }
    }

    // Prueba de conexión
    public static void main(String[] args) {
        ConexionBD conexion = ConexionBD.getInstancia();
        new ConexionBD();
    }
    
    // Reconectar si la conexión está cerrada o es nula
    public boolean reconectar() {
        try {
            if (conexion == null || conexion.isClosed()) {

                // Cargar el driver
                Class.forName("org.postgresql.Driver");

                // Reconexión
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
                System.out.println("Reconexión exitosa a PostgreSQL");
                return true;

            } else {
                System.out.println("La conexión ya está activa, no es necesario reconectar.");
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error al reconectar con PostgreSQL");
            e.printStackTrace();
            return false;
        }
    }

}
