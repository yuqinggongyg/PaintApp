import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.awt.Font.*;

public class GUI extends JFrame {
    private StringBuilder choosingImage;
    public GUI(StringBuilder choosingImage){
        super("Welcome to draw!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));
        pack();
        setLocationRelativeTo(null);
        this.choosingImage = choosingImage;
        addGroupComponents();
    }

    public void addGroupComponents(){
        // Set up JPanel
        JPanel canvasPanel = new JPanel();
        canvasPanel.setBackground(new Color(236,180,191));

        SpringLayout springLayout = new SpringLayout();
        canvasPanel.setLayout(springLayout);

        // Canvas
        Canvas canvas = new Canvas(900, 800);
        canvasPanel.add(canvas);
        if (choosingImage != null){
            canvas.setBackgroundImg(choosingImage.toString());
            repaint();
            revalidate();
        }
        springLayout.putConstraint(SpringLayout.SOUTH, canvas, 0, SpringLayout.SOUTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.EAST, canvas,0, SpringLayout.EAST,canvasPanel);

        //  Paint session label
        JLabel draw = new JLabel("Paint Canvas");
        draw.setFont(new Font(draw.getText(), ITALIC,25));
        draw.setForeground(Color.blue);
        canvasPanel.add(draw);
        springLayout.putConstraint(SpringLayout.NORTH, draw, 55, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, draw, 60, SpringLayout.WEST, canvasPanel);

        // Color choose for whiteboard background
        JButton chooseBackgroundButton = new JButton("Select a background color");
        chooseBackgroundButton.setFont(new Font(chooseBackgroundButton.getText().toString(), PLAIN,16));
        chooseBackgroundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Select a background color", Color.WHITE);
                chooseBackgroundButton.setBackground(c);
                if (c == Color.BLACK){
                    chooseBackgroundButton.setForeground(Color.WHITE);
                }else{
                    chooseBackgroundButton.setForeground(Color.BLACK);
                }
                chooseBackgroundButton.setOpaque(true);
                chooseBackgroundButton.setBorderPainted(false);
                canvas.setBackground(c);
            }
        });
        canvasPanel.add(chooseBackgroundButton);
        springLayout.putConstraint(SpringLayout.NORTH, chooseBackgroundButton, 90, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, chooseBackgroundButton, 20, SpringLayout.WEST, canvasPanel);

        // Color chooser for brush
        JButton chooseColorButton = new JButton("Select a color for brush");
        chooseColorButton.setFont(new Font(chooseColorButton.getText().toString(), PLAIN,16));
        chooseColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Select a color for brush", Color.BLACK);
                chooseColorButton.setBackground(c);
                if (c == Color.BLACK){
                    chooseColorButton.setForeground(Color.WHITE);
                }else {
                    chooseColorButton.setForeground(Color.BLACK);
                }
                chooseColorButton.setOpaque(true);
                chooseColorButton.setBorderPainted(false);
                canvas.setColor(c);
            }
        });
        canvasPanel.add(chooseColorButton);
        springLayout.putConstraint(SpringLayout.NORTH, chooseColorButton, 120, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, chooseColorButton, 30, SpringLayout.WEST, canvasPanel);

        // ShapeButton
        String[] shapes = {"Brush", "Square", "Rectangle", "Circle","Oval", "Line","Arc", "Round Rectangle", "3D Rectangle"};
        JComboBox<String> menu = new JComboBox<String>(shapes);
        menu.setFont(new Font(menu.getSelectedItem().toString(), PLAIN, 16));
        menu.setSelectedItem("Brush");

        // change bg colors between two buttons
        JButton eraserButton = new JButton("Eraser");
        eraserButton.setFont(new Font(eraserButton.getText().toString(), PLAIN,16));

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setFont(new Font(menu.getSelectedItem().toString(), ITALIC,20));
                canvas.setShape(menu.getSelectedItem().toString());
                canvas.setColor(chooseColorButton.getBackground());
                eraserButton.setBackground(null);
                eraserButton.setOpaque(false);
            }
        });
        canvasPanel.add(menu);
        springLayout.putConstraint(SpringLayout.NORTH, menu,150, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, menu,48, SpringLayout.WEST, canvasPanel);

        // Eraser
        eraserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = canvas.getBackground();
                canvas.setColor(c);
                eraserButton.setBackground(Color.BLACK);
                eraserButton.setOpaque(true);
                menu.setFont(new Font(menu.getSelectedItem().toString(),Font.PLAIN, 16));
            }
        });
        canvasPanel.add(eraserButton);
        springLayout.putConstraint(SpringLayout.NORTH, eraserButton,180, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, eraserButton,95, SpringLayout.WEST, canvasPanel);

        // Reset whiteboard
        JButton resetButton = new JButton("Reset your canvas!");
        resetButton.setFont(new Font(resetButton.getText().toString(), PLAIN,16));
        resetButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.resetCanvas();

                chooseBackgroundButton.setBackground(Color.WHITE);
                chooseBackgroundButton.setForeground(Color.BLACK);
                chooseBackgroundButton.setOpaque(false);
                chooseBackgroundButton.setBorderPainted(true);
                canvas.setBackground(Color.WHITE);

                chooseColorButton.setBackground(Color.BLACK);
                chooseColorButton.setForeground(Color.BLACK);
                chooseColorButton.setOpaque(false);
                chooseColorButton.setBorderPainted(true);
                canvas.setColor(Color.black);

                menu.setSelectedItem("Brush");

                eraserButton.setBackground(null);
                eraserButton.setOpaque(false);
            }
        });
        canvasPanel.add(resetButton);
        springLayout.putConstraint(SpringLayout.NORTH, resetButton, 210, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, resetButton, 45, SpringLayout.WEST, canvasPanel);

        // Background image session label
        JLabel backgroundImg = new JLabel("Background Image");
        backgroundImg.setFont(new Font(backgroundImg.getText(), ITALIC,25));
        backgroundImg.setForeground(Color.blue);
        canvasPanel.add(backgroundImg);
        springLayout.putConstraint(SpringLayout.NORTH, backgroundImg, 290, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, backgroundImg, 30, SpringLayout.WEST, canvasPanel);

        JLabel imageName = new JLabel("Enter image file name");
        imageName.setFont(new Font(imageName.getText(), Font.PLAIN, 13));
        canvasPanel.add(imageName);
        springLayout.putConstraint(SpringLayout.NORTH, imageName, 340, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, imageName, 30, SpringLayout.WEST, canvasPanel);

        JTextField imgName = new JTextField(7);
        canvasPanel.add(imgName);
        if (choosingImage != null) imgName.setText(choosingImage.toString());
        springLayout.putConstraint(SpringLayout.NORTH, imgName, 335, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, imgName, 170, SpringLayout.WEST, canvasPanel);

        JButton add = new JButton("Add Image");
        ButtonGroup buttonGroup = new ButtonGroup();
        add.setFont(new Font(add.getText().toString(), PLAIN,16));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setBackgroundImg(imgName.getText());
                choosingImage = new StringBuilder(imgName.getText());
                if (!imgName.getText().equals("template1.png")&&
                        !imgName.getText().equals("template1.png")&&
                        !imgName.getText().equals("template1.png")&&
                        !imgName.getText().equals("template1.png")){
                    buttonGroup.clearSelection();
                }
                repaint();
                revalidate();
            }
        });
        canvasPanel.add(add);
        springLayout.putConstraint(SpringLayout.NORTH, add, 360, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, add, 80, SpringLayout.WEST, canvasPanel);

        // Template for background

        JLabel template = new JLabel("Template");
        template.setFont(new Font(template.getText(), Font.PLAIN, 13));
        canvasPanel.add(template);
        springLayout.putConstraint(SpringLayout.NORTH, template, 418, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, template, 55, SpringLayout.WEST, canvasPanel);

        JRadioButton button1 = new JRadioButton();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgName.setText("template1.png");
                canvas.setBackgroundImg("template1.png");
                repaint();
                revalidate();
            }
        });
        JRadioButton button2 = new JRadioButton();
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgName.setText("template2.png");
                canvas.setBackgroundImg("template2.png");
                repaint();
                revalidate();
            }
        });
        JRadioButton button3 = new JRadioButton();
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgName.setText("template3.png");
                canvas.setBackgroundImg("template3.png");
                repaint();
                revalidate();
            }
        });
        JRadioButton button4 = new JRadioButton();
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgName.setText("template4.png");
                canvas.setBackgroundImg("template4.png");
                repaint();
                revalidate();
            }
        });
        buttonGroup.add(button1);
        buttonGroup.add(button2);
        buttonGroup.add(button3);
        buttonGroup.add(button4);
        canvasPanel.add(button1);
        canvasPanel.add(button2);
        canvasPanel.add(button3);
        canvasPanel.add(button4);
        springLayout.putConstraint(SpringLayout.NORTH, button1, 415, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, button1, 115, SpringLayout.WEST, canvasPanel);
        springLayout.putConstraint(SpringLayout.NORTH, button2, 415, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, button2, 145, SpringLayout.WEST, canvasPanel);
        springLayout.putConstraint(SpringLayout.NORTH, button3, 415, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, button3, 175, SpringLayout.WEST, canvasPanel);
        springLayout.putConstraint(SpringLayout.NORTH, button4, 415, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, button4, 205, SpringLayout.WEST, canvasPanel);

        // remove background Image
        JButton remove  = new JButton("Remove Image");
        remove.setFont(new Font(remove.getText().toString(), PLAIN,16));
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choosingImage!= null){
                    choosingImage = null;
                }
                canvas.removeBackgroundImg();
                repaint();
                revalidate();
                imgName.setText("");
                buttonGroup.clearSelection();
            }
        });
        canvasPanel.add(remove);
        springLayout.putConstraint(SpringLayout.NORTH, remove, 385, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, remove, 65, SpringLayout.WEST, canvasPanel);


        // Write to new file label and button
        JLabel newFileName = new JLabel("Save Your Canvas");
        newFileName .setFont(new Font(draw.getText(), ITALIC,25));
        newFileName.setForeground(Color.blue);
        canvasPanel.add(newFileName);
        springLayout.putConstraint(SpringLayout.NORTH, newFileName, 480, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, newFileName, 36, SpringLayout.WEST, canvasPanel);

        JLabel newImageName = new JLabel("Enter new file name");
        newImageName.setFont(new Font(newImageName.getText(), Font.PLAIN, 13));
        canvasPanel.add(newImageName);
        springLayout.putConstraint(SpringLayout.NORTH, newImageName, 513, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, newImageName, 30, SpringLayout.WEST, canvasPanel);

        JTextField writeInputName = new JTextField(7);
        canvasPanel.add(writeInputName);
        springLayout.putConstraint(SpringLayout.NORTH, writeInputName, 508, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, writeInputName, 160, SpringLayout.WEST, canvasPanel);

        JButton saveFileButton = new JButton("Save");
        saveFileButton.setFont(new Font(saveFileButton.getText().toString(),PLAIN,16));
        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Robot robot = new Robot();
                    Frame[] frames = Frame.getFrames();
                    for (Frame frame: frames){
                        if (frame instanceof  JFrame){
                            int x = frame.getLocationOnScreen().x;
                            int y = frame.getLocationOnScreen().y;
                            Rectangle panelBounds = new Rectangle(x+300, y+28,900,800-28);
                            BufferedImage screenshot = robot.createScreenCapture(panelBounds);
                            ImageIO.write(screenshot, "png", new File(writeInputName.getText()));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        canvasPanel.add(saveFileButton);
        springLayout.putConstraint(SpringLayout.NORTH, saveFileButton, 530, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, saveFileButton, 100, SpringLayout.WEST, canvasPanel);

        //JLabel logo
        ImageIcon icon = new ImageIcon("logo.png");
        Image logo = icon.getImage();
        Image scaledlogo = logo.getScaledInstance(150,150,Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledlogo);
        JLabel imgLabel = new JLabel(scaledIcon);
        canvasPanel.add(imgLabel);
        springLayout.putConstraint(SpringLayout.NORTH, imgLabel, 580, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, imgLabel, 70, SpringLayout.WEST, canvasPanel);

        this.getContentPane().add(canvasPanel);
    }
}
