import java.net.URL;
import java.util.Random;

public class Asteroid extends Sprite {

	private final int BOARD_WIDTH = 800;
    private final int BOARD_HEIGHT = 600;
    private final int ASTEROID_SPEED = 2;
    private double dx, dy;
    private int health = 2;

    public Asteroid() {
        super();

        initAsteroid();
    }

    private void initAsteroid() {

    	URL url = Asteroid.class.getResource("/resources/asteroid3.png");
        loadImage(url);
        getImageDimensions();
        
    	r = getRandomNumberInRange(0, 360);
    	
    	if (r == 0)
    		r = 360;
    	
    	if (r > 0 && r <= 90) {
    		x = getRandomNumberInRange(400, 800);
    		y = 500;
    	}
    	if (r > 90 && r < 180) {
    		x = getRandomNumberInRange(0, 600);
    		y = 500;
    	}
    	if (r >= 180 && r <= 270) {
    		x = getRandomNumberInRange(0, 600);
    		y = 0;
    	}
    	if (r >= 270 && r <= 360) {
    		x = getRandomNumberInRange(400, 800);
    		y = 0;
    	}
    }

    public void move() {

    	dx = ASTEROID_SPEED * -Math.cos(Math.toRadians(r));
    	dy = ASTEROID_SPEED * -Math.sin(Math.toRadians(r));
    	x += dx;
    	y += dy;
    	
        if (x > BOARD_WIDTH + 100 || x < - 50) {
            visible = false;
        }
        if (y > BOARD_HEIGHT + 100 || y < - 50) {
            visible = false;
        }
    }
    
    public int getHealth() {
    	return health;
    }
    
    public void setHealth(int health) {
    	this.health = health;
    }
    
    private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}