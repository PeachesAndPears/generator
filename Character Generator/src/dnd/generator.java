package dnd;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class generator {
    double X1;
    double Y1;
    double newSize;
    int popLoc;
    boolean move = true;
    int dragLocation;
    private JPanel bigPanel;
    private JFrame theWindow;
    private JButton paintIt, eraseIt, editIt;
    public DrawPanel alignmentPointPanel;
    public MCircle point;
    public String currFile;
    private JPanel buttonPanel;

    public generator() throws IOException {
        theWindow = new JFrame("Pearse's Character Generator");
        theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bigPanel = new JPanel();
        bigPanel.setPreferredSize(new Dimension(800, 500));
        ImagePanel bugbear = new ImagePanel();
        ImagePanel goblin = new ImagePanel("https://i.imgur.com/AJ6SWi9.jpg");
        bigPanel.add(bugbear);
        bigPanel.add(goblin);
        theWindow.setLocationRelativeTo(null);
        JTextField field = new JTextField(10);
        AutoSuggestor autoSuggestor = new AutoSuggestor(field, theWindow, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
            @Override
            boolean wordTyped(String typedWord) {
                //create list for dictionary this in your case might be done via calling a method which queries db and returns results as arraylist
                ArrayList<String> words = new ArrayList<>();
                words.add("hello");
                words.add("heritage");
                words.add("happiness");
                words.add("goodbye");
                words.add("cruel");
                words.add("car");
                words.add("war");
                words.add("will");
                words.add("world");
                words.add("wall");
                setDictionary(words);
                //addToDictionary("bye");//adds a single word
                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
            }
        };
        alignmentPointPanel = new DrawPanel(600, 600);
        bigPanel.add(alignmentPointPanel);
        bigPanel.add(field);
        theWindow.add(bigPanel);
        theWindow.pack();
        theWindow.setVisible(true);
        GridBagConstraints c = new GridBagConstraints();
    }

    public static void main(String[] args) throws IOException {
        new generator();
    }

    class DrawPanel extends ImagePanel {
        private int prefwid, prefht;
        // Initialize the DrawPanel by creating a new ArrayList for the images
        // and creating a MouseListener to respond to clicks in the panel.

        public DrawPanel(int wid, int ht) throws IOException{
            prefwid = wid;
            prefht = ht;
            point = new MCircle(80, 60, 60, Color.BLACK);
            addMouseListener(new MListen());
            addMouseMotionListener(new MListen());
        }

        public Dimension getPreferredSize() {
            return new Dimension(prefwid, prefht);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            point.draw(g2d);
        }

        public int select(double x, double y) {
            if (point.contains(x, y)) {
                return 1;
            }
            return -1;
        }
    }

    public class ImagePanel extends JPanel {
        private BufferedImage image;

        public ImagePanel() throws IOException {
            setPreferredSize(new Dimension(50, 80));
            image = ImageIO.read(new File("C:\\Users\\pears\\Pictures\\kev and p.png"));//new URL("https://drive.google.com/file/d/0B1SUmVBG0D0mclV4Q0xwNlpJS3c/view"));

        }

        public ImagePanel(String url) {
            setPreferredSize(new Dimension(50, 80));
            try {
                image = ImageIO.read(new URL(url));
            } catch (IOException ex) {
                // handle exception...
            }
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
        }

    }

    class MListen extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            X1 = e.getX();
            Y1 = e.getY();
            alignmentPointPanel.repaint();
        }

        public void mouseReleased(MouseEvent e) {
            dragLocation = -1;
        }

        public void mouseDragged(MouseEvent e) {
            popLoc = 0;
            double distance = (Math.pow(Math.abs(X1 - e.getX()), 2.0) + Math.pow(Math.abs(Y1 - e.getY()), 2.0));
            if (move) {
                move = !move;
                X1 = e.getX();
                Y1 = e.getY();
            } else {
                move = !move;
            }
            if (dragLocation < 0) {
                dragLocation = alignmentPointPanel.select(e.getX(), e.getY());
            } else if (dragLocation > -1) {
                point.move((e.getX() - X1) * 2, (e.getY() - Y1) * 2);//e.getX(), e.getY());
            }
            alignmentPointPanel.repaint();
        }
    }

    class MCircle {
        RectangularShape sh;
        Color col;

        public MCircle() {
            sh = new Ellipse2D.Double(0, 0, 0, 0);
        }

        public boolean contains(double x, double y) {
            return sh.contains(new Point2D.Double(x, y));
        }

        public MCircle(double dia, double x, double y, Color c) {
            super();
            // See Ellipse2D.Double in the Java API.  This object for the
            // RectangularShape in the Mosaic is the only significant difference
            // between the MCircle and MSquare classes.
            sh = new Ellipse2D.Double(x - dia / 2, y - dia / 2, dia, dia);
            col = c;
        }

        public void draw(Graphics2D g) {
            g.setColor(col);
            g.fill(sh);
        }

        public void move(double x, double y) {
            double oldX = sh.getCenterX();
            double oldY = sh.getCenterY();
            double newX = oldX + x;
            double newY = oldY + y;
            sh.setFrameFromCenter(newX, newY, newX + sh.getWidth() / 2,
                    newY + sh.getWidth() / 2);
        }

        public String saveFile() {
            StringBuilder b = new StringBuilder();
            b.append("Circle" + ",");
            b.append(sh.getWidth() + ",");
            b.append(sh.getCenterX() + "," + sh.getCenterY() + ",");
            b.append(col.getRed() + ":" + col.getGreen() + ":" + col.getBlue());
            return b.toString();
        }
    }
}



