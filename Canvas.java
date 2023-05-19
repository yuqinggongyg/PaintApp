import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {
    private final static int STROKE_SIZE = 6;

    // store paths created in the whiteboard
    private List<List<Point>> paths;

    // draw a line between points
    private List<Point> currentPath;

    // dot color
    private Color color;

    // location of dot
    private int x, y;

    private int canvasWidth, canvasHeight;

    private StringBuilder shape;

    private Image image;
    private Image scaledImage;

    public Canvas(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.WHITE));

        // init variables
        paths = new ArrayList<>(30);
        canvasWidth = width;
        canvasHeight = height;

        shape = new StringBuilder("Brush");

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // current mouse location
                x = e.getX();
                y = e.getY();

                // draw current location
                Graphics g = getGraphics();
                g.setColor(color);
                if (shape.toString().equals("Brush")){
                    g.fillRect(x, y, STROKE_SIZE, STROKE_SIZE);
                }else if (shape.toString().equals("Rectangle")){
                    g.fillRect(x, y, 200,140);
                }else if (shape.toString().equals("Square")){
                    g.fillRect(x, y, 90,90);
                }else if (shape.toString().equals("Circle")){
                    g.fillOval(x, y, 100,100);
                }else if(shape.toString().equals("Line")){
                    g.fillRect(x,y,500,3);
                }else if (shape.toString().equals("Oval")){
                    g.fillOval(x,y,200,100);
                }else if (shape.toString().equals("Arc")){
                    g.fillArc(x,y,150,150,90,90);
                }else if (shape.toString().equals("Round Rectangle")){
                    g.fillRoundRect(x,y,100,100,90,90);
                }else if(shape.toString().equals("3D Rectangle")){
                    g.fill3DRect(x,y,100,50,true);
                }
                g.dispose();

                currentPath = new ArrayList<>(30);
                currentPath.add(new Point(color, x, y));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // add current path to paths
                paths.add(currentPath);

                // clear the current path
                currentPath = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // get current location
                x = e.getX();
                y = e.getY();

                // able to draw a line
                // Graphics2D g2d = (Graphics2D) getGraphics();
                Graphics2D g2d = (Graphics2D) getGraphics();
                g2d.setColor(color);
                if (!currentPath.isEmpty()) {
                    Point prevPoint = currentPath.get(currentPath.size() - 1);
                    g2d.setStroke(new BasicStroke(STROKE_SIZE));

                    // connect the current point to the previous point to draw a line
                    g2d.drawLine(prevPoint.getX(), prevPoint.getY(), x, y);
                }
                g2d.dispose();

                // add the new point to the path
                Point nextPoint = new Point(color, e.getX(), e.getY());
                currentPath.add(nextPoint);
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setShape(String newShape){
        shape = new StringBuilder(newShape);
    }

    public void setBackgroundImg(String imgName) {
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(imgName));
            scaledImage = image.getScaledInstance(canvasWidth,canvasHeight,Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeBackgroundImg(){
        this.image = null;
        scaledImage = null;
    }

    public Image getScaledImage(){
        return this.scaledImage;
    }

    public void resetCanvas(){
        // clears all rectangles
        Graphics g = getGraphics();
        g.clearRect(0, 0, canvasWidth, canvasHeight);
        g.dispose();

        // clear paths
        paths = new ArrayList<>(30);
        currentPath = null;

        repaint();
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (scaledImage != null){
            g.drawImage(scaledImage, 0,0, null);
        }

        // redraws paths created so far
        for(List<Point> path : paths){
            Point from = null;
            for(Point point : path){
                g2d.setColor(point.getColor());
                if(from != null){
                    g2d.setStroke(new BasicStroke(STROKE_SIZE));
                    g2d.drawLine(from.getX(), from.getY(), point.getX(), point.getY());
                }
                from = point;
            }
        }
    }
}