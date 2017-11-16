/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.cnr.istc.hsv.abstracts.extra;

import javax.swing.Icon;

/**
 *
 * @author Luca Coraci
 */
public enum SensorProperty{
    //"General Info","Timelines","State Variables","Events","Special","Goals"
    ROOT_HOUSE(0,"houseIcon",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/GiraffHouse32.png"))),
    STATUS_OK(1,"Status Ok",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/sensor32.png"))),
    STATUS_DISCONNECTED(2,"Disconnected",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/sensor_off32.png"))),
    ALARM_ICON(2,"Alarm",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/GiraffWarning32.png"))),
    INIT_ALARM_ICON(2,"Alarm",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/GiraffWarning32bw.png"))),
    
//    GAP_OPEN_ICON(3,"Gap Open",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/doorOpen64.png"))),
//    GAP_CLOSED_ICON(4,"Gap Closed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/doorClosed64.png"))),
//    PIR_MOVE_ICON(5,"Pir Movement",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/sensor64_data.png"))),
//    PIR_NOT_MOVE_ICON(6,"Pir not Movement",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/sensor64_noData.png"))),
//    ELECTRICITY_ON_ICON(7,"Power ON",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/powerOn32.png"))),
//    ELECTRICITY_OFF_ICON(8,"Power OFF",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/powerOff32.png"))),
//    PRESSURE_ON_ICON(9,"Is Pressed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/crappyOldChair64.png"))),
//    PRESSURE_OFF_ICON(10,"Is not Pressed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/chair-icon64.png"))),
    
    GAP_OPEN_ICON(3,"Gap Open",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/doorOpen64.png"))),
    GAP_CLOSED_ICON(4,"Gap Closed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/doorClosed64.png"))),
    INIT_GAP_CLOSED_ICON(4,"Gap Closed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/doorClosed64bw.png"))),
    PIR_MOVE_ICON(5,"Pir Movement",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/pirOn_32.png"))),
    INIT_PIR_MOVE_ICON(5,"Pir Movement",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/pirOn_32bw.png"))),
    PIR_NOT_MOVE_ICON(6,"Pir not Movement",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/pirOff_32.png"))),
    ELECTRICITY_ON_ICON(7,"Power ON",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/powerOn32.png"))),
    INIT_ELECTRICITY_ON_ICON(7,"Power ON",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/powerOn32bw.png"))),
    ELECTRICITY_OFF_ICON(8,"Power OFF",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/powerOff32.png"))),
    PRESSURE_ON_ICON(9,"Is Pressed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/pressureOn_32.png"))),
    INIT_PRESSURE_ON_ICON(9,"Is Pressed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/pressureOn_32bw.png"))),
    PRESSURE_OFF_ICON(10,"Is not Pressed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/pressureOff_32.png"))),
    
    GENERIC_SENSOR_OFF(11,"Is not Pressed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/sensor64_noData.png"))),
    GENERIC_SENSOR_ON(12,"Is not Pressed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/sensor64_data.png"))),
    
    FRIDGE_OPEN(13,"Fridge Open",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/fridgeOpened64.png"))),
    FRIDGE_CLOSED(14,"Fridge Closed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/fridgeClosed64.png"))),
    INIT_RIDGE_CLOSED(14,"Fridge Closed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/fridgeClosed64bw.png"))),
    
    WARDROBE_OPEN(15,"Wardrobe Open",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/wardrobeGrande64.png"))),
    WARDROBE_CLOSED(16,"Wardrobe Closed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/wardrobeGrandeClosed64.png"))),
    INIT_WARDROBE_CLOSED(16,"Wardrobe Closed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/wardrobeGrandeClosed64bw.png"))),
    WINDOW_OPENED(17,"Window Open",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/VentanaAperta3.png"))),
    WINDOW_CLOSED(18,"Window Closed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/VentanaChiusa3.png"))),
    INIT_WINDOW_CLOSED(18,"Window Closed",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/VentanaChiusa3bw.png"))),
    
    PLUGGED_ICON(30,"Power Enabled",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/connectedGreen32.png"))),
    UNPLUGGED_ICON(31,"Power Disabled",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/disconnected_unplug32.png"))),
    LAMP_ON_ICON(32,"Lamp is on",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/Desk_lamp_icon64.png"))),
    LAMP_OFF_ICON(33,"Lamp is off",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/Desk_lamp_icon64OFF.png"))),
    INIT_PLUGGED_ICON(34,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/connected32gray.png"))),
    INIT_UNPLUGGED_ICON(35,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/disconnected32gray.png"))),
    INIT_LAMP_ICON(36,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/Desk_lamp_icon64_Init.png"))),
    
    TV_ON(36,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/tv_64_ON.png"))),
    TV_OFF(36,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/tv_64_OFF.png"))),
    INIT_TV(36,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/TV_64_GREY.png"))),
    
    LAMP_MEDIUM_LIGHT(36,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/lamp_level1.png"))),
    LAMP_MAX_LIGHT(36,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/lamp_2_level2.png"))),
    
    TERMO_INACTIVE(36,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/termo_inactive32.png"))),
    TERMO_OK(36,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/termo_ok32.png"))),
    TERMO_HOT(36,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/termo_hot32.png"))),
    TERMO_COLD(36,"unknown",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/termo_cold32.png"))),
    
    ENERGY_OFF(36,"energy",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/powerUsage_OFF32.png"))),
    ENERGY_ON(36,"energy",new javax.swing.ImageIcon(SensorProperty.class.getResource("/it/cnr/istc/hsv/images/powerUsage_ON32.png")));
            
    

    private int value;
    private String customName;
    private Icon icon;    

    SensorProperty() {
    }

    SensorProperty(int val,String name, Icon icon) {
        value = val;
        customName = name;
        this.icon = icon;
    }

    public static SensorProperty setValue(int i) {
        SensorProperty my = null;
        switch (i) {
            case 1:
                my = SensorProperty.STATUS_OK;
                break;
            case 2:
                my = SensorProperty.STATUS_DISCONNECTED;
        }
        return my;
    }

    public int getValue() {
        return value;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getName()
    {
        return customName;
    }
    
    

    @Override
    public String toString(){
        return customName;
    }

}
