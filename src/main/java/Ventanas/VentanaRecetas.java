/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Ventanas;

import Controlador.RecetaController;
import Dao.RecetaDAO;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

/**
 *
 * @author erick
 */
public class VentanaRecetas extends javax.swing.JPanel {

    /**
     * Creates new form VentanaRecetas
     */
    private Timer timerBusqueda;
    private RecetaController controller;
    private boolean limpiando = false;
    public VentanaRecetas() {
        initComponents();
        controller = new RecetaController(RecetaDAO.getInstancia());
        
        cajaNoReceta.setEnabled(false);
        cbMedicosCab.setEnabled(false);
        cbPacientes.setEnabled(false);
        cajaMedicamento.setEnabled(false);
        cajaFecha.setEnabled(false);
        cajaCantidad.setEnabled(false);
        cbUnidad.setEnabled(false);
        cajaIndicaciones.setEnabled(false);
        btnEliminarReceta.setEnabled(false);
        btnEditarReceta.setEnabled(false);
        btnConfirmarReceta.setEnabled(false);
        
        cargarTabla();
        
        
        timerBusqueda = new Timer(300, e -> {
        String texto = cajaBusquedaRecetas.getText();

        SwingWorker<ResultSetTableModel, Void> worker = new SwingWorker<>() {

            @Override
            protected ResultSetTableModel doInBackground() throws Exception {
                return controller.filtrar(texto);
            }

            @Override
            protected void done() {
                try {
                    tablaRegRecetas.setModel(get());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error en búsqueda");
                }
            }
        };

        worker.execute();
    });

        timerBusqueda.setRepeats(false);
        
        
        //Selecion de tabla para llenar campos
        tablaRegRecetas.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int fila = tablaRegRecetas.getSelectedRow();
            
            limpiando = true;
            cargarMedicosEnCombo();
            cargarPacientesEnCombo();
            if (fila != -1) {
                cajaNoReceta.setText(tablaRegRecetas.getValueAt(fila, 0).toString());
                cbMedicosCab.setSelectedItem(tablaRegRecetas.getValueAt(fila, 1).toString());
                cbPacientes.setSelectedItem(tablaRegRecetas.getValueAt(fila, 2).toString());
                cajaMedicamento.setText(tablaRegRecetas.getValueAt(fila, 3).toString());
                cajaFecha.setText(tablaRegRecetas.getValueAt(fila, 4).toString());
                cajaCantidad.setText(tablaRegRecetas.getValueAt(fila, 5).toString());
                cbUnidad.setSelectedItem(tablaRegRecetas.getValueAt(fila, 6).toString());
                cajaIndicaciones.setText(tablaRegRecetas.getValueAt(fila, 7).toString());
                
                limpiando = false;
                actualizarLabelMedico();
                actualizarLabelPaciente();

                btnEliminarReceta.setEnabled(true);
                btnEditarReceta.setEnabled(true);
                }
            }
        });
    }
    
    public void limpiarCampos(){
        limpiando = true;
        
        cajaNoReceta.setText("");
        cbMedicosCab.setSelectedIndex(0);
        cbPacientes.setSelectedIndex(0);
        cajaMedicamento.setText("");
        cajaFecha.setText("");
        cajaCantidad.setText("");
        cbUnidad.setSelectedIndex(0);
        cajaIndicaciones.setText("");
        cajaBusquedaRecetas.setText("");
        lblMedico.setText("");
        lblPaciente.setText("");
    
        limpiando = false;
        
        if (timerBusqueda != null) {
            timerBusqueda.restart();
        }

        btnEliminarReceta.setEnabled(false);
        btnEditarReceta.setEnabled(false);
        btnConfirmarReceta.setEnabled(false);
        habilitarCamposEdicion(false);
    }
    
    public void habilitarCamposEdicion(boolean habilitar){
        
        cbMedicosCab.setEnabled(habilitar);
        cbPacientes.setEnabled(habilitar);
        cajaMedicamento.setEnabled(habilitar);
        cajaFecha.setEnabled(habilitar);
        cajaCantidad.setEnabled(habilitar);
        cbUnidad.setEnabled(habilitar);
        cajaIndicaciones.setEnabled(habilitar);
        btnEditarReceta.setEnabled(habilitar);
    }
    
    private void cargarMedicosEnCombo(){
        cbMedicosCab.removeAllItems();
        
        cbMedicosCab.addItem("Elije Médico...");
        ResultSet rs = controller.obtenerMedicos();
        try {
            while (rs.next()) {
                String ssn = rs.getString("SSN");

                cbMedicosCab.addItem(ssn);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar médicos");
        }
        
    }
    
    private void actualizarLabelMedico(){
        
        if (cbMedicosCab.getSelectedIndex() == 0) return; 
        String ssn = cbMedicosCab.getSelectedItem().toString();
        String nombre = controller.obtenerNombreCompletoMedico(ssn);
        lblMedico.setText("Médico asig. " + nombre);
    }
    
    
    private void cargarPacientesEnCombo(){
        cbPacientes.removeAllItems();
        
        cbPacientes.addItem("Elije Paciente...");
        ResultSet rs = controller.obtenerPacientes();
        try {
            while (rs.next()) {
                String ssn = rs.getString("SSN");

                cbPacientes.addItem(ssn);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar médicos");
        }
        
    }
    
    private void actualizarLabelPaciente(){
        
        if (cbPacientes.getSelectedItem() == null) return;
        String ssn = cbPacientes.getSelectedItem().toString();
        String nombre = controller.obtenerNombreCompletoPaciente(ssn);
        lblPaciente.setText("Paciente asig. " + nombre);
    }
    
    
    
    
    
    
    
    private void cargarTabla() {

        SwingWorker<ResultSetTableModel, Void> worker = new SwingWorker<>() {

            @Override
            protected ResultSetTableModel doInBackground() throws Exception {
                return controller.obtenerRecetas();
            }

            @Override
            protected void done() {
                try {
                    tablaRegRecetas.setModel(get());
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al cargar datos");
                }
            }
        };

        worker.execute();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cajaNoReceta = new javax.swing.JTextField();
        cajaMedicamento = new javax.swing.JTextField();
        cajaFecha = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbMedicosCab = new javax.swing.JComboBox<>();
        cbPacientes = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cajaCantidad = new javax.swing.JTextField();
        cajaIndicaciones = new javax.swing.JTextField();
        lblMedico = new javax.swing.JLabel();
        lblPaciente = new javax.swing.JLabel();
        btnLimpiarRecetas = new javax.swing.JButton();
        btnNuevoReceta = new javax.swing.JButton();
        btnEliminarReceta = new javax.swing.JButton();
        btnEditarReceta = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cajaBusquedaRecetas = new javax.swing.JTextField();
        btnConfirmarReceta = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRegRecetas = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        cbUnidad = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(46, 61, 84));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(241, 245, 249));
        jLabel1.setText("RECETAS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(241, 245, 249));
        jLabel2.setText("No. Receta:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(241, 245, 249));
        jLabel3.setText("SSN Medico:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(241, 245, 249));
        jLabel4.setText("SSN Paciente:");

        cajaNoReceta.setBackground(new java.awt.Color(71, 85, 105));

        cajaMedicamento.setBackground(new java.awt.Color(71, 85, 105));

        cajaFecha.setBackground(new java.awt.Color(71, 85, 105));
        cajaFecha.setText("DD/MM/YYYY");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(241, 245, 249));
        jLabel5.setText("Medicamento:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(241, 245, 249));
        jLabel6.setText("Fecha:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(241, 245, 249));
        jLabel7.setText("Cantidad:");

        cbMedicosCab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMedicosCabActionPerformed(evt);
            }
        });

        cbPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPacientesActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(241, 245, 249));
        jLabel8.setText("Indicaciones:");

        cajaCantidad.setBackground(new java.awt.Color(71, 85, 105));

        cajaIndicaciones.setBackground(new java.awt.Color(71, 85, 105));

        lblMedico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMedico.setForeground(new java.awt.Color(241, 245, 249));
        lblMedico.setText("jLabel9");

        lblPaciente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPaciente.setForeground(new java.awt.Color(241, 245, 249));
        lblPaciente.setText("jLabel9");

        btnLimpiarRecetas.setText("LIMPIAR");
        btnLimpiarRecetas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarRecetasActionPerformed(evt);
            }
        });

        btnNuevoReceta.setText("NUEVO");
        btnNuevoReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoRecetaActionPerformed(evt);
            }
        });

        btnEliminarReceta.setText("ELIMINAR");
        btnEliminarReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarRecetaActionPerformed(evt);
            }
        });

        btnEditarReceta.setText("EDITAR");
        btnEditarReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarRecetaActionPerformed(evt);
            }
        });

        jLabel11.setText("Buscar");

        cajaBusquedaRecetas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaBusquedaRecetasKeyReleased(evt);
            }
        });

        btnConfirmarReceta.setText("GUARDAR");

        tablaRegRecetas.setBackground(new java.awt.Color(71, 85, 105));
        tablaRegRecetas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No. Receta", "SSN Medico", "SSN Paciente", "Medicamento", "Fecha", "Cantidad", "Unidad", "Indicaciones"
            }
        ));
        jScrollPane1.setViewportView(tablaRegRecetas);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Unidad:");

        cbUnidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elije...", "mg", "g", "mcg", "ml", "tabletas", "cápsulas", "gotas", "ampolletas" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cajaFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cajaIndicaciones))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cajaNoReceta, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cajaMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cajaCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbMedicosCab, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnLimpiarRecetas)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnNuevoReceta)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnEliminarReceta)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnEditarReceta))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cajaBusquedaRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnConfirmarReceta)))))
                        .addGap(0, 44, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbUnidad, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cajaNoReceta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cajaMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cajaCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaIndicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiarRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoReceta, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarReceta, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarReceta, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbMedicosCab, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnConfirmarReceta)
                        .addComponent(cajaBusquedaRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoRecetaActionPerformed
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        Dg_RecetasAltas dialog = new Dg_RecetasAltas(parent, true); // modal
        dialog.setVisible(true);
        cargarTabla();
    }//GEN-LAST:event_btnNuevoRecetaActionPerformed

    private void cbMedicosCabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMedicosCabActionPerformed
        if (limpiando) return; 
        actualizarLabelMedico();
    }//GEN-LAST:event_cbMedicosCabActionPerformed

    private void cbPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPacientesActionPerformed
        if (limpiando) return; 
        actualizarLabelPaciente();
    }//GEN-LAST:event_cbPacientesActionPerformed

    private void cajaBusquedaRecetasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaBusquedaRecetasKeyReleased
         if (timerBusqueda != null) {
            timerBusqueda.restart();
            }
    }//GEN-LAST:event_cajaBusquedaRecetasKeyReleased

    private void btnEliminarRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarRecetaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarRecetaActionPerformed

    private void btnLimpiarRecetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarRecetasActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarRecetasActionPerformed

    private void btnEditarRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarRecetaActionPerformed
        habilitarCamposEdicion(true);
        btnEditarReceta.setEnabled(false);
        btnEliminarReceta.setEnabled(false);
        btnNuevoReceta.setEnabled(false);
        btnConfirmarReceta.setEnabled(true);
    }//GEN-LAST:event_btnEditarRecetaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmarReceta;
    private javax.swing.JButton btnEditarReceta;
    private javax.swing.JButton btnEliminarReceta;
    private javax.swing.JButton btnLimpiarRecetas;
    private javax.swing.JButton btnNuevoReceta;
    private javax.swing.JTextField cajaBusquedaRecetas;
    private javax.swing.JTextField cajaCantidad;
    private javax.swing.JTextField cajaFecha;
    private javax.swing.JTextField cajaIndicaciones;
    private javax.swing.JTextField cajaMedicamento;
    private javax.swing.JTextField cajaNoReceta;
    private javax.swing.JComboBox<String> cbMedicosCab;
    private javax.swing.JComboBox<String> cbPacientes;
    private javax.swing.JComboBox<String> cbUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblPaciente;
    private javax.swing.JTable tablaRegRecetas;
    // End of variables declaration//GEN-END:variables
}
