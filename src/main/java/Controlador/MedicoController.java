package Controlador;

import Dao.MedicoDAO;
import Modelo.Medico;
import Modelo.ResultSetTableModel;

public class MedicoController {

    private MedicoDAO dao;

    public MedicoController() {
        dao = MedicoDAO.getInstancia();
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
    public ResultSetTableModel filtrar(String texto) {
        try {
            return dao.obtenerFiltrados(texto);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar médicos", e);
        }
    }

    // ================= AGREGAR =================
    public boolean agregar(Medico m) {

        validar(m);

        return dao.agregarMedico(m);
    }

    // ================= EDITAR =================
    public boolean editar(Medico m) {

        validar(m);

        return dao.editarMedico(m);
    }

    // ================= ELIMINAR =================
    public boolean eliminar(String ssn) {

        if (ssn == null || ssn.isEmpty()) {
            throw new IllegalArgumentException("SSN inválido");
        }

        return dao.eliminarMedico(ssn);
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