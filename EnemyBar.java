/**
 * Authors: John Victor Warain, Paulo Miguel A. Pilar (226808), (225008)
 * Version: April 29, 2023
 *
 * Disclaimer:
 * I have not discussed the Java language code in my program
 * with anyone other than my instructor or the teaching assistants
 * assigned to this course.
 * I have not used Java language code obtained from another student,
 * or any other unauthorized source, either modified or unmodified.
 * If any Java language code or documentation used in my program
 * was obtained from another source, such as a textbook or website,
 * that has been clearly noted with a proper citation in the comments
 * of my program.
 *
 * This class represents the enemy health bar in the game. It manages the health, appearance, and rendering of the enemy health bar.
 */
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnemyBar {

    private int height, hitCounts, width, barY, barX;
    public BufferedImage fly, bar, emptybar, healthbar;

    /**
     * Constructs an EnemyBar object with default settings.
     * It initializes the width, height, position, and loads the bar images.
     */
    public EnemyBar() {
        width = 400;
        height = 60;

        barY = 50;
        barX = 350;

        getBarImage();
    }

    /**
     * Loads the images for the empty bar, health bar, and enemy sprite from the resources.
     * It assigns the loaded images to the respective BufferedImage variables.
     * https://javarevisited.blogspot.com/2011/12/read-write-image-in-java-example.html#axzz81mFzsmo3
     */
    public void getBarImage() {

        try {
            emptybar = ImageIO.read(getClass().getResourceAsStream("images/healthbarframe.png"));
            healthbar = ImageIO.read(getClass().getResourceAsStream("images/redbar.png"));
            fly = ImageIO.read(getClass().getResourceAsStream("images/fly1.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the new health value for the enemy bar.
     * It calculates the hit counts based on the given health value and updates the hitCounts variable.
     *
     * @param x The new health value for the enemy bar.
     */
    public void newHealth(int x) {
        hitCounts = (width / 20) * x;
    }

    /**
     * Renders the enemy health bar on the screen.
     * It draws the empty bar, health bar, and enemy sprite using the provided Graphics2D object.
     *
     * @param g2d The Graphics2D object used for rendering.
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(emptybar, barX, barY, width, height, null);
        g2d.drawImage(healthbar, barX, barY + 5, hitCounts - 1, height - 10, null);
        g2d.drawImage(fly, barX - 30, barY - 20, 120, 90, null);
    }
}
