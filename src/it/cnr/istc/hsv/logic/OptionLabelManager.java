/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.logic;

import it.cnr.istc.hsv.abstracts.OptionLabelListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it> ISTC-CNR
 */
public class OptionLabelManager {
    
    private static OptionLabelManager _instance = null;
    private List<OptionLabelListener> listeners = new ArrayList<>();
    
    public static OptionLabelManager getInstance() {
        if (_instance == null) {
            _instance = new OptionLabelManager();
            return _instance;
        } else {
            return _instance;
        }
    }
    
    private OptionLabelManager() {
        super();
    }
    
    public void addOptionLabelListener(OptionLabelListener listener){
        this.listeners.add(listener);
    }
    
    public void hide(String sid){
        for (OptionLabelListener listener : listeners) {
            listener.hide(sid);
        }
    }
    
    public void show(String sid){
        for (OptionLabelListener listener : listeners) {
            listener.show(sid);
        }
    }
}
