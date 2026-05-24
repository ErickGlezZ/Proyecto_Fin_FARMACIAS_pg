/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Recursos;

import ConexionBD.ConexionBD;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;

/**
 *
 * @author erick
 */
public class Reporte {

    public void generarReporteRecetasCompletas() {

        Connection cn = ConexionBD.getInstancia().getConexion();

        if (cn == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "No hay conexión con PostgreSQL"
            );

            return;
        }

        try {

            // ================= CONSULTA PRINCIPAL =================
            String sql = """
                         
                    SELECT
                        id_receta,
                        fecha,
                        medicamento,
                        cantidad,
                        unidad,
                        paciente,
                        medico,
                        especialidad
                         
                    FROM vista_recetas_completas
                         
                    ORDER BY fecha DESC
                    """;

            PreparedStatement ps = cn.prepareStatement(sql);

            // ================= MÉTRICAS =================
            int totalRecetas = 0;
            int totalMedicamentos = 0;

            // TOTAL RECETAS
            ResultSet rsTotalRecetas = cn.prepareStatement("""
                                                           
                    SELECT COUNT(*) AS total
                    FROM recetas
                                                           
                    """).executeQuery();

            if (rsTotalRecetas.next()) {

                totalRecetas = rsTotalRecetas.getInt("total");
            }

            // TOTAL MEDICAMENTOS DISTINTOS
            ResultSet rsMedicamentos = cn.prepareStatement("""
                                                           
                    SELECT COUNT(DISTINCT medicamento)
                    AS total
                                                           
                    FROM recetas
                                                           
                    """).executeQuery();

            if (rsMedicamentos.next()) {

                totalMedicamentos = rsMedicamentos.getInt("total");
            }

            // ================= TOP MÉDICO =================
            String topMedico = "Sin información";

            ResultSet rsTop = cn.prepareStatement("""
                                                  
                    SELECT
                        medico,
                        COUNT(*) AS total
                                                  
                    FROM vista_recetas_completas
                                                  
                    GROUP BY medico
                                                  
                    ORDER BY total DESC
                                                  
                    LIMIT 1
                                                  
                    """).executeQuery();

            if (rsTop.next()) {

                topMedico =
                        rsTop.getString("medico")
                        + " - "
                        + rsTop.getInt("total")
                        + " recetas";
            }

            // ================= ARCHIVO PDF =================
            String nombreArchivo = "Reporte_Recetas_Completas.pdf";

            File archivo = new File(nombreArchivo);

            // ================= GENERAR REPORTE =================
            DynamicReports.report()

                    .setDataSource(ps.executeQuery())

                    // ================= TÍTULO =================
                    .title(

                            DynamicReports.cmp.verticalList(

                                    DynamicReports.cmp.text(
                                            "REPORTE GENERAL DE RECETAS"
                                    )
                                            .setStyle(
                                                    DynamicReports.stl.style()
                                                            .bold()
                                                            .setFontSize(22)
                                            ),

                                    DynamicReports.cmp.verticalGap(10),

                                    DynamicReports.cmp.text(
                                            "Sistema de Gestión Farmacéutica"
                                    ),

                                    DynamicReports.cmp.text(
                                            "Fecha de generación: "
                                            + java.time.LocalDate.now()
                                    ),

                                    DynamicReports.cmp.verticalGap(20),

                                    // ================= MÉTRICAS =================
                                    DynamicReports.cmp.text(
                                            "📄 Total de recetas registradas: "
                                            + totalRecetas
                                    )
                                            .setStyle(
                                                    DynamicReports.stl.style()
                                                            .bold()
                                                            .setFontSize(14)
                                            ),

                                    DynamicReports.cmp.text(
                                            "💊 Medicamentos distintos recetados: "
                                            + totalMedicamentos
                                    )
                                            .setStyle(
                                                    DynamicReports.stl.style()
                                                            .bold()
                                                            .setFontSize(14)
                                            ),

                                    DynamicReports.cmp.text(
                                            "🏆 Médico con más recetas: "
                                            + topMedico
                                    )
                                            .setStyle(
                                                    DynamicReports.stl.style()
                                                            .bold()
                                                            .setFontSize(14)
                                            ),

                                    DynamicReports.cmp.verticalGap(20)
                            )
                    )

                    // ================= COLUMNAS =================
                    .columns(

                            Columns.column(
                                    "ID",
                                    "id_receta",
                                    DataTypes.integerType()
                            ),

                            Columns.column(
                                    "Fecha",
                                    "fecha",
                                    DataTypes.dateType()
                            ),

                            Columns.column(
                                    "Medicamento",
                                    "medicamento",
                                    DataTypes.stringType()
                            ),

                            Columns.column(
                                    "Cantidad",
                                    "cantidad",
                                    DataTypes.integerType()
                            ),

                            Columns.column(
                                    "Unidad",
                                    "unidad",
                                    DataTypes.stringType()
                            ),

                            Columns.column(
                                    "Paciente",
                                    "paciente",
                                    DataTypes.stringType()
                            ),

                            Columns.column(
                                    "Médico",
                                    "medico",
                                    DataTypes.stringType()
                            ),

                            Columns.column(
                                    "Especialidad",
                                    "especialidad",
                                    DataTypes.stringType()
                            )
                    )

                    // ================= ESTILO =================
                    .highlightDetailEvenRows()

                    // ================= FOOTER =================
                    .pageFooter(
                            DynamicReports.cmp.pageXofY()
                    )

                    // ================= EXPORTAR =================
                    .toPdf(new FileOutputStream(nombreArchivo));

            // ================= ABRIR PDF =================
            if (Desktop.isDesktopSupported()) {

                Desktop.getDesktop().open(archivo);
            }

            JOptionPane.showMessageDialog(
                    null,
                    "Reporte generado correctamente"
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Error al generar el reporte:\n" + e.getMessage()
            );
        }
    }
}