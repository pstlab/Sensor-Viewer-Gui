/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;

import it.cnr.istc.hsv.abstracts.layers.MyJLayer;
import it.cnr.istc.hsv.abstracts.layers.MyLayer;
import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Luca
 */
public class BlinkableGlassPane extends MyLayer<JComponent> {

    List<BlinkerTimer> blinks = new ArrayList<BlinkerTimer>();
    JPanel panel = null;
    private ConcurrentLinkedQueue<FadingArrow> arrows = new ConcurrentLinkedQueue<FadingArrow>();
    private Color arrowColor = Color.BLUE;
    private int arrowLimit = 3;
    private FadingArrow lastArrow = null;

    public BlinkableGlassPane(JPanel panel) {
        super();
        this.panel = panel;
    }

    public void killAll() {
        for (BlinkerTimer blinkerTimer : blinks) {
            blinkerTimer.stop();
        }

    }

//    public void addExtraSurface(AdditionalExtraSurfacePanel panel){
//        this.extraSurfaces.add(panel);
//        panel.getContainer().setBounds(0, 0, (int)panel.getContainer().getPreferredSize().getWidth(), (int)panel.getContainer().getPreferredSize().getHeight());
//        this.panel.add(panel.getContainer());
//        
//    }
    public void setArrowLimit(int arrowLimit) {
        if (this.arrowLimit == arrowLimit) {
            return;
        }
        this.arrowLimit = arrowLimit;
        if (arrowLimit != -1 && arrows.size() >= arrowLimit) {
            do {
                FadingArrow poll = arrows.poll();
                poll.fading = 0.0f;
            } while (arrows.size() > this.arrowLimit);
        }
    }

    public int getArrowLimit() {
        return arrowLimit;
    }

    public void setArrowColor(Color arrowColor) {
        this.arrowColor = arrowColor;
    }

    public Color getArrowColor() {
        return arrowColor;
    }

    public void drawArrow(float startX, float startY, float endX, float endY) {
        System.out.println("DRAWING ARROW:");
        System.out.println("startX -> "+startX);
        System.out.println("startY -> "+startY);
        System.out.println("endX -> "+endX);
        System.out.println("endY -> "+endY);
        
        if (arrowLimit != -1 && arrows.size() == arrowLimit) {
            System.out.println("arrowLimit = " + arrowLimit);
            System.out.println("arrow size = " + arrows.size());
            System.out.println("EQUAL");
//            if (lastArrow != null) {
            System.out.println("REMOVING LAST");
//                lastArrow.fading = 0;
            FadingArrow poll = arrows.poll();
            poll.fading = 0.0f;
        }
//        } else {

        System.out.println("DRAWING ARROW COME UN PAZZO");
        FadingArrow fadingArrow = new FadingArrow(startX, startY, endX, endY, 1f);
        arrows.add(fadingArrow);
        lastArrow = fadingArrow;
        if (arrows.size() == 1) {
            Thread t = new Thread(new ArrowFaderThread());
            t.start();
        }
//        }

    }

    class ArrowFaderThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    if (arrows.isEmpty()) {
                        System.out.println("[ICV] - removing arrow (fade < 0.0002)");
                        return;
                    }
                    Thread.sleep(50);
//                    System.out.println("----------------------------------------------------------->>>>>>>>>>>>>>>");

                    for (FadingArrow fadingArrow : arrows) {
                        if (fadingArrow.getFading() <= 0.0002f) {
                            arrows.remove(fadingArrow);

                        }
                        if (fadingArrow.getFading() < 0.2) {
                            fadingArrow.setFading(fadingArrow.getFading() - 0.0015f);
                        } else {
//                            fadingArrow.setFading(fadingArrow.getFading() - 0.0004f);
                            fadingArrow.setFading(fadingArrow.getFading() - 0.0009f);
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(BlinkableGlassPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void fillSpaceAround(Icon icon, int max, double x, double y, double radius, double dec, long cap, long min) {

        double startX = x * this.panel.getBounds().getWidth();
        double startY = y * this.panel.getBounds().getHeight();
        for (int i = 0; i < max; i++) {
            Random r = new Random();
            r.setSeed(new Date().getTime());
            int sign = r.nextDouble() > 0.5 ? 1 : -1;
            double coX = startX + (sign * r.nextDouble() * radius);
            double coY = startY + (sign * r.nextDouble() * radius);
            BlinkerTimer t = new BlinkerTimer(icon, coX / this.panel.getWidth(), coY / this.panel.getHeight(), dec, cap, min);
            t.addActionListener(new TimeOutTimer(t));
            blinks.add(t);
//            System.out.println("size of blinks : " + blinks.size());
//            try {
//                Thread.sleep((long)(Math.random()*2000));
//            } catch (InterruptedException ex) {
//                Logger.getLogger(BlinkableGlassPane.class.getName()).log(Level.SEVERE, null, ex);
//            }
            t.setInitialDelay((int) (r.nextDouble() * 2000));
            t.start();
            this.panel.repaint();
//            System.out.println("starting blinking");
        }
//        System.out.println("MEH");
//        try {
//            Thread.sleep(15000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(BlinkableGlassPane.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void spamBlinkingAround(Icon icon, double x, double y, double radius, double dec, long cap, long min) {

        double startX = x * this.panel.getBounds().getWidth() + radius;
        double startY = y * this.panel.getBounds().getHeight();
//        System.out.println("x = "+x);
//        System.out.println("y = "+y);
        int alpha = 0;
        for (int i = 0; i < 8; i++) {
            startX += radius * Math.cos(Math.toRadians(alpha));
            startY += radius * Math.sin(Math.toRadians(alpha));
//            System.out.println("startX = "+startX);
//            System.out.println("startY = "+startY);
            alpha += 45;
            BlinkerTimer t = new BlinkerTimer(icon, startX / this.panel.getWidth(), startY / this.panel.getHeight(), dec, cap, min);
            t.addActionListener(new TimeOutTimer(t));
            blinks.add(t);
//            System.out.println("size of blinks : " + blinks.size());
            t.start();
            this.panel.repaint();
//            System.out.println("starting blinking");
        }
//        System.out.println("MEH");
//        try {
//            Thread.sleep(15000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(BlinkableGlassPane.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public BlinkerTimer startBlinking(Icon icon, double x, double y, double dec, long cap, long min) {
        BlinkerTimer t = new BlinkerTimer(icon, x, y, dec, cap, min);
        t.addActionListener(new TimeOutTimer(t));
        blinks.add(t);
        t.start();
        this.panel.repaint();
        return t;
    }

    public BlinkerTimer startBlinking(Icon icon, double x, double y) {
        BlinkerTimer t = new BlinkerTimer(icon, x, y);
        t.addActionListener(new TimeOutTimer(t));
        blinks.add(t);
        t.start();
        this.panel.repaint();
        return t;
    }

    public void stopBlinker(BlinkerTimer t) {
        t.stop();
        this.blinks.remove(t);
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        MyJLayer jlayer = (MyJLayer) c;
        jlayer.setLayerEventMask(
                AWTEvent.MOUSE_EVENT_MASK
                | AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    @Override
    public void uninstallUI(JComponent c) {
        MyJLayer jlayer = (MyJLayer) c;
        jlayer.setLayerEventMask(0);
        super.uninstallUI(c);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        Graphics2D g2 = (Graphics2D) g.create();
        int i = 0;
        List<BlinkerTimer> garbage = new ArrayList<BlinkerTimer>();

        for (BlinkerTimer blinkerTimer : blinks) {
            if (!blinkerTimer.isBlink()) {
                garbage.add(blinkerTimer);
            } 
            double x = blinkerTimer.getX();
            double y = blinkerTimer.getY();
            int ix = (int) (c.getWidth() * x);
            int iy = (int) (c.getHeight() * y);
//                g2.setPaint(Color.BLUE);
//            g2.drawImage(((ImageIcon) blinkerTimer.getIcon()).getImage(), ix, iy, null);
            g2.drawImage(((ImageIcon) blinkerTimer.getIcon()).getImage(), (int) ix - 16, (int) iy + 4, null);
//            g2.drawImage(((ImageIcon) blinkerTimer.getIcon()).getImage(), 150, 150, null);
//                g2.drawImage(((ImageIcon)SensorTester.balug24).getImage(), ix, iy, null);
//                g2.fillOval(200, 200, 30, 30);
//                System.out.println(" painting "+i+" blinki: "+ix+", "+iy+"  e    x: "+x+", y:"+y + "   -> c.width: "+c.getWidth()+", c.height: "+c.getHeight());
            i++;
        }
        for (BlinkerTimer blinkerTimer : garbage) {
            blinks.remove(blinkerTimer);
        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // ARROWS 
        for (FadingArrow fadingArrow : arrows) {
//            System.out.println("DISEGNAMO UNA BELLA ARROW !!");
            Point startArrowPoint = new Point((int) (c.getWidth() * fadingArrow.getStartX()), (int) (c.getHeight() * fadingArrow.getStartY()));
            Point endArrowPoint = new Point((int) (c.getWidth() * fadingArrow.getEndX()), (int) (c.getHeight() * fadingArrow.getEndY()));

//            Point startArrowPoint = new Point((int) ( fadingArrow.getStartX()), (int) ( fadingArrow.getStartY()));
//            Point endArrowPoint = new Point((int) ( fadingArrow.getEndX()), (int) ( fadingArrow.getEndY()));

            g2.setPaint(new Color(0f, 0f, 1f, fadingArrow.getFading()));

            //line
            double w = Math.abs(startArrowPoint.x - endArrowPoint.x);
            double h = Math.abs(startArrowPoint.y - endArrowPoint.y);
            double x = Math.min(startArrowPoint.x, endArrowPoint.x);
            double y = Math.min(startArrowPoint.y, endArrowPoint.y);
            Rectangle2D.Double bounds = new Rectangle2D.Double(x, y, w, h);
            g2.setStroke(new BasicStroke(5f));
            Path2D.Double path = new Path2D.Double();
            double len = Math.sqrt((startArrowPoint.x - endArrowPoint.x) * (startArrowPoint.x - endArrowPoint.x) + (startArrowPoint.y - endArrowPoint.y) * (startArrowPoint.y - endArrowPoint.y));

//            g2.drawRect(100, 100, 20, 20);
//            System.out.println("star x = "+startArrowPoint.x );
//            System.out.println("end x = "+endArrowPoint.x );
//            System.out.println("start y = "+startArrowPoint.y );
//            System.out.println("end y = "+endArrowPoint.y );
//ì
            double m = ((float) (endArrowPoint.y - startArrowPoint.y)) / ((float) (endArrowPoint.x - startArrowPoint.x));
//            System.out.println(" m = " + m);
            double alfa = Math.atan(m);
//            System.out.println(" alfa = " + alfa);
//            int distac_X = (int) (15 * Math.cos(alfa));
//            int distac_Y = (int) (15 * Math.sin(alfa));
//            System.out.println("seno di 60 è 0.5 = "+Math.sin(3.1415d/6d));
//            System.out.println("distac x = " + distac_X);
//            System.out.println("distac y = " + distac_Y);
//            System.out.println("alfa in gradi è : " + Math.toDegrees(alfa));

            path.moveTo(30, 10);
            path.lineTo(len - 52, 10);
            path.setWindingRule(Path2D.WIND_NON_ZERO);
            Path2D.Double line = path;
            Path2D.Double head;

            path = new Path2D.Double();
            path.moveTo(len - 50, 10);
            path.lineTo(len - 50, 0);
            path.lineTo(len - 30, 10);
            path.lineTo(len - 50, +20);
            path.closePath();
            head = path;

            AffineTransform at = AffineTransform.getRotateInstance(endArrowPoint.x - startArrowPoint.x, endArrowPoint.y - startArrowPoint.y, startArrowPoint.x, startArrowPoint.y);
            at.concatenate(AffineTransform.getTranslateInstance(startArrowPoint.x, startArrowPoint.y));
            line.transform(at);
            head.transform(at);
//            g2.setPaint(arrowColor);
            g2.draw(line);
            g2.fill(head);

        }
//        for (AdditionalExtraSurfacePanel extraSurface : extraSurfaces) {
//                extraSurface.getContainer().setBackground(new Color(1.0f, 0, 0, 0.5f));
//            }

//        Font font = new Font("Verdana", Font.BOLD, 10);
//        g1.setFont(font);
//        FontMetrics cfm = g1.getFontMetrics(font);
//        String ansns = null;
//        if (endingAnnotation.getTriggerText() != null) {
//            ansns = "for Answer: " + endingAnnotation.getTriggerText();
//        }
//        String distance = d + "d " + hours + "h " + min + "m " + seconds + " ss";
//        int distanceTextLenght = cfm.stringWidth(distance);
//        if (endingAnnotation.getTriggerText() != null) {
//            distanceTextLenght = cfm.stringWidth(ansns) > cfm.stringWidth(distance) ? cfm.stringWidth(ansns) : cfm.stringWidth(distance);
//        }
//        int beautyDistance = 0;
//        if (startArrowPoint.y == endArrowPoint.y) {
//            beautyDistance = distanceTextLenght / 2;
//        }
//        RoundRectangle2D.Float r2r = new RoundRectangle2D.Float(
//                startArrowPoint.x + ((endArrowPoint.x - startArrowPoint.x) / 2) - 3 - beautyDistance,
//                ansns != null ? startArrowPoint.y + ((endArrowPoint.y - startArrowPoint.y) / 2) - cfm.getHeight() * 2 - 6 : startArrowPoint.y + ((endArrowPoint.y - startArrowPoint.y) / 2) - cfm.getHeight() - 6,
//                distanceTextLenght + 6,
//                ansns != null ? cfm.getHeight() * 2 : cfm.getHeight(),
//                6,
//                6
//        );
//        g1.setPaint(new Color(255, 255, 255, 200));
//        g1.fill(r2r);
//        g1.setPaint(iCVAnnotationLink.getArrowColor());
//
//        if (ansns != null) {
//            g1.drawString(ansns, startArrowPoint.x + ((endArrowPoint.x - startArrowPoint.x) / 2) - beautyDistance, startArrowPoint.y + ((endArrowPoint.y - startArrowPoint.y) / 2) - 25);
//        }
//        g1.drawString(distance, startArrowPoint.x + ((endArrowPoint.x - startArrowPoint.x) / 2) - beautyDistance, startArrowPoint.y + ((endArrowPoint.y - startArrowPoint.y) / 2) - 10);
//    }
        // END ARROWS 
        // Paint the view.
//            super.paint(g2, c);
//    g2.dispose ();
    }

//    class BlinkerTimer extends Timer{
//
//        private Color color;
//        private double x;
//        private double y;
//        private boolean blink = true;
//        private final List<Double> diffValues = MathUtil.generateDecelerationValues(9.8d, 30000, 300);
//        private int step = 1;
//
//        public BlinkerTimer(Color color, double x, double y, int delay) {
//            super((int)diffValues.get(step), null);
//            this.color = color;
//            this.x = x;
//            this.y = y;
//            this.setRepeats(false);
//        }
//
//        public boolean isBlink() {
//            return blink;
//        }
//
//        public void setBlink(boolean blink) {
//            this.blink = blink;
//        }
//        
//        public void setTimeOut(TimeOutTimer ou){
//            this.addActionListener(ou);
//        }
//        
//        public BlinkerTimer(int delay, TimeOutTimer listener) {
//            super(delay, listener);
//        }
//
//        public Color getColor() {
//            return color;
//        }
//
//        public double getX() {
//            return x;
//        }
//
//        public double getY() {
//            return y;
//        }
//        
//    }    
    class TimeOutTimer implements ActionListener {

        private final BlinkerTimer timer;

        public TimeOutTimer(BlinkerTimer timer) {
            this.timer = timer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!timer.isBlink()) {
                BlinkableGlassPane.this.blinks.remove(timer);
                BlinkableGlassPane.this.panel.repaint();
                return;
            }
            if (BlinkableGlassPane.this.blinks.contains(timer)) {
                BlinkableGlassPane.this.blinks.remove(timer);
            } else {
                BlinkableGlassPane.this.blinks.add(timer);
            }
            timer.updateDelay();
            BlinkableGlassPane.this.panel.repaint();
        }

    }

    class FadingArrow {

        private float startX;
        private float startY;
        private float endX;
        private float endY;
        private float fading;

        public float getStartX() {
            return startX;
        }

        public void setStartX(float startX) {
            this.startX = startX;
        }

        public float getStartY() {
            return startY;
        }

        public void setStartY(float startY) {
            this.startY = startY;
        }

        public float getEndX() {
            return endX;
        }

        public void setEndX(float endX) {
            this.endX = endX;
        }

        public float getEndY() {
            return endY;
        }

        public void setEndY(float endY) {
            this.endY = endY;
        }

        public float getFading() {
            return fading;
        }

        public void setFading(float fading) {
            this.fading = fading;
        }

        public FadingArrow() {
        }

        public FadingArrow(float startX, float startY, float endX, float endY, float fading) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.fading = fading;
        }

    }

}
