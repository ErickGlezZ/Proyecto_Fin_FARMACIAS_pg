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
    private String tipoEntrada = "numeros";
    private RecetaController controller;
    
    public VentanaRecetas() {
        initComponents();
        controller = new RecetaController(RecetaDAO.getInstancia());
        
        cargarTabla();
        
        
        timerBusqueda = new Timer(300, e -> {
            
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
                    
                    tablaRegRecetas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

                    tablaRegRecetas.getColumnModel().getColumn(0).setPreferredWidth(80);   // id_receta
                    tablaRegRecetas.getColumnModel().getColumn(1).setPreferredWidth(100);  // ssn_medico
                    tablaRegRecetas.getColumnModel().getColumn(2).setPreferredWidth(100);  // ssn_paciente
                    tablaRegRecetas.getColumnModel().getColumn(3).setPreferredWidth(190);  // medicamento
                    tablaRegRecetas.getColumnModel().getColumn(4).setPreferredWidth(100);  // fecha
                    tablaRegRecetas.getColumnModel().getColumn(5).setPreferredWidth(224);  // indicaciones
                    tablaRegRecetas.getColumnModel().getColumn(6).setPreferredWidth(40);   // editar
                    tablaRegRecetas.getColumnModel().getColumn(7).setPreferredWidth(40);   // eliminar
                    
                    if (tablaRegRecetas.getColumnCount() >= 8) {
                        // Columna EDITAR
                        tablaRegRecetas.getColumnModel().getColumn(6)
                                .setCellRenderer(new EditarRenderer());

                        tablaRegRecetas.getColumnModel().getColumn(6)
                                .setMaxWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(6)
                                .setMinWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(6)
                                .setHeaderValue("");



                        // Columna ELIMINAR
                        tablaRegRecetas.getColumnModel().getColumn(7)
                                .setCellRenderer(new EliminarRenderer());

                        tablaRegRecetas.getColumnModel().getColumn(7)
                                .setMaxWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(7)
                                .setMinWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(7)
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
                
                int idReceta = Integer.parseInt(tablaRegRecetas.getValueAt(fila, 0).toString());
                
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
        
        
        cbFiltro.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {

            String opcion = cbFiltro.getSelectedItem().toString();

            cajaBusquedaRecetas.setText("");

            switch (opcion) {

                case "No. Receta":
                    tipoEntrada = "numeros";
                    break;

                case "SSN Médico":
                case "SSN Paciente":
                    tipoEntrada = "numerosg"; 
                    break;

                case "Medicamento":
                case "Indicaciones":
                case "Unidad":
                    tipoEntrada = "letras";
                    break;

                case "Cantidad":
                    tipoEntrada = "numeros";
                    break;

                case "Fecha":
                    tipoEntrada = "fecha"; 
                    break;

                default:
                    tipoEntrada = "libre";
                    break;
            }
        }
    });
        
        
        
        cajaBusquedaRecetas.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyTyped(java.awt.event.KeyEvent evt) {

            char c = evt.getKeyChar();

            switch (tipoEntrada) {

                case "letras":
                    if (!Character.isLetter(c) && c != ' ') {
                        evt.consume();
                    }
                    break;

                case "numeros":
                    if (!Character.isDigit(c)) {
                        evt.consume();
                    }
                    break;

                case "numerosg": // SSN o códigos con guiones
                    if (!Character.isDigit(c) && c != '-') {
                        evt.consume();
                    }
                    break;

                case "fecha":
                    if (!Character.isDigit(c) && !Character.isLetter(c) && c != ' ') {
                        evt.consume();
                    }
                    break;

                case "libre":
                    // no bloquea nada
                    break;
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
                    tablaRegRecetas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

                    tablaRegRecetas.getColumnModel().getColumn(0).setPreferredWidth(80);   // id_receta
                    tablaRegRecetas.getColumnModel().getColumn(1).setPreferredWidth(100);  // ssn_medico
                    tablaRegRecetas.getColumnModel().getColumn(2).setPreferredWidth(100);  // ssn_paciente
                    tablaRegRecetas.getColumnModel().getColumn(3).setPreferredWidth(190);  // medicamento
                    tablaRegRecetas.getColumnModel().getColumn(4).setPreferredWidth(100);  // fecha
                    tablaRegRecetas.getColumnModel().getColumn(5).setPreferredWidth(224);  // indicaciones
                    tablaRegRecetas.getColumnModel().getColumn(6).setPreferredWidth(40);   // editar
                    tablaRegRecetas.getColumnModel().getColumn(7).setPreferredWidth(40);   // eliminar
                    
                    if (tablaRegRecetas.getColumnCount() >= 8) {
                        // Columna EDITAR
                        tablaRegRecetas.getColumnModel().getColumn(6)
                                .setCellRenderer(new EditarRenderer());

                        tablaRegRecetas.getColumnModel().getColumn(6)
                                .setMaxWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(6)
                                .setMinWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(6)
                                .setHeaderValue("");



                        // Columna ELIMINAR
                        tablaRegRecetas.getColumnModel().getColumn(7)
                                .setCellRenderer(new EliminarRenderer());

                        tablaRegRecetas.getColumnModel().getColumn(7)
                                .setMaxWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(7)
                                .setMinWidth(40);

                        tablaRegRecetas.getColumnModel().getColumn(7)
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
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(46, 61, 84));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(241, 245, 249));
        jLabel1.setText("RECETAS");

        btnLimpiarRecetas.setBackground(new java.awt.Color(40, 40, 40));
        btnLimpiarRecetas.setText("LIMPIAR");
        btnLimpiarRecetas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarRecetasActionPerformed(evt);
            }
        });

        btnNuevoReceta.setBackground(new java.awt.Color(40, 40, 40));
        btnNuevoReceta.setText("NUEVO");
        btnNuevoReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoRecetaActionPerformed(evt);
            }
        });

        jLabel11.setText("Buscar");

        cajaBusquedaRecetas.setBackground(new java.awt.Color(71, 85, 105));
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

        cbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No. Receta", "SSN Médico", "SSN Paciente", "Medicamento", "Fecha", "Cantidad", "Unidad", "Indicaciones" }));
        cbFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFiltroActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/recccc.png"))); // NOI18N

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
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cajaBusquedaRecetas))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnNuevoReceta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLimpiarRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnLimpiarRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cajaBusquedaRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevoReceta, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
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
                getClass().getResource("/img/delete2.png")
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
                getClass().getResource("/img/editar-informacion.png")
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaRegRecetas;
    // End of variables declaration//GEN-END:variables
}
