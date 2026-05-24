/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Medico;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;

/**
 *
 * @author erick
 */
public interface IMedicoDAO {
    boolean agregar(Medico m);
    boolean eliminar(String ssn);
    boolean editar(Medico m);
    
    int contarPacientes(String ssn);
    //int contarRecetas(String ssn);
    int obtenerTotalMedicos();

    ResultSet obtenerMedicoPorSSN(String ssn);
    ResultSetTableModel obtenerTodos();
    ResultSetTableModel filtrar(String campo, String texto);
}
