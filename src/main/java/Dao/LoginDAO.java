package Dao;

import ConexionBD.ConexionBD;
import Interfaces.ILoginDAO;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO implements ILoginDAO {

    private static LoginDAO instancia;

    // Instancia única de conexión
    private ConexionBD conexionBD;

    // Constructor privado
    private LoginDAO() {

        conexionBD = ConexionBD.getInstancia();
    }

    // Método público para obtener instancia única
    public static LoginDAO getInstancia() {

        if (instancia == null) {

            instancia = new LoginDAO();
        }

        return instancia;
    }

    @Override
    public boolean iniciarSesion(String usuario, String password) {

        String sql = """
                     SELECT *
                     FROM usuarios
                     WHERE username = ?
                     AND password = ?
                     """;

        try {

            ResultSet rs = conexionBD.ejecutarConsultaSQL(
                    sql,
                    usuario,
                    password
            );

            return rs.next();

        } catch (SQLException e) {

            System.out.println("Error al iniciar sesión");
            e.printStackTrace();

            return false;
        }
    }
}