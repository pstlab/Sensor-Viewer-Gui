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
        doorContact.setId(1l);

        ESensorType tvType = new ESensorType();

        tvType.setMeaning("is open");
        tvType.setTypeUnit("boolean");
        tvType.setName(SensorTypeClassifier.SensorTypes.POWER.typeName());
        tvType.setId(1l);

        ESensorType fridgeType = new ESensorType();

        fridgeType.setMeaning("is open");
        fridgeType.setTypeUnit("boolean");
        fridgeType.setName(SensorTypeClassifier.SensorTypes.GAP.typeName());
        fridgeType.setId(1l);

        ESensorType pirContact = new ESensorType();
        pirContact.setMeaning("is present");
        pirContact.setTypeUnit("boolean");
        pirContact.setName(SensorTypeClassifier.SensorTypes.PIR.typeName());
        pirContact.setId(1l);

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

        int entranceDoorId = house.getEntrance_id();
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
                    ESensor eSensor = new ESensor();

                    ESensorType eSensorType = null;
                    //DRAMMA: 
                    if (sensor.getSensortype().equals("bool") && location.getType().contains("door")) {
                        eSensorType = doorContact;
                    } else if (sensor.getSensortype().equals("bool") && sensor.getLabel().equals("Sensor")) {
                        eSensorType = pirContact;
                    } else if (sensor.getLabel().equals("Switch") && location.getType().contains("fridge") && sensor.getSensortype().equals("bool")) {
                        eSensorType = fridgeType;
                    } else if (sensor.getLabel().equals("Temperature")) {
                        eSensorType = temperatureType;
                    } else if (sensor.getLabel().equals("Luminance")) {
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
                        eSensorType.setLabelUnit(sensor.getUnits());
                        eSensorType.setTypeUnit(sensor.getType());
                    }

                    eSensor.setId(Long.parseLong(sensor.getNode_id()));
                    eSensor.setLocation(location.getType());
                    eSensor.setName(sensor.getName());
                    eSensor.setxMap(location.getXmap());
                    eSensor.setyMap(location.getYmap());
                    eSensor.setSid(sensor.getSid());
                    eSensor.setSensorType(eSensorType);

                    System.out.println("SENSOR NAME:   " + sensor.getName());
                    System.out.println("LOCATION:      " + location.getType());
                    System.out.println("SENSOR TYPE:   " + sensor.getSensortype());
                    System.out.println("LABEL:         " + sensor.getLabel());
                    System.out.println("UNITS:         " + sensor.getUnits());
                    System.out.println("TYPE:          " + sensor.getType());
                    System.out.println("ASSIGNED TYPE: " + eSensor.getSensorType().getName());
                    System.out.println("=======================================");

                    if (eSensor.getId().equals(entranceDoorId)) {
                        ehouse.setEntranceDoor(eSensor);
                    }

                    if (sensor.getLastValue() != null) {
                        ESensorData data = new ESensorData();
                        data.setSensor(eSensor);
                        data.setTimestamp(new Date(sensor.getLastValue().getTime()));
                        data.setUnit(sensor.getUnits());
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
        if (sensor.getLabel().equals(SWITCH) && sensor.getType().equals("bool") && sensor.getUnits().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private long ID() {
        return new Date().getTime();
    }
}
