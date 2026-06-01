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
    private String tipoEntrada = "numerosg";
    private PacienteController controller;
    
    public VentanaPacientes() {
        initComponents();
        controller = new PacienteController(PacienteDAO.getInstancia());
        
        
        cargarTabla();
        
        
        timerBusqueda = new Timer(300, e -> {
            
            
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
                    
                    tablaRegPacientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                    
                    tablaRegPacientes.getColumnModel().getColumn(0).setPreferredWidth(110);
                    tablaRegPacientes.getColumnModel().getColumn(1).setPreferredWidth(200);
                    tablaRegPacientes.getColumnModel().getColumn(2).setPreferredWidth(60);
                    tablaRegPacientes.getColumnModel().getColumn(3).setPreferredWidth(110);
                    tablaRegPacientes.getColumnModel().getColumn(4).setPreferredWidth(314);
                    tablaRegPacientes.getColumnModel().getColumn(5).setPreferredWidth(40);
                    tablaRegPacientes.getColumnModel().getColumn(6).setPreferredWidth(40);
                    
                    if (tablaRegPacientes.getColumnCount() >= 7) {
                        // Columna EDITAR
                        tablaRegPacientes.getColumnModel().getColumn(5)
                                .setCellRenderer(new EditarRenderer());

                        tablaRegPacientes.getColumnModel().getColumn(5)
                                .setMaxWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(5)
                                .setMinWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(5)
                                .setHeaderValue("");



                        // Columna ELIMINAR
                        tablaRegPacientes.getColumnModel().getColumn(6)
                                .setCellRenderer(new EliminarRenderer());

                        tablaRegPacientes.getColumnModel().getColumn(6)
                                .setMaxWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(6)
                                .setMinWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(6)
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
                int totalRecetas = controller.contarRecetas(ssnPaciente);

                String mensaje;

                // Si NO tiene relaciones
                if (totalRecetas == 0) {

                    mensaje = """
                               ¿Seguro que deseas eliminar este médico?
                               """;

                } else {

                    // Si tiene relaciones
                    mensaje = "El paciente seleccionado tiene "
                            + totalRecetas + " recetas asociadas "

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
        
        
        cbFiltro.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {

            String opcion = cbFiltro.getSelectedItem().toString();

            cajaBusquedaPacientes.setText("");

            switch (opcion) {

                case "SSN Paciente":
                case "SSN Médico":
                    tipoEntrada = "numerosg"; // permite guiones
                    break;

                case "Nombre":
                case "Apellido Paterno":
                case "Apellido Materno":
                case "Calle":
                case "Colonia":
                    tipoEntrada = "letras";
                    break;

                case "Edad":
                case "Número":
                case "Código Postal":
                    tipoEntrada = "numeros";
                    break;

                default:
                    tipoEntrada = "libre";
                    break;
            }
        }
    });
        
        
        
        
        cajaBusquedaPacientes.addKeyListener(new java.awt.event.KeyAdapter() {
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

                case "libre":
                    // no bloquea nada
                    break;
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
        jLabel11 = new javax.swing.JLabel();
        cbFiltro = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "SSN", "Nombre Completo", "Edad", "SSN Medico Cabecera", "Direccion"
            }
        ));
        jScrollPane1.setViewportView(tablaRegPacientes);

        jLabel11.setBackground(new java.awt.Color(241, 245, 249));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Filtro");

        cbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SSN Paciente", "Nombre", "Apellido Paterno", "Apellido Materno", "Edad", "SSN Médico", "Calle", "Número", "Colonia", "Código Postal" }));
        cbFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFiltroActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/paccc.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cajaBusquedaPacientes))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnLimpiarPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAgregarPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 358, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpiarPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cajaBusquedaPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
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
                    
                    tablaRegPacientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                    
                    tablaRegPacientes.getColumnModel().getColumn(0).setPreferredWidth(110);
                    tablaRegPacientes.getColumnModel().getColumn(1).setPreferredWidth(200);
                    tablaRegPacientes.getColumnModel().getColumn(2).setPreferredWidth(60);
                    tablaRegPacientes.getColumnModel().getColumn(3).setPreferredWidth(110);
                    tablaRegPacientes.getColumnModel().getColumn(4).setPreferredWidth(314);
                    tablaRegPacientes.getColumnModel().getColumn(5).setPreferredWidth(40);
                    tablaRegPacientes.getColumnModel().getColumn(6).setPreferredWidth(40);
                    
                    if (tablaRegPacientes.getColumnCount() >= 7) {
                        // Columna EDITAR
                        tablaRegPacientes.getColumnModel().getColumn(5)
                                .setCellRenderer(new EditarRenderer());

                        tablaRegPacientes.getColumnModel().getColumn(5)
                                .setMaxWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(5)
                                .setMinWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(5)
                                .setHeaderValue("");



                        // Columna ELIMINAR
                        tablaRegPacientes.getColumnModel().getColumn(6)
                                .setCellRenderer(new EliminarRenderer());

                        tablaRegPacientes.getColumnModel().getColumn(6)
                                .setMaxWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(6)
                                .setMinWidth(40);

                        tablaRegPacientes.getColumnModel().getColumn(6)
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
    private javax.swing.JButton btnAgregarPacientes;
    private javax.swing.JButton btnLimpiarPacientes;
    private javax.swing.JTextField cajaBusquedaPacientes;
    private javax.swing.JComboBox<String> cbFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaRegPacientes;
    // End of variables declaration//GEN-END:variables
}
