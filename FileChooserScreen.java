import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static java.awt.Font.ITALIC;
import static java.awt.Font.PLAIN;

public class FileChooserScreen extends JFrame {
    public  FileChooserScreen() {
        super("Welcome to draw!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800,600));
        pack();
        setLocationRelativeTo(null);
        addGroupComponents();
    }

    public void addGroupComponents(){
        JPanel homePanel = new JPanel();
        homePanel.setBackground(Color.PINK);

        SpringLayout springLayout = new SpringLayout();
        homePanel.setLayout(springLayout);

        //  App name label
        JLabel appName = new JLabel("Paint App");
        appName.setFont(new Font(appName.getText(), ITALIC,40));
        appName.setForeground(Color.blue);
        homePanel.add(appName);
        springLayout.putConstraint(SpringLayout.NORTH, appName, 110, SpringLayout.NORTH, homePanel);
        springLayout.putConstraint(SpringLayout.WEST, appName, 320, SpringLayout.WEST, homePanel);

        //  introduction
        JTextArea intro = new JTextArea();
        intro.append("Explore your creativity with my Java Swing GUI painting app! Draw different"+"\n");
        intro.append("shapes and colors on a customizable canvas, and choose from a variety of"+"\n");
        intro.append("    background colors and images to make your artwork truly unique!"+"\n");
        intro.setEditable(false);
        intro.setOpaque(false);
        intro.setFont(new Font(intro.getText(), ITALIC,15));
        intro.setForeground(Color.BLACK);
        homePanel.add(intro);
        springLayout.putConstraint(SpringLayout.NORTH, intro, 200, SpringLayout.NORTH, homePanel);
        springLayout.putConstraint(SpringLayout.WEST, intro, 100, SpringLayout.WEST, homePanel);

        // Choose an existing file
        JButton choosingImageButton = new JButton("Select your previous drawing");
        choosingImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    File selectedFile = jFileChooser.getSelectedFile();
                    GUI gui = new GUI(new StringBuilder(selectedFile.getName()));
                    // gui.choosingImage = new StringBuilder(selectedFile.getName());
                    gui.setVisible(true);
                }
            }
        });
        homePanel.add(choosingImageButton);
        springLayout.putConstraint(SpringLayout.WIDTH, choosingImageButton,Spring.constant(200), SpringLayout.NORTH, homePanel);
        springLayout.putConstraint(SpringLayout.HEIGHT, choosingImageButton,Spring.constant(50), SpringLayout.NORTH, homePanel);
        SpringLayout.Constraints button1Constraints = springLayout.getConstraints(choosingImageButton);
        button1Constraints.setX(Spring.constant(300));
        button1Constraints.setY(Spring.constant(300));

        // Create a new canvas
        JButton createCanvasButton = new JButton("Create a new canvas!");
        createCanvasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI gui = new GUI(null);
                gui.setVisible(true);
            }
        });
        homePanel.add(createCanvasButton);
        springLayout.putConstraint(SpringLayout.WIDTH, createCanvasButton,Spring.constant(200), SpringLayout.NORTH, homePanel);
        springLayout.putConstraint(SpringLayout.HEIGHT, createCanvasButton,Spring.constant(50), SpringLayout.NORTH, homePanel);
        SpringLayout.Constraints button2Constraints = springLayout.getConstraints(createCanvasButton);
        button2Constraints.setX(Spring.constant(300));
        button2Constraints.setY(Spring.constant(350));

        //JLabel logo
        ImageIcon bgIC = new ImageIcon("bg.png");
        Image bg = bgIC.getImage();
        Image scaledbg = bg.getScaledInstance(800,600,Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledbg);
        JLabel imgLabel = new JLabel(scaledIcon);
        homePanel.add(imgLabel);
        springLayout.putConstraint(SpringLayout.NORTH, imgLabel, 0, SpringLayout.NORTH, homePanel);
        springLayout.putConstraint(SpringLayout.WEST, imgLabel, 0, SpringLayout.WEST, homePanel);

        this.getContentPane().add(homePanel);
    }
}