import java.awt.*;
import java.awt.event.*;

public class TilePanel extends GridPanel implements MouseMotionListener
{
	//------------------------------------------------------------------------//
	
	Rect selectbox;
	
	//------------------------------------------------------------------------//
	public TilePanel(TileMapEditor editor, int scale)
	{
	    super(editor, scale);
	    
	    selectbox = new Rect(-100, 100, scale, scale, null);
	    selectbox.setColor(Color.YELLOW);
	    
	    setBounds(850, 10, scale*8, scale*8);
	    
	    requestFocus(); // ✅ ensure it gets focus to trigger mouseMoved
	    addMouseMotionListener(this);

	}

	//------------------------------------------------------------------------//

	public void setBGColor()
	{
		bgcolor = Color.BLACK;
	}
	
	//------------------------------------------------------------------------//
   // Mark tile selected by mouse press and record code for that tile        //
	//------------------------------------------------------------------------//
	
	@Override
	public void mouseMoved(MouseEvent e) {
	    mx = e.getX();
	    my = e.getY();
	    hoverbox.setLocation((mx / scale) * scale, (my / scale) * scale);
	    repaint();
	}


	public void mousePressed(MouseEvent e)
	{
		int row = my / scale;
		int col = mx / scale;
		
		selectbox.setLocation(scale*col, scale*row);
		
		int index = 8 * row + col;
		
		char tile_code = (char)((int)'A' + index);
		
		if(index < tilemap.tile.length)   tilemap.setActiveTile(tile_code);
		
		else                              tilemap.setActiveTile('.');
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
	    mouseinPanel = true;
	    repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
	    mouseinPanel = false;
	    repaint();
	}

	
	//------------------------------------------------------------------------//
   // Draw the tile palette, selectbox, and hoverbox                         //
	//------------------------------------------------------------------------//
	
	public void paint(Graphics g)
	{
		drawPalette(g);
		
		selectbox.draw(g);
		
		if(mouseinPanel)   hoverbox.draw(g);
	}

	//------------------------------------------------------------------------//
   // Draw each tile from the TileMap in an 8x8 grid                         //
	//------------------------------------------------------------------------//
	
	public void drawPalette(Graphics g)
	{
		if(tilemap.tile != null)
		{
			int padding = 8;
			
			for(int row = 0; row < 8; row++)
			{
				for(int col = 0; col < 8; col++)
				{
					if(8*row+col < tilemap.tile.length)
					{
						g.drawImage
						(
							tilemap.tile[8 * row + col], 
							scale * col + padding / 2, scale * row + padding / 2,
							scale - padding, scale - padding, 
							null
						);
					}
				}
			}					
		}
	}
	
	//------------------------------------------------------------------------//	
}