import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class LightOverlay {
	private float minAlpha = 0.50f;
    private BufferedImage overlay;
    private float alpha = 0.0f;
    private boolean fadingIn = true;
    private float fadeSpeed = 0.009f;
    private float maxAlpha = 0.8f;

    public LightOverlay(String imagePath) throws IOException {
        overlay = ImageIO.read(new File(imagePath));
    }

    public void update() {
        if (fadingIn) {
            alpha += fadeSpeed;
            if (alpha >= maxAlpha) {
                alpha = maxAlpha;
                fadingIn = false;
            }
        } else {
            alpha -= fadeSpeed;
            if (alpha <= minAlpha) {
                alpha = minAlpha;
                fadingIn = true;
            }
        }

        // Clamp alpha just in case
        alpha = Math.max(minAlpha, Math.min(alpha, maxAlpha));
    }

    public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
        if (overlay == null) return;

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(ac);

        // Draw the overlay stretched to fit the screen
        g2d.drawImage(overlay, 0, 0, screenWidth, screenHeight, null);

        // Reset composite to default
        g2d.setComposite(AlphaComposite.SrcOver);
    }
}