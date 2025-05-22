import java.awt.Color;
import java.awt.Graphics;

public class Rect
{
	//------------------------------------------------------------------------//
	
	int x;
	int y;
	
	int w;
	int h;
	
	int old_x;
	int old_y;
	
	private Color color;
	
	//------------------------------------------------------------------------//
	
	public Rect(int x, int y, int w, int h, Color color)
	{
      setLocation(x, y);
      
      setSize(w, h);
      
      this.color = color ;
	}
	
	public void setColor(Color c) {
		color = c; 
	}
	
	//------------------------------------------------------------------------//
	
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	//------------------------------------------------------------------------//
	
	public void setSize(int w, int h)
	{
		this.w = w;
		this.h = h;
	}
	
	//------------------------------------------------------------------------//
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		
		g.drawRect(x, y, w, h);
	}


public void chase(Rect r) {
	if(r.x < this.x) x-= 2;
	if(r.x > this.x) x+= 2;
	if(r.y > this.y) y+= 2;
	if(r.y < this.y) y-= 2;
	
	
}
public void avoid(Rect r) {
	if(r.x > this.x) x-= 2;
	if(r.x < this.x) x+= 2;
	if(r.y < this.y) y+= 2;
	if(r.y > this.y) y-= 2;
}
	
public Boolean contains(int mx, int my ) {
		
		return (mx >= x) && 
				(mx <= x+w) && 
				(my >= y) && 
				(my <= y+h);
}
	
	



public void moveBy(int dx, int dy) {
	old_x = x;  
	old_y = y;  
	
	x += dx;
	y += dy;
}

public void moveUp(int dy) {
	old_y = y;  
	
	y -= dy;
}


public void moveDown(int dy) {
	old_y = y;  
	
	y += dy;
}

public void moveLeft(int dx) {
	old_x = x;  
	
	x -= dx;
}

public void moveRight(int dx) {
	old_x = x;  
	
	x += dx;
}


public void pushBy(int dx , int dy) {

	x += dx;
	y += dy;
}

public void resizeBy(int dw, int dh) {
	
	w += dw;
	h += dh;
}

public boolean overlap(Rect r) {
	 
	return  (r.y <= y + h)     &&
			(  y <= r.y + r.h) &&
			(r.x <= x + w)     &&
			(  x <= r.x + r.w);
}


public boolean camefromBelow() {
	
	return y < old_y;
}

public boolean camefromAbove() {
	
	return y > old_y;
}

public boolean camefromLeft() {
	
	return x > old_x;
}

public boolean camefromRight() {
	
	return x < old_x;
}
public void setPosition(int x, int y) {
	this.x = x;
	this.y = y;
}





}
