import java.awt.*;
import java.awt.event.*;

public class MapPanel extends GridPanel implements MouseMotionListener
{
	//------------------------------------------------------------------------//
	
	public MapPanel(TileMapEditor editor, int scale)
	{
		super(editor, scale);
		setBounds(10, 10, 800, 448);
		addMouseMotionListener(this); // ✅ for drag-to-paint
	}

	
	//------------------------------------------------------------------------//
   // Change the TileMap at the mouse location when the mouse is pressed     //
	//------------------------------------------------------------------------//
	
	public void mousePressed(MouseEvent e)
	{
		tilemap.changeAt(mx + EditorCamera.x, my + EditorCamera.y);
		
		repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
	    mx = e.getX();
	    my = e.getY();

	    tilemap.changeAt(mx + EditorCamera.x, my + EditorCamera.y);
	    repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	    mx = e.getX();
	    my = e.getY();
	    hoverbox.setLocation((mx / scale) * scale, (my / scale) * scale);
	    repaint();
	}

	
	

	
	//------------------------------------------------------------------------//
   // Scroll the TileMap using the arrow Keys and                            //
	// Change the TileMap at the mouse location when the Spacebar is pressed  //
	//------------------------------------------------------------------------//
	
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_UP   )   EditorCamera.moveUp   (scale);
		if(code == KeyEvent.VK_DOWN )   EditorCamera.moveDown (scale);
		if(code == KeyEvent.VK_LEFT )   EditorCamera.moveLeft (scale);
		if(code == KeyEvent.VK_RIGHT)   EditorCamera.moveRight(scale);
		
		if(code == KeyEvent.VK_SPACE)   tilemap.changeAt(mx + EditorCamera.x, my + EditorCamera.y);
		
		repaint();
	}
	
	//------------------------------------------------------------------------//
   // Draw the TileMap and the hoverbox                                      //
	//------------------------------------------------------------------------//
	
	public void paint(Graphics g)
	{
		tilemap.draw(g);	   	
		
		if(mouseinPanel)   hoverbox.draw(g);
	}

	//------------------------------------------------------------------------//
	
}