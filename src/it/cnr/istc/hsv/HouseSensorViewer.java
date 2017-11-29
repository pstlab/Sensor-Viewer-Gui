/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.cnr.istc.hsv.mqtt.MQTTManager;
import it.cnr.istc.hsv.panels.test.MapPanelTester;
import it.cnr.istc.hsv.tests.Cane;
import it.cnr.istc.hsv.tests.Colleghi;
import it.cnr.istc.hsv.tests.Persona;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */
public class HouseSensorViewer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Image img = null;
        try {
            img = ImageIO.read(new File("./IT4.jpg"));
        } catch (IOException ex) {
            if (img == null) {
                System.out.println("IMG NULL!");
            }
            ex.printStackTrace();
        }

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HouseSensorViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HouseSensorViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HouseSensorViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HouseSensorViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        MQTTManager.getInstance().connect();
        MapPanelTester mapPanelTester = new MapPanelTester();
        mapPanelTester.setImage(img);
        mapPanelTester.setLocationRelativeTo(null);
        mapPanelTester.setVisible(true);

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        
//        Cane merlicchia = new Cane("Merlicchia", false);
//        Cane fida = new Cane("Fida", true);
//        Cane dylan = new Cane("dylan", false);
//        
//        Persona luca = new Persona("Luca", "Coraci");
//        Persona alessandra = new Persona("Alessandra", "Sorrentino");
//        luca.addCane(merlicchia);
//        luca.addCane(fida);
//        alessandra.addCane(dylan);
//        
//        Colleghi colleghi = new Colleghi();
//        colleghi.addPersona(luca);
//        colleghi.addPersona(alessandra);
//        
//        String json = gson.toJson(colleghi);
//        System.out.println("JSON = \n"+json);
//        
//        fida.setNome("Giggetta");
//        
//        
//        
//        String json2 = gson.toJson(colleghi);
//        System.out.println("JSON = \n"+json2);
    }

}
