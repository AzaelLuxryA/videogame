import java.awt.Color;
import java.awt.Graphics;

public class Entity extends Sprite{
    private int timesHit = 0; // Simple hit counter
    private boolean wasHitThisAttack = false; // NEW: Track if already hit in current attack
    private int maxhp;
	private int hp;
	private Rect hitbox;
	int hitBoxx,hitBoxy;
	private boolean corpseMade = false;
	
	public Entity(int x, int y, int w, int h, int hp) {
		super(x, y, w, h);
		maxhp = hp;
		this.hp = hp;
		
		
		int hitBoxWidth = (int)(w*0.4);
		int hitBoxHeight = (int)(h*0.4);
		hitBoxx = (w - hitBoxWidth)/2;
		hitBoxy = (h - hitBoxHeight)/2;
		this.hitbox = new Rect(x + hitBoxx,y + hitBoxy,hitBoxWidth,hitBoxHeight, null );
		
		this.hitbox = new Rect(x,y,w,h, null);
		// TODO Auto-generated constructor stub
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	
	public int getHP() {
		return hp;
	}
	
	public void setHP(int newHP) {
		hp = newHP;
	}
		
	public void takeDamage(int damage) {
		this.hp -= damage;
		if (hp <= 0) hp = 0;

		if (hp > maxhp) hp = maxhp;

		
	}
	public boolean isDead() {
		return hp <= 0;
	}
	
	
	public void setHitbox(int offsetX, int offsetY, int width, int height) {
	        this.hitbox = new Rect(x + offsetX, y + offsetY, width, height, null);
	    }
	
	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
	}
	private void updateHitbox() {
		hitbox.setPosition(x + hitBoxx, y + hitBoxy);
	}
	
	
	public Entity getEntity() {
		return this;
	}
	
	 public void registerHit() {
	        if (!wasHitThisAttack) { // Only count new hits
	            timesHit++;
	            wasHitThisAttack = true;
	        }
	    }
	    
	public void resetHitTracking() { // NEW: Call this when attack ends
	        wasHitThisAttack = false;
	    }
	    
	public boolean shouldRemove() {
	        return timesHit >= 3;
	 }
	
	public void drawHUD(Graphics g) {
	    int barWidth = 200;
	    int barHeight = 20;
	    int barX = 20;
	    int barY = 20;

	    float healthRatio = (float) getHP() / maxhp;
	    if (healthRatio < 0) healthRatio = 0;

	    // Draw background
	    g.setColor(Color.DARK_GRAY);
	    g.fillRect(barX, barY, barWidth, barHeight);

	    // Health fill
	    if (healthRatio > 0.5) {
	        g.setColor(Color.GREEN);
	    } else if (healthRatio > 0.2) {
	        g.setColor(Color.ORANGE);
	    } else {
	        g.setColor(Color.RED);
	    }

	    g.fillRect(barX, barY, (int)(barWidth * healthRatio), barHeight);

	    // Border
	    g.setColor(Color.BLACK);
	    g.drawRect(barX, barY, barWidth, barHeight);

	    // Optional: HP text
	    g.setColor(Color.WHITE);
	    g.drawString("HP: " + getHP() + " / " + maxhp, barX + 5, barY + 15);
	}
	
	public void drawHealthBar(Graphics g) {
	    int barWidth = w - 20;
	    int barHeight = 6;
	    int padding = 4;

	    // Calculate health ratio
	    float healthRatio = (float) getHP() / maxhp; // assuming max HP = 100
	    if (healthRatio < 0) healthRatio = 0;

	    // Position bar above the entity
	    int barX = x;
	    int barY = y - barHeight - padding;

	    // Draw background (gray)
	    g.setColor(Color.DARK_GRAY);
	    g.fillRect(barX, barY, barWidth, barHeight);

	    // Draw foreground (green/red depending on HP)
	    if (healthRatio > 0.5) {
	        g.setColor(Color.GREEN);
	    } else if (healthRatio > 0.2) {
	        g.setColor(Color.ORANGE);
	    } else {
	        g.setColor(Color.RED);
	    }

	    int filledWidth = (int)(barWidth * healthRatio);
	    g.fillRect(barX, barY, filledWidth, barHeight);

	    // Optional: draw border
	    g.setColor(Color.BLACK);
	    g.drawRect(barX, barY, barWidth, barHeight);
	}
	public boolean isCorpseMade() {
	    return corpseMade;
	}
	public void markCorpseMade() {
	    corpseMade = true;
	}

}
