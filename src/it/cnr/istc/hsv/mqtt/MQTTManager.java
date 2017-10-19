/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.mqtt;

import it.cnr.istc.hsv.logic.entities.ESensorData;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.google.gson.Gson;
import it.cnr.istc.hsv.logic.entities.EHouse;
import it.cnr.istc.hsv.logic.entities.ERoom;
import it.cnr.istc.hsv.logic.entities.ERoomType;
import it.cnr.istc.hsv.logic.entities.ESensor;
import it.cnr.istc.hsv.logic.entities.ESensorType;
import it.cnr.istc.hsv.logic.frommqtt.House;
import it.cnr.istc.hsv.logic.frommqtt.Location;
import it.cnr.istc.hsv.logic.frommqtt.Room;
import it.cnr.istc.hsv.logic.frommqtt.Sensor;
import it.cnr.istc.hsv.panels.MapPanel;


/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */
public class MQTTManager implements MqttCallback {

    private static MQTTManager _instance = null;
    private List<MessageListener> messageListeners = new ArrayList<>();
    private String broker = "tcp://150.146.65.149:1883";
//    public static final String IP = "tcp://150.146.65.68:1883"; //per connetterlo al broker ActiveMQ
    private String clientId = "gui";
    private boolean ignore = false;
    private MqttClient sampleClient = null;
    public static final String ASK_CONFIG = "house-config-file";
    public static final String GET_CONFIG = "get-house-config-file";
    public static final String SWITCH = "Switch";
    
    public boolean TEST = true;

    public MapPanel mapPanelTest = null;
    public static MQTTManager getInstance() {
        if (_instance == null) {
            _instance = new MQTTManager();
            return _instance;
        } else {
            return _instance;
        }
    }

    public String getClientId() {
        return clientId;
    }

    public void setMapPanelTest(MapPanel mapPanelTest) {
        this.mapPanelTest = mapPanelTest;
    }
    
    
    
    
    

    public void askConfiguration() {
        try {
            System.out.println("asking configuration..");
            publish(ASK_CONFIG, clientId);
        } catch (MqttException ex) {
            Logger.getLogger(MQTTManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private MQTTManager() {
        super();

    }

    public void addMessageListener(MessageListener listener) {
        System.out.println("someone registered!");
        this.messageListeners.add(listener);
    }

    public void newSensorData(ESensorData data) {
        for (MessageListener messageListener : messageListeners) {
            messageListener.messageReceived(data);
        }

    }

    public void subscribe(String topic) {
        try {
            sampleClient.subscribe(topic);
        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    public void publish(String topic, String message) throws MqttException {
        System.out.println("sending message:");
        System.out.println("\tTopic: " + topic);
        System.out.println("\tMessage: " + message);
        System.out.println("----------------------------------------------");
        sampleClient.publish(topic, new MqttMessage(message.getBytes()));
    }

    public void connect() {

        
        try {
            InetAddress localIp = InetAddress.getLocalHost();
            System.out.println("IP of my system is := " + localIp.getHostAddress());

//            if 
//                    mqtt == null && !isProcessRunning("mosquitto.exe *32")) {
//                mqtt = Runtime.getRuntime().exec("./mosquitto/mosquitto.exe");
            System.out.println("MQTT connected");
            System.out.println("subscribing all");
            MemoryPersistence persistence = new MemoryPersistence();
            try {

//                    Thread.sleep(1000);
                sampleClient = new MqttClient(broker, clientId, persistence);
                if(TEST){
                    return;
                }
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(false);
                connOpts.setKeepAliveInterval(Integer.MAX_VALUE);
                System.out.println("Connecting to broker: " + broker);
                sampleClient.connect(connOpts);
                System.out.println("Connected");
                sampleClient.setCallback(_instance);
                subscribe(GET_CONFIG+"/"+clientId);
//                    sampleClient.subscribe("youtube");
//                    sampleClient.subscribe("gesture");
//                    sampleClient.subscribe("master");
//                    sampleClient.subscribe("speak");
//                    sampleClient.subscribe("#");
//                    System.out.println("Disconnected");
            } catch (MqttException me) {
                System.out.println("reason " + me.getReasonCode());
                System.out.println("msg " + me.getMessage());
                System.out.println("loc " + me.getLocalizedMessage());
                System.out.println("cause " + me.getCause());
                System.out.println("excep " + me);
                me.printStackTrace();
            }

//            }
//            this.messageArrived("TEST", null);
        } catch (IOException ex) {
            Logger.getLogger(MQTTManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MQTTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        System.out.println("message received -> " + mm);
        System.out.println("topic: " + topic);
        if (topic.equals(GET_CONFIG+"/"+clientId) && !ignore) {
            System.out.println("CONFIGURAZIONE ARRIVATA !!!!!!!!!!!");
            String message = new String(mm.getPayload());
            System.out.println("message: \n");
            System.out.println(message);
            Gson gson = new Gson();
//            EHouse ehouse  = new EHouse();
            
            House house = gson.fromJson(message, House.class);
            
          
            EHouse eHouse = HouseParser.getInstance().parseHouse(house);
            mapPanelTest.setHouse(eHouse);
//            for (MessageListener messageListener : messageListeners) {
//                messageListener.houseArrived(eHouse);
//            }
            System.out.println("NAME of House: "+house.getHome_name());
            System.out.println("ID of House: "+house.getHome_id());
//            System.out.println("NAME of House: "+house.getHome_name());
            
        }
    }
    
    private long ID(){
        return new Date().getTime();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        
    }
    
   
            
}
