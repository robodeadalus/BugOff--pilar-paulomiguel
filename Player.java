
/**
@author John Victor Warain, Paulo Miguel A. Pilar (226808), (225008)
@version April 18, 2023
**/
/*
I have not discussed the Java language code in my program
with anyone other than my instructor or the teaching assistants
assigned to this course.
I have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in my program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of my program.

 * The Player class represents a player entity in the game.
 * It handles player movement, collision detection, sprite animation,
 * and drawing the player on the graphics context.
 * */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Player extends Entity {

    private boolean right, left, up, down;
    private boolean isCollidingRight, isCollidingLeft, isCollidingDown, isCollidingUp;
    private boolean isVulnerable ;
    private boolean type;
    private int immunityFrames, counter;
    public BufferedImage char1, char2, char1flash, char2flash, temp;

    /*
     *Constructs a Player object with the specified position, size, and type.
     * It sets the attributes of the player.
     */
    public Player(int x, int y, int size, boolean isOrange) {
        this.x = x;
        this.y = y;
        health = 3;
        counter = 0;
        this.size = size;
        speed = 10;
        isVulnerable = true;
        type = isOrange; 

        getPlayerImage();
    }

    
    /**
     * Loads the player images based on the type of the player.
     * They will either be an apple or an orange
     */
    public void getPlayerImage() {
        if(type == true){
            try {
                char1 = ImageIO.read(getClass().getResourceAsStream("images/orange1.png"));
                char2 = ImageIO.read(getClass().getResourceAsStream("images/orange2.png"));
                char1flash = ImageIO.read(getClass().getResourceAsStream("images/orange1flash.png"));
                char2flash = ImageIO.read(getClass().getResourceAsStream("images/orange2flash.png"));
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type == false){
            try {
                char1 = ImageIO.read(getClass().getResourceAsStream("images/apple1.png"));
                char2 = ImageIO.read(getClass().getResourceAsStream("images/apple2.png"));
                char1flash = ImageIO.read(getClass().getResourceAsStream("images/apple1flash.png"));
                char2flash = ImageIO.read(getClass().getResourceAsStream("images/apple2flash.png"));
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Performs the sprite animation based on the game counter and player's vulnerability state.
     * It uses a counter system to toggle between frames.
     */
    public void spriteAnimation() {
        if (counter < 25) {
            if(isVulnerable == false){
                immunityFrames++;
                if(immunityFrames >= 20)
                    temp = char1;
                else
                    temp = char1flash;
            }
            else{
                temp = char1;
            }
            counter++;
        } else if (counter < 35) {
            if(isVulnerable == false){
                immunityFrames++;
                if(immunityFrames >= 20)
                    temp = char2flash;
                else
                    temp = char2;
            }
            else{
                temp = char2;
            }
            counter++;
        } else {
            counter = 0;
        }

        if(immunityFrames == 40){
            isVulnerable = true;
            immunityFrames = 0;
        }
    }

    /**
     * Moves the player based on the current direction and collision status.
     */
    public void movePlayer() {
        if (right && !isCollidingRight) {
            moveH(speed);
        }
        if (left && !isCollidingLeft) {
            moveH(-speed);
        }
        if (up && !isCollidingUp) {
            moveV(-speed);
        }
        if (down && !isCollidingDown) {
            moveV(speed);
        }
    }

    /**
     * Moves the player horizontally by the specified amount.
     *
     * @param n The amount to move horizontally.
     */
    public void moveH(int n) {
        x += n;
    }

    /**
     * Moves the player vertically by the specified amount.
     *
     * @param n The amount to move vertically.
     */
    public void moveV(int n) {
        y += n;
    }

    /**
     * Sets the X-coordinate of the player's position.
     *
     * @param n The new X-coordinate.
     */
    public void setX(int n) {
        x = n;
    }

    /**
     * Sets the Y-coordinate of the player's position.
     *
     * @param n The new Y-coordinate.
     */
    public void setY(int n) {
        y = n;
    }

    /**
     * Returns the X-coordinate of the player's position.
     *
     * @return The X-coordinate of the player.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y-coordinate of the player's position.
     *
     * @return The Y-coordinate of the player.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the size of the player.
     *
     * @return The size of the player.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the right direction flag for the player movement.
     *
     * @param arg The flag indicating the right direction.
     */
    public void setRight(boolean arg) {
        right = arg;
    }

    /**
     * Sets the left direction flag for the player movement.
     *
     * @param arg The flag indicating the left direction.
     */
    public void setLeft(boolean arg) {
        left = arg;
    }

    /**
     * Sets the up direction flag for the player movement.
     *
     * @param arg The flag indicating the up direction.
     */
    public void setUp(boolean arg) {
        up = arg;
    }

    /**
     * Sets the down direction flag for the player movement.
     *
     * @param arg The flag indicating the down direction.
     */
    public void setDown(boolean arg) {
        down = arg;
    }

    /**
     * Decreases the player's health by 1 and makes the player temporarily invulnerable.
     */
    public void playerHurt() {
        health -= 1;
        isVulnerable = false;
    }

    /**
     * Checks if the player is currently vulnerable.
     *
     * @return true if the player is vulnerable, false otherwise.
     */
    public boolean isPlayerVulnerable() {
        return isVulnerable;
    }

    /**
     * Sets the collision status for the right side of the player.
     *
     * @param arg The collision status for the right side.
     */
    public void collideRight(boolean arg) {
        isCollidingRight = arg;
    }

    /**
     * Sets the collision status for the left side of the player.
     *
     * @param arg The collision status for the left side.
     */
    public void collideLeft(boolean arg) {
        isCollidingLeft = arg;
    }

    /**
     * Sets the collision status for the upper side of the player.
     *
     * @param arg The collision status for the upper side.
     */
    public void collideUp(boolean arg) {
        isCollidingUp = arg;
    }

    /**
     * Sets the collision status for the lower side of the player.
     *
     * @param arg The collision status for the lower side.
     */
    public void collideDown(boolean arg) {
        isCollidingDown = arg;
    }

    /**
     * Draws the player on the specified graphics context.
     *
     * @param g2d The graphics context on which to draw the player.
     */
    public void draw(Graphics2D g2d) {
        if (health >= 0) {
            spriteAnimation();
            BufferedImage image = temp;
            if (type) {
                g2d.drawImage(image, x, y, 80, 100, null);
            } else {
                g2d.drawImage(image, x, y, 70, 100, null);
            }
        }
    }
}
