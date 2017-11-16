/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.panels;

import it.cnr.istc.hsv.abstracts.GuiElements;
import it.cnr.istc.hsv.abstracts.HousePlanPanel;
import it.cnr.istc.hsv.abstracts.extra.BlinkableGlassPane;
import it.cnr.istc.hsv.abstracts.extra.DraggableLabel;
import it.cnr.istc.hsv.abstracts.extra.DraggablePanel;
import it.cnr.istc.hsv.abstracts.extra.HSVSetting;
import it.cnr.istc.hsv.abstracts.extra.HouseMapper;
import it.cnr.istc.hsv.abstracts.extra.IconRetriever;
import it.cnr.istc.hsv.abstracts.extra.PositionRevealer;
import it.cnr.istc.hsv.abstracts.extra.SensorTypeClassifier;
import it.cnr.istc.hsv.abstracts.extra.VirtualDataPool;
import it.cnr.istc.hsv.abstracts.extra.dummyreasoner.DummyReasoner;
import it.cnr.istc.hsv.abstracts.extra.dummyreasoner.OutMessagePanel;
import it.cnr.istc.hsv.abstracts.layers.ClockLayer;
import it.cnr.istc.hsv.abstracts.layers.MyJLayer;
import it.cnr.istc.hsv.abstracts.layers.MyLayer;
import it.cnr.istc.hsv.logic.entities.EHouse;
import it.cnr.istc.hsv.logic.entities.ERoom;
import it.cnr.istc.hsv.logic.entities.ESensor;
import it.cnr.istc.hsv.logic.entities.ESensorData;
import it.cnr.istc.hsv.mqtt.MQTTManager;
import static it.cnr.istc.hsv.mqtt.MQTTManager.GET_CONFIG;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */
public class MapPanel extends javax.swing.JPanel implements PositionRevealer {

    private MyLayer<JComponent> layerUI2 = null;
    private HousePlanPanel housePlanPanel1 = new HousePlanPanel();
    private ClockLayer clockLayer = null;

    private boolean saved = false;
    private int dx = 0;
    private int dy = 0;
    public volatile static int editPanelWidth = 0;
    public volatile static int editPanelHeight = 0;
    private DraggableLabel lastPuppetLabel = null;
    private DraggablePanel puppetOutside = null;
    private EHouse house = null;

    public List<DraggableLabel> sensorsLabels = new ArrayList<DraggableLabel>();
    public List<DraggableLabel> roomLabels = new ArrayList<DraggableLabel>();
    public List<DraggableLabel> puppetLabels = new ArrayList<DraggableLabel>();
    public List<DraggablePanel> extraPanels = new ArrayList<DraggablePanel>();
    private DraggablePanel legendDraggablePanel = null;
    private Map<Long, DraggableLabel> roomLabelMap = new HashMap<Long, DraggableLabel>();
//     public static final Icon windowClosed = new javax.swing.ImageIcon(MapPanel.class.getResource("/it/cnr/istc/hsv/images/VentanaChiusa3.png"));

    //TEST
    private ESensor pirSensor1;
    private ESensor pirSensor2;
    private ESensor pirSensor3;
    private ESensor entranceDoor;

    /**
     * Creates new form MapPanel
     */
    public MapPanel() {
        initComponents();

        MQTTManager.getInstance().setMapPanelTest(this);
        revealHeight();
        revealWidht();
        if (!Beans.isDesignTime()) {
            housePlanPanel1.setOpaque(false);
            this.setOpaque(true);
            this.setBackground(GuiElements.GiraffPlus_LightOrange);

            layerUI2 = new BlinkableGlassPane(this.housePlanPanel1);

            JPanel containerP = new JPanel();
            containerP.setLayout(new GridLayout(0, 1));
            containerP.setOpaque(false);

            MyJLayer<JComponent> jlayer = new MyJLayer<JComponent>(housePlanPanel1, layerUI2);
            containerP.add(jlayer);

            clockLayer = new ClockLayer();
            JPanel containerP2 = new JPanel();
            containerP2.setLayout(new GridLayout(0, 1));
            containerP2.add(containerP);
            MyJLayer trup2 = new MyJLayer<JComponent>(containerP, clockLayer);
            containerP2.add(trup2);

            this.jScrollPane1.getViewport().setOpaque(false);
            this.jScrollPane1.setViewportView(containerP2);
            resizeAll();
        }

    }

    private void resizeAll() {
//        if (jToggleButton_Extend.isSelected()) {
//            editPanelWidth = this.jSplitPane1.getWidth() - 280;
//            editPanelHeight = this.jSplitPane1.getHeight();
//        } else {
//            editPanelWidth = this.jSplitPane1.getWidth();
//            editPanelHeight = this.jSplitPane1.getHeight();
//        }
//        if (editPanelWidth == 0) {
//            editPanelHeight = (int) this.jSplitPane1.getPreferredSize().getWidth();
//        }
//        if (editPanelHeight == 0) {
//            editPanelHeight = (int) this.jSplitPane1.getPreferredSize().getHeight();
//        }

//        System.out.println("resize|");
        revealHeight();
        revealWidht();
        for (DraggableLabel draggableLabel : roomLabels) {
            if (draggableLabel.getKx() <= 0 || draggableLabel.getKx() >= 1) {
                continue;
            }
            if (draggableLabel.getKy() <= 0 || draggableLabel.getKy() >= 1) {
                continue;
            }
            float newX = editPanelWidth * draggableLabel.getKx();
            float newY = editPanelHeight * draggableLabel.getKy();
            draggableLabel.getOggetto().setBounds((int) newX, (int) newY, 150, 30);

        }
        for (DraggableLabel draggableLabel : puppetLabels) {
            if (draggableLabel.getKx() <= 0 || draggableLabel.getKx() >= 1) {
                continue;
            }
            if (draggableLabel.getKy() <= 0 || draggableLabel.getKy() >= 1) {
                continue;
            }
            float newX = editPanelWidth * draggableLabel.getKx();
            float newY = editPanelHeight * draggableLabel.getKy();
            draggableLabel.getOggetto().setBounds((int) newX, (int) newY, 80, 80);

        }
        for (DraggablePanel draggablePanel : extraPanels) {
            if (draggablePanel.getKx() <= 0 || draggablePanel.getKx() >= 1) {
                continue;
            }
            if (draggablePanel.getKy() <= 0 || draggablePanel.getKy() >= 1) {
                continue;
            }
            float newX = editPanelWidth * draggablePanel.getKx();
            float newY = editPanelHeight * draggablePanel.getKy();
            draggablePanel.getOggetto().setBounds((int) newX, (int) newY, draggablePanel.getOggetto().getBounds().width, draggablePanel.getOggetto().getBounds().height);

        }
        for (DraggableLabel draggableLabel : sensorsLabels) {
            if (draggableLabel.getKx() <= 0 || draggableLabel.getKx() >= 1) {
                continue;
            }
            if (draggableLabel.getKy() <= 0 || draggableLabel.getKy() >= 1) {
                continue;
            }

            if (draggableLabel.isLight()) {
//                float newX = this.jScrollPane1.getWidth() * draggableLabel.getKx();
//                float newY = this.jScrollPane1.getHeight() * draggableLabel.getKy();
//                float kw = this.jScrollPane1.getWidth() * draggableLabel.getkWidth();
//                float kh = this.jScrollPane1.getHeight() * draggableLabel.getkHeight();

                float newX = editPanelWidth * draggableLabel.getKx();
                float newY = editPanelHeight * draggableLabel.getKy();
                float kw = this.jScrollPane1.getWidth() * draggableLabel.getkWidth();
                float kh = this.jScrollPane1.getHeight() * draggableLabel.getkHeight();
                draggableLabel.getOggetto().setBounds((int) newX, (int) newY, (int) kw, (int) kh);
            } else {
                float newX = this.jScrollPane1.getWidth() * draggableLabel.getKx();
                float newY = this.jScrollPane1.getHeight() * draggableLabel.getKy();
                if (draggableLabel.isInterruptor()) {
                    draggableLabel.getOggetto().setBounds((int) newX + 50, (int) newY + 32, 32, 32);
                } else {
                    draggableLabel.getOggetto().setBounds((int) newX, (int) newY, 64, 64);
                }
            }

//            float newX = editPanelWidth * draggableLabel.getKx();
//            float newY = editPanelHeight * draggableLabel.getKy();
//            if (giraffRobotLabel != null) {
//                if (giraffRobotLabel.getKx() <= 0 || giraffRobotLabel.getKx() >= 1) {
//                    continue;
//                }
//                if (giraffRobotLabel.getKy() <= 0 || giraffRobotLabel.getKy() >= 1) {
//                    continue;
//                }
////            float newX = this.jScrollPane2.getWidth() * draggableLabel.getKx();
////            float newY = this.jScrollPane2.getHeight() * draggableLabel.getKy();
//                float ffx = editPanelWidth * giraffRobotLabel.getKx();
//                float ffy = editPanelHeight * giraffRobotLabel.getKy();
//                giraffRobotLabel.getOggetto().setBounds((int) ffx, (int) ffy, 60, 100);
//            }
        }
    }

    public void setPirSensor1(ESensor pirSensor1) {
        this.pirSensor1 = pirSensor1;
    }

    public void setPirSensor2(ESensor pirSensor2) {
        this.pirSensor2 = pirSensor2;
    }

    public void setPirSensor3(ESensor pirSensor3) {
        this.pirSensor3 = pirSensor3;
    }

    public EHouse getHouse() {
        return house;
    }

    
    public void setHouse(EHouse house) {
        this.house = house;
        List<ESensor> sensors = house.getSensors();
        for (ESensor sensor : sensors) {
            if (sensor.getSensorType().getName().equals(SensorTypeClassifier.SensorTypes.LUMINOSITY.typeName())) {
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< LUMEN GENTIUM!!");
                ERoom room = house.whereIsThisSensor(sensor);
//                final float newX = (float) room.getSquareX() <= 0 || (float) room.getSquareX() > 1 ? (float) this.housePlanPanel1.getPreferredSize().getWidth() / 2 : (float) this.housePlanPanel1.getPreferredSize().getWidth() * (float) room.getSquareX();
//                final float newY = (float) room.getSquareY() <= 0 || (float) room.getSquareY() > 1 ? (float) this.housePlanPanel1.getPreferredSize().getHeight() / 2 : (float) this.housePlanPanel1.getPreferredSize().getHeight() * (float) room.getSquareY();
//                final float w = (float) room.getSquareWidth() <= 0 || (float) room.getSquareWidth() > 1 ? (float) this.housePlanPanel1.getPreferredSize().getWidth() / 2 :  (float) room.getSquareWidth() * (float)this.housePlanPanel1.getPreferredSize().getWidth();
//                final float h = (float) room.getSquareHeight() <= 0 || (float) room.getSquareHeight() > 1 ? (float) this.housePlanPanel1.getPreferredSize().getHeight() / 2 : (float) room.getSquareHeight() * (float)this.housePlanPanel1.getPreferredSize().getHeight();

                final float newX = (float) room.getSquareX() <= 0 || (float) room.getSquareX() > 1 ? (float) editPanelWidth / 2 : (float) editPanelWidth * (float) room.getSquareX();
                final float newY = (float) room.getSquareY() <= 0 || (float) room.getSquareY() > 1 ? (float) editPanelHeight / 2 : (float) editPanelHeight * (float) room.getSquareY();
                final float w = (float) room.getSquareWidth() <= 0 || (float) room.getSquareWidth() > 1 ? (float) editPanelWidth / 2 : (float) room.getSquareWidth() * (float)editPanelWidth;
                final float h = (float) room.getSquareHeight() <= 0 || (float) room.getSquareHeight() > 1 ? (float) editPanelHeight / 2 : (float) room.getSquareHeight() * editPanelHeight;

                DraggableLabel dl = new DraggableLabel(this, house, (BlinkableGlassPane) this.layerUI2, sensor, newX, newY, w, h, null);
                dl.setLight(true);
                this.sensorsLabels.add(dl);
                this.housePlanPanel1.add(dl.getOggetto());

            } else {
                Icon ic = VirtualDataPool.getInstance().getHouseMapSensorTypeIconBySensor(house, sensor, true);
                final float newX = (float) sensor.getxMap() <= 0 || (float) sensor.getxMap() > 1 ? (float) this.housePlanPanel1.getPreferredSize().getWidth() / 2 : (float) this.housePlanPanel1.getPreferredSize().getWidth() * (float) sensor.getxMap();
                final float newY = (float) sensor.getyMap() <= 0 || (float) sensor.getyMap() > 1 ? (float) this.housePlanPanel1.getPreferredSize().getHeight() / 2 : (float) this.housePlanPanel1.getPreferredSize().getHeight() * (float) sensor.getyMap();

                DraggableLabel dl = new DraggableLabel(this, house, (BlinkableGlassPane) this.layerUI2, sensor, (int) newX, (int) newY, 64, 64, ic);
                this.sensorsLabels.add(dl);
                this.housePlanPanel1.add(dl.getOggetto());
            }

//            if (sensor.getSensorType().getName().equals(SensorTypeClassifier.SensorTypes.PIR.typeName())) {
//                this.house.getRooms()
//            }
        }
        List<ERoom> rooms = house.getRooms();
        for (ERoom room : rooms) {
            final float newX = (float) room.getX() <= 0 || (float) room.getX() > 1 ? (float) this.housePlanPanel1.getPreferredSize().getWidth() / 2 : (float) this.housePlanPanel1.getPreferredSize().getWidth() * (float) room.getX();
            final float newY = (float) room.getY() <= 0 || (float) room.getY() > 1 ? (float) this.housePlanPanel1.getPreferredSize().getHeight() / 2 : (float) this.housePlanPanel1.getPreferredSize().getHeight() * (float) room.getY();

            final float puppetX = (float) room.getxPuppet() <= 0 || (float) room.getxPuppet() > 1 ? (float) this.housePlanPanel1.getPreferredSize().getWidth() / 2 : (float) this.housePlanPanel1.getPreferredSize().getWidth() * (float) room.getxPuppet();
            final float puppetY = (float) room.getyPuppet() <= 0 || (float) room.getyPuppet() > 1 ? (float) this.housePlanPanel1.getPreferredSize().getHeight() / 2 : (float) this.housePlanPanel1.getPreferredSize().getHeight() * (float) room.getyPuppet();

            DraggableLabel rl = new DraggableLabel(this, house, room, (int) newX, (int) newY, null);
            DraggableLabel puppet = new DraggableLabel(this, house, room, house.getUsers().get(0), (int) puppetX, (int) puppetY, true);
            this.roomLabels.add(rl);
            this.roomLabelMap.put(room.getId(), rl);
            this.housePlanPanel1.add(rl.getOggetto());

            this.puppetLabels.add(puppet);
            this.housePlanPanel1.add(puppet.getOggetto());

        }
    }

    public synchronized void setPuppetVisible(String puppetName, boolean rotate) {
        Boolean someoneInHome = DummyReasoner.getInstance().isSomeOneInside(house);
        System.out.println("SOMEONE IS HOME!! " + someoneInHome);
        if (someoneInHome == null) {
            return;
        }
        if (someoneInHome != null && someoneInHome && puppetOutside != null) {
            puppetOutside.setVisible(false);
            puppetOutside = null;
        }
        if (someoneInHome != null && !someoneInHome) {
            for (DraggableLabel puppet : puppetLabels) {
                puppet.setVisible(false);
                puppet.setRotate(false);
            }
            if (puppetOutside == null) {
                OutMessagePanel outMessagePanel = new OutMessagePanel();
                puppetOutside = this.addExtraPanel(0.35f, 0.04f, outMessagePanel);
                this.repaint();
                outMessagePanel.setDraggablePanel(puppetOutside);
                outMessagePanel.setDoorClosedAtTimeStamp(VirtualDataPool.getInstance().getLastEntranceDataPerHome(house.getId()).getTimestamp().toString());
                try {
                    checkLastPIRdata();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                ESensorData lastPIRdata = VirtualDataPool.getInstance().getLastPirDataPerHome().get(house.getId());
                ESensor sensor = lastPIRdata.getSensor();
                ERoom room = house.whereIsThisSensor(sensor);
                outMessagePanel.setLastActivityIn(room.getName());
                outMessagePanel.setLastActivityTimeStamp(lastPIRdata.getTimestamp().toString());
            } else {
                puppetOutside.setVisible(true);
            }
            return;
        } else {
            if (puppetOutside != null) {
                puppetOutside.setVisible(false);
            }
        }

        if (!someoneInHome) {
            for (DraggableLabel puppetLabel : puppetLabels) {
                puppetLabel.setVisible(false);
            }
        } else {
            for (DraggableLabel puppet : puppetLabels) {
                System.out.println("analyzing -> " + puppet.getNome());
                if (puppet.getNome().equals(puppetName)) {
                    System.out.println("TRUE !!");
                    puppet.setVisible(true);
                    if (rotate) {
                        puppet.setRotate(true);
                    }
                    this.setLastPuppetLabel(puppet);
//                WaiterSupporter.getInstance().resume();
//                JOptionPane.showMessageDialog(null, "TRUE TROVATO ! - > "+puppet.getNome(), "title", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    puppet.setVisible(false);
                    puppet.setRotate(false);
//                Thread t = new Thread(new PuppetBlocker(puppet));
//                t.start();
                }
            }
        }
    }

    public void setLastPuppetLabel(DraggableLabel lastPuppetLabel) {
        this.lastPuppetLabel = lastPuppetLabel;
    }

    public DraggableLabel getLastPuppetLabel() {
        return lastPuppetLabel;
    }

    public void setImage(Image image) {
        this.housePlanPanel1.setImage(image);
        this.housePlanPanel1.repaint();
        repaint();
    }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
        if (this.housePlanPanel1 != null) {
            this.housePlanPanel1.repaint();
        }
    }

    @Override
    public int revealWidht() {
//        if (editPanelWidth == 0) {
//            editPanelWidth = (int) this.jScrollPane1.getPreferredSize().getWidth();
//        } else {
        editPanelWidth = (int) this.jScrollPane1.getWidth();
//        }
//        JOptionPane.showMessageDialog(null, "WITDH DI MERDA = "+editPanelWidth);
        return editPanelWidth;
    }

    @Override
    public int revealHeight() {
//        if (editPanelHeight == 0) {
//            editPanelHeight = (int) this.jScrollPane1.getPreferredSize().getHeight();
//        } else {
        editPanelHeight = (int) this.jScrollPane1.getHeight();
//        }
        return editPanelHeight;
    }

    public DraggablePanel addExtraPanel(float xf, float yf, JPanel panel) {
        DraggablePanel draggablePanel = new DraggablePanel(xf, yf, this, panel);
        draggablePanel.setVisible(true);
        draggablePanel.setDraggable(true);

//        draggablePanel.getOggetto().setBackground(new Color(0, 0, 0, 0.3f));
//        draggablePanel.getOggetto().setBorder(new RoundBorder());
        this.extraPanels.add(draggablePanel);
        System.out.println("this size = " + this.housePlanPanel1.getComponentCount());
        this.housePlanPanel1.add(draggablePanel.getOggetto(), 0);
        return draggablePanel;
    }

    public void removeExtraPanel(DraggablePanel panel) {
        panel.setVisible(false);
//        this.extraPanels.remove(panel);
//        this.housePlanPanel1.remove(panel.getOggetto());
//        this.housePlanPanel1.repaint();
    }

    public void addRoomCardinality(ERoom room) {
        DraggableLabel roomLabel = this.roomLabelMap.get(room.getId());
//        JOptionPane.showMessageDialog(null, "roomLABEL = "+roomLabel.getNome());
        if (roomLabel != null) {
            String nome = roomLabel.getNome();
            String[] split = nome.split("#");
            if (split.length != 1) {
                int n = Integer.parseInt(split[1]);
                roomLabel.setNome(split[0] + "#" + (n + 1));
            } else {
                roomLabel.setNome(nome + " #1");
            }
        }
    }

    public synchronized void checkLastPIRdata() throws Exception {
        List<ESensor> pirSensorListByHouseId = HouseMapper.getPIRSensorListByHouse(house);
        ESensorData lastPIRdata = VirtualDataPool.getInstance().getLastPirDataPerHome().get(house.getId());
        if (lastPIRdata == null) {
            return;
        }
        Date now = new Date();
        for (ESensor sensorEntity : pirSensorListByHouseId) {
            ESensorData previousSample = sensorEntity.getLastSensorData();
            if (previousSample.getTimestamp().after(lastPIRdata.getTimestamp())) {
                ESensorData temp = VirtualDataPool.getInstance().getLastPirDataPerHome().get(house.getId());
                temp.setTimestamp(previousSample.getTimestamp());
                VirtualDataPool.getInstance().getLastPirDataPerHome().put(house.getId(), temp);
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        jPanel1 = new JPanel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jToggleButton1 = new JToggleButton();
        jButton4 = new JButton();
        jButton5 = new JButton();
        jButton6 = new JButton();

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel1.setBackground(new Color(255, 0, 153));

        jButton1.setText("PIR 1 Ufficio");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("TEST");
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("PIR 2 Bagno");
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jToggleButton1.setText("Porta");
        jToggleButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("PIR 3 Ingresso");
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("ASK CONFIGURATION");
        jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("FAKE JSON");
        jButton6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jButton5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6)
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jToggleButton1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6)
                .addGap(82, 82, 82)
                .addComponent(jButton1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addGap(13, 13, 13)
                .addComponent(jToggleButton1)
                .addGap(39, 39, 39)
                .addComponent(jButton2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(76, 76, 76))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ESensorData data = new ESensorData();
        data.setValue("true");
        data.setTimestamp(new Date());
        data.setSensor(pirSensor1);

        MQTTManager.getInstance().newSensorData(data);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        for (DraggableLabel sensorsLabel : sensorsLabels) {
            sensorsLabel.testBlinking();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ESensorData data = new ESensorData();
        data.setValue("true");
        data.setTimestamp(new Date());
        data.setSensor(pirSensor2);

        MQTTManager.getInstance().newSensorData(data);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formComponentResized(ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        resizeAll();
    }//GEN-LAST:event_formComponentResized

    private void jToggleButton1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        ESensorData data = new ESensorData();
        data.setValue(jToggleButton1.isSelected() ? "true" : "false");
        data.setTimestamp(new Date());
        data.setSensor(this.house.getEntranceDoor());

        MQTTManager.getInstance().newSensorData(data);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton4ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ESensorData data = new ESensorData();
        data.setValue("true");
        data.setTimestamp(new Date());
        data.setSensor(pirSensor3);

        MQTTManager.getInstance().newSensorData(data);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        MQTTManager.getInstance().askConfiguration();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       String message = "{\"home_id\":\"0x0184e8a0\",\"home_name\":\"casaaa\",\"entrance_id\":120,\"residentList\":[{\"id\":0,\"name\":\"luca\",\"surname\":\"coraci\"}],\"roomList\":[{\"roomtype\":\"Entrance\",\"name\":\"entrata\",\"x\":0.0,\"y\":0.0,\"squarex\":0.0,\"squarey\":0.0,\"squarewidth\":0.0,\"squareheight\":0.0,\"xpuppet\":0.0,\"ypuppet\":0.0,\"locationList\":[{\"id\":0,\"type\":\"maindoor\",\"xmap\":0,\"ymap\":0,\"sensorList\":[{\"id\":119,\"sid\":\"5-48-0\",\"name\":\"gap\",\"node_id\":\"5\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Motion Sensor\",\"unit\":\"\",\"type\":\"bool\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"5-48-0\"}},{\"id\":120,\"sid\":\"5-48-1\",\"name\":\"gap\",\"node_id\":\"5\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Door/Window Sensor\",\"unit\":\"\",\"type\":\"bool\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"5-48-1\"}},{\"id\":121,\"sid\":\"5-48-2\",\"name\":\"gap\",\"node_id\":\"5\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Tamper Sensor\",\"unit\":\"\",\"type\":\"bool\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"5-48-2\"}},{\"id\":122,\"sid\":\"5-49-1\",\"name\":\"gap\",\"node_id\":\"5\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Temperature\",\"unit\":\"F\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"5-49-1\"}},{\"id\":123,\"sid\":\"5-49-3\",\"name\":\"gap\",\"node_id\":\"5\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Luminance\",\"unit\":\"%\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"5-49-3\"}}]}]},{\"roomtype\":\"Kitchen\",\"name\":\"cucina\",\"x\":0.0,\"y\":0.0,\"squarex\":0.0,\"squarey\":0.0,\"squarewidth\":0.0,\"squareheight\":0.0,\"xpuppet\":0.0,\"ypuppet\":0.0,\"locationList\":[{\"id\":0,\"type\":\"fridge\",\"xmap\":0,\"ymap\":0,\"sensorList\":[{\"id\":1,\"sid\":\"2-37-0\",\"name\":\"switch\",\"node_id\":\"2\",\"state\":\"0\",\"sensortype\":\"Binary Power Switch\",\"label\":\"Switch\",\"unit\":\"\",\"type\":\"bool\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"2-37-0\"}},{\"id\":3,\"sid\":\"2-50-0\",\"name\":\"switch\",\"node_id\":\"2\",\"state\":\"0\",\"sensortype\":\"Binary Power Switch\",\"label\":\"Energy\",\"unit\":\"kWh\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"2-50-0\"}},{\"id\":6,\"sid\":\"2-50-8\",\"name\":\"switch\",\"node_id\":\"2\",\"state\":\"0\",\"sensortype\":\"Binary Power Switch\",\"label\":\"Power\",\"unit\":\"W\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"2-50-8\"}},{\"id\":7,\"sid\":\"2-50-16\",\"name\":\"switch\",\"node_id\":\"2\",\"state\":\"0\",\"sensortype\":\"Binary Power Switch\",\"label\":\"Voltage\",\"unit\":\"V\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"2-50-16\"}},{\"id\":8,\"sid\":\"2-50-20\",\"name\":\"switch\",\"node_id\":\"2\",\"state\":\"0\",\"sensortype\":\"Binary Power Switch\",\"label\":\"Current\",\"unit\":\"A\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"2-50-20\"}}]}]},{\"roomtype\":\"Bed Room\",\"name\":\"stanza da letto\",\"x\":0.0,\"y\":0.0,\"squarex\":0.0,\"squarey\":0.0,\"squarewidth\":0.0,\"squareheight\":0.0,\"xpuppet\":0.0,\"ypuppet\":0.0,\"locationList\":[{\"id\":0,\"type\":\"lamp\",\"xmap\":0,\"ymap\":0,\"sensorList\":[{\"id\":144,\"sid\":\"6-37-0\",\"name\":\"\",\"node_id\":\"6\",\"state\":\"0\",\"sensortype\":\"Binary Power Switch\",\"label\":\"Switch\",\"unit\":\"\",\"type\":\"bool\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"6-37-0\"}},{\"id\":146,\"sid\":\"6-50-0\",\"name\":\"\",\"node_id\":\"6\",\"state\":\"0\",\"sensortype\":\"Binary Power Switch\",\"label\":\"Energy\",\"unit\":\"kWh\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"6-50-0\"}},{\"id\":149,\"sid\":\"6-50-8\",\"name\":\"\",\"node_id\":\"6\",\"state\":\"0\",\"sensortype\":\"Binary Power Switch\",\"label\":\"Power\",\"unit\":\"W\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"6-50-8\"}},{\"id\":150,\"sid\":\"6-50-16\",\"name\":\"\",\"node_id\":\"6\",\"state\":\"0\",\"sensortype\":\"Binary Power Switch\",\"label\":\"Voltage\",\"unit\":\"V\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"6-50-16\"}},{\"id\":151,\"sid\":\"6-50-20\",\"name\":\"\",\"node_id\":\"6\",\"state\":\"0\",\"sensortype\":\"Binary Power Switch\",\"label\":\"Current\",\"unit\":\"A\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"6-50-20\"}}]}]},{\"roomtype\":\"Living Room \",\"name\":\"salotto\",\"x\":0.0,\"y\":0.0,\"squarex\":0.0,\"squarey\":0.0,\"squarewidth\":0.0,\"squareheight\":0.0,\"xpuppet\":0.0,\"ypuppet\":0.0,\"locationList\":[{\"id\":0,\"type\":\"table\",\"xmap\":0,\"ymap\":0,\"sensorList\":[{\"id\":215,\"sid\":\"8-48-0\",\"name\":\"\",\"node_id\":\"8\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Sensor\",\"unit\":\"\",\"type\":\"bool\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"8-48-0\"}},{\"id\":216,\"sid\":\"8-49-1\",\"name\":\"\",\"node_id\":\"8\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Temperature\",\"unit\":\"C\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"8-49-1\"}},{\"id\":217,\"sid\":\"8-49-3\",\"name\":\"\",\"node_id\":\"8\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Luminance\",\"unit\":\"lux\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"8-49-3\"}}]},{\"id\":0,\"type\":\"window\",\"xmap\":0,\"ymap\":0,\"sensorList\":[{\"id\":190,\"sid\":\"7-48-0\",\"name\":\"\",\"node_id\":\"7\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Motion Sensor\",\"unit\":\"\",\"type\":\"bool\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"7-48-0\"}},{\"id\":191,\"sid\":\"7-48-1\",\"name\":\"\",\"node_id\":\"7\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Door/Window Sensor\",\"unit\":\"\",\"type\":\"bool\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"7-48-1\"}},{\"id\":192,\"sid\":\"7-48-2\",\"name\":\"\",\"node_id\":\"7\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Tamper Sensor\",\"unit\":\"\",\"type\":\"bool\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"7-48-2\"}},{\"id\":193,\"sid\":\"7-49-1\",\"name\":\"\",\"node_id\":\"7\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Temperature\",\"unit\":\"F\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"7-49-1\"}},{\"id\":194,\"sid\":\"7-49-3\",\"name\":\"\",\"node_id\":\"7\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Luminance\",\"unit\":\"%\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"7-49-3\"}}]}]},{\"roomtype\":\"Bathroom \",\"name\":\"bagno\",\"x\":0.0,\"y\":0.0,\"squarex\":0.0,\"squarey\":0.0,\"squarewidth\":0.0,\"squareheight\":0.0,\"xpuppet\":0.0,\"ypuppet\":0.0,\"locationList\":[{\"id\":0,\"type\":\"shower\",\"xmap\":0,\"ymap\":0,\"sensorList\":[{\"id\":93,\"sid\":\"4-48-0\",\"name\":\"pir\",\"node_id\":\"4\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Sensor\",\"unit\":\"\",\"type\":\"bool\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"4-48-0\"}},{\"id\":94,\"sid\":\"4-49-1\",\"name\":\"pir\",\"node_id\":\"4\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Temperature\",\"unit\":\"C\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"4-49-1\"}},{\"id\":95,\"sid\":\"4-49-3\",\"name\":\"pir\",\"node_id\":\"4\",\"state\":\"0\",\"sensortype\":\"Routing Binary Sensor\",\"label\":\"Luminance\",\"unit\":\"lux\",\"type\":\"decimal\",\"sensor_value\":{\"time\":0,\"value\":\"0\",\"sid\":\"4-49-3\"}}]}]}],\"topology\":{\"cucina\":[],\"entrata\":[],\"stanza da letto\":[],\"salotto\":[],\"bagno\":[]}}";
       
        MqttMessage m = new MqttMessage(message.getBytes());
        try {
            MQTTManager.getInstance().messageArrived(GET_CONFIG+"/"+MQTTManager.getInstance().getClientId(), m);
        } catch (Exception ex) {
            Logger.getLogger(MapPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
