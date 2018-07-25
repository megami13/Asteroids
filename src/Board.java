import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	private final int ICRAFT_X = 350;
    private final int ICRAFT_Y = 250;
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DELAY = 15;
    private List<Asteroid> asteroids;
    private List<SmallAsteroid> smallAsteroids;
    private boolean ingame, startMenu;
    private Timer timer;
    private SpaceShip spaceShip;
    long last_time = System.nanoTime();
    static int delta_time;
    int asteroidsDestroyed = 0;
    JButton restart, easyB, mediumB, hardB;
    private static final String FILENAME = "E:\\Uczelnia\\Java\\testFile.txt";
    private String[] score = new String[3];
    int score_i = 0;
    private int[] int_score = new int[3];
    int difficulty;

	 public Board() {

	        //initBoard();
	        startMenu = true;
	        timer = new Timer(DELAY, this);
	        timer.start();
	        
	        easyB = new JButton("Easy");
	        easyB.addActionListener(this);
	        //easyB.setBounds(335, 320, 110, 20);
	        //easyB.setBackground(Color.BLACK);
	        //easyB.setForeground(Color.WHITE);
	        //add(easyB);
	        
	        mediumB = new JButton("Medium");
	        mediumB.addActionListener(this);
	        
	        hardB = new JButton("Hard");
	        hardB.addActionListener(this);
	    }

	 private void initBoard() {

	        readFile();
	        addKeyListener(new TAdapter());
	        setFocusable(true);
	        setBackground(Color.BLACK);
	        setDoubleBuffered(true);
	        ingame = true;

	        spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);
	        
	        initAsteroids();
	        initSmallAsteroids();

	        timer = new Timer(DELAY, this);
	        timer.start();
	    }

	 public void initAsteroids() {
	        
	        asteroids = new ArrayList<>();
	    }
	 
	 public void initSmallAsteroids() {
	        
	        smallAsteroids = new ArrayList<>();
	 }
	 
	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        
	        if (startMenu) {
	        	drawSetDifficulty(g);
	        }
	        else if (ingame) {
	        	drawObjects(g);
	        }
	        else {
	        	drawGameOver(g);
	        }
	        
	        Toolkit.getDefaultToolkit().sync();
	    }
	    
	    private void drawObjects(Graphics g) {
	        
	        Graphics2D g2d = (Graphics2D) g;
	        
	        if (spaceShip.isVisible()) {
	        	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                RenderingHints.VALUE_ANTIALIAS_ON);
		          AffineTransform old = g2d.getTransform();
		          
		        BufferedImage image = toBufferedImage(spaceShip.getImage());
		        double rotationRequired = Math.toRadians (spaceShip.getR());
		        double locationX = spaceShip.getWidth() / 2;
		        double locationY = spaceShip.getHeight() / 2;
		        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		        	        
		        g2d.drawImage(op.filter(image, null), spaceShip.getX(), spaceShip.getY(), null);
		        
		          //in case you have other things to rotate
		          g2d.setTransform(old);
		          
	        }
	          List<Missile> missiles = spaceShip.getMissiles();

	          for (Missile missile : missiles) {
	              
	        	BufferedImage imageM = toBufferedImage(missile.getImage());
	  	        double rotationRequiredM= Math.toRadians (missile.getR());
	  	        double locationXM = missile.getWidth() / 2;
	  	        double locationYM = missile.getHeight() / 2;
	  	        AffineTransform txM = AffineTransform.getRotateInstance(rotationRequiredM, locationXM, locationYM);
	  	        AffineTransformOp opM = new AffineTransformOp(txM, AffineTransformOp.TYPE_BILINEAR);
	  	        	        
	  	        g2d.drawImage(opM.filter(imageM, null), missile.getX(), missile.getY(), null);
	          }
	          
	          for (Asteroid asteroid : asteroids) {
	        	  
	        	  if (asteroid.isVisible()) {
	        		  BufferedImage imageA = toBufferedImage(asteroid.getImage());
			  	        double rotationRequiredA= Math.toRadians (asteroid.getR());
			  	        double locationXA = asteroid.getWidth() / 2;
			  	        double locationYA = asteroid.getHeight() / 2;
			  	        AffineTransform txM = AffineTransform.getRotateInstance(rotationRequiredA, locationXA, locationYA);
			  	        AffineTransformOp opM = new AffineTransformOp(txM, AffineTransformOp.TYPE_BILINEAR);
			  	        	        
			  	        g2d.drawImage(opM.filter(imageA, null), asteroid.getX(), asteroid.getY(), null);
	        	  }
	          }
	          
	          for (SmallAsteroid smallAsteroid : smallAsteroids) {
	        	  
	        	  if (smallAsteroid.isVisible()) {
	        		  BufferedImage imageA = toBufferedImage(smallAsteroid.getImage());
			  	        double rotationRequiredA= Math.toRadians (smallAsteroid.getR());
			  	        double locationXA = smallAsteroid.getWidth() / 2;
			  	        double locationYA = smallAsteroid.getHeight() / 2;
			  	        AffineTransform txM = AffineTransform.getRotateInstance(rotationRequiredA, locationXA, locationYA);
			  	        AffineTransformOp opM = new AffineTransformOp(txM, AffineTransformOp.TYPE_BILINEAR);
			  	        	        
			  	        g2d.drawImage(opM.filter(imageA, null), smallAsteroid.getX(), smallAsteroid.getY(), null);
	        	  }
	          }
			  

	          g.setColor(Color.WHITE);
	          g.drawString("Asteroids destroyed : " + asteroidsDestroyed, 5, 15);
	        
	    }
	    
	    private void drawGameOver(Graphics g) {

	    	int yourScore = asteroidsDestroyed;;
	    	int temp;
	    	for (int i = 0; i < 3; i++) {
	    		if (asteroidsDestroyed > int_score[i]) {
	    			temp = int_score[i];
	    			int_score[i] = asteroidsDestroyed;
	    			asteroidsDestroyed = temp;
	    		}
	    	}

	    	try (PrintWriter out = new PrintWriter("E:\\Uczelnia\\Java\\testFile.txt")) {
	    		out.println(Integer.toString(int_score[0]));
	    	    out.println(Integer.toString(int_score[1]));
	    	    out.println(Integer.toString(int_score[2]));
	    	} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	readFile();
	    	
	    	
	        String msg = "Game Over";
	        Font small = new Font("Helvetica", Font.BOLD, 14);
	        FontMetrics fm = getFontMetrics(small);
	        
	        Font small1 = new Font("Helvetica", Font.PLAIN, 12);

	        g.setColor(Color.white);
	        g.setFont(small);
	        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2 - 10,
	                B_HEIGHT / 2 - 150);
	        
	        String msgHighScore = "Highscore";
	        
	        g.drawString(msgHighScore, (B_WIDTH - fm.stringWidth(msgHighScore)) / 2 - 10,
	                B_HEIGHT / 2 - 80);
	        
	        String score1 = "1st: " + Integer.toString(int_score[0]);
	        String score2 = "2nd: " + Integer.toString(int_score[1]);
	        String score3 = "3rd: " + Integer.toString(int_score[2]);
	        String yourScore_s = "Asteroids destroyed: " + Integer.toString(yourScore);
	        
	        g.drawString(score1, (B_WIDTH - fm.stringWidth(score1)) / 2 - 10,
	                B_HEIGHT / 2 - 40);
	        g.drawString(score2, (B_WIDTH - fm.stringWidth(score2)) / 2 - 10,
	                B_HEIGHT / 2 - 20);
	        g.drawString(score3, (B_WIDTH - fm.stringWidth(score3)) / 2 - 10,
	                B_HEIGHT / 2 - 0);
	        
	        g.setFont(small1);
	        g.drawString(yourScore_s, (B_WIDTH - fm.stringWidth(yourScore_s)) / 2 + 10,
	                B_HEIGHT / 2 - 120);
	        
	        restart = new JButton("Play again");
	        restart.addActionListener(this);
	        restart.setBounds(340, 350, 110, 20);
	        restart.setBackground(Color.BLACK);
	        restart.setForeground(Color.WHITE);
	        add(restart);
	    }
	    
	    private void drawSetDifficulty(Graphics g) {

	    	readFile();
	    	
	    	//addKeyListener(new TAdapter());
	        //setFocusable(true);
	        setBackground(Color.BLACK);
	        //setDoubleBuffered(true);
	    	
	        String asteroidsString = "A S T E R O I D S";
	        String msg = "Choose difficulty";
	        Font small = new Font("Helvetica", Font.BOLD, 14);
	        Font large = new Font("Helvetica", Font.BOLD, 30);
	        FontMetrics fm = getFontMetrics(small);
	        FontMetrics fm1 = getFontMetrics(large);

	        g.setColor(Color.white);
	        g.setFont(small);
	        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2 - 10,
	                B_HEIGHT / 2 + 10);
	        
	        g.setFont(large);
	        g.drawString(asteroidsString, (B_WIDTH - fm1.stringWidth(asteroidsString)) / 2 - 10,
	                B_HEIGHT / 2 - 150);
	        
	        String msgHighScore = "Highscore";
	        
	        g.setFont(small);
	        g.drawString(msgHighScore, (B_WIDTH - fm.stringWidth(msgHighScore)) / 2 - 10,
	                B_HEIGHT / 2 - 100);
	        
	        String score1 = "1st: " + Integer.toString(int_score[0]);
	        String score2 = "2nd: " + Integer.toString(int_score[1]);
	        String score3 = "3rd: " + Integer.toString(int_score[2]);
	        
	        g.drawString(score1, (B_WIDTH - fm.stringWidth(score1)) / 2 - 10,
	                B_HEIGHT / 2 - 60);
	        g.drawString(score2, (B_WIDTH - fm.stringWidth(score2)) / 2 - 10,
	                B_HEIGHT / 2 - 40);
	        g.drawString(score3, (B_WIDTH - fm.stringWidth(score3)) / 2 - 10,
	                B_HEIGHT / 2 - 20);
	        
	        //easyB = new JButton("Easy");
	        //easyB.addActionListener(this);
	        easyB.setBounds(335, 320, 110, 20);
	        easyB.setBackground(Color.BLACK);
	        easyB.setForeground(Color.WHITE);
	        add(easyB);
	        
	        //mediumB = new JButton("Medium");
	        //mediumB.addActionListener(this);
	        mediumB.setBounds(335, 350, 110, 20);
	        mediumB.setBackground(Color.BLACK);
	        mediumB.setForeground(Color.WHITE);
	        add(mediumB);
	        
	        //hardB = new JButton("Hard");
	        //hardB.addActionListener(this);
	        hardB.setBounds(335, 380, 110, 20);
	        hardB.setBackground(Color.BLACK);
	        hardB.setForeground(Color.WHITE);
	        add(hardB);
	    }
	    
	    public static BufferedImage toBufferedImage(Image img)
	    {
	        if (img instanceof BufferedImage)
	        {
	            return (BufferedImage) img;
	        }

	        // Create a buffered image with transparency
	        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	        // Draw the image on to the buffered image
	        Graphics2D bGr = bimage.createGraphics();
	        bGr.drawImage(img, 0, 0, null);
	        bGr.dispose();

	        // Return the buffered image
	        return bimage;
	    }
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        
	    	inGame();
	    	if (ingame) {
	    		long time = System.nanoTime();
		        delta_time = (int) ((time - last_time) / 1000000);
		        last_time = time;
		        
		        //inGame();
		        
		    	updateMissiles();
		        updateSpaceShip();
		        updateAsteroids();
		        updateSmallAsteroids();
		        
		        checkCollisions();

		        repaint();
	    	}
	    	else {
	    		Object source = e.getSource();
	    		
	    		if (source == restart) {
	    			
	    			restart.setVisible(false);
	    			
	    			asteroids.clear();
	    			smallAsteroids.clear();
	    			spaceShip.resetMissiles();
	    			asteroidsDestroyed = 0;
	    	        
	    	        spaceShip.setX(ICRAFT_X);
	    			spaceShip.setY(ICRAFT_Y);
	    			spaceShip.setR(90);
	    			spaceShip.setDXDY(0, 0);
	    			spaceShip.setVisible(true);
	    	        
	    	        ingame = true;
	    	        
	    	        timer = new Timer(DELAY, this);
	    	        timer.start();
	    		}
	    		
	    		else if (source == easyB) {
	    			difficulty = 99;
	    			startMenu = false;
	    			initBoard();
	    			easyB.setVisible(false);
	    			mediumB.setVisible(false);
	    			hardB.setVisible(false);
	    		}
	    		
	    		else if (source == mediumB) {
	    			difficulty = 98;
	    			startMenu = false;
	    			initBoard();
	    			easyB.setVisible(false);
	    			mediumB.setVisible(false);
	    			hardB.setVisible(false);
	    		}
	    		
	    		else if (source == hardB) {
	    			difficulty = 97;
	    			startMenu = false;
	    			initBoard();
	    			easyB.setVisible(false);
	    			mediumB.setVisible(false);
	    			hardB.setVisible(false);
	    		}
	    	}
	    }
	    
	    static long getDeltaTime() {
	    	return delta_time;
	    }
	    
	    private void inGame() {

	        if (!ingame) {
	            timer.stop();
	        }
	    }
	    
	    private void updateMissiles() {

	        List<Missile> missiles = spaceShip.getMissiles();

	        for (int i = 0; i < missiles.size(); i++) {

	            Missile missile = missiles.get(i);

	            if (missile.isVisible()) {

	                missile.move();
	            } else {

	                missiles.remove(i);
	            }
	        }
	    }
	    
	    private void updateSpaceShip() {

	        if (spaceShip.isVisible()) {
	            
	            spaceShip.move();
	        }
	    }
	    
	    private void updateAsteroids() {

	    	Random generator = new Random(); 
	    	int j = generator.nextInt(100) + 1;
	    	
	    	if (j > difficulty) {
	    		asteroids.add(new Asteroid());
	    	}
	    	
	        for (int i = 0; i < asteroids.size(); i++) {

	            Asteroid a = asteroids.get(i);
	            
	            if (a.isVisible()) {
	                a.move();
	            } else {
	            	asteroids.remove(i);
	            }
	        }
	    }
	    
	    private void updateSmallAsteroids() {
	    	
	        for (int i = 0; i < smallAsteroids.size(); i++) {

	            SmallAsteroid a = smallAsteroids.get(i);
	            
	            if (a.isVisible()) {
	                a.move();
	            } else {
	            	smallAsteroids.remove(i);
	            }
	        }
	    }

	    public static boolean testIntersection(Shape shapeA, Shape shapeB) {
	    	   Area areaA = new Area(shapeA);
	    	   areaA.intersect(new Area(shapeB));
	    	   return !areaA.isEmpty();
	    	}
	    
	    public void checkCollisions() {

	        Ellipse2D r3 = spaceShip.getCircleShip();

	        
	        for (Asteroid asteroid : asteroids) {
	            
	            Ellipse2D r2 = asteroid.getCircle();

	            if (testIntersection(r2, r3)) {
	            	spaceShip.setVisible(false);
	                asteroid.setVisible(false);
	                ingame = false;
	            }/*
	            if (r3.intersects(r2)) {
	                
	                spaceShip.setVisible(false);
	                asteroid.setVisible(false);
	                ingame = false;
	            }*/
	        }
	        
	        for (SmallAsteroid smallAsteroid : smallAsteroids) {
	            
	            Rectangle r2 = smallAsteroid.getBounds();

	            if (r3.intersects(r2)) {
	                
	                spaceShip.setVisible(false);
	                smallAsteroid.setVisible(false);
	                ingame = false;
	            }
	        }

	        List<Missile> ms = spaceShip.getMissiles();

	        for (Missile m : ms) {

	            Rectangle r1 = m.getBounds();

	            for (Asteroid asteroid : asteroids) {

	                Ellipse2D r2 = asteroid.getCircle();

	                if (testIntersection(r1, r2)) {
	                	if (asteroid.getHealth() == 2) {
	                		asteroid.setHealth(1);
	                	}
	                	else {
		                    m.setVisible(false);
		                    asteroid.setVisible(false);
		                    if (difficulty == 99) {
		                    	asteroidsDestroyed++;
		                    }
		                    else if (difficulty == 98)
		                    	asteroidsDestroyed +=2;
		                    else if (difficulty == 97)
		                    	asteroidsDestroyed +=3;
		                    smallAsteroids.add(new SmallAsteroid(asteroid.getX(), asteroid.getY()));
		                    smallAsteroids.add(new SmallAsteroid(asteroid.getX(), asteroid.getY()));
	                	}
	                }/*
	                if (r1.intersects(r2)) {
	                    
	                	if (asteroid.getHealth() == 2) {
	                		asteroid.setHealth(1);
	                	}
	                	else {
		                    m.setVisible(false);
		                    asteroid.setVisible(false);
		                    if (difficulty == 99) {
		                    	asteroidsDestroyed++;
		                    }
		                    else if (difficulty == 98)
		                    	asteroidsDestroyed +=2;
		                    else if (difficulty == 97)
		                    	asteroidsDestroyed +=3;
		                    smallAsteroids.add(new SmallAsteroid(asteroid.getX(), asteroid.getY()));
		                    smallAsteroids.add(new SmallAsteroid(asteroid.getX(), asteroid.getY()));
	                	}
	                }*/
	            }
	            
	            for (SmallAsteroid smallAsteroid : smallAsteroids) {

	                Ellipse2D r2 = smallAsteroid.getCircle();

	                if (testIntersection(r1, r2)) {
	                	m.setVisible(false);
	                    smallAsteroid.setVisible(false);
	                    asteroidsDestroyed++;
	                }/*
	                if (r1.intersects(r2)) {
	                    
	                    m.setVisible(false);
	                    smallAsteroid.setVisible(false);
	                    asteroidsDestroyed++;
	                }*/
	            }
	        }
	    }
	    
	    public void readFile() {
	    	BufferedReader br = null;
			FileReader fr = null;

			try {

				//br = new BufferedReader(new FileReader(FILENAME));
				fr = new FileReader(FILENAME);
				br = new BufferedReader(fr);

				String sCurrentLine;

				while ((sCurrentLine = br.readLine()) != null) {
					//System.out.println(sCurrentLine);
					score[score_i] = sCurrentLine;
					int_score[score_i] = Integer.parseInt(score[score_i]);
					//System.out.println(score[score_i]);
					score_i++;
				}
				score_i = 0;

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (br != null)
						br.close();

					if (fr != null)
						fr.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
	    }

	    private class TAdapter extends KeyAdapter {

	        @Override
	        public void keyReleased(KeyEvent e) {
	            spaceShip.keyReleased(e);
	        }

	        @Override
	        public void keyPressed(KeyEvent e) {
	            spaceShip.keyPressed(e);
	        }
	    }
	 
}
