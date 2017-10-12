/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.istc.hsv.abstracts.extra;



import it.cnr.istc.hsv.logic.entities.EHouse;
import it.cnr.istc.hsv.logic.entities.ESensor;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class DraggablePanel {

    private ESensor sensor = null;
    private String location = null;
    private float kx;
    private float ky;
    private EHouse home = null;
    private boolean draggable = false;
    private BlinkableGlassPane blinker = null;
    private BlinkerTimer activeBlinkerTimer;
//    private JPanel presenceChart = null;
    protected JJJPanel oggetto;
    protected String nome = "";
    private boolean salvato = false;
    private int dx = 0;
    private int dy = 0;
    private Timer redPirTimer = null;
    private PositionRevealer positionRevealer;
    private Date lastTimeStamp = null;

    protected int myIndex = -1;
    private boolean drawingArrowEnabled = true;

    public DraggablePanel(float startingX, float startingY, PositionRevealer positionRevealer, JPanel panel) {
        this.positionRevealer = positionRevealer;
        oggetto = new JJJPanel(this, true);
//        oggetto.setBackground(Color.red);
//        oggetto.setLayout(new GridLayout(0, 1));
//        oggetto.addComponent(panel);
//        [255,204,0]
//        oggetto.setBackground(new Color(255,204,0,230));
        Color infoBackgroundColor = HSVSetting.getInstance().getInfoBackgroundColor();
        oggetto.setBackground(infoBackgroundColor);
        panel.setOpaque(false);
        oggetto.addComponent(panel);
        
        
        this.kx = startingX;
        this.ky = startingY;
//        oggetto.setOpaque(true);
          float newX = positionRevealer.revealWidht() * this.getKx();
            float newY = positionRevealer.revealHeight() * this.getKy();
        oggetto.setBounds((int)newX, (int)newY, panel.getPreferredSize().width + 65, panel.getPreferredSize().height+65);
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                oggettoMouseClicked(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                oggettoMousePressed(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                oggettoMouseReleased(evt);
            }
        });
        panel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                if (isDraggable()) {
                    oggettoMouseDragged(evt);
                }
            }
        });
        
         oggetto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                oggettoMouseClicked(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                oggettoMousePressed(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                oggettoMouseReleased(evt);
            }
        });
        oggetto.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                if (isDraggable()) {
                    oggettoMouseDragged(evt);
                }
            }
        });
        
        
        oggetto.setVisible(true);
//        System.out.println("CREATOOOOOOOOOOOOOOOOOOOOOOOO: "+panel.getPreferredSize().width);
//        System.out.println("CREATOOOOOOOOOOOOOOOOOOOOOOOO: "+panel.getPreferredSize().height);
//        System.out.println("CREATOOOOOOOOOOOOOOOOOOOOOOOO");
//        System.out.println("CREATOOOOOOOOOOOOOOOOOOOOOOOO");
        
    }

    public void setDrawingArrowEnabled(boolean drawingArrowEnabled) {
        this.drawingArrowEnabled = drawingArrowEnabled;
    }

    public boolean isDrawingArrowEnabled() {
        return drawingArrowEnabled;
    }

    public void vanishBlinking() {
        if (blinker != null) {
            this.blinker.killAll();
        }

    }

    public void setRotate(boolean rotate) {
        this.oggetto.setRotate(rotate);
    }

    public EHouse getHome() {
        return home;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    /**
     * Constructor for Puppet Labels
     *
     * @param positionRevealer
     * @param home
     * @param location
     * @param x
     * @param y
     * @param icon
     */
//    public DraggablePanel(PositionRevealer positionRevealer, HomeEntity home, LocationEntity location, int x, int y, boolean male) {
//        this.positionRevealer = positionRevealer;
//        if (DVPISSettings.getInstance().isVerbose()) {
//            System.out.println("[dvpis.office] Parsing Puppet info");
//            System.out.println("[dvpiss.office - Room Location] creating label for room: " + location.getName());
//            System.out.println("LOCATION LABEL: " + location.getName() + " x: " + x + ", y: " + y);
//        }
//        this.location = location;
//        this.home = home;
////        ObjectId PIR = null;
//        String roomName = location.getName();
//        String[] split = roomName.split("\\.");
//        if (split.length == 2) {
//            roomName = split[1];
//        }
//        this.nome = roomName;
//
//        oggetto = new JJJPanel(this, true, location.getId());
//
//        oggetto.setBackground(null);
//        oggetto.setOpaque(false);
////        oggetto.setIcon(male ? IconRetriever.oldMan80 : IconRetriever.oldWoman80);
//        oggetto.setToolTipText(nome);
////        oggetto.setText("");
//        Font defaultFont = new Font("Helvetica", Font.BOLD, 14);
//        oggetto.setFont(defaultFont);
//        oggetto.setForeground(Color.BLACK);
//        FontMetrics fontMetrics = oggetto.getFontMetrics(oggetto.getFont());
//        int width = fontMetrics.stringWidth(I18nTranslator.getInstance().translate(roomName));
//        if (width <= 0 || width >= 200) {
//            width = 30;
//        }
//        int height = fontMetrics.getHeight();
//        oggetto.setBounds(x, y, 80, 80);
//        this.kx = (float) location.getX();
//        this.ky = (float) location.getY();
//        oggetto.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                oggettoMouseClicked(evt);
//            }
//
//            public void mousePressed(java.awt.event.MouseEvent evt) {
//                oggettoMousePressed(evt);
//            }
//
//            public void mouseReleased(java.awt.event.MouseEvent evt) {
//                oggettoMouseReleased(evt);
//            }
//        });
//        oggetto.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//            public void mouseDragged(java.awt.event.MouseEvent evt) {
//                if (isDraggable()) {
//                    oggettoMouseDragged(evt);
//                }
//            }
//        });
//
////        oggetto.setToolTipText(getNome());
//    }

    public float getKx() {
        return kx;
    }

    public float getKy() {
        return ky;
    }

    public ESensor getSensor() {
        return sensor;
    }

    public int getX() {
        return oggetto.getX();
    }

    public int getY() {
        return oggetto.getY();
    }

    public int getHeight() {
        return oggetto.getHeight();
    }

    public void setHeight(int height) {
        this.oggetto.setBounds(oggetto.getX(), oggetto.getY(), oggetto.getWidth(), height);
    }

    public final String getNome() {
        return nome;
    }

    public int getWidht() {
        return oggetto.getWidth();
    }

    public void setWidht(int widht) {
        this.oggetto.setBounds(oggetto.getX(), oggetto.getY(), widht, oggetto.getHeight());

    }

    public void setX(int x) {
        this.oggetto.setBounds(x, oggetto.getY(), oggetto.getWidth(), oggetto.getHeight());
    }

    public void setY(int y) {
        this.oggetto.setBounds(oggetto.getX(), y, oggetto.getWidth(), oggetto.getHeight());

    }

    public Color getColor() {
        return oggetto.getBackground();
    }

    public void setColor(Color c) {
        oggetto.setBackground(c);
    }

    public void setVisible(boolean v) {
        if (oggetto != null) {
//            System.out.println("oggetto setting visible: " + v);
            oggetto.setVisible(v);
        }

    }

    private void oggettoMousePressed(java.awt.event.MouseEvent evt) {
        if (!salvato) {
            dx = evt.getX();
            dy = evt.getY();
            salvato = true;
        }
    }

    protected void oggettoMouseDragged(java.awt.event.MouseEvent evt) {
        oggetto.setBounds(oggetto.getX() + evt.getX() - dx, oggetto.getY() + evt.getY() - dy, oggetto.getWidth(), oggetto.getHeight());
    }

    protected void oggettoMouseReleased(java.awt.event.MouseEvent evt) {
        salvato = false;
        
//        if (sensor == null && location != null) {
            this.kx = ((float) this.getOggetto().getBounds().x) / (float) positionRevealer.revealWidht();

            this.ky = ((float) this.getOggetto().getBounds().y) / (float) positionRevealer.revealHeight();

//            home.getLocation(location.getId()).setX(kx);
//            home.getLocation(location.getId()).setY(ky);

//        } else if (sensor != null && location == null) {
//            this.kx = ((float) this.getOggetto().getBounds().x) / (float) positionRevealer.revealWidht();
//
//            this.ky = ((float) this.getOggetto().getBounds().y) / (float) positionRevealer.revealHeight();
//
//            home.getLocation(sensor.getLocation()).setX(kx);
//            home.getLocation(sensor.getLocation()).setY(ky);
//        }

    }

    protected void oggettoMouseClicked(java.awt.event.MouseEvent evt) {

    }

    public JJJPanel getOggetto() {
        return oggetto;
    }

}
