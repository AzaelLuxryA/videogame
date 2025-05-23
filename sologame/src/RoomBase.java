import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color; // Import the Color class

public class RoomBase {

    protected TileMap tileMap;
    protected ArrayList<Entity> entities = new ArrayList<>();
    protected ArrayList<Rect> doors = new ArrayList<>();
    protected Maincharacter mc;
   // protected ArrayList<Entity> enemies = new ArrayList<>();

    /**
     * Constructs a room by reading its map from the given filename.
     *
     * @param mapFilename The file containing the map data.
     * @param tileSize    The size (in pixels) of each tile.
     */
    public RoomBase(String mapFilename, int tileSize, Maincharacter mc) {
        // TileMap2 reads the map data from a file and loads the assets internally.
        tileMap = new TileMap(mapFilename, tileSize);
        this.mc = mc;
    }

    /**
     * Adds a sprite entity to the room.
     *
     * @param sprite The sprite to add.
     */
    public void addEntity(Entity e) {
    	 entities.add(e);
    }

    /**
     * Updates the room.
     * For example, you might update entity positions or game logic here.
     */
    public void update() {
        // Update game logic or entities as needed.
    }

    /**
     * Draws the room, including the tile map and all entities.
     *
     * @param g The Graphics context.
     */
    
    public void draw(Graphics g) {
    	
        tileMap.draw(g);
        drawDoors(g);
        mc.drawAttackBox(g, null);
        
        for (Entity sprite : entities) {
        	g.setColor(Color.black);
            sprite.draw(g);
            sprite.drawHitBox(g);
            if (sprite instanceof MainC)
            	sprite.drawHUD(g);
            if(sprite.isEnemy()) {
            	sprite.drawroamingSpace(g);
            	sprite.drawHealthBar(g);
            //sprite.drawchasingHitBox(g);
            }
        }
                
        // Draw collision rectangles for debugging
        //drawCollisionRectangles(g);
        
        //drawTileRectangles(g); // Draw tile rectangles
    }

    /**
     * Checks if the pixel position (x,y) is blocked.
     * Converts pixel coordinates to tile coordinates using tileMap.S.
     * We assume that floor tiles are represented by '.' while any other character
     * indicates a blocked tile.
     *
     * @param x The x coordinate in pixels.
     * @param y The y coordinate in pixels.
     * @return true if the tile at (x,y) is blocked; false otherwise.
     */
    public boolean isBlocked(int x, int y) {
        int col = x / tileMap.scale;
        int row = y / tileMap.scale;
        if (row >= 0 && row < tileMap.map.length && 
            col >= 0 && col < tileMap.map[row].length()) {
            char tileCode = tileMap.map[row].charAt(col);
            
            // Allow movement over `.` and `^`
            return tileCode != '.' && tileCode != '^' && tileCode !='B';
        }
        
        // Outside the map bounds, we consider the area blocked.
        return true;
    }
    
    public void addDoor(Rect door) {
    	//Rect door = new Rect(x, y, 32,32, Color.red);
    	doors.add(door);
    }
    
    
    public void drawDoors(Graphics g) {
    	for (Rect door: doors){
    		door.draw(g);
    	}
    }
    
    public Rect getDoor(int index) {
    	return doors.get(index);
    }
    
    


    /**
     * Checks if a sprite can move to a given position without collisions.
     *
     * @param sprite The sprite to check.
     * @param newX   The potential new x coordinate.
     * @param newY   The potential new y coordinate.
     * @return true if the sprite can move to the position, false otherwise.
     */
    public boolean canMoveTo(Sprite sprite, int newX, int newY) {
        // Calculate smaller width and height for the collision rectangle
        int newWidth = sprite.w / 2; // Or any factor you like (e.g., 2 for half size)
        int newHeight = sprite.h / 2;
        // Calculate the position to center the smaller rectangle
        int newXOffset = (sprite.w - newWidth) / 2;
        int newYOffset = (sprite.h - newHeight) / 2;

        // Create a rectangle representing the sprite's intended new position
        Rect newRect = new Rect(newX + newXOffset, newY + newYOffset, newWidth, newHeight, null);
        newRect.setColor(Color.red);

        // Check tilemap collision using the rectangle
        if (isRectBlocked(newRect)) {
            return false;
        }

        // No collision
        return true;
    }

    /**
     * Checks if the given rectangle collides with a blocked tile.
     *
     * @param rect The rectangle to check.
     * @return true if the rectangle collides with a blocked tile, false otherwise.
     */

    private boolean isRectBlocked(Rect rect) {
        // Get the coordinates of the corners of the rectangle
        int left = rect.x;
        int right = rect.x + rect.w - 1;
        int top = rect.y;
        int bottom = rect.y + rect.h - 1;

        // Check the four corners of the rectangle for collision
        if (isBlocked(left, top) || isBlocked(right, top) ||
            isBlocked(left, bottom) || isBlocked(right, bottom)) {
            return true; // Collision with a blocked tile
        }
        return false;
    }

    /**
     * Handles sprite-to-sprite collisions.  This method is called *after* a sprite
     * has moved, to resolve any resulting overlaps.
     *
     * @param sprite The sprite to check for collisions against other entities.
     */
    public void handleSpriteCollisions(Sprite sprite) {
        //  No Collision Detection
    }

    /**
     * Resolves the overlap between two sprites (a simple example).
     * This basic version just pushes the 'sprite' back to its old position.
     * You might want more sophisticated resolution (e.g., sliding along the
     * edge of the other sprite).
     *
     * @param sprite The sprite that moved.
     * @param other  The other sprite it collided with.
     */
    private void resolveOverlap(Sprite sprite, Sprite other) {
        // No Collision Detection
    }

    /**
     * Draws the collision rectangles for all entities in the room.
     *
     * @param g The Graphics context.
     */
    private void drawCollisionRectangles(Graphics g) {
        for (Sprite sprite : entities) {
            // Set the color for the rectangle (e.g., red)
            g.setColor(Color.RED);
            // Calculate smaller width and height (e.g., half the original size)
            int newWidth = sprite.w / 2;
            int newHeight = sprite.h / 2;
            // Calculate the position to center the smaller rectangle
            int newX = sprite.x + (sprite.w - newWidth) / 2;
            int newY = sprite.y + (sprite.h - newHeight) / 2;

            // Draw the smaller rectangle
            g.drawRect(newX, newY, newWidth, newHeight);
        }
    }

    private void drawTileRectangles(Graphics g) {
        int tileSize = tileMap.scale;
        g.setColor(Color.BLUE); // Choose a color for the tile rectangles

        for (int row = 0; row < tileMap.map.length; row++) {
            for (int col = 0; col < tileMap.map[row].length(); col++) {
                // Calculate the x and y coordinates for the top-left corner of the tile
                int x = col * tileSize;
                int y = row * tileSize;
                g.drawRect(x, y, tileSize, tileSize); // Draw the rectangle
            }
        }
    }

    /**
     * Retrieves the TileMap2 instance used by this room.
     *
     * @return The current TileMap2.
     */
    public TileMap getTileMap() {
        return tileMap;
    }

    /**
     * Retrieves the list of entities in this room.
     *
     * @return An ArrayList of Sprite objects.
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    public void removeEntity(Entity entity) {
    	Iterator<Entity> it = entities.iterator();
    	while (it.hasNext()) {
    		Entity e = it.next();
    		if (e.equals(entity)) {
    			it.remove();
    		}
    	}
    }
    
    public void removeDeadEntity() {
    	Iterator<Entity> it = entities.iterator();
    	
    	while (it.hasNext()) {
    		Entity e = it.next();
    		if(e.isDead()) it.remove();
    	}
    	
    	
    }
    
    
    public Boolean IsRoomOver() { // check if room is no more entities are in the room.
    	return entities.isEmpty();
    }
    
    
}
