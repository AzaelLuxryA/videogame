import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class MainC extends Entity {
	Rect hitbox;
	Rect roamingSpace = null;
	
	 private boolean isAttacking = false;
	 private String attackDirection = "down";
	 private int attackFrameCounter = 0;
	 private final int ATTACK_FRAMES = 8; // Your 8-frame animation

	
	
    public MainC(int x, int y, int w, int h, int hp) {
        super(x, y, w, h, hp);
        
        int hitBoxWidth = (int)(w*0.4);
		int hitBoxHeight = (int)(h*0.4);
		hitBoxx = (w - hitBoxWidth)/2;
		hitBoxy = (h - hitBoxHeight)/2;
		this.hitbox = new Rect(x + hitBoxx,y + hitBoxy,hitBoxWidth,hitBoxHeight, null);
    
        setupAnimations();
    }

    private void setupAnimations() {
        addAnimation("idle", new Animation("idle_1-", 40, 15));
        addAnimation("dnw", new Animation("walk_0-", 5, 10));
        addAnimation("lfw", new Animation("walk_1-", 5, 10));
        addAnimation("rtw", new Animation("walk_2-", 5, 10));
        addAnimation("upw", new Animation("walk_3-", 5, 10));
        
        addAnimation("attack_down", new Animation("attack_0-", 8, 5)); // 5ms per frame
        addAnimation("attack_left", new Animation("attack_1-", 8, 5));
        addAnimation("attack_right", new Animation("attack_2-", 8, 5));
        addAnimation("attack_up", new Animation("attack_3-", 8, 5));

        setAnimation("idle"); // Default animation
    }

    public int[] updateAnimation(boolean[] keys) {
        boolean moved = false;
        int newX = x;
        int newY = y;

        if (isAttacking) {
            attackFrameCounter++;
            if (attackFrameCounter >= ATTACK_FRAMES) {
                isAttacking = false;
                setAnimation("idle");
            }
        }
        
       if (!isAttacking) {
        if (keys[KeyEvent.VK_W]) {
            setAnimation("upw");
            newY -= 4;
            attackDirection = "up";
            moved = true;
            updateHitbox();
        }
        if (keys[KeyEvent.VK_S]) {
            setAnimation("dnw");
            newY += 4;
            attackDirection = "down";
            moved = true;
            updateHitbox();
        }
        if (keys[KeyEvent.VK_A]) {
            setAnimation("lfw");
            newX -= 4;
            attackDirection = "left";
            moved = true;
            updateHitbox();
        }
        if (keys[KeyEvent.VK_D]) {
            setAnimation("rtw");
            newX += 4;
            attackDirection = "right";
            moved = true;
            updateHitbox();
        }
        if (keys[KeyEvent.VK_K]) {
        	takeDamage(5);
        }

        if (!moved) {
            setAnimation("idle");
        }
        if (keys[KeyEvent.VK_SPACE] && !isAttacking) {
            isAttacking = true;
            attackFrameCounter = 0;
            setAnimation("attack_" + attackDirection);
        }
       }
       return new int[]{newX, newY}; 

    }
    
    public boolean isAttacking() {
        return isAttacking;
    }
    
    public int getHitBoxx() {
		return hitbox.x;
	}
	public int getHitBoxy() {
		return hitbox.y;
	}
	@Override
	public boolean isEnemy() {
		return false;
	}
	@Override
	public void drawHitBox(Graphics g) {
		hitbox.draw(g);
	}
	@Override
	public void drawroamingSpace(Graphics g) {
		roamingSpace.draw(g);
	}
	
	void updateHitbox() {
		hitbox.setPosition(x + hitBoxx,y +  hitBoxy);
	}

	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return super.getEntity();
	}
    
  

}