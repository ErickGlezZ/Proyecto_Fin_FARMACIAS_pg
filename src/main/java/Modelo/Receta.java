/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;

/**
 *
 * @author erick
 */
public class Receta {
    //private int id_receta;
    private String ssn_medico;
    private String ssn_paciente;
    private String medicamento;
    private LocalDate fecha;
    private int cantidad;
    private String unidad;
    private String indicaciones;

    public Receta(String ssn_medico, String ssn_paciente, String medicamento, LocalDate fecha, int cantidad, String unidad, String indicaciones) {
        //this.id_receta = id_receta;
        this.ssn_medico = ssn_medico;
        this.ssn_paciente = ssn_paciente;
        this.medicamento = medicamento;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.indicaciones = indicaciones;
    }

    /*
    public int getId_receta() {
        return id_receta;
    }
*/
    public String getSsn_medico() {
        return ssn_medico;
    }

    public String getSsn_paciente() {
        return ssn_paciente;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public String getIndicaciones() {
        return indicaciones;
    }
/*
    @Override
    public String toString() {
        return "Receta{" + "id_receta=" + id_receta + ", ssn_medico=" + ssn_medico + ", ssn_paciente=" + ssn_paciente + ", medicamento=" + medicamento + ", fecha=" + fecha + ", cantidad=" + cantidad + ", unidad=" + unidad + ", indicaciones=" + indicaciones + '}';
    }
*/
    
}
