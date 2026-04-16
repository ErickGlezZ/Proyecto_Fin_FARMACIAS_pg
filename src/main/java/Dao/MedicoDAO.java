/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import ConexionBD.ConexionBD;
import Interfaces.IMedicoDAO;
import Modelo.Medico;
import Modelo.ResultSetTableModel;
import java.sql.SQLException;

/**
 *
 * @author erick
 */
public class MedicoDAO implements IMedicoDAO{
    private static MedicoDAO instancia;
    
    // Instancia única de conexión
    private ConexionBD conexionBD;

    // Constructor privado
    private MedicoDAO() {
        conexionBD = ConexionBD.getInstancia();
    }
    
    // Método público para obtener la instancia única
    public static MedicoDAO getInstancia() {
        if (instancia == null) {
            instancia = new MedicoDAO();
        }
        return instancia;
    }
    
    
    // ================= CONSULTA GENERAL =================
    @Override
    public ResultSetTableModel obtenerTodos() {
        String consulta = "SELECT * FROM medicos ORDER BY SSN DESC";

        try {
            return new ResultSetTableModel(
                conexionBD.getDriver(),
                conexionBD.getURL(),
                consulta
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener médicos", e);
        }
    }

    // ================= CONSULTA FILTRADA =================
    public ResultSetTableModel obtenerFiltrados(String texto) throws SQLException, ClassNotFoundException {

        String consulta =
                "SELECT * FROM medicos WHERE " +
                "LOWER(nombre) LIKE ? OR " +
                "LOWER(ape_paterno) LIKE ? OR " +
                "LOWER(ape_materno) LIKE ? OR " +
                "LOWER(especialidad) LIKE ? OR " +
                "CAST(años_experiencia AS TEXT) LIKE ? OR " +
                "CAST(ssn AS TEXT) LIKE ?";

        String valor = "%" + texto.toLowerCase() + "%";

        return new ResultSetTableModel(
                conexionBD.getDriver(),
                conexionBD.getURL(),
                consulta,
                valor, valor, valor, valor, valor, valor
        );
    }

    // ================= ALTAS =================
    public boolean agregarMedico(Medico medico) {

        String sql = "INSERT INTO Medicos (SSN, Nombre, Ape_Paterno, Ape_Materno, Especialidad, Años_Experiencia) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        return conexionBD.ejecutarInstruccionLMD(sql,
                medico.getSsn(),
                medico.getNombre(),
                medico.getApePaterno(),
                medico.getApeMaterno(),
                medico.getEspecialidad(),
                medico.getAños());
    }

    // ================= BAJAS =================
    public boolean eliminarMedico(String ssn) {

        String sql = "DELETE FROM Medicos WHERE SSN = ?";

        return conexionBD.ejecutarInstruccionLMD(sql, ssn);
    }

    // ================= CAMBIOS =================
    public boolean editarMedico(Medico medico) {

        String sql = "UPDATE Medicos SET Nombre = ?, Ape_Paterno = ?, Ape_Materno = ?, Especialidad = ?, Años_Experiencia = ? WHERE SSN = ?";

        return conexionBD.ejecutarInstruccionLMD(sql,
                medico.getNombre(),
                medico.getApePaterno(),
                medico.getApeMaterno(),
                medico.getEspecialidad(),
                medico.getAños(),
                medico.getSsn());
    }

    @Override
    public boolean agregar(Medico m) {
        return agregarMedico(m); 
    }

    @Override
    public boolean eliminar(String ssn) {
        return eliminarMedico(ssn);
    }

    @Override
    public boolean editar(Medico m) {
        return editarMedico(m); 
    }

    @Override
    public ResultSetTableModel filtrar(String texto) {
        try {
            return obtenerFiltrados(texto);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar médicos", e);
        }

    }
}
    
    

