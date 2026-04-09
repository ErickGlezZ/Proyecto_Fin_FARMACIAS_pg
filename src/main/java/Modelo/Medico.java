/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author erick
 */
public class Medico {
    
    private String ssn;
    private String nombre;
    private String apePaterno;
    private String apeMaterno;
    private String especialidad;
    private Byte Años;

    public Medico(String ssn, String nombre, String apePaterno, String apeMaterno, String especialidad, Byte Años) {
        this.ssn = ssn;
        this.nombre = nombre;
        this.apePaterno = apePaterno;
        this.apeMaterno = apeMaterno;
        this.especialidad = especialidad;
        this.Años = Años;
        
        
    }

    public String getSsn() {
        return ssn;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApePaterno() {
        return apePaterno;
    }

    public String getApeMaterno() {
        return apeMaterno;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public Byte getAños() {
        return Años;
    }

    @Override
    public String toString() {
        return "Medico{" + "ssn=" + ssn + ", nombre=" + nombre + ", apePaterno=" + apePaterno + ", apeMaterno=" + apeMaterno + ", especialidad=" + especialidad + ", A\u00f1os=" + Años + '}';
    }
    
    
}
