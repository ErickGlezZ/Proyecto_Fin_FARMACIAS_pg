/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import ConexionBD.ConexionBD;
import Interfaces.IRecetaDAO;
import Modelo.Receta;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author erick
 */
public class RecetaDAO implements IRecetaDAO{
    
    private static RecetaDAO instancia;
    
    // Instancia única de conexión
    private ConexionBD conexionBD;

    // Constructor privado
    private RecetaDAO() {
        conexionBD = ConexionBD.getInstancia();
    }
    
    // Método público para obtener la instancia única
    public static RecetaDAO getInstancia() {
        if (instancia == null) {
            instancia = new RecetaDAO();
        }
        return instancia;
    }
    
    // ================= CONSULTA GENERAL =================
    @Override
    public ResultSetTableModel obtenerRecetas() {
        String consulta = "SELECT * FROM recetas ORDER BY id_receta DESC";

        try {
            return new ResultSetTableModel(
                conexionBD.getDriver(),
                conexionBD.getURL(),
                consulta
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener recetas", e);
        }
    }
    
    //====================CONSULTA GENERAL MEDICOS=============
    @Override
    public ResultSet obtenerTodosLosMedicos() {
        String sql = "SELECT SSN, Nombre FROM Medicos ORDER BY Nombre";
        return conexionBD.ejecutarConsultaSQL(sql);
    }
    
    @Override
    public ResultSet obtenerTodosLosPacientes() {
        String sql = "SELECT SSN, Nombre FROM Pacientes ORDER BY Nombre";
        return conexionBD.ejecutarConsultaSQL(sql);
    }
    
    // ================= CONSULTA FILTRADA =================
    public ResultSetTableModel obtenerFiltrados(String texto) throws SQLException, ClassNotFoundException {

        String consulta =
            "SELECT * FROM recetas WHERE " +
            "CAST(id_receta AS TEXT) LIKE ? OR " +
            "LOWER(ssn_medico) LIKE ? OR " +
            "LOWER(ssn_paciente) LIKE ? OR " +
            "LOWER(medicamento) LIKE ? OR " +
            "CAST(fecha AS TEXT) LIKE ? OR " +
            "CAST(cantidad AS TEXT) LIKE ? OR " +
            "LOWER(unidad) LIKE ? OR " +
            "LOWER(indicaciones) LIKE ?";

        String valor = "%" + texto.toLowerCase() + "%";

        return new ResultSetTableModel(
                conexionBD.getDriver(),
                conexionBD.getURL(),
                consulta,
                valor, valor, valor, valor, valor, valor, valor, valor
        );
    }
    
    //============================ALTAS=====================
    
    public boolean agregarReceta(Receta receta){
    String sql = "INSERT INTO recetas (Ssn_medico, Ssn_paciente, Medicamento, Fecha, Cantidad, Unidad, Indicaciones) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    return conexionBD.ejecutarInstruccionLMD(sql,        
            receta.getSsn_medico(),
            receta.getSsn_paciente(),
            receta.getMedicamento(),
            receta.getFecha(),
            receta.getCantidad(),
            receta.getUnidad(),
            receta.getIndicaciones());
    }
    
    //==========================BAJAS==========================
    public boolean eliminarReceta(int id_receta){
        String sql = "DELETE FROM recetas WHERE id_receta = ?";
        return conexionBD.ejecutarInstruccionLMD(sql, id_receta);
    }
    
    //========================CAMBIOS=======================
    
    public boolean editarReceta(Receta receta){
        String sql = "UPDATE recetas SET Ssn_medico = ?, Ssn_paciente = ?, Medicamento = ?, Fecha = ?, Cantidad = ?, Unidad = ?, Indicaciones = ? WHERE Id_receta = ?";

        
        return conexionBD.ejecutarInstruccionLMD(sql, 
                receta.getSsn_medico(),
                receta.getSsn_paciente(),
                receta.getMedicamento(),
                receta.getFecha(),
                receta.getCantidad(),
                receta.getUnidad(),
                receta.getIndicaciones());
    }
    
    //=======================CONSULTAS===================
    @Override
    public String obtenerNombreCompletoMedico(String ssn) {
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
    
    @Override
    public String obtenerNombreCompletoPaciente(String ssn) {
        String sql = "SELECT Nombre, Ape_Paterno, Ape_Materno FROM pacientes WHERE SSN = ?";
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
    public boolean agregar(Receta r) {
        return agregarReceta(r);
    }

    @Override
    public boolean eliminar(int id_receta) {
        return eliminarReceta(id_receta);
    }

    @Override
    public boolean editar(Receta r) {
        return editarReceta(r);
    }

    

    @Override
    public ResultSetTableModel filtrar(String texto) {
       try {
            return obtenerFiltrados(texto);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar recetas", e);
        }
    }
}
