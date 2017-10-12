/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.cnr.istc.hsv.abstracts.extra;

import java.util.List;
import javax.swing.Icon;
import javax.swing.Timer;

/**
 *
 * @author Luca
 */
public class BlinkerTimer extends Timer{

        private Icon icon;
        private double x;
        private double y;
        private boolean blink = true;
        private List<Double> diffValues = null; //MathUtil.generateDecelerationValues(9.8d, 30000, 100);
        private int step = 1;
        private double deceleration = 9.8d;
        private long capTime = 30000;
        private long minimumDelay = 100;

        public BlinkerTimer(Icon icon, double x, double y) {
            super(0, null);
            diffValues = MathUtil.generateDecelerationValues(deceleration, capTime, minimumDelay);
            setDelay(diffValues.get(step).intValue());
            this.icon = icon;
            this.x = x;
            this.y = y;
            this.setRepeats(true);
            step++;
        }
        
        public BlinkerTimer(Icon icon, double x, double y, double deceleration, long capTime, long minDelay) {
            super(0, null);
            this.deceleration = deceleration;
            this.capTime = capTime;
            this.minimumDelay = minDelay;
            diffValues = MathUtil.generateDecelerationValues(deceleration, capTime, minimumDelay);
            setDelay(diffValues.get(step).intValue());
            this.icon = icon;
            this.x = x;
            this.y = y;
            this.setRepeats(true);
            step++;
        }

    public Icon getIcon() {
        return icon;
    }
        
        
        
        

    public double getDeceleration() {
        return deceleration;
    }

    public void setDeceleration(double deceleration) {
        this.deceleration = deceleration;
    }

    public long getCapTime() {
        return capTime;
    }

    public void setCapTime(long capTime) {
        this.capTime = capTime;
    }

    public long getMinimumDelay() {
        return minimumDelay;
    }

    public void setMinimumDelay(long minimumDelay) {
        this.minimumDelay = minimumDelay;
    }
        
        

        public boolean isBlink() {
            return blink;
        }

        public void setBlink(boolean blink) {
            this.blink = blink;
        }
        
        public void setTimeOut(BlinkableGlassPane.TimeOutTimer ou){
            this.addActionListener(ou);
        }
        
        public void updateDelay(){
            if(step >= diffValues.size()-1){
                setRepeats(false);
                this.blink = false;
                return;
            }
            setDelay(diffValues.get(step).intValue());
            step++;
        }
        
        public BlinkerTimer(int delay, BlinkableGlassPane.TimeOutTimer listener) {
            super(delay, listener);
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
        
    } 
