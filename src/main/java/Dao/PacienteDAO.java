/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import ConexionBD.ConexionBD;
import Interfaces.IPacienteDAO;
import Modelo.Paciente;
import Modelo.ResultSetTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author erick
 */
public class PacienteDAO implements IPacienteDAO{
    
    private static PacienteDAO instancia;
    
    // Instancia única de conexión
    private ConexionBD conexionBD;

    // Constructor privado
    private PacienteDAO() {
        conexionBD = ConexionBD.getInstancia();
    }
    
    // Método público para obtener la instancia única
    public static PacienteDAO getInstancia() {
        if (instancia == null) {
            instancia = new PacienteDAO();
        }
        return instancia;
    }
    
    // ================= CONSULTA GENERAL =================
    @Override
    public ResultSetTableModel obtenerTodos() {
        //String consulta = "SELECT * FROM pacientes ORDER BY SSN DESC";
        
        String consulta = """
        SELECT ssn,
               nombre,
               ape_paterno,
               ape_materno,
               edad,
               ssn_medico_cabecera,
               calle,
               numero,
               colonia,
               codigo_postal,
               'Y' AS editar,
               'X' AS eliminar
        FROM pacientes
        """;
        
        try {
            return new ResultSetTableModel(
                conexionBD.getDriver(),
                conexionBD.getURL(),
                consulta
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener pacientes", e);
        }
    }
    
    //=============CONSULTA PARA OBTENER SSN===============
    @Override
    public ResultSet obtenerPacientePorSSN(String ssn) {

    String sql = """
        SELECT *
        FROM pacientes
        WHERE ssn = ?
        """;

    try {

        PreparedStatement ps =
                conexionBD.getConexion().prepareStatement(sql);

        ps.setString(1, ssn);

        return ps.executeQuery();

    } catch (SQLException e) {

        e.printStackTrace();
        return null;
    }
}
    
    //====================CONSULTA GENERAL MEDICOS=============
    
    @Override
    public ResultSet obtenerTodosLosMedicos() {
        String sql = "SELECT SSN, Nombre FROM Medicos ORDER BY Nombre";
        return conexionBD.ejecutarConsultaSQL(sql);
    }

    // ================= CONSULTA FILTRADA =================
    public ResultSetTableModel obtenerFiltrados(String campo, String texto)
        throws SQLException, ClassNotFoundException {

        String columna = switch (campo) {
            case "SSN" -> "ssn";
            case "Nombre" -> "nombre";
            case "Apellido Paterno" -> "ape_paterno";
            case "Apellido Materno" -> "ape_materno";
            case "Edad" -> "edad";
            case "SSN Médico" -> "ssn_medico_cabecera";
            case "Calle" -> "calle";
            case "Número" -> "numero";
            case "Colonia" -> "colonia";
            case "Código Postal" -> "codigo_postal";
            default -> "ssn";
        };

        String consulta = """
            SELECT ssn,
                   nombre,
                   ape_paterno,
                   ape_materno,
                   edad,
                   ssn_medico_cabecera,
                   calle,
                   numero,
                   colonia,
                   codigo_postal,
                   'Y' AS editar,
                   'X' AS eliminar
            FROM pacientes
            WHERE CAST(%s AS TEXT) ILIKE ?
            ORDER BY nombre ASC
            """.formatted(columna);

        String valor = "%" + texto + "%";

        return new ResultSetTableModel(
                conexionBD.getDriver(),
                conexionBD.getURL(),
                consulta,
                valor
        );
    }
    /*
    public ResultSetTableModel obtenerFiltrados(String texto) throws SQLException, ClassNotFoundException {

        String consulta =
            "SELECT * FROM pacientes WHERE " +
            "LOWER(nombre) LIKE ? OR " +
            "LOWER(ape_paterno) LIKE ? OR " +
            "LOWER(ape_materno) LIKE ? OR " +
            "CAST(edad AS TEXT) LIKE ? OR " +
            "LOWER(ssn_medico_cabecera) LIKE ? OR " +
            "LOWER(calle) LIKE ? OR " +
            "LOWER(numero) LIKE ? OR " +
            "LOWER(colonia) LIKE ? OR " +
            "LOWER(codigo_postal) LIKE ? OR " +
            "CAST(ssn AS TEXT) LIKE ?";

        String valor = "%" + texto.toLowerCase() + "%";

        return new ResultSetTableModel(
                conexionBD.getDriver(),
                conexionBD.getURL(),
                consulta,
                valor, valor, valor, valor, valor, valor, valor, valor, valor, valor
        );
    }
    */
    //============================ALTAS=====================
    
    public boolean agregarPaciente(Paciente paciente){
    String sql = "INSERT INTO pacientes (SSN, Nombre, Ape_Paterno, Ape_Materno, Edad, SSN_Medico_Cabecera, Calle, Numero, Colonia, Codigo_Postal) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    return conexionBD.ejecutarInstruccionLMD(sql, 
            paciente.getSsn(),
            paciente.getNombre(),
            paciente.getApePaterno(),
            paciente.getApeMaterno(),
            paciente.getEdad(),
            paciente.getSsnMedicoCabecera(),
            paciente.getCalle(),
            paciente.getNumero(),
            paciente.getColonia(),
            paciente.getCodigoPostal());
    }
    
    //==========================BAJAS==========================
    public boolean eliminarPaciente(String SSN){
        String sql = "DELETE FROM Pacientes WHERE SSN = ?";
        return conexionBD.ejecutarInstruccionLMD(sql, SSN);
    }
    
    //========================CAMBIOS=======================
    
    public boolean editarPaciente(Paciente paciente){
        String sql = "UPDATE Pacientes SET Nombre = ?, Ape_Paterno = ?, Ape_Materno = ?, Edad = ?, SSN_Medico_Cabecera = ?, Calle = ?, Numero = ?, Colonia = ?, Codigo_Postal = ? WHERE SSN = ?";

        
        return conexionBD.ejecutarInstruccionLMD(sql, 
                paciente.getNombre(),
                paciente.getApePaterno(),
                paciente.getApeMaterno(),
                paciente.getEdad(),
                paciente.getSsnMedicoCabecera(),
                paciente.getCalle(),
                paciente.getNumero(),
                paciente.getColonia(),
                paciente.getCodigoPostal(),
                paciente.getSsn());
    }
    
    //=======================CONSULTAS===================
    @Override
    public String obtenerNombreCompleto(String ssn) {
    String sql = "SELECT Nombre, Ape_Paterno, Ape_Materno FROM Medicos WHERE SSN = ?";
    ResultSet rs = conexionBD.ejecutarConsultaSQL(sql, ssn);

    try {
        if (rs != null && rs.next()) {
            return rs.getString("Nombre") + " " +
                   rs.getString("Ape_Paterno") + " " +
                   rs.getString("Ape_Materno");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return "No encontrado";
    }
    
    public boolean existePaciente(String SSN) {
        String sql = "SELECT SSN FROM Pacientes WHERE SSN = ?";
        ResultSet rs = conexionBD.ejecutarConsultaSQL(sql, SSN.trim());
        try {
            return rs != null && rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    

    @Override
    public boolean agregar(Paciente p) {
        return agregarPaciente(p);
    }

    @Override
    public boolean eliminar(String ssn) {
        return eliminarPaciente(ssn);
    }

    @Override
    public boolean editar(Paciente p) {
        return editarPaciente(p);
    }

    @Override
    public ResultSetTableModel filtrar(String campo, String texto) {
        try {
            return obtenerFiltrados(campo, texto);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar pacientes", e);
        }
    }
    
}
