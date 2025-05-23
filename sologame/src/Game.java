import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game extends GameApplet {
    // Create the main character sprite at position (100,100) with a size of 150x150 pixels
	Sound corpseSound = new Sound("corpse_sound.wav");
	Sound caveSound = new Sound("backgroundmusic.wav");
    Maincharacter mainC = new Maincharacter(100, 100, 128, 128, 100);
    Slime enemy = new Slime(400,250,128,128,3);
    Slime enemy2 = new Slime(400,250,128,128,3);
    Corpse corpse = new Corpse(enemy.x, enemy.y, enemy.w, enemy.h, corpseSound);
    //Orc enemy2 = new Orc(300,200,128,128,8);
    Slime minion;
    boolean pPressedLastFrame = false;
    LightOverlay overlay;

    // Define an animation for the enemy walking left
    Animation E_walkLeft = new Animation("name", 5, 10);

    // Create an enemy instance at position (300,400) with a size of 50x50 pixels

    // Variables to store the current room
    RoomBase currentRoom;
    RoomOne room1;
    RoomTwo room2;
    RoomThree room3;

    @Override
    public void init() {
    	
        super.init(); // Initialize the base class functionality (such as image buffering)
        try {
            overlay = new LightOverlay("darkcave.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Create RoomTwo instance and pass the main character and enemy as parameters
        room1 = new RoomOne(mainC);
        room2 = new RoomTwo(mainC);
        room3 = new RoomThree(mainC);
        currentRoom = room1;
        
        currentRoom.addEntity(enemy);
        currentRoom.addEntity(mainC);
       // currentRoom.addEntity(enemy2);
        // Set default animation to idle for the main character
        mainC.setAnimation("idle");
        corpse.setAnimation("idle");
        caveSound.play();
		//room2.addEntity(enemy2);
		
		
    }

    @Override
    public void gameLoop() {

    	overlay.update();
    	int[] newPos = mainC.updateAnimation(keys);
//    	System.out.println(enemy.random);
//    	System.out.println("sprite x and y is " + mainC.x + " " +mainC.y);
//    	System.out.println("enemy hitbox is " + mainC.getHitBoxx() + " " + mainC.getHitBoxy());
    	//enemy.move(mainC);
    	//enemy2.move(mainC);
    	//enemy2.move(mainC);
    	//if(minion != null) {
    	//	minion.move(mainC);
    	//}
        int newX = mainC.x;
        int newY = mainC.y;
        
        //int[] newPos = mainC.updateAnimation(keys); 
        // Update movement and animation through MainC class
        mainC.updateAnimation(keys);
        if (keys[KeyEvent.VK_P] && !pPressedLastFrame) {
            enemy.takeDamage(1);
            //mainC.takeDamage(5);
            
            System.out.println("enemy2 HP = " + enemy.getHP());
            System.out.println("is Enemy" + enemy.isEnemy());
            
        }
        
        
        
        pPressedLastFrame = keys[KeyEvent.VK_P];
//        System.out.println("remaining Health" + mainC.getHP());
        System.out.println("remainig entities" + room2.getEntities().size());
//        System.out.println("main x = " + mainC.x + "main y = " + mainC.y );
        
       
        
        if (currentRoom.canMoveTo(mainC, newPos[0], newPos[1])) {
            mainC.setPosition(newPos[0], newPos[1]);
        }
        
        if (enemy.getHP() == 0 && !enemy.isCorpseMade()) {
            corpse.x = enemy.x;
            corpse.y = enemy.y;
            //corpse.setImage(buffer);
            currentRoom.addEntity(corpse);
            enemy.markCorpseMade();
            currentRoom.removeDeadEntity();
        }
        if (keys[KeyEvent.VK_F]) {
        	corpse.update(mainC, true);
        	
        }
        currentRoom.removeDeadEntity();
        
        
        
        
        //if(room1.IsRoomOver()) { 
//        	if(mainC.x > 849 && mainC.y > 688) {
//        	currentRoom = room3; 
//        	mainC.setLocation(200, 200);
//        	mainC.setHP(100);
//        	room2.addEntity(mainC);
//        	}
       // }
        
        if(currentRoom instanceof RoomOne) {
        	if(room1.getDoor(0).overlap(mainC) && room1.getEntities().size()==1) {
        		currentRoom = room2;
        		
        		mainC.setPosition(100, 100);
        		currentRoom.removeEntity(mainC);
        		currentRoom.addEntity(mainC);
        		currentRoom.addEntity(enemy2);
        		enemy2.avoid();
        		enemy2.setAnimation("idle");
        		
        		
        	}
        	
       }
        
        
        if(currentRoom instanceof RoomTwo) {
        	//currentRoom.addEntity(enemy2);
        	//enemy2.move(mainC);
        	
        	if(room2.getDoor(0).overlap(mainC) & room2.getEntities().size()==1) {
        		currentRoom = room3;
        		mainC.setPosition(100, 100);
        		currentRoom.addEntity(mainC);
        		
        	}
        	
 
        	
        }
        for (Entity e : currentRoom.getEntities()) {
            if (e instanceof Maincharacter) {
                ((Maincharacter) e).updateAnimation(keys); 
                ((Maincharacter) e).attack(currentRoom); // ✅ This is required
            } else {
                e.update(); // whatever your enemy or NPC update logic is
            }
        }
        
        for (Entity e : currentRoom.getEntities()) {
            // Skip the player

            int oldX = e.x;
            int oldY = e.y;

            if (e instanceof Orc) {
                ((Orc) e).move(mainC);
            } else if (e instanceof Slime) {
            	//System.out.println("slimeismoving");
                ((Slime) e).move(mainC);
                e.updateHitbox();
            }
           	
            if (!currentRoom.canMoveTo(e, e.x, e.y)) {
                e.setPosition(oldX, oldY); // Undo move if collision
                e.updateHitbox();
            }
        }
        
      
        
        for (Entity e : currentRoom.getEntities()) {
            e.resetHitTracking();
        }
        
        // Attack detection
        if (mainC.isAttacking()) {
            for (int i = 0; i < currentRoom.getEntities().size(); i++) {
                Entity e = currentRoom.getEntities().get(i);
                if (e.isEnemy() && e.overlap(mainC.getAttackHitbox())){
                    e.registerHit();
                    
                }
            }
        }
        
    }

    @Override
    public void paint(Graphics g) {
        // Draw the current room using buffer graphics
        currentRoom.draw(bufferGraphics);
        overlay.draw((Graphics2D)bufferGraphics,1000,1000);
        // Draw the main character sprite
       // mainC.draw(bufferGraphics);

        // Flip the buffer image to the screen for rendering
        g.drawImage(buffer, 0, 0, this);
    }
}
