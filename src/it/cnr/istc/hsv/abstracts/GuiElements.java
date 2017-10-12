/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts;

//import it.cnr.istc.dvpis.gui.spot.monitor.enums.SensorProperty;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
//import si.xlab.giraffplus.storage.entities.GiraffEntity;

/**
 *
 * @author Luca
 */
public class GuiElements {

    public static final Color GiraffPlus_Orange = new Color(255, 204, 0);
    public static final Color GiraffPlus_LightOrange = new Color(255, 225, 137);
    public static final Color GiraffPlus_CommonYellow = new Color(255, 255, 204);
    
    
//    public static final Image gasMaskBig = new javax.swing.ImageIcon(GuiElements.class.getResource("/it/cnr/istc/dvpis/images/gasMask.png")).getImage();
//    public static final Image floodIcon = new javax.swing.ImageIcon(GuiElements.class.getResource("/it/cnr/istc/dvpis/images/pozzanghera_tra64.png")).getImage();
//    public static final Image smokeIcon = new javax.swing.ImageIcon(GuiElements.class.getResource("/it/cnr/istc/dvpis/images/smoke.png")).getImage();
//    
//    public static final Image slipperyIcon64 = new javax.swing.ImageIcon(GuiElements.class.getResource("/it/cnr/istc/dvpis/images/slipper64.png")).getImage();
//    public static final Image slipperyIcon48 = new javax.swing.ImageIcon(GuiElements.class.getResource("/it/cnr/istc/dvpis/images/slipper48.png")).getImage();
//    public static final Image slipperyIcon32 = new javax.swing.ImageIcon(GuiElements.class.getResource("/it/cnr/istc/dvpis/images/slipper32.png")).getImage();
//    public static final Image slipperyIcon24 = new javax.swing.ImageIcon(GuiElements.class.getResource("/it/cnr/istc/dvpis/images/slipper24.png")).getImage();
    
    
            
    
    
    public static OrangeSplitDividerUI createDividerUI(){
        return new OrangeSplitDividerUI();
    }

    static final class OrangeSplitDividerUI extends BasicSplitPaneUI {

        @Override
        public BasicSplitPaneDivider createDefaultDivider() {
            return new BasicSplitPaneDivider(this) {
                @Override
                public void setBorder(Border b) {
                }
                
//                GiraffEntity gir = new GiraffEntity();
                

                @Override
                public void paint(Graphics g) {
                    
                    g.setColor(GiraffPlus_Orange);
                    g.fillRect(0, 0, getSize().width, getSize().height);
                    super.paint(g);
                }
            };
        }
    }
}
