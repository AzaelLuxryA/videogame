import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Animation {
    Image[] images;
    int current = 0;
    int duration;
    int delay;

    public Animation(String baseName, int frameCount, int duration) {
        images = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            images[i] = Toolkit.getDefaultToolkit().getImage(baseName + i + ".png");
        }
        
        this.duration = duration;
        this.delay = duration;
    }
    

    public Image nextImage() {
        if (delay == 0) {
            current++;
            if (current >= images.length) current = 0;
            delay = duration;
        } else {
            delay--;
        }
        return images[current];
    }

    public Image stillImage() {
        return images[0];
    }

    public void draw(Graphics pen, int x, int y, int width, int height) {
        pen.drawImage(images[current], x, y, width, height, null);
    }
}
