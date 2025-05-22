
import java.awt.Graphics;

public class RoomThree extends RoomBase {
    public RoomThree() {
    	// "map3.map" is the file name for the room's layout, and 40 is the tile size
        super("map3.map", 40);
	}

	// Declare enemy objects that will be added to the room

    // Constructor for RoomThree
    // Parameters:
    // - mainSprite: The main character that will be placed in the room
    // - v1, v2: Enemy objects that exist outside this class and will be placed in the room
   

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
        
        
    }
}
