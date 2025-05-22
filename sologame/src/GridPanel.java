import java.awt.*;
import java.awt.event.*;

public class GridPanel extends Panel implements KeyListener, MouseListener, MouseMotionListener
{
	//------------------------------------------------------------------------//
	
	Image    offscreen;         
	Graphics offscreen_g;
	
	TileMap tilemap;
	
	Color bgcolor;
	
	int scale;
	
	Rect hoverbox;
	
	int mx = 0;
	int my = 0;
	
	boolean mouseinPanel = false;
		
	//------------------------------------------------------------------------//
	
	public GridPanel(TileMapEditor editor, int scale)
	{
		offscreen    = editor.offscreen;
		offscreen_g  = editor.offscreen_g;
		
		tilemap      = editor.tilemap;
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		setBGColor();
		
		setScale(scale);
		
		hoverbox = new Rect(-100, 100, scale, scale, null);
		hoverbox.setColor(Color.GREEN);
	}
		
	//------------------------------------------------------------------------//
   // Override for a different background color in derived class             //
	//------------------------------------------------------------------------//

	public void setBGColor()
	{
		bgcolor = Color.LIGHT_GRAY;
	}
		
	//------------------------------------------------------------------------//

	public void setScale(int scale)
	{
		this.scale = scale;
	}
	
	//------------------------------------------------------------------------//
   // Place the hoverbox around the cell containing the mouse                //   
   //------------------------------------------------------------------------//
	
	public void mouseMoved(MouseEvent e)
	{
		mx = e.getX();
		my = e.getY();
				
		hoverbox.setLocation(mx/scale*scale, my/scale*scale);
		
		repaint();		
	}
	
	//------------------------------------------------------------------------//
   // Note that the mouse is in the Panel for drawing hoverbox               //
	//------------------------------------------------------------------------//
	
	public void mouseEntered(MouseEvent e)
	{
		mouseinPanel = true;
		
		repaint();
	}
	
	//------------------------------------------------------------------------//
   // Note that the mouse is not in the Panel so as not to draw hoverbox     //
	//------------------------------------------------------------------------//
	
	public void mouseExited(MouseEvent e)
	{
		mouseinPanel = false;
		
		repaint();
	}
	
	//------------------------------------------------------------------------//
	
	public void mouseDragged (MouseEvent e)  {  }
	public void mousePressed (MouseEvent e)  {  }	
	public void mouseReleased(MouseEvent e)  {  }
	public void mouseClicked (MouseEvent e)  {  }

	public void keyPressed (KeyEvent e)  {  }
	public void keyReleased(KeyEvent e)  {  }
	public void keyTyped   (KeyEvent e)  {	 }
	
	//------------------------------------------------------------------------//
   // Implement Double Buffering and use of Background Color                 //
	//------------------------------------------------------------------------//
	
	public void update(Graphics g)
	{
		offscreen_g.setColor(bgcolor);
		
		offscreen_g.fillRect(0, 0, 1920, 1080);
		
		paint(offscreen_g);
		
		g.drawImage(offscreen, 0, 0, null);
	}

	//------------------------------------------------------------------------//	
}