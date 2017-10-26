/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;

//import it.cnr.istc.dvpis.abstracts.SensorTypeClassifier;
//import it.cnr.istc.dvpis.common.IconRetriever;
//import it.cnr.istc.dvpis.common.VirtualDataPool;
//import static it.cnr.istc.dvpis.common.VirtualDataPool.BASE_PUPPET_LABEL_CODE;
//import static it.cnr.istc.dvpis.common.VirtualDataPool.BASE_PUPPET_LABEL_DELIMITER;
//import it.cnr.istc.dvpis.common.flow.ApplicationManager;
//import it.cnr.istc.dvpis.common.flow.StorageAPIManager;
//import it.cnr.istc.dvpis.common.flow.i18n.I18nTranslator;
//import it.cnr.istc.dvpis.common.flow.i18n.LangKeywords;
//import it.cnr.istc.dvpis.common.flow.settings.HSVSetting;
//import it.cnr.istc.dvpis.common.flow.wait.WaiterSupporter;
//import it.cnr.istc.dvpis.gui.alarms.AlarmManager;
//import it.cnr.istc.dvpis.gui.spot.monitor.enums.SensorProperty;
//import static it.cnr.istc.dvpis.gui.spot.monitor.panels.houses2.HouseEditorPanel.balug24;
//import it.cnr.istc.dvpis.gui.spot.monitor.panels.houses2.models.PositionRevealer;
//import it.cnr.istc.dvpis.middleware.MiddlewareManager;
//import it.cnr.istc.dvpis.personalization.PersonalizationManager;
//import it.cnr.istc.dvpis.reports.ReportManager;
//import it.cnr.istc.extra.BlinkableGlassPane;
//import it.cnr.istc.extra.BlinkerTimer;
//import it.cnr.istc.extra.test.SensorTester;
//import it.cnr.istc.giraffplus.caching.api.Data;
//import it.cnr.istc.giraffplus.caching.api.HomeStaticProfile;
//import it.cnr.istc.giraffplus.caching.api.StaticProfile;
//import it.cnr.istc.giraffplus.pers.api.ConnectionListener;
//import it.cnr.istc.giraffplus.pers.api.Event;
//import it.cnr.istc.giraffplus.pers.api.PerSClientListener;
//import it.cnr.istc.giraffplus.pers.api.services.QueryListener;
//import it.cnr.istc.icv.test.BooleanDataSupporter;
//import it.cnr.isti.giraff.api.MessageListener;
//import it.cnr.isti.giraff.api.ServiceDescriptor;
import static it.cnr.istc.hsv.abstracts.extra.VirtualDataPool.BASE_PUPPET_LABEL_CODE;
import static it.cnr.istc.hsv.abstracts.extra.VirtualDataPool.BASE_PUPPET_LABEL_DELIMITER;
import it.cnr.istc.hsv.logic.entities.EHouse;
import it.cnr.istc.hsv.logic.entities.ERoom;
import it.cnr.istc.hsv.logic.entities.ESensor;
import it.cnr.istc.hsv.logic.entities.ESensorData;
import it.cnr.istc.hsv.logic.entities.EUser;

import it.cnr.istc.hsv.mqtt.MQTTManager;
import it.cnr.istc.hsv.mqtt.MessageListener;
import it.cnr.istc.hsv.panels.MapPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Beans;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.Timer;
//import org.bson.types.ObjectId;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
//import si.xlab.giraffplus.storage.Util;
//import si.xlab.giraffplus.storage.entities.House;
//import si.xlab.giraffplus.storage.entities.LocationEntity;
//import si.xlab.giraffplus.storage.entities.Room;
//import si.xlab.giraffplus.storage.entities.SensorData;
//import si.xlab.giraffplus.storage.entities.Sensor;
//import si.xlab.giraffplus.storage.entities.SensorTypeEntity;
//import si.xlab.giraffplus.storage.entities.User;

/**
 *
 * @author User
 */
public class DraggableLabel implements MessageListener //        , PerSClientListener, ConnectionListener, QueryListener
{

    private ESensor sensor = null;
    private ERoom roomLocation = null;
    private EUser userLocation = null;
    private float kx;
    private float ky;
    private float kWidth;
    private float kHeight;
    private EHouse home = null;
    private boolean draggable = true;
    private BlinkableGlassPane blinker = null;
    private BlinkerTimer activeBlinkerTimer;
//    private JPanel presenceChart = null;
    protected JJJLabel oggetto;
    protected String nome = "";
    private boolean salvato = false;
    private int dx = 0;
    private int dy = 0;
    private Timer redPirTimer = null;
    private PositionRevealer positionRevealer;
    private Date lastTimeStamp = null;
    private boolean giraff = false;

    protected int myIndex = -1;
    private boolean drawingArrowEnabled = true;
    private int onlineCounter = 0;
    private boolean interruptor = false;
    private boolean interruptor_plagged = false;
    private boolean multiPir = false;
    private boolean multiDoor = false;
    private boolean light = false;

    public static final Icon balug24 = new javax.swing.ImageIcon(DraggableLabel.class.getResource("/it/cnr/istc/hsv/images/balug24.png"));
    public static final Icon smoke = new javax.swing.ImageIcon(DraggableLabel.class.getResource("/it/cnr/istc/hsv/images/smokeSmall.png"));
    public static long impulsiveIdleTime = 15000l;

//    private ObjectId attachedSensor = null;
    public DraggableLabel() {

        oggetto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                oggettoMouseClicked(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                oggettoMousePressed(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                oggettoMouseReleased(evt);
            }
        });
        oggetto.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                if (isDraggable()) {
                    oggettoMouseDragged(evt);
                }
            }
        });
        if (!Beans.isDesignTime()) {
            System.out.println("registering.. ");
            MQTTManager.getInstance().addMessageListener(this);
        }
    }

    public float getkWidth() {
        return kWidth;
    }

    public void setkWidth(float kWidth) {
        this.kWidth = kWidth;
    }

    public float getkHeight() {
        return kHeight;
    }

    public void setkHeight(float kHeight) {
        this.kHeight = kHeight;
    }

    public void setInterruptor(boolean interruptor) {
        this.interruptor = interruptor;
    }

    public boolean isInterruptor() {
        return interruptor;
    }

    public void setDrawingArrowEnabled(boolean drawingArrowEnabled) {
        this.drawingArrowEnabled = drawingArrowEnabled;
    }

    public boolean isDrawingArrowEnabled() {
        return drawingArrowEnabled;
    }

    public void vanishBlinking() {
        if (blinker != null) {
            this.blinker.killAll();
        }

    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
        this.oggetto.setLight(light);
    }

    public void setRotate(boolean rotate) {
        this.oggetto.setRotate(rotate);
    }

    public void testBlinking() {
        Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, true);
        if (true && blinker != null) {
            float shiftX = ((float) (10)) / ((float) positionRevealer.revealWidht());
            float shiftY = ((float) (5)) / ((float) positionRevealer.revealHeight());

            BlinkerTimer startBlinking = blinker.startBlinking(balug24, this.getKx() + shiftX, this.getKy() + shiftY, 9.8d, impulsiveIdleTime, 100);
            if (activeBlinkerTimer != null) {
                blinker.stopBlinker(activeBlinkerTimer);
            }
            activeBlinkerTimer = startBlinking;

            if (redPirTimer == null) {
                redPirTimer = new Timer((int) impulsiveIdleTime, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Icon icFalse = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(DraggableLabel.this.home, sensor, false);
                        DraggableLabel.this.oggetto.setIcon(icFalse);
                    }
                });
                redPirTimer.setRepeats(false);
                redPirTimer.start();
            } else {
                redPirTimer.setRepeats(false);
                redPirTimer.restart();
            }
        }
        oggetto.setIcon(ic);
    }

    public EHouse getHome() {
        return home;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    /**
     * Constructor for Puppet Labels
     *
     * @param positionRevealer
     * @param home
     * @param location
     * @param x
     * @param y
     * @param icon
     */
    public DraggableLabel(PositionRevealer positionRevealer, EHouse home, ERoom room, EUser ulocation, int x, int y, boolean male) {
        this.positionRevealer = positionRevealer;
        if (HSVSetting.getInstance().isVerbose()) {
            System.out.println("[dvpis.office] Parsing Puppet info");
            System.out.println("[dvpiss.office - Room Location] creating label for puppet: " + ulocation);
            System.out.println("LOCATION LABEL: " + ulocation + " x: " + x + ", y: " + y);
        }
        this.userLocation = ulocation;
        this.home = home;
        this.roomLocation = room;
//        ObjectId PIR = null;
        String puppetName = ulocation.toString();
//        String[] split = puppetName.split("\\.");
//        if (split.length == 2) {
//            puppetName = split[1];
//        }
        this.nome = BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId();

        oggetto = new JJJLabel(this, true);

        oggetto.setBackground(null);
        oggetto.setOpaque(false);
        oggetto.setIcon(male ? IconRetriever.oldMan80 : IconRetriever.oldWoman80);
        oggetto.setToolTipText(puppetName + " in " + room.getName());
        oggetto.setText("");
        Font defaultFont = new Font("Helvetica", Font.BOLD, 14);
        oggetto.setFont(defaultFont);
        oggetto.setForeground(Color.BLACK);
        FontMetrics fontMetrics = oggetto.getFontMetrics(oggetto.getFont());
//        int width = fontMetrics.stringWidth(I18nTranslator.getInstance().translate(roomName));
        int width = fontMetrics.stringWidth(puppetName);
        if (width <= 0 || width >= 200) {
            width = 30;
        }
        int height = fontMetrics.getHeight();
        oggetto.setBounds(x, y, 80, 80);
        this.kx = (float) x;
        this.ky = (float) y;
        oggetto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                oggettoMouseClicked(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                oggettoMousePressed(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                oggettoMouseReleased(evt);
            }
        });
        oggetto.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                if (isDraggable()) {
                    oggettoMouseDragged(evt);
                }
            }
        });
        if (!Beans.isDesignTime()) {
            System.out.println("registering.. ");
            MQTTManager.getInstance().addMessageListener(this);
        }

//        oggetto.setToolTipText(getNome());
    }

    /**
     * Constructor for Room Label
     *
     * @param positionRevealer
     * @param home
     * @param location
     * @param x
     * @param y
     * @param icon
     */
    public DraggableLabel(PositionRevealer positionRevealer, EHouse home, ERoom roomLocation, int x, int y, Icon icon) {
        this.positionRevealer = positionRevealer;
        if (HSVSetting.getInstance().isVerbose()) {
            System.out.println("[dvpiss.office - Room Location] creating label for room: " + roomLocation);
            System.out.println("LOCATION LABEL: " + roomLocation + " x: " + x + ", y: " + y);
        }
        this.roomLocation = roomLocation;
        this.home = home;
//        ObjectId PIR = null;
        String roomName = roomLocation.getName();
//        String[] split = roomName.split("\\.");
//        if (split.length == 2) {
//            roomName = split[1];
//        }
        this.nome = roomName;

        oggetto = new JJJLabel(this, true);

        oggetto.setBackground(null);
        oggetto.setOpaque(false);
        oggetto.setIcon(icon);

//        oggetto.setText(I18nTranslator.getInstance().translate(roomName));
        oggetto.setText(roomName);
        Font defaultFont = new Font("Helvetica", Font.BOLD, 14);
        oggetto.setFont(defaultFont);
        oggetto.setForeground(Color.BLACK);
        FontMetrics fontMetrics = oggetto.getFontMetrics(oggetto.getFont());
        int width = fontMetrics.stringWidth(roomName);
        if (width <= 0 || width >= 200) {
            width = 30;
        }
        int height = fontMetrics.getHeight();
        oggetto.setBounds(x, y, width + 30, height);
        this.kx = (float) x;
        this.ky = (float) y;
        oggetto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                oggettoMouseClicked(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                oggettoMousePressed(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                oggettoMouseReleased(evt);
            }
        });
        oggetto.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                if (isDraggable()) {
                    oggettoMouseDragged(evt);
                }
            }
        });
        if (!Beans.isDesignTime()) {
            System.out.println("registering.. ");
            MQTTManager.getInstance().addMessageListener(this);
        }

//        oggetto.setToolTipText(getNome());
    }

//    /**
//     * Constructor for Robot
//     *
//     * @param positionRevealer
//     * @param home
//     * @param location
//     * @param x
//     * @param y
//     */
//    public DraggableLabel(PositionRevealer positionRevealer, House home, String location, int x, int y) {
//        this.positionRevealer = positionRevealer;
//        this.giraff = true;
//        if (HSVSetting.getInstance().isVerbose()) {
//            System.out.println("[dvpiss.office - Room Location] creating label for room: " + location);
////            System.out.println("LOCATION LABEL: " + location.getName() + " x: " + x + ", y: " + y);
//        }
//        this.location = location;
//        this.home = home;
////        ObjectId PIR = null;
////        String roomName = location.getName();
////        String[] split = roomName.split("\\.");
////        if (split.length == 2) {
////            roomName = split[1];
////        }
//        this.nome = "";
//
//        oggetto = new JJJLabel(this, true);
//
//        oggetto.setBackground(null);
//        oggetto.setOpaque(false);
//        oggetto.setIcon(IconRetriever.giraffRobot100);
//
//        Font defaultFont = new Font("Helvetica", Font.BOLD, 14);
//        oggetto.setFont(defaultFont);
//        oggetto.setForeground(Color.BLACK);
//        int width = 60;
//        int height = 100;
//        oggetto.setBounds(x, y, width, height);
//        this.kx = (float) x;
//        this.ky = (float) y;
//        oggetto.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                oggettoMouseClicked(evt);
//            }
//
//            public void mousePressed(java.awt.event.MouseEvent evt) {
//                oggettoMousePressed(evt);
//            }
//
//            public void mouseReleased(java.awt.event.MouseEvent evt) {
//                oggettoMouseReleased(evt);
//            }
//        });
//        oggetto.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//            public void mouseDragged(java.awt.event.MouseEvent evt) {
//                if (isDraggable()) {
//                    oggettoMouseDragged(evt);
//                }
//            }
//        });
//        PersonalizationManager.getInstance().getPersonalizator().addClientListener(this);
//        PersonalizationManager.getInstance().getConnectionManagerAPI().addConnectionListener(this);
//
//        ApplicationManager.getInstance().addThread(new Runnable() {
//
//            @Override
//            public void run() {
//                System.out.println("forcing giraff robot icon to offline");
//                System.out.println("forcing giraff robot icon to offline");
//                System.out.println("forcing giraff robot icon to offline");
//                System.out.println("forcing giraff robot icon to offline");
//                onlineCounter--;
//                if (onlineCounter <= -2) {
//                    oggetto.setIcon(IconRetriever.giraffOFFLINE);
//                    onlineCounter = -2;
//                }
//            }
//
//        }, 10000l, 60000l);
//        oggetto.setToolTipText(getNome());
//    }
    /**
     * Constructor for Sensor Entity
     *
     * @param positionRevealer
     * @param home
     * @param blinker
     * @param sensor
     * @param x
     * @param y
     * @param wid
     * @param hei
     * @param icon
     */
    public DraggableLabel(final PositionRevealer positionRevealer, final EHouse home, BlinkableGlassPane blinker, final ESensor sensor, float x, float y, float wid, float hei, Icon icon) {
        this.blinker = blinker;
        this.positionRevealer = positionRevealer;
        this.sensor = sensor;
        this.home = home;
        this.kWidth = wid;
        this.kHeight = hei;

        this.setkWidth((float) wid / (float) MapPanel.editPanelWidth);//ONLY FOR LIGHT 
        this.setkHeight((float) hei / (float) MapPanel.editPanelHeight);//ONLY FOR LIGHT

        if (sensor.getSensorType().getName().equals("Z-Wave Multisensor PIR")) {
            setMultiPir(true);
        }
        if (sensor.getSensorType().getName().equals("Z-Wave Door Window Sensor")) {
            setMultiDoor(true);
        }

        if (!Beans.isDesignTime()) {
            System.out.println("registering.. ");
            MQTTManager.getInstance().addMessageListener(this);
        }
        oggetto = new JJJLabel(this, false);
//        setNome(sensor.getName());
        setNome("");
        oggetto.setBackground(null);
        if (sensor.getSensorType().getName().equals(SensorTypeClassifier.SensorTypes.LUMINOSITY.typeName())) {
            oggetto.setOpaque(true);
            oggetto.setBackground(new Color(20, 20, 20, 200));
        } else {
            oggetto.setOpaque(false);
        }
        oggetto.setIcon(icon);
//        FontMetrics fontMetrics = oggetto.getFontMetrics(oggetto.getFont());
//        int width = fontMetrics.stringWidth(this.nome);
//        int height = fontMetrics.getHeight();
//        oggetto.setBounds(x, y, width, height);

        oggetto.setBounds((int) x, (int) y, (int) wid, (int) hei);
        if (icon != null) { //NON E' light
            this.kx = (float) sensor.getxMap();
            this.ky = (float) sensor.getyMap();
        } else {
            ERoom r = this.home.whereIsThisSensor(sensor);
            this.roomLocation = r;

            this.kx = x / (float) (float) MapPanel.editPanelWidth;
            this.ky = y / (float) (float) MapPanel.editPanelHeight;
            this.roomLocation.setSquareX(this.kx);
            this.roomLocation.setSquareY(this.ky);
        }
        oggetto.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                oggettoMouseClicked(evt);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                oggettoMousePressed(evt);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                oggettoMouseReleased(evt);
            }
        });
        oggetto.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                if (isDraggable()) {
                    oggettoMouseDragged(evt);
                }
            }
        });

//        oggetto.setToolTipText("<html>" + I18nTranslator.getInstance().translate(getNome()) + " Code: " + home.getLocation(sensor.getLocation()).getCode());
//        oggetto.setToolTipText("<html>" + getNome());
        oggetto.setToolTipText(sensor.getName() + " at " + sensor.getLocation());
//        SwingUtilities.invokeLater(new Runnable() {

        Thread ttt;
        ttt = new Thread(new Runnable() {
            @Override
            public void run() {
                Date now = new Date();
                try {
//                    WaiterSupporter.getInstance().getSplasher().start();     "retriving initial state of sensor: " + nome);
                    long startQ1 = System.currentTimeMillis();
//                    List<Data> r_data = ReportManager.getInstance().getCachingAPI().getData(DraggableLabel.this.sensor, new Date(now.getTime() - 1000 * 60 * 10), now);
                    ESensorData r_previousSample = sensor.getLastSensorData();
//                    if (r_data != null && !r_data.isEmpty()) {
//
//                        r_previousSample = r_data.get(r_data.size() - 1);
//                    } else {
//                        try {
//                            r_previousSample = ReportManager.getInstance().getCachingAPI().getPreviousSample(DraggableLabel.this.sensor, now);
//                        } catch (Exception ex) {
//                            System.out.println("Error on prev sample: ");
//                            ex.printStackTrace();
//
//                        }
//                    }

                    String unit = sensor.getSensorType().getTypeUnit();
//                    User pu = ReportManager.getInstance().getCachingAPI().getUser(home.getPrimaryUsers().get(0));
//                    StaticProfile staticProfile = ReportManager.getInstance().getCachingAPI().getStaticProfile(pu);
                    ESensor entranceDoor = home.getEntranceDoor();
//                    HomeStaticProfile homeStaticProfile = null;
//                    if (staticProfile.hasHomeStaticProfile(home)) {
//                        homeStaticProfile = staticProfile.getHomeStaticProfile(home);
//                    }

//                    if (homeStaticProfile != null && staticProfile.getHomeStaticProfile(home).hasEntranceDoor()) {
//                        entranceDoor = staticProfile.getHomeStaticProfile(home).getEntranceDoor();
//                    }
                    synchronized (VirtualDataPool.getInstance().getLastPirDataPerHome()) {
                        if (SensorTypeClassifier.getDVPISSensorUnit(unit) == SensorTypeClassifier.DVPISSensorType.STRANGE_BOOLEAN || multiPir) {
                            ESensorData lastSensorData = VirtualDataPool.getInstance().getLastPirDataPerHome().get(home.getId());
                            if (lastSensorData != null && r_previousSample != null) {
                                if (r_previousSample.getTimestamp().getTime() > (lastSensorData.getTimestamp().getTime())) {
                                    ERoom room = home.whereIsThisSensor(sensor);
                                    if (positionRevealer instanceof MapPanel) {
                                        ((MapPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId(), true);
                                    }
//                                    else if (positionRevealer instanceof HouseEditorCleanPanel) {
//                                        ((HouseEditorCleanPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId(), true);
//                                    }
                                    ESensorData sData = new ESensorData();
                                    sData.setTimestamp(r_previousSample.getTimestamp());
                                    sData.setSensor(r_previousSample.getSensor());
                                    sData.setValue(r_previousSample.getValue());
                                    VirtualDataPool.getInstance().getLastPirDataPerHome().put(home.getId(), sData);
                                } else {
//                                 Room room = ReportManager.getInstance().getCachingAPI().getRoom(lastSensorData.getRoom());
//                                ((HouseEditorCleanPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId());
////                                SensorData sData = new SensorData();
////                                sData.setTimestamp(lastSensorData.getTimestamp());
////                                sData.setSensor_id(lastSensorData.getSensor_id());
////                                sData.setValues(lastSensorData.getValues());
//                                VirtualDataPool.getInstance().getLastPirDataPerHome().put(home.getId(), lastSensorData);
                                    System.out.println(" not relevant .. ");
                                }

                            } else if (lastSensorData == null && r_previousSample != null) {

                                ESensorData sData = new ESensorData();
                                sData.setTimestamp(r_previousSample.getTimestamp());
                                sData.setSensor(r_previousSample.getSensor());
                                sData.setValue(r_previousSample.getValue());
                                ERoom room = home.whereIsThisSensor(sensor);
                                VirtualDataPool.getInstance().getLastPirDataPerHome().put(home.getId(), sData);
                                if (positionRevealer instanceof MapPanel) {
                                    ((MapPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId(), true);
                                }
//                                else if (positionRevealer instanceof HouseEditorCleanPanel) {
//                                    ((HouseEditorCleanPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId(), true);
//                                }

                            }
                        }

                        if (SensorTypeClassifier.getDVPISSensorUnit(unit) == SensorTypeClassifier.DVPISSensorType.MULTI_TYPE) {
                            String loc = sensor.getName();
                            ESensorData sData = new ESensorData();
                            sData.setTimestamp(r_previousSample.getTimestamp());
                            sData.setSensor(r_previousSample.getSensor());
                            sData.setValue(r_previousSample.getValue());
//                                if (loc.getName().toLowerCase().contains("lamp")) {

//                            for (SensorTypeEntity.NameUnitPair unitPair : vf) {
                            String name = sensor.getSensorType().getName(); //nome del sotto sensore
                            String _unit = sensor.getSensorType().getTypeUnit(); //unità di misura del sensore
//                    JOptionPane.showMessageDialog(null, "nome = " + name + ", unit = " + _unit);
                            if (_unit.equals("boolean")) {
                                if (name.equals("is on")) { // da rimettere is on
                                    //LA PRESA E' ACCESA O NO
                                    //plugged or not
                                    if (isInterruptor()) {
                                        boolean isOn = Boolean.parseBoolean(sData.getValue());
                                        Icon ic = isOn ? SensorProperty.PLUGGED_ICON.getIcon() : SensorProperty.UNPLUGGED_ICON.getIcon();
                                        interruptor_plagged = isOn;
                                        oggetto.setIcon(ic);
                                        oggetto.setToolTipText(isOn ? "Switch off" : "Switch on");
//                                                return;
                                    }
                                } else if (name.equals("is open")) {
                                    boolean isOn = Boolean.parseBoolean(sData.getValue());
                                    Icon ic = null;
                                    if (loc.toLowerCase().contains("window") || sensor.getName().toLowerCase().contains("window")) {
                                        ic = isOn ? SensorProperty.WINDOW_OPENED.getIcon() : SensorProperty.WINDOW_CLOSED.getIcon();
                                    } else {
                                        ic = isOn ? SensorProperty.GAP_OPEN_ICON.getIcon() : SensorProperty.GAP_CLOSED_ICON.getIcon();
                                    }
                                    interruptor_plagged = isOn;
                                    oggetto.setIcon(ic);
//                                                return;
                                    multiDoor = true;
//                                            JOptionPane.showMessageDialog(null, "MA CHE CAZZ DISEGNI STE PORTE DI MERDA O NO ? ");
//                                            return;
                                } else if (name.equals("motion")) {
                                    boolean isOn = Boolean.parseBoolean(sData.getValue());
                                    Icon ic = System.currentTimeMillis() - sData.getTimestamp().getTime() <= 30000 ? SensorProperty.PIR_MOVE_ICON.getIcon() : SensorProperty.PIR_NOT_MOVE_ICON.getIcon();
                                    interruptor_plagged = isOn;
                                    oggetto.setIcon(ic);
//                                                return;
                                    multiPir = true;
                                } else {
                                    //E' booleano ma non è is ON, quindi lo metto nella timeline
                                }
                            } else {
                                //non è booleano
                                if (!multiDoor) {
//                                    Iterator keys = sData.getValues().keys();

//                                    while (keys.hasNext()) {
//                                        Object next = keys.next();
//                                        if (!next.toString().equals(name)) {
//                                            continue;
//                                        }
//                                        Double vv = sData.getValues().getDouble(next.toString());
                                    Double vv = Double.valueOf(sData.getValue());
//                                                Date timestamp = sensorData.getTimestamp();
//                                                Icon ic = vv == 0 ? SensorProperty.LAMP_OFF_ICON.getIcon() : SensorProperty.LAMP_ON_ICON.getIcon();
                                    Icon ic = vv != 0 ? SensorProperty.GENERIC_SENSOR_ON.getIcon() : SensorProperty.GENERIC_SENSOR_OFF.getIcon();
                                    if (loc.toLowerCase().contains("lamp")) {
                                        ic = vv == 0 ? SensorProperty.LAMP_OFF_ICON.getIcon() : SensorProperty.LAMP_ON_ICON.getIcon();
                                    }
                                    if (loc.toLowerCase().contains("lamp") && vv == 0) {
                                        ic = SensorProperty.LAMP_OFF_ICON.getIcon();
                                    }
                                    if (name.equals("level")) {
                                        if (vv == 0) {
                                            ic = SensorProperty.LAMP_OFF_ICON.getIcon();
                                        } else if (vv > 0 && vv < 40) {
                                            ic = SensorProperty.LAMP_ON_ICON.getIcon();
                                        } else if (vv >= 40 && vv <= 75) {
                                            ic = SensorProperty.LAMP_MEDIUM_LIGHT.getIcon();
                                        } else if (vv > 75) {
                                            ic = SensorProperty.LAMP_MAX_LIGHT.getIcon();
                                        }
                                    }
                                    if (loc.toLowerCase().contains("tv")) {
                                        ic = vv == 0 ? SensorProperty.TV_OFF.getIcon() : SensorProperty.TV_ON.getIcon();
                                    }
                                    oggetto.setIcon(ic);
//                                        Logger.getLogger(DraggableLabel.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                    }
                                }

//                        JOptionPane.showMessageDialog(null, "LINEAR
                            }
//                                }
//                            WaiterSupporter.getInstance().getSplasher().stop();
                        }
                    }
//                     long endQ1 = System.currentT meMillis();
//                     System.out.println("SENSOR: "+DraggableLabel.this.sensor.getName()+"LR HA IMPIEGATO: "+((float)(endQ1-startQ1)/1000)+ " sec ");
////                    JOptionPane.showMessageDialog(null, "LR HA IMPIEGATO: "+((endQ1-startQ1)*1000)+ " sec ");

                    long startQ = System.currentTimeMillis();
//                    List<SensorData> sendQueryWithPreSample = StorageAPIManager.getInstance().getStorage().sendQueryWithPreSample(5, DraggableLabel.this.sensor.getId(), now, now);
                    long endQ = System.currentTimeMillis();
                    System.out.println("[dvpis@office] pre-sample of sensor: " + DraggableLabel.this.sensor.getName() + " has been loaded in " + ((float) (endQ - startQ) / 1000) + " sec ");
//                    JOptionPane.showMessageDialog(null, "ALES HA IMPIEGATO: "+((endQ-startQ)*1000)+ " sec ");
//                    Data previousSample = null;
//                    if (sendQueryWithPreSample != null && !sendQueryWithPreSample.isEmpty()) {
//                        SensorData sssddd = sendQueryWithPreSample.get(sendQueryWithPreSample.size()-1);
//                        previousSample = new Data(DraggableLabel.this.sensor, sssddd.getTimestamp(), sssddd.getValues());
//                    }
//            List<SensorData> sendQueryWithPreSample = StorageAPIManager.getInstance().getStorage().sendQueryWithPreSample(3, this.sensor.getId(), now, now);
                    if (r_previousSample != null) {
//                        WaiterSupporter.getInstance().getSplasher().publish("previous sample for sensor" + "sensor " + DraggableLabel.this.sensor.getName() + " found at date: " + r_previousSample.getTimestamp());
                        startSensor(r_previousSample);
//                        WaiterSupporter.getInstance().getSplasher().publish("sensor " + DraggableLabel.this.sensor.getName() + " initialized");
                        lastTimeStamp = r_previousSample.getTimestamp();
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy [HH:mm:ss:SSSS]");
                        oggetto.setToolTipText("<html>" + getNome() + "<br><b>Last value" + ":</b> " + format.format(lastTimeStamp));
                        if (HSVSetting.getInstance().isVerbose()) {
                            System.out.println("[dvpis@office] initializind map sensor: " + DraggableLabel.this.sensor.getName());
                        }
                        if (entranceDoor != null && sensor.getId().equals(entranceDoor.getId())) {

                            VirtualDataPool.getInstance().setLastEntranceDataPerHome(home.getId(), r_previousSample);
//                            ObjectId roomID = ReportManager.getInstance().getCachingAPI().getSensor(VirtualDataPool.getInstance().getLastPirDataPerHome().get(home.getId()).getSensor_id()).getRoom();
                            ERoom room = home.whereIsThisSensor(VirtualDataPool.getInstance().getLastPirDataPerHome().get(home.getId()).getSensor());
                            if (positionRevealer instanceof MapPanel) {
                                ((MapPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId(), true);
                            }
//                            else if (positionRevealer instanceof HouseEditorCleanPanel) {
//                                ((HouseEditorCleanPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId(), true);
//                            }
                            //TOREMOVE

                        }
                    }
//                    ServiceDescriptor sensorDescriptor = MiddlewareManager.getInstance().getSensorDescriptor(DraggableLabel.this.sensor.getId());
//                    if (sensorDescriptor == null) {
//                        if (HSVSetting.getInstance().isVerbose()) {
//                            System.out.println("[dvpis.office] House panel is not able to connect this sensor to middleware: " + DraggableLabel.this.sensor.getName());
//                        }
//                    }
//                    else {
////                        WaiterSupporter.getInstance().getSplasher().publish("subscribing to middlwar..");
//                        MiddlewareManager.getInstance().getMiddleware().subscribe(DraggableLabel.this.home.getName(), sensorDescriptor.getContextBusTopic(), DraggableLabel.this, null);
////                        WaiterSupporter.getInstance().getSplasher().publish("sensor: " + DraggableLabel.this.sensor.getName() + " is now subscribed to MW");
//                    }
//                    WaiterSupporter.getInstance().getSplasher().stop();
//                    WaiterSupporter.getInstance().getSplasher().stop();
                } catch (Exception ex) {
                    ex.printStackTrace();
//                    WaiterSupporter.getInstance().getSplasher().stop();
//                    WaiterSupporter.getInstance().getSplasher().stop();
                }
            }
        });
        ttt.start();

//        loadMe(sensor);
//        
//        
//        
//        
//        
// 
//        
//        StorageAPIManager.getInstance().getQueryService().addQueryListener(this);
//        Event.Query query = new Event.Query();
//        query.setQueryType(Event.QueryType.LastValue);
//        query.setHome(this.home.getId());
//        query.setQuerySensor(sensor.getId());
//        query.setSender(VirtualDataPool.getInstance().getCurrentUser().getId());
//        query.setRecipients(new ObjectId[]{VirtualDataPool.getInstance().getCurrentUser().getId()});
//
//        ServiceDescriptor sensorDescriptor = MiddlewareManager.getInstance().getSensorDescriptor(DraggableLabel.this.sensor.getId());
//        if (sensorDescriptor == null) {
//            if (HSVSetting.getInstance().isVerbose()) {
//                System.out.println("[dvpis.office] House panel is not able to connect this sensor to middleware: " + DraggableLabel.this.sensor.getName());
//            }
//        } else {
////                                            WaiterSupporter.getInstance().getSplasher().publish("subscribing to middlwar..");
//            MiddlewareManager.getInstance().getMiddleware().subscribe(DraggableLabel.this.home.getName(), sensorDescriptor.getContextBusTopic(), DraggableLabel.this, null);
////                                            WaiterSupporter.getInstance().getSplasher().publish("sensor: " + DraggableLabel.this.sensor.getName() + " is now subscribed to MW");
//        }
//        StorageAPIManager.getInstance().getQueryService().sendQuery(query);
    }

    public float getKx() {
        return kx;
    }

    public float getKy() {
        return ky;
    }

    public ESensor getSensor() {
        return sensor;
    }

    public int getX() {
        return oggetto.getX();
    }

    public int getY() {
        return oggetto.getY();
    }

    public int getHeight() {
        return oggetto.getHeight();
    }

    public void setHeight(int height) {
        this.oggetto.setBounds(oggetto.getX(), oggetto.getY(), oggetto.getWidth(), height);
    }

    public final String getNome() {
        return nome;
    }

    public final void setNome(String n) {
        this.nome = n;
        if (nome != null) {
            oggetto.setText(nome);
        } else {
            oggetto.setText("");
        }

    }

    public int getWidht() {
        return oggetto.getWidth();
    }

    public void setWidht(int widht) {
        this.oggetto.setBounds(oggetto.getX(), oggetto.getY(), widht, oggetto.getHeight());

    }

    public void setX(int x) {
        this.oggetto.setBounds(x, oggetto.getY(), oggetto.getWidth(), oggetto.getHeight());
    }

    public void setY(int y) {
        this.oggetto.setBounds(oggetto.getX(), y, oggetto.getWidth(), oggetto.getHeight());

    }

    public Color getColor() {
        return oggetto.getBackground();
    }

    public void setColor(Color c) {
        oggetto.setBackground(c);
    }

    public void setVisible(boolean v) {
        if (oggetto != null) {
//            System.out.println("oggetto setting visible: " + v);
            oggetto.setVisible(v);
        }

    }

    private void oggettoMousePressed(java.awt.event.MouseEvent evt) {
        if (!salvato) {
            dx = evt.getX();
            dy = evt.getY();
            salvato = true;
        }
    }

    protected void oggettoMouseDragged(java.awt.event.MouseEvent evt) {
        oggetto.setBounds(oggetto.getX() + evt.getX() - dx, oggetto.getY() + evt.getY() - dy, oggetto.getWidth(), oggetto.getHeight());

    }

    protected void oggettoMouseReleased(java.awt.event.MouseEvent evt) {
        salvato = false;
        if (isLight()) {
            this.kx = ((float) this.getOggetto().getBounds().x) / (float) positionRevealer.revealWidht();

            this.ky = ((float) this.getOggetto().getBounds().y) / (float) positionRevealer.revealHeight();
            this.roomLocation.setSquareX(kx);
            this.roomLocation.setSquareY(ky);
        }

        if (sensor != null && roomLocation == null && userLocation == null) {
            this.kx = ((float) this.getOggetto().getBounds().x) / (float) positionRevealer.revealWidht();

            this.ky = ((float) this.getOggetto().getBounds().y) / (float) positionRevealer.revealHeight();

            System.out.println("WIDTH  = " + positionRevealer.revealWidht());
            System.out.println("HEIGHT = " + positionRevealer.revealHeight());
            System.out.println("this.getOggetto().getBounds().x ---> " + this.getOggetto().getBounds().x);
            System.out.println("this.getOggetto().getBounds().y ---> " + this.getOggetto().getBounds().x);
            System.out.println("kx ---> " + kx);
            System.out.println("ky ---> " + ky);
            sensor.setxMap((float) kx);
            sensor.setyMap((float) ky);

        } else if (sensor == null && roomLocation != null && userLocation == null) {
            this.kx = ((float) this.getOggetto().getBounds().x) / (float) positionRevealer.revealWidht();

            this.ky = ((float) this.getOggetto().getBounds().y) / (float) positionRevealer.revealHeight();

            System.out.println("WIDTH  = " + positionRevealer.revealWidht());
            System.out.println("HEIGHT = " + positionRevealer.revealHeight());
            System.out.println("this.getOggetto().getBounds().x ---> " + this.getOggetto().getBounds().x);
            System.out.println("this.getOggetto().getBounds().y ---> " + this.getOggetto().getBounds().x);
            System.out.println("kx ---> " + kx);
            System.out.println("ky ---> " + ky);
            roomLocation.setX((float) kx);
            roomLocation.setY((float) ky);
        } else if (sensor == null && roomLocation != null && userLocation != null) {
            this.kx = ((float) this.getOggetto().getBounds().x) / (float) positionRevealer.revealWidht();

            this.ky = ((float) this.getOggetto().getBounds().y) / (float) positionRevealer.revealHeight();

            roomLocation.setxPuppet(kx);
            roomLocation.setyPuppet(ky);
//            userLocation.setxPuppet((int) kx);
//            userLocation.setyPuppet((int) ky);
        }

    }

    protected void oggettoMouseClicked(java.awt.event.MouseEvent evt) {

    }

    public boolean isInterruptor_plagged() {
        return interruptor_plagged;
    }

    public JJJLabel getOggetto() {
        return oggetto;
    }

    @Override
    public void messageReceived(ESensorData sensorData) {
        try {

//            JSONObject jSONObject = new JSONObject(string2);
//            ObjectId id = new ObjectId(jSONO  bject.getString("sensor_id"));
            if (sensor != null) {
                if (!this.sensor.getSid().equals(sensorData.getSensor().getSid())) {
                    return;
                }
                System.out.println("DRAGGABLE: MESSAGE RECEIVED");
                System.out.println("DRAGGABLE: Sensor -> " + sensorData.getSensor().getId() + ", " + sensorData.getSensor().getName());
                System.out.println("DRAGGABLE: Type   -> " + sensorData.getSensor().getSensorType().getName());
                System.out.println("DRAGGABLE: Value  -> " + sensorData.getValue());
                System.out.println("DRAGGABLE: ============================");

                if (sensorData != null) {
//                System.out.println("MESSAGE RECEIVED -> \n" + sensorData.getValues().toString(4));
                    if (lastTimeStamp == null) {
                        lastTimeStamp = sensorData.getTimestamp();
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy [HH:mm:ss:SSSS]");
                        oggetto.setToolTipText("<html>" + getNome() + "<br><b>Ultimo valore :</b> " + format.format(lastTimeStamp));
                    } else {
                        if (lastTimeStamp.equals(sensorData.getTimestamp())) {
                            System.out.println("[dvips@office][WARNING] - Impulse detected! (sensor: " + sensorData.getSensor().getId() + ")");
//                        Date now = new Date();
////                        Data previousSample = ReportManager.getInstance().getCachingAPI().getPreviousSample(DraggableLabel.this.sensor, now);
////                        List<Data> ddd = ReportManager.getInstance().getCachingAPI().getData(sensor, new Date(now.getTime() - 1000 * 60 * 10), now);
//////                        if (!ddd.isEmpty()) {
////////                            Data lastValue = ddd.get(ddd.size() - 1);
////////                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX LAST VALUE: ");
////////                            lastValue.getValues().toString(4);
//////                        }
////                        Data previousSample = ReportManager.getInstance().getCachingAPI().getPreviousSample(DraggableLabel.this.sensor, now);
////                        if ( previousSample !=null) {
////                            Data previousSample = ddd.get(ddd.size() - 1);
//                        SensorData previousSample = sensorData.getSensor().getLastSensorData();
//                        if (previousSample != null) {
//                            startSensor(previousSample);
//                            lastTimeStamp = previousSample.getTimestamp();
//                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy [HH:mm:ss:SSSS]");
//                            oggetto.setToolTipText("<html>" + I18nTranslator.getInstance().translate(getNome()) + "<br><b>" + I18nTranslator.getInstance().translate(LangKeywords.HOUSEMAP_TOOLTIP_LASTVALUE) + ":</b> " + format.format(lastTimeStamp));
//                            return;
//                        }
//                        }
                        } else {
                            lastTimeStamp = sensorData.getTimestamp();
                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy [HH:mm:ss:SSSS]");
                            oggetto.setToolTipText("<html>" + getNome() + "<br><b>Ultimo valore:</b> " + format.format(lastTimeStamp));
                        }
                    }
                    String meaning = sensor.getSensorType().getMeaning();
                    System.out.println("MEANING: " + meaning);
                    System.out.println("CLASSIFY: " + SensorTypeClassifier.getDVPISSensorUnit(meaning));
                    if (SensorTypeClassifier.getDVPISSensorUnit(meaning) == SensorTypeClassifier.DVPISSensorType.BOOLEAN) {
//                    LocationEntity loc = home.getLocation(sensor.getLocation());
                        String loc = sensor.getName();
//                    if (loc.getName().toLowerCase().contains("lamp")) { //WAS OK
//                    List<SensorTypeEntity.NameUnitPair> vf = sensor.getSensorType().getValuesFormat();
//                    for (SensorTypeEntity.NameUnitPair unitPair : vf) {
//                        String name = unitPair.name; //nome del sotto sensore
//                        String _unit = unitPair.unit; //unità di misura del sensore
//                    String meaning = sensor.getSensorType().getMeaning(); //nome del sotto sensore
                        String _unit = sensor.getSensorType().getTypeUnit(); //unità di misura del sensore
//                    JOptionPane.showMessageDialog(null, "nome = " + name + ", unit = " + _unit);
                        if (_unit.equals("boolean")) {
                            System.out.println("------>>> BOOLEAN OK");
                            if (meaning.equals("is on")) { // da rimettere is on

                                System.out.println("-------- >>> IS ON");
                                //LA PRESA E' ACCESA O NO
                                //plugged or not
                                if (isInterruptor()) {
                                    System.out.println("-------- >>> IS INTERRUPTOR");
                                    boolean isOn = Boolean.parseBoolean(sensorData.getValue());
                                    Icon ic = isOn ? SensorProperty.PLUGGED_ICON.getIcon() : SensorProperty.UNPLUGGED_ICON.getIcon();
                                    this.interruptor_plagged = isOn;
                                    oggetto.setIcon(ic);
                                    oggetto.setToolTipText(isOn ? "Switch off" : "Switch on");
                                    return;
                                } else {
                                    System.out.println("-------- >>> NOOOO NON INTERR");
                                }
                            } else if (meaning.equals("is open")) {
                                System.out.println("VALUE: " + sensorData.getValue());
                                boolean isOn = Boolean.parseBoolean(sensorData.getValue());
                                System.out.println("IS OPEN ? " + isOn);
                                Icon ic = null;
                                if (loc.toLowerCase().contains("window") || sensor.getName().toLowerCase().contains("window")) {
                                    ic = isOn ? SensorProperty.WINDOW_OPENED.getIcon() : SensorProperty.WINDOW_CLOSED.getIcon();
                                } else {
                                    ic = isOn ? SensorProperty.GAP_OPEN_ICON.getIcon() : SensorProperty.GAP_CLOSED_ICON.getIcon();
                                }
//                                Icon ic = isOn ? SensorProperty.GAP_OPEN_ICON.getIcon() : SensorProperty.GAP_CLOSED_ICON.getIcon();
                                this.interruptor_plagged = isOn;
                                oggetto.setIcon(ic);
//                                JOptionPane.showMessageDialog(null, "MA CHE CAZZ DISEGNI STE PORTE DI MERDA O NO ? ");
                                EUser pu = home.getUsers().get(0);
//                            StaticProfile staticProfile = ReportManager.getInstance().getCachingAPI().getStaticProfile(pu);
                                ESensor entranceDoor = home.getEntranceDoor();

                                if (entranceDoor != null && sensor.getId().equals(entranceDoor.getId())) {

//                                    Data data = new Data(sensor, lastTimeStamp, jSONObject);
//                                  Data thisValue = new Data(sensor, sensorData.getTimestamp(), sensorData.getValues());
                                    VirtualDataPool.getInstance().setLastEntranceDataPerHome(home.getId(), sensorData);
//                                ObjectId roomID = ReportManager.getInstance().getCachingAPI().getSensor(VirtualDataPool.getInstance().getLastPirDataPerHome().get(home.getId()).getSensor_id()).getRoom();
                                    ERoom room = home.whereIsThisSensor(sensor);
                                    if (positionRevealer instanceof MapPanel) {
                                        ((MapPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId(), true);
                                    }
//                                else if (positionRevealer instanceof HouseEditorCleanPanel) {
//                                    ((HouseEditorCleanPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId(), true);
//                                }
                                    //TOREMOVE
//                                    JOptionPane.showMessageDialog(null, "LAST ENTRANCE MERD SETTAATA: " + thisValue.getValues().toString(4));

                                }
//                                return;
                            } else if (meaning.equals("motion")) {
                                boolean isOn = Boolean.parseBoolean(sensorData.getValue());
//                                    if(ApplicationManager.getInstance().getTimingService().now() - sensorData.getTimestamp().getTime() > 30000)
                                Icon ic = new Date().getTime() - sensorData.getTimestamp().getTime() <= 30000 ? SensorProperty.PIR_MOVE_ICON.getIcon() : SensorProperty.PIR_NOT_MOVE_ICON.getIcon();
                                this.interruptor_plagged = isOn;
                                oggetto.setIcon(ic);
//                                    return;
                            } else {
                                //E' booleano ma non è is ON, quindi lo metto nella timeline
                            }
                        } else {
                            //non è booleano

                            if (!multiDoor) {
//                            Iterator keys = sensorData.getValues().keys();

//                            while (keys.hasNext()) {
//                                Object next = keys.next();
//                                if (!next.toString().equals(name)) {
//                                    continue;
//                                }
                                System.out.println("MEANING: " + meaning);
                                System.out.println("--------->>>>>>>>>>>>>>>>>>>>>>>>");
                                Double value = Double.parseDouble(sensorData.getValue());
                                Date timestamp = sensorData.getTimestamp();
                                Icon ic = value != 0 ? SensorProperty.GENERIC_SENSOR_ON.getIcon() : SensorProperty.GENERIC_SENSOR_OFF.getIcon();
                                if (meaning.toLowerCase().contains("lamp")) {
                                    ic = value == 0 ? SensorProperty.LAMP_OFF_ICON.getIcon() : SensorProperty.LAMP_ON_ICON.getIcon();
                                }
                                if (meaning.toLowerCase().contains("lamp") && value == 0) {
                                    ic = SensorProperty.LAMP_OFF_ICON.getIcon();
                                }
                                if (meaning.equals("level")) {
                                    if (value == 0) {
                                        ic = SensorProperty.LAMP_OFF_ICON.getIcon();
                                        System.out.println("AREA 0");
                                    }
                                    if (value > 0 && value < 40) {
                                        System.out.println("AREA 1");
                                        ic = SensorProperty.LAMP_ON_ICON.getIcon();
                                    } else if (value >= 40 && value <= 75) {
                                        System.out.println("AREA 2");
                                        ic = SensorProperty.LAMP_MEDIUM_LIGHT.getIcon();
                                    } else if (value > 75) {
                                        System.out.println("AREA 3");
                                        ic = SensorProperty.LAMP_MAX_LIGHT.getIcon();
                                    }
                                }
                                if (meaning.toLowerCase().contains("tv")) {
                                    ic = value == 0 ? SensorProperty.TV_OFF.getIcon() : SensorProperty.TV_ON.getIcon();
                                }

                                if (value != null && blinker != null) {
                                    float shiftX = ((float) (10)) / ((float) positionRevealer.revealWidht());
                                    float shiftY = ((float) (5)) / ((float) positionRevealer.revealHeight());
                                    if (!isInterruptor()) {
                                        blinker.startBlinking(balug24, this.getKx() + shiftX, this.getKy() + shiftY, 1.1d, 15000, 500);
                                    }
                                }
                                oggetto.setIcon(ic);
//                                        Logger.getLogger(DraggableLabel.class.getName()).log(Level.SEVERE, null, ex);
//                            }
                            }
                        }

//                        JOptionPane.showMessageDialog(null, "LINEAR
//                    }
//                    } //WAS OK
                    }
                    if (SensorTypeClassifier.getDVPISSensorUnit(meaning) == SensorTypeClassifier.DVPISSensorType.BOOLEAN && !multiDoor) {
                        SensorTypeClassifier.SensorTypes typeFromSensorType = SensorTypeClassifier.getTypeFromSensorType(sensor.getSensorType());
                        boolean value = Boolean.parseBoolean(sensorData.getValue());
                        Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, value);
                        if (typeFromSensorType != SensorTypeClassifier.SensorTypes.GAP) {
                            if (value && blinker != null) {
                                float shiftX = ((float) (10)) / ((float) positionRevealer.revealWidht());
                                float shiftY = ((float) (5)) / ((float) positionRevealer.revealHeight());
                                blinker.startBlinking(balug24, this.getKx() + shiftX, this.getKy() + shiftY, 1.1d, 15000, 500);
                            }
                        }
                        oggetto.setIcon(ic);
//                    User pu = ReportManager.getInstance().getCachingAPI().getUser(home.getPrimaryUsers().get(0));
//                    StaticProfile staticProfile = ReportManager.getInstance().getCachingAPI().getStaticProfile(pu, home);
//                    Sensor entranceDoor = staticProfile.getEntranceDoor();

//                    mixedDataPanel.addBooleanData(sensorMap.get(id).getName(), bs1, true);
                    }
                    if (SensorTypeClassifier.getDVPISSensorUnit(meaning) == SensorTypeClassifier.DVPISSensorType.STRANGE_BOOLEAN || multiPir) {
                        System.out.println("MULTI PIR ENTRANCE");
                        if (VirtualDataPool.getInstance().getLastPirDataPerHome().get(this.home.getId()) == null) {
                            VirtualDataPool.getInstance().setLastPirDataPerHome(this.home.getId(), sensorData);
                        }
                        synchronized (VirtualDataPool.getInstance().getLastPirDataPerHome()) {
                            if (!VirtualDataPool.getInstance().getLastPirDataPerHome().containsKey(this.home.getId())) {
                                VirtualDataPool.getInstance().getLastPirDataPerHome().put(this.home.getId(), sensorData);
                            } else {
                                ESensorData oldData = VirtualDataPool.getInstance().getLastPirDataPerHome().get(this.home.getId());

//                            if(oldData.getTimestamp().after(sensorData.getTimestamp())){
//                                System.out.println("Casino temporale !!!!");
//                                System.out.println("Casino temporale !!!!");
//                                System.out.println("Casino temporale !!!!");
//                                System.out.println("Casino temporale !!!!");
//                                System.out.println("Casino temporale !!!!");
//                                System.out.println("Casino temporale !!!!");
//                                System.out.println("Casino temporale !!!!");
//                                
//                                return;
//                            }
                                ESensor oldSensor = oldData.getSensor();
                                if (oldSensor.getId().equals(sensor.getId())) {
                                    System.out.println("Sta sempre qui!");
                                    VirtualDataPool.getInstance().getLastPirDataPerHome().put(this.home.getId(), sensorData);
//                                if (positionRevealer instanceof HouseEditorInformalPUPanel) {
//                                    Room room = ReportManager.getInstance().getCachingAPI().getRoom(sensor.getRoom());
////                                    ((HouseEditorCleanPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId());
//                                }
                                } else {
//                            if()s

                                    ERoom oldRoom = home.whereIsThisSensor(oldSensor);
                                    ERoom room = home.whereIsThisSensor(sensor);

//                                String oldRoomLabel = VirtualDataPool.getInstance().getLabelLocationByRoomId(oldSensor.getRoom());
//                                String labelLocationByRoomId = VirtualDataPool.getInstance().getLabelLocationByRoomId(sensor.getRoom());
                                    if (drawingArrowEnabled) {

//                                    JOptionPane.showMessageDialog(null, "QUALCUNO E' ENTRATO IN : "+room.getName());
                                        blinker.drawArrow(
                                                (float) oldRoom.getX(),//*positionRevealer.revealWidht(),
                                                (float) oldRoom.getY(), //*positionRevealer.revealHeight(),
                                                (float) room.getX(), //*positionRevealer.revealWidht(),
                                                (float) room.getY()); //*positionRevealer.revealHeight());
                                        if (positionRevealer instanceof MapPanel) {
                                            ((MapPanel) positionRevealer).addRoomCardinality(room);
                                        }
//                                    else if (positionRevealer instanceof HouseEditorCleanPanel) {
//                                        ((HouseEditorCleanPanel) positionRevealer).addRoomCardinality(room);
//                                    }
                                    }
                                    VirtualDataPool.getInstance().getLastPirDataPerHome().put(this.home.getId(), sensorData);
                                }
                                if (oldData.getTimestamp().getTime() < (sensorData.getTimestamp().getTime())) {
                                    ERoom room = home.whereIsThisSensor(sensor);
                                    if (positionRevealer instanceof MapPanel) {
                                        ((MapPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId(), true);
                                    }
//                                else if (positionRevealer instanceof HouseEditorCleanPanel) {
//                                    ((HouseEditorCleanPanel) positionRevealer).setPuppetVisible(BASE_PUPPET_LABEL_CODE + BASE_PUPPET_LABEL_DELIMITER + room.getName() + BASE_PUPPET_LABEL_DELIMITER + room.getId(), true);
//                                }

                                }
                            }
                        }
//                    boolean value = sensorData.getValues().getBoolean(unit);
                        Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, true);
                        System.out.println("ENTERING BLINKING");
                        if (blinker != null) {
                            float shiftX = ((float) (10)) / ((float) positionRevealer.revealWidht());
                            float shiftY = ((float) (5)) / ((float) positionRevealer.revealHeight());

                            if (activeBlinkerTimer != null) {
                                blinker.stopBlinker(activeBlinkerTimer);
                            }
                            BlinkerTimer startBlinking = blinker.startBlinking(balug24, this.getKx() + shiftX, this.getKy() + shiftY, 9.8d, impulsiveIdleTime, 100);
                            System.out.println("THE BLINKER IS STARTED");
                            activeBlinkerTimer = startBlinking;

                            if (redPirTimer == null) {
                                redPirTimer = new Timer((int) impulsiveIdleTime, new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        Icon icFalse = multiPir ? SensorProperty.PIR_NOT_MOVE_ICON.getIcon() : VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(DraggableLabel.this.home, sensor, false);
                                        DraggableLabel.this.oggetto.setIcon(icFalse);
                                    }
                                });
                                redPirTimer.setRepeats(false);
                                redPirTimer.start();
                            } else {
                                redPirTimer.setRepeats(false);
                                redPirTimer.restart();
                            }
                        } else {
                            System.out.println("NOOOOOOOOOO NO BLINK");
                        }
                        if (!multiPir) {
                            oggetto.setIcon(ic);
                        }

                    }
//                if (SensorTypeClassifier.getDVPISSensorUnit(unit) == SensorTypeClassifier.DVPISSensorType.FALLEN_ALARM) {
//
////                    boolean value = sensorData.getValues().getBoolean(unit);
//                    if (true) {
//                        blinker.fillSpaceAround(((ImageIcon) SensorTester.fallen), 40, this.getKx(), this.getKy(), 60d, 15d, 30000, 1000);
//                    }
//                    Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, true);
//                    oggetto.setIcon(ic);
//                    AlarmManager.getInstance().alarmIsHuppening(SensorTypeClassifier.getDVPISSensorUnit(unit), I18nTranslator.getInstance().translate(unit));
//                }
//                if (SensorTypeClassifier.getDVPISSensorUnit(unit) == SensorTypeClassifier.DVPISSensorType.FLOODING_ALARM) {
//
//                    boolean value = sensorData.getValues().getBoolean(unit);
//                    Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, value);
//                    if (value) {
//                        blinker.fillSpaceAround(((ImageIcon) SensorTester.flood), 40, this.getKx(), this.getKy(), 60d, 15d, 30000, 1000);
//                    }
//                    oggetto.setIcon(ic);
//                    AlarmManager.getInstance().alarmIsHuppening(SensorTypeClassifier.getDVPISSensorUnit(unit), I18nTranslator.getInstance().translate(unit));
//                }
//                if (SensorTypeClassifier.getDVPISSensorUnit(unit) == SensorTypeClassifier.DVPISSensorType.CO_ALARM) {
//
//                    boolean value = sensorData.getValues().getBoolean(unit);
//                    Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, value);
//                    oggetto.setIcon(ic);
//                    AlarmManager.getInstance().alarmIsHuppening(SensorTypeClassifier.getDVPISSensorUnit(unit), I18nTranslator.getInstance().translate(unit));
//                }
//                if (SensorTypeClassifier.getDVPISSensorUnit(unit) == SensorTypeClassifier.DVPISSensorType.SMOKE_ALARM) {
//
//                    boolean value = sensorData.getValues().getBoolean(unit);
////                    Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(sensor,value);
//                    if (value) {
//                        blinker.fillSpaceAround(((ImageIcon) SensorTester.smoke), 40, this.getKx(), this.getKy(), 60d, 15d, 30000, 1000);
//                    }
//                    oggetto.setIcon((ImageIcon) SensorTester.smoke);
//                    AlarmManager.getInstance().alarmIsHuppening(SensorTypeClassifier.getDVPISSensorUnit(unit), I18nTranslator.getInstance().translate(unit));
//                }
                }
            }

//        } catch (JSONException ex) {
//            Logger.getLogger(DraggableLabel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DraggableLabel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void startSensor(ESensorData sensorData) {

        if (isInterruptor()) {
            return;
        }
        try {
            if (sensorData != null) {
                String unit = sensor.getSensorType().getTypeUnit();
                if (SensorTypeClassifier.getDVPISSensorUnit(unit).equals(SensorTypeClassifier.DVPISSensorType.BOOLEAN) && !multiDoor) {
//                    BooleanDataSupporter bs1 = new BooleanDataSupporter(sensorData.getTimestamp(), sensorData.getValues().getBoolean(unit));
                    //                mixedDataPanel.setEndRange(sensorData.getTimestamp().getTime());
                    boolean value = Boolean.parseBoolean(sensorData.getValue());
                    Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, value);
                    oggetto.setIcon(ic);
//                    mixedDataPanel.addBooleanData(sensorMap.get(id).getName(), bs1, true);
                } else if (SensorTypeClassifier.getDVPISSensorUnit(unit).equals(SensorTypeClassifier.DVPISSensorType.STRANGE_BOOLEAN) || multiPir) {

//                    boolean value = sensorData.getValues().getBoolean(unit);
//                    if (sensorData.getTimestamp().before(new Date(new Date().getTime() - HSVSetting.impulsiveIdleTime))) {
//                        value = false;
//                    }
//                    Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(sensor, value);
//                    oggetto.setIcon(ic);
                    boolean value = true;
                    Date timestamp = sensorData.getTimestamp();
                    if (timestamp.before(new Date(new Date().getTime() - impulsiveIdleTime))) {
                        value = false;
                    }
                    Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, value);
                    if (multiPir) {
                        ic = value ? SensorProperty.PIR_MOVE_ICON.getIcon() : SensorProperty.PIR_NOT_MOVE_ICON.getIcon();
                    }
                    oggetto.setIcon(ic);
//                    JOptionPane.showMessageDialog(null, "VALUE= "+value);
                    if (value && blinker != null && (new Date().getTime() - sensorData.getTimestamp().getTime() < impulsiveIdleTime)) {
                        float shiftX = ((float) (10)) / ((float) positionRevealer.revealWidht());
                        float shiftY = ((float) (5)) / ((float) positionRevealer.revealHeight());

                        BlinkerTimer startBlinking = blinker.startBlinking(balug24, this.getKx() + shiftX, this.getKy() + shiftY, 9.8d, impulsiveIdleTime, 100);
                        if (activeBlinkerTimer != null) {
                            blinker.stopBlinker(activeBlinkerTimer);
                        }
                        activeBlinkerTimer = startBlinking;

                        if (redPirTimer == null) {
                            redPirTimer = new Timer((int) impulsiveIdleTime, new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    Icon icFalse = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(DraggableLabel.this.home, sensor, false);
                                    DraggableLabel.this.oggetto.setIcon(icFalse);
                                }
                            });
                            redPirTimer.setRepeats(false);
                            redPirTimer.start();
                        } else {
                            redPirTimer.setRepeats(false);
                            redPirTimer.restart();
                        }
                    }
                } else if (SensorTypeClassifier.getDVPISSensorUnit(unit).equals(SensorTypeClassifier.DVPISSensorType.FALLEN_ALARM)) {

//                    boolean value = sensorData.getValues().getBoolean(unit);
                    Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, true);
                    oggetto.setIcon(ic);
                } else if (SensorTypeClassifier.getDVPISSensorUnit(unit).equals(SensorTypeClassifier.DVPISSensorType.FLOODING_ALARM)) {

//                    boolean value = sensorData.getValues().getBoolean(unit);
                    Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, true);
                    oggetto.setIcon(ic);
                } else if (SensorTypeClassifier.getDVPISSensorUnit(unit).equals(SensorTypeClassifier.DVPISSensorType.CO_ALARM)) {

//                    boolean value = sensorData.getValues().getBoolean(unit);
                    Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, true);
                    oggetto.setIcon(ic);
                } else if (SensorTypeClassifier.getDVPISSensorUnit(unit).equals(SensorTypeClassifier.DVPISSensorType.SMOKE_ALARM)) {

//                    boolean value = sensorData.getValues().getBoolean(unit);
                    Icon ic = VirtualDataPool.getInstance().getValueHouseMapSensorTypeIcon(this.home, sensor, true);
                    oggetto.setIcon(ic);
                } else {
                    if (HSVSetting.getInstance().isVerbose()) {
                        System.out.println("\t\t**********************");
                        System.out.println("\t\t unknown sensor data: ");
                        System.out.println("\t\t ID: " + sensorData.getSensor().getId());
                        System.out.println("\t\t SENSOR: " + sensor.getName());
                        System.out.println("\t\t TYPE: " + sensor.getSensorType().getName());
                        System.out.println("\t\t UNIT: " + sensor.getSensorType().getTypeUnit());
                        System.out.println("\t\t VALUES: " + sensorData.getValue());
                        System.out.println("\t\t**********************");
                    }
                }
            }

        } catch (Exception ex) {
            if (HSVSetting.getInstance().isVerbose()) {
                System.out.println("[dvpis@office] sensor " + sensor + " failed to start");
            }
            ex.printStackTrace();
            Logger.getLogger(DraggableLabel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    @Override
//    public void serverConnected() {
//
//    }
//
//    @Override
//    public void serverDisconnected() {
//
//    }
//
//    @Override
//    public void userConnected(User user) {
//        if (giraff && user.getId().equals(this.home.getPrimaryUsers().get(0))) {
//            oggetto.setIcon(IconRetriever.giraffONLINE);
//
//            onlineCounter++;
//        } else {
//
//        }
//    }
//
//    @Override
//    public void userConnected(User user, House home) {
//        if (giraff && user.getId().equals(this.home.getPrimaryUsers().get(0))) {
//            oggetto.setIcon(IconRetriever.giraffONLINE);
//            onlineCounter++;
//        } else {
//
//        }
//    }
//
//    @Override
//    public void userDisconnected(User user) {
//        if (giraff && user.getId().equals(this.home.getPrimaryUsers().get(0))) {
//            oggetto.setIcon(IconRetriever.giraffOFFLINE);
//            onlineCounter--;
//        }
//    }
//
//    @Override
//    public void userDisconnected(User user, House home) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void userActionPerformed(User user, Event event) {
//
//    }
//
//    @Override
//    public void userStateChanged(User user, Event.UserState state) {
//
//    }
//
//    @Override
//    public void userProfileChanged(User user, Event.UserProfile profile) {
//
//    }
//
//    @Override
//    public void userEventReceived(User user, Event event) {
//
//    }
//
//    @Override
//    public void isConnected(User user, House home) {
////        System.out.print("is CONNETED ?????????? -> ");
//        if (giraff && user.getId().equals(this.home.getPrimaryUsers().get(0))) {
//            oggetto.setIcon(IconRetriever.giraffONLINE);
//            onlineCounter++;
////            System.out.println(" YES !!!!!!");
//        }
//    }
//
//    @Override
//    public void queryReceived(Event.Query query) {
//
//    }
//
//    public static SensorData fromJSONObjectF(JSONObject o) throws Exception {
//        SensorData data = new SensorData();
//
//        if (o.has("sensor_id")) {
//            System.out.println("Found sensor_id :: " + o.get("sensor_id"));
//            String sensorId = "" + o.get("sensor_id");
//            String idString = ("" + sensorId).substring(1, sensorId.length() - 1);
//            data.setSensor_id(new ObjectId(idString));
//        }
//        if (o.has("timestamp")) {
//            data.setTimestamp(Util.formatter.get().parse((String) o.get("timestamp")));
//        }
//
//        if (o.has("values")) {
//            System.out.println(o.get("values"));
//            if ((o.get("values") != null) && (o.getString("values").compareTo("null") != 0)) {
//                JSONObject vals = o.getJSONObject("values");
//
//                data.setValues(vals);
//            }
//        }
//
//        return data;
//
//    }
    public void setMultiPir(boolean multiPir) {
        this.multiPir = multiPir;
    }

    public boolean isMultiPir() {
        return multiPir;
    }

    public void setMultiDoor(boolean multiDoor) {
        this.multiDoor = multiDoor;
    }

    public boolean isMultiDoor() {
        return multiDoor;
    }

    @Override
    public void houseArrived(EHouse house) {
    }

}
