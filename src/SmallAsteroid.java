import java.util.Random;
import java.net.URL;

public class SmallAsteroid extends Sprite {

	private final int BOARD_WIDTH = 800;
    private final int BOARD_HEIGHT = 600;
    private final int INITIAL_X = 800;
    private final int ASTEROID_SPEED = 2;
    private double dx, dy;

    public SmallAsteroid(int x, int y) {
    	super(x, y);

        initAsteroid();
    }

    private void initAsteroid() {

    	URL url = SmallAsteroid.class.getResource("/resources/asteroidSmall.png");
        loadImage(url);
        getImageDimensions();
        
    	r = getRandomNumberInRange(0, 360);

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
    
    private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}