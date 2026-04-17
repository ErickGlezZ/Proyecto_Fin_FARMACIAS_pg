/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import ConexionBD.ConexionBD;
import Interfaces.IPacienteDAO;
import Modelo.Paciente;
import Modelo.ResultSetTableModel;
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
    //@Override
    public ResultSetTableModel obtenerTodos() {
        String consulta = "SELECT * FROM pacientes ORDER BY SSN DESC";

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

    // ================= CONSULTA FILTRADA =================
    public ResultSetTableModel obtenerFiltrados(String texto) throws SQLException, ClassNotFoundException {

        String consulta =
                "SELECT * FROM pacientes WHERE " +
                "LOWER(nombre) LIKE ? OR " +
                "LOWER(ape_paterno) LIKE ? OR " +
                "LOWER(ape_materno) LIKE ? OR " +
                "CAST(edad AS TEXT) LIKE ? " +
                "LOWER(ssn_medico_cabecera) LIKE ? OR " +
                "LOWER(calle) LIKE ? OR " +
                "LOWER(numero) LIKE ?" +
                "LOWER(colonia) LIKE ?" +
                "LOWER(codigo_postal) LIKE ?" +
                "CAST(ssn AS TEXT) LIKE ?";

        String valor = "%" + texto.toLowerCase() + "%";

        return new ResultSetTableModel(
                conexionBD.getDriver(),
                conexionBD.getURL(),
                consulta,
                valor, valor, valor, valor, valor, valor, valor, valor, valor, valor
        );
    }
    
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
    
    public String obtenerNombreCompleto(String ssn) {
    String sql = "SELECT Nombre, Ape_Paterno, Ape_Materno FROM Pacientes WHERE SSN = ?";
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
    
    
    public ResultSet obtenerTodosLosMedicos() {
    String sql = "SELECT SSN, Nombre FROM Medicos ORDER BY Nombre";

    return conexionBD.ejecutarConsultaSQL(sql);
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
    public ResultSetTableModel filtrar(String texto) {
        try {
            return obtenerFiltrados(texto);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar pacientes", e);
        }
    }
    
}
