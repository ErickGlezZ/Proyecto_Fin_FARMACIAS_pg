/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Modelo.Medico;
import Modelo.ResultSetTableModel;

/**
 *
 * @author erick
 */
public interface IMedicoDAO {
    boolean agregar(Medico m);
    boolean eliminar(String ssn);
    boolean editar(Medico m);

    ResultSetTableModel obtenerTodos();
    ResultSetTableModel filtrar(String texto);
}
