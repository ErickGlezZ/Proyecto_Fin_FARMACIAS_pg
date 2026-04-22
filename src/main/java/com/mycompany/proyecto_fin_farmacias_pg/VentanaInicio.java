/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyecto_fin_farmacias_pg;

import Ventanas.VentanaMedicos;
import Ventanas.VentanaPacientes;
import Ventanas.VentanaPrincipal;
import Ventanas.VentanaRecetas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import login.inicioSesion;

/**
 *
 * @author erick
 */
public class VentanaInicio extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaInicio.class.getName());
    private JPanel panelSeleccionado = null;
    
    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaInicio() {
        initComponents();
        
        setLocationRelativeTo(null);
        setSize(1200, 700);
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        // 1. Configura el JFrame para BorderLayout
        getContentPane().setLayout(new BorderLayout());
        
        // 2. Agrega panelMenu al WEST (izquierda)
        getContentPane().add(panelMenu, BorderLayout.WEST);

        // 3. Agrega panelContenido al CENTER (derecha)
        getContentPane().add(panelContenido, BorderLayout.CENTER);

        panelContenido.setLayout(new BorderLayout());
        cargarPanelInicio();
        
        // Panel tipo botón "sin contorno"
        panelInicio.setOpaque(true);
        panelInicio.setBackground(new Color(60, 90, 150)); // color del fondo
        panelInicio.setBorder(null);
        panelInicio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelMedicos.setOpaque(true);
        panelMedicos.setBackground(new Color(30, 41, 59)); // color del fondo
        panelMedicos.setBorder(null);
        panelMedicos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelPacientes.setOpaque(true);
        panelPacientes.setBackground(new Color(30, 41, 59)); // color del fondo
        panelPacientes.setBorder(null);
        panelPacientes.setCursor(new Cursor(Cursor.HAND_CURSOR));

        
        // LABELS no deben tapar el fondo
        lblInicio.setOpaque(false);

        // Evento del botón-panel
        panelInicio.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            if (panelSeleccionado != panelInicio) {
            panelInicio.setBackground(new Color(30, 41, 59));
        }
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
             if (panelSeleccionado != panelInicio) {
            panelInicio.setBackground(new Color(30, 41, 59));
        }
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            System.out.println("Click en Inicio");
            seleccionarPanel(panelInicio);

            panelMedicos.setBackground(new Color(30, 41, 59));

            panelContenido.removeAll();
            panelContenido.add(new VentanaPrincipal());
            panelContenido.revalidate();
            panelContenido.repaint();    
        }
    });
        
        
    //==================LABEL MEDICOS=========================
    
        lblMedicos.setOpaque(false);

        // Evento del botón-panel
        panelMedicos.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
             if (panelSeleccionado != panelMedicos) {
            panelMedicos.setBackground(new Color(30, 41, 59));
        }
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
             if (panelSeleccionado != panelMedicos) {
            panelMedicos.setBackground(new Color(30, 41, 59));
        }
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            System.out.println("Click en Médicos");
            seleccionarPanel(panelMedicos);

            panelInicio.setBackground(new Color(30, 41, 59));
            panelContenido.removeAll();
            panelContenido.add(new VentanaMedicos());
            panelContenido.revalidate();
            panelContenido.repaint();    
        }
    });
        
        
        //==================LABEL PACIENTES====================
    
        lblPacientes.setOpaque(false);

        // Evento del botón-panel
        panelPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
             if (panelSeleccionado != panelPacientes) {
            panelPacientes.setBackground(new Color(30, 41, 59));
        }
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
             if (panelSeleccionado != panelPacientes) {
            panelPacientes.setBackground(new Color(30, 41, 59));
        }
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            System.out.println("Click en Pacientes");
            seleccionarPanel(panelPacientes);

            panelInicio.setBackground(new Color(30, 41, 59));
            panelContenido.removeAll();
            panelContenido.add(new VentanaPacientes());
            panelContenido.revalidate();
            panelContenido.repaint();    
        }
    });
    
    
    //===================LABEL RECETAS========================
    
    lblRecetas.setOpaque(false);
    panelRecetas.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
             if (panelSeleccionado != panelRecetas) {
            panelRecetas.setBackground(new Color(30, 41, 59));
        }
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
             if (panelSeleccionado != panelRecetas) {
            panelRecetas.setBackground(new Color(30, 41, 59));
        }
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            System.out.println("Click en Recetas");
            seleccionarPanel(panelRecetas);

            panelInicio.setBackground(new Color(30, 41, 59));
            panelContenido.removeAll();
            panelContenido.add(new VentanaRecetas());
            panelContenido.revalidate();
            panelContenido.repaint();    
        }
    });
        

    //=================LABEL CERRAR SESION==============
        lblCerrarSesion.setOpaque(false);

        // Evento del botón-panel
        panelCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
             if (panelSeleccionado != panelCerrarSesion) {
            panelCerrarSesion.setBackground(new Color(30, 41, 59));
        }
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
             if (panelSeleccionado != panelCerrarSesion) {
            panelCerrarSesion.setBackground(new Color(30, 41, 59));
        }
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            System.out.println("Click en Cerrar Sesion");
            seleccionarPanel(panelPacientes);

            panelInicio.setBackground(new Color(30, 41, 59));
            /*
            panelContenido.removeAll();
            panelContenido.add(new VentanaPacientes());
            panelContenido.revalidate();
            panelContenido.repaint();  
            */
            VentanaInicio.this.dispose();
            java.awt.EventQueue.invokeLater(() -> new inicioSesion().setVisible(true));
        }
    });
    }   
        
        
        public void cargarPanelInicio() {
        panelContenido.removeAll();
        panelContenido.add(new Ventanas.VentanaPrincipal(), BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }


        private void seleccionarPanel(JPanel panel) {

        // Restaurar el panel anterior
        if (panelSeleccionado != null) {
            panelSeleccionado.setBackground(new Color(30, 41, 59));  // color normal
        }

        // Activar el nuevo panel
        panel.setBackground(new Color(60, 90, 150)); // color seleccionado
        panelSeleccionado = panel;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelInicio = new javax.swing.JPanel();
        lblInicio = new javax.swing.JLabel();
        panelMedicos = new javax.swing.JPanel();
        lblMedicos = new javax.swing.JLabel();
        panelPacientes = new javax.swing.JPanel();
        lblPacientes = new javax.swing.JLabel();
        panelRecetas = new javax.swing.JPanel();
        lblRecetas = new javax.swing.JLabel();
        panelCerrarSesion = new javax.swing.JPanel();
        lblCerrarSesion = new javax.swing.JLabel();
        panelContenido = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelMenu.setBackground(new java.awt.Color(30, 41, 59));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\erick\\OneDrive\\Documentos\\NetBeansProjects\\Proyecto_Fin_FARMACIAS_pg\\src\\main\\java\\img\\logito.png")); // NOI18N

        panelInicio.setBackground(new java.awt.Color(30, 41, 59));

        lblInicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblInicio.setForeground(new java.awt.Color(241, 245, 249));
        lblInicio.setText("INICIO");

        javax.swing.GroupLayout panelInicioLayout = new javax.swing.GroupLayout(panelInicio);
        panelInicio.setLayout(panelInicioLayout);
        panelInicioLayout.setHorizontalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInicioLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelInicioLayout.setVerticalGroup(
            panelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        panelMedicos.setBackground(new java.awt.Color(30, 41, 59));

        lblMedicos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMedicos.setForeground(new java.awt.Color(241, 245, 249));
        lblMedicos.setText("MEDICOS");

        javax.swing.GroupLayout panelMedicosLayout = new javax.swing.GroupLayout(panelMedicos);
        panelMedicos.setLayout(panelMedicosLayout);
        panelMedicosLayout.setHorizontalGroup(
            panelMedicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMedicosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelMedicosLayout.setVerticalGroup(
            panelMedicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMedicos, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        panelPacientes.setBackground(new java.awt.Color(30, 41, 59));

        lblPacientes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPacientes.setForeground(new java.awt.Color(241, 245, 249));
        lblPacientes.setText("PACIENTES");

        javax.swing.GroupLayout panelPacientesLayout = new javax.swing.GroupLayout(panelPacientes);
        panelPacientes.setLayout(panelPacientesLayout);
        panelPacientesLayout.setHorizontalGroup(
            panelPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPacientesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelPacientesLayout.setVerticalGroup(
            panelPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPacientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        panelRecetas.setBackground(new java.awt.Color(30, 41, 59));

        lblRecetas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRecetas.setForeground(new java.awt.Color(241, 245, 249));
        lblRecetas.setText("RECETAS");

        javax.swing.GroupLayout panelRecetasLayout = new javax.swing.GroupLayout(panelRecetas);
        panelRecetas.setLayout(panelRecetasLayout);
        panelRecetasLayout.setHorizontalGroup(
            panelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRecetasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRecetasLayout.setVerticalGroup(
            panelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblRecetas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        panelCerrarSesion.setBackground(new java.awt.Color(30, 41, 59));

        lblCerrarSesion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCerrarSesion.setForeground(new java.awt.Color(241, 245, 249));
        lblCerrarSesion.setText("CERRAR SESIÓN");

        javax.swing.GroupLayout panelCerrarSesionLayout = new javax.swing.GroupLayout(panelCerrarSesion);
        panelCerrarSesion.setLayout(panelCerrarSesionLayout);
        panelCerrarSesionLayout.setHorizontalGroup(
            panelCerrarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCerrarSesionLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelCerrarSesionLayout.setVerticalGroup(
            panelCerrarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMedicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelPacientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(panelRecetas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelContenido.setBackground(new java.awt.Color(46, 61, 84));
        panelContenido.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout panelContenidoLayout = new javax.swing.GroupLayout(panelContenido);
        panelContenido.setLayout(panelContenidoLayout);
        panelContenidoLayout.setHorizontalGroup(
            panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 907, Short.MAX_VALUE)
        );
        panelContenidoLayout.setVerticalGroup(
            panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelContenido, javax.swing.GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelContenido, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(() -> new VentanaInicio().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblMedicos;
    private javax.swing.JLabel lblPacientes;
    private javax.swing.JLabel lblRecetas;
    private javax.swing.JPanel panelCerrarSesion;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelMedicos;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelPacientes;
    private javax.swing.JPanel panelRecetas;
    // End of variables declaration//GEN-END:variables
}
