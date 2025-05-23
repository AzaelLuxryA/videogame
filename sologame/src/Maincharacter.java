import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.HashSet;


public class Maincharacter extends Entity {
	private final int ATTACK_COOLDOWN_FRAMES = 30; // frames to wait before next attack
	private int attackCooldownCounter = 0;
	Rect hitbox;
	Rect roamingSpace = null;
	 private boolean shouldFlip = false;
	 private boolean isAttacking = false;
	 private String attackDirection = "down";
	 private int attackFrameCounter = 0;
	 private final int ATTACK_FRAMES = 8; // Your 8-frame animation
	 private HashSet<Entity> hitEnemiesThisAttack = new HashSet<>();
	
	
    public Maincharacter(int x, int y, int w, int h, int hp) {
        super(x, y, w, h, hp);
        
        int hitBoxWidth = (int)(w*0.5);
		int hitBoxHeight = (int)(h*0.5);
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
        if (keys[KeyEvent.VK_SPACE] && !isAttacking && attackCooldownCounter ==0) {
            isAttacking = true;
            attackFrameCounter = 0;
            attackCooldownCounter = ATTACK_COOLDOWN_FRAMES;
            hitEnemiesThisAttack.clear(); 
            setAnimation("attack_" + attackDirection);
            shouldFlip = attackDirection.equals("left"); // Flip only if attacking left
        }
       }
       if (attackCooldownCounter > 0) {
    	    attackCooldownCounter--;
    	}
       return new int[]{newX, newY}; 
       
    }
    
    public boolean isAttacking() {
        return isAttacking;
    }
    public Rect getAttackHitbox() {
        int attackSize = 50;
        int offset = 10;
        int attackoffset = 50;
        

        int attackX = x;
        int attackY = y;
        int width = w;
        int height = h;

        switch (attackDirection) {
            case "right":
                attackX = x + w - attackoffset;
                attackY = y + offset;
                width = attackSize;
                height = h - 2 * offset;
                break;
            case "left":
                attackX = x - attackSize + attackoffset;
                attackY = y + offset;
                width = attackSize;
                height = h - 2 * offset;
                break;
            case "up":
                attackX = x + offset;
                attackY = y - attackSize + attackoffset;
                width = w - 2 * offset;
                height = attackSize;
                break;
            case "down":
                attackX = x + offset;
                attackY = y + h - attackoffset;
                width = w - 2 * offset;
                height = attackSize;
                break;
        }

        return new Rect(attackX, attackY, width, height, Color.RED);
    }
    public void attack(RoomBase room) {
    	if (!isAttacking || attackFrameCounter < 4 || attackFrameCounter > 10) return;
    	
        Rect attackBox = getAttackHitbox();

        for (Entity e : room.getEntities()) {
        	//e.update();
            if (!e.isDead() && e.isEnemy() && attackBox.overlap(e.getHitBox())) {
            	
                if (attackBox.overlap(e.getHitBox())) {
                	System.out.println("it overlaps");
                    e.takeDamage(1);
                    hitEnemiesThisAttack.add(e); // 👈 Prevent hitting this one again
                }
            }
        }
    }
    
    public void drawAttackBox(Graphics g, Camera camera) {
        if (isAttacking) {
            Rect attackBox = getAttackHitbox();
            g.setColor(new Color(255, 0, 0, 100)); // transparent red
            g.fillRect(attackBox.x - camera.x, attackBox.y - camera.y, attackBox.w, attackBox.h);
        }
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
	public Rect getHitbox() {
		return this.hitbox;
	}
    
  

}