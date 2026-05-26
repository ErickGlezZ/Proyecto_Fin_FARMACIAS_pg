package Controlador;

import Dao.LoginDAO;
import Interfaces.ILoginDAO;

public class LoginController {

    private ILoginDAO dao;
    
    public LoginController(ILoginDAO dao) {
        this.dao = dao;
    }

    public boolean login(String usuario, String password) {

        return dao.iniciarSesion(usuario, password);
    }
}