/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import ConexionBD.ConexionBD;
import Modelo.Medico;
import Modelo.ResultSetTableModel;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author erick
 */
public class MedicoDAO {
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
    
    
    public void actualizarTabla(JTable tabla) {

        JOptionPane loading = new JOptionPane("Consultando datos...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = loading.createDialog("Espere");
        dialog.setModal(false);
        dialog.setVisible(true);


        new Thread(() -> {
            final String CONTROLADOR_JDBC = conexionBD.getDriver();
            final String URL = conexionBD.getURL();
            final String CONSULTA = "SELECT * FROM medicos ORDER BY SSN DESC";

            try {

                Thread.sleep(1000); // 1 segundos

                ResultSetTableModel modelo = new ResultSetTableModel(CONTROLADOR_JDBC, URL, CONSULTA);

                SwingUtilities.invokeLater(() -> {
                    tabla.setModel(modelo);
                    dialog.dispose(); // Oculta mensaje cuando termine

                    if (modelo.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No se encontraron registros con ese valor.");
                        return;
                    }

                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                dialog.dispose();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                dialog.dispose();
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(null, "Error al consultar la base de datos.")
                );
            }
        }).start();
    }
    
    
    //============================ALTAS=====================
    
    public boolean agregarMedico(Medico medico){
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //=====================CONSULTAS========================
    public ResultSetTableModel obtenerMedicosFiltrados(String texto) {
    String consulta = 
        "SELECT * FROM medicos WHERE " +
        "LOWER(nombre) LIKE ? OR " +
        "LOWER(ape_paterno) LIKE ? OR " +
        "LOWER(ape_materno) LIKE ? OR " +
        "LOWER(especialidad) LIKE ? OR " +
        "CAST(años_experiencia AS TEXT) LIKE ? OR " +
        "CAST(ssn AS TEXT) LIKE ?";

    String valor = "%" + texto.toLowerCase() + "%";

    try {
        return new ResultSetTableModel(
            conexionBD.getDriver(),
            conexionBD.getURL(),
            consulta,
            valor, valor, valor, valor, valor, valor
        );
    } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException("Error al filtrar medicos", e);
    }
}
    
    
}
