/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyecto_fin_farmacias_pg;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import login.inicioSesion;

/**
 *
 * @author erick
 */
public class Proyecto_Fin_FARMACIAS_pg {

    public static void main(String[] args) {
        
         try {
            //FlatMacLightLaf.setup();  
            FlatMacDarkLaf.setup();
            
           
            
            UIManager.put("Component.arc", 16);
            UIManager.put("Button.arc", 16);
            UIManager.put("TextComponent.arc", 12);
            UIManager.put("CheckBox.arc", 16);

            UIManager.put("Component.focusWidth", 1);
            UIManager.put("Button.hoverBackground", new Color(96,165,250));
            UIManager.put("Button.pressedBackground", new Color(200, 220, 250));
            UIManager.put("Panel.background", new Color(46,61,84));
           

            UIManager.put("defaultFont", new Font("Inter", Font.PLAIN, 14));
            UIManager.put("Component.font", new Font("Inter", Font.PLAIN, 14));
            UIManager.put("Button.font", new Font("Inter", Font.BOLD, 14));
            UIManager.put("Label.font", new Font("Inter", Font.BOLD, 14));
            

       
            /*
            FlatMacLightLaf.setup();  
            //FlatMacDarkLaf.setup();
            
            UIManager.put("Component.arc", 20);
            UIManager.put("Button.arc", 20);
            UIManager.put("TextComponent.arc", 20);
            UIManager.put("CheckBox.arc", 20);

            UIManager.put("Component.focusWidth", 1);
            UIManager.put("Button.hoverBackground", new Color(220, 235, 255));
            UIManager.put("Button.pressedBackground", new Color(200, 220, 250));

            UIManager.put("defaultFont", new Font("Inter", Font.ITALIC, 12));

        } catch(Exception ex) {
            System.err.println("Error al cargar FlatLaf");
        }
            */
        } catch(Exception ex) {
            System.err.println("Error al cargar FlatLaf");
        }

      
        
        new inicioSesion().setVisible(true);
    }
}
