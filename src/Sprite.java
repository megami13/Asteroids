import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.net.URL;
import javax.swing.ImageIcon;

public class Sprite {

    protected int x;
    protected int y;
    protected int r;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    public Sprite() {
    	visible = true;
    }
    
    public Sprite(int x, int y) {

        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void loadImage(URL url) {

        ImageIcon ii = new ImageIcon(url);
        image = ii.getImage();
    }
    
    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }    

    public Image getImage() {
        return image;
    }

    public int getR() {
        
        return r;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getWidth() {
    	return width;
    }

    public int getHeight() {
    	return height;
    }
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public Ellipse2D getCircle() {
    	return new Ellipse2D.Double(x, y, width, height);
    }
    
    public Ellipse2D getCircleShip() {
    	return new Ellipse2D.Double(x, y, width - 5, height);
    }
}