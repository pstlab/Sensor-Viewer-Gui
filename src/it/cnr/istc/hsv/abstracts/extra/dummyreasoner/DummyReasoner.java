/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra.dummyreasoner;

import it.cnr.istc.hsv.abstracts.extra.VirtualDataPool;
import it.cnr.istc.hsv.logic.entities.EHouse;
import it.cnr.istc.hsv.logic.entities.ESensor;
import it.cnr.istc.hsv.logic.entities.ESensorData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 *
 * @author Luca
 */
public class DummyReasoner {

    private static DummyReasoner _instance = null;
    private final long timeout = 1000l * 60l * 30l;
    private Map<Long, ESensorData> lastEntranceDoorDataByHouseMap = new HashMap<Long, ESensorData>();
    private Map<Long, ESensorData> lastEntrancePIRDataByHouseMap = new HashMap<Long, ESensorData>();

    public static DummyReasoner getInstance() {
        if (_instance == null) {
            _instance = new DummyReasoner();
            return _instance;
        } else {
            return _instance;
        }
    }

    private DummyReasoner() {
        super();
    }

    public Boolean isSomeOneInside(EHouse house) {

        try {
//            UserEntity pu = ReportManager.getInstance().getCachingAPI().getUser(house.getPrimaryUsers().get(0));
//            StaticProfile staticProfile = ReportManager.getInstance().getCachingAPI().getStaticProfile(pu);
            ESensor entranceDoor = house.getEntranceDoor();
           
            if (entranceDoor == null) {
                return null;
            }
            ESensorData lastPIRdata = VirtualDataPool.getInstance().getLastPirDataPerHome().get(house.getId());
            ESensorData lastEntranceDataPerHome = VirtualDataPool.getInstance().getLastEntranceDataPerHome(house.getId());

            if (lastEntranceDataPerHome == null || lastPIRdata == null) {
                return null;
            }
            System.out.println("VALUE -> " + lastEntranceDataPerHome.getValue());
            boolean doorClosed = false;
//            if (entranceDoor.getSensorType().getName().equals("Z-Wave Door Window Sensor")) {
//                List<SensorType.NameUnitPair> vf = entranceDoor.getSensorType().getValuesFormat();
//                for (SensorTypeEntity.NameUnitPair unitPair : vf) {
//                    String name = unitPair.name; //nome del sotto sensore
//                    String _unit = unitPair.unit; //unità di misura del sensore
////                    JOptionPane.showMessageDialog(null, "nome = " + name + ", unit = " + _unit);
//                    System.out.println("checking name: "+name);
//                    System.out.println("checking unit: "+_unit);
//                    if (_unit.equals("boolean")) {
//                        if (name.equals("is open")) {
//                            doorClosed = !lastEntranceDataPerHome.getValues().getBoolean(name);
//                        }
//                    }
//                }
//            } else {
                doorClosed = !Boolean.parseBoolean(lastEntranceDataPerHome.getValue());
//            }

            System.out.println("[dvpis@office] entrance door closed: " + doorClosed);
            System.out.println("[dvpis@office] last pir data: " + lastPIRdata.getTimestamp() + " - time: " + lastPIRdata.getTimestamp().getTime());
            System.out.println("[dvpis@office] last entrance door data: " + lastEntranceDataPerHome.getTimestamp() + " - time: " + lastEntranceDataPerHome.getTimestamp().getTime());
            if (doorClosed && lastPIRdata.getTimestamp().getTime() < lastEntranceDataPerHome.getTimestamp().getTime()) {
                System.out.println("[dvpis@office] reasoner output: it seems there is nobody at home");
//                JOptionPane.showMessageDialog(null, "QUA NON C'è NESSUNO ORMAI !!!");
                return false;
            } else if(lastPIRdata.getTimestamp().getTime() > lastEntranceDataPerHome.getTimestamp().getTime())
            {
                System.out.println("[dvpis@office] reasoner output: There is someone into the house");
//                JOptionPane.showMessageDialog(null, "C'è GENTE QUI"); 
                return true;
            }else{
                return null;
            }
//            return null;

//        UserEntity pu = ReportManager.getInstance().getCachingAPI().getUser(house.getPrimaryUsers().get(0));
//        StaticProfile staticProfile = ReportManager.getInstance().getCachingAPI().getStaticProfile(pu, house);
//        SensorEntity entranceDoor = staticProfile.getEntranceDoor();
//        if(entranceDoor == null){
//            return null;
//        }
//        Date now = new Date();
//        RoomEntity entranceRoom = ReportManager.getInstance().getCachingAPI().getRoom(entranceDoor.getRoom());
//        if(entranceRoom == null){
//            return null;
//        }
//        List<SensorEntity> sensorListByRoomName = HouseMapper.getSensorListByRoomName(house.getId(), entranceRoom.getName());
//        System.out.println("*********************************************************");
//        SensorEntity entrancePIR = null;
//        for (SensorEntity sensorEntity : sensorListByRoomName) {
//            System.out.println("room "+entranceRoom.getName()+" contains: "+sensorEntity.getName());
//            if(SensorTypeClassifier.getTypeFromSensorType(sensorEntity.getSensorType()) == SensorTypeClassifier.SensorTypes.PIR){
//                entrancePIR = sensorEntity;
//            }
//        }
//        System.out.println("*********************************************************");
//        if(entrancePIR == null){
//            return null;
//        }
//        
//        Data previousSample = ReportManager.getInstance().getCachingAPI().getPreviousSample(entranceDoor, now);
//        System.out.println("last access entrance door -> "+lastAccessEntranceDoor);
//        System.out.println("last access entrance door value -> "+lastAccessEntranceDoor.);
//        
//        ReportManager.getInstance().getCachingAPI().getLastAccess(entrancePIR);
//        return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public long getOutTime(EHouse home) {
        return -1;
    }

}
