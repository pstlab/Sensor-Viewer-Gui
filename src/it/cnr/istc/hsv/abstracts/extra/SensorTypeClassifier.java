/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;


import it.cnr.istc.hsv.logic.entities.ESensorType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Luca
 */
public class SensorTypeClassifier {
    
    public enum DVPISSensorType{
        BOOLEAN,
        STRANGE_BOOLEAN,
        LINEAR,
        ACTIVITY,
        FALLEN_ALARM,
        FLOODING_ALARM,
        CO_ALARM,
        SMOKE_ALARM,
        MULTI_TYPE,
        UNKNOWN;
    }
    
    public enum SensorTypes {

        PIR("Motion"),
        GAP("Door Contact Sensor"),
        TEMPERATURE("Temperature Sensor"),
        LUMINOSITY("Luminosity Sensor"),
        PRESSURE("Pressure"),
        POWER("Electrical Usage"),
        FLOOD("Flood detector"),
        FALL("Fall detector");

        SensorTypes(String name) {
            this.name = name;
        }

        private String name;
        
        public String typeName(){
            return name;
        }
    }

    
    private static final Map<String, DVPISSensorType> classification = new HashMap<String, DVPISSensorType>();
    
    private static final List<String> alarmMappedList = new ArrayList<String>();
    
    static {
        classification.put("is present", DVPISSensorType.STRANGE_BOOLEAN);
        classification.put("open", DVPISSensorType.BOOLEAN);
        classification.put("is pressed", DVPISSensorType.BOOLEAN);
        classification.put("is on", DVPISSensorType.BOOLEAN);
        classification.put("is open", DVPISSensorType.BOOLEAN);
        classification.put("is fallen", DVPISSensorType.FALLEN_ALARM);
        classification.put("is flooding", DVPISSensorType.FLOODING_ALARM);
        classification.put("co", DVPISSensorType.CO_ALARM);
        classification.put("smoke", DVPISSensorType.SMOKE_ALARM);
        classification.put("MultiType", DVPISSensorType.MULTI_TYPE);
        
        alarmMappedList.add("Fall detector"); //5266a4687d1e4f5713ed9be1
        alarmMappedList.add("CO Detector");
        alarmMappedList.add("Flood detector");
        alarmMappedList.add("Smoke Detector");
    }
    
    public static DVPISSensorType getDVPISSensorUnit(String mehUnit){
        return classification.containsKey(mehUnit) ? classification.get(mehUnit) : DVPISSensorType.UNKNOWN;
    }

    
    public static boolean isSensorAnAlarm(long id){
//        if(id.equals(CachingAPI.CO_DETECTOR)){
//            return true;
//        }
//        if(id.equals(CachingAPI.FALL_DETECTOR)){
//            return true;
//        }
//        if(id.equals(CachingAPI.FLOOD_DETECTOR)){
//            return true;
//        }
//        if(id.equals(CachingAPI.SMOKE_DETECTOR)){
//            return true;
//        }
        return false;
        
//        return alarmMappedList.contains(type);
    }
    
    public static SensorTypes getTypeFromSensorType(ESensorType type){
        String name = type.getName();
        if(name.equals(SensorTypes.GAP.typeName())){
            return SensorTypes.GAP;
        }
        else if(name.equals(SensorTypes.PIR.typeName()) || type.getName().equals("Z-Wave Multisensor PIR")){
            return SensorTypes.PIR;
        }
        else if(name.equals(SensorTypes.POWER.typeName())){
            return SensorTypes.POWER;
        }
        else if(name.equals(SensorTypes.PRESSURE.typeName())){
            return SensorTypes.PRESSURE;
        } else if(name.equals(SensorTypes.FLOOD.typeName())){
            return SensorTypes.FLOOD;
        }else if(name.equals(SensorTypes.FALL.typeName())){
            return SensorTypes.FALL;
        }else{
            return null;
        }
//        
        
    }
}
