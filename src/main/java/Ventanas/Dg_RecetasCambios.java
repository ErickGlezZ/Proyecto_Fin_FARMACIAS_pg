/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Ventanas;

import Controlador.RecetaController;
import Dao.RecetaDAO;
import Modelo.Receta;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.util.Date;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author erick
 */
public class Dg_RecetasCambios extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Dg_RecetasCambios.class.getName());

    /**
     * Creates new form Dg_RecetasCambios
     */
    private int idReceta;
    private RecetaController controller;
    public Dg_RecetasCambios(java.awt.Frame parent, boolean modal, int idReceta) {
        super(parent, modal);
        initComponents();
        setTitle("Editar Recetas");  
        setSize(320, 630);           
        setLocationRelativeTo(null);  
        setResizable(false); 
        controller = new RecetaController(RecetaDAO.getInstancia());
        
        this.idReceta = idReceta;
        cargarMedicosEnCombo();
        cargarPacientesEnCombo();  
        
        try {
        MaskFormatter formatoFecha = new MaskFormatter("##/##/####");
        formatoFecha.setPlaceholderCharacter('_');

        cajaFechaCambios.setFormatterFactory(
            new DefaultFormatterFactory(formatoFecha)
        );

        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        cargarDatosReceta();
    }
    
    
    private void limpiarCampos(){
        
        cbSSNMedicoCambios.setSelectedIndex(0);
        cbSSNPacienteCambios.setSelectedIndex(0);
        cajaMedicamentoCambios.setText("");
        cajaFechaCambios.setText("");
        cajaCantidadCambios.setText("");
        cbUnidadCambios.setSelectedIndex(0);
        cajaIndicacionesCambios.setText("");
    }
    
    private void cargarMedicosEnCombo(){
        cbSSNMedicoCambios.removeAllItems();
        
        cbSSNMedicoCambios.addItem("Elije Médico...");
        ResultSet rs = controller.obtenerMedicos();
        try {
            while (rs.next()) {
                String ssn = rs.getString("SSN");

                cbSSNMedicoCambios.addItem(ssn);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar médicos");
        }
        
    }
    
    private void actualizarLabelMedico(){
        
        if (cbSSNMedicoCambios.getSelectedItem() == null) return;
        String ssn = cbSSNMedicoCambios.getSelectedItem().toString();
        String nombre = controller.obtenerNombreCompletoMedico(ssn);
        lblMedico.setText("Médico asig. " + nombre);
    }
    
    private void cargarPacientesEnCombo(){
        cbSSNPacienteCambios.removeAllItems();
        
        cbSSNPacienteCambios.addItem("Elije Paciente...");
        ResultSet rs = controller.obtenerPacientes();
        try {
            while (rs.next()) {
                String ssn = rs.getString("SSN");

                cbSSNPacienteCambios.addItem(ssn);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar médicos");
        }
        
    }
    
    private void actualizarLabelPaciente(){
        
        if (cbSSNPacienteCambios.getSelectedItem() == null) return;
        String ssn = cbSSNPacienteCambios.getSelectedItem().toString();
        String nombre = controller.obtenerNombreCompletoPaciente(ssn);
        lblPaciente.setText("Paciente asig. " + nombre);
    }
    
    
    private void cargarDatosReceta() {

        ResultSet rs = controller.obtenerRecetaPorId(idReceta);

        try {

            if (rs.next()) {

                cbSSNMedicoCambios.setSelectedItem(rs.getString("ssn_medico"));

                cbSSNPacienteCambios.setSelectedItem(rs.getString("ssn_paciente"));

                cajaMedicamentoCambios.setText(rs.getString("medicamento"));

                cajaFechaCambios.setText(
                        rs.getDate("fecha")
                          .toLocalDate()
                          .format(
                              java.time.format.DateTimeFormatter
                                      .ofPattern("dd/MM/yyyy")
                          )
                );

                cajaCantidadCambios.setText(rs.getString("cantidad"));

                cbUnidadCambios.setSelectedItem(rs.getString("unidad"));

                cajaIndicacionesCambios.setText(rs.getString("indicaciones"));

                actualizarLabelMedico();
                actualizarLabelPaciente();
            }

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar datos"
            );
        }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cajaIndicacionesCambios = new javax.swing.JTextField();
        btnEditarRecetas = new javax.swing.JButton();
        cbSSNMedicoCambios = new javax.swing.JComboBox<>();
        cbSSNPacienteCambios = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cajaMedicamentoCambios = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cajaCantidadCambios = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lblMedico = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblPaciente = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cajaFechaCambios = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        cbUnidadCambios = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cajaIndicacionesCambios.setBackground(new java.awt.Color(71, 85, 105));

        btnEditarRecetas.setBackground(new java.awt.Color(40, 40, 40));
        btnEditarRecetas.setText("EDITAR");
        btnEditarRecetas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarRecetasActionPerformed(evt);
            }
        });

        cbSSNMedicoCambios.setBackground(new java.awt.Color(71, 85, 105));
        cbSSNMedicoCambios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSSNMedicoCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSSNMedicoCambiosActionPerformed(evt);
            }
        });

        cbSSNPacienteCambios.setBackground(new java.awt.Color(71, 85, 105));
        cbSSNPacienteCambios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSSNPacienteCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSSNPacienteCambiosActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(241, 245, 249));
        jLabel2.setText("SSN_Medico:");

        cajaMedicamentoCambios.setBackground(new java.awt.Color(71, 85, 105));

        jLabel3.setForeground(new java.awt.Color(241, 245, 249));
        jLabel3.setText("SSN_Paciente:");

        cajaCantidadCambios.setBackground(new java.awt.Color(71, 85, 105));

        jLabel4.setForeground(new java.awt.Color(241, 245, 249));
        jLabel4.setText("Medicamento:");

        lblMedico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMedico.setForeground(new java.awt.Color(241, 245, 249));
        lblMedico.setText("jLabel9");

        jLabel5.setForeground(new java.awt.Color(241, 245, 249));
        jLabel5.setText("Fecha:");

        lblPaciente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPaciente.setForeground(new java.awt.Color(241, 245, 249));
        lblPaciente.setText("jLabel11");

        jLabel6.setForeground(new java.awt.Color(241, 245, 249));
        jLabel6.setText("Cantidad:");

        cajaFechaCambios.setBackground(new java.awt.Color(71, 85, 105));

        jLabel7.setForeground(new java.awt.Color(241, 245, 249));
        jLabel7.setText("Unidad:");

        cbUnidadCambios.setBackground(new java.awt.Color(71, 85, 105));
        cbUnidadCambios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elije Unidad...", "mg", "g", "mcg", "ml", "tabletas", "cápsulas", "gotas", "ampolletas" }));

        jLabel8.setForeground(new java.awt.Color(241, 245, 249));
        jLabel8.setText("Indicaciones:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cajaCantidadCambios, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                            .addComponent(cajaFechaCambios)
                            .addComponent(cbUnidadCambios, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbSSNPacienteCambios, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cajaMedicamentoCambios)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnEditarRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cajaIndicacionesCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMedico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbSSNMedicoCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(14, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSSNMedicoCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSSNPacienteCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaMedicamentoCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(cajaFechaCambios))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cajaCantidadCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(cbUnidadCambios))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cajaIndicacionesCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditarRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void btnEditarRecetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarRecetasActionPerformed
        String fechaTexto = cajaFechaCambios.getText();

        if (fechaTexto.contains("_")) {
            JOptionPane.showMessageDialog(null, "Completa la fecha");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            Date fecha = sdf.parse(fechaTexto);
            LocalDate fechaLocal = fecha.toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate();
            //java.sql.Date fechaBD = new java.sql.Date(fecha.getTime());

            System.out.println("Fecha lista para BD: " + fechaLocal);

            Receta r = new Receta(cbSSNMedicoCambios.getSelectedItem().toString(),
                cbSSNPacienteCambios.getSelectedItem().toString(),
                cajaMedicamentoCambios.getText(),
                fechaLocal,
                Integer.parseInt(cajaCantidadCambios.getText()),
                cbUnidadCambios.getSelectedItem().toString(),
                cajaIndicacionesCambios.getText());

                r.setId_receta(idReceta);
                
            if (controller.editar(r)) {
                JOptionPane.showMessageDialog(this,"Registro Editado CORRECTAMENTE");
                System.out.println("Registro Editado CORRECTAMENTE");

                limpiarCampos();
                dispose();
            }else{
                JOptionPane.showMessageDialog(this,"Error en la insercion");
                System.out.println("ERROR en la insercion");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fecha inválida");
        }
    }//GEN-LAST:event_btnEditarRecetasActionPerformed

    private void cbSSNMedicoCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSSNMedicoCambiosActionPerformed
        actualizarLabelMedico();
    }//GEN-LAST:event_cbSSNMedicoCambiosActionPerformed

    private void cbSSNPacienteCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSSNPacienteCambiosActionPerformed
        actualizarLabelPaciente();
    }//GEN-LAST:event_cbSSNPacienteCambiosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dg_RecetasCambios dialog = new Dg_RecetasCambios(new javax.swing.JFrame(), true, 1);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditarRecetas;
    private javax.swing.JTextField cajaCantidadCambios;
    private javax.swing.JFormattedTextField cajaFechaCambios;
    private javax.swing.JTextField cajaIndicacionesCambios;
    private javax.swing.JTextField cajaMedicamentoCambios;
    private javax.swing.JComboBox<String> cbSSNMedicoCambios;
    private javax.swing.JComboBox<String> cbSSNPacienteCambios;
    private javax.swing.JComboBox<String> cbUnidadCambios;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblPaciente;
    // End of variables declaration//GEN-END:variables
}
