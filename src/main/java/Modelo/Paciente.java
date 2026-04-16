/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author erick
 */
public class Paciente {
    private String ssn;
    private String nombre;
    private String apePaterno;
    private String apeMaterno;
    private Byte edad;
    private String ssnMedicoCabecera;
    private String calle;
    private int numero;
    private String colonia;
    private int codigoPostal;

    public Paciente(String ssn, String nombre, String apePaterno, String apeMaterno, Byte edad, String ssnMedicoCabecera, String calle, int numero, String colonia, int codigoPostal) {
        this.ssn = ssn;
        this.nombre = nombre;
        this.apePaterno = apePaterno;
        this.apeMaterno = apeMaterno;
        this.edad = edad;
        this.ssnMedicoCabecera = ssnMedicoCabecera;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
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

    public Byte getEdad() {
        return edad;
    }

    public String getSsnMedicoCabecera() {
        return ssnMedicoCabecera;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getColonia() {
        return colonia;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    @Override
    public String toString() {
        return "Paciente{" + "ssn=" + ssn + ", nombre=" + nombre + ", apePaterno=" + apePaterno + ", apeMaterno=" + apeMaterno + ", edad=" + edad + ", ssnMedicoCabecera=" + ssnMedicoCabecera + ", calle=" + calle + ", numero=" + numero + ", colonia=" + colonia + ", codigoPostal=" + codigoPostal + '}';
    }
    
    
    
}
