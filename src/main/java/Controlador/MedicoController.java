package Controlador;

import Interfaces.IMedicoDAO;
import Modelo.Medico;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;

public class MedicoController {

    //private MedicoDAO dao;
    private IMedicoDAO dao;

    public MedicoController(IMedicoDAO dao) {
        this.dao = dao;
    }

    // ================= CARGAR TABLA =================
    public ResultSetTableModel obtenerTodos() {
        try {
            return dao.obtenerTodos();
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar los médicos", e);
        }
    }

    // ================= FILTRO =================
    public ResultSetTableModel filtrar(String campo, String texto) {
        try {
            return dao.filtrar(campo, texto);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar médicos", e);
        }
    }
    
    public ResultSet obtenerMedicoPorSSN(String ssn){
        try {
            return dao.obtenerMedicoPorSSN(ssn);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener SSN de Medicos", e);
        }
    }

    // ================= AGREGAR =================
    public boolean agregar(Medico m) {

        validar(m);

        return dao.agregar(m);
    }

    // ================= EDITAR =================
    public boolean editar(Medico m) {

        validar(m);

        return dao.editar(m);
    }

    // ================= ELIMINAR =================
    public boolean eliminar(String ssn) {

        if (ssn == null || ssn.isEmpty()) {
            throw new IllegalArgumentException("SSN inválido");
        }

        return dao.eliminar(ssn);
    }
    
    
    public int contarPacientes(String ssn) {
        return dao.contarPacientes(ssn);
    }

    // ================= VALIDACIONES =================
    private void validar(Medico m) {

        if (m.getSsn() == null || m.getSsn().isEmpty()) {
            throw new IllegalArgumentException("El SSN está vacío");
        }

        if (m.getNombre() == null || m.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre está vacío");
        }

        if (m.getEspecialidad().equals("Elige Especialidad...")) {
            throw new IllegalArgumentException("Selecciona una especialidad válida");
        }

        if (m.getAños() < 0) {
            throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos");
        }
    }
}