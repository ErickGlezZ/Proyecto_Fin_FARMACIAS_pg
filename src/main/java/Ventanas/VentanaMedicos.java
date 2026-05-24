/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Ventanas;

import Controlador.MedicoController;
import Dao.MedicoDAO;
import Modelo.Medico;
import Modelo.ResultSetTableModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

/**
 *
 * @author erick
 */

public class VentanaMedicos extends javax.swing.JPanel {

    /**
     * Creates new form VentanaMedicos
     */
    private Timer timerBusqueda;
    //private MedicoController controller = new MedicoController();
    private MedicoController controller;
    
    public VentanaMedicos() {
        initComponents();
        
        
        controller = new MedicoController(MedicoDAO.getInstancia());
        
        
        cargarTabla();
        
        timerBusqueda = new Timer(300, e -> {
        
        String campo = cbFiltro.getSelectedItem().toString();
        String texto = cajaBusqueda.getText();

        SwingWorker<ResultSetTableModel, Void> worker = new SwingWorker<>() {

            @Override
            protected ResultSetTableModel doInBackground() throws Exception {
                return controller.filtrar(campo, texto);
            }

            @Override
            protected void done() {
                try {
                    tablaRegMedicos.setModel(get());
                    
                    if (tablaRegMedicos.getColumnCount() >= 8) {
                        // Columna EDITAR
                        tablaRegMedicos.getColumnModel().getColumn(6)
                                .setCellRenderer(new EditarRenderer());

                        tablaRegMedicos.getColumnModel().getColumn(6)
                                .setMaxWidth(40);

                        tablaRegMedicos.getColumnModel().getColumn(6)
                                .setMinWidth(40);

                        tablaRegMedicos.getColumnModel().getColumn(6)
                                .setHeaderValue("");



                        // Columna ELIMINAR
                        tablaRegMedicos.getColumnModel().getColumn(7)
                                .setCellRenderer(new EliminarRenderer());

                        tablaRegMedicos.getColumnModel().getColumn(7)
                                .setMaxWidth(40);

                        tablaRegMedicos.getColumnModel().getColumn(7)
                                .setMinWidth(40);

                        tablaRegMedicos.getColumnModel().getColumn(7)
                                .setHeaderValue("");
                    }
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error en búsqueda");
                }
            }
        };

        worker.execute();
    });

    timerBusqueda.setRepeats(false);
    
    //==========Clic eliminar===============
        tablaRegMedicos.addMouseListener(new java.awt.event.MouseAdapter() {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

            int fila = tablaRegMedicos.rowAtPoint(e.getPoint());
            int columna = tablaRegMedicos.columnAtPoint(e.getPoint());
            
            int colEliminar = tablaRegMedicos.getColumnCount() - 1;
            int colEditar = tablaRegMedicos.getColumnCount() - 2;
            

            // columna del icono
            if (columna == colEliminar) {

                String ssnMedico = tablaRegMedicos.getValueAt(fila, 0).toString();

                int totalPacientes = controller.contarPacientes(ssnMedico);
                //int totalRecetas = controller.contarRecetas(ssnMedico);
                

                String mensaje;

                // Si NO tiene relaciones
                if (totalPacientes == 0) {

                    mensaje = """
                               ¿Seguro que deseas eliminar este médico?
                               """;

                } else {

                    // Si tiene relaciones
                    mensaje = "El médico seleccionado tiene "
                            + totalPacientes + " pacientes asociados "

                            + "Si continúa, toda esta información será eliminada automáticamente.\n\n"

                            + "¿Desea continuar?";
                }

                int confirm = JOptionPane.showConfirmDialog(

                        null,

                        mensaje,

                        "Confirmar eliminación",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {

                    if (controller.eliminar(ssnMedico)) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Registro eliminado correctamente"
                        );

                        cargarTabla();
                        limpiarCampos();

                    } else {

                        JOptionPane.showMessageDialog(
                                null,
                                "Error al eliminar"
                        );
                    }

                } else {

                    tablaRegMedicos.clearSelection();
                }
            }else if(columna == colEditar){
                
                String ssnMedico = tablaRegMedicos.getValueAt(fila, 0).toString();
                
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(VentanaMedicos.this);
                Dg_MedicosCambios dialog = new Dg_MedicosCambios(parent, true, ssnMedico); // modal
                dialog.setVisible(true);
                cargarTabla();
            }
        }
    });
        
        
        tablaRegMedicos.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

        @Override
        public void mouseMoved(java.awt.event.MouseEvent e) {

            int columna = tablaRegMedicos.columnAtPoint(e.getPoint());
            
            int colEliminar = tablaRegMedicos.getColumnCount() - 1;
            int colEditar = tablaRegMedicos.getColumnCount() - 2;
            // columna del icono
            if (columna == colEliminar || columna == colEditar) {

                tablaRegMedicos.setCursor(
                        new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
                );

            } else {

                tablaRegMedicos.setCursor(
                        new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)
                );
            }
        }
    });
    
    //Se llama el metodo para eliminar con suprimir
    tablaRegMedicos.getInputMap(javax.swing.JComponent.WHEN_FOCUSED)
        .put(javax.swing.KeyStroke.getKeyStroke("DELETE"), "eliminar");

    tablaRegMedicos.getActionMap().put("eliminar", new javax.swing.AbstractAction() {
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        eliminarDesdeTabla();
    }
    });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAgregarMedicos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRegMedicos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cajaBusqueda = new javax.swing.JTextField();
        btnLimpiarCampos = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbFiltro = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(46, 61, 84));

        btnAgregarMedicos.setBackground(new java.awt.Color(40, 40, 40));
        btnAgregarMedicos.setForeground(new java.awt.Color(241, 245, 249));
        btnAgregarMedicos.setText("NUEVO");
        btnAgregarMedicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMedicosActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(241, 245, 249));
        jScrollPane1.setForeground(new java.awt.Color(40, 40, 40));

        tablaRegMedicos.setBackground(new java.awt.Color(71, 85, 105));
        tablaRegMedicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SSN", "Nombre", "Apellido Paterno", "Apellido Materno", "Especialidad", "Años Experiencia"
            }
        ));
        jScrollPane1.setViewportView(tablaRegMedicos);

        jLabel3.setText("Buscar");

        cajaBusqueda.setBackground(new java.awt.Color(71, 85, 105));
        cajaBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaBusquedaKeyReleased(evt);
            }
        });

        btnLimpiarCampos.setBackground(new java.awt.Color(40, 40, 40));
        btnLimpiarCampos.setText("LIMPIAR");
        btnLimpiarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarCamposActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(241, 245, 249));
        jLabel8.setText("MEDICOS");

        jLabel1.setText("Filtro");

        cbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SSN", "Nombre", "Apellido Paterno", "Apellido Materno", "Especialidad", "Años Experiencia" }));
        cbFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFiltroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(cajaBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnAgregarMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(471, 471, 471)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregarMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLimpiarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cajaBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void cargarTabla() {

        SwingWorker<ResultSetTableModel, Void> worker = new SwingWorker<>() {

            @Override
            protected ResultSetTableModel doInBackground() throws Exception {
                return controller.obtenerTodos();
            }

            @Override
            protected void done() {
                try {
                    tablaRegMedicos.setModel(get());
                    
                    if (tablaRegMedicos.getColumnCount() >= 8) {
                        // Columna EDITAR
                        tablaRegMedicos.getColumnModel().getColumn(6)
                                .setCellRenderer(new EditarRenderer());

                        tablaRegMedicos.getColumnModel().getColumn(6)
                                .setMaxWidth(40);

                        tablaRegMedicos.getColumnModel().getColumn(6)
                                .setMinWidth(40);

                        tablaRegMedicos.getColumnModel().getColumn(6)
                                .setHeaderValue("");



                        // Columna ELIMINAR
                        tablaRegMedicos.getColumnModel().getColumn(7)
                                .setCellRenderer(new EliminarRenderer());

                        tablaRegMedicos.getColumnModel().getColumn(7)
                                .setMaxWidth(40);

                        tablaRegMedicos.getColumnModel().getColumn(7)
                                .setMinWidth(40);

                        tablaRegMedicos.getColumnModel().getColumn(7)
                                .setHeaderValue("");
                    }
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar datos");
                }
            }
        };

        worker.execute();
    }
    
    //Metodo para borrar con suprimir
    private void eliminarDesdeTabla() {

        int fila = tablaRegMedicos.getSelectedRow();

        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona un registro");
            return;
        }

        String ssn = tablaRegMedicos.getValueAt(fila, 0).toString();

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que quieres eliminar este médico?",
                "Confirmar eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {

            if (controller.eliminar(ssn)) {
                javax.swing.JOptionPane.showMessageDialog(this, "Registro eliminado correctamente");
                cargarTabla();
                limpiarCampos();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al eliminar",
                        "No existe ese registro",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
   public void limpiarCampos(){
        
        cajaBusqueda.setText("");
        cbFiltro.setSelectedIndex(0);
        tablaRegMedicos.clearSelection();

        cargarTabla();
       
    }
    
    
        
    private void btnAgregarMedicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMedicosActionPerformed
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        Dg_MedicosAltas dialog = new Dg_MedicosAltas(parent, true); // modal
        dialog.setVisible(true);
        cargarTabla();
    }//GEN-LAST:event_btnAgregarMedicosActionPerformed

    private void cajaBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaBusquedaKeyReleased
        if (timerBusqueda != null) {
        timerBusqueda.restart();
        }
    }//GEN-LAST:event_cajaBusquedaKeyReleased

    private void btnLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarCamposActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarCamposActionPerformed

    private void cbFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFiltroActionPerformed
        cajaBusqueda.setText("");
        tablaRegMedicos.clearSelection();
        cargarTabla();
    }//GEN-LAST:event_cbFiltroActionPerformed

    
    class EliminarRenderer extends javax.swing.JButton implements javax.swing.table.TableCellRenderer {

    public EliminarRenderer() {

        setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/img/borrar.png")
        ));

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        return this;
    }
}
    
    
    class EditarRenderer extends javax.swing.JButton implements javax.swing.table.TableCellRenderer {

    public EditarRenderer() {

        setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/img/boton-editar.png")
        ));

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        return this;
    }
}
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarMedicos;
    private javax.swing.JButton btnLimpiarCampos;
    private javax.swing.JTextField cajaBusqueda;
    private javax.swing.JComboBox<String> cbFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaRegMedicos;
    // End of variables declaration//GEN-END:variables
}
