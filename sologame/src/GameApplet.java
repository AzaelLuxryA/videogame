import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;


public class GameApplet extends Applet implements Runnable, KeyListener {

    private Thread gameThread;
    private boolean running = false;
    protected Image buffer;
    protected Graphics bufferGraphics;

    protected boolean[] keys = new boolean[256];

   

    public void init() {
        setSize(1200, 800);
        
        buffer = createImage(getWidth(), getHeight());
        bufferGraphics = buffer.getGraphics();

		addKeyListener(this);  
		setFocusable(true);     
		requestFocus();         
    }

    public void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        running = false;
    }

    public void run() {
        while (running) {
            gameLoop();
            repaint();
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void gameLoop() {
        // Game update logic goes here (player, enemies, waves)
    }

    public void paint(Graphics pen) {
        
    }

    public void update(Graphics pen) {
        paint(pen); // Override default update behavior (to prevent flicker)
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Key press logic
    	
    	 if (e.getKeyCode() < keys.length)
 	        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Key release logic
    	
    	 if (e.getKeyCode() < keys.length)
 	        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Usually not needed
    }
    
   
}

