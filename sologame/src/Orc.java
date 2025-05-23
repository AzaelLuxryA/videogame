//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.math.*;
public class Orc extends Entity{

private int hp;
private Rect hitbox;
private Rect roamingSpace;
private Rect chasingHitBox;
int random;
private int step = 3;
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
boolean guard = false;
private int phase = 1;

int chasingHBw,chasingHBh,chasingx,chasingy;
//private int max = 100;
//private int maxy = 100;
	
	public Orc(int x, int y, int w, int h, int hp) {
		
		super(x,y,w,h,hp);
		if(hp < 8) {
			this.hp = 8;
		}else this.hp = hp;
		int hitBoxWidth = (int)(w*0.5);
		int hitBoxHeight = (int)(h*0.5);
		hitBoxx = (w - hitBoxWidth)/2;
		hitBoxy = (h - hitBoxHeight)/2;
		this.hitbox = new Rect(x + hitBoxx,y + hitBoxy,hitBoxWidth,hitBoxHeight, null);
		startPointx = x;
		startPointy = y;
		range = (int)(Math.random()*90 + 50);
		setupAnimations();
		random = (int)(Math.random() *3)+ 1;
		direction = "down";
		roamingSpace = new Rect((x - w)/2,(y - h)/2,w *3, h*3, null);
		
		chasingHBw = (int)(w*2.4);
		chasingHBh = (int)(h*2.4);
		chasingx = (w - chasingHBw)/2;
		chasingy = (h - chasingHBh)/2;
		
		//phase = 1;
		chasingHitBox = new Rect(chasingx,chasingy,chasingHBw,chasingHBh, null);
		//move();
	}
	private void setupAnimations() {
		
		//int orcType = (int)(Math.random()*3) + 1;
		switch(phase) {
		case 1:
			addAnimation("orc_idle", new Animation("orc1_idle_1-", 4, 15));
		    addAnimation("orc_dnw", new Animation("orc1_walk_0-", 4, 10));
		    addAnimation("orc_upw", new Animation("orc1_walk_1-", 4, 10));
		    addAnimation("orc_lfw", new Animation("orc1_walk_2-", 4, 10));
		    addAnimation("orc_rtw", new Animation("orc1_walk_3-", 4, 10));
		
		    //setAnimation("orc_idle"); // Default animation
		    break;
		case 2:
			addAnimation("orc_idle", new Animation("orc2_idle_1-", 4, 15));
		    addAnimation("orc_dnw", new Animation("orc2_walk_0-", 6, 10));
		    addAnimation("orc_upw", new Animation("orc2_walk_1-", 6, 10));
		    addAnimation("orc_lfw", new Animation("orc2_walk_2-", 6, 10));
		    addAnimation("orc_rtw", new Animation("orc2_walk_3-", 6, 10));
		
		    //setAnimation("orc_idle"); // Default animation
		    break;
		case 3:
			addAnimation("orc_idle", new Animation("orc3_idle_1-", 4, 15));
		    addAnimation("orc_dnw", new Animation("orc3_walk_0-", 6, 10));
		    addAnimation("orc_upw", new Animation("orc3_walk_1-", 6, 10));
		    addAnimation("orc_lfw", new Animation("orc3_walk_2-", 6, 10));
		    addAnimation("orc_rtw", new Animation("orc3_walk_3-", 6, 10));
		
		    //setAnimation("orc_idle"); // Default animation
		    break;
		}
		
		
		
	}
	
	public int getHP() {
	return hp;
	}
	public void isWorking() {
		
	}
	
	public void takeDamage(int damage) {
		this.hp -= damage;
		
		if(hp >3 && hp < 6) {
			phase = 2;
			setupAnimations();
		}
		else if(hp>=0 && hp <=3) {
			phase = 3;
			setupAnimations();
		}else if(hp <0) hp = 0;
		
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
				Orc.super.chase(mainc.hitbox);
				if (x > oldx) {
					setAnimation("orc_rtw");
				} else if (x < oldx) {
					setAnimation("orc_lfw");
				} else if (y > oldy) {
					setAnimation("orc_dnw");
				} else if (y < oldy) {
					setAnimation("orc_upw");
				}
				updateHitbox();
			}
			else if(this.hitbox.overlap(mainc.hitbox)) {
				int oldx = x;
				int oldy = y;
				Orc.super.avoid(mainc.hitbox);
				if (x > oldx) {
					setAnimation("orc_rtw");
				} else if (x < oldx) {
					setAnimation("orc_lfw");
				} else if (y > oldy) {
					setAnimation("orc_dnw");
				} else if (y < oldy) {
					setAnimation("orc_upw");
				}
				updateHitbox();
			}
			else if(this.hitbox.overlap(roamingSpace)==false) {
				int oldx = x;
				int oldy = y;
				Orc.super.chase(roamingSpace);
				if (x > oldx) {
					setAnimation("orc_rtw");
				} else if (x < oldx) {
					setAnimation("orc_lfw");
				} else if (y > oldy) {
					setAnimation("orc_dnw");
				} else if (y < oldy) {
					setAnimation("orc_upw");
				}
				updateHitbox();
			}
				
			else {
			switch(random) {
			case 1:
				if(goingRight) {
					setAnimation("orc_rtw");
					x+= step;
					if(x >= (startPointx + range)) {
						
						goingRight = false;
					}
				}else {
					setAnimation("orc_lfw");
					x-= step;
					if( x<= (startPointx - range)) {
						goingRight = true;
					}
				}
				updateHitbox();
				break;
			case 2:
				if(goingUp) {
					setAnimation("orc_dnw");
					y+=step;
					if(y >= (startPointy + range)) {
						goingUp = false;
					}
				}else {
					setAnimation("orc_upw");
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
					setAnimation("orc_dnw");
					y+= step;
					break;
				case "up":
					setAnimation("orc_upw");
					y-=step;
					break;
				case "left":
					setAnimation("orc_lfw");
					x-=step;
					break;
				case "right":
					setAnimation("orc_rtw");
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
	        this.hitbox = new Rect(x + offsetX, y + offsetY, width, height, null);
	    }
	public int getHitBoxx() {
		return hitbox.x;
	}
	public int getHitBoxy() {
		return hitbox.y;
	}
	void updateHitbox() {
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