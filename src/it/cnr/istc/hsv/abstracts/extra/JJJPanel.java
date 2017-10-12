/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.Beans;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class JJJPanel extends RoundedContainerPanel    {

//    private JDialog tooltip = null;
    private boolean saved = false;
    private int dx = 0;
    private int dy = 0;
    private boolean rotate = false;
    private boolean increasing = true;
    private double alpha = 0;
    private Thread rotatingThread = null;
    private int rotatingIteration = 0;

//    public JJJLabel(DraggableLabel p) {
//        super();
//        oggetto_padre = p;
//    }
    public JJJPanel(DraggablePanel p, boolean location) {
        super();
        oggetto_padre = p;

    }

    public DraggablePanel getOggetto_padre() {
        return oggetto_padre;
    }
    private DraggablePanel oggetto_padre;

    public void setRotate(boolean rotateOrder) {
        this.rotate = rotateOrder;
        if (!rotateOrder) {
            this.rotatingIteration = Integer.MAX_VALUE;
        } else {
            this.rotatingIteration = 0;
            if (rotatingThread == null) {
                rotatingThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (rotatingIteration < 1800) {
                            try {
                                Thread.sleep(50);
                                if (increasing) {
                                    if (rotatingIteration < 100) {
                                        alpha += 0.027d;
                                    } else if (rotatingIteration >= 100 && rotatingIteration < 300) {
                                        alpha += 0.022d;
                                    } else if (rotatingIteration >= 300 && rotatingIteration < 500) {
                                        alpha += 0.017d;
                                    } else if (rotatingIteration >= 500 && rotatingIteration < 800) {
                                        alpha += 0.015d;
                                    } else if (rotatingIteration >= 800 && rotatingIteration < 1200) {
                                        alpha += 0.012d;
                                    } else if (rotatingIteration >= 1200 && rotatingIteration < 1800) {
                                        alpha += 0.01d;
                                    } else {
                                        alpha += 0.03d;
                                    }

                                    if (alpha > 0.16) {
                                        increasing = false;
                                    }
                                } else {
                                    if (rotatingIteration < 100) {
                                        alpha -= 0.027d;
                                    } else if (rotatingIteration >= 100 && rotatingIteration < 300) {
                                        alpha -= 0.022d;
                                    } else if (rotatingIteration >= 300 && rotatingIteration < 500) {
                                        alpha -= 0.017d;
                                    } else if (rotatingIteration >= 500 && rotatingIteration < 800) {
                                        alpha -= 0.015d;
                                    } else if (rotatingIteration >= 800 && rotatingIteration < 1200) {
                                        alpha -= 0.012d;
                                    } else if (rotatingIteration >= 1200 && rotatingIteration < 1800) {
                                        alpha -= 0.01d;
                                    } else {
                                        alpha -= 0.03d;
                                    }
                                    if (alpha < -0.16) {
                                        increasing = true;
                                    }
                                }
                                rotatingIteration++;
                                JJJPanel.this.repaint();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                                Logger.getLogger(JJJPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        alpha = 0;
                        rotatingThread = null;
                    }
                });
                rotatingThread.start();
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

    }

    public boolean isRotate() {
        return rotate;
    }

}
