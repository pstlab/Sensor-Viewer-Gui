/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.mqtt;

import it.cnr.istc.hsv.abstracts.extra.SensorTypeClassifier;
import it.cnr.istc.hsv.logic.entities.EHouse;
import it.cnr.istc.hsv.logic.entities.ERoom;
import it.cnr.istc.hsv.logic.entities.ERoomType;
import it.cnr.istc.hsv.logic.entities.ESensor;
import it.cnr.istc.hsv.logic.entities.ESensorData;
import it.cnr.istc.hsv.logic.entities.ESensorType;
import it.cnr.istc.hsv.logic.entities.EUser;
import it.cnr.istc.hsv.logic.frommqtt.House;
import it.cnr.istc.hsv.logic.frommqtt.Location;
import it.cnr.istc.hsv.logic.frommqtt.Resident;
import it.cnr.istc.hsv.logic.frommqtt.Room;
import it.cnr.istc.hsv.logic.frommqtt.Sensor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */
public class HouseParser {

    private static HouseParser _instance = null;
    public static final String SWITCH = "Switch";
    private Map<String, ESensor> sidSensorMap = new HashMap<>();

    public static HouseParser getInstance() {
        if (_instance == null) {
            _instance = new HouseParser();
            return _instance;
        } else {
            return _instance;
        }
    }

    private HouseParser() {
        super();

    }

    public EHouse parseHouse(House house) {

        List<String> ignoreList = new ArrayList<>();
        ignoreList.add("tamper sensor");

        ESensorType luminosityType = new ESensorType();
        luminosityType.setMeaning("light");
        luminosityType.setTypeUnit("int");
        luminosityType.setName(SensorTypeClassifier.SensorTypes.LUMINOSITY.typeName());
        luminosityType.setId(178l);

        ESensorType temperatureType = new ESensorType();
        temperatureType.setMeaning("temperature");
        temperatureType.setTypeUnit("float");
        temperatureType.setName(SensorTypeClassifier.SensorTypes.TEMPERATURE.typeName());
        temperatureType.setId(128l);

        ESensorType doorContact = new ESensorType();
        doorContact.setMeaning("is open");
        doorContact.setTypeUnit("boolean");
        doorContact.setName(SensorTypeClassifier.SensorTypes.GAP.typeName());
        doorContact.setId(177l);

        ESensorType energyType = new ESensorType();
        energyType.setMeaning("consume");
        energyType.setTypeUnit("decimal");
        energyType.setLabelUnit("kWh");
        energyType.setName(SensorTypeClassifier.SensorTypes.ENERGY.typeName());
        energyType.setId(1277l);

        ESensorType voltageType = new ESensorType();
        voltageType.setMeaning("consume");
        voltageType.setTypeUnit("decimal");
        voltageType.setLabelUnit("V");
        voltageType.setName(SensorTypeClassifier.SensorTypes.VOLTAGE.typeName());
        voltageType.setId(197l);

        ESensorType currentType = new ESensorType();
        currentType.setMeaning("consume");
        currentType.setTypeUnit("decimal");
        currentType.setLabelUnit("A");
        currentType.setName(SensorTypeClassifier.SensorTypes.CURRENT.typeName());
        currentType.setId(197l);

        ESensorType switchType = new ESensorType();

        switchType.setMeaning("is on");
        switchType.setTypeUnit("boolean");
        switchType.setLabelUnit("0/1");
        switchType.setName(SensorTypeClassifier.SensorTypes.SWITCH.typeName());
        switchType.setId(344l);

        ESensorType powerType = new ESensorType();

        powerType.setMeaning("is on");
        powerType.setTypeUnit("boolean");
        powerType.setLabelUnit("W");
        powerType.setName(SensorTypeClassifier.SensorTypes.POWER.typeName());
        powerType.setId(1785l);

        ESensorType pirContact = new ESensorType();
        pirContact.setMeaning("is present");
        pirContact.setTypeUnit("boolean");
        pirContact.setName(SensorTypeClassifier.SensorTypes.PIR.typeName());
        pirContact.setId(18l);

        EHouse ehouse = new EHouse();
        Map<String, List<ESensor>> esensorIDMap = new HashMap<>();

//            House house = gson.fromJson(message, House.class);
        ehouse.setName(house.getHome_name());
        ehouse.setId(house.getId());
        ehouse.setZid(house.getHome_id());

        ArrayList<Resident> residentList = house.getResidentList();
        for (Resident resident : residentList) {
            EUser eu = new EUser();
            eu.setName(resident.getName());
            eu.setSurname(resident.getSurname());
            ehouse.addEUser(eu);
        }

        long entranceDoorId = house.getEntrance_id();
        System.out.println(" ENTRANCE DOOR ID FROM JSON: " + entranceDoorId);
        ArrayList<Room> roomList = house.getRoomList();
        for (Room room : roomList) {
            ERoom eroom = new ERoom();
            ERoomType eRoomType = new ERoomType();

            eRoomType.setNome(room.getRoomtype());

            eroom.setRoomType(eRoomType);
            eroom.setName(room.getName());
            eroom.setSquareHeight(room.getSquareheight());
            eroom.setSquareWidth(room.getSquarewidth());
            eroom.setSquareX(room.getSquarex());
            eroom.setSquareY(room.getSquarey());
            eroom.setX(room.getX());
            eroom.setY(room.getY());
            eroom.setxPuppet(room.getXpuppet());
            eroom.setyPuppet(room.getYpuppet());

            eroom.setId(ID());

            List<Sensor> switchSensors = new ArrayList<>();

            ArrayList<Location> locationList = room.getLocationList();
            for (Location location : locationList) {
                ArrayList<Sensor> sensorList = location.getSensorList();
                for (Sensor sensor : sensorList) {

                    String labelToIgnore = sensor.getLabel();
                    if (ignoreList.contains(labelToIgnore.toLowerCase())) {
                        System.out.println(" >>> SENSOR IGNORED: " + labelToIgnore + "  (sensor: " + sensor.getSid() + ")");
                        continue;
                    }

                    ESensor eSensor = new ESensor();

                    ESensorType eSensorType = null;
                    //DRAMMA: 
                    if (sensor.getName().equals("gap") && sensor.getType().equals("bool") && sensor.getLabel().toLowerCase().contains("door")) {
                        eSensorType = doorContact;
                    } else if (sensor.getName().equals("pir") && sensor.getLabel().equals("Sensor")) {
                        eSensorType = pirContact;
                    } else if (sensor.getLabel().toLowerCase().equals("switch") && location.getType().contains("fridge") && sensor.getType().equals("bool")) {
                        eSensorType = switchType;
                    } else if (sensor.getLabel().toLowerCase().equals("switch") && location.getType().contains("tv") && sensor.getType().equals("bool")) {
                        eSensorType = switchType;
                    } else if (sensor.getLabel().toLowerCase().equals("switch") && location.getType().contains("lamp") && sensor.getType().equals("bool")) {
                        eSensorType = switchType;
                    } else if (sensor.getLabel().toLowerCase().equals("switch") && location.getType().contains("fan") && sensor.getType().equals("bool")) {
                        eSensorType = switchType;
                    } else if (sensor.getLabel().equals("Temperature")) {
                        eSensorType = temperatureType;
                    } else if (sensor.getLabel().equals("Motion Sensor") && sensor.getType().equals("bool")) {
                        eSensorType = pirContact;
                    } else if (sensor.getLabel().equals("Energy") && sensor.getType().equals("decimal")) {
                        eSensorType = energyType;
                    } else if (sensor.getLabel().equals("Power") && sensor.getType().equals("decimal")) {
                        eSensorType = powerType;
                    } else if (sensor.getLabel().equals("Voltage") && sensor.getType().equals("decimal")) {
                        eSensorType = voltageType;
                    } else if (sensor.getLabel().equals("Current") && sensor.getType().equals("decimal")) {
                        eSensorType = currentType;
                    } //                    else if ( sensor.getLabel().equals("Power") && ) {
                    //                        eSensorType = fridgeType;
                    //                    }
                    else if (sensor.getLabel().equals("Luminance")) {
                        eSensorType = luminosityType;
                        if (eroom.getSquareX() == 0f) {
                            eroom.setSquareX(0.3f);
                            eroom.setSquareY(0.3f);
                            eroom.setSquareWidth(0.4f);
                            eroom.setSquareHeight(0.4f);
                        }
                    } else {
                        eSensorType = new ESensorType();
                        eSensorType.setName(sensor.getSensortype());
                        eSensorType.setId(ID());
                        eSensorType.setMeaning(sensor.getLabel());
                        eSensorType.setLabelUnit(sensor.getUnit());
                        eSensorType.setTypeUnit(sensor.getType());
                    }

                    eSensor.setId(Long.parseLong(sensor.getNode_id()));
                    eSensor.setLocation(location.getType());
                    eSensor.setName(sensor.getName());
                    eSensor.setxMap(location.getXmap());
                    eSensor.setyMap(location.getYmap());
                    eSensor.setSid(sensor.getSid());
                    eSensor.setSensorType(eSensorType);
                    eSensor.setNodeId(sensor.getNode_id());

                    System.out.println("SENSOR ID:   " + sensor.getId());
                    System.out.println("SENSOR SID:   " + sensor.getSid());
                    System.out.println("SENSOR NAME:   " + sensor.getName());
                    System.out.println("LOCATION:      " + location.getType());
                    System.out.println("SENSOR TYPE:   " + sensor.getSensortype());
                    System.out.println("LABEL:         " + sensor.getLabel());
                    System.out.println("UNITS:         " + sensor.getUnit());
                    System.out.println("TYPE:          " + sensor.getType());
                    System.out.println("ASSIGNED TYPE: " + eSensor.getSensorType().getName());
                    System.out.println("=======================================");

                    if (Long.parseLong(sensor.getId()) == entranceDoorId) {

//                        JOptionPane.showMessageDialog(null, "ENTRATA TROVATAAAAAAAA " + eSensor.getId());
                        ehouse.setEntranceDoor(eSensor);
                    }

                    if (sensor.getLastValue() != null) {
                        ESensorData data = new ESensorData();
                        data.setSensor(eSensor);
                        data.setTimestamp(new Date(sensor.getLastValue().getTime()));
                        data.setUnit(sensor.getUnit());
                        data.setValue(sensor.getLastValue().getValue());
                        eSensor.setLastSensorData(data);
                    }

                    if (sensor.getLabel().equals(SWITCH)) {
                        switchSensors.add(sensor);
                    }
                    String node_id = eSensor.getSid().split("-")[0];
                    if (!esensorIDMap.containsKey(node_id)) {
                        esensorIDMap.put(node_id, new ArrayList<ESensor>());
                    }
                    esensorIDMap.get(node_id).add(eSensor);
                    sidSensorMap.put(sensor.getSid(), eSensor);
                    ehouse.addSensor(eSensor);
                    eroom.getSensors().add(eSensor);
                }
                //now linking switch sensor to real sensor
                for (Sensor switchSensor : switchSensors) {
                    String node_id = switchSensor.getNode_id();
                    List<ESensor> switchedSensors = esensorIDMap.get(node_id);
                    for (ESensor switchedSensor : switchedSensors) {
                        switchedSensor.setSwitchData(switchedSensor.getLastSensorData());
                    }
                }

            }
            ehouse.addERoom(eroom);
        }
        return ehouse;
    }

    public ESensor getSensorBySid(String sid) {
        return this.sidSensorMap.get(sid);
    }

    public boolean isThisSensorSwitch(Sensor sensor) {
        if (sensor.getLabel().equals(SWITCH) && sensor.getType().equals("bool") && sensor.getUnit().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private long ID() {
        return new Date().getTime();
    }
}
