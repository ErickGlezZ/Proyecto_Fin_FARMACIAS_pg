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
        
        cargarTabla();
        
        
        timerBusqueda = new Timer(300, e -> {
            
            if (cbFiltro.getSelectedIndex() == 0) {
                return;
            }
            
            String campo = cbFiltro.getSelectedItem().toString();
            String texto = cajaBusquedaRecetas.getText();

            SwingWorker<ResultSetTableModel, Void> worker = new SwingWorker<>() {

            @Override
            protected ResultSetTableModel doInBackground() throws Exception {
                return controller.filtrar(campo, texto);
            }

            @Override
            protected void done() {
                try {
                    tablaRegRecetas.setModel(get());
                    
                    if (tablaRegRecetas.getColumnCount() >= 10) {
                        // Columna EDITAR
                        tablaRegRecetas.getColumnModel().getColumn(8)
                                .setCellRenderer(new EditarRenderer());

                        tablaRegRecetas.getColumnModel().getColumn(8)
                                .setMaxWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(8)
                                .setMinWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(8)
                                .setHeaderValue("");



                        // Columna ELIMINAR
                        tablaRegRecetas.getColumnModel().getColumn(9)
                                .setCellRenderer(new EliminarRenderer());

                        tablaRegRecetas.getColumnModel().getColumn(9)
                                .setMaxWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(9)
                                .setMinWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(9)
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
        tablaRegRecetas.addMouseListener(new java.awt.event.MouseAdapter() {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

            int fila = tablaRegRecetas.rowAtPoint(e.getPoint());
            int columna = tablaRegRecetas.columnAtPoint(e.getPoint());
            
            int colEliminar = tablaRegRecetas.getColumnCount() - 1;
            int colEditar = tablaRegRecetas.getColumnCount() - 2;
            

            // columna del icono
            if (columna == colEliminar) {

                int idReceta = Integer.parseInt(tablaRegRecetas.getValueAt(fila, 0).toString());

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro que deseas eliminar esta receta?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {

                    if (controller.eliminar(idReceta)) {

                        JOptionPane.showMessageDialog(null,
                                "Registro eliminado correctamente");

                        cargarTabla();
                        limpiarCampos();

                    } else {

                        JOptionPane.showMessageDialog(null,
                                "Error al eliminar");
                    }
                }else {

                    tablaRegRecetas.clearSelection();
                }
            }else if(columna == colEditar){
                int idReceta = Integer.parseInt(
                    tablaRegRecetas.getValueAt(fila, 0).toString()
                );
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(VentanaRecetas.this);
                Dg_RecetasCambios dialog = new Dg_RecetasCambios(parent, true, idReceta); // modal
                dialog.setVisible(true);
                cargarTabla();
            }
        }
    });
        
        
        tablaRegRecetas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

        @Override
        public void mouseMoved(java.awt.event.MouseEvent e) {

            int columna = tablaRegRecetas.columnAtPoint(e.getPoint());
            
            int colEliminar = tablaRegRecetas.getColumnCount() - 1;
            int colEditar = tablaRegRecetas.getColumnCount() - 2;
            // columna del icono
            if (columna == colEliminar || columna == colEditar) {

                tablaRegRecetas.setCursor(
                        new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
                );

            } else {

                tablaRegRecetas.setCursor(
                        new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)
                );
            }
        }
    });
    }
    
    public void limpiarCampos(){
        cajaBusquedaRecetas.setText("");
        cbFiltro.setSelectedIndex(0);
        tablaRegRecetas.clearSelection();

        cargarTabla();
        
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
                    
                    if (tablaRegRecetas.getColumnCount() >= 10) {
                        // Columna EDITAR
                        tablaRegRecetas.getColumnModel().getColumn(8)
                                .setCellRenderer(new EditarRenderer());

                        tablaRegRecetas.getColumnModel().getColumn(8)
                                .setMaxWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(8)
                                .setMinWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(8)
                                .setHeaderValue("");



                        // Columna ELIMINAR
                        tablaRegRecetas.getColumnModel().getColumn(9)
                                .setCellRenderer(new EliminarRenderer());

                        tablaRegRecetas.getColumnModel().getColumn(9)
                                .setMaxWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(9)
                                .setMinWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(9)
                                .setHeaderValue("");
                    }
                    
                    

                
                
                
                    
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
        btnLimpiarRecetas = new javax.swing.JButton();
        btnNuevoReceta = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cajaBusquedaRecetas = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRegRecetas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        cbFiltro = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(46, 61, 84));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(241, 245, 249));
        jLabel1.setText("RECETAS");

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

        jLabel11.setText("Buscar");

        cajaBusquedaRecetas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaBusquedaRecetasKeyReleased(evt);
            }
        });

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

        jLabel2.setText("Filtro");

        cbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elije Filtro...", "No. Receta", "SSN Médico", "SSN Paciente", "Medicamento", "Fecha", "Cantidad", "Unidad", "Indicaciones" }));
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cajaBusquedaRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpiarRecetas)
                                .addGap(18, 18, 18)
                                .addComponent(btnNuevoReceta)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiarRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoReceta, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaBusquedaRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoRecetaActionPerformed
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        Dg_RecetasAltas dialog = new Dg_RecetasAltas(parent, true); // modal
        dialog.setVisible(true);
        cargarTabla();
    }//GEN-LAST:event_btnNuevoRecetaActionPerformed

    private void cajaBusquedaRecetasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaBusquedaRecetasKeyReleased
         if (timerBusqueda != null) {
            timerBusqueda.restart();
            }
    }//GEN-LAST:event_cajaBusquedaRecetasKeyReleased

    private void btnLimpiarRecetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarRecetasActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarRecetasActionPerformed

    private void cbFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFiltroActionPerformed
        cajaBusquedaRecetas.setText("");
        tablaRegRecetas.clearSelection();
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
    private javax.swing.JButton btnLimpiarRecetas;
    private javax.swing.JButton btnNuevoReceta;
    private javax.swing.JTextField cajaBusquedaRecetas;
    private javax.swing.JComboBox<String> cbFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaRegRecetas;
    // End of variables declaration//GEN-END:variables
}
