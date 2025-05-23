import java.awt.Graphics;
import java.awt.Image;

public class Corpse extends Entity {
    private boolean interacted = false;
    private Sound sound; // A custom class or audio system
    //private Image corpseImage; // Single animation frame
    private Rect hitbox;
    private int hp;

    public Corpse(int x, int y, int w, int h, Sound sound) {
        super(x, y, w, h, 1); // HP = 1, since it's not a fightable entity
        this.sound = sound;

        // Set a simple hitbox (you could adjust if needed)
        hitbox = new Rect(x, y, w, h, null);
        setupAnimations();
        hp = 1;
    }

    public void update(Maincharacter player, boolean fPressed) {
        if (interacted) return;

        // Check if player is overlapping and F is pressed
        if (this.getHitbox().overlap(player.getHitbox()) && fPressed) {
        	sound.play();
            interact();
        }
    }
    private void setupAnimations() {
        addAnimation("idle", new Animation("corpse_0-", 3, 3));
        setAnimation("idle");
    }
  

    private void interact() {
        sound.play(); // Play a sound effect
        interacted = true; // Mark as interacted
    }
    public boolean isDead() {
    	
    	if(shouldRemove()) {
    		hp = 0;
    		return hp <= 0;
    		
    	}
    	else return false;
	}

    public boolean shouldRemove() {
    	return interacted && (sound == null || sound.isDone());
    }

    
    public Rect getHitbox() {
		return this.hitbox;
	}
    public Rect updateHitbox(int x, int y) {
    	return hitbox = new Rect(x, y, w, h, null);
    }
    
}
