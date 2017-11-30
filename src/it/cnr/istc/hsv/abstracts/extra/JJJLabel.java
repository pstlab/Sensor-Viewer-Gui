/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;

import it.cnr.istc.hsv.mqtt.MQTTManager;
import it.cnr.istc.hsv.panels.MapPanel;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.beans.Beans;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class JJJLabel extends javax.swing.JLabel {

//    private JDialog tooltip = null;
    private boolean saved = false;
    private int dx = 0;
    private int dy = 0;
    private boolean rotate = false;
    private boolean increasing = true;
    private double alpha = 0;
    private Thread rotatingThread = null;
    private int rotatingIteration = 0;
    private boolean light = false;
    private boolean enlarge = false;
    private boolean enlarging = false;
    private boolean interruptor = false;
    private boolean on = true;

//    public JJJLabel(DraggableLabel p) {
//        super();
//        oggetto_padre = p;
//    }
    public JJJLabel(DraggableLabel p, boolean location) {
        super();
        oggetto_padre = p;

    }

    public void setOn(boolean on) {
        this.on = on;
    }
    
    

    public void setInterruptor(boolean interruptor) {
        this.interruptor = interruptor;
        if (interruptor) {
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
//                System.out.println("benvenuto gino");
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
//                System.out.println("ciao gino");
                    JJJLabel.this.setCursor(Cursor.getDefaultCursor());
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                    MQTTManager.getInstance().changeSwitch(oggetto_padre.getSensor(), !on);
                }
            });
        }
    }

    public boolean isInterruptor() {
        return interruptor;
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
//                System.out.println("e.x = " + e.getX());
//                System.out.println("w = " + JJJLabel.this.getWidth());
                if (e.getX() > JJJLabel.this.getWidth() - 20 && e.getX() < JJJLabel.this.getWidth()) {
                    JJJLabel.this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                    enlarge = true;
                    oggetto_padre.setDraggable(false);
                } else {
                    JJJLabel.this.setCursor(Cursor.getDefaultCursor());
                    enlarge = false;
                    oggetto_padre.setDraggable(true);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e); //To change body of generated methods, choose Tools | Templates.
                if (enlarging && !oggetto_padre.isDraggable()) {
                    JJJLabel.this.setBounds(
                            JJJLabel.this.getBounds().x,
                            JJJLabel.this.getBounds().y,
                            e.getX(),
                            e.getY());

                }
            }

        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
//                System.out.println("benvenuto gino");
                if (e.getX() > JJJLabel.this.getWidth() - 20 && e.getX() < JJJLabel.this.getWidth()) {
                    JJJLabel.this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                    enlarge = true;
                    oggetto_padre.setDraggable(false);
                } else {
                    JJJLabel.this.setCursor(Cursor.getDefaultCursor());
                    enlarge = false;
                    oggetto_padre.setDraggable(true);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                System.out.println("ciao gino");
                JJJLabel.this.setCursor(Cursor.getDefaultCursor());
                if (!enlarging) {
                    enlarge = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if (enlarging) {
                    enlarge = false;
                    enlarging = false;
                    float kw = (float) e.getX() / (float) MapPanel.editPanelWidth;
                    float kh = (float) e.getY() / (float) MapPanel.editPanelHeight;
                    oggetto_padre.setkWidth(kw);
                    oggetto_padre.setkHeight(kh);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (enlarge) {
                    enlarging = true;
                }
            }

        });
    }

    public DraggableLabel getOggetto_padre() {
        return oggetto_padre;
    }
    private DraggableLabel oggetto_padre;

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
                                JJJLabel.this.repaint();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                                Logger.getLogger(JJJLabel.class.getName()).log(Level.SEVERE, null, ex);
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

        if (this.getIcon() != null && rotate) {
            BufferedImage bi = new BufferedImage(
                    this.getIcon().getIconWidth(),
                    this.getIcon().getIconHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = (Graphics2D) g;//bi.createGraphics();
            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(
                    RenderingHints.KEY_FRACTIONALMETRICS,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2.setRenderingHints(rh);

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.rotate(alpha, bi.getWidth() / 2, bi.getHeight());
        }
        super.paintComponent(g);

    }

    public boolean isRotate() {
        return rotate;
    }

}
