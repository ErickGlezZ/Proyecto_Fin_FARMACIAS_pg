/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Ventanas;

import Controlador.PacienteController;
import Dao.PacienteDAO;
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
public class VentanaPacientes extends javax.swing.JPanel {

    /**
     * Creates new form VentanaPacientes
     */
    private Timer timerBusqueda;
    private PacienteController controller;
    private boolean limpiando = false;
    public VentanaPacientes() {
        initComponents();
        controller = new PacienteController(PacienteDAO.getInstancia());
        
        
        cargarTabla();
        
        
        timerBusqueda = new Timer(300, e -> {
            if (cbFiltro.getSelectedIndex() == 0) {
                return;
            }
            
            String campo = cbFiltro.getSelectedItem().toString();
            String texto = cajaBusquedaPacientes.getText();

        SwingWorker<ResultSetTableModel, Void> worker = new SwingWorker<>() {

            @Override
            protected ResultSetTableModel doInBackground() throws Exception {
                return controller.filtrar(campo, texto);
            }

            @Override
            protected void done() {
                try {
                    tablaRegPacientes.setModel(get());
                    
                    if (tablaRegPacientes.getColumnCount() >= 12) {
                        // Columna EDITAR
                        tablaRegPacientes.getColumnModel().getColumn(10)
                                .setCellRenderer(new EditarRenderer());

                        tablaRegPacientes.getColumnModel().getColumn(10)
                                .setMaxWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(10)
                                .setMinWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(10)
                                .setHeaderValue("");



                        // Columna ELIMINAR
                        tablaRegPacientes.getColumnModel().getColumn(11)
                                .setCellRenderer(new EliminarRenderer());

                        tablaRegPacientes.getColumnModel().getColumn(11)
                                .setMaxWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(11)
                                .setMinWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(11)
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
        tablaRegPacientes.addMouseListener(new java.awt.event.MouseAdapter() {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

            int fila = tablaRegPacientes.rowAtPoint(e.getPoint());
            int columna = tablaRegPacientes.columnAtPoint(e.getPoint());
            
            int colEliminar = tablaRegPacientes.getColumnCount() - 1;
            int colEditar = tablaRegPacientes.getColumnCount() - 2;
            

            // columna del icono
            if (columna == colEliminar) {

                String ssnPaciente = tablaRegPacientes.getValueAt(fila, 0).toString();

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro que deseas eliminar esta receta?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {

                    if (controller.eliminar(ssnPaciente)) {

                        JOptionPane.showMessageDialog(null,
                                "Registro eliminado correctamente");

                        cargarTabla();
                        limpiarCampos();

                    } else {

                        JOptionPane.showMessageDialog(null,
                                "Error al eliminar");
                    }
                }else {

                    tablaRegPacientes.clearSelection();
                }
            }else if(columna == colEditar){
                
                String ssnPaciente = tablaRegPacientes.getValueAt(fila, 0).toString();
                
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(VentanaPacientes.this);
                Dg_PacientesCambios dialog = new Dg_PacientesCambios(parent, true, ssnPaciente); // modal
                dialog.setVisible(true);
                cargarTabla();
            }
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

        jLabel1 = new javax.swing.JLabel();
        btnLimpiarPacientes = new javax.swing.JButton();
        btnAgregarPacientes = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        cajaBusquedaPacientes = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRegPacientes = new javax.swing.JTable();
        lblMedicoAsig = new java.awt.Label();
        jLabel11 = new javax.swing.JLabel();
        cbFiltro = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(46, 61, 84));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(241, 245, 249));
        jLabel1.setText("PACIENTES");

        btnLimpiarPacientes.setBackground(new java.awt.Color(40, 40, 40));
        btnLimpiarPacientes.setText("LIMPIAR");
        btnLimpiarPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarPacientesActionPerformed(evt);
            }
        });

        btnAgregarPacientes.setBackground(new java.awt.Color(40, 40, 40));
        btnAgregarPacientes.setText("NUEVO");
        btnAgregarPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPacientesActionPerformed(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(241, 245, 249));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(241, 245, 249));
        jLabel13.setText("Buscar");

        cajaBusquedaPacientes.setBackground(new java.awt.Color(71, 85, 105));
        cajaBusquedaPacientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaBusquedaPacientesKeyReleased(evt);
            }
        });

        tablaRegPacientes.setBackground(new java.awt.Color(71, 85, 105));
        tablaRegPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "SSN", "Nombre", "Ap. Paterno", "Ap. Materno", "Edad", "SSN Medico", "Calle", "Número", "Colonia", "Cod. Postal"
            }
        ));
        jScrollPane1.setViewportView(tablaRegPacientes);

        lblMedicoAsig.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel11.setBackground(new java.awt.Color(241, 245, 249));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Filtro");

        cbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SSN Paciente", "Nombre", "Apellido Paterno", "Apellido Materno", "Edad", "SSN Médico", "Calle", "Número", "Colonia", "Código Postal" }));
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMedicoAsig, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 551, Short.MAX_VALUE)
                        .addComponent(btnLimpiarPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregarPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 1081, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cajaBusquedaPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaBusquedaPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLimpiarPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAgregarPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMedicoAsig, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                    tablaRegPacientes.setModel(get());
                    
                    if (tablaRegPacientes.getColumnCount() >= 12) {
                        // Columna EDITAR
                        tablaRegPacientes.getColumnModel().getColumn(10)
                                .setCellRenderer(new EditarRenderer());

                        tablaRegPacientes.getColumnModel().getColumn(10)
                                .setMaxWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(10)
                                .setMinWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(10)
                                .setHeaderValue("");



                        // Columna ELIMINAR
                        tablaRegPacientes.getColumnModel().getColumn(11)
                                .setCellRenderer(new EliminarRenderer());

                        tablaRegPacientes.getColumnModel().getColumn(11)
                                .setMaxWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(11)
                                .setMinWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(11)
                                .setHeaderValue("");
                    }
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar datos");
                }
            }
        };

        worker.execute();
    }
    public void limpiarCampos(){
        
        cajaBusquedaPacientes.setText("");
        cbFiltro.setSelectedIndex(0);
        tablaRegPacientes.clearSelection();

        cargarTabla();
       
    }
    
    
    
    
    
    
    
    private void btnAgregarPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPacientesActionPerformed
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        Dg_PacientesAltas dialog = new Dg_PacientesAltas(parent, true); // modal
        dialog.setVisible(true);
        cargarTabla();
    }//GEN-LAST:event_btnAgregarPacientesActionPerformed

    private void cajaBusquedaPacientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaBusquedaPacientesKeyReleased
        if (timerBusqueda != null) {
            timerBusqueda.restart();
        }
    }//GEN-LAST:event_cajaBusquedaPacientesKeyReleased

    private void btnLimpiarPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarPacientesActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarPacientesActionPerformed

    private void cbFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFiltroActionPerformed
        cajaBusquedaPacientes.setText("");
        tablaRegPacientes.clearSelection();
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
    private javax.swing.JButton btnAgregarPacientes;
    private javax.swing.JButton btnLimpiarPacientes;
    private javax.swing.JTextField cajaBusquedaPacientes;
    private javax.swing.JComboBox<String> cbFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Label lblMedicoAsig;
    private javax.swing.JTable tablaRegPacientes;
    // End of variables declaration//GEN-END:variables
}
