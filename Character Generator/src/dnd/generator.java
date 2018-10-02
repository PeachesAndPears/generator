package dnd;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class generator {
    private JPanel bigPanel;
    private JFrame theWindow;
    private JButton paintIt, eraseIt, editIt;

    private JPanel buttonPanel;

    public generator() {
        theWindow = new JFrame("Pearse's Character Generator");
        theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paintIt = new JButton("Button");
        buttonPanel = new JPanel();
        buttonPanel.add(paintIt, new GridBagLayout());
        bigPanel = new JPanel();
        bigPanel.setPreferredSize(new Dimension(800, 500));
        bigPanel.add(buttonPanel);

        ImagePanel bugbear = new ImagePanel();
        ImagePanel goblin = new ImagePanel("https://i.imgur.com/AJ6SWi9.jpg");
        bigPanel.add(bugbear);
        bigPanel.add(goblin);
        theWindow.add(bigPanel);

        theWindow.pack();
        theWindow.setVisible(true);

        GridBagConstraints c = new GridBagConstraints();
    }

    public static void main(String[] args) {
        new generator();
    }

    public class ImagePanel extends JPanel {

        private BufferedImage image;


        public ImagePanel() {
            setPreferredSize(new Dimension(50,80));
            try {
                image = ImageIO.read(new File("C:\\Users\\pears\\Pictures\\kev and p.png"));//new URL("https://drive.google.com/file/d/0B1SUmVBG0D0mclV4Q0xwNlpJS3c/view"));
            } catch (IOException ex) {
                // handle exception...
            }
        }

        public ImagePanel(String url) {
            setPreferredSize(new Dimension(50,80));
            try {
                image = ImageIO.read(new URL(url));
            } catch (IOException ex) {
                // handle exception...
            }
        }
        @Override
        public void paint(Graphics g){
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
        }

    }
}
