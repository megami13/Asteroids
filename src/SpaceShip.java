import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class SpaceShip extends Sprite {

	private double dx;
    private double dy;
    private int dr;
    private List<Missile> missiles;
    boolean upPressed = false, downPressed = false, rightPressed = false, leftPressed = false; 
    double dec = 0;
    float deltaTime;
    int rotSpeed = 2;
    
    public SpaceShip(int x, int y) {
        super(x, y);
        
        initCraft();
    }
    
    private void initCraft() {

        missiles = new ArrayList<>();
        
        URL url = SpaceShip.class.getResource("/resources/trojkat.png");
        loadImage(url); 
        getImageDimensions();
        r = 90;
        
        Missile.setSpaceShip(this);
    }
    
    public void move() {
        
        x += dx;
        y += dy;
        r += dr;
        if (r > 360)
        	r = 0;
        else if (r < 0)
        	 r = 360;
        
        if (x > 800)
        	x = 0;
        else if (x < 0)
        	x = 800;
        
        if (y > 600)
        	y = 0;
        else if (y < 0)
        	y = 600;
    }
    
    public Image getImage() {
        
        return image;
    }
    
    public void setX(int x) {
    	this.x = x;
    }
    
    public void setY(int y) {
    	this.y = y;
    }
    
    public void setR(int r) {
    	this.r = r;
    }
    
    public void setDXDY(int dx, int dy) {
    	this.dx = dx;
    	this.dy = dy;
    }
    
    public void fire() {
        missiles.add(new Missile(x + width, y + height / 2));
    }
    
    public List<Missile> getMissiles() {
        return missiles;
    }
    
    public void resetMissiles() {
    	missiles.clear();
    }
    
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        double deltaTime = Board.getDeltaTime();
        deltaTime = deltaTime / 10;
        //System.out.println(r);
        
        if (key == KeyEvent.VK_SPACE) {
            fire();
        }
        
        if (key == KeyEvent.VK_LEFT) {
        	dr = -2 * rotSpeed;
        	leftPressed = true;
        }

        if (key == KeyEvent.VK_RIGHT) {
        	dr = 2 * rotSpeed;
        	rightPressed = true;
        }

        if (key == KeyEvent.VK_UP) {
        	dx = -2* Math.cos(Math.toRadians(r)) * deltaTime;
        	dy = -2* Math.sin(Math.toRadians(r)) * deltaTime;
        	dec = 1;
        	upPressed = true;
        }

        if (key == KeyEvent.VK_DOWN) {
        	dx = -dec* Math.cos(Math.toRadians(r)) * deltaTime;
        	dy = -dec* Math.sin(Math.toRadians(r)) * deltaTime;
        	if (dec <= 0)
        		dec = 0;
        	else
        		dec =- 0.1;
        	/*
        	float vec = (float) Math.sqrt(dx*dx + dy*dy);
            if (dec > 0 && vec > 0) {
            	dx = (dx/vec)* dec;
            	dy = (dy/vec)* dec;
            	dec =- 0.2;
            }*/
            downPressed = true;
        }
        
        if (key == KeyEvent.VK_LEFT && rightPressed == true) {
        	dr = 0;
        }
        
        if (key == KeyEvent.VK_RIGHT && leftPressed == true) {
        	dr = 0;
        }
        
        if (key == KeyEvent.VK_UP && downPressed == true) {
            dx = 0;
        	dr = 0;
        }
        
        if (key == KeyEvent.VK_DOWN && upPressed == true) {
        	dx = 0;
        	dr = 0;
        }
        
        if (key == KeyEvent.VK_UP && rightPressed == true) {
        	dr = 2 * rotSpeed;
        	dx = -2* Math.cos(Math.toRadians(r)) * deltaTime;
        	dy = -2 * Math.sin(Math.toRadians(r)) * deltaTime;
        }
        
        if (key == KeyEvent.VK_UP && leftPressed == true) {
        	dr = -2 * rotSpeed;
        	dx = -2 * Math.cos(Math.toRadians(r)) * deltaTime;
        	dy = -2 * Math.sin(Math.toRadians(r)) * deltaTime;
        }
        
        if (key == KeyEvent.VK_DOWN && rightPressed == true) {
        }
        
        if (key == KeyEvent.VK_DOWN && leftPressed == true) {
        }
        
        if (key == KeyEvent.VK_RIGHT && upPressed == true) {
        	dr = 2 * rotSpeed;
        	dx = -2* Math.cos(Math.toRadians(r)) * deltaTime;
        	dy = -2* Math.sin(Math.toRadians(r)) * deltaTime;
        }
        
        if (key == KeyEvent.VK_LEFT && upPressed == true) {
        	dr = -2 * rotSpeed;
        	dx = -2* Math.cos(Math.toRadians(r)) * deltaTime;
        	dy = -2* Math.sin(Math.toRadians(r)) * deltaTime;
        }
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        	dr = 0;
        	leftPressed = false;
        }

        if (key == KeyEvent.VK_RIGHT) {
        	dr = 0;
        	rightPressed = false;
        }

        if (key == KeyEvent.VK_UP) {
            upPressed = false;
            
            float vec = (float) Math.sqrt(dx*dx + dy*dy);
            if (vec > 0) {
            	dx = (dx/vec) * 2;
            	dy = (dy/vec) * 2;
            	dec = ((dx + dy)/vec) /2;
            }
        }

        if (key == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
    }
}
