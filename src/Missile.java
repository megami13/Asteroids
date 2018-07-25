import java.net.URL;

public class Missile extends Sprite {

    private final int BOARD_WIDTH = 800;
    private final int BOARD_HEIGHT = 600;
    private final int MISSILE_SPEED = 5;
    private static SpaceShip spaceShip;
    double dx, dy;

    public Missile(int x, int y) {
        super(x, y);
        
        initMissile();
        r = spaceShip.getR();
    }
    
    public int getR() {
        
        return r;
    }
    
    public static void setSpaceShip(SpaceShip spaceShip1){
    	spaceShip = spaceShip1;
    }

    private void initMissile() {
    	
    	URL url = Missile.class.getResource("/resources/missile.png");
        loadImage(url);  
        getImageDimensions();
    }

    public void move() {
        
        //x += MISSILE_SPEED;
    	dx = MISSILE_SPEED* -Math.cos(Math.toRadians(r));
    	dy = MISSILE_SPEED* -Math.sin(Math.toRadians(r));
    	x += dx;
    	y += dy;
        
        if (x > BOARD_WIDTH || x < 0) {
            visible = false;
        }
        if (y > BOARD_HEIGHT || y < 0) {
            visible = false;
        }
    }
}
