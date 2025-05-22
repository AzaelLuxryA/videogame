import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;

public class Sprite extends Rect {
	Rect hitbox;
	Rect roamingSpace;
	Rect chasingHitBox;
	
    private HashMap<String, Animation> animations = new HashMap<>();
    private String currentAnimationKey = "";

    private Image singleImage = null;  // For static sprites
    
	boolean isMoving = false;


    public Sprite(int x, int y, int w, int h) {
        super(x, y, w, h, null);
        hitbox = new Rect(x,y,w,h, null);
    }

    // -------- LOAD OPTIONS --------

    // Load a single image only
    public void setImage(Image img) {
        this.singleImage = img;
        this.animations.clear();
        this.currentAnimationKey = "";
    }

    // Add an animation with a name
    public void addAnimation(String name, Animation anim) {
        this.animations.put(name, anim);
        this.singleImage = null; // disable static image if any
        if (currentAnimationKey.equals("")) {
            currentAnimationKey = name;
        }
    }

    // Set which animation to show
    public void setAnimation(String name) {
        if (!name.equals(currentAnimationKey) && animations.containsKey(name)) {
            currentAnimationKey = name;
        }
    }


    // -------- DRAWING --------

    @Override
    public void draw(Graphics pen) {
        if (singleImage != null) {
            pen.drawImage(singleImage, x, y, w, h, null);
        } else {
            Animation anim = animations.get(currentAnimationKey);
            if (anim != null) {
    
                    pen.drawImage(anim.nextImage(), x, y, w, h, null);
               
            }
        }

        // Reset movement state for next frame
        isMoving = false;
    }


    public Animation getCurrentAnimation() {
        return animations.get(currentAnimationKey);
    }

    public boolean isUsingSingleImage() {
        return singleImage != null;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean ccollisionHitbox(Rect hitbox) {
    	if(this.hitbox.overlap(hitbox)) {
    		return true;
    	}
    	else return false;
    }
    
    public boolean isEnemy() {
    	return false;
    }
    public void drawHitBox(Graphics g) {
		hitbox.draw(g);
	}
    public void drawroamingSpace(Graphics g) {
    	roamingSpace.draw(g);
    }
    public void drawchasingHitBox(Graphics g) {
    	chasingHitBox.draw(g);
    }
    
    
}
