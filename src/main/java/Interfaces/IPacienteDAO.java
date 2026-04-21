/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Paciente;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;

/**
 *
 * @author erick
 */
public interface IPacienteDAO {
    boolean agregar(Paciente p);
    boolean eliminar(String ssn);
    boolean editar(Paciente p);
    
    ResultSetTableModel obtenerTodos();
    ResultSetTableModel filtrar(String texto);
    ResultSet obtenerTodosLosMedicos();
    String obtenerNombreCompleto(String ssn);
}
