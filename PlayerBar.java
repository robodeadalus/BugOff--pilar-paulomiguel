
/**
@author John Victor Warain, Paulo Miguel A. Pilar (226808), (225008)
@version May 7, 2023
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
*
* This class is for the health bar of the player. It creates the three hearts that the user sees on the upper left
 * */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class PlayerBar {

    private int health, height, width, heartY;
    public BufferedImage heart, emptyheart, temp;
    /**
    * Constructs a PlayerBar object.
    * Initializes the player's health to 3 and sets the dimensions of the hearts.
    * Loads the heart images from the resources.
    */
	public PlayerBar(){
        health = 3;
        width = 75;
        height = 60;
        heartY = 50;
        
        getHeartImage();
	}

    /**
 * Loads the heart images from the resources.
 * The heart and emptyheart images are loaded.
 * If an IOException occurs, it is printed to the standard error stream.
 */
    public void getHeartImage(){

        try {
            heart = ImageIO.read(getClass().getResourceAsStream("images/heart.png"));
            emptyheart = ImageIO.read(getClass().getResourceAsStream("images/emptyheart.png"));
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
 * Decreases the player's health by one.
 * Called when the player is hurt.
 */
    public void heartDecrease(){
        health--;
    }

    /**
 * Draws the player's health bar on the screen.
 * The hearts corresponding to the player's remaining health are displayed.
 *
 * @param g2d the Graphics2D object to draw the health bar
 */
    public void draw(Graphics2D g2d){

        for(int i = 0; i <= 2; i++){
            g2d.drawImage(emptyheart, 30 + i * 80 , heartY, width, height, null);
        }

        for(int i = 0; i < health; i++){
            g2d.drawImage(heart, 30 + i * 80 , heartY, width, height, null);
        }

    }
}