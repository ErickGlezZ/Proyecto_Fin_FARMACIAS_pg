/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Recursos;

import ConexionBD.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.data.category.DefaultCategoryDataset;
/**
 *
 * @author erick
 */
public class GraficaMedicamentos {
    public void generarGraficaMedicamentos() {

        try {

            Connection cn =
                    ConexionBD.getInstancia().getConexion();

            String sql = """
                         
                    SELECT *
                    FROM fn_medicamentos_mas_recetados()
                         
                    """;

            PreparedStatement ps =
                    cn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            DefaultCategoryDataset dataset =
                    new DefaultCategoryDataset();

            while (rs.next()) {

                String medicamento =
                        rs.getString("medicamento");

                int total =
                        rs.getInt("total_recetas");

                dataset.addValue(
                        total,
                        "Medicamentos",
                        medicamento
                );
            }

            JFreeChart chart =
                    ChartFactory.createBarChart(
                            "Medicamentos Más Recetados",
                            "Medicamento",
                            "Cantidad",
                            dataset
                    );

            ChartPanel panel =
                    new ChartPanel(chart);

            JFrame ventana =
                    new JFrame("Gráfica de Medicamentos");

            ventana.setDefaultCloseOperation(
                    JFrame.DISPOSE_ON_CLOSE
            );

            ventana.add(panel);

            ventana.pack();

            ventana.setLocationRelativeTo(null);

            ventana.setVisible(true);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
