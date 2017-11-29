/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.panels.test;

import it.cnr.istc.hsv.abstracts.extra.SensorTypeClassifier;
import it.cnr.istc.hsv.logic.entities.EHouse;
import it.cnr.istc.hsv.logic.entities.ERoom;
import it.cnr.istc.hsv.logic.entities.ESensor;
import it.cnr.istc.hsv.logic.entities.ESensorData;
import it.cnr.istc.hsv.logic.entities.ESensorType;
import it.cnr.istc.hsv.logic.entities.EUser;
import it.cnr.istc.hsv.mqtt.MQTTManager;
import it.cnr.istc.hsv.mqtt.MessageListener;
import it.cnr.istc.hsv.panels.MapPanel;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */
public class MapPanelTester extends javax.swing.JFrame implements MessageListener{

    /**
     * Creates new form MapPanelTester
     */
    public MapPanelTester() {
        initComponents();
        //TESTING HOUSE: 
        MQTTManager.getInstance().addMessageListener(this);

        EUser user = new EUser();
        user.setName("Luca");
        user.setSurname("Coraci");
        EHouse house = new EHouse();
        house.setId(1000l);

        house.getUsers().add(user);
        
        ESensorType luminosityType = new ESensorType();
        luminosityType.setMeaning("light");
        luminosityType.setTypeUnit("int");
        luminosityType.setName(SensorTypeClassifier.SensorTypes.LUMINOSITY.typeName());
        luminosityType.setId(178l);

        ESensorType doorContact = new ESensorType();
        doorContact.setMeaning("is open");
        doorContact.setTypeUnit("boolean");
        doorContact.setName(SensorTypeClassifier.SensorTypes.GAP.typeName());
        doorContact.setId(1l);

        ESensorType pirContact = new ESensorType();
        pirContact.setMeaning("is present");
        pirContact.setTypeUnit("boolean");
        pirContact.setName(SensorTypeClassifier.SensorTypes.PIR.typeName());
        pirContact.setId(1l);

        ESensor s_entranceDoor = new ESensor();
        s_entranceDoor.setId(2l);
        s_entranceDoor.setName("Enrance Door");
        s_entranceDoor.setSensorType(doorContact);
        s_entranceDoor.setxMap(0.2f);
        s_entranceDoor.setyMap(0.2f);
        s_entranceDoor.setMultiSensor(false);

        ESensor s_pir1 = new ESensor();
        s_pir1.setId(3l);
        s_pir1.setName("Pir contact");
        s_pir1.setSensorType(pirContact);
        s_pir1.setxMap(0.3f);
        s_pir1.setyMap(0.3f);
        s_pir1.setMultiSensor(false);
        s_pir1.setLocation("Office");

        ESensor s_pir2 = new ESensor();
        s_pir2.setId(332l);
        s_pir2.setName("Pir contact");
        s_pir2.setSensorType(pirContact);
        s_pir2.setxMap(0.4f);
        s_pir2.setyMap(0.4f);
        s_pir2.setMultiSensor(false);
        s_pir2.setLocation("Bathroom");
        
        ESensor s_pir3 = new ESensor();
        s_pir3.setId(48l);
        s_pir3.setName("Pir contact");
        s_pir3.setSensorType(pirContact);
        s_pir3.setxMap(0.8f);
        s_pir3.setyMap(0.8f);
        s_pir3.setMultiSensor(false);
        s_pir3.setLocation("Entrance");
        
        
        ESensor s_luminosity = new ESensor();
        s_luminosity.setId(42l);
        s_luminosity.setName("Luminosity Sensor");
        s_luminosity.setSensorType(luminosityType);
        s_luminosity.setxMap(0.5f);
        s_luminosity.setyMap(0.85f);
        s_luminosity.setMultiSensor(false);
        s_luminosity.setLocation("Office");

        house.setEntranceDoor(s_entranceDoor);
        house.getSensors().add(s_entranceDoor);
        house.getSensors().add(s_pir1);
        house.getSensors().add(s_pir2);
        house.getSensors().add(s_pir3);
        house.getSensors().add(s_luminosity);

        ERoom office = new ERoom();
        office.setId(10l);
        office.setName("Office");
        office.getSensors().add(s_entranceDoor);
        office.getSensors().add(s_pir1);
        office.getSensors().add(s_luminosity);
        office.setxPuppet(0.7f);
        office.setyPuppet(0.6f);
        office.setX(0.2f);
        office.setY(0.5f);
        office.setSquareX(0.3f);
        office.setSquareY(0.6f);
        office.setSquareWidth(0.25f);
        office.setSquareHeight(0.35f);

        ERoom bagno = new ERoom();
        bagno.setId(18l);
        bagno.setName("Bagno");
        bagno.setxPuppet(0.4f);
        bagno.setyPuppet(0.45f);
        bagno.setX(0.7f);
        bagno.setY(0.3f);
        bagno.getSensors().add(s_pir2);
        
        ERoom entrance = new ERoom();
        entrance.setId(19l);
        entrance.setName("Ingresso");
        entrance.setxPuppet(0.2f);
        entrance.setyPuppet(0.45f);
        entrance.setX(0.7f);
        entrance.setY(0.5f);
        entrance.getSensors().add(s_pir3);

        house.getRooms().add(office);
        house.getRooms().add(bagno);
        house.getRooms().add(entrance);
//
//        this.mapPanel1.setHouse(house);
//        this.mapPanel1.setPirSensor1(s_pir1);
//        this.mapPanel1.setPirSensor2(s_pir2);
//        this.mapPanel1.setPirSensor3(s_pir3);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new JCheckBoxMenuItem();
        mapPanel1 = new MapPanel();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        jMenuItem1 = new JMenuItem();
        jMenuItem2 = new JMenuItem();
        jMenu2 = new JMenu();

        jCheckBoxMenuItem1.setText("Test Mode");
        jCheckBoxMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mapPanel1.setBackground(new Color(255, 255, 255));

        jMenu1.setText("File");

        jMenuItem1.setText("Load F-Data");
        jMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Load Configuration");
        jMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mapPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mapPanel1, GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       this.mapPanel1.loadFake();
       this.repaint();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        MQTTManager.getInstance().setTEST(this.jCheckBoxMenuItem1.isSelected());
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        MQTTManager.getInstance().askConfiguration();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MapPanelTester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MapPanelTester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MapPanelTester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MapPanelTester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MapPanelTester().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JCheckBoxMenuItem jCheckBoxMenuItem1;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private MapPanel mapPanel1;
    // End of variables declaration//GEN-END:variables

    public void setImage(Image img) {
        this.mapPanel1.setImage(img); 
    }

    
    @Override
    public void messageReceived(ESensorData data) {
    }

    @Override
    public void houseArrived(EHouse house) {
        this.mapPanel1.setHouse(house);
    }
}


//"home_name":"test","entrance_id":5,"residentList":[{"name":"luca","surname":"test"}],"roomList":[{"roomtype":"Entrance","name":"entrance","x":0.0,"y":0.0,"squarex":0.0,"squarey":0.0,"squarewidth":0.0,"squareheight":0.0,"xpuppet":0.0,"ypuppet":0.0,"locationList":[{"type":"maindoor","xmap":0,"ymap":0,"sensorList":[{"id":"5","sid":"5-48-0","name":"Sensor","node_id":"5","state":"0","sensortype":"Routing Binary Sensor","value":"False"}]}]},{"roomtype":"Kitchen","name":"kitchen","x":0.0,"y":0.0,"squarex":0.0,"squarey":0.0,"squarewidth":0.0,"squareheight":0.0,"xpuppet":0.0,"ypuppet":0.0,"locationList":[{"type":"fridge","xmap":0,"ymap":0,"sensorList":[{"id":"0","sid":"2-37-0","name":"Switch","node_id":"2","state":"0","sensortype":"Binary Power Switch","value":"False"}]},{"type":"table","xmap":0,"ymap":0,"sensorList":[]}]}],"topology":{"entrance":[],"kitchen":[]}}
//NAME of House: test