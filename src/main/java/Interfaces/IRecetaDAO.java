/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Receta;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;

/**
 *
 * @author erick
 */
public interface IRecetaDAO {
    
    boolean agregar(Receta r);
    boolean eliminar(int id_receta);
    boolean editar(Receta r);
    
    ResultSetTableModel obtenerRecetas();
    ResultSetTableModel filtrar(String texto);
    ResultSet obtenerTodosLosMedicos();
    ResultSet obtenerTodosLosPacientes();
    String obtenerNombreCompletoMedico(String ssn);
    String obtenerNombreCompletoPaciente(String ssn);
}
