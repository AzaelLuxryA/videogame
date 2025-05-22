import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Maincharacter extends Entity {
	
		Rect hitbox;
		Rect roamingSpace = null;
	 private boolean shouldFlip = false;
	 private boolean isAttacking = false;
	 private String attackDirection = "down";
	 private int attackFrameCounter = 0;
	 private final int ATTACK_FRAMES = 8; // Your 8-frame animation

	
	
    public Maincharacter(int x, int y, int w, int h, int hp) {
        super(x, y, w, h, hp);
        
        int hitBoxWidth = (int)(w*0.4);
		int hitBoxHeight = (int)(h*0.4);
		hitBoxx = (w - hitBoxWidth)/2;
		hitBoxy = (h - hitBoxHeight)/2;
		this.hitbox = new Rect(x + hitBoxx,y + hitBoxy,hitBoxWidth,hitBoxHeight, null);
    
        setupAnimations();
    }

    private void setupAnimations() {
        addAnimation("idle", new Animation("maincharacter_0-", 18, 3));
        addAnimation("dnw", new Animation("maincharacter_walk_0-", 24, 1));
        addAnimation("lfw", new Animation("maincharacter_walk_left_0-", 24, 1));
        addAnimation("rtw", new Animation("maincharacter_walk_0-", 18, 1));
        addAnimation("upw", new Animation("maincharacter_walk_0-", 18, 1));
        
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
    	   if (keys[KeyEvent.VK_A]) {
    		    setAnimation("lfw");
    		    newX -= 4;
    		    attackDirection = "left";
    		    moved = true;
    		    this.shouldFlip = true; // Flip when moving left
    		    updateHitbox();
    		}
    		if (keys[KeyEvent.VK_D]) {
    		    setAnimation("rtw");
    		    newX += 4;
    		    attackDirection = "right";
    		    moved = true;
    		    shouldFlip = false; // Unflip when moving right
    		    updateHitbox();
    		}
    		if (keys[KeyEvent.VK_W]) {
    			if(keys[KeyEvent.VK_A]) {
    				setAnimation("left");
    			}else if(keys[KeyEvent.VK_D]) {
    				setAnimation("right");
    			}else setAnimation("upw");
    		    newY -= 4;
    		    attackDirection = "up";
    		    moved = true;
    		    shouldFlip = false;
    		    updateHitbox();
    		}
    		if (keys[KeyEvent.VK_S]) {
    			if(keys[KeyEvent.VK_A]) {
    				setAnimation("left");
    			}else if(keys[KeyEvent.VK_D]) {
    				setAnimation("right");
    			}else setAnimation("down");
    		    newY += 4;
    		    attackDirection = "down";
    		    moved = true;
    		    shouldFlip = false;
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
            shouldFlip = attackDirection.equals("left"); // Flip only if attacking left
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
	
	private void updateHitbox() {
		hitbox.setPosition(x + hitBoxx,y +  hitBoxy);
	}

	@Override
	public Entity getEntity() {
		// TODO Auto-generated method stub
		return super.getEntity();
	}
	public Rect getHitbox() {
		return this.hitbox;
	}
    
  

}