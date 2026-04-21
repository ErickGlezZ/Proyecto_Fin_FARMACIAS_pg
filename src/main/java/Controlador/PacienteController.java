package Controlador;

import Interfaces.IPacienteDAO;
import Modelo.Paciente;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;

public class PacienteController {

    private IPacienteDAO dao;

    
    public PacienteController(IPacienteDAO dao) {
        this.dao = dao;
    }

    // ================= CARGAR TABLA =================
    public ResultSetTableModel obtenerTodos() {
        try {
            return dao.obtenerTodos();
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar los pacientes", e);
        }
    }

    // ================= FILTRAR =================
    public ResultSetTableModel filtrar(String texto) {
        try {
            return dao.filtrar(texto);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar pacientes", e);
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
    
    //=================OBTENER NOMBRE COMPLETO MEDICO===========
    public String obtenerNombreCompleto(String ssn){
        try {
            return dao.obtenerNombreCompleto(ssn);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar nombre completo medicos", e);
        }
    }

    // ================= AGREGAR =================
    public boolean agregar(Paciente p) {
        validar(p);
        return dao.agregar(p);
    }

    // ================= EDITAR =================
    public boolean editar(Paciente p) {
        validar(p);
        return dao.editar(p);
    }

    // ================= ELIMINAR =================
    public boolean eliminar(String ssn) {
        if (ssn == null || ssn.isEmpty()) {
            throw new IllegalArgumentException("SSN inválido");
        }
        return dao.eliminar(ssn);
    }

    // ================= VALIDACIONES =================
    private void validar(Paciente p) {

        if (p.getSsn() == null || p.getSsn().isEmpty()) {
            throw new IllegalArgumentException("El SSN está vacío");
        }

        if (p.getNombre() == null || p.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre está vacío");
        }

        if (p.getEdad() < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }

        if (p.getSsnMedicoCabecera() == null || p.getSsnMedicoCabecera().isEmpty()) {
            throw new IllegalArgumentException("Debe asignar un médico");
        }

        if (p.getCalle() == null || p.getCalle().isEmpty()) {
            throw new IllegalArgumentException("La calle está vacía");
        }

        if (p.getNumero() <= 0) {
            throw new IllegalArgumentException("Número invalido");
        }

        if (p.getColonia() == null || p.getColonia().isEmpty()) {
            throw new IllegalArgumentException("La colonia está vacía");
        }

        if (p.getCodigoPostal() <= 0) {
            throw new IllegalArgumentException("Código postal inválido");
        }
    }
}