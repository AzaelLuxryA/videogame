import java.awt.Color;
import java.awt.Graphics;

public class RoomOne extends RoomBase {
    // Declare enemy objects that will be added to the room

    // Constructor for RoomTwo
    // Parameters:
    // - mainSprite: The main character that will be placed in the room
    // - v1, v2: Enemy objects that exist outside this class and will be placed in the room
	Maincharacter mc;
    public RoomOne(Maincharacter mc) {
        
    	// Call the parent constructor (RoomBase), setting up the tile map
        // "map1.map" is the file name for the room's layout, and 40 is the tile size
        super("room1.txt", 40,mc);
        
        addDoor(new Rect(440,20,32,32, null));

        // Assign the enemies passed from outside this class to local variables
   
      
    }

    // Override the update method to manage room-specific logic
    @Override
    public void update() {
        // If RoomBase has general update logic, we keep it by calling super.update()
        super.update();
    }

    // Override the draw method to render the room and its contents
    @Override
    public void draw(Graphics g) {
        // Call the parent draw method to handle standard rendering logic
        super.draw(g);
        
        
        
        // If you want to add custom graphics like UI elements or effects,
        // this is where you would do it.
    }
}
