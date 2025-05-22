//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.math.*;
public class Slime extends Entity{

private int hp;
private Rect hitbox;
private Rect roamingSpace;
private Rect chasingHitBox;
int random;
private int step = 2;
int startPointx;
int startPointy;
int range;
boolean goingRight = true;
boolean goingUp = true;
String direction;
String directions[] = {"up","down","right","left"}; 
int randomDirection = (int)(Math.random()*3);
int directionTimer = 0;
int hitBoxx, hitBoxy;

int chasingHBw,chasingHBh,chasingx,chasingy;
//private int max = 100;
//private int maxy = 100;
	
	public Slime(int x, int y, int w, int h, int hp) {
		
		super(x,y,w,h,hp);
		this.hp = hp;
		int hitBoxWidth = (int)(w*0.4);
		int hitBoxHeight = (int)(h*0.4);
		hitBoxx = (w - hitBoxWidth)/2;
		hitBoxy = (h - hitBoxHeight)/2;
		this.hitbox = new Rect(x + hitBoxx,y + hitBoxy,hitBoxWidth,hitBoxHeight, null);
		startPointx = x;
		startPointy = y;
		range = (int)(Math.random()*90 + 50);
		setupAnimations();
		random = (int)(Math.random() *3)+ 1;
		direction = "down";
		int roamWidth = w * 3;
		int roamHeight = h * 3;
		int roamX = x + w / 2 - roamWidth / 2;
		int roamY = y + h / 2 - roamHeight / 2;

		roamingSpace = new Rect(roamX, roamY, roamWidth, roamHeight, null);
		
		chasingHBw = (int)(w*1.4);
		chasingHBh = (int)(h*1.4);
		chasingx = (w - chasingHBw)/2;
		chasingy = (h - chasingHBh)/2;
		
		
		chasingHitBox = new Rect(chasingx,chasingy,chasingHBw,chasingHBh, null);
		//move();
	}
	private void setupAnimations() {
		
		int slimeType = (int)(Math.random()*3) + 1;
		switch(slimeType) {
		case 1:
			addAnimation("slime_idle", new Animation("slime1_idle_1-", 6, 15));
		    addAnimation("slime_dnw", new Animation("slime1_walk_0-", 6, 10));
		    addAnimation("slime_upw", new Animation("slime1_walk_1-", 6, 10));
		    addAnimation("slime_lfw", new Animation("slime1_walk_2-", 6, 10));
		    addAnimation("slime_rtw", new Animation("slime1_walk_3-", 6, 10));
		
		    setAnimation("slime_idle"); // Default animation
		    break;
		case 2:
			addAnimation("slime_idle", new Animation("slime2_idle_1-", 8, 15));
		    addAnimation("slime_dnw", new Animation("slime2_walk_0-", 8, 10));
		    addAnimation("slime_upw", new Animation("slime2_walk_1-", 8, 10));
		    addAnimation("slime_lfw", new Animation("slime2_walk_2-", 8, 10));
		    addAnimation("slime_rtw", new Animation("slime2_walk_3-", 8, 10));
		
		    setAnimation("slime_idle"); // Default animation
		    break;
		case 3:
			addAnimation("slime_idle", new Animation("slime3_idle_0-", 6, 15));
		    addAnimation("slime_dnw", new Animation("slime3_walk_0-", 6, 10));
		    addAnimation("slime_upw", new Animation("slime3_walk_1-", 6, 10));
		    addAnimation("slime_lfw", new Animation("slime3_walk_2-", 6, 10));
		    addAnimation("slime_rtw", new Animation("slime3_walk_3-", 6, 10));
		
		    setAnimation("slime_idle"); // Default animation
		    break;
		}
		
			
	    
	}
	
	public int getHP() {
	return hp;
	}
	
	public void takeDamage(int damage) {
		this.hp -= damage;
		if(hp <0) hp = 0;
		
	}
	public boolean isDead() {
		return hp <= 0;
	}
	private String pickRandomDirec() {
		 int r = (int)(Math.random() * 4);
		    switch (r) {
		        case 0: 
		        	if(y <= (startPointy - range)) {
		        		return "down";
		        	}else return "up";
		        case 1:
		        	if(y >= (startPointy + range)) {
		        		return "up";
		        	}else return "down";
		        case 2:
		        	if(x <= (startPointx - range)) {
		        		return "right";
		        	}else return "left";
		        default:
		        	if(x>= (startPointx + range)) {
		        		return "left";
		        	}return "right";
		        	
		    }
	}
	public void move(Maincharacter mainc) {
		
			if(this.chasingHitBox.overlap(mainc.hitbox)) {
				int oldx = x;
				int oldy = y;
				Slime.super.chase(mainc.hitbox);
				if (x > oldx) {
					setAnimation("slime_rtw");
				} else if (x < oldx) {
					setAnimation("slime_lfw");
				} else if (y > oldy) {
					setAnimation("slime_dnw");
				} else if (y < oldy) {
					setAnimation("slime_upw");
				}
				updateHitbox();
			}
			else if(this.hitbox.overlap(mainc.hitbox)) {
				int oldx = x;
				int oldy = y;
				Slime.super.avoid(mainc.hitbox);
				if (x > oldx) {
					setAnimation("slime_rtw");
				} else if (x < oldx) {
					setAnimation("slime_lfw");
				} else if (y > oldy) {
					setAnimation("slime_dnw");
				} else if (y < oldy) {
					setAnimation("slime_upw");
				}
				updateHitbox();
			}
			else if(this.hitbox.overlap(roamingSpace)==false) {
				int oldx = x;
				int oldy = y;
				Slime.super.chase(roamingSpace);
				if (x > oldx) {
					setAnimation("slime_rtw");
				} else if (x < oldx) {
					setAnimation("slime_lfw");
				} else if (y > oldy) {
					setAnimation("slime_dnw");
				} else if (y < oldy) {
					setAnimation("slime_upw");
				}
				updateHitbox();
			}
				
			else {
			switch(random) {
			case 1:
				if(goingRight) {
					setAnimation("slime_rtw");
					x+= step;
					if(x >= (startPointx + range)) {
						
						goingRight = false;
					}
				}else {
					setAnimation("slime_lfw");
					x-= step;
					if( x<= (startPointx - range)) {
						goingRight = true;
					}
				}
				updateHitbox();
				break;
			case 2:
				if(goingUp) {
					setAnimation("slime_dnw");
					y+=step;
					if(y >= (startPointy + range)) {
						goingUp = false;
					}
				}else {
					setAnimation("slime_upw");
					y-=step;
					if( y<=(startPointy - range)) {
						goingUp=true;
					}
				}
				updateHitbox();
				break;
			case 3:
				
				if(directionTimer <= 0 ) {
					direction = pickRandomDirec();
					
					directionTimer = 50;
				}
				
				switch(direction) {
				case "down":
					setAnimation("slime_dnw");
					y+= step;
					break;
				case "up":
					setAnimation("slime_upw");
					y-=step;
					break;
				case "left":
					setAnimation("slime_lfw");
					x-=step;
					break;
				case "right":
					setAnimation("slime_rtw");
					x+=step;
					break;
				}
				
					
				updateHitbox();
				directionTimer--;
				break;
				
			}
			
			}
			
			
	}
	
	public void setHitbox(int offsetX, int offsetY, int width, int height) {
	        this.hitbox = new Rect(x + offsetX, y + offsetY, width, height,null);
	    }
	public int getHitBoxx() {
		return hitbox.x;
	}
	public int getHitBoxy() {
		return hitbox.y;
	}
	private void updateHitbox() {
		hitbox.setPosition(x + hitBoxx,y +  hitBoxy);
		chasingHitBox.setPosition(x + chasingx, y +chasingy);
	}
	@Override
	public boolean isEnemy() {
		return true;
	}
	@Override
	public void drawHitBox(Graphics g) {
		hitbox.draw(g);
	}
	@Override
	public void drawroamingSpace(Graphics g) {
		roamingSpace.draw(g);
	}
	@Override
	public void drawchasingHitBox(Graphics g) {
		chasingHitBox.draw(g);
	}
	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x , y );
	}
	
}