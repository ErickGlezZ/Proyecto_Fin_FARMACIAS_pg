/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Interfaces.IRecetaDAO;
import Modelo.Receta;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;

/**
 *
 * @author erick
 */
public class RecetaController {
    
    private IRecetaDAO dao;

    public RecetaController(IRecetaDAO dao) {
        this.dao = dao;
    }
    
    // ================= CARGAR TABLA =================
    public ResultSetTableModel obtenerRecetas() {
        try {
            return dao.obtenerRecetas();
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar las recetas", e);
        }
    }

    // ================= FILTRAR =================
    public ResultSetTableModel filtrar(String texto) {
        try {
            return dao.filtrar(texto);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar recetas", e);
        }
    }
    
    //==================OBTENER MEDICOS=================
    public ResultSet obtenerMedicos() {
        try {
            return dao.obtenerTodosLosMedicos();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener médicos", e);
        }
    }
    
    //==================OBTENER PACIENTES=================
    public ResultSet obtenerPacientes() {
        try {
            return dao.obtenerTodosLosPacientes();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener pacientes", e);
        }
    }
    
    //=================OBTENER NOMBRE COMPLETO MEDICO===========
    public String obtenerNombreCompletoMedico(String ssn){
        try {
            return dao.obtenerNombreCompletoMedico(ssn);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar nombre completo de medicos", e);
        }
    }
    
    //=================OBTENER NOMBRE COMPLETO PACIENTE===========
    public String obtenerNombreCompletoPaciente(String ssn){
        try {
            return dao.obtenerNombreCompletoPaciente(ssn);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar nombre completo de pacientes", e);
        }
    }
    
    // ================= AGREGAR =================
    public boolean agregar(Receta r) {
        validar(r);
        return dao.agregar(r);
    }

    // ================= EDITAR =================
    public boolean editar(Receta r) {
        validar(r);
        return dao.editar(r);
    }

    // ================= ELIMINAR =================
    public boolean eliminar(int id_receta) {
        
        return dao.eliminar(id_receta);
    }
    
    
    // ================= VALIDACIONES =================
    private void validar(Receta r) {
        
        if (r.getId_receta() <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        if (r.getSsn_medico() == null || r.getSsn_medico().isEmpty()) {
            throw new IllegalArgumentException("El SSN del médico está vacío");
        }

        if (r.getSsn_paciente() == null || r.getSsn_paciente().isEmpty()) {
            throw new IllegalArgumentException("El SSN del paciente está vacío");
        }

        if (r.getMedicamento() == null || r.getMedicamento().isEmpty()) {
            throw new IllegalArgumentException("El medicamento está vacío");
        }

        if (r.getFecha() == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }

        if (r.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        if (r.getUnidad() == null || r.getUnidad().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar una unidad");
        }

        if (r.getIndicaciones() == null || r.getIndicaciones().isEmpty()) {
            throw new IllegalArgumentException("Las indicaciones están vacías");
        }
    }
}
