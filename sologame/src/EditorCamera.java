public class EditorCamera
{
	//------------------------------------------------------------------------//
	
	static int x = 0;
	static int y = 0;
	
	//------------------------------------------------------------------------//
	
	public static void setLocation(int x, int y)
	{
		EditorCamera.x = x;
		EditorCamera.y = y;
	}
	
	
 
	//------------------------------------------------------------------------//
	
	public static void moveUp(int dy)
	{
		y -= dy;
	}
	
	//------------------------------------------------------------------------//
	
	public static void moveDown(int dy)
	{
		y += dy;
	}
	
	//------------------------------------------------------------------------//
	
	public static void moveLeft(int dx)
	{
		x -= dx;
	}
	
	//------------------------------------------------------------------------//
	
	public static void moveRight(int dx)
	{
		x += dx;
	}
	
	//------------------------------------------------------------------------//
	
}