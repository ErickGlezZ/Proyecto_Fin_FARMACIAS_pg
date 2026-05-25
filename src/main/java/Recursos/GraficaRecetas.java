/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Recursos;

import ConexionBD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;

/**
 *
 * @author erick
 */
public class GraficaRecetas {
    public void generarGraficaRecetasPorFecha() {

        try {

            Connection cn = ConexionBD.getInstancia().getConexion();

            String sql = """
                         
                    SELECT *
                    FROM fn_recetas_por_fecha()
                         
                    """;

            PreparedStatement ps = cn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            // Dataset de la gráfica
            DefaultCategoryDataset dataset =
                    new DefaultCategoryDataset();

            while (rs.next()) {

                String fecha =
                        rs.getDate("fecha").toString();

                int total =
                        rs.getInt("total_recetas");

                dataset.addValue(
                        total,
                        "Recetas",
                        fecha
                );
            }

            // Crear gráfica
            JFreeChart chart =
                    ChartFactory.createLineChart(

                            "Recetas por Fecha",
                            "Fecha",
                            "Cantidad de Recetas",
                            dataset
                    );

            // Ventana de gráfica
            ChartPanel panel = new ChartPanel(chart);

JFrame ventana = new JFrame("Gráfica de Recetas");

ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

ventana.add(panel);

ventana.pack();

ventana.setLocationRelativeTo(null);

ventana.setVisible(true);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
