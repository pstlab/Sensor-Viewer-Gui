/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.mqtt;

import it.cnr.istc.hsv.logic.entities.ESensorData;
import it.cnr.istc.hsv.logic.entities.EHouse;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */
public interface MessageListener {
    
//    	/**
//	 * The callback method to be notified about the message received.
//	 * 
//	 * @location
//	 * @param topic
//	 *            - the topic where the message has been published
//	 * @param message
//	 *            - the actual message serialized as String
//	 */
//	public void messageReceived(String location, String topic, String message);
    
    public void messageReceived(ESensorData data);
    
    public void houseArrived(EHouse house);
    
}
