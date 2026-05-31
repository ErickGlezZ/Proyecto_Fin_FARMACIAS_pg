/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import Controlador.LoginController;
import Dao.LoginDAO;
import Recursos.GraficaMedicamentos;
import Recursos.GraficaRecetas;
import Recursos.Reporte;

/**
 *
 * @author erick
 */
public class SistemaFacade {
    
    private LoginController controller;
    
    public SistemaFacade(){
        
        controller = new LoginController(LoginDAO.getInstancia());
    }
    // LOGIN
    public boolean iniciarSesion(String usuario, String password) {

        return controller.login(usuario, password);
    }

    // REPORTES
    public void generarReporteRecetas() {

        Reporte reporte = new Reporte();

        reporte.generarReporteRecetasCompletas();
    }

    // GRAFICA MEDICAMENTOS
    public void generarGraficaMedicamentos() {

        GraficaMedicamentos grafica = new GraficaMedicamentos();

        grafica.generarGraficaMedicamentos();
    }

    // GRAFICA RECETAS
    public void generarGraficaRecetas() {

        GraficaRecetas grafica = new GraficaRecetas();

        grafica.generarGraficaRecetasPorFecha();
    }
}
