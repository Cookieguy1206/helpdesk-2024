package HelpDesk;

import Controlador.*;
import Modelo.*;
import Vista.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.logging.*;
import javax.mail.*;
import javax.swing.*;

public class HelpDesk {

    static Timer timer;
    static ActionListener AL;

    public static void main(String[] args) throws SQLException, IOException, MessagingException {

        ConsultasProblema Problema = new ConsultasProblema();

        Problema.Estetica();

        AñadirProblema AñadirProblema = new AñadirProblema();
        VerProblema VistaProblema = new VerProblema();
        VistaTicket Soluciones = new VistaTicket();
        VistaAvances VistaAvances = new VistaAvances();
        VistaImagen VistaImagen = new VistaImagen();
        SplashScreen SplashScreen = new SplashScreen();
        ModeloProblema Modelo = new ModeloProblema();
        ModeloPersona ModeloP = new ModeloPersona();
        ModeloSolucion ModeloS = new ModeloSolucion();
        ModeloAvances ModeloA = new ModeloAvances();
        ModeloCorreo ModeloC = new ModeloCorreo();
        ControladorRecibirEmail RecEm = new ControladorRecibirEmail();

        ControladorProblema ControladroProblema = new ControladorProblema(AñadirProblema, VistaProblema, Soluciones, VistaAvances, VistaImagen, SplashScreen, Modelo, ModeloP, ModeloS, ModeloA, ModeloC, Problema, RecEm);
 
        AL = (ActionEvent e) -> {
            if (SplashScreen.BarraCarga.getValue() < 200) {
                SplashScreen.setVisible(true);
                SplashScreen.setLocationRelativeTo(null);
                SplashScreen.BarraCarga.setValue((SplashScreen.BarraCarga.getValue() + 1));
            }
            if (SplashScreen.BarraCarga.getValue() == 150) {
                try {
                    SplashScreen.setVisible(true);
                    SplashScreen.setLocationRelativeTo(null);
                    RecEm.RecibirEmail();
                } catch (MessagingException | IOException | SQLException ex) {
                    Logger.getLogger(HelpDesk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (SplashScreen.BarraCarga.getValue() >= 200) {
                try {
                    timer.stop();
                    SplashScreen.dispose();
                    ControladroProblema.Iniciar();
                } catch (SQLException | IOException | MessagingException ex) {
                    Logger.getLogger(HelpDesk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        timer = new Timer(10, AL);
        timer.start();
    }
}
