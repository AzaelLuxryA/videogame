import java.awt.*;
import java.io.*;

public class TileMap
{
	//------------------------------------------------------------------------//
	
	String filename = "";
	
	String[] map = {""};
	
	Image[]  tile = null;
	String[] tile_name = null;
	
	Image    background = null;
	String   background_name = null;

	int scale = 32;
	
	char active_tile = '.';
	
	//------------------------------------------------------------------------//
	
	public TileMap()
	{
		
	}

	//------------------------------------------------------------------------//

	public TileMap(String filename, int scale)
	{
		this.filename = filename;
		
		loadMap(filename);
		
		loadAssets();		
		
		this.scale = scale;
	}
	
	//------------------------------------------------------------------------//

	public void create(int rows, int cols)
	{
		map = new String[rows];
		
		StringBuilder empty_row = new StringBuilder(cols);
		
		for(int col = 0; col < cols; col++)
		{	
			empty_row.append('.');
		}
		
		for(int row = 0; row < rows; row++)
		{	
			map[row] = empty_row.toString();
		}
	}
	
	
	public int getRows() {
	    return map.length;
	}

	public int getCols() {
	    return map[0].length();
	}

	public int getTileSize() {
	    return scale;
	}
	
	//------------------------------------------------------------------------//

	public void setMap(String[] map)
	{
		this.map = map;
	}
	
	//------------------------------------------------------------------------//

	public void setTileNames(String[] tile_name)
	{
		this.tile_name = tile_name;
	}
	
	//------------------------------------------------------------------------//

	public void setBackgroundNames(String background_name)
	{
		this.background_name = background_name;
	}
	
	//------------------------------------------------------------------------//
   // Load TileMap data from a text file                                     //
	//------------------------------------------------------------------------//
	
	public void loadMap(String filename)
	{
		this.filename = filename;
		
		File file = new File(filename);
		
		try
		{
		   BufferedReader input = new BufferedReader(new FileReader(file));
		   
		   map       = loadStringArray(input);    // Load Map Codes
		   tile_name = loadStringArray(input);    // Load Tile Filenames		   
		   background_name = input.readLine();	   // Load Background Filename
		   
		   input.close();
		}
		catch(IOException x) {};		
	}

	//------------------------------------------------------------------------//
   // Load Images for Tiles and Background as indicated TileMap data files   // 
	//------------------------------------------------------------------------//
	
	public void loadAssets()
	{		
	   tile      = new Image[tile_name.length];

	   for(int i = 0; i < tile.length; i++)
		{
			tile[i] = getImage(tile_name[i]);
		}
		
		background = getImage(background_name);		
	}
	
	//------------------------------------------------------------------------//
   // Save TileMap data to a text file                                       //
	//------------------------------------------------------------------------//
	
	public void saveMap(String filename)
	{
		File file = new File(filename);
		
		try
		{
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			
			saveStringArray(map, output);        // Save Map Codes			
			saveStringArray(tile_name, output);  // Save Tile Filenames
			output.write(background_name);       // Save Background Filename
			
			output.close();
		}
		catch(IOException x) {}
	}
	
	//------------------------------------------------------------------------//
	
	public String[] loadStringArray(BufferedReader input) throws IOException
	{
	   int n = Integer.parseInt(input.readLine());  
	   
	   String[] s = new String[n];
	   
	   for(int i = 0; i < s.length; i++)
	   
	   	s[i] = input.readLine();
	   
	   return s;
	}

	//------------------------------------------------------------------------//

	public void saveStringArray(String[] s, BufferedWriter output) throws IOException
	{
		output.write(s.length + "\n");
		
		for(int i = 0; i < s.length; i++)
			
			output.write(s[i] + "\n");
	}
	
	//------------------------------------------------------------------------//
   // return the value at location (x, y) of the TileMap                     // 
	//------------------------------------------------------------------------//
	
	public char valueAt(int y, int x)
	{
		int row = y / scale;
		int col = x / scale;
		
		return map[row].charAt(col);
	}
	
	//------------------------------------------------------------------------//
   // Set the tile code that will be used to make changes to the TileMap     //
	//------------------------------------------------------------------------//

	public void setActiveTile(char code)
	{
		active_tile = code;
	}

	//------------------------------------------------------------------------//
   // Change the tile in the TileMap at location (x, y) to the active_tile   //
	//------------------------------------------------------------------------//
	
	public void changeAt(int x, int y)
	{
		int row = y / scale;
		int col = x / scale;
		
		map[row] = map[row].substring(0, col)   + 
				     
				     active_tile                  +  
				     
				     map[row].substring(col + 1);
	}
	
   //-------------------------------------------------------------------------//
   // Draw Clipped TileMap                                                       //
	//------------------------------------------------------------------------//

	public void draw(Graphics g)
	{
		g.drawImage(background, 0, 0, null);
	
		int c_row = Math.max(EditorCamera.y / scale, 0);
		int c_col = Math.max(EditorCamera.x / scale, 0);
		
		for(int row = c_row; row < Math.min(c_row+950/scale, map.length); row++)
		{	
			for(int col = c_col; col < Math.min(c_col+1500/scale, map[0].length()); col++)
			{
				char c = map[row].charAt(col);				
				
				if((c != '.') && ((c - 'A') < tile.length))
				{
				   g.drawImage(tile[c - 'A'], scale*col - EditorCamera.x, scale*row - EditorCamera.y, scale, scale, null);                      			
				}
			}
		}
	}
	
	
	
	
	public void draw(Graphics g, Camera camera)
	{
	    g.drawImage(background, -camera.x, -camera.y, null); // Optional: background movement

	    int c_row = Math.max(camera.y / scale, 0);
	    int c_col = Math.max(camera.x / scale, 0);

	    int maxRow = Math.min(c_row + (950 / scale), map.length);
	    int maxCol = Math.min(c_col + (1500 / scale), map[0].length());

	    for (int row = c_row; row < maxRow; row++) {
	        for (int col = c_col; col < maxCol; col++) {
	            char c = map[row].charAt(col);
	            if ((c != '.') && ((c - 'A') < tile.length)) {
	                g.drawImage(tile[c - 'A'], scale * col - camera.x, scale * row - camera.y, scale, scale, null);
	            }
	        }
	    }
	}

	
	//------------------------------------------------------------------------//
   // Draw the TileMap (Deprecated due to lack of clipping)                  //
	//------------------------------------------------------------------------//
		
	public void drawNoClipping(Graphics g)
	{
		g.drawImage(background, 0, 0, null);
		
		for(int row = 0; row < map.length; row++)
		{	
			for(int col = 0; col < map[row].length(); col++)
			{
				char c = map[row].charAt(col);				
				
				if(c != '.')
				{	
			      g.drawImage(tile[c - 'A'], scale*col - EditorCamera.x, scale*row - EditorCamera.y, scale, scale, null);
				}
			}
		}		
	}
	
	//------------------------------------------------------------------------//
   // Convenience method for loading images                                  //
	//------------------------------------------------------------------------//
	
	public Image getImage(String filename)
	{
		return Toolkit.getDefaultToolkit().getImage(filename);
	}

	//------------------------------------------------------------------------//
		
/*	
	public boolean clearAbove(Rect r)
	{
		int top    = r.y;
		int left   = r.x;
		int right  = r.x + S-1;
		
		return (valueAt(top-S/4, left) == '.')  && (valueAt(top-S/4, right) == '.');
	}
	
	//------------------------------------------------------------------------//
	
	public boolean clearBelow(Rect r)
	{
		int bottom = r.y + S-1;
		int left   = r.x;
		int right  = r.x + S-1;

		return  (valueAt(bottom+r.vy+1, left) ==  '.')  && (valueAt(bottom+r.vy+1, right) == '.');
	}
	
	//------------------------------------------------------------------------//
	
	public boolean clearLeftOf(Rect r)
	{
		int top    = r.y;
		int bottom = r.y + S-1;
		int left   = r.x;
		
		return (valueAt(top, left-S/8) == '.')  && (valueAt(bottom, left-S/8) == '.');
	}
	
	//------------------------------------------------------------------------//
	
	public boolean clearRightOf(Rect r)
	{
		int top    = r.y;
		int bottom = r.y + S-1;
		int right  = r.x + S-1;
		
		return (valueAt(top, right+S/8) == '.')  && (valueAt(bottom, right+S/8) == '.');
	}
//*/	
	//------------------------------------------------------------------------//
	
}