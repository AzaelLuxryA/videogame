import java.awt.*;

public class RoomTwo extends RoomBase {
 
    // Constructor for RoomTwo
    // Parameters:
    // - mainSprite: The main character that will be placed in the room
    // - v1, v2: Enemy objects that exist outside this class and will be placed in the room
    public RoomTwo(Maincharacter mc) {
        
    	// Call the parent constructor (RoomBase), setting up the tile map
        // "map1.map" is the file name for the room's layout, and 40 is the tile size
        super("roomtwo.txt", 40,mc);
        addDoor(new Rect(950,250,32,32,null));
      

        // Add the main character to the room so it can be tracked and updated
    

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
